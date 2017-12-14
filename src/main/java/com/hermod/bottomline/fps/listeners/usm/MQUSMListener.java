package com.hermod.bottomline.fps.listeners.usm;

import com.bottomline.directfps.fpsusmelements.*;
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
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component(value = "mqUSMListener")
@Scope("prototype")
public class MQUSMListener extends BaseListener implements MessageListener {

    private static Logger LOG = LogManager.getLogger(MQUSMListener.class);

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

    public void onMessage(Message message) {

        LOG.info("[FPS] Getting usm message...............");
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
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    public void sendMessageToTopic(Reader reader) {
        this.sendMessageToTopic(reader, null);
    }

    public void sendMessageToTopic(Reader reader, String id) {
        boolean schemaValidation = true;
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (reader != null) {
            Resource fpsUSMxsdResource = new ClassPathResource("./xsd/usm/FPSUSMs.xsd");
            Resource iso20022TypesxsdResource = new ClassPathResource("./xsd/usm/ISO20022Types.xsd");
            Resource fpsUSMTypesxsdResource = new ClassPathResource("./xsd/usm/FPSUSMTypes.xsd");
            Resource fpsUSMElementsResource = new ClassPathResource("./xsd/usm/FPSUSMElements.xsd");
            Source[] xsdSources = null;
            try{
                xsdSources = new Source[]{new StreamSource(iso20022TypesxsdResource.getInputStream()),
                        new StreamSource(fpsUSMTypesxsdResource.getInputStream()),
                        new StreamSource(fpsUSMElementsResource.getInputStream()),
                        new StreamSource(fpsUSMxsdResource.getInputStream())};
            }catch(IOException e){
                LOG.error("[FPS][USM] ERROR: Getting sources from USM xsd files. {}",e.getMessage());
            }
            String message = "";
            try {
                String uuid = StringUtils.isNotEmpty(id)?id:IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
                StringWriter writer = new StringWriter();
                IOUtils.copy(reader, writer);
                message = writer.toString();

                //Send mq message to logging topic
                kafkaSender.sendRawMessage(loggingTopic, message, uuid);

                // Validate against scheme
                try {
                    Source src = new StreamSource(new StringReader(message));
                    Schema schema = schemaFactory.newSchema(xsdSources);
                    Validator validator = schema.newValidator();
                    validator.validate(src);
                } catch (SAXException ex) {
                    schemaValidation = false;
                    LOG.error("[FPS][USM] Error Validating message against scheme. Error:{} Message: {}", ex.getMessage(),
                            message);
                } catch (IOException e) {
                    schemaValidation = false;
                    LOG.error("[FPS][USM] I/O Error. Error:{} Message: {}", e.getMessage(), message);
                }
                // Getting Avro
                Source src = new StreamSource(new StringReader(message));
                FPSMessage fpsMessage = readMessage(message);


                // Call the correspondent transform
                FPSTransform transform = getTransformsUML(fpsMessage.getClass().getName());
                if (transform != null) {
                    Object avroFpsMessage = transform.fps2avro(fpsMessage);
                    boolean isValid = validMessage((FPSAvroMessage)avroFpsMessage);

                    //Document paymentDocument = ((Document) ((FPSAvroMessage) avroFpsMessage).getMessage());

                    if (schemaValidation && isValid) {

                        LOG.info("[FPS][PmtId: {}] Sending USM message", uuid);


                    }


                } else {
                    throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
                }
            }catch (ConversionException convEx){
                LOG.error("[FPS] Error generating Avro file. Error: {} Message: {}",
                        convEx.getMessage(), message);
            }catch(IOException e) {
                LOG.error("[FPS] IO Error {}",
                        e.getMessage());
            }catch(MessageConversionException conversionEx){
                LOG.error("[FPS] Error transforming message {}",
                        conversionEx.getMessage());
            } catch (Exception ex) {
                LOG.error("[FPS] Error getting message. Message error {}",
                        ex.getMessage(), ex);
            }
        }
    }

    private boolean validMessage(FPSAvroMessage avroFpsMessage) {
        return true;
    }

    protected void sendToKafka(String topic, String uuid, Event event){
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                null, "bottomlineEnv", "USM"
        );
    }

    private final FPSMessage readMessage(String message) throws Exception {
        StringBuffer xmlStr = new StringBuffer( message );

        FPSMessage serviceStatusMessage = null;
        if(message.toLowerCase().contains("fpsinst3rdpartystatusmessage")) {
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), FPSInst3RdPartyStatusMessageType.class);
        }else if(message.toLowerCase().contains("servicestatusmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), ServiceStatusMessageType.class);
        }else if(message.toLowerCase().contains("netsenderthresholdstatuschangemessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), NetSenderThresholdStatusChangeMessageType.class);
        }else if(message.toLowerCase().contains("netsendercapstatusmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), NetSenderCapStatusMessageType.class);
        }else if(message.toLowerCase().contains("sitlchangemessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), SITLChangeMessageType.class);
        }else if(message.toLowerCase().contains("stlpaymentcodemessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), STLPaymentCodeMessageType.class);
        }else if(message.toLowerCase().contains("netsenderthresholdmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), NetSenderThresholdMessageType.class);
        }else if(message.toLowerCase().contains("netsendercapchangemessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), NetSenderCapChangeMessageType.class);
        }else if(message.toLowerCase().contains("settlementstatusmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), SettlementStatusMessageType.class);
        }else if(message.toLowerCase().contains("schemereturnpaymentfailure")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), SchemeReturnPaymentFailureType.class);
        }else if(message.toLowerCase().contains("freeformatmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), FreeFormatMessageType.class);
        }else if(message.toLowerCase().contains("aspmalertmessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), ASPMAlertMessageType.class);
        }else if(message.toLowerCase().contains("proprietarymessage")){
            serviceStatusMessage = JAXB.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), ProprietaryMessageType.class);
        }
        return serviceStatusMessage;
    }

}
