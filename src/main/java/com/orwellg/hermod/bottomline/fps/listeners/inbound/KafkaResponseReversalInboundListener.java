package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundReversalResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
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

import static com.codahale.metrics.MetricRegistry.name;
import static com.orwellg.hermod.bottomline.fps.utils.Constants.RESP_SUFFIX;

@Component(value="kafkaResponseReversalInboundListener")
public class KafkaResponseReversalInboundListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseReversalInboundListener.class);
	private Counter inbound_sop_reversal_responses;
	private Counter inbound_sip_reversal_responses;
	private Counter inbound_fdp_reversal_responses;
	private Counter inbound_cbp_reversal_responses;
	private Counter inbound_srn_reversal_responses;
	private Counter inbound_rtn_reversal_responses;

	@Value("${wq.mq.queue.sip.inbound.resp}")
	private String outboundQueue;
	@Value("${wq.mq.queue.asyn.inbound.resp}")
	private String outboundAsynQueue;

	@Value("${kafka.topic.fps.inbound.logging}")
	private String loggingTopic;

	@Value("${connector.%id.mq_primary}")
	private String environmentPrimaryMQ;

	@Value("${jms.mq.bottomline.environment.1}")
	private String environmentMQSite1;

	@Value("${jms.mq.bottomline.environment.2}")
	private String environmentMQSite2;

	@Autowired
	private KafkaSender kafkaSender;

	@Autowired
	private TaskExecutor taskInboundReversalExecutor;

	public KafkaResponseReversalInboundListener(MetricRegistry metricRegistry){
		if(metricRegistry!= null) {
			String direction = FPSDirection.OUTPUT.getDirection();
			inbound_sop_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "SOP", direction));
			inbound_sip_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "SIP", direction));
			inbound_cbp_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "CBP", direction));
			inbound_fdp_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "FDP", direction));
			inbound_srn_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "SRN", direction));
			inbound_rtn_reversal_responses = metricRegistry.counter(name("connector_fps_inbound_reversal", "inbound", "RTN", direction));
		}else{
			LOG.error("No exists metrics registry");
		}
	}

	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		try {
			String key = message.key();
			String value = message.value();
			LOG.debug("[FPS][PmtId: {}] Processing event reversal response for FPS inbound payment", key);
			taskInboundReversalExecutor.execute(new Runnable() {
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

	@Async("taskInboundReversalExecutor")
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
			String paymentType = SIP;
			String queueToSend = outboundAsynQueue;
			String environmentMQ = environmentPrimaryMQ;

			Event event = null;

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
					event = EventGenerator.generateEvent(
							this.getClass().getName(),
							FPSEvents.FPS_HERMOD_BL_INBOUND_RESPONSE.getEventName(),
							uuid,
							rawMessage.toString(),
							entity,
							brand
					);
					kafkaSender.sendRawMessage(loggingTopic, RawMessageUtils.encodeToString(Event.SCHEMA$, event), uuid);

					Gson gson = new Gson();

					String originalStr = gson.toJson(fpsPaymentReversalResponse.getRvsdDocument());
					String FPID = extractFPID(fpsPaymentReversalResponse.getRvsdDocument());

					//Send to MQ (Environment=Queue)
					Headers headers = message.headers();
					Header header = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader());
					if (header != null) {
						paymentType = new String(header.value(), "UTF-8");
					}
					if (paymentType.equalsIgnoreCase(SIP)) {
						queueToSend = outboundQueue;
					}

					calculateMetrics(paymentType);

					Header headerSite = headers.lastHeader(KafkaHeaders.FPS_SITE.getKafkaHeader());
					environmentMQ = getEnvironment(headerSite, key);

					updatePaymentResponseInMemory(originalStr, FPID, rawMessage.toString(), key, paymentType, environmentMQ);
					boolean reversalSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, environmentMQ);
					if(!reversalSent){
						if(environmentMQ.equalsIgnoreCase(environmentMQSite1)){
							environmentMQ = environmentMQSite2;
						}
						reversalSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, environmentMQ);
					}


					event = EventGenerator.generateEvent(
							this.getClass().getName(),
							FPSEvents.FPS_PAYMENT_REVERSED.getEventName(),
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

	private void calculateMetrics(String paymentType) {
		if (paymentType.equalsIgnoreCase(SIP)) {
			inbound_sip_reversal_responses.inc();
		}else if (paymentType.equalsIgnoreCase(SOP)) {
			inbound_sop_reversal_responses.inc();
		}else if (paymentType.equalsIgnoreCase(FDP)) {
			inbound_fdp_reversal_responses.inc();
		}else if (paymentType.equalsIgnoreCase(CBP)) {
			inbound_cbp_reversal_responses.inc();
		}else if (paymentType.equalsIgnoreCase(SRN)) {
			inbound_srn_reversal_responses.inc();
		}else if (paymentType.equalsIgnoreCase(RTN)) {
			inbound_rtn_reversal_responses.inc();
		}
	}
}
