package com.hermod.bottomline.fps.listeners.outbound;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.storage.InMemoryOutboundPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.storage.PaymentOutboundBean;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.EventGenerator;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

public abstract class MQOutboundListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQOutboundListener.class);
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

    @Value("${kafka.topic.inbound.response.payment}")
    private String inboundTopic;


    @Value("${kafka.topic.fps.logging}")
    private String loggingTopic;

    protected void onMessage(Message message, String paymentType) {

        LOG.info("[FPS][PaymentType: {}] Getting outbound payment response message...............", paymentType);
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
        this.sendMessageToTopic(reader, paymentType, null);
    }

    public void sendMessageToTopic(Reader reader, String paymentType, String id) {
        boolean schemaValidation = true;
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (reader != null) {
            Resource xsdResource = new ClassPathResource("./xsd/pacs.002.001.06.xsd");
            String message = "";
            try {
                String uuid = StringUtils.isNotEmpty(id)?id:idGenerator.generatorID().getGeneralUniqueId();
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

                    //String FPID = extractFPID((FPSAvroMessage) avroFpsMessage);
                    //String paymentTypeCode = extractPaymentTypeCode((FPSAvroMessage) avroFpsMessage);

                    Document paymentDocument = ((Document) ((FPSAvroMessage) avroFpsMessage).getMessage());

                    if (schemaValidation && isValid) {
                        // Send avro message to Kafka
                        FPSInboundPaymentResponse fpsResponse = new FPSInboundPaymentResponse ();
                        fpsResponse.setStsDocument(paymentDocument);
                        fpsResponse.setStsRsn(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
                        fpsResponse.setTxSts(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
                        fpsResponse.setPaymentId(uuid);

                        InMemoryOutboundPaymentStorage storage = InMemoryOutboundPaymentStorage.getInstance();
                        PaymentOutboundBean paymentBean = storage.findPayment(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId());
                        if(paymentBean != null ) {
                            FPSOutboundPayment originalMessage = paymentBean.getOutboundPayment();

                            fpsResponse.setOrgnlFPID(originalMessage.getFPID());
                            fpsResponse.setOrgnlPaymentId(originalMessage.getPaymentId());
                            fpsResponse.setOrgnlPaymentType(originalMessage.getPaymentType());
                            fpsResponse.setReturnCode(originalMessage.getReturnCode());
                            fpsResponse.setReturnedPaymentId(originalMessage.getReturnedPaymentId());
                            fpsResponse.setCdtrAccountId(originalMessage.getCdtrAccountId());
                            fpsResponse.setDbtrAccountId(originalMessage.getDbtrAccountId());
                        }

                        LOG.info("[FPS][PmtId: {}] Sending FPS Outbound payment response", uuid);

                        Event event = EventGenerator.generateEvent(
                                this.getClass().getName(),
                                FPSEvents.FPS_RESPONSE_RECEIVED.getEventName(),
                                uuid,
                                gson.toJson(fpsResponse),
                                entity,
                                brand
                        );

                        sendToKafka(inboundTopic, uuid, event);

                        LOG.info("[FPS][PmtId: {}] Sent FPS Outbound payment response", uuid);
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
                LOG.error("[FPS][PaymentType: {}] Error getting response from payment. Message error {}",
                        paymentType,  ex.getMessage(), ex);
            }
        }
    }

    private String getCdtrAccountId(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document paymentDocument) {
        String cdtrAccountId = null;
        if (paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct() != null){
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id =
                    paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId();
            if(id.getIBAN() != null ){
                cdtrAccountId = id.getIBAN();
            }else{
                if(id.getOthr() != null)
                cdtrAccountId  = id.getOthr().getId();
            }

        }
        return cdtrAccountId;
    }

    private String getDbtrAccountId(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document paymentDocument) {
        String cdtrAccountId = null;
        if (paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct() != null){
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id =
                    paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId();
            if(id.getIBAN() != null ){
                cdtrAccountId = id.getIBAN();
            }else{
                if(id.getOthr() != null)
                    cdtrAccountId  = id.getOthr().getId();
            }

        }
        return cdtrAccountId;
    }


    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }

    protected abstract void sendToKafka(String topic, String uuid, Event event);

}
