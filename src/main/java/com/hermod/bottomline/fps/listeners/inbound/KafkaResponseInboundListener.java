package com.hermod.bottomline.fps.listeners.inbound;


import com.google.gson.Gson;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPaymentResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.StringWriter;


@Component(value="kafkaResponseInboundListener")
public class KafkaResponseInboundListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseInboundListener.class);
	@Value("${wq.mq.queue.sip.inbound.resp}")
	private String outboundQueue;
	@Value("${wq.mq.queue.asyn.inbound.resp}")
	private String outboundAsynQueue;

	@Value("${kafka.topic.fps.logging}")
	private String loggingTopic;

	@Value("${wq.mq.num.max.attempts}")
	private int numMaxAttempts;

	@Autowired
	private JmsOperations jmsOperations;

	@Autowired
	private KafkaSender kafkaSender;


	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		String key = message.key();
		String value = message.value();
		try {
			LOG.info("[FPS][PmtId: {}] Processing event response for FPS inbound payment", key);
			// Parse Event Message
			Event eventPayment = null;
			try {
				eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing event response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}

			if(!eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_RETURN_PROCESSED.getEventName())){
				// Parse FPS Inbound Payment Rejection
				LOG.info("[FPS][PmtId: {}] parsing response for FPS inbound payment {}", key, eventPayment.getEvent().getData());
				FPSOutboundPaymentResponse fpsPaymentResponse = null;
				try {
					fpsPaymentResponse = new Gson().fromJson(eventPayment.getEvent().getData(), FPSOutboundPaymentResponse.class);
				} catch (Exception ex) {
					LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
				}
				LOG.info("[FPS][PmtId: {}] parsed response for FPS inbound payment. Response message: {}", key, fpsPaymentResponse.toString());

				// Generate Response Reject
				FPSAvroMessage fpsPacs002Response = null;
				try {
					fpsPacs002Response = generateFPSPacs002Response(fpsPaymentResponse);
					LOG.info("[FPS][PmtId: {}] Response generated for FPS inbound payment. Response: {}", key, fpsPacs002Response.toString());
					// Call the correspondent transform
					FPSTransform transform = transforms.get("transform_pacs_002_001");
					if (transform != null) {
						FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);

						StringWriter rawMessage = transformResponseToString(fpsMessage);

						LOG.info("[FPS][PmtId: {}] XML Response generated for FPS inbound payment. Response: {}", key, rawMessage.toString());
						kafkaSender.sendRawMessage(loggingTopic, rawMessage.toString(), key);

						updatePaymentResponseInMemory(fpsPaymentResponse.getOrgnlPaymentDocument(), rawMessage.toString(), key);

						//Send to MQ (Environment=Queue)
						String queueToSend = outboundAsynQueue;
						Headers headers = message.headers();
						Header header = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader());
						String paymentType = "SIP";
						if (header != null) {
							paymentType = new String(header.value(), "UTF-8");
						}
						if (paymentType.equalsIgnoreCase("SIP")) {
							queueToSend = outboundQueue;
						}

						sendToMQ(key, rawMessage, queueToSend, paymentType);

					} else {
						throw new MessageConversionException("Exception in message emission. The transform for pacs_002_001 is null");
					}
				}catch(JmsException jmsex){
					LOG.error("[FPS][PmtId: {}] Error sending response for FPS inbound payment to Bottomline. Error Message: {}", key, jmsex.getMessage(), jmsex);
				} catch (Exception ex) {
					LOG.error("[FPS][PmtId: {}] Error generating response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
				}
			}else{
				LOG.info("[FPS][PmtId: {}] Finish no sending return message processed ", key);
			}
		 } catch (Exception e) {
     		throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
		 }
	}

	private void sendToMQ(String key, StringWriter rawMessage, String queueToSend, String paymentType) {
		boolean messageSent = false;

		while (!messageSent && numMaxAttempts>0) {
            try{
                LOG.info("[FPS][PaymentType: {}][PmtId: {}] Message to be sent to queue {} to Bottomline: {}", paymentType, key, queueToSend, rawMessage.toString());
                jmsOperations.send(queueToSend, session -> {
                    return session.createTextMessage(rawMessage.toString());
                });
            } catch (Exception ex) {
                LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
                numMaxAttempts--;
            }
        }
	}

}
