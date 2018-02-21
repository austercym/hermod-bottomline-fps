package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.generators.EventGenerator;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundReversalResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.JmsException;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Date;

import static com.orwellg.hermod.bottomline.fps.utils.Constants.RESP_SUFFIX;

@Component(value="kafkaResponseReversalInboundListener")
public class KafkaResponseReversalInboundListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseReversalInboundListener.class);

	@Value("${wq.mq.queue.sip.inbound.resp}")
	private String outboundQueue;
	@Value("${wq.mq.queue.asyn.inbound.resp}")
	private String outboundAsynQueue;

	@Value("${kafka.topic.fps.logging}")
	private String loggingTopic;

	@Value("${connector.%id.mq_primary}")
	private String environmentPrimaryMQ;

	@Autowired
	private KafkaSender kafkaSender;

	@Autowired
	private TaskExecutor taskInboundResponseExecutor;

	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		try {
			String key = message.key();
			String value = message.value();
			LOG.debug("[FPS][PmtId: {}] Processing event reversal response for FPS inbound payment", key);
			taskInboundResponseExecutor.execute(new Runnable() {
				@Override
				public void run() {
					processInboundPaymentResponseReversal(message, key, value);

				}
			});
			LOG.debug("[FPS][PmtId: {}] End processing event reversal response for FPS inbound payment", key);
		} catch (Exception e) {
			throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
		}
	}
	@Async("taskInboundResponseExecutor")
	private void processInboundPaymentResponseReversal(ConsumerRecord<String, String> message, String key, String value) {
		long startTime = new Date().getTime();
		LOG.info("[FPS][PmtId: {}] Processing event reversal response for FPS inbound reversal payment", key);
		// Parse Event Message
		Event eventPayment = null;
		try {
			eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
		} catch (Exception ex) {
			LOG.error("[FPS][PmtId: {}] Error parsing event response for FPS inbound reversal payment. Error Message: {}", key, ex.getMessage(), ex);
		}

		LOG.info("[FPS][PmtId: {}] Event type name {}.", key, eventPayment.getEvent().getName());
		if(!eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_RETURN_PROCESSED.getEventName()) &&
				!eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_PAYMENT_RETURNED.getEventName())	){
			// Parse FPS Inbound Payment Rejection
			LOG.info("[FPS][PmtId: {}] parsing response for FPS inbound reversal payment {}", key, eventPayment.getEvent().getData());
			FPSOutboundReversalResponse fpsPaymentReversalResponse = null;
			try {
				fpsPaymentReversalResponse = new Gson().fromJson(eventPayment.getEvent().getData(), FPSOutboundReversalResponse.class);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound reversal payment. Error Message: {}", key, ex.getMessage(), ex);
			}
			LOG.info("[FPS][PmtId: {}] parsed response for FPS inbound reversal payment. Response message: {}", key, fpsPaymentReversalResponse.toString());

			// Generate Reversal Response
			FPSAvroMessage fpsPacs002Response = null;
			try {
				fpsPacs002Response = generateFPSPacs002ReversalResponse(fpsPaymentReversalResponse);
				LOG.info("[FPS][PmtId: {}] Response generated for FPS inbound reversal payment. Response: {}", key, fpsPacs002Response.toString());
				// Call the correspondent transform
				FPSTransform transform = transforms.get("transform_pacs_002_001");
				if (transform != null) {
					FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);

					StringWriter rawMessage = transformResponseToString(fpsMessage);
					String uuid = getResponsePaymentId(fpsPaymentReversalResponse);

					LOG.info("[FPS][PmtId: {}] XML Response generated for FPS inbound reversal payment. Response: {}", key, rawMessage.toString());
					kafkaSender.sendRawMessage(loggingTopic, rawMessage.toString(), uuid);

					Gson gson = new Gson();

					String originalStr = gson.toJson(fpsPaymentReversalResponse.getRvsdDocument());
					String FPID = extractFPID(fpsPaymentReversalResponse.getRvsdDocument());

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

					Header headerSite = headers.lastHeader(KafkaHeaders.FPS_SITE.getKafkaHeader());
					String environmentMQ = environmentPrimaryMQ;
					if(headerSite != null){
						environmentMQ = new String(headerSite.value(), "UTF-8");
						LOG.debug("[FPS][PaymentType: {}][PmtId: {}] Get header FPS_SITE: {}",
								paymentType, key, environmentMQ);
					}else{
						LOG.debug("[FPS][PaymentType: {}][PmtId: {}] No header FPS_SITE. Sending to primary MQ: {}",
								paymentType, key, environmentMQ);
					}
					updatePaymentResponseInMemory(originalStr, FPID, rawMessage.toString(), key, paymentType, environmentMQ);
					boolean reversalSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, environmentMQ);


					Event event = EventGenerator.generateEvent(
							this.getClass().getName(),
							FPSEvents.FPS_PAYMENT_SENT.getEventName(),
							uuid,
							eventPayment.getEvent().getData(),
							entity,
							brand
					);
					kafkaSender.sendInMemoryMessage(inMemoryResponseTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event),
							FPID, uuid, environmentMQ, paymentType);

				} else {
					throw new MessageConversionException("Exception in message emission. The transform for pacs_002_001 is null");
				}
			}catch(JmsException jmsex){
				LOG.error("[FPS][PmtId: {}] Error sending response for FPS inbound reversal payment to Bottomline. Error Message: {}", key, jmsex.getMessage(), jmsex);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error generating response for FPS inbound reversal payment. Error Message: {}", key, ex.getMessage(), ex);
			}
		}else{
			LOG.info("[FPS][PmtId: {}] Finish no sending return message processed ", key);
		}
		LOG.debug("[FPS][PmtId: {}] Time to process inbound payment reversal response: {} ms",
				key, new Date().getTime()-startTime);

	}

	protected String extractFPID(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document document) {
		String FPID = "";
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
		return FPID;
	}

	private String getResponsePaymentId(FPSOutboundReversalResponse fpsPaymentResponse) {
		return fpsPaymentResponse.getPaymentId()+ RESP_SUFFIX;
	}
}
