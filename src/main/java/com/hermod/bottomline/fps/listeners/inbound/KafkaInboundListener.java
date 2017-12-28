package com.hermod.bottomline.fps.listeners.inbound;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.*;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                                                        String responseMessage, String paymentId, String paymentType) {
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance();

        LOG.debug("[FPS][PmtId: {}] Storing response message to in-memory storage with FPID {}", paymentId, FPID);
        PaymentBean payment = storage.findPayment(FPID, originalStr);
        if (payment == null) {
            storage.storePayment(FPID, originalStr, paymentId, paymentType);
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
}
