package com.hermod.bottomline.fps.listeners;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.hermod.bottomline.fps.utils.generators.IDGeneratorBean;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSInboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.*;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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

public class KafkaListener extends BaseListener{

    private static Logger LOG = LogManager.getLogger(KafkaListener.class);

    @Autowired
    protected IDGeneratorBean idGenerator;


    protected StringWriter transformResponseToString(FPSMessage fpsMessage) throws JAXBException {
        StringWriter rawMessage = new StringWriter();
        final JAXBContext jc = JAXBContext.newInstance(iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class);
        final Marshaller marshaller = jc.createMarshaller();

        marshaller.marshal(fpsMessage, new StreamResult(rawMessage));
        return rawMessage;
    }

    protected FPSAvroMessage generateFPSPacs002Response(FPSInboundPaymentResponse fpsPaymentResponse) {
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
        // TODO Change pacs.008 version to v.06
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

    protected PaymentBean updatePaymentResponseInMemory(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document originalMessage,
                                                        String responseMessage, String paymentId) {
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance();
        Gson gson = new Gson();

        String originalStr = gson.toJson(originalMessage);
        String FPID = extractFPID(originalMessage);

        LOG.debug("[FPS][PmtId: {}] Storing response message to in-memory storage with FPID {}", paymentId, FPID);
        PaymentBean payment = storage.findPayment(FPID, originalStr);
        if (payment == null) {
            storage.storePayment(FPID, originalStr, paymentId);
        }
        payment = storage.completePaymentResponse(FPID, originalStr, responseMessage);

        return payment;
    }

    private String extractFPID(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document originalMessage) {
        String FPID = "";
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19 creditTransferTransaction = originalMessage
                .getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
        if(!creditTransferTransaction.getInstrForNxtAgt().isEmpty()){
            FPID = creditTransferTransaction.getInstrForNxtAgt().get(0).getInstrInf();
            FPID = FPID.substring(FPID.lastIndexOf('/')+1);
        } else{
            String txId = creditTransferTransaction.getPmtId().getTxId();
            String paymentTypeCode = creditTransferTransaction.getPmtTpInf().getLclInstrm().getPrtry();
            String currency = creditTransferTransaction.getIntrBkSttlmAmt().getCcy();
            String sendingFPSInstitution = creditTransferTransaction.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
            String dateSent = creditTransferTransaction.getIntrBkSttlmDt().replaceAll("-","");
            FPID = txId+paymentTypeCode+dateSent+currency+sendingFPSInstitution;
        }
        return FPID;
    }

    protected StringWriter transformPaymentRequestToString(FPSMessage fpsMessage) throws JAXBException {
        StringWriter rawMessage = new StringWriter();
        final JAXBContext jc = JAXBContext.newInstance(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class);
        final Marshaller marshaller = jc.createMarshaller();

        marshaller.marshal(fpsMessage, new StreamResult(rawMessage));
        return rawMessage;
    }
}
