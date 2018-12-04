package com.orwellg.hermod.bottomline.fps.services.transform.pacs008;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import iso.std.iso._20022.tech.xsd.pacs_008_001.*;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Pacs008FPS2AvroTransform {

    public static void transform(iso.std.iso._20022.tech.xsd.pacs_008_001.Document fpsMessage,
                                 com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document avroMessage) throws ConversionException {

            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FIToFICustomerCreditTransferV05 fiToFICustomerCreditTransferV05 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FIToFICustomerCreditTransferV05();

        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GroupHeader49 grpHeader = getGroupHeader(fpsMessage);
        fiToFICustomerCreditTransferV05.setGrpHdr(grpHeader);

        iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV05 fiToFICstmrCdtTrf = fpsMessage.getFIToFICstmrCdtTrf();
        if(fiToFICstmrCdtTrf != null){
            List<iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19> cdtTrfTxInfList = fiToFICstmrCdtTrf.getCdtTrfTxInf();
            if(cdtTrfTxInfList != null){
                for (iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf: cdtTrfTxInfList) {
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19 creditTransferTransaction19 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditTransferTransaction19();

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentIdentification3 paymentIdentification3 = getPmtId(cdtTrfTxInf);
                    creditTransferTransaction19.setPmtId(paymentIdentification3);

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21 pmtTpInf = getPmtTpInf(cdtTrfTxInf);
                    creditTransferTransaction19.setPmtTpInf(pmtTpInf);


                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveCurrencyAndAmount intrBkSttlmAmt = getIntrBkSttlmAmt(cdtTrfTxInf);
                    creditTransferTransaction19.setIntrBkSttlmAmt(intrBkSttlmAmt);

                    XMLGregorianCalendar intrBkSttlmDt = cdtTrfTxInf.getIntrBkSttlmDt();

                    if(intrBkSttlmDt != null) {
                        creditTransferTransaction19.setIntrBkSttlmDt(intrBkSttlmDt.toXMLFormat());
                    }

                    XMLGregorianCalendar accptncDtTm = cdtTrfTxInf.getAccptncDtTm();

                    if(accptncDtTm != null){
                        creditTransferTransaction19.setAccptncDtTm(accptncDtTm.toGregorianCalendar().getTimeInMillis());
                    }

                    ActiveOrHistoricCurrencyAndAmount instdAmt = cdtTrfTxInf.getInstdAmt();
                    if(instdAmt != null) {
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveOrHistoricCurrencyAndAmount instdAmt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveOrHistoricCurrencyAndAmount();
                        String ccy = instdAmt.getCcy();
                        if(StringUtils.isNotEmpty(ccy)){
                            instdAmt1.setCcy(ccy);
                        }
                        BigDecimal value = instdAmt.getValue();

                        if(value != null) {
                            instdAmt1.setValue(DecimalTypeUtils.toDecimal(value,2));
                        }
                        creditTransferTransaction19.setInstdAmt(instdAmt1);
                    }

                    BigDecimal xchgRate = cdtTrfTxInf.getXchgRate();
                    if(xchgRate != null) {
                        creditTransferTransaction19.setXchgRate(DecimalTypeUtils.toDecimal(xchgRate,2));
                    }

                    ChargeBearerType1Code chrgBr = cdtTrfTxInf.getChrgBr();
                    if(chrgBr != null) {
                        creditTransferTransaction19.setChrgBr(chrgBr.value());
                    }

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 dbtr = getDbtr(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtr(dbtr);

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 dbtrAcct = getDbtrAcct(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtrAcct(dbtrAcct);

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 dbtrAgt = getDbtrAgt(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtrAgt(dbtrAgt);


                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 cdtrAgt = getCdtrAgt(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtrAgt(cdtrAgt);


                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 cdtr = getCdtr(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtr(cdtr);

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 cdtrAcct = getCdtrAcct(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtrAcct(cdtrAcct);

                    List<InstructionForCreditorAgent1> instrForCdtrAgt = cdtTrfTxInf.getInstrForCdtrAgt();
                    if(instrForCdtrAgt != null) {
                        if(!instrForCdtrAgt.isEmpty()){
                            creditTransferTransaction19.setInstrForCdtrAgt(new ArrayList<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForCreditorAgent1>());
                        }
                        for (InstructionForCreditorAgent1 instructionForCreditorAgent1 : instrForCdtrAgt) {
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForCreditorAgent1 instructionForCreditorAgent2 = getInstrForCdtrAgt(instructionForCreditorAgent1);
                            creditTransferTransaction19.getInstrForCdtrAgt().add(instructionForCreditorAgent2);
                        }
                    }

                    List<InstructionForNextAgent1> instrForNxtAgt = cdtTrfTxInf.getInstrForNxtAgt();
                    if(instrForNxtAgt != null) {
                        if(!instrForNxtAgt.isEmpty()){
                            creditTransferTransaction19.setInstrForNxtAgt(new ArrayList<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1>());
                        }
                        for (InstructionForNextAgent1 instructionForNextAgent2 : instrForNxtAgt) {
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1 instructionForNextAgent1 = getInstrForNxtAgt(instructionForNextAgent2);

                            creditTransferTransaction19.getInstrForNxtAgt().add(instructionForNextAgent1);
                        }
                    }

                    BranchAndFinancialInstitutionIdentification5 instgAgt1 = cdtTrfTxInf.getInstgAgt();
                    if (instgAgt1 != null) {
                        FinancialInstitutionIdentification8 finInstnId1 = instgAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                            if(clrSysMmbId1 != null) {
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                                if(clrSysId1 != null) {
                                    String cd = clrSysId1.getCd();
                                    if(StringUtils.isNotEmpty(cd)) {
                                        clrSysId.setCd(cd);
                                    }
                                    String prtry = clrSysId1.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        clrSysId.setPrtry(prtry);
                                    }
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }

                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 instgAgt = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                                instgAgt.setFinInstnId(finInstnId);
                                creditTransferTransaction19.setInstgAgt(instgAgt);
                            }
                        }
                    }

                    List<Charges2> chrgsInf = cdtTrfTxInf.getChrgsInf();
                    if(chrgsInf!= null) {
                        if(!chrgsInf.isEmpty()) {
                            creditTransferTransaction19.setChrgsInf(new ArrayList<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Charges2>());
                            for (Charges2 chrgInf : chrgsInf) {
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Charges2 chrgInf1 = getChrgInf(chrgInf);
                                creditTransferTransaction19.getChrgsInf().add(chrgInf1);
                            }
                        }
                    }

                    Purpose2Choice purp = cdtTrfTxInf.getPurp();
                    if(purp != null){
                        String prtry = purp.getPrtry();
                        if(StringUtils.isNotEmpty(prtry)){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Purpose2Choice purpose = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Purpose2Choice();
                            purpose.setPrtry(prtry);
                            creditTransferTransaction19.setPurp(purpose);
                        }
                    }

                    List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.RemittanceLocation4> listRemittanceLocation4 = new ArrayList<>();
                    creditTransferTransaction19.setRltdRmtInf(listRemittanceLocation4);

                    List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.SupplementaryData1> listSupplementaryData1 = new ArrayList<>();
                    creditTransferTransaction19.setSplmtryData(listSupplementaryData1);


                    RemittanceInformation10 rmtInf = cdtTrfTxInf.getRmtInf();
                    if(rmtInf!= null) {
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.RemittanceInformation10 rmtInf2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.RemittanceInformation10();

                        List<StructuredRemittanceInformation12> strdList = rmtInf.getStrd();
                        List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRemittanceInformation12> strdLst2 = new ArrayList<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRemittanceInformation12>();
                        if(strdList !=null) {
                            if(!strdList.isEmpty()) {
                                for (StructuredRemittanceInformation12 strd : strdList) {
                                    CreditorReferenceInformation2 cdtrRefInf = strd.getCdtrRefInf();
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRemittanceInformation12 strd1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRemittanceInformation12();
                                    if (cdtrRefInf != null) {
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditorReferenceInformation2 cdtrRefInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CreditorReferenceInformation2();
                                        String ref = cdtrRefInf.getRef();
                                        if (StringUtils.isNotEmpty(ref)) {
                                            cdtrRefInf1.setRef(ref);
                                            strd1.setCdtrRefInf(cdtrRefInf1);
                                        }
                                    }
                                    List<String> addtlRmtInfList = strd.getAddtlRmtInf();
                                    if (addtlRmtInfList != null) {
                                        if (!addtlRmtInfList.isEmpty()) {
                                            strd1.setAddtlRmtInf(new ArrayList<>());
                                            for (String addtlRmtInf : addtlRmtInfList) {
                                                strd1.getAddtlRmtInf().add(addtlRmtInf);
                                            }
                                        }
                                    }


                                    strdLst2.add(strd1);
                                    rmtInf2.setStrd(strdLst2);
                                }
                            }
                        }

                        creditTransferTransaction19.setRmtInf(rmtInf2);
                    }

                    // Intermediary Agent
                    BranchAndFinancialInstitutionIdentification5 intrmyAgt11 = cdtTrfTxInf.getIntrmyAgt1();
                    if(intrmyAgt11 != null){

                        FinancialInstitutionIdentification8 finInstnId = intrmyAgt11.getFinInstnId();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 intrmyAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                        if(finInstnId != null ){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                            String bicfi = finInstnId.getBICFI();
                            if(StringUtils.isNotEmpty(bicfi)){
                                finInstnId1.setBICFI(bicfi);
                            }
                            ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                            if(clrSysMmbId != null){
                                String mmbId = clrSysMmbId.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)){
                                    clrSysMmbId1.setMmbId(mmbId);
                                }
                                ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                if(clrSysId != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                    if(StringUtils.isNotEmpty(clrSysId.getPrtry())){
                                        clrSysId1.setPrtry(clrSysId.getPrtry());
                                    }
                                    clrSysMmbId1.setClrSysId(clrSysId1);
                                }

                                finInstnId1.setClrSysMmbId(clrSysMmbId1);

                                String name = finInstnId.getNm();
                                if(StringUtils.isNotEmpty(name)){
                                    finInstnId1.setNm(name);
                                }

                            }
                            intrmyAgt1.setFinInstnId(finInstnId1);
                        }


                        creditTransferTransaction19.setIntrmyAgt1(intrmyAgt1);
                    }

                    // Intermediary Agent
                    BranchAndFinancialInstitutionIdentification5 intrmyAgt21 = cdtTrfTxInf.getIntrmyAgt2();
                    if(intrmyAgt21 != null){

                        FinancialInstitutionIdentification8 finInstnId = intrmyAgt21.getFinInstnId();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 intrmyAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                        if(finInstnId != null ){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                            String bicfi = finInstnId.getBICFI();
                            if(StringUtils.isNotEmpty(bicfi)){
                                finInstnId1.setBICFI(bicfi);
                            }
                            ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                            if(clrSysMmbId != null){
                                String mmbId = clrSysMmbId.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)){
                                    clrSysMmbId1.setMmbId(mmbId);
                                }
                                ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                if(clrSysId != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                    if(StringUtils.isNotEmpty(clrSysId.getPrtry())){
                                        clrSysId1.setPrtry(clrSysId.getPrtry());
                                    }
                                    clrSysMmbId1.setClrSysId(clrSysId1);
                                }

                                finInstnId1.setClrSysMmbId(clrSysMmbId1);

                                String name = finInstnId.getNm();
                                if(StringUtils.isNotEmpty(name)){
                                    finInstnId1.setNm(name);
                                }

                            }
                            intrmyAgt1.setFinInstnId(finInstnId1);
                        }


                        creditTransferTransaction19.setIntrmyAgt2(intrmyAgt1);
                    }


                    List<RegulatoryReporting3> rgltryRptgLst = cdtTrfTxInf.getRgltryRptg();
                    if(rgltryRptgLst != null) {
                        for (RegulatoryReporting3 rgltryRptg : rgltryRptgLst) {
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.RegulatoryReporting3 rgltryRptgLst1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.RegulatoryReporting3();
                            List<StructuredRegulatoryReporting3> dtlsLst = rgltryRptg.getDtls();
                            if(dtlsLst != null) {
                                for (StructuredRegulatoryReporting3 dtls : dtlsLst) {
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRegulatoryReporting3 dtls2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRegulatoryReporting3();

                                    List<String> infLst = dtls.getInf();
                                    if(infLst != null) {
                                        for (String inf : infLst) {
                                            if(dtls2.getInf() == null){
                                                dtls2.setInf(new ArrayList <>());
                                            }
                                            dtls2.getInf().add(inf);
                                        }
                                    }
                                    if(rgltryRptgLst1.getDtls() == null){
                                        rgltryRptgLst1.setDtls(new ArrayList<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.StructuredRegulatoryReporting3 >());
                                    }
                                    rgltryRptgLst1.getDtls().add(dtls2);
                                }
                            }
                            if(creditTransferTransaction19.getRgltryRptg() == null){
                                creditTransferTransaction19.setRgltryRptg(new ArrayList <>());
                            }
                            creditTransferTransaction19.getRgltryRptg().add(rgltryRptgLst1);
                        }
                    }

                    if(fiToFICustomerCreditTransferV05.getCdtTrfTxInf() == null){
                        fiToFICustomerCreditTransferV05.setCdtTrfTxInf(new ArrayList<>());
                    }
                    fiToFICustomerCreditTransferV05.getCdtTrfTxInf().add(creditTransferTransaction19);

                }
            }
        }

        avroMessage.setFIToFICstmrCdtTrf(fiToFICustomerCreditTransferV05);

    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 getCdtrAcct(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 cdtrAcct = null;
        if(cdtTrfTxInf!= null) {
            CashAccount24 cdtrAcct1 = cdtTrfTxInf.getCdtrAcct();
            if (cdtrAcct1 != null) {
                AccountIdentification4Choice id1 = cdtrAcct1.getId();
                if (id1 != null) {
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice();
                    GenericAccountIdentification1 othr1 = id1.getOthr();
                    if (othr1 != null) {
                        String id2 = othr1.getId();
                        if (StringUtils.isNotEmpty(id2)) {
                            cdtrAcct = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1 othr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1();
                            othr.setId(id2);
                            id.setOthr(othr);
                        }
                    }

                    String iban = id1.getIBAN();
                    if (StringUtils.isNotEmpty(iban)) {
                        id.setIBAN(iban);
                    }
                    if(cdtrAcct == null){
                        cdtrAcct = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24();
                    }
                    cdtrAcct.setId(id);
                }
            }
        }

        return cdtrAcct;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 getDbtrAgt(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 dbtrAgt = null;
        if(cdtTrfTxInf != null) {
            BranchAndFinancialInstitutionIdentification5 dbtrAgt1 = cdtTrfTxInf.getDbtrAgt();
            if (dbtrAgt1 != null) {
                FinancialInstitutionIdentification8 finInstnId1 = dbtrAgt1.getFinInstnId();
                if(finInstnId1 != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }

                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                clrSysId.setCd(cd);
                                clrSysMmbId.setClrSysId(clrSysId);
                            }
                        }

                        String mmbId = clrSysMmbId1.getMmbId();
                        if(StringUtils.isNotEmpty(mmbId)){
                            clrSysMmbId.setMmbId(mmbId);
                        }
                        finInstnId.setClrSysMmbId(clrSysMmbId);
                    }
                    dbtrAgt = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                    dbtrAgt.setFinInstnId(finInstnId);
                }
            }
        }
        return dbtrAgt;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Charges2 getChrgInf(iso.std.iso._20022.tech.xsd.pacs_008_001.Charges2 charges2) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Charges2  charge = null;
        if(charges2 != null) {
            ActiveOrHistoricCurrencyAndAmount amt = charges2.getAmt();
            if(amt != null){
                charge = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Charges2();
                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveOrHistoricCurrencyAndAmount amt2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveOrHistoricCurrencyAndAmount();
                String ccy = amt.getCcy();
                if(StringUtils.isNotEmpty(ccy)) {
                    amt2.setCcy(ccy);
                }
                BigDecimal value = amt.getValue();
                if(value != null) {
                    amt2.setValue(DecimalTypeUtils.toDecimal(value, 2));
                }
                charge.setAmt(amt2);
            }
            BranchAndFinancialInstitutionIdentification5 agt = charges2.getAgt();
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 agt2 = null;
            if (agt != null) {
                FinancialInstitutionIdentification8 finInstnId1 = agt.getFinInstnId();
                if(finInstnId1 != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }
                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId =
                                        new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                clrSysId.setCd(cd);
                                clrSysMmbId.setClrSysId(clrSysId);
                            }
                        }

                        String mmbId = clrSysMmbId1.getMmbId();
                        if(StringUtils.isNotEmpty(mmbId)){
                            clrSysMmbId.setMmbId(mmbId);
                        }

                        finInstnId.setClrSysMmbId(clrSysMmbId);
                    }
                    agt2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                    agt2.setFinInstnId(finInstnId);
                    charge.setAgt(agt2);
                }
            }
        }
        return charge;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1 getInstrForNxtAgt(iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForNextAgent1 instructionForNextAgent2) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1 instructionForNextAgent1 = null;
        if(instructionForNextAgent2 != null) {
            String instrInf = instructionForNextAgent2.getInstrInf();
            if (StringUtils.isNotEmpty(instrInf)) {
                instructionForNextAgent1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForNextAgent1();
                instructionForNextAgent1.setInstrInf(instrInf);

            }
        }
        return instructionForNextAgent1;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForCreditorAgent1 getInstrForCdtrAgt(iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForCreditorAgent1 instructionForCdtrAgent2) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForCreditorAgent1 instructionForCreditorAgent1 = null;
        if(instructionForCdtrAgent2 != null) {
            String instrInf = instructionForCdtrAgent2.getInstrInf();
            if (StringUtils.isNotEmpty(instrInf)) {
                instructionForCreditorAgent1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.InstructionForCreditorAgent1();
                instructionForCreditorAgent1.setInstrInf(instrInf);

            }
        }
        return instructionForCreditorAgent1;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 getCdtrAgt(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 cdtrAgt = null;
        if(cdtTrfTxInf != null) {
            BranchAndFinancialInstitutionIdentification5 cdtrAgt1 = cdtTrfTxInf.getCdtrAgt();
            if (cdtrAgt1 != null) {
                FinancialInstitutionIdentification8 finInstnId1 = cdtrAgt1.getFinInstnId();
                if(finInstnId1 != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }
                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                clrSysId.setCd(cd);
                                clrSysMmbId.setClrSysId(clrSysId);
                            }
                        }

                        String mmbId = clrSysMmbId1.getMmbId();
                        if(StringUtils.isNotEmpty(mmbId)){
                            clrSysMmbId.setMmbId(mmbId);
                        }

                        finInstnId.setClrSysMmbId(clrSysMmbId);
                    }
                    cdtrAgt = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                    cdtrAgt.setFinInstnId(finInstnId);
                }
            }
        }
        return cdtrAgt;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24  getDbtrAcct(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 dbtrAcct = null;
        if(cdtTrfTxInf!= null) {
            CashAccount24 dbtrAcct1 = cdtTrfTxInf.getDbtrAcct();
            if (dbtrAcct1 != null) {
                AccountIdentification4Choice id1 = dbtrAcct1.getId();
                if (id1 != null) {
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice();
                    GenericAccountIdentification1 othr1 = id1.getOthr();
                    if (othr1 != null) {
                        String id2 = othr1.getId();
                        if (StringUtils.isNotEmpty(id2)) {
                            dbtrAcct = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1 othr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1();
                            othr.setId(id2);
                            id.setOthr(othr);
                        }
                    }

                    String iban = id1.getIBAN();
                    if (StringUtils.isNotEmpty(iban)) {
                        id.setIBAN(iban);
                    }
                    if(dbtrAcct == null){
                        dbtrAcct = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24();
                    }
                    dbtrAcct.setId(id);
                }
            }
        }

        return dbtrAcct;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 getCdtr(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 cdtr = null;
        if(cdtTrfTxInf != null) {
            PartyIdentification43 cdtr1 = cdtTrfTxInf.getCdtr();
            if (cdtr1 != null) {
                String nm = cdtr1.getNm();
                if (StringUtils.isNotEmpty(nm)) {
                    cdtr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43();
                    cdtr.setNm(nm);
                }
                PostalAddress6 pstlAdr = cdtr1.getPstlAdr();
                if(pstlAdr != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6 pstlAdr1 = null;
                    String ctry = pstlAdr.getCtry();
                    if(StringUtils.isNotEmpty(ctry)){
                        pstlAdr1 = new  com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6();
                        pstlAdr1.setCtry(ctry);
                    }
                    List<String> adrLine = pstlAdr.getAdrLine();
                    if(adrLine != null) {
                         if(pstlAdr1 == null){
                             pstlAdr1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6();
                         }
                        List<String> postalAddressLines = new ArrayList<>();
                        for (String addrLn : adrLine) {
                            postalAddressLines.add(addrLn);
                        }
                        pstlAdr1.setAdrLine(postalAddressLines);
                        if(cdtr == null){
                            cdtr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43();
                        }
                        cdtr.setPstlAdr(pstlAdr1);
                    }

                }
            }
        }
        return cdtr;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 getDbtr(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43 dbtr = null;
        if(cdtTrfTxInf != null) {
            PartyIdentification43 dbtr1 = cdtTrfTxInf.getDbtr();
            if (dbtr1 != null) {
                String nm = dbtr1.getNm();
                if (StringUtils.isNotEmpty(nm)) {
                    dbtr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43();
                    dbtr.setNm(nm);
                }
                PostalAddress6 pstlAdr = dbtr1.getPstlAdr();
                if(pstlAdr != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6 pstlAdr1 = null;
                    String ctry = pstlAdr.getCtry();
                    if(StringUtils.isNotEmpty(ctry)){
                        pstlAdr1 = new  com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6();
                        pstlAdr1.setCtry(ctry);
                    }
                    List<String> adrLine = pstlAdr.getAdrLine();
                    if(adrLine != null) {
                        if( pstlAdr1 == null){
                            pstlAdr1 = new  com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PostalAddress6();
                        }
                        List<String> postalAddressLines = new ArrayList<>();
                        for (String addrLn : adrLine) {
                            postalAddressLines.add(addrLn);
                        }
                        pstlAdr1.setAdrLine(postalAddressLines);
                        if(dbtr == null){
                            dbtr = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PartyIdentification43();
                        }
                        dbtr.setPstlAdr(pstlAdr1);
                    }

                }
            }
        }
        return dbtr;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveCurrencyAndAmount getIntrBkSttlmAmt(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveCurrencyAndAmount intrBkSttlmAmt = null;
        if(cdtTrfTxInf != null) {
            ActiveCurrencyAndAmount intrBkSttlmAmt1 = cdtTrfTxInf.getIntrBkSttlmAmt();
            if(intrBkSttlmAmt1 != null) {
                intrBkSttlmAmt = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ActiveCurrencyAndAmount();
                String ccy = intrBkSttlmAmt1.getCcy();
                if(StringUtils.isNotEmpty(ccy)) {
                    intrBkSttlmAmt.setCcy(ccy);
                }

                BigDecimal intrBkSttlmAmt1Value = intrBkSttlmAmt1.getValue();
                if(intrBkSttlmAmt1Value != null){
                    Decimal value = DecimalTypeUtils.toDecimal(intrBkSttlmAmt1Value, 2);
                    intrBkSttlmAmt.setValue(value);
                }
            }
        }
        return intrBkSttlmAmt;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21 getPmtTpInf(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21 pmtTpInf = null;
        if(cdtTrfTxInf != null) {
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ServiceLevel8Choice svcLvl = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ServiceLevel8Choice();
            PaymentTypeInformation21 pmtTpInf1 = cdtTrfTxInf.getPmtTpInf();

            if(pmtTpInf1 != null){
                Priority2Code instrPrty1 = pmtTpInf1.getInstrPrty();
                if(instrPrty1 != null) {
                    String instrPrty = instrPrty1.value();
                    if (StringUtils.isNotEmpty(instrPrty)) {
                        pmtTpInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21();
                        pmtTpInf.setInstrPrty(instrPrty);
                    }
                }
                ServiceLevel8Choice svcLvl1 = pmtTpInf1.getSvcLvl();
                if(svcLvl1 != null) {
                    String cd = svcLvl1.getCd();
                    if (StringUtils.isNotEmpty(cd)) {
                        svcLvl.setCd(cd);
                    }
                    String prtry = svcLvl1.getPrtry();
                    if (StringUtils.isNotEmpty(prtry)) {
                        svcLvl.setPrtry(prtry);
                    }
                    if(pmtTpInf == null){
                        pmtTpInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21();
                    }
                    pmtTpInf.setSvcLvl(svcLvl);
                }

                LocalInstrument2Choice lclInstrm1 = pmtTpInf1.getLclInstrm();
                if(lclInstrm1 != null) {
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.LocalInstrument2Choice lclInstrm = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.LocalInstrument2Choice();
                    String cd = lclInstrm1.getCd();
                    if(StringUtils.isNotEmpty(cd)) {
                        lclInstrm.setCd(cd);
                    }
                    String prtry = lclInstrm1.getPrtry();
                    if(StringUtils.isNotEmpty(prtry)) {
                        lclInstrm.setPrtry(prtry);
                    }
                    if(pmtTpInf == null){
                        pmtTpInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21();
                    }
                    pmtTpInf.setLclInstrm(lclInstrm);
                }
                CategoryPurpose1Choice ctgyPurp = pmtTpInf1.getCtgyPurp();
                if(ctgyPurp != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CategoryPurpose1Choice ctgyPurp2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CategoryPurpose1Choice();
                    String prtry = ctgyPurp.getPrtry();
                    if(StringUtils.isNotEmpty(prtry)){
                        ctgyPurp2.setPrtry(prtry);
                    }
                    if(pmtTpInf == null){
                        pmtTpInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentTypeInformation21();
                    }
                    pmtTpInf.setCtgyPurp(ctgyPurp2);
                }
            }
        }
        return pmtTpInf;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentIdentification3 getPmtId(iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 cdtTrfTxInf) {
        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentIdentification3 paymentIdentification3 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.PaymentIdentification3();
        if(cdtTrfTxInf!= null) {
            PaymentIdentification3 pmtId = cdtTrfTxInf.getPmtId();
            if(pmtId != null) {
                String endToEndId = pmtId.getEndToEndId();
                if(StringUtils.isNotEmpty(endToEndId)) {
                    paymentIdentification3.setEndToEndId(endToEndId);
                }
                String txId = pmtId.getTxId();
                if(StringUtils.isNotEmpty(txId)) {
                    paymentIdentification3.setTxId(txId);
                }
                String clrSysRef = pmtId.getClrSysRef();
                if(StringUtils.isNotEmpty(clrSysRef)){
                    paymentIdentification3.setClrSysRef(clrSysRef);
                }
            }
        }
        return paymentIdentification3;
    }

    private static com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GroupHeader49 getGroupHeader(iso.std.iso._20022.tech.xsd.pacs_008_001.Document fpsMessage) throws ConversionException {

        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GroupHeader49 grpHeader = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GroupHeader49();
        if(fpsMessage != null) {
            iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV05 fiToFICstmrCdtTrf = fpsMessage.getFIToFICstmrCdtTrf();
            if (fiToFICstmrCdtTrf != null) {
                iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader49 grpHdr = fiToFICstmrCdtTrf.getGrpHdr();

                if (grpHdr != null) {
                    String msgId = grpHdr.getMsgId();
                    if (StringUtils.isNotEmpty(msgId)) {
                        grpHeader.setMsgId(msgId);
                    }
                    XMLGregorianCalendar creDtTm = grpHdr.getCreDtTm();

                    if(creDtTm != null) {
                        grpHeader.setCreDtTm(creDtTm.toGregorianCalendar().getTimeInMillis());
                    }
                    String nbOfTxs = grpHdr.getNbOfTxs();
                    if(StringUtils.isNotEmpty(nbOfTxs)) {
                        grpHeader.setNbOfTxs(nbOfTxs);
                    }
                    iso.std.iso._20022.tech.xsd.pacs_008_001.SettlementInstruction1 sttlmInf1 = grpHdr.getSttlmInf();

                    if(sttlmInf1 != null) {
                        SettlementMethod1Code sttlmMtd1 = sttlmInf1.getSttlmMtd();
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.SettlementInstruction1 sttlmInf = null;
                        if(sttlmMtd1 != null) {
                            sttlmInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.SettlementInstruction1();
                            sttlmInf.setSttlmMtd(sttlmMtd1.value());
                        }
                        iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 sttlmAcct = sttlmInf1.getSttlmAcct();
                        if(sttlmAcct != null){
                            iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice id = sttlmAcct.getId();
                            if(id != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24 sttlmAcct1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.CashAccount24();
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice id1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.AccountIdentification4Choice();
                                String iban = id.getIBAN();
                                if(StringUtils.isNotEmpty(iban)) {
                                    id1.setIBAN(iban);
                                }
                                iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1 othr = id.getOthr();
                                if(othr != null){
                                    String id2 = othr.getId();
                                    if(StringUtils.isNotEmpty(id2)) {
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1 othr2 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.GenericAccountIdentification1();
                                        othr2.setId(id2);
                                        id1.setOthr(othr2);
                                    }
                                }
                                if (sttlmInf == null) {
                                    sttlmInf = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.SettlementInstruction1();
                                }
                                sttlmAcct1.setId(id1);
                                sttlmInf.setSttlmAcct(sttlmAcct1);
                            }
                        }
                        grpHeader.setSttlmInf(sttlmInf);
                    }

                    iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 instgAgt1 = grpHdr.getInstgAgt();
                    if (instgAgt1 != null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId1 = instgAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();

                            if(clrSysMmbId1 != null) {
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                                if(clrSysId1 != null) {
                                    String cd = clrSysId1.getCd();
                                    if(StringUtils.isNotEmpty(cd)) {
                                        clrSysId.setCd(cd);
                                    }
                                    String prtry = clrSysId1.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        clrSysId.setPrtry(prtry);
                                    }
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 instgAgt =  new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                                instgAgt.setFinInstnId(finInstnId);
                                grpHeader.setInstgAgt(instgAgt);
                            }
                        }
                    }

                    iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 instdAgt1 = grpHdr.getInstdAgt();

                    if (instdAgt1 != null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId1 = instdAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                            if(clrSysMmbId1 != null) {
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice clrSysId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemIdentification2Choice();
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                                if(clrSysId1 != null) {
                                    String cd = clrSysId1.getCd();
                                    if(StringUtils.isNotEmpty(cd)) {
                                        clrSysId.setCd(cd);
                                    }
                                    String prtry = clrSysId1.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        clrSysId.setPrtry(prtry);
                                    }
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2 clrSysMmbId =new  com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8 finInstnId = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }

                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5 instdAgt = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.BranchAndFinancialInstitutionIdentification5();
                                instdAgt.setFinInstnId(finInstnId);
                                grpHeader.setInstdAgt(instdAgt);
                            }
                        }
                    }
                }
            }
        }
        return grpHeader;
    }

    private static Object longToXmlCalendar(final Object input) throws ConversionException {
        try {
            final Long dateAsLong = (Long)input;
            final Date date = new Date(dateAsLong);
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            final DatatypeFactory dtf = DatatypeFactory.newInstance();
            final XMLGregorianCalendar xmlCalendar = dtf.newXMLGregorianCalendar(calendar);
            xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            return xmlCalendar;
        }
        catch (Exception err) {
            throw new ConversionException("Custom conversion Long->XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() +">", err, Pacs008FPS2AvroTransform.class);
        }
    }

    private static Object charSequenceToXmlCalendar(final Object input) throws ConversionException {
        try {
            final String inputDate = input.toString();

            if (inputDate.length() == 0) {
                return null;
            }
            final DateFormat format;

            if (inputDate.matches("^\\d{2}:\\d{2}:\\d{2}")){
                format = new SimpleDateFormat("hh:mm:ss");
            }else{
                format = new SimpleDateFormat("yyyy-MM-dd");
            }

            final Date date = format.parse(inputDate);
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            final DatatypeFactory dtf = DatatypeFactory.newInstance();
            final XMLGregorianCalendar xmlCalendar = dtf.newXMLGregorianCalendar(calendar);
            xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            return xmlCalendar;
        }
        catch (Exception err) {
            throw new ConversionException("Custom conversion CharSequence -> XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() + ">", err, Pacs008FPS2AvroTransform.class);
        }
    }

}
