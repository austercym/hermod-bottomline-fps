package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.listeners.BaseListener;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentBean;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundReversalResponse;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.*;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KafkaInboundListener extends BaseListener {

    private static Logger LOG = LogManager.getLogger(KafkaInboundListener.class);

    @Value("${kafka.topic.cache.response}")
    protected String inMemoryResponseTopic;

    @Value("{entity.name}")
    protected String entity;
    @Value("${brand.name}")
    protected String brand;

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    protected StringWriter transformResponseToString(FPSMessage fpsMessage) throws JAXBException {
        StringWriter rawMessage = new StringWriter();
        final JAXBContext jc = JAXBContext.newInstance(iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class);
        final Marshaller marshaller = jc.createMarshaller();

        marshaller.marshal(fpsMessage, new StreamResult(rawMessage));
        return rawMessage;
    }

    protected FPSAvroMessage generateFPSPacs002(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document originalDocument,
                                              String paymentId, String rsn, String txSts){
        Document fpsPacs002Response = new Document();
        Gson gson = new Gson();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        FPSAvroMessage avroMessage = new FPSAvroMessage();

        // Generate message identifier for response message
        String msgId002;
        try {
            msgId002 = IDGeneratorBean.getInstance().generatorID().getFasterPaymentUniqueId();
        } catch (Exception e) {
            LOG.error("[FPS][PmtId: {}] Error generating message identifier for response. Error Message: {}", paymentId, e.getMessage(), e);
            msgId002 = "002" + originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId() + df.format(new Date());
            LOG.info("[FPS][PmtId: {}] generated message identifier by default. Pacs.002 MsgId: {}", paymentId, msgId002);
        }

        // Payment Status Report
        fpsPacs002Response.setFIToFIPmtStsRpt(new FIToFIPaymentStatusReportV06());

        // Group Header
        fpsPacs002Response.getFIToFIPmtStsRpt().setGrpHdr(new GroupHeader53());
        fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setMsgId(msgId002);
        fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setCreDtTm(new Date().getTime());
        // Instructing Agent - from instructed agent on original payment
        BranchAndFinancialInstitutionIdentification5 instgAgt = gson.fromJson(gson.toJson(originalDocument.getFIToFICstmrCdtTrf().getGrpHdr().getInstdAgt()), BranchAndFinancialInstitutionIdentification5.class);
        fpsPacs002Response.getFIToFIPmtStsRpt().getGrpHdr().setInstgAgt(instgAgt);

        // Transaction Information and Status
        List<PaymentTransaction52> listTxInfAndSts = new ArrayList<PaymentTransaction52>();
        PaymentTransaction52 pmtInfAndSts = new PaymentTransaction52();
        // <OrgnlGrpInf>
        pmtInfAndSts.setOrgnlGrpInf(new OriginalGroupInformation3());
        pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgId(originalDocument.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
        // TODO Change pacs.008 version to v.06
        pmtInfAndSts.getOrgnlGrpInf().setOrgnlMsgNmId("pacs.008.001.05");
        // <OrgnlTxId>
        pmtInfAndSts.setOrgnlTxId(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId());
        // <TxSts>
        pmtInfAndSts.setTxSts(txSts);
        // <StsRsnInf>
        List<StatusReasonInformation9> listSts = new ArrayList<>();
        StatusReasonInformation9 sts = new StatusReasonInformation9();
        sts.setRsn(new StatusReason6Choice(null, rsn));
        listSts.add(sts);
        pmtInfAndSts.setStsRsnInf(listSts);
        // <InstdAgt> - from instructed agent on original payment
        BranchAndFinancialInstitutionIdentification5 instdAgt = gson.fromJson(gson.toJson(originalDocument.getFIToFICstmrCdtTrf().getGrpHdr().getInstgAgt()), BranchAndFinancialInstitutionIdentification5.class);
        pmtInfAndSts.setInstdAgt(instdAgt);
        //<OrgnlTxRef>
        pmtInfAndSts.setOrgnlTxRef(new OriginalTransactionReference20());
        pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmAmt(new ActiveOrHistoricCurrencyAndAmount());
        pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setCcy(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy());
        pmtInfAndSts.getOrgnlTxRef().getIntrBkSttlmAmt().setValue(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
        pmtInfAndSts.getOrgnlTxRef().setIntrBkSttlmDt(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmDt());
        pmtInfAndSts.getOrgnlTxRef().setSttlmInf(gson.fromJson(gson.toJson(originalDocument.getFIToFICstmrCdtTrf().getGrpHdr().getSttlmInf()),SettlementInstruction1.class));
        pmtInfAndSts.getOrgnlTxRef().setPmtTpInf(new PaymentTypeInformation25());
        pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setSvcLvl(gson.fromJson(gson.toJson(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getSvcLvl()),ServiceLevel8Choice.class));
        pmtInfAndSts.getOrgnlTxRef().getPmtTpInf().setLclInstrm(gson.fromJson(gson.toJson(originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm()),LocalInstrument2Choice.class));

        List<String> FPIdLst = new ArrayList<>();
        List<InstructionForNextAgent1> instrForNxtAgt = originalDocument.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstrForNxtAgt();
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

    protected PaymentBean updatePaymentResponseInMemory(String originalStr, String FPID,
                                                        String responseMessage, String paymentId,
                                                        String paymentType, String environmentMQ) {
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance(expiringMinutes);

        LOG.debug("[FPS][PmtId: {}] Storing response message to in-memory storage with FPID {}", paymentId, FPID);
        PaymentBean payment = storage.findPayment(FPID, originalStr);
        if (payment == null) {
            storage.storePayment(FPID, originalStr, paymentId, paymentType, environmentMQ);
        }
        payment = storage.completePaymentResponse(FPID, originalStr, responseMessage);

        return payment;
    }

    protected StringWriter transformPaymentRequestToString(FPSMessage fpsMessage) throws JAXBException {
        StringWriter rawMessage = new StringWriter();
        final JAXBContext jc = JAXBContext.newInstance(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class);
        final Marshaller marshaller = jc.createMarshaller();

        marshaller.marshal(fpsMessage, new StreamResult(rawMessage));
        return rawMessage;
    }

    protected FPSAvroMessage generateFPSPacs002ReversalResponse(FPSOutboundReversalResponse fpsPaymentResponse) {
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
}
