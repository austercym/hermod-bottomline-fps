package com.hermod.bottomline.fps.listeners.outbound;

import com.google.gson.Gson;
import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.storage.PaymentBean;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.commons.utils.enums.CurrencyCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class KafkaOutboundListener extends BaseListener {

    private static Logger LOG = LogManager.getLogger(KafkaOutboundListener.class);

    protected StringWriter transformRequestToString(FPSMessage fpsMessage) throws JAXBException {
        StringWriter rawMessage = new StringWriter();
        final JAXBContext jc = JAXBContext.newInstance(iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class);
        final Marshaller marshaller = jc.createMarshaller();

        marshaller.marshal(fpsMessage, new StreamResult(rawMessage));
        return rawMessage;
    }


    protected PaymentBean updatePaymentResponseInMemory(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document originalMessage,
                                                        String responseMessage, String paymentId, String paymentType, String environmentMQ) {
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance();
        Gson gson = new Gson();

        String originalStr = gson.toJson(originalMessage);
        String FPID = extractFPID(originalMessage);

        LOG.debug("[FPS][PmtId: {}] Storing response message to in-memory storage with FPID {}", paymentId, FPID);
        PaymentBean payment = storage.findPayment(FPID, originalStr);
        if (payment == null) {
            storage.storePayment(FPID, originalStr, paymentId, paymentType, environmentMQ);
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
            String currency = CurrencyCodes.getInstance().getCurrencyCode(creditTransferTransaction.getIntrBkSttlmAmt().getCcy());
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
