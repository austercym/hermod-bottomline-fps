package com.orwellg.hermod.bottomline.fps.listeners.usm;

import com.bottomline.directfps.fpsusmelements.*;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.listeners.BaseListener;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.utils.USMMessage;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.hermod.bottomline.fps.utils.singletons.IDGeneratorBean;
import com.orwellg.hermod.bottomline.fps.utils.singletons.SchemeValidatorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundUSM;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import com.orwellg.umbrella.commons.utils.enums.fps.USMMessageTypes;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXB;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.Date;

import static com.codahale.metrics.MetricRegistry.name;

@Component(value = "mqUSMListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQUSMListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQUSMListener.class);

    private  Counter inbound_usm_requests;

    @Autowired
    private Gson gson;

    @Autowired
    protected KafkaSender kafkaSender;

    @Value("{entity.name}")
    private String entity;
    @Value("${brand.name}")
    private String brand;

    @Value("${kafka.topic.usm.message}")
    private String usmTopic;

    @Value("${kafka.topic.fps.inbound.logging}")
    private String loggingTopic;

    @Value("${connector.%id.mq_primary}")
    private String environmentMQ;

    public MQUSMListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_usm_requests = metricRegistry.counter(name("connector_fps", "inbound", "USM", FPSDirection.INPUT.getDirection()));
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {

        inbound_usm_requests.inc();

        LOG.info("[FPS] Getting usm message...............");
        InputStream stream = null;
        Reader reader = null;
        StringWriter writer = new StringWriter();

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
            sendMessageToTopic(writer, null);
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
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }


    public void sendMessageToTopic(Writer writer, String id) {
        Event event = null;
        if (writer != null) {
            String message = "";
            try {
                message = writer.toString();

                if(emergencyLog){
                    LOG.debug("[FPS][PaymentType: {}] Payload received {}", USM, message);
                }

                String uuid = StringUtils.isNotEmpty(id)?id:IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
                //Send mq message to logging topic
                event = getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_USM_RECEIVED.getEventName());

                kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid);

                // Validate against scheme
                boolean schemaValidation = isWellFormedMessage(message);

                // Getting Avro
                Source src = new StreamSource(new StringReader(message));
                USMMessage usmMessage = readMessage(message);

                // Call the correspondent transform
                FPSTransform transform = getTransformsUML(usmMessage.getFpsMessage().getClass().getName());

                if (transform != null) {
                    Object avroFpsMessage = transform.fps2avro(usmMessage.getFpsMessage());
                    boolean isValid = validMessage((FPSAvroMessage)avroFpsMessage);

                    if (schemaValidation && isValid) {

                        FPSInboundUSM fpsInboundUSM = new FPSInboundUSM();
                        fpsInboundUSM.setPaymentId(uuid);
                        fpsInboundUSM.setPaymentTimestamp(DateTime.now().getMillis());
                        fpsInboundUSM.setMessageId(usmMessage.getMessageId().toString());
                        fpsInboundUSM.setMsgDatetime(usmMessage.getDateTime().toGregorianCalendar().getTimeInMillis());
                        fpsInboundUSM.setUSMType(usmMessage.getUsmType().getMessageCode());
                        fpsInboundUSM.setUSMMessage(usmMessage.getFpsMessage());

                        LOG.info("[FPS][PmtId: {}] Sending USM message to topic {}", uuid, usmTopic);

                        event = EventGenerator.generateEvent(this.getClass().getName(),
                                FPSEvents.FPS_USM_RECEIVED.getEventName(), uuid, gson.toJson(fpsInboundUSM),
                                entity, brand
                        );

                        sendToKafka(usmTopic, uuid, event);
                        LOG.info("[FPS][PmtId: {}] Sent FPS USM message to topic {}", uuid, usmTopic);
                    }


                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + usmMessage.getFpsMessage().getClass().getName() + " is null");
                }
            }catch (ConversionException convEx){
                LOG.error("[FPS][USM] Error generating Avro file. Error: {} Message: {}", convEx.getMessage(),
                        message);
            }catch(IOException e) {
                LOG.error("[FPS][USM] IO Error {}", e.getMessage());
            }catch(MessageConversionException conversionEx){
                LOG.error("[FPS][USM] Error transforming message {}", conversionEx.getMessage());
            } catch (Exception ex) {
                LOG.error("[FPS][USM] Error getting message. Message error {}", ex.getMessage(), ex);
            }
        }
    }

    private boolean isWellFormedMessage(String message) {
        boolean schemaValidation = true;
        try {
            Source src = new StreamSource(new StringReader(message));
            Validator validator = SchemeValidatorBean.getInstance().getValidatorUSM();
            long timeStart = new Date().getTime();
            synchronized (validator) {
                validator.validate(src);
            }
            LOG.debug("[FPS] Validate against scheme last {} ms", new Date().getTime()-timeStart);
        } catch (SAXException ex) {
            schemaValidation = false;
            LOG.error("[FPS][USM] Error Validating message against scheme. Error:{} Message: {}", ex.getMessage(),
                    message);
        } catch (IOException e) {
            schemaValidation = false;
            LOG.error("[FPS][USM] I/O Error. Error:{} Message: {}", e.getMessage(), message);
        }
        return schemaValidation;
    }

    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }

    protected void sendToKafka(String topic, String uuid, Event event){
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                null, environmentMQ, USM, false
        );
    }

    private final USMMessage readMessage(String message) throws Exception {
        StringBuffer xmlStr = new StringBuffer( message );

        USMMessage usmMessage = new USMMessage();

        if(message.toLowerCase().contains("fpsinst3rdpartystatusmessage")) {
            FPSInst3RdPartyStatusMessageType fpsInst3RdPartyStatusMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    FPSInst3RdPartyStatusMessageType.class);
            usmMessage.setDateTime(fpsInst3RdPartyStatusMessageType.getDateTime());
            usmMessage.setMessageId(fpsInst3RdPartyStatusMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.FPS_INST_3RD_PARTY_STATUS);
            usmMessage.setFpsMessage(fpsInst3RdPartyStatusMessageType);
        }else if(message.toLowerCase().contains("servicestatusmessage")){
            ServiceStatusMessageType serviceStatusMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    ServiceStatusMessageType.class);
            usmMessage.setDateTime(serviceStatusMessageType.getDateTime());
            usmMessage.setMessageId(serviceStatusMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.SERVICE_STATUS);
            usmMessage.setFpsMessage(serviceStatusMessageType);
        }else if(message.toLowerCase().contains("netsenderthresholdstatuschangemessage")){
            NetSenderThresholdStatusChangeMessageType netSenderThresholdStatusChangeMessageType =
                    JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), NetSenderThresholdStatusChangeMessageType.class);
            usmMessage.setDateTime(netSenderThresholdStatusChangeMessageType.getDateTime());
            usmMessage.setMessageId(netSenderThresholdStatusChangeMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.NET_SENDER_THRESHOLD_STATUS_CHANGE);
            usmMessage.setFpsMessage(netSenderThresholdStatusChangeMessageType);
        }else if(message.toLowerCase().contains("netsendercapstatusmessage")){
            NetSenderCapStatusMessageType netSenderCapStatusMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    NetSenderCapStatusMessageType.class);
            usmMessage.setDateTime(netSenderCapStatusMessageType.getDateTime());
            usmMessage.setMessageId(netSenderCapStatusMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.NET_SENDER_CAP_STATUS);
            usmMessage.setFpsMessage(netSenderCapStatusMessageType);
        }else if(message.toLowerCase().contains("sitlchangemessage")){
            SITLChangeMessageType sitlChangeMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    SITLChangeMessageType.class);
            usmMessage.setDateTime(sitlChangeMessageType.getDateTime());
            usmMessage.setMessageId(sitlChangeMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.SITL_CHANGE);
            usmMessage.setFpsMessage(sitlChangeMessageType);
        }else if(message.toLowerCase().contains("stlpaymentcodemessage")){
            STLPaymentCodeMessageType stlPaymentCodeMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    STLPaymentCodeMessageType.class);
            usmMessage.setDateTime(stlPaymentCodeMessageType.getDateTime());
            usmMessage.setMessageId(stlPaymentCodeMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.STL_PAYMENT_CODE);
            usmMessage.setFpsMessage(stlPaymentCodeMessageType);
        }else if(message.toLowerCase().contains("netsenderthresholdmessage")){
            NetSenderThresholdMessageType netSenderThresholdMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    NetSenderThresholdMessageType.class);
            usmMessage.setDateTime(netSenderThresholdMessageType.getDateTime());
            usmMessage.setMessageId(netSenderThresholdMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.NET_SENDER_THRESHOLD);
            usmMessage.setFpsMessage(netSenderThresholdMessageType);
        }else if(message.toLowerCase().contains("netsendercapchangemessage")){
            NetSenderCapChangeMessageType netSenderCapChangeMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    NetSenderCapChangeMessageType.class);
            usmMessage.setDateTime(netSenderCapChangeMessageType.getDateTime());
            usmMessage.setMessageId(netSenderCapChangeMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.NET_SENDER_CAP_CHANGE);
            usmMessage.setFpsMessage(netSenderCapChangeMessageType);
        }else if(message.toLowerCase().contains("settlementstatusmessage")){
            SettlementStatusMessageType settlementStatusMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    SettlementStatusMessageType.class);
            usmMessage.setDateTime(settlementStatusMessageType.getDateTime());
            usmMessage.setMessageId(settlementStatusMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.SETTLEMENT_STATUS);
            usmMessage.setFpsMessage(settlementStatusMessageType);
        }else if(message.toLowerCase().contains("schemereturnpaymentfailure")){
            SchemeReturnPaymentFailureType schemeReturnPaymentFailureType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    SchemeReturnPaymentFailureType.class);
            usmMessage.setDateTime(schemeReturnPaymentFailureType.getDateTime());
            usmMessage.setMessageId(schemeReturnPaymentFailureType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.SCHEME_RETURN_PAYMENT_FAILURE);
            usmMessage.setFpsMessage(schemeReturnPaymentFailureType);
        }else if(message.toLowerCase().contains("freeformatmessage")){
            FreeFormatMessageType freeFormatMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    FreeFormatMessageType.class);
            usmMessage.setDateTime(freeFormatMessageType.getDateTime());
            usmMessage.setMessageId(freeFormatMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.FREE_FORMAT);
            usmMessage.setFpsMessage(freeFormatMessageType);
        }else if(message.toLowerCase().contains("aspmalertmessage")){
            ASPMAlertMessageType aspmAlertMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    ASPMAlertMessageType.class);
            usmMessage.setDateTime(aspmAlertMessageType.getDateTime());
            usmMessage.setMessageId(aspmAlertMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.ASPM_ALERT);
            usmMessage.setFpsMessage(aspmAlertMessageType);
        }else if(message.toLowerCase().contains("proprietarymessage")){
            ProprietaryMessageType proprietaryMessageType = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())),
                    ProprietaryMessageType.class);
            usmMessage.setDateTime(proprietaryMessageType.getDateTime());
            usmMessage.setMessageId(proprietaryMessageType.getMessageID());
            usmMessage.setUsmType(USMMessageTypes.PROPIETARY);
            usmMessage.setFpsMessage(proprietaryMessageType);
        }
        return usmMessage;
    }

}
