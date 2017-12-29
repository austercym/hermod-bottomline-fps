package com.hermod.bottomline.fps.listeners.outbound;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.storage.InMemoryOutboundPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentOutboundBean;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.EventGenerator;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.hermod.bottomline.fps.utils.generators.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXException;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import java.io.*;

public abstract class MQOutboundListener extends BaseListener implements MessageListener {

    public static final String RESP_SUFFIX = "_RESP";
    private static Logger LOG = LogManager.getLogger(MQOutboundListener.class);

    @Autowired
    private Gson gson;

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

        LOG.info("[FPS][PaymentType: {}] Receiving outbound payment response message from Bottomline", paymentType);
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
        if (reader != null) {
            String message = "";
            try {
                String uuid = StringUtils.isNotEmpty(id) ? id : IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
                try {
                    LOG.info("[FPS] Transform MQ message to raw message");
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(reader, writer);
                    message = writer.toString();

                    boolean schemaValidation = true;
                    try {
                        Source src = new StreamSource(new StringReader(message));
                        // Validate against scheme
                        Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs002();
                        validator.validate(src);
                    } catch (SAXException ex) {
                        schemaValidation = false;
                        LOG.error("[FPS][PaymentType: {}] Error Validating message against scheme. Error:{} Message: {}", paymentType, ex.getMessage(), message);
                    } catch (IOException e) {
                        schemaValidation = false;
                        LOG.error("[FPS][PaymentType: {}] I/O Error. Error:{} Message: {}", paymentType, e.getMessage(), message);
                    }
                    // Getting Avro
                    Source src = new StreamSource(new StringReader(message));
                    final JAXBElement result = (JAXBElement) marshaller.unmarshal(src);
                    FPSMessage fpsMessage = (FPSMessage) result.getValue();

                    // Call the correspondent transform
                    FPSTransform transform = getTransform(fpsMessage.getClass().getPackage().getName());
                    if (transform != null) {
                        Object avroFpsMessage = transform.fps2avro(fpsMessage);
                        boolean isValid = validMessage((FPSAvroMessage) avroFpsMessage);

                        Document paymentDocument = ((Document) ((FPSAvroMessage) avroFpsMessage).getMessage());

                        paymentType = getPaymentType(paymentDocument);

                        if (schemaValidation && isValid) {
                            // Send avro message to Kafka
                            FPSInboundPaymentResponse fpsResponse = new FPSInboundPaymentResponse();
                            fpsResponse.setStsDocument(paymentDocument);
                            fpsResponse.setStsRsn(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
                            fpsResponse.setTxSts(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
                            if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt() != null) {
                                if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue().getValue() != null) {
                                    fpsResponse.setIntrBkSttlmAmt(DecimalTypeUtils.toDecimal(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue().getValue()));
                                }
                                fpsResponse.setIntrBkSttlmAmtCcy(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getCcy());
                            }

                            InMemoryOutboundPaymentStorage storage = InMemoryOutboundPaymentStorage.getInstance();
                            PaymentOutboundBean paymentBean = storage.findPayment(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId());
                            if (paymentBean != null) {
                                FPSOutboundPayment originalMessage = paymentBean.getOutboundPayment();
                                uuid = getResponsePaymentId(originalMessage);
                                //Send mq message to hbase topic
                                kafkaSender.sendRawMessage(loggingTopic, message, uuid);

                                fpsResponse.setPaymentId(uuid);
                                fpsResponse.setOrgnlFPID(originalMessage.getFPID());
                                fpsResponse.setOrgnlPaymentId(originalMessage.getPaymentId());
                                fpsResponse.setOrgnlPaymentType(originalMessage.getPaymentType());
                                fpsResponse.setReturnCode(originalMessage.getReturnCode());
                                fpsResponse.setReturnedPaymentId(originalMessage.getReturnedPaymentId());
                                fpsResponse.setCdtrAccountId(originalMessage.getCdtrAccountId());
                                fpsResponse.setDbtrAccountId(originalMessage.getDbtrAccountId());
                            } else {
                                fpsResponse.setOrgnlPaymentId(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId());
                                if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry().indexOf('/') > 0) {
                                    fpsResponse.setOrgnlPaymentType(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry().substring(0, paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry().indexOf('/')));
                                } else {
                                    fpsResponse.setOrgnlPaymentType(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry());
                                }
                                if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf().getStrd() != null && !paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf().getStrd().isEmpty()) {
                                    if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf() != null && !paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().isEmpty()) {
                                        fpsResponse.setOrgnlFPID(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().get(0));
                                    }
                                }

                                if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct() != null) {
                                    if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getIBAN() != null) {
                                        fpsResponse.setCdtrAccountId(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getIBAN());
                                    }
                                    if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getOthr() != null) {
                                        fpsResponse.setCdtrAccountId(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
                                    }
                                }

                            }

