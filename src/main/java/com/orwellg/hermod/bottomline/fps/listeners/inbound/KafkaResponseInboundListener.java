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
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPaymentResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
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

@Component(value="kafkaResponseInboundListener")
public class KafkaResponseInboundListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseInboundListener.class);
	private Counter inbound_sop_responses;
	private Counter inbound_fdp_responses;
	private Counter inbound_cbp_responses;
	private Counter inbound_srn_responses;
	private Counter inbound_rtn_responses;
	private Counter inbound_sip_responses;

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


	public KafkaResponseInboundListener(MetricRegistry metricRegistry){
		if(metricRegistry!= null) {
			String direction = FPSDirection.OUTPUT.getDirection();
			inbound_sop_responses = metricRegistry.counter(name("connector_fps", "inbound", "SOP", direction));
			inbound_cbp_responses = metricRegistry.counter(name("connector_fps", "inbound", "CBP", direction));
			inbound_fdp_responses = metricRegistry.counter(name("connector_fps", "inbound", "FDP", direction));
			inbound_srn_responses = metricRegistry.counter(name("connector_fps", "inbound", "SRN", direction));
			inbound_rtn_responses = metricRegistry.counter(name("connector_fps", "inbound", "RTN", direction));
			inbound_sip_responses = metricRegistry.counter(name("connector_fps", "inbound", "SIP", direction));

			//final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
			//reporterJMX.start();
		}else{
			LOG.error("No exists metrics registry");
		}
	}

	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		String key = message.key();
		String value = message.value();
		try{
			LOG.debug("[FPS][PmtId: {}] Processing event response for FPS inbound payment", key);
			taskInboundResponseExecutor.execute(new Runnable() {
				@Override
				public void run() {
					processInboundPaymentResponse(message, key, value);
				}
			});
			LOG.debug("[FPS][PmtId: {}] End processing event response for FPS inbound payment", key);
		} catch (Exception e) {
			LOG.error("[FPS][PmtId: {}] Exception in message emission. Message:", key, e.getMessage());
			throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
		}
	}

	@Async("taskInboundResponseExecutor")
	private void processInboundPaymentResponse(ConsumerRecord<String, String> message, String key, String value) {
		long startTime = new Date().getTime();
		// Parse Event Message
		Event eventPayment = null;
		try {
            eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
        } catch (Exception ex) {
            LOG.error("[FPS][PmtId: {}] Error parsing event response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
        }

		LOG.info("[FPS][PmtId: {}] Event type name {}.", key, eventPayment.getEvent().getName());
		if(!eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_RETURN_PROCESSED.getEventName()) &&
                !eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_PAYMENT_RETURNED.getEventName())	){
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
			String paymentType = SIP;
			String queueToSend = outboundAsynQueue;
			String environmentMQ = environmentPrimaryMQ;

            try {
                fpsPacs002Response = generateFPSPacs002Response(fpsPaymentResponse);
                LOG.info("[FPS][PmtId: {}] Response generated for FPS inbound payment. Response: {}", key, fpsPacs002Response.toString());
                // Call the correspondent transform
                FPSTransform transform = transforms.get("transform_pacs_002_001");
                if (transform != null) {
                    FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);

                    StringWriter rawMessage = transformResponseToString(fpsMessage);
                    String uuid = getResponsePaymentId(fpsPaymentResponse);

                    LOG.info("[FPS][PmtId: {}] XML Response generated for FPS inbound payment. Response: {}", key, rawMessage.toString());
                    kafkaSender.sendRawMessage(loggingTopic, rawMessage.toString(), uuid);

                    Gson gson = new Gson();

                    String originalStr = gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument());
                    String FPID = extractFPID(fpsPaymentResponse.getOrgnlPaymentDocument());

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
                    if(headerSite != null){
                        environmentMQ = new String(headerSite.value(), "UTF-8");
                        LOG.debug("[FPS][PaymentType: {}][PmtId: {}] Get header FPS_SITE: {}",
                                paymentType, key, environmentMQ);
                    }else{
                        LOG.debug("[FPS][PaymentType: {}][PmtId: {}] No header FPS_SITE. Sending to primary MQ: {}",
                                paymentType, key, environmentMQ);
                    }
                    updatePaymentResponseInMemory(originalStr, FPID, rawMessage.toString(), key, paymentType, environmentMQ);
                    boolean responseSent = sendToMQ(key, rawMessage.toString(), queueToSend, paymentType, environmentMQ);

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
                LOG.error("[FPS][PmtId: {}] Error sending response for FPS inbound payment to Bottomline. Error Message: {}", key, jmsex.getMessage(), jmsex);
            } catch (Exception ex) {
                LOG.error("[FPS][PmtId: {}] Error generating response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
            }
        }else{
            LOG.info("[FPS][PmtId: {}] Finish no sending return message processed ", key);
        }
		LOG.debug("[FPS][PmtId: {}] Time to process inbound payment response: {} ms",
                key, new Date().getTime()-startTime);
	}

	private void calculateMetrics(String paymentType) {
		if (paymentType.equalsIgnoreCase(SIP)) {
            inbound_sip_responses.inc();
        }else if (paymentType.equalsIgnoreCase(SOP)) {
            inbound_sop_responses.inc();
        }else if (paymentType.equalsIgnoreCase(FDP)) {
            inbound_fdp_responses.inc();
        }else if (paymentType.equalsIgnoreCase(CBP)) {
            inbound_cbp_responses.inc();
        }else if (paymentType.equalsIgnoreCase(SRN)) {
            inbound_srn_responses.inc();
        }else if (paymentType.equalsIgnoreCase(RTN)) {
            inbound_rtn_responses.inc();
        }
	}

	private FPSAvroMessage generateFPSPacs002Response(FPSOutboundPaymentResponse fpsPaymentResponse) {
		return generateFPSPacs002(fpsPaymentResponse.getOrgnlPaymentDocument(), fpsPaymentResponse.getPaymentId(),
				fpsPaymentResponse.getStsRsn(), fpsPaymentResponse.getTxSts());
	}



	protected String extractFPID(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document originalMessage) {
		String FPID = "";
		com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19 creditTransferTransaction = originalMessage
				.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		if(!creditTransferTransaction.getInstrForNxtAgt().isEmpty()){
			FPID = creditTransferTransaction.getInstrForNxtAgt().get(0).getInstrInf();
			FPID = FPID.substring(FPID.lastIndexOf('/')+1);
		} else{
			String txId = creditTransferTransaction.getPmtId().getTxId();
			String paymentTypeCode = creditTransferTransaction.getPmtTpInf().getLclInstrm().getPrtry();
			String currency = CurrencyCodes.getInstance().getCurrencyCode(creditTransferTransaction.getIntrBkSttlmAmt().getCcy());
			String sendingFPSInstitution = creditTransferTransaction.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
			String dateSent = creditTransferTransaction.getIntrBkSttlmDt().replaceAll("-","");
			FPID = txId+paymentTypeCode+dateSent+currency+sendingFPSInstitution;
		}
		return FPID;
	}
	private String getResponsePaymentId(FPSOutboundPaymentResponse fpsPaymentResponse) {
		return fpsPaymentResponse.getPaymentId() + RESP_SUFFIX;
	}

}
