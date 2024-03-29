package com.orwellg.hermod.bottomline.fps.listeners.outbound;


import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryOutboundPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentOutboundBean;
import com.orwellg.hermod.bottomline.fps.storage.PaymentStatus;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.Constants;
import com.orwellg.hermod.bottomline.fps.utils.QoSHeaders;
import com.orwellg.hermod.bottomline.fps.utils.QoSValidationExceotion;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.hermod.bottomline.fps.utils.singletons.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSValidationErrorCodes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.codahale.metrics.MetricRegistry.name;


@Component(value = "kafkaRequestOutboundListener")
public class KafkaRequestOutboundListener extends KafkaOutboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

    private static Logger LOG = LogManager.getLogger(KafkaRequestOutboundListener.class);


    @Value("${wq.mq.queue.sip.outbound}")
    private String outboundQueue;

    @Value("${wq.mq.queue.asyn.outbound}")
    private String outboundAsyncQueue;

    @Value("${kafka.topic.outbound.response}")
    private String outboundResponseTopic;

    @Value("${kafka.topic.outbound.request}")
    private String outboundRequestTopic;

    @Value("${kafka.topic.fps.outbound.logging}")
    private String loggingTopic;

    @Value("${kafka.topic.fps.outbound.undopayment}")
    private String undoPaymentTopic;


    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @Value("{entity.name}")
    private String entity;
    @Value("${brand.name}")
    private String brand;

    @Value("${connector.%id.mq_primary}")
    private String environmentMQ;

    @Value("${jms.mq.bottomline.environment.1}")
    private String environmentMQSite1;

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQSite2;

    @Value("${connector.payments.sent.roundrobin}")
    private Boolean roundRobinSent;

    private final KafkaSender kafkaSender;

    private final MetricRegistry metricRegistry;

    private final TaskExecutor taskOutboundRequestExecutor;

    private static AtomicLong index;
    static {
        if (index == null){
            index = new AtomicLong(0);
        }
    }
    private String getEnvironment() {
        String environment = environmentMQ;

        if(roundRobinSent) {

            long i = index.incrementAndGet();
            if (i % 2 == 0) {
                environment = environmentMQSite2;
            } else {
                environment = environmentMQSite1;
            }
        }
        return environment;
    }

    @Autowired
    public KafkaRequestOutboundListener(MetricRegistry metricRegistry, KafkaSender kafkaSender, TaskExecutor taskOutboundRequestExecutor){
        this.metricRegistry = metricRegistry;
        this.kafkaSender = kafkaSender;
        this.taskOutboundRequestExecutor = taskOutboundRequestExecutor;
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> message) {

        try {

            String key = message.key();
            String value = message.value();
            Headers headers = message.headers();
            LOG.debug("[FPS][PmtId: {}] Processing event request for FPS outbound payment", key);
            taskOutboundRequestExecutor.execute(new Runnable(){
                @Override
                public void run() {
                    processOutboundPayment(key, value, headers);
                }
            } );
            LOG.debug("[FPS][PmtId: {}] End processing event request for FPS outbound payment", key);
        } catch (Exception e) {
            throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
        }
    }



    @Async("taskOutboundRequestExecutor")
    public void processOutboundPayment(String key, String value, Headers headers) {
        QoSHeaders qoSHeaders = getQoSData(headers);
        long startTime = new Date().getTime();
        Event event = null;
        Gson gson = new Gson();
        // Parse Event Message
        Event eventPayment = null;
        try {
            eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
        } catch (Exception ex) {
            LOG.error("[FPS][PmtId: {}] Error decoding event request for FPS outbound payment. Error Message: {}", key, ex.getMessage(), ex);
        }

        // Parse FPS Outbound Payment Request
        LOG.info("[FPS][PmtId: {}] parsing request for FPS outbound payment", key);
        FPSOutboundPayment fpsOutboundPayment = null;
        try {
            fpsOutboundPayment = gson.fromJson(eventPayment.getEvent().getData(), FPSOutboundPayment.class);
        } catch (Exception ex) {
            LOG.error("[FPS][PmtId: {}] Error parsing request for FPS outbound payment. Error Message: {}", key, ex.getMessage(), ex);
        }
        LOG.info("[FPS][PmtId: {}]  Request parsed for FPS outbound payment. Request message: {}", key, fpsOutboundPayment.toString());

        Document fpsDocument = fpsOutboundPayment.getPaymentDocument();
        String paymentType = fpsOutboundPayment.getPaymentType();
        String paymentId = fpsOutboundPayment.getPaymentId();
        calculateMetrics(paymentType);

        try {
            // Call the correspondent transform
            // Generate Request Reject
            FPSAvroMessage avroMessage = new FPSAvroMessage();
            avroMessage.setMessage(fpsDocument);

            FPSTransform transform = transforms.get("transform_pacs_008_001");

            if (transform != null) {
                LOG.info("[FPS][PmtId: {}]  Transform FPS outbound payment from avro file.", key);
                FPSMessage fpsMessage = transform.avro2fps(avroMessage);
                //boolean isValid = validMessage(fpsDocument);

                StringWriter rawMessage = transformRequestToString(fpsMessage);

                LOG.info("[FPS][PmtId: {}] XML Request generated for FPS outbound payment. Request: {}", paymentId, rawMessage.toString());
                event = EventGenerator.generateEvent(
                        this.getClass().getName(),
                        FPSEvents.FPS_HERMOD_BL_OUTBOUND_SENT.getEventName(),
                        key,
                        rawMessage.toString(),
                        entity,
                        brand
                );
                kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), key);

                boolean schemaValidation = true;
                // Validate against scheme
                try {
                    Source src = new StreamSource(new StringReader(rawMessage.toString()));
                    Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs008();
                    long timeStart = new Date().getTime();
                    synchronized (validator) {
                        validator.validate(src);
                    }
                    LOG.debug("[FPS] Validate against scheme last {} ms", new Date().getTime()-timeStart);
                } catch (SAXException ex) {
                    schemaValidation = false;
                    LOG.error("[FPS][PaymentType: {}] Error Validating message against scheme. Error:{} Message: {}",
                            paymentType, ex.getMessage(), rawMessage);
                } catch (IOException e) {
                    schemaValidation = false;
                    LOG.error("[FPS][PaymentType: {}] I/O Error. Error:{} Message: {}", paymentType, e.getMessage(),
                            rawMessage);
                }

                if (schemaValidation) {

                    //Send to MQ (Environment=Queue)
                    String queueToSend = outboundAsyncQueue;

                    if (paymentType.equalsIgnoreCase("SIP")) {
                        queueToSend = outboundQueue;
                    }

                    String datacenter = getEnvironment();
                    boolean paymentSent =  false;

                    try {
                        paymentSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, datacenter, qoSHeaders);
                        if (!paymentSent) {
                            String alternativeEnvironmentMQ = environmentMQSite1;
                            if (datacenter.equalsIgnoreCase(environmentMQSite1)) {
                                alternativeEnvironmentMQ = environmentMQSite2;
                            }
                            paymentSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, alternativeEnvironmentMQ, qoSHeaders);
                        }

                        if(paymentSent) {

                            fpsOutboundPayment.setTxSts("SENT");

                            String eventName = FPSEvents.FPS_PAYMENT_SENT.getEventName();
                            if (eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_SEND_RETURN.getEventName())) {
                                eventName = FPSEvents.FPS_RETURN_SENT.getEventName();
                            }
                            event = EventGenerator.generateEvent(this.getClass().getName(), eventName, paymentId,
                                    gson.toJson(fpsOutboundPayment), entity, brand);

                            LOG.info("[FPS][PmtId: {}] Sending Message to Topic {}", key, outboundResponseTopic);
                            sendToKafka(outboundResponseTopic, key, event);

                            storeOutboundPayment(paymentId, fpsOutboundPayment);

                            LOG.info("[FPS][PmtId: {}] Finish sending FPS Outbound payment", paymentId);
                        }else{ // Payment can not be sent to Bottomline
                            LOG.info("[FPS][PmtId: {}] Generating reject because of connection error to Gateway for FPS Outbound payment",
                                    paymentId);
                            rejectPayment(key, fpsOutboundPayment, paymentId, FPSValidationErrorCodes.ERROR_GATEWAY_CONNECTION,
                                    FPSEvents.FPS_HERMOD_BL_CONNECTION_FAILED);
                            LOG.error("[FPS][PmtId: {}] Finish connection error to Gateway sending FPS Outbound payment request. Message {}",
                                    paymentId,
                                    rawMessage);
                        }

                    }catch (QoSValidationExceotion e){
                        // Error because of QoS
                        LOG.info("[FPS][PmtId: {}] Generating reject because of invalid QoS for FPS Outbound payment",
                                paymentId);
                        rejectPayment(key, fpsOutboundPayment, paymentId, FPSValidationErrorCodes.ERROR_QOS_VALIDATION,
                                FPSEvents.FPS_VALIDATION_ERROR);
                        LOG.error("[FPS][PmtId: {}] Finish validating QoS for FPS Outbound payment request. Message {}",
                                paymentId,
                                rawMessage);
                    }
                } else {
                    // Error validating payment with scheme
                    LOG.info("[FPS][PmtId: {}] Generating reject because of invalid FPS Outbound payment", paymentId);
                    rejectPayment(key, fpsOutboundPayment, paymentId, FPSValidationErrorCodes.ERROR_SCHEME_VALIDATION,
                            FPSEvents.FPS_VALIDATION_ERROR);
                    LOG.error("[FPS][PmtId: {}] Finish validating error on FPS Outbound payment request. Message {}",
                            paymentId,
                            rawMessage);
                }
            } else {
                throw new MessageConversionException("Exception in message emission. The transform for pacs_008_001 is null");
            }
        } catch (Exception ex) {
            LOG.error("[FPS][PmtId: {}] Error generating request for FPS outbound payment. Error Message: {}",
                    paymentId, ex.getMessage(), ex);
        }

        LOG.debug("[FPS][PmtId: {}] Time to process outbound payment request: {} ms",
                paymentId, new Date().getTime()-startTime);
    }

    private void rejectPayment(String key, FPSOutboundPayment fpsOutboundPayment, String paymentId,
                               FPSValidationErrorCodes fpsValidationErrorCodes, FPSEvents fpsEvents) {
        Event event;

        Gson gson = new Gson();

        //Undo payment

        event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_UNDO_PAYMENT.getEventName(), paymentId,
                paymentId, entity, brand);
        String topic = undoPaymentTopic;

        LOG.info("[FPS][PmtId: {}] Sending Message to Topic {} to undo payment", key, topic);
        sendToKafka(topic, key, event);

        // Reject Response to outbound response topic

        topic = outboundResponseTopic;
        fpsOutboundPayment.setTxSts(Constants.REJECT_CODE);
        fpsOutboundPayment.setStsRsn(fpsValidationErrorCodes.getError().toString());
        fpsOutboundPayment.setPaymentTimestamp(new Date().getTime());
        LOG.info("[FPS][PmtId: {}] Generating reject payment", paymentId);
        event = EventGenerator.generateEvent(this.getClass().getName(), fpsEvents.getEventName(), paymentId,
                gson.toJson(fpsOutboundPayment), entity, brand);
        LOG.info("[FPS][PmtId: {}] Sending Message to Topic {} to reject payment", key, outboundResponseTopic);
        sendToKafka(topic, key, event);
    }

    private Boolean checkQoS(Headers headers) {
        Long currentTimestamp = new Date().getTime();

        Boolean validQoS = Boolean.TRUE;

        QoSHeaders qoSHeaders = getQoSData(headers);

        Integer qosSLA = qoSHeaders.getQosSLA();
        Long qosTimestamp = qoSHeaders.getQosTimestamp();
        if(qosSLA != null && qosTimestamp != null) {
            LOG.info("[FPS] Checking QoS service, sla ={}, timestamp={}, current timestamp={}", qosSLA, qosTimestamp, currentTimestamp);
            if ((qosTimestamp + qosSLA) <= currentTimestamp) {
                validQoS = Boolean.FALSE;
            }
        }
        return validQoS;
    }

    private QoSHeaders getQoSData(Headers headers) {
        QoSHeaders qoSHeaders = new QoSHeaders();
        Header[] headersList = headers.toArray();
        for (Header header: headersList){
            if(header.value() != null){
                try{
                    if(header.key().equalsIgnoreCase(KafkaHeaders.QOS_TIMESTAMP.getKafkaHeader())){
                        LOG.info("[FPS] QoS Timestamp {}", header.value());
                        qoSHeaders.setQosTimestamp(Long.parseLong(new String(header.value())));

                    }else if(header.key().equalsIgnoreCase(KafkaHeaders.QOS_SLA.getKafkaHeader())){
                        LOG.info("[FPS] qosSLA {}", header.value());
                        qoSHeaders.setQosSLA(Integer.parseInt(new String(header.value())));
                    }
                }catch(NumberFormatException ex){
                    LOG.warn("[FPS} Header {} value {}",header.key(), header.value());
                }
            }
        }

        return qoSHeaders;
    }

    private PaymentOutboundBean storeOutboundPayment(String paymentId, FPSOutboundPayment outboundPayment) {
        PaymentOutboundBean resendPreviousResponse = null;
        InMemoryOutboundPaymentStorage storage = InMemoryOutboundPaymentStorage.getInstance(expiringMinutes);
        PaymentOutboundBean payment = storage.findPayment(paymentId);
        if (payment != null && payment.getStatus().equals(PaymentStatus.PROCESSED)) {
            resendPreviousResponse = payment;
        } else {
            resendPreviousResponse = storage.storePayment(outboundPayment, paymentId);
        }
        return resendPreviousResponse;
    }

    private boolean validMessage(Document fpsDocument) {
        return true;
    }


    private String getDbtrAccountId(Document fpsDocument) {
        String accountId = "";
        if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId() != null) {
            if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getIBAN() != null &&
                    !fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getIBAN().isEmpty()) {
                accountId = fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getIBAN();
            } else if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr() != null &&
                    !fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId().isEmpty()) {
                accountId = fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId();

            }
        }

        return accountId;
    }

    private String getCdtrAccountId(Document fpsDocument) {
        String accountId = "";
        if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct() != null &&
                fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId() != null) {
            if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getIBAN() != null &&
                    !fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getIBAN().isEmpty()) {
                accountId = fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getIBAN();
            } else if (fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr() != null &&
                    !fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId().isEmpty()) {
                accountId = fpsDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();

            }
        }
        return accountId;
    }

    protected void sendToKafka(String topic, String uuid, Event event) {
        kafkaSender.sendRawMessage(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid
        );
    }

    private void calculateMetrics(String paymentType) {
        String direction = FPSDirection.OUTPUT.getDirection();

        SortedMap <String, Counter> counters = metricRegistry.getCounters();

        Counter metric = counters.get(name("connector_fps", "outbound", paymentType, direction));
        if(metric == null) {
            metric = metricRegistry.counter(name("connector_fps", "outbound", paymentType, direction));
        }
        metric.inc();
    }

}