                            LOG.info("[FPS][PmtId: {}] Sending FPS Outbound payment response", uuid);
                            Event event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_RESPONSE_RECEIVED.getEventName(), fpsResponse.getOrgnlPaymentId(), gson.toJson(fpsResponse), entity, brand);

                            sendToKafka(inboundTopic, uuid, event, paymentType);

                            LOG.info("[FPS][PmtId: {}] Sent FPS Outbound payment response", uuid);
                        }

                    } else {
                        throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                    }
                } catch (ConversionException convEx) {
                    kafkaSender.sendRawMessage(loggingTopic, message, uuid);
                    LOG.error("[FPS][PaymentType: {}]Error generating Avro file. Error: {} Message: {}", paymentType, convEx.getMessage(), message);
                } catch (IOException e) {
                    kafkaSender.sendRawMessage(loggingTopic, message, uuid);
                    LOG.error("[FPS][PaymentType: {}] IO Error {}", paymentType, e.getMessage());
                } catch (MessageConversionException conversionEx) {
                    kafkaSender.sendRawMessage(loggingTopic, message, uuid);
                    LOG.error("[FPS][PaymentType: {}] Error transforming message {}", paymentType, conversionEx.getMessage());
                }
            }catch (Exception ex) {
                LOG.error("[FPS][PaymentType: {}] Error getting response from payment. Message error {}", paymentType, ex.getMessage(), ex);
            }
        }
    }

    private String getPaymentType(Document paymentDocument) {
        String paymentType = "";
        if(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts() != null &&
                !paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().isEmpty() &&
                paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0) != null) {
            String prtry = paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry();
            LOG.info("[FPS] Payment Type {}", prtry);
            if(prtry.indexOf('/')> 0) {
                paymentType = prtry.substring(0, prtry.indexOf('/'));
            }
        }
        return paymentType;
    }

    private String getResponsePaymentId(FPSOutboundPayment originalMessage) {

        return originalMessage.getPaymentId()+ RESP_SUFFIX;
    }

    private String getCdtrAccountId(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document paymentDocument) {
        String cdtrAccountId = null;
        if (paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct() != null) {
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id =
                    paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId();
            if (id.getIBAN() != null) {
                cdtrAccountId = id.getIBAN();
            } else {
                if (id.getOthr() != null)
                    cdtrAccountId = id.getOthr().getId();
            }

        }
        return cdtrAccountId;
    }

    private String getDbtrAccountId(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document paymentDocument) {
        String cdtrAccountId = null;
        if (paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct() != null) {
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id =
                    paymentDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId();
            if (id.getIBAN() != null) {
                cdtrAccountId = id.getIBAN();
            } else {
                if (id.getOthr() != null)
                    cdtrAccountId = id.getOthr().getId();
            }

        }
        return cdtrAccountId;
    }


    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }

    protected abstract void sendToKafka(String topic, String uuid, Event event, String paymentType);

}
