package com.hermod.bottomline.fps.listeners.inbound;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.storage.PaymentStatus;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.Constants;
import com.hermod.bottomline.fps.utils.generators.EventGenerator;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.hermod.bottomline.fps.utils.generators.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.*;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
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
import java.util.Date;

public abstract class MQListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQListener.class);

    @Autowired
    private Gson gson;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    protected KafkaSender kafkaSender;

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @Value("{entity.name}")
    private String entity;
    @Value("${brand.name}")
    private String brand;

    @Value("${kafka.topic.inbound.request}")
    protected String inboundTopic;

    @Value("${kafka.topic.reversal.request}")
    protected String inboundReversalTopic;

    @Value("${kafka.topic.reversal.response}")
    protected String inboundReversalResponseTopic;

    @Value("${kafka.topic.inbound.response}")
    private String outboundResponseTopic;

    @Value("${kafka.topic.fps.logging}")
    private String loggingTopic;

    @Value("${kafka.topic.inbound.response.replyTo}")
    protected String replyTo;

    @Value("${wq.mq.queue.sip.inbound.resp}")
    private String outboundQueue;
    @Value("${wq.mq.queue.asyn.inbound.resp}")
    private String outboundAsynQueue;

    @Value("${connector.mq_primary}")
    private String environmentMQ;

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
        this.sendMessageToTopic(reader, paymentType, null);
    }

    public void sendMessageToTopic(Reader reader, String paymentType, String id) {
        boolean schemaValidation = true;
        if (reader != null) {
            String message = "";
            try {
                String uuid = StringUtils.isNotEmpty(id)?id:IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
                StringWriter writer = new StringWriter();
                IOUtils.copy(reader, writer);
                message = writer.toString();

                //Send mq message to logging topic
                kafkaSender.sendRawMessage(loggingTopic, message, uuid);

                String errorMessage = "";
                boolean isReversal = false;
                if(message!=null && message.contains("pacs.008")) {
                    try {
                        // Validate against scheme
                        Source src = new StreamSource(new StringReader(message));
                        Validator validator = SchemeValidatorBean.getInstance().getValidatorPacs008();
                        validator.validate(src);
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
                            validator.validate(src);
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
                    PaymentBean previousPaymentProcessed = checkPreviousResponse(originalPaymentMessage,uuid, FPID, paymentType, environmentMQ);

                    if (previousPaymentProcessed != null){

                        LOG.info("[FPS][PmtId: {}] Payment previously processed, FPID: {}. Sending previous FPS Inbound payment response. Message: {}",
                                uuid, FPID, previousPaymentProcessed.getResponseMessage());
                        while(!previousPaymentProcessed.getStatus().getName().equals(PaymentStatus.PROCESSED.getName())){
                            LOG.info("[FPS][PmtId: {}] Waiting for finishing to be processed, FPID: {}", uuid, FPID);
                            Thread.sleep(10);
                            previousPaymentProcessed = checkPreviousResponse(originalPaymentMessage,uuid, FPID, paymentType, environmentMQ);
                        }
                        String paymentTypeToSend = previousPaymentProcessed.getPaymentType();
                        String queueToSend = outboundAsynQueue;

                        if (paymentTypeToSend.equalsIgnoreCase("SIP")) {
                            queueToSend = outboundQueue;
                        }

                        boolean responseSent = sendToMQ(uuid, previousPaymentProcessed.getResponseMessage(), queueToSend, paymentType, previousPaymentProcessed.getEnvironmentMQ());


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
                                if(paymentTypeCode.equalsIgnoreCase("RTN")){
                                    eventName = FPSEvents.FPS_RETURN_RECEIVED.getEventName();
                                }
                                Event event = EventGenerator.generateEvent(this.getClass().getName(),
                                        eventName, uuid, gson.toJson(fpsRequest), entity, brand
                                );
                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment request", uuid);
                                sendToKafka(inboundTopic, uuid, event, paymentTypeCode, environmentMQ);
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
                                Event event = EventGenerator.generateEvent(
                                        this.getClass().getName(),
                                        FPSEvents.FPS_REVERSAL_RECEIVED.getEventName(),
                                        uuid,
                                        gson.toJson(fpsInboundReversal),
                                        entity,
                                        brand
                                );

                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound reversal request", uuid);
                                sendToKafka(inboundReversalTopic, uuid, event, paymentTypeCode, environmentMQ);
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


                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment Reject response", uuid);
                                // Send avro message to Kafka
                                Event event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(), uuid, gson.toJson(fpsResponse), entity, brand);

                                sendToKafka(outboundResponseTopic, uuid, event, paymentTypeCode, environmentMQ);

                                LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment Reject response", uuid);
                            }else{
                                FPSOutboundReversalResponse fpsResponse = new FPSOutboundReversalResponse();

                                fpsResponse.setFPID(FPID);
                                fpsResponse.setPaymentId(uuid);
                                fpsResponse.setRvsdDocument(paymentreversalDocument);
                                fpsResponse.setRvsdRsn(Constants.NO_VALIDATION_CODE);
                                fpsResponse.setRvsdSts(Constants.REJECT_CODE);
                                fpsResponse.setPaymentTimestamp(new Date().getTime());


                                LOG.info("[FPS][PmtId: {}] Sending FPS Inbound payment Reversal response", uuid);
                                // Send avro message to Kafka
                                Event event = EventGenerator.generateEvent(this.getClass().getName(), FPSEvents.FPS_VALIDATION_ERROR.getEventName(), uuid, gson.toJson(fpsResponse), entity, brand);

                                sendToKafka(inboundReversalResponseTopic, uuid, event, paymentTypeCode, environmentMQ);

                                LOG.info("[FPS][PmtId: {}] Sent FPS Inbound payment Reversal response", uuid);
                            }
                        }
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

    protected abstract void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ);

}
