package com.hermod.bottonline.fps.listeners;



import com.hermod.bottonline.fps.services.kafka.KafkaSender;
import com.hermod.bottonline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.*;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1;
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

import com.google.gson.Gson;
import com.hermod.bottonline.fps.services.transform.FPSTransform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


@Component(value="kafkaRejectListener")
public class KafkaRejectListener extends BaseListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	private static Logger LOG = LogManager.getLogger(KafkaRejectListener.class);
	@Value("${wq.mq.queue.sip.outbound}")
	private String outboundQueue;
	
	@Autowired
	private Gson gson;

	@Value("${kafka.topic.fps.logging}")
	private String loggingTopic;

	@Autowired
	private IDGeneratorBean idGenerator;
	
	@Autowired
	private JmsOperations jmsOperations;

	@Autowired
	private KafkaSender kafkaSender;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> message) {

		String key = message.key();
		String value = message.value();
		try {
			LOG.info("[FPS][PmtId: {}] Processing event rejection for FPS inbound payment", key);
			// Parse Event Message
			Event eventPaymentReject = null;
			try {
				eventPaymentReject = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing event rejection for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}

			// Parse FPS Inbound Payment Rejection
			LOG.info("[FPS][PmtId: {}] parsing response for FPS inbound payment", key);
			FPSInboundPaymentResponse fpsPaymentReject = null;
			try {
				fpsPaymentReject = new Gson().fromJson(eventPaymentReject.getEvent().getData(), FPSInboundPaymentResponse.class);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}
			LOG.info("[FPS][PmtId: {}] parsed response for FPS inbound payment. Response message: {}", key, fpsPaymentReject.toString());

			// Generate Response Reject
			FPSAvroMessage fpsPacs002Response = null;
			try {
				fpsPacs002Response  = generateFPSPacs002Response(fpsPaymentReject);
			} catch (Exception ex) {
				LOG.error("[FPS][PmtId: {}] Error generating rejection for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
			}
			LOG.info("[FPS][PmtId: {}] Response Reject generated for FPS inbound payment. Response: {}", key, fpsPacs002Response.toString());

			// Call the correspondent transform
			FPSTransform transform = transforms.get("transform_pacs_002_001");
			if (transform != null) {
				FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);

				//String rawMessage = convertDocumentToString((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document)fpsMessage);
				//LOG.info("[FPS][PmtId: {}] XML Reject generated for FPS inbound payment. Response: {}", key, rawMessage);
				//kafkaSender.sendRawMessage(loggingTopic, rawMessage, key);
				//TODO Send to MQ (Environment=Queue)
	    			//jmsOperations.convertAndSend(outboundQueue, fpsMessage);
			} else {
				throw new MessageConversionException("Exception in message emision. The transform for pacs_002_001 is null");
			}
		 } catch (Exception e) {
     		throw new MessageConversionException("Exception in message emision. Message: " + e.getMessage(), e);
		 }
	}

	private static String convertDocumentToString(org.w3c.dom.Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 *
	 */
	private FPSAvroMessage generateFPSPacs002Response(FPSInboundPaymentResponse fpsPaymentResponse) {
		Document fpsPacs002Response = new Document();
		Gson gson = new Gson();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		FPSAvroMessage avroMessage = new FPSAvroMessage();

		// Generate message identifier for response message
		String msgId002;
		try {
			msgId002 = idGenerator.generatorID().getGeneralUniqueId();
		} catch (Exception e) {
			LOG.error("[FPS][PmtId: {}] Error generating message identificer for response. Error Message: {}", fpsPaymentResponse.getPaymentId(), e.getMessage(), e);
			msgId002 = "002" + fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId() + df.format(new Date());
			LOG.error("[FPS][PmtId: {}] generated message identificer by default. Pacs.002 MsgId: {}", fpsPaymentResponse.getPaymentId(), msgId002);
		}

		// Payment Status Report
		fpsPacs002Response.setFIToFIPmtStsRpt(new FIToFIPaymentStatusReportV06());

		// Group Header
		fpsPacs002Response.getFIToFIPmtStsRpt().setGrpHdr(new GroupHeader53());
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setMsgId(msgId002);
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setCreDtTm(new Date().getTime());
		// Instructing Agent - from instructed agent on original payment
		BranchAndFinancialInstitutionIdentification5 instgAgt = gson.fromJson(gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getGrpHdr().getInstdAgt()), BranchAndFinancialInstitutionIdentification5.class);
		fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setInstgAgt(instgAgt);

		// Transaction Information and Status
		List<PaymentTransaction52> listTxInfAndSts = new ArrayList<PaymentTransaction52>();
		PaymentTransaction52 pmtInfAndSts = new PaymentTransaction52();
		// <OrgnlGrpInf>
		pmtInfAndSts.setOrgnlGrpInf(new OriginalGroupInformation3());
		pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgId(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
		// TODO
		pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgNmId("pacs.008.001.05");
		// <OrgnlTxId>
		pmtInfAndSts.setOrgnlTxId(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId());
		// <TxSts>
		pmtInfAndSts.setTxSts(fpsPaymentResponse.getTxSts());
		// <StsRsnInf>
		List<StatusReasonInformation9> listSts = new ArrayList<>();
		StatusReasonInformation9 sts = new StatusReasonInformation9();
		sts.setRsn(new StatusReason6Choice(null, fpsPaymentResponse.getStsRsn()));
		listSts.add(sts);
		pmtInfAndSts.setStsRsnInf(listSts);
		// <InstdAgt> - from instructed agent on original payment
		BranchAndFinancialInstitutionIdentification5 instdAgt = gson.fromJson(gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getGrpHdr().getInstdAgt()), BranchAndFinancialInstitutionIdentification5.class);
		pmtInfAndSts.setInstdAgt(instdAgt);
		//<OrgnlTxRef>
		pmtInfAndSts.setOrgnlTxRef(new OriginalTransactionReference20());
		pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmAmt(new ActiveOrHistoricCurrencyAndAmount());
		pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setCcy(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy());
		pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setValue(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmDt(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmDt());
		pmtInfAndSts.getOrgnlTxRef().setSttlmInf(gson.fromJson(gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getGrpHdr().getSttlmInf()),SettlementInstruction1.class));
		pmtInfAndSts.getOrgnlTxRef().setPmtTpInf(new PaymentTypeInformation25());
		pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setSvcLvl(gson.fromJson(gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getSvcLvl()),ServiceLevel8Choice.class));
		pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setLclInstrm(gson.fromJson(gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm()),LocalInstrument2Choice.class));

		List<String> FPIdLst = new ArrayList<>();
		List<InstructionForNextAgent1> instrForNxtAgt = fpsPaymentResponse.getOrgnlPaymentDocument().getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstrForNxtAgt();
		for (InstructionForNextAgent1 instruction : instrForNxtAgt) {
			String instrInf = instruction.getInstrInf();
			if (instrInf.startsWith("/FPID/")) {
				FPIdLst.add(instrInf);
				break;
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

}
