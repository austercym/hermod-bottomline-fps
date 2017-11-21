package com.hermod.bottonline.fps.listeners;

import java.io.*;
import java.util.UUID;

import javax.jms.*;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPayment;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19;
import iso.std.iso._20022.tech.xsd.pacs_008_001.Document;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hermod.bottonline.fps.services.kafka.KafkaSender;
import com.hermod.bottonline.fps.services.transform.FPSTransform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.hermod.bottonline.fps.utils.generators.EventGenerator;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

@Component(value = "mqListener")
@Scope("prototype")
public class MQListener extends BaseListener implements MessageListener {

    public static final String PAYMENT_TYPE = "SIP";
    public static final String REJECT_CODE = "RJCT";
    public static final String NO_VALIDATION_CODE = "9999";
    private static Logger LOG = LogManager.getLogger(MQListener.class);

    @Autowired
    private Gson gson;

    @Value("{entity.name}")
    private String entity;
    @Value("${brand.name}")
    private String brand;
    @Value("${kafka.topic.outbound}")
    private String outboundTopic;
    @Value("${kafka.topic.outbound.error}")
    private String outboundErrorTopic;

    @Value("${kafka.topic.inbound}")
    private String replyTo;




    @Value("${jms.mq.bottomline.environment}")
    private String bottomlineEnv;


    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    private KafkaSender kafkaSender;

    @Override
    public void onMessage(Message message) {

        LOG.info("Entered in messagge reception ...............");
        InputStream stream = null;
        Reader reader = null;

        try {
            if (message instanceof TextMessage) {
                reader = new StringReader(((TextMessage) message).getText());
            } else if (message instanceof BytesMessage) {
                BytesMessage msg = (BytesMessage) message;
                byte[] data = new byte[(int) msg.getBodyLength()];
                msg.readBytes(data);
                stream = new ByteArrayInputStream(data);

                reader = new InputStreamReader(stream);
            } else {
                throw new MessageConversionException("The received message with type " + message.getJMSType() + " is not recognized.");
            }

            sendMessageToTopic(reader);
        } catch (Exception e) {
            throw new MessageConversionException("Exception in message reception. Message: " + e.getMessage(), e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                LOG.error("Error when try close the streams resources. Message: {}", e.getMessage(), e);
            }
        }
    }

    public void sendMessageToTopic(Reader reader) throws ConversionException {
        Source source;
        boolean schemaValidation = true;
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (reader != null) {
            Resource xsdResource = new ClassPathResource("./xsd/pacs.008.001.05.xsd");
            try {
                StringWriter writer = new StringWriter();
                IOUtils.copy(reader, writer);
                String message = writer.toString();
                try {
                    Source src = new StreamSource(new StringReader(message));
                    // Validate against scheme

                    Schema schema = schemaFactory.newSchema(new StreamSource(xsdResource.getInputStream()));
                    Validator validator = schema.newValidator();
                    validator.validate(src);
                } catch (SAXException ex) {
                    schemaValidation = false;
                    LOG.error("Error validate message with scheme: {}", ex.getMessage());
                } catch (IOException e) {
                    schemaValidation = false;
                    LOG.error("Error I/O: {}", e.getMessage());
                }

                // Getting Avro
                Source src = new StreamSource(new StringReader(message));
                final JAXBElement result = (JAXBElement) marshaller.unmarshal(src);
                FPSMessage fpsMessage = (FPSMessage) result.getValue();

                // Call the correspondent transform
                FPSTransform transform = getTransform(fpsMessage.getClass().getPackage().getName());
                if (transform != null) {
                    Object avroFpsMessage = transform.fps2avro(fpsMessage);

                    boolean isValid = validMessage((FPSAvroMessage)avroFpsMessage);

                    LOG.info("Sending message to Kafka ...............");
                    //TODO get ID from Generator ID
                    String uuid = UUID.randomUUID().toString();

                    String FPID = extractFPID((FPSAvroMessage) avroFpsMessage);
                    String paymentTypeCode = extractParameterTypeCode((FPSAvroMessage) avroFpsMessage);

                    if (schemaValidation && isValid) {
                        // Send avro message to Kafka

                        FPSInboundPayment fpsRequest = new FPSInboundPayment();
                        fpsRequest.setPaymentDocument((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        fpsRequest.setFPID(FPID);
                        fpsRequest.setPaymentId(uuid);
                        fpsRequest.setPaymentType(paymentTypeCode);

                        Event event = EventGenerator.generateEvent(
                                this.getClass().getName(),
                                FPSEvents.FPS_REQUEST_RECEIVED.getEventName(),
                                uuid,
                                gson.toJson(fpsRequest),
                                entity,
                                brand
                        );

                        kafkaSender.send(
                                outboundTopic,
                                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                                uuid,
                                replyTo, bottomlineEnv, PAYMENT_TYPE
                        );
                    } else {

                        FPSInboundPaymentResponse fpsResponse = new FPSInboundPaymentResponse();

                        fpsResponse.setFPID(FPID);
                        fpsResponse.setPaymentId(uuid);
                        fpsResponse.setOrgnlPaymentDocument((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        fpsResponse.setTxSts(REJECT_CODE);
                        fpsResponse.setStsRsn(NO_VALIDATION_CODE);


                        // Send avro message to Kafka
                        Event event = EventGenerator.generateEvent(
                                this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(),
                                uuid,
                                gson.toJson(fpsResponse),
                                entity,
                                brand
                        );

                        kafkaSender.send(
                                outboundErrorTopic,
                                RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid,
                                replyTo, bottomlineEnv, PAYMENT_TYPE
                        );

                    }
                    LOG.info("Sent message to Kafka ...............");
                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                }
            }catch (ConversionException convEx){
                LOG.error("Error generating Avro file{}", convEx.getMessage());
            }catch(IOException e){
                LOG.error("IO Error {}", e.getMessage());
            }
        }
    }

    private String extractFPID(FPSAvroMessage avroFpsMessage) {
        String FPID = "";
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19 creditTransferTransaction = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) avroFpsMessage.getMessage())
                .getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
        if(!creditTransferTransaction.getInstrForNxtAgt().isEmpty()){
            FPID = creditTransferTransaction.getInstrForNxtAgt().get(0).getInstrInf();
            FPID = FPID.substring(FPID.lastIndexOf('/')+1);
        } else{
            String txId = creditTransferTransaction.getPmtId().getTxId();
            String paymentTypeCode = creditTransferTransaction.getPmtTpInf().getLclInstrm().getPrtry();
            String currency = creditTransferTransaction.getIntrBkSttlmAmt().getCcy();
            String sendingFPSInstitution = creditTransferTransaction.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
            String dateSent = creditTransferTransaction.getIntrBkSttlmDt().replaceAll("-","");
            FPID = txId+paymentTypeCode+dateSent+currency+sendingFPSInstitution;
        }
        return FPID;
    }

    private String extractParameterTypeCode(FPSAvroMessage avroFpsMessage) {
        String paymentTypeCode = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) avroFpsMessage.getMessage()).getFIToFICstmrCdtTrf().getCdtTrfTxInf()
                .get(0).getPmtTpInf().getLclInstrm().getPrtry();
        paymentTypeCode = paymentTypeCode.substring(0, paymentTypeCode.indexOf('/'));
        return paymentTypeCode;
    }

    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }
}
