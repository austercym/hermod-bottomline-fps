package com.hermod.bottomline.fps.listeners.inbound;


import com.google.gson.Gson;
import com.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundReversalResponse;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.*;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1;
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
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component(value="kafkaResponseReversalInboundListener")
public class KafkaResponseReversalInboundListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaResponseReversalInboundListener.class);

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
			LOG.info("[FPS][PmtId: {}] Processing event response for FPS inbound reversal payment", key);
			// Parse Event Message
			Event eventPayment = null;
			try {
				eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing event response for FPS inbound reversal payment. Error Message: {}", key, ex.getMessage(), ex);
			}

			LOG.info("[FPS][PmtId: {}] Event type name {}.", key, eventPayment.getEvent().getName());
			if(!eventPayment.getEvent().getName().equalsIgnoreCase(FPSEvents.FPS_RETURN_PROCESSED.getEventName())){
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

						LOG.info("[FPS][PmtId: {}] XML Response generated for FPS inbound reversal payment. Response: {}", key, rawMessage.toString());
						kafkaSender.sendRawMessage(loggingTopic, rawMessage.toString(), key);

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

						updatePaymentResponseInMemory(originalStr, FPID, rawMessage.toString(), key, paymentType);
						sendToMQ(key, rawMessage.toString(), queueToSend, paymentType);

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
		 } catch (Exception e) {
     		throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
		 }
	}
	private FPSAvroMessage generateFPSPacs002ReversalResponse(FPSOutboundReversalResponse fpsPaymentResponse) {
		Document fpsPacs002Response = new Document();
		Gson gson = new Gson();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		FPSAvroMessage avroMessage = new FPSAvroMessage();

		// Generate message identifier for response message
		String msgId002;
		try {
			msgId002 = IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
		} catch (Exception e) {
			LOG.error("[FPS][PmtId: {}] Error generating message identifier for response. Error Message: {}", fpsPaymentResponse.getPaymentId(), e.getMessage(), e);
			msgId002 = "002" + fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxId() + df.format(new Date());
			LOG.error("[FPS][PmtId: {}] generated message identifier by default. Pacs.002 MsgId: {}", fpsPaymentResponse.getPaymentId(), msgId002);
		}

		// Payment Status Report
		fpsPacs002Response.setFIToFIPmtStsRpt(new FIToFIPaymentStatusReportV06());

		// Group Header
		fpsPacs002Response.getFIToFIPmtStsRpt().setGrpHdr(new GroupHeader53());
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setMsgId(msgId002);
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setCreDtTm(new Date().getTime());
		// Instructing Agent - from instructed agent on original payment
		BranchAndFinancialInstitutionIdentification5 instgAgt = gson.fromJson(gson.toJson(
				fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getInstdAgt()), BranchAndFinancialInstitutionIdentification5.class);
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setInstgAgt(instgAgt);

		// Transaction Information and Status
		List<PaymentTransaction52> listTxInfAndSts = new ArrayList<PaymentTransaction52>();
		PaymentTransaction52 pmtInfAndSts = new PaymentTransaction52();
		// <OrgnlGrpInf>
		pmtInfAndSts.setOrgnlGrpInf(new OriginalGroupInformation3());
		pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgId(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getGrpHdr().getMsgId());

		pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgNmId("pacs.007.001.05");
		// <OrgnlTxId>
		pmtInfAndSts.setOrgnlTxId(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxId());
		// <TxSts>
		pmtInfAndSts.setTxSts(fpsPaymentResponse.getRvsdSts());
		// <StsRsnInf>
		List<StatusReasonInformation9> listSts = new ArrayList<>();
		StatusReasonInformation9 sts = new StatusReasonInformation9();
		sts.setRsn(new StatusReason6Choice(null, fpsPaymentResponse.getRvsdRsn()));
		listSts.add(sts);
		pmtInfAndSts.setStsRsnInf(listSts);
		// <InstdAgt> - from instructed agent on original payment
		BranchAndFinancialInstitutionIdentification5 instdAgt = gson.fromJson(gson.toJson(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getInstgAgt()),
				BranchAndFinancialInstitutionIdentification5.class);
		pmtInfAndSts.setInstdAgt(instdAgt);
		//<OrgnlTxRef>
		pmtInfAndSts.setOrgnlTxRef(new OriginalTransactionReference20());
		pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmAmt(new ActiveOrHistoricCurrencyAndAmount());
		pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setCcy(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getRvsdIntrBkSttlmAmt().getCcy());
		pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setValue(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getRvsdIntrBkSttlmAmt().getValue());
		pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmDt(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getIntrBkSttlmDt());
		pmtInfAndSts.getOrgnlTxRef().setSttlmInf(gson.fromJson(gson.toJson(fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getGrpHdr().getSttlmInf()),SettlementInstruction1.class));
		pmtInfAndSts.getOrgnlTxRef().setPmtTpInf(new PaymentTypeInformation25());
		pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setSvcLvl(gson.fromJson(gson.toJson(
				fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getPmtTpInf().getSvcLvl()),
				ServiceLevel8Choice.class));
		pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setLclInstrm(gson.fromJson(gson.toJson(
				fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getPmtTpInf().getLclInstrm()),
				LocalInstrument2Choice.class));

		List<String> FPIdLst = new ArrayList<>();

		if( fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf() != null
				&& fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd() != null
				&& !fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().isEmpty()
				&& fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0) != null
				&& fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf() != null
				&& !fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().isEmpty()){
			String instrInf = fpsPaymentResponse.getRvsdDocument().getFIToFIPmtRvsl().getTxInf().get(0).getOrgnlTxRef().getRmtInf().getStrd().get(0).getAddtlRmtInf().get(0);
			if (instrInf.startsWith("/FPID/")) {
				FPIdLst.add(instrInf);
			}
		}

		if (!FPIdLst.isEmpty()) {
			RemittanceInformation10 rmtInf = new RemittanceInformation10();
			List<StructuredRemittanceInformation12> strdLst = new ArrayList<>();
			StructuredRemittanceInformation12 strd = new StructuredRemittanceInformation12();
			strd.setAddtlRmtInf(FPIdLst);
			strdLst.add(strd);
			rmtInf.setStrd(strdLst);
			pmtInfAndSts.getOrgnlTxRef().setRmtInf(rmtInf);
		}

		listTxInfAndSts.add(pmtInfAndSts);
		fpsPacs002Response.getFIToFIPmtStsRpt().setTxInfAndSts(listTxInfAndSts);

		avroMessage.setMessage(fpsPacs002Response);
		return avroMessage;
	}


	private void sendToMQ(String key, String rawMessage, String queueToSend, String paymentType) {
		boolean messageSent = false;

		while (!messageSent && numMaxAttempts>0) {
            try{
                LOG.info("[FPS][PaymentType: {}][PmtId: {}] Message to be sent to queue {} to Bottomline: {}", paymentType, key, queueToSend, rawMessage);
                jmsOperations.send(queueToSend, session -> {
                    return session.createTextMessage(rawMessage);
                });
				messageSent = true;
            } catch (Exception ex) {
                LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
                numMaxAttempts--;
            }
        }
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

}
