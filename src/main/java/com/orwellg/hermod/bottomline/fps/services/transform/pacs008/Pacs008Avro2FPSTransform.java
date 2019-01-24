package com.orwellg.hermod.bottomline.fps.services.transform.pacs008;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.*;
import iso.std.iso._20022.tech.xsd.pacs_008_001.ChargeBearerType1Code;
import iso.std.iso._20022.tech.xsd.pacs_008_001.Priority2Code;
import iso.std.iso._20022.tech.xsd.pacs_008_001.SettlementMethod1Code;
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

public class Pacs008Avro2FPSTransform {

    public static void transform(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document avroMessage,
                                 iso.std.iso._20022.tech.xsd.pacs_008_001.Document target) throws ConversionException {

        iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV05 fiToFICustomerCreditTransferV05 = new iso.std.iso._20022.tech.xsd.pacs_008_001.FIToFICustomerCreditTransferV05();

        iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader49 grpHeader = getGroupHeader(avroMessage);
        fiToFICustomerCreditTransferV05.setGrpHdr(grpHeader);

        FIToFICustomerCreditTransferV05 fiToFICstmrCdtTrf = avroMessage.getFIToFICstmrCdtTrf();
        if(fiToFICstmrCdtTrf != null){
            List<CreditTransferTransaction19> cdtTrfTxInfList = fiToFICstmrCdtTrf.getCdtTrfTxInf();
            if(cdtTrfTxInfList != null){
                for (CreditTransferTransaction19 cdtTrfTxInf: cdtTrfTxInfList) {
                    iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19 creditTransferTransaction19 = new iso.std.iso._20022.tech.xsd.pacs_008_001.CreditTransferTransaction19();

                    iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentIdentification3 paymentIdentification3 = getPmtId(cdtTrfTxInf);
                    creditTransferTransaction19.setPmtId(paymentIdentification3);

                    iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21 pmtTpInf = getPmtTpInf(cdtTrfTxInf);
                    creditTransferTransaction19.setPmtTpInf(pmtTpInf);


                    iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveCurrencyAndAmount intrBkSttlmAmt = getIntrBkSttlmAmt(cdtTrfTxInf);
                    creditTransferTransaction19.setIntrBkSttlmAmt(intrBkSttlmAmt);

                    String intrBkSttlmDt = cdtTrfTxInf.getIntrBkSttlmDt();

                    if(StringUtils.isNotEmpty(intrBkSttlmDt)) {
                        creditTransferTransaction19.setIntrBkSttlmDt((XMLGregorianCalendar) charSequenceToXmlCalendar(intrBkSttlmDt));
                    }

                    Long accptncDtTm = cdtTrfTxInf.getAccptncDtTm();
                    if(accptncDtTm != null){
                        creditTransferTransaction19.setAccptncDtTm((XMLGregorianCalendar) longToXmlCalendar(accptncDtTm));
                    }

                    ActiveOrHistoricCurrencyAndAmount instdAmt = cdtTrfTxInf.getInstdAmt();
                    if(instdAmt != null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveOrHistoricCurrencyAndAmount instdAmt1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveOrHistoricCurrencyAndAmount();
                        String ccy = instdAmt.getCcy();
                        if(StringUtils.isNotEmpty(ccy)){
                            instdAmt1.setCcy(ccy);
                        }
                        Decimal value = instdAmt.getValue();
                        if(value != null) {
                            instdAmt1.setValue(value.getValue());
                        }
                        creditTransferTransaction19.setInstdAmt(instdAmt1);
                    }

                    Decimal xchgRate = cdtTrfTxInf.getXchgRate();
                    if(xchgRate != null) {
                        BigDecimal value = xchgRate.getValue();
                        if (value != null) {
                            creditTransferTransaction19.setXchgRate(value);
                        }
                    }

                    String chrgBr = cdtTrfTxInf.getChrgBr();
                    if(StringUtils.isNotEmpty(chrgBr)) {
                        creditTransferTransaction19.setChrgBr(ChargeBearerType1Code.fromValue(chrgBr));
                    }

                    iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 dbtr = getDbtr(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtr(dbtr);

                    iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 dbtrAcct = getDbtrAcct(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtrAcct(dbtrAcct);

                    iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 dbtrAgt = getDbtrAgt(cdtTrfTxInf);
                    creditTransferTransaction19.setDbtrAgt(dbtrAgt);


                    iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 cdtrAgt = getCdtrAgt(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtrAgt(cdtrAgt);


                    iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 cdtr = getCdtr(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtr(cdtr);

                    iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 cdtrAcct = getCdtrAcct(cdtTrfTxInf);
                    creditTransferTransaction19.setCdtrAcct(cdtrAcct);

                    List<InstructionForCreditorAgent1> instrForCdtrAgt = cdtTrfTxInf.getInstrForCdtrAgt();
                    if(instrForCdtrAgt != null) {
                        for (InstructionForCreditorAgent1 instructionForCreditorAgent1 : instrForCdtrAgt) {
                            iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForCreditorAgent1 instructionForCreditorAgent2 = getInstrForCdtrAgt(instructionForCreditorAgent1);
                            creditTransferTransaction19.getInstrForCdtrAgt().add(instructionForCreditorAgent2);
                        }
                    }

                    List<InstructionForNextAgent1> instrForNxtAgt = cdtTrfTxInf.getInstrForNxtAgt();
                    if(instrForNxtAgt != null) {
                        for (InstructionForNextAgent1 instructionForNextAgent2 : instrForNxtAgt) {
                            iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForNextAgent1 instructionForNextAgent1 = getInstrForNxtAgt(instructionForNextAgent2);
                            creditTransferTransaction19.getInstrForNxtAgt().add(instructionForNextAgent1);
                        }
                    }

                    BranchAndFinancialInstitutionIdentification5 instgAgt1 = cdtTrfTxInf.getInstgAgt();
                    if (instgAgt1 != null) {
                        FinancialInstitutionIdentification8 finInstnId1 = instgAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                            if(clrSysMmbId1 != null) {
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }

                                iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 instgAgt = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                                instgAgt.setFinInstnId(finInstnId);
                                creditTransferTransaction19.setInstgAgt(instgAgt);
                            }
                        }
                    }



                    List<Charges2> chrgsInf = cdtTrfTxInf.getChrgsInf();
                    if(chrgsInf!= null) {
                        for (Charges2 chrgInf : chrgsInf) {
                            iso.std.iso._20022.tech.xsd.pacs_008_001.Charges2 chrgInf1 = getChrgInf(chrgInf);
                            creditTransferTransaction19.getChrgsInf().add(chrgInf1);
                        }
                    }

                    Purpose2Choice purp = cdtTrfTxInf.getPurp();
                    if(purp != null){
                        String prtry = purp.getPrtry();
                        if(StringUtils.isNotEmpty(prtry)){
                            iso.std.iso._20022.tech.xsd.pacs_008_001.Purpose2Choice purpose = new iso.std.iso._20022.tech.xsd.pacs_008_001.Purpose2Choice();
                            purpose.setPrtry(prtry);
                            creditTransferTransaction19.setPurp(purpose);
                        }
                    }


                    RemittanceInformation10 rmtInf = cdtTrfTxInf.getRmtInf();
                    if(rmtInf!= null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.RemittanceInformation10 rmtInf2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.RemittanceInformation10();
                        List<StructuredRemittanceInformation12> strdList = rmtInf.getStrd();
                        List<iso.std.iso._20022.tech.xsd.pacs_008_001.StructuredRemittanceInformation12> strdLst = rmtInf2.getStrd();
                        if(strdList !=null) {
                            for (StructuredRemittanceInformation12 strd : strdList) {
                                CreditorReferenceInformation2 cdtrRefInf = strd.getCdtrRefInf();
                                iso.std.iso._20022.tech.xsd.pacs_008_001.StructuredRemittanceInformation12 strd1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.StructuredRemittanceInformation12();
                                if (cdtrRefInf != null) {
                                    iso.std.iso._20022.tech.xsd.pacs_008_001.CreditorReferenceInformation2 cdtrRefInf1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.CreditorReferenceInformation2();
                                    String ref = cdtrRefInf.getRef();
                                    if (StringUtils.isNotEmpty(ref)) {
                                        cdtrRefInf1.setRef(ref);
                                        strd1.setCdtrRefInf(cdtrRefInf1);
                                    }
                                }
                                List<String> addtlRmtInfList = strd.getAddtlRmtInf();
                                if (addtlRmtInfList != null) {
                                    for (String addtlRmtInf : addtlRmtInfList) {
                                        strd1.getAddtlRmtInf().add(addtlRmtInf);
                                    }
                                }

                                strdLst.add(strd1);
                            }
                        }

                        creditTransferTransaction19.setRmtInf(rmtInf2);
                    }


                    // Intermediary Agent
                    BranchAndFinancialInstitutionIdentification5 intrmyAgt11 = cdtTrfTxInf.getIntrmyAgt1();
                    if(intrmyAgt11 != null){

                        FinancialInstitutionIdentification8 finInstnId = intrmyAgt11.getFinInstnId();
                        iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 intrmyAgt1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                        if(finInstnId != null ){
                            iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                            String bicfi = finInstnId.getBICFI();
                            if(StringUtils.isNotEmpty(bicfi)){
                                finInstnId1.setBICFI(bicfi);
                            }
                            ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                            iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                            if(clrSysMmbId != null){
                                String mmbId = clrSysMmbId.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)){
                                    clrSysMmbId1.setMmbId(mmbId);
                                }
                                ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                if(clrSysId != null){
                                    iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                        iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 intrmyAgt1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                        if(finInstnId != null ){
                            iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                            String bicfi = finInstnId.getBICFI();
                            if(StringUtils.isNotEmpty(bicfi)){
                                finInstnId1.setBICFI(bicfi);
                            }
                            ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                            iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                            if(clrSysMmbId != null){
                                String mmbId = clrSysMmbId.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)){
                                    clrSysMmbId1.setMmbId(mmbId);
                                }
                                ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                if(clrSysId != null){
                                    iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                            iso.std.iso._20022.tech.xsd.pacs_008_001.RegulatoryReporting3 rgltryRptgLst1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.RegulatoryReporting3();
                            List<StructuredRegulatoryReporting3> dtlsLst = rgltryRptg.getDtls();
                            if(dtlsLst != null) {
                                for (StructuredRegulatoryReporting3 dtls : dtlsLst) {
                                    iso.std.iso._20022.tech.xsd.pacs_008_001.StructuredRegulatoryReporting3 dtls2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.StructuredRegulatoryReporting3();

                                    List<String> infLst = dtls.getInf();
                                    if(infLst != null) {
                                        for (String inf : infLst) {
                                            dtls2.getInf().add(inf);
                                        }
                                    }
                                    rgltryRptgLst1.getDtls().add(dtls2);
                                }
                            }
                            creditTransferTransaction19.getRgltryRptg().add(rgltryRptgLst1);
                        }
                    }


                    fiToFICustomerCreditTransferV05.getCdtTrfTxInf().add(creditTransferTransaction19);

                }
            }
        }

        target.setFIToFICstmrCdtTrf(fiToFICustomerCreditTransferV05);

    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 getCdtrAcct(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 cdtrAcct = null;
        if(cdtTrfTxInf!= null) {
            CashAccount24 cdtrAcct1 = cdtTrfTxInf.getCdtrAcct();
            if (cdtrAcct1 != null) {
                AccountIdentification4Choice id1 = cdtrAcct1.getId();
                if (id1 != null) {
                    iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice id = new iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice();
                    GenericAccountIdentification1 othr1 = id1.getOthr();
                    if (othr1 != null) {
                        String id2 = othr1.getId();
                        if (StringUtils.isNotEmpty(id2)) {
                            cdtrAcct = new iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24();
                            iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1 othr = new iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1();
                            othr.setId(id2);
                            id.setOthr(othr);
                        }
                    }

                    String iban = id1.getIBAN();
                    if (StringUtils.isNotEmpty(iban)) {
                        if(cdtrAcct == null) {
                            cdtrAcct = new iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24();
                        }
                        id.setIBAN(iban);
                    }
                    cdtrAcct.setId(id);
                }
            }
        }

        return cdtrAcct;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 getDbtrAgt(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 dbtrAgt = null;
        if(cdtTrfTxInf != null) {
            BranchAndFinancialInstitutionIdentification5 dbtrAgt1 = cdtTrfTxInf.getDbtrAgt();
            if (dbtrAgt1 != null) {
                FinancialInstitutionIdentification8 finInstnId1 = dbtrAgt1.getFinInstnId();
                if(finInstnId1 != null){
                    iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }

                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                    dbtrAgt = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                    dbtrAgt.setFinInstnId(finInstnId);
                }
            }
        }
        return dbtrAgt;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.Charges2 getChrgInf(Charges2 charges2) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.Charges2 charge = null;
        if(charges2 != null) {
            ActiveOrHistoricCurrencyAndAmount amt = charges2.getAmt();
            if(amt != null){
                charge = new iso.std.iso._20022.tech.xsd.pacs_008_001.Charges2();
                iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveOrHistoricCurrencyAndAmount amt2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveOrHistoricCurrencyAndAmount();
                String ccy = amt.getCcy();
                if(StringUtils.isNotEmpty(ccy)) {
                    amt2.setCcy(ccy);
                }
                Decimal value = amt.getValue();
                if(value != null) {
                    amt2.setValue(value.getValue());
                }
                charge.setAmt(amt2);
            }
            BranchAndFinancialInstitutionIdentification5 agt = charges2.getAgt();
            iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 agt2 = null;
            if (agt != null) {
                FinancialInstitutionIdentification8 finInstnId1 = agt.getFinInstnId();
                if(finInstnId1 != null){
                    iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }
                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                    agt2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                    agt2.setFinInstnId(finInstnId);
                    charge.setAgt(agt2);
                }
            }
        }
        return charge;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForNextAgent1 getInstrForNxtAgt(InstructionForNextAgent1 instructionForNextAgent2) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForNextAgent1 instructionForNextAgent1 = null;
        if(instructionForNextAgent2 != null) {
            String instrInf = instructionForNextAgent2.getInstrInf();
            if (StringUtils.isNotEmpty(instrInf)) {
                instructionForNextAgent1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForNextAgent1();
                instructionForNextAgent1.setInstrInf(instrInf);

            }
        }
        return instructionForNextAgent1;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForCreditorAgent1 getInstrForCdtrAgt(InstructionForCreditorAgent1 instructionForCdtrAgent2) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForCreditorAgent1 instructionForCreditorAgent1 = null;
        if(instructionForCdtrAgent2 != null) {
            String instrInf = instructionForCdtrAgent2.getInstrInf();
            if (StringUtils.isNotEmpty(instrInf)) {
                instructionForCreditorAgent1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.InstructionForCreditorAgent1();
                instructionForCreditorAgent1.setInstrInf(instrInf);

            }
        }
        return instructionForCreditorAgent1;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 getCdtrAgt(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 cdtrAgt = null;
        if(cdtTrfTxInf != null) {
            BranchAndFinancialInstitutionIdentification5 cdtrAgt1 = cdtTrfTxInf.getCdtrAgt();
            if (cdtrAgt1 != null) {
                FinancialInstitutionIdentification8 finInstnId1 = cdtrAgt1.getFinInstnId();
                if(finInstnId1 != null){
                    iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                    String bicfi = finInstnId1.getBICFI();
                    if(StringUtils.isNotEmpty(bicfi)){
                        finInstnId.setBICFI(bicfi);
                    }
                    ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                    if(clrSysMmbId1 != null){
                        ClearingSystemIdentification2Choice clrSysId1 = clrSysMmbId1.getClrSysId();
                        iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                        if(clrSysId1 != null){
                            String cd = clrSysId1.getCd();
                            if(StringUtils.isNotEmpty(cd)){
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                    cdtrAgt = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                    cdtrAgt.setFinInstnId(finInstnId);
                }
            }
        }
        return cdtrAgt;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 getDbtrAcct(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 dbtrAcct = null;
        if(cdtTrfTxInf!= null) {
            CashAccount24 dbtrAcct1 = cdtTrfTxInf.getDbtrAcct();
            if (dbtrAcct1 != null) {
                AccountIdentification4Choice id1 = dbtrAcct1.getId();
                if (id1 != null) {
                    iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice id = new iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice();
                    GenericAccountIdentification1 othr1 = id1.getOthr();
                    if (othr1 != null) {
                        String id2 = othr1.getId();
                        if (StringUtils.isNotEmpty(id2)) {
                            dbtrAcct = new iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24();
                            iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1 othr = new iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1();
                            othr.setId(id2);
                            id.setOthr(othr);
                        }
                    }

                    String iban = id1.getIBAN();
                    if (StringUtils.isNotEmpty(iban)) {
                        if(dbtrAcct == null) {
                            dbtrAcct = new iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24();
                        }
                        id.setIBAN(iban);
                    }
                    dbtrAcct.setId(id);
                }
            }
        }

        return dbtrAcct;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 getCdtr(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 cdtr = new iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43();
        if(cdtTrfTxInf != null) {
            PartyIdentification43 cdtr1 = cdtTrfTxInf.getCdtr();
            if (cdtr1 != null) {
                String nm = cdtr1.getNm();
                if (StringUtils.isNotEmpty(nm)) {
                    cdtr.setNm(nm);
                }
                PostalAddress6 pstlAdr = cdtr1.getPstlAdr();
                if(pstlAdr != null){
                    List<String> adrLine = pstlAdr.getAdrLine();
                    if(adrLine != null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.PostalAddress6 pstlAdr1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.PostalAddress6();
                        List<String> postalAddressLines = new ArrayList<>();
                        if(pstlAdr1.getAdrLine() != null){
                            postalAddressLines = pstlAdr1.getAdrLine();
                        }
                        for (String addrLn : adrLine) {
                            postalAddressLines.add(addrLn);
                        }
                        cdtr.setPstlAdr(pstlAdr1);
                    }

                }
            }
        }
        return cdtr;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 getDbtr(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43 dbtr = new iso.std.iso._20022.tech.xsd.pacs_008_001.PartyIdentification43();
        if(cdtTrfTxInf != null) {
            PartyIdentification43 dbtr1 = cdtTrfTxInf.getDbtr();
            if (dbtr1 != null) {
                String nm = dbtr1.getNm();
                if (StringUtils.isNotEmpty(nm)) {
                    dbtr.setNm(nm);
                }
                PostalAddress6 pstlAdr = dbtr1.getPstlAdr();
                if(pstlAdr != null){
                    List<String> adrLine = pstlAdr.getAdrLine();
                    if(adrLine != null) {
                        iso.std.iso._20022.tech.xsd.pacs_008_001.PostalAddress6 pstlAdr1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.PostalAddress6();
                        List<String> postalAddressLines = new ArrayList<>();
                        if(pstlAdr1.getAdrLine() != null){
                            postalAddressLines = pstlAdr1.getAdrLine();
                        }
                        for (String addrLn : adrLine) {
                            postalAddressLines.add(addrLn);
                        }
                        dbtr.setPstlAdr(pstlAdr1);
                    }

                }
            }
        }
        return dbtr;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveCurrencyAndAmount getIntrBkSttlmAmt(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveCurrencyAndAmount intrBkSttlmAmt = null;
        if(cdtTrfTxInf != null) {
            ActiveCurrencyAndAmount intrBkSttlmAmt1 = cdtTrfTxInf.getIntrBkSttlmAmt();
            if(intrBkSttlmAmt1 != null) {
                intrBkSttlmAmt = new iso.std.iso._20022.tech.xsd.pacs_008_001.ActiveCurrencyAndAmount();
                String ccy = intrBkSttlmAmt1.getCcy();
                if(StringUtils.isNotEmpty(ccy)) {
                    intrBkSttlmAmt.setCcy(ccy);
                }
                Decimal value = intrBkSttlmAmt1.getValue();
                if(value != null) {
                    intrBkSttlmAmt.setValue(value.getValue());
                }

            }
        }
        return intrBkSttlmAmt;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21 getPmtTpInf(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21 pmtTpInf = null;
        if(cdtTrfTxInf != null) {
            iso.std.iso._20022.tech.xsd.pacs_008_001.ServiceLevel8Choice svcLvl = new iso.std.iso._20022.tech.xsd.pacs_008_001.ServiceLevel8Choice();
            PaymentTypeInformation21 pmtTpInf1 = cdtTrfTxInf.getPmtTpInf();
            if(pmtTpInf1 != null){
                String instrPrty = pmtTpInf1.getInstrPrty();
                if(StringUtils.isNotEmpty(instrPrty)){
                    pmtTpInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21();
                    pmtTpInf.setInstrPrty(Priority2Code.fromValue(instrPrty));
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
                        pmtTpInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21();
                    }
                    pmtTpInf.setSvcLvl(svcLvl);
                }

                LocalInstrument2Choice lclInstrm1 = pmtTpInf1.getLclInstrm();
                if(lclInstrm1 != null) {
                    iso.std.iso._20022.tech.xsd.pacs_008_001.LocalInstrument2Choice lclInstrm = new iso.std.iso._20022.tech.xsd.pacs_008_001.LocalInstrument2Choice();
                    String cd = lclInstrm1.getCd();
                    if(StringUtils.isNotEmpty(cd)) {
                        lclInstrm.setCd(cd);
                    }
                    String prtry = lclInstrm1.getPrtry();
                    if(StringUtils.isNotEmpty(prtry)) {
                        lclInstrm.setPrtry(prtry);
                    }
                    if(pmtTpInf == null){
                        pmtTpInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21();
                    }
                    pmtTpInf.setLclInstrm(lclInstrm);
                }
                CategoryPurpose1Choice ctgyPurp = pmtTpInf1.getCtgyPurp();
                if(ctgyPurp != null){
                    iso.std.iso._20022.tech.xsd.pacs_008_001.CategoryPurpose1Choice ctgyPurp2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.CategoryPurpose1Choice();
                    String prtry = ctgyPurp.getPrtry();
                    if(StringUtils.isNotEmpty(prtry)){
                        ctgyPurp2.setPrtry(prtry);
                    }
                    if(pmtTpInf == null){
                        pmtTpInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentTypeInformation21();
                    }
                    pmtTpInf.setCtgyPurp(ctgyPurp2);
                }
            }
        }
        return pmtTpInf;
    }

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentIdentification3 getPmtId(CreditTransferTransaction19 cdtTrfTxInf) {
        iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentIdentification3 paymentIdentification3 = new iso.std.iso._20022.tech.xsd.pacs_008_001.PaymentIdentification3();
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

    private static iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader49 getGroupHeader(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document avroMessage) throws ConversionException {

        iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader49 grpHeader = new iso.std.iso._20022.tech.xsd.pacs_008_001.GroupHeader49();
        if(avroMessage != null) {
            FIToFICustomerCreditTransferV05 fiToFICstmrCdtTrf = avroMessage.getFIToFICstmrCdtTrf();
            if (fiToFICstmrCdtTrf != null) {
                GroupHeader49 grpHdr = fiToFICstmrCdtTrf.getGrpHdr();

                if (grpHdr != null) {
                    String msgId = grpHdr.getMsgId();
                    if (StringUtils.isNotEmpty(msgId)) {
                        grpHeader.setMsgId(msgId);
                    }
                    Long creDtTm = grpHdr.getCreDtTm();
                    if(creDtTm != null) {
                        grpHeader.setCreDtTm((XMLGregorianCalendar) longToXmlCalendar(creDtTm));
                    }
                    String nbOfTxs = grpHdr.getNbOfTxs();
                    if(StringUtils.isNotEmpty(nbOfTxs)) {
                        grpHeader.setNbOfTxs(nbOfTxs);
                    }
                    SettlementInstruction1 sttlmInf1 = grpHdr.getSttlmInf();
                    if(sttlmInf1 != null) {
                        String sttlmMtd1 = sttlmInf1.getSttlmMtd();
                        iso.std.iso._20022.tech.xsd.pacs_008_001.SettlementInstruction1 sttlmInf = null;
                        if(sttlmMtd1 != null) {
                            sttlmInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.SettlementInstruction1();
                            SettlementMethod1Code sttlmMtd = SettlementMethod1Code.fromValue(sttlmMtd1);
                            sttlmInf.setSttlmMtd(sttlmMtd);
                        }
                        CashAccount24 sttlmAcct = sttlmInf1.getSttlmAcct();
                        if(sttlmAcct != null){
                            AccountIdentification4Choice id = sttlmAcct.getId();
                            if(id != null){
                                iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24 sttlmAcct1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.CashAccount24();
                                iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice id1 = new iso.std.iso._20022.tech.xsd.pacs_008_001.AccountIdentification4Choice();
                                String iban = id.getIBAN();
                                if(StringUtils.isNotEmpty(iban)) {
                                    id1.setIBAN(iban);
                                }
                                GenericAccountIdentification1 othr = id.getOthr();
                                if(othr != null){
                                    String id2 = othr.getId();
                                    if(StringUtils.isNotEmpty(id2)) {
                                        iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1 othr2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.GenericAccountIdentification1();
                                        othr2.setId(id2);
                                        id1.setOthr(othr2);
                                    }
                                }
                                if (sttlmInf == null) {
                                    sttlmInf = new iso.std.iso._20022.tech.xsd.pacs_008_001.SettlementInstruction1();
                                }
                                sttlmAcct1.setId(id1);
                                sttlmInf.setSttlmAcct(sttlmAcct1);
                            }
                        }
                        grpHeader.setSttlmInf(sttlmInf);
                    }

                    BranchAndFinancialInstitutionIdentification5 instgAgt1 = grpHdr.getInstgAgt();
                    if (instgAgt1 != null) {
                        FinancialInstitutionIdentification8 finInstnId1 = instgAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                            if(clrSysMmbId1 != null) {
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }

                                iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 instgAgt = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
                                instgAgt.setFinInstnId(finInstnId);
                                grpHeader.setInstgAgt(instgAgt);
                            }
                        }
                    }

                    BranchAndFinancialInstitutionIdentification5 instdAgt1 = grpHdr.getInstdAgt();
                    if (instdAgt1 != null) {
                        FinancialInstitutionIdentification8 finInstnId1 = instdAgt1.getFinInstnId();
                        if (finInstnId1 != null) {

                            ClearingSystemMemberIdentification2 clrSysMmbId1 = finInstnId1.getClrSysMmbId();
                            if(clrSysMmbId1 != null) {
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice clrSysId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemIdentification2Choice();
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
                                iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2 clrSysMmbId = new iso.std.iso._20022.tech.xsd.pacs_008_001.ClearingSystemMemberIdentification2();
                                clrSysMmbId.setClrSysId(clrSysId);
                                String mmbId = clrSysMmbId1.getMmbId();
                                if(StringUtils.isNotEmpty(mmbId)) {
                                    clrSysMmbId.setMmbId(mmbId);
                                }
                                iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8 finInstnId = new iso.std.iso._20022.tech.xsd.pacs_008_001.FinancialInstitutionIdentification8();
                                finInstnId.setClrSysMmbId(clrSysMmbId);
                                String bicfi = finInstnId1.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId.setBICFI(bicfi);
                                }

                                iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5 instdAgt = new iso.std.iso._20022.tech.xsd.pacs_008_001.BranchAndFinancialInstitutionIdentification5();
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
            throw new ConversionException("Custom conversion Long->XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() +">", err, Pacs008Avro2FPSTransform.class);
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
            throw new ConversionException("Custom conversion CharSequence -> XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() + ">", err, Pacs008Avro2FPSTransform.class);
        }
    }

}
