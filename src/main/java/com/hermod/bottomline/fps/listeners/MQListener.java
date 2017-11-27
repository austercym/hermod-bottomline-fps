package com.hermod.bottomline.fps.listeners;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.storage.PaymentStatus;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.EventGenerator;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPayment;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXException;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

public abstract class MQListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQListener.class);
    public static final String REJECT_CODE = "RJCT";
    public static final String NO_VALIDATION_CODE = "9999";

    @Autowired
    private Gson gson;

    @Autowired
    private IDGeneratorBean idGenerator;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    protected KafkaSender kafkaSender;

    @Value("{entity.name}")
    private String entity;
    @Value("${brand.name}")
    private String brand;
    @Value("${kafka.topic.outbound.request}")
    protected String outboundTopic;
    @Value("${kafka.topic.outbound.reject}")
    private String outboundErrorTopic;

    @Value("${kafka.topic.fps.logging}")
    private String loggingTopic;

    @Value("${kafka.topic.inbound.response}")
    protected String replyTo;

    protected void onMessage(Message message, String paymentType) {

        LOG.info("[FPS][PaymentType: {}] Getting inbound payment message...............", paymentType);
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
            sendMessageToTopic(reader, paymentType);
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
                LOG.error("[FPS][PaymentType: {}] Error closing streams resources. Message: {}", 
                        paymentType, e.getMessage());
            }
        }
    }

    public void sendMessageToTopic(Reader reader, String paymentType) {
        boolean schemaValidation = true;
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (reader != null) {
            Resource xsdResource = new ClassPathResource("./xsd/pacs.008.001.05.xsd");
            String message = "";
            try {
                String uuid = idGenerator.generatorID().getGeneralUniqueId();
                StringWriter writer = new StringWriter();
                IOUtils.copy(reader, writer);
                message = writer.toString();

                //Send mq message to logging topic
                kafkaSender.sendRawMessage(loggingTopic, message, uuid);
                try {
                    Source src = new StreamSource(new StringReader(message));
                    // Validate against scheme

                    Schema schema = schemaFactory.newSchema(new StreamSource(xsdResource.getInputStream()));
                    Validator validator = schema.newValidator();
                    validator.validate(src);
                } catch (SAXException ex) {
                    schemaValidation = false;
                    LOG.error("[FPS][PaymentType: {}] Error Validating message against scheme. Error:{} Message: {}", 
                            paymentType,  ex.getMessage(), message);
                } catch (IOException e) {
                    schemaValidation = false;
                    LOG.error("[FPS][PaymentType: {}] I/O Error. Error:{} Message: {}", 
                            paymentType,  e.getMessage(), message);
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


                    String FPID = extractFPID((FPSAvroMessage) avroFpsMessage);
                    String paymentTypeCode = extractParameterTypeCode((FPSAvroMessage) avroFpsMessage);

                    if (schemaValidation && isValid) {
                        // Send avro message to Kafka
                        FPSInboundPayment fpsRequest = new FPSInboundPayment();
                        fpsRequest.setPaymentDocument((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        fpsRequest.setFPID(FPID);
                        fpsRequest.setPaymentId(uuid);
                        fpsRequest.setPaymentType(paymentTypeCode);

                        LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment request", uuid);

                        Event event = EventGenerator.generateEvent(
                                this.getClass().getName(),
                                FPSEvents.FPS_REQUEST_RECEIVED.getEventName(),
                                uuid,
                                gson.toJson(fpsRequest),
                                entity,
                                brand
                        );


                        String paymentMessage = gson.toJson(fpsRequest.getPaymentDocument());
                        selectMessageDestination(outboundTopic, paymentMessage, uuid, FPID, event);

                        LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment request", uuid);
                    } else {

                        FPSInboundPaymentResponse fpsResponse = new FPSInboundPaymentResponse();

                        fpsResponse.setFPID(FPID);
                        fpsResponse.setPaymentId(uuid);
                        fpsResponse.setOrgnlPaymentDocument((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        fpsResponse.setTxSts(REJECT_CODE);
                        fpsResponse.setStsRsn(NO_VALIDATION_CODE);


                        LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment Reject response", uuid);
                        // Send avro message to Kafka
                        Event event = EventGenerator.generateEvent(
                                this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(),
                                uuid,
                                gson.toJson(fpsResponse),
                                entity,
                                brand
                        );

                        String paymentMessage = gson.toJson(fpsResponse.getOrgnlPaymentDocument());
                        selectMessageDestination(outboundErrorTopic, paymentMessage, uuid, FPID, event);

                        LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment Reject response", uuid);
                    }

                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                }
            }catch (ConversionException convEx){
                LOG.error("[FPS][PaymentType: {}]Error generating Avro file. Error: {} Message: {}", 
                        paymentType, convEx.getMessage(), message);
            }catch(IOException e) {
                LOG.error("[FPS][PaymentType: {}] IO Error {}", 
                        paymentType, e.getMessage());
            }catch(MessageConversionException conversionEx){
                LOG.error("[FPS][PaymentType: {}] Error transforming message {}", 
                        paymentType, conversionEx.getMessage());
            } catch (Exception ex) {
                LOG.error("[FPS][PaymentType: {}] Error getting unique ID", 
                        paymentType,  ex.getMessage());
            }
        }
    }

    private void selectMessageDestination(String topic, String message, String uuid, String FPID, Event event) {
        PaymentBean resendPreviousResponse = checkPreviousResponse(message, uuid, FPID);

        if(resendPreviousResponse == null) {
            sendToKafka(topic, uuid, event);
        }else{
            //TODO send previous response to resp queue
        }
    }

    private PaymentBean checkPreviousResponse(String message, String uuid, String FPID) {
        PaymentBean resendPreviousResponse = null;
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance();
        PaymentBean payment = storage.findPayment(FPID, message);
        if (payment != null && payment.getStatus().equals(PaymentStatus.PROCESSED)){
            LOG.info("[FPS][PmtId: {}] Payment previously processed, FPID: {}. Sending previous FPS Inbound payment response", uuid, FPID);
            resendPreviousResponse = payment;
        }else{
            storage.storePayment(FPID, message, uuid);
        }
        return resendPreviousResponse;
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

    protected abstract void sendToKafka(String topic, String uuid, Event event);

}
