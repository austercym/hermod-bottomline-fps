package com.hermod.bottomline.fps.listeners;


import com.google.gson.Gson;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.command.CodeResultValues;
import com.orwellg.umbrella.avro.types.command.Command;
import com.orwellg.umbrella.avro.types.command.accounting.AccountingCommandData;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component(value="kafkaResponseListener")
public class KafkaResponseListener extends KafkaListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseListener.class);
	@Value("${wq.mq.queue.sip.inbound.resp}")
	private String outboundQueue;
	
	@Autowired
	private Gson gson;

	@Value("${kafka.topic.fps.logging}")
	private String loggingTopic;
	
	@Autowired
	private JmsOperations jmsOperations;

	@Autowired
	private KafkaSender kafkaSender;

	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		String key = message.key();
		String value = message.value();
		try {
			LOG.info("[FPS][PmtId: {}] Processing command for FPS inbound payment", key);
			// Parse Command Message
			Command commandPaymentResponse = null;
			try {
				commandPaymentResponse = RawMessageUtils.decodeFromString(Command.SCHEMA$, value);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing event rejection for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}

			// Parse FPS Inbound Payment Rejection
			LOG.info("[FPS][PmtId: {}] parsing response for FPS inbound payment", key);
			AccountingCommandData accountingCommandData = null;
			FPSInboundPaymentResponse fpsInboundPaymentResponse = null;
			try {
				accountingCommandData = new Gson().fromJson(commandPaymentResponse.getCommand().getData(), AccountingCommandData.class);
				fpsInboundPaymentResponse = new FPSInboundPaymentResponse();
				//fpsInboundPaymentResponse.setFPID();
				fpsInboundPaymentResponse.setPaymentId(key);
				//fpsInboundPaymentResponse.setPaymentType();
				String txSts = "RJCT";
				if(commandPaymentResponse.getCommandResponse().getCode().equals(CodeResultValues.OK)){
					txSts = "ACSP";
					fpsInboundPaymentResponse.setStsRsn("0000");
				}else{
					fpsInboundPaymentResponse.setStsRsn("9999");
				}
				fpsInboundPaymentResponse.setTxSts(txSts);
				fpsInboundPaymentResponse.setOrgnlPaymentDocument(gson.fromJson(accountingCommandData.getTransactionInfo().getData(),
						com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class));

			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}
			LOG.info("[FPS][PmtId: {}] parsed response for FPS inbound payment. Response message: {}", key, fpsInboundPaymentResponse.toString());

			// Generate Response Reject
			FPSAvroMessage fpsPacs002Response = null;
			try {
				fpsPacs002Response  = generateFPSPacs002Response(fpsInboundPaymentResponse);
				LOG.info("[FPS][PmtId: {}] Response generated for FPS inbound payment. Response: {}", key, fpsPacs002Response.toString());
				// Call the correspondent transform
				FPSTransform transform = transforms.get("transform_pacs_002_001");
				if (transform != null) {
					FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);

					StringWriter rawMessage = transformResponseToString(fpsMessage);

					LOG.info("[FPS][PmtId: {}] XML Response generated for FPS inbound payment. Response: {}", key, rawMessage.toString());
					kafkaSender.sendRawMessage(loggingTopic, rawMessage.toString(), key);

					updatePaymentResponseInMemory(fpsInboundPaymentResponse.getOrgnlPaymentDocument(),rawMessage.toString(), key);

					//TODO Send to MQ (Environment=Queue)
					//jmsOperations.convertAndSend(outboundQueue, fpsMessage);
				} else {
					throw new MessageConversionException("Exception in message emission. The transform for pacs_002_001 is null");
				}
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error generating response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}

		 } catch (Exception e) {
     		throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
		 }
	}

}
