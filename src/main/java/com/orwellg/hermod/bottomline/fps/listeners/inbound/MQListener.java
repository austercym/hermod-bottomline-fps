package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.codahale.metrics.Counter;
import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.listeners.BaseListener;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentBean;
import com.orwellg.hermod.bottomline.fps.storage.PaymentStatus;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.Constants;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.hermod.bottomline.fps.utils.singletons.IDGeneratorBean;
import com.orwellg.hermod.bottomline.fps.utils.singletons.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.*;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
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

public abstract class MQListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQListener.class);

    //protected String environmentMQ;

    protected Counter inbound_sop_requests;
    protected Counter inbound_fdp_requests;
    protected Counter inbound_cbp_requests;
    protected Counter inbound_srn_requests;
    protected Counter inbound_rtn_requests;
    protected Counter inbound_sip_requests;
    protected Counter inbound_poo_requests;
    protected Counter inbound_standin_requests;

    @Autowired
    private Gson gson;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    protected KafkaSender kafkaSender;

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;


    @Value("${kafka.topic.inbound.request}")
    protected String inboundTopic;

    @Value("${kafka.topic.reversal.request}")
    protected String inboundReversalTopic;

    @Value("${kafka.topic.reversal.response}")
    protected String inboundReversalResponseTopic;

    @Value("${kafka.topic.inbound.response}")
    private String outboundResponseTopic;

    @Value("${kafka.topic.fps.inbound.logging}")
    private String loggingTopic;

    @Value("${kafka.topic.inbound.response.replyTo}")
    protected String replyTo;

    @Value("${kafka.topic.cache.request}")
    protected String inMemoryRequestTopic;

    @Value("${wq.mq.queue.sip.inbound.resp}")
    private String outboundQueue;
    @Value("${wq.mq.queue.asyn.inbound.resp}")
    private String outboundAsynQueue;


    @Value("${jms.mq.bottomline.environment.1}")
    private String environmentMQSite1;

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQSite2;

    @Autowired
    private TaskExecutor taskInboundRequestExecutor;



    protected void onMessage(Message message, String paymentType) {

        LOG.debug("[FPS][PaymentType: {}] Receiving inbound payment request message from Bottomline", paymentType);
        InputStream stream = null;
        Reader reader = null;
        Writer writer = new StringWriter();

        Long qosTimestamp = new Date().getTime();

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
            taskInboundRequestExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    sendMessageToTopic(writer, paymentType, null, qosTimestamp);
                }
            });
            LOG.debug("[FPS][PaymentType: {}] End processing inbound payment request", paymentType);
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

    @Async("taskInboundRequestExecutor")
    public void sendMessageToTopic(Writer writer, String paymentType, String id, Long qosMilliseconds) {
        boolean schemaValidation = true;
        Event event =  null;
        if (writer != null) {
            String message = "";
            try {
                long startTime = new Date().getTime();
                message = writer.toString();

                if(emergencyLog){
                    LOG.debug("[FPS][PaymentType: {}] Payload received {}",paymentType, message);
                }

                String uuid = StringUtils.isNotEmpty(id)?id:IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
                //Send mq message to logging topic
                event = getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_INBOUND_RECEIVED.getEventName());

                kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid);

                String errorMessage = "";
                boolean isReversal = false;
                if(message!=null && message.contains("pacs.008")) {
                    try {
                        // Validate against scheme
                        Source src = new StreamSource(new StringReader(message));
                        Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs008();
                        long timeStart = new Date().getTime();
                        synchronized (validator) {
                            validator.validate(src);
                        }
                        LOG.debug("[FPS] Validate against scheme last {} ms", new Date().getTime()-timeStart);
                    } catch (SAXException ex) {
                        schemaValidation = false;
                        errorMessage = ex.getMessage();

                    } catch (IOException e) {
                        schemaValidation = false;
                        errorMessage = e.getMessage();
                    }
                }else if(message != null && message.contains("pacs.007")) {
                        try {
                            // Validate against scheme
                            Source src = new StreamSource(new StringReader(message));

                            Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs007();
                            long timeStart = new Date().getTime();
                            synchronized (validator) {
                                validator.validate(src);
                            }
                            LOG.debug("[FPS] Validate against scheme last {} ms", new Date().getTime()-timeStart);
                            schemaValidation = true;
                            isReversal = true;
                        } catch (SAXException ex) {
                            schemaValidation = false;
                            errorMessage = ex.getMessage();
                        } catch (IOException e) {
                            schemaValidation = false;
                            errorMessage = e.getMessage();
                        }
                }

                if(!schemaValidation){
                    LOG.error("[FPS][PaymentType: {}] Error Validating message against scheme. Error:{}", paymentType, errorMessage);
                }

                // Getting Avro
                Source src = new StreamSource(new StringReader(message));
                final JAXBElement result = (JAXBElement) marshaller.unmarshal(src);
                FPSMessage fpsMessage = (FPSMessage) result.getValue();

                // Call the correspondent transform
                FPSTransform transform = getTransform(fpsMessage.getClass().getPackage().getName());
                String nextEnvironment = getEnvironment();
                if (transform != null) {
                    Object avroFpsMessage = transform.fps2avro(fpsMessage);

                    //boolean isValid = validMessage((FPSAvroMessage)avroFpsMessage);

                    String FPID = extractFPID((FPSAvroMessage) avroFpsMessage, isReversal);
                    String paymentTypeCode = extractPaymentTypeCode((FPSAvroMessage) avroFpsMessage, isReversal);

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document paymentDocument = null;
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document paymentreversalDocument = null;
                    String originalPaymentMessage;
                    if(!isReversal){
                        paymentDocument = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        originalPaymentMessage = gson.toJson(paymentDocument);
                    }else{
                        paymentreversalDocument = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document) ((FPSAvroMessage) avroFpsMessage).getMessage());
                        originalPaymentMessage = gson.toJson(paymentreversalDocument);
                    }
                    PaymentBean previousPaymentProcessed = checkPreviousResponse(originalPaymentMessage,uuid, FPID, paymentType, nextEnvironment);

                    if (previousPaymentProcessed != null){

                        LOG.info("[FPS][PmtId: {}] Payment previously processed, FPID: {}. Sending previous FPS Inbound payment response. Message: {}",
                                uuid, FPID, previousPaymentProcessed.getResponseMessage());
                        while(!previousPaymentProcessed.getStatus().getName().equals(PaymentStatus.PROCESSED.getName())){
                            LOG.info("[FPS][PmtId: {}] Waiting for finishing to be processed, FPID: {}", uuid, FPID);
                            Thread.sleep(10);
                            previousPaymentProcessed = checkPreviousResponse(originalPaymentMessage,uuid, FPID, paymentType, nextEnvironment);
                        }
                        String paymentTypeToSend = previousPaymentProcessed.getPaymentType();
                        String queueToSend = outboundAsynQueue;

                        if (paymentTypeToSend.equalsIgnoreCase(SIP)) {
                            queueToSend = outboundQueue;
                        }

                        boolean responseSent = sendToMQ(uuid, previousPaymentProcessed.getResponseMessage(),
                                queueToSend, paymentType, previousPaymentProcessed.getEnvironmentMQ());
                        if(!responseSent){
                            String alternativeEnvironmentMQ = environmentMQSite1;
                            if(previousPaymentProcessed.getEnvironmentMQ().equalsIgnoreCase(environmentMQSite1)){
                                alternativeEnvironmentMQ = environmentMQSite2;
                            }
                            responseSent = sendToMQ(uuid, previousPaymentProcessed.getResponseMessage(), queueToSend, paymentType, alternativeEnvironmentMQ);
                        }


                    }else {
                        if (schemaValidation) {
                            // Send avro message to Kafka
                            if(!isReversal) {
                                FPSInboundPayment fpsRequest = new FPSInboundPayment();
                                fpsRequest.setPaymentDocument(paymentDocument);
                                fpsRequest.setFPID(FPID);
                                fpsRequest.setPaymentId(uuid);
                                fpsRequest.setPaymentType(paymentTypeCode);
                                String eventName = FPSEvents.FPS_PAYMENT_RECEIVED.getEventName();
                                if(paymentTypeCode.equalsIgnoreCase(RTN)){
                                    eventName = FPSEvents.FPS_RETURN_RECEIVED.getEventName();
                                }
                                event = EventGenerator.generateEvent(this.getClass().getName(),
                                        eventName, uuid, gson.toJson(fpsRequest), entity, brand
                                );
                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment request to {}", uuid, nextEnvironment);
                                sendToKafka(inboundTopic, uuid, event, paymentTypeCode, nextEnvironment, qosMilliseconds);
                            }else{
                                FPSInboundReversal fpsInboundReversal = new FPSInboundReversal();
                                fpsInboundReversal.setPaymentId(uuid);
                                fpsInboundReversal.setFPID(FPID);
                                fpsInboundReversal.setRvslDocument(paymentreversalDocument);
                                fpsInboundReversal.setRvsdIntrBkSttlmAmt(paymentreversalDocument.getFIToFIPmtRvsl().getTxInf().get(0).getRvsdIntrBkSttlmAmt().getValue());
                                fpsInboundReversal.setRvsdIntrBkSttlmAmtCcy(paymentreversalDocument.getFIToFIPmtRvsl().getTxInf().get(0).getRvsdIntrBkSttlmAmt().getCcy());
                                fpsInboundReversal.setOrgnlPaymentId(paymentreversalDocument.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxId());
                                fpsInboundReversal.setOrgnlPaymentType(paymentTypeCode);

                                fpsInboundReversal.setPaymentTimestamp(new Date().getTime());
                                event = EventGenerator.generateEvent(
                                        this.getClass().getName(),
                                        FPSEvents.FPS_REVERSAL_RECEIVED.getEventName(),
                                        uuid,
                                        gson.toJson(fpsInboundReversal),
                                        entity,
                                        brand
                                );

                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound reversal request to {}", uuid, nextEnvironment);
                                sendToKafka(inboundReversalTopic, uuid, event, paymentTypeCode, nextEnvironment, qosMilliseconds);


                                event = EventGenerator.generateEvent(
                                        this.getClass().getName(),
                                        FPSEvents.FPS_REVERSAL_RECEIVED.getEventName(),
                                        uuid,
                                        originalPaymentMessage,
                                        entity,
                                        brand
                                );
                                kafkaSender.sendInMemoryMessage(inMemoryRequestTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                                        FPID, uuid, nextEnvironment, paymentType, qosMilliseconds);
                            }

                            LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment request", uuid);
                        } else {
                            if(!isReversal) {

                                FPSOutboundPaymentResponse fpsResponse = new FPSOutboundPaymentResponse();

                                fpsResponse.setFPID(FPID);
                                fpsResponse.setPaymentId(uuid);
                                fpsResponse.setOrgnlPaymentDocument(paymentDocument);
                                fpsResponse.setTxSts(Constants.REJECT_CODE);
                                fpsResponse.setStsRsn(Constants.NO_VALIDATION_CODE);
                                fpsResponse.setPaymentTimestamp(new Date().getTime());


                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment Reject response to {}", uuid, nextEnvironment);
                                // Send avro message to Kafka
                                event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(),
                                        uuid, gson.toJson(fpsResponse), entity, brand);

                                sendToKafka(outboundResponseTopic, uuid, event, paymentTypeCode, nextEnvironment, qosMilliseconds);

                                LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment Reject response", uuid);
                            }else{
                                FPSOutboundReversalResponse fpsResponse = new FPSOutboundReversalResponse();

                                fpsResponse.setFPID(FPID);
                                fpsResponse.setPaymentId(uuid);
                                fpsResponse.setRvsdDocument(paymentreversalDocument);
                                fpsResponse.setRvsdRsn(Constants.NO_VALIDATION_CODE);
                                fpsResponse.setRvsdSts(Constants.REJECT_CODE);
                                fpsResponse.setPaymentTimestamp(new Date().getTime());


                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment Reversal response to {}", uuid, nextEnvironment);
                                // Send avro message to Kafka
                                event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(), uuid, gson.toJson(fpsResponse), entity, brand);

                                sendToKafka(inboundReversalResponseTopic, uuid, event, paymentTypeCode, nextEnvironment, qosMilliseconds);

                                LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment Reversal response", uuid);
                            }
                        }
                    }
                    
                    event = EventGenerator.generateEvent(
                            this.getClass().getName(),
                            FPSEvents.FPS_PAYMENT_RECEIVED.getEventName(),
                            uuid,
                            originalPaymentMessage,
                            entity,
                            brand
                    );
                    kafkaSender.sendInMemoryMessage(inMemoryRequestTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                            FPID, uuid, nextEnvironment, paymentType, qosMilliseconds);

                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                }
                LOG.debug("[FPS][PmtId: {}] Time to process inbound payment request: {} ms",
                        uuid, new Date().getTime()-startTime);
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
                LOG.error("[FPS][PaymentType: {}] Error {}",
                        paymentType,  ex.getMessage(), ex);
            }
        }
    }


    private PaymentBean checkPreviousResponse(String message, String uuid, String FPID, String paymentType, String environmentMQ) {
        PaymentBean resendPreviousResponse = null;
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance(expiringMinutes);
        PaymentBean payment = storage.findPayment(FPID, message);
        if (payment != null && payment.getStatus().equals(PaymentStatus.PROCESSED)){
            resendPreviousResponse = payment;
        }else{
            storage.storePayment(FPID, message, uuid, paymentType, environmentMQ);
        }
        return resendPreviousResponse;
    }

    private String extractFPID(FPSAvroMessage avroFpsMessage, boolean isReversal) {
        String FPID = "";

        if(!isReversal){
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19 creditTransferTransaction;
            creditTransferTransaction = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) avroFpsMessage.getMessage())
                    .getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
            if(!creditTransferTransaction.getInstrForNxtAgt().isEmpty()){
                FPID = creditTransferTransaction.getInstrForNxtAgt().get(0).getInstrInf();
                FPID = FPID.substring(FPID.lastIndexOf('/')+1);
            } else{
                String txId = StringUtils.rightPad(creditTransferTransaction.getPmtId().getTxId(), 18);
                String localInstrument = creditTransferTransaction.getPmtTpInf().getLclInstrm().getPrtry();
                int slashIndex = localInstrument.lastIndexOf('/');
                String paymentTypeCode = localInstrument.substring(slashIndex+1, slashIndex+3);
                String currency = CurrencyCodes.getInstance().getCurrencyCode(creditTransferTransaction.getIntrBkSttlmAmt().getCcy());
                String sendingFPSInstitution = creditTransferTransaction.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
                String dateSent = creditTransferTransaction.getIntrBkSttlmDt().replaceAll("-","");
                FPID = StringUtils.rightPad(txId+paymentTypeCode+dateSent+currency+sendingFPSInstitution, 42);
            }
        }else {
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document document = (com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document) avroFpsMessage.getMessage();

            if (document.getFIToFIPmtRvsl().getTxInf() != null && !document.getFIToFIPmtRvsl().getTxInf().isEmpty()
                    && document.getFIToFIPmtRvsl().getTxInf().get(0) != null
                    && document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef() != null
                    && document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf() != null
                    && document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd() != null
                    && !document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().isEmpty()
                    && document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0) != null
                    && document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf() != null
                    && !document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().isEmpty()) {
                String addtlRmtInf = document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().get(0);
                FPID = addtlRmtInf.substring(addtlRmtInf.lastIndexOf('/') + 1);
            } else {
                String txId = StringUtils.rightPad(document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxId(), 18);
                String localInstrument = document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry();
                int slashIndex = localInstrument.lastIndexOf('/');
                String paymentTypeCode = localInstrument.substring(slashIndex + 1, slashIndex + 3);
                String currency = CurrencyCodes.getInstance().getCurrencyCode(document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getIntrBkSttlmAmt().getCcy());

                String sendingFPSInstitution = document.getFIToFIPmtRvsl().getTxInf().get(0).getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
                String dateSent = document.getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getIntrBkSttlmDt().replaceAll("-", "");
                FPID = StringUtils.rightPad(txId + paymentTypeCode + dateSent + currency + sendingFPSInstitution, 42);
            }
        }
        return FPID;
    }

    private String extractPaymentTypeCode(FPSAvroMessage avroFpsMessage, boolean isReversal) {
        String paymentTypeCode = "";
        if(!isReversal) {
            paymentTypeCode = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document) avroFpsMessage.getMessage()).getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm().getPrtry();
        }else{
            paymentTypeCode = ((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document) avroFpsMessage.getMessage()).getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry();
        }
        paymentTypeCode = paymentTypeCode.substring(0, paymentTypeCode.indexOf('/'));
        return paymentTypeCode;
    }

    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }

    protected void calculateMetrics(String paymentType) {
        if (paymentType.equalsIgnoreCase(SIP)) {
            inbound_sip_requests.inc();
        }else if (paymentType.equalsIgnoreCase(SOP)) {
            inbound_sop_requests.inc();
        }else if (paymentType.equalsIgnoreCase(FDP)) {
            inbound_fdp_requests.inc();
        }else if (paymentType.equalsIgnoreCase(CBP)) {
            inbound_cbp_requests.inc();
        }else if (paymentType.equalsIgnoreCase(SRN)) {
            inbound_srn_requests.inc();
        }else if (paymentType.equalsIgnoreCase(RTN)) {
            inbound_rtn_requests.inc();
        }
    }
    protected abstract void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds);

    protected abstract String getEnvironment();

}
