package com.orwellg.hermod.bottomline.fps.listeners.outbound;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.listeners.BaseListener;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryOutboundPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentOutboundBean;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.Constants;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.hermod.bottomline.fps.utils.singletons.IDGeneratorBean;
import com.orwellg.hermod.bottomline.fps.utils.singletons.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Async;
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
import java.util.Date;
import java.util.SortedMap;

import static com.codahale.metrics.MetricRegistry.name;
import static com.orwellg.hermod.bottomline.fps.utils.Constants.RESP_SUFFIX;

public abstract class MQOutboundListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQOutboundListener.class);

    @Autowired
    private Gson gson;

    @Autowired
    protected MetricRegistry metricRegistry;

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


    @Value("${kafka.topic.fps.outbound.logging}")
    private String loggingTopic;

    @Value("${connector.%id.mq_primary}")
    private String environmentMQ;

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @Autowired
    private TaskExecutor taskOutboundResponseExecutor;


    protected void onMessage(Message message, String paymentType) {
        LOG.debug("[FPS][PaymentType: {}] Receiving outbound payment response message from Bottomline", paymentType);
        InputStream stream = null;
        Reader reader = null;
        Writer writer = new StringWriter();

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
            IOUtils.copy(reader, writer);
            taskOutboundResponseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    sendMessageToTopic(writer, paymentType, null);
                }
            });
            LOG.debug("[FPS][PaymentType: {}] End processing outbound payment response", paymentType);
        } catch (Exception e) {
            throw new MessageConversionException("Exception in message reception. Message: " + e.getMessage(), e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                writer.close();
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                LOG.error("[FPS][PaymentType: {}] Error closing streams resources. Message: {}",
                        paymentType, e.getMessage());
            }
        }
    }

    @Async("taskOutboundResponseExecutor")
    public void sendMessageToTopic(Writer writer, String paymentType, String id) {
        Event event = null;
        String uuid = id;
        if (writer != null) {
            String message = "";
            try {
                long startTime = new Date().getTime();
                LOG.info("[FPS] Transform MQ message to raw message");
                message = writer.toString();

                if(emergencyLog){
                    LOG.debug("[FPS][PaymentType: {}] Payload received {}",paymentType, message);
                }


                boolean schemaValidation = true;
                try {
                    Source src = new StreamSource(new StringReader(message));
                    // Validate against scheme
                    Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs002();
                    long timeStart = new Date().getTime();
                    synchronized (validator) {
                        validator.validate(src);
                    }
                    LOG.debug("[FPS] Validate against scheme last {} ms", new Date().getTime()-timeStart);
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
                    //boolean isValid = validMessage((FPSAvroMessage) avroFpsMessage);

                    Document paymentDocument = ((Document) ((FPSAvroMessage) avroFpsMessage).getMessage());

                    paymentType = getPaymentType(paymentDocument);

                    if (schemaValidation) {
                        // Send avro message to Kafka
                        FPSInboundPaymentResponse fpsResponse = new FPSInboundPaymentResponse();
                        fpsResponse.setStsDocument(paymentDocument);
                        fpsResponse.setStsRsn(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
                        fpsResponse.setTxSts(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());


                        calculateMetricResponses(fpsResponse, paymentType);


                        if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt() != null) {
                            if (paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue() != null && paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue().getValue() != null) {
                                fpsResponse.setIntrBkSttlmAmt(DecimalTypeUtils.toDecimal(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getValue().getValue()));
                            }
                            fpsResponse.setIntrBkSttlmAmtCcy(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getCcy());
                        }

                        InMemoryOutboundPaymentStorage storage = InMemoryOutboundPaymentStorage.getInstance(expiringMinutes);
                        PaymentOutboundBean paymentBean = storage.findPayment(paymentDocument.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId());
                        if (paymentBean != null) {
                            FPSOutboundPayment originalMessage = paymentBean.getOutboundPayment();
                            uuid = getResponsePaymentId(originalMessage);
                            event = getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_OUTBOUND_RESPONSE.getEventName());

                            kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid);

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
                            uuid = fpsResponse.getOrgnlPaymentId() + RESP_SUFFIX;
                            event = getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_OUTBOUND_RESPONSE.getEventName());

                            kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid);

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
                        event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_RESPONSE_RECEIVED.getEventName(), fpsResponse.getOrgnlPaymentId(), gson.toJson(fpsResponse), entity, brand);

                        sendToKafka(inboundTopic, uuid, event, paymentType, environmentMQ);

                        LOG.info("[FPS][PmtId: {}] Sent FPS Outbound payment response", uuid);
                    }

                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                }
                LOG.debug("[FPS][PmtId: {}] Time to process outbound payment response: {} ms",
                        uuid, new Date().getTime()-startTime);
            } catch (ConversionException convEx) {
                LOG.error("[FPS][PaymentType: {}]Error generating Avro file. Error: {} Message: {}", paymentType, convEx.getMessage(), message);
            } catch (MessageConversionException conversionEx) {
                LOG.error("[FPS][PaymentType: {}] Error transforming message {}", paymentType, conversionEx.getMessage());
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
            }else{
                paymentType = prtry;
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

    protected abstract void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ);

    private void calculateMetricResponses(FPSInboundPaymentResponse fpsResponse, String paymentType) {
        String txSts = fpsResponse.getTxSts();
        SortedMap<String, Counter> counters = metricRegistry.getCounters();
        if(StringUtils.isNotEmpty(txSts)){
            Counter counter = null;
            String stsRsn = fpsResponse.getStsRsn();
            if(StringUtils.isNotEmpty(stsRsn)) {
                String key = "connector_fps.outbound."+paymentType+"." + FPSDirection.INPUT.getDirection() + "." + txSts + "." + stsRsn;
                if (counters.containsKey(key)) {
                    counter = counters.get(key);

                } else {
                    counter = metricRegistry.counter(name("connector_fps", "outbound", paymentType, FPSDirection.INPUT.getDirection(), txSts, stsRsn));
                }
            }else{
                String key = "connector_fps.outbound."+paymentType+"." + FPSDirection.INPUT.getDirection() + "." + txSts;
                if (counters.containsKey(key)) {
                    counter = counters.get(key);

                } else {
                    counter = metricRegistry.counter(name("connector_fps", "outbound", paymentType, FPSDirection.INPUT.getDirection(), txSts));
                }
            }
            counter.inc();
        }else{

            calculateMetrics(counters, paymentType);
        }
    }


    protected void calculateMetrics(SortedMap<String, Counter> counters, String paymentType) {
        String key = "connector_fps.outbound."+paymentType+"." + FPSDirection.INPUT.getDirection();
        Counter counter = null;
        if (counters.containsKey(key)) {
            counter = counters.get(key);

        } else {
            counter = metricRegistry.counter(name("connector_fps", "outbound", paymentType, FPSDirection.INPUT.getDirection()));
        }
        counter.inc();

    }

}
