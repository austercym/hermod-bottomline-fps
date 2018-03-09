package com.orwellg.hermod.bottomline.fps.services.transform.pacs002;

import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import iso.std.iso._20022.tech.xsd.pacs_002_001.*;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class Pacs002FPS2AvroTransform {

    public static void transform(iso.std.iso._20022.tech.xsd.pacs_002_001.Document fpsDocument,
                                 com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document avroMessage
                                      )  {

        FIToFIPaymentStatusReportV06 fiToFIPmtStsRpt = fpsDocument.getFIToFIPmtStsRpt();

        if(fiToFIPmtStsRpt!= null){
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FIToFIPaymentStatusReportV06 fiToFIPmtStsRpt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FIToFIPaymentStatusReportV06();
            GroupHeader53 grpHdr = fiToFIPmtStsRpt.getGrpHdr();
            if(grpHdr != null){
                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GroupHeader53 groupHeader53 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GroupHeader53();
                String msgId = grpHdr.getMsgId();
                if(StringUtils.isNotEmpty(msgId)){
                    groupHeader53.setMsgId(msgId);
                }
                XMLGregorianCalendar creDtTm = grpHdr.getCreDtTm();
                if(creDtTm != null){
                    groupHeader53.setCreDtTm(creDtTm.toGregorianCalendar().getTimeInMillis());
                }

                BranchAndFinancialInstitutionIdentification5 instgAgt = grpHdr.getInstgAgt();
                if(instgAgt != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5 instgAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5();
                    FinancialInstitutionIdentification8 finInstnId = instgAgt.getFinInstnId();
                    if(finInstnId != null){
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8();
                        String bicfi = finInstnId.getBICFI();
                        if(StringUtils.isNotEmpty(bicfi)){
                            finInstnId1.setBICFI(bicfi);
                        }
                        ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                        if(clrSysMmbId != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2();
                            ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                            if(clrSysId != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice();
                                String cd = clrSysId.getCd();
                                if(StringUtils.isNotEmpty(cd)){
                                    clrSysId1.setCd(cd);
                                }
                                String prtry = clrSysId.getPrtry();
                                if(StringUtils.isNotEmpty(prtry)){
                                    clrSysId1.setPrtry(prtry);
                                }


                                clrSysMmbId1.setClrSysId(clrSysId1);
                            }

                            String mmbId = clrSysMmbId.getMmbId();
                            if(StringUtils.isNotEmpty(mmbId)){
                                clrSysMmbId1.setMmbId(mmbId);
                            }
                            finInstnId1.setClrSysMmbId(clrSysMmbId1);
                        }

                        instgAgt1.setFinInstnId(finInstnId1);
                    }

                    groupHeader53.setInstgAgt(instgAgt1);
                }

                fiToFIPmtStsRpt1.setGrpHdr(groupHeader53);
            }

            List<PaymentTransaction52> txInfAndStsLst = fiToFIPmtStsRpt.getTxInfAndSts();
            if(txInfAndStsLst != null){
                List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTransaction52> txInfAndSts1Lst = new ArrayList<>();
                for(PaymentTransaction52 txInfAndSts: txInfAndStsLst){
                    if(txInfAndSts != null){
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTransaction52 txInfAndSts1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTransaction52();

                        String stsId = txInfAndSts.getStsId();
                        if(StringUtils.isNotEmpty(stsId)){
                            txInfAndSts1.setStsId(stsId);
                        }

                        OriginalGroupInformation3 orgnlGrpInf = txInfAndSts.getOrgnlGrpInf();
                        if(orgnlGrpInf != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalGroupInformation3 orgnlGrpInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalGroupInformation3();
                            String orgnlMsgId = orgnlGrpInf.getOrgnlMsgId();
                            if(StringUtils.isNotEmpty(orgnlMsgId)){
                                orgnlGrpInf1.setOrgnlMsgId(orgnlMsgId);
                            }
                            String orgnlMsgNmId = orgnlGrpInf.getOrgnlMsgNmId();
                            if(StringUtils.isNotEmpty(orgnlMsgNmId)){
                                orgnlGrpInf1.setOrgnlMsgNmId(orgnlMsgNmId);
                            }

                            txInfAndSts1.setOrgnlGrpInf(orgnlGrpInf1);
                        }

                        String orgnlTxId = txInfAndSts.getOrgnlTxId();
                        if(StringUtils.isNotEmpty(orgnlTxId)){
                            txInfAndSts1.setOrgnlTxId(orgnlTxId);
                        }
                        TransactionIndividualStatus3Code txSts = txInfAndSts.getTxSts();
                        if(txSts!= null){
                            String value = txSts.value();
                             if(StringUtils.isNotEmpty(value)){
                                txInfAndSts1.setTxSts(value);
                            }
                        }

                        List<StatusReasonInformation9> stsRsnInfLst = txInfAndSts.getStsRsnInf();
                        if(stsRsnInfLst != null){
                            List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReasonInformation9> stsRsnInfLst1 = new ArrayList<>();
                            for (StatusReasonInformation9 stsRsnInf:stsRsnInfLst) {
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReasonInformation9 stsRsnInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReasonInformation9();
                                StatusReason6Choice rsn = stsRsnInf.getRsn();
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReason6Choice value = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReason6Choice();
                                if(rsn != null) {
                                    String prtry = rsn.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)) {
                                        value.setPrtry(prtry);
                                    }
                                    String cd = rsn.getCd();
                                    if(StringUtils.isNotEmpty(cd)) {
                                        value.setCd(cd);
                                    }
                                    stsRsnInf1.setRsn(value);
                                }
                                List<String> addtlInfLst = stsRsnInf.getAddtlInf();
                                if(addtlInfLst != null){
                                    List<String> addtlInf1 = new ArrayList<>();
                                    for (String addtlInf: addtlInfLst) {
                                        if(StringUtils.isNotEmpty(addtlInf)){
                                            addtlInf1.add(addtlInf);
                                        }
                                    }
                                    stsRsnInf1.setAddtlInf(addtlInf1);
                                }
                                stsRsnInfLst1.add(stsRsnInf1);
                            }
                            txInfAndSts1.setStsRsnInf(stsRsnInfLst1);
                        }

                        XMLGregorianCalendar accptncDtTm = txInfAndSts.getAccptncDtTm();
                        if(accptncDtTm != null){
                            txInfAndSts1.setAccptncDtTm(accptncDtTm.toGregorianCalendar().getTimeInMillis());
                        }

                        String clrSysRef = txInfAndSts.getClrSysRef();
                        if(StringUtils.isNotEmpty(clrSysRef)){
                            txInfAndSts1.setClrSysRef(clrSysRef);
                        }
                        BranchAndFinancialInstitutionIdentification5 instdAgt = txInfAndSts.getInstdAgt();

                        if(instdAgt != null){
                            FinancialInstitutionIdentification8 finInstnId = instdAgt.getFinInstnId();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5 instdAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5();
                            if(finInstnId!= null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8();
                                String bicfi = finInstnId.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId1.setBICFI(bicfi);
                                }
                                ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                if(clrSysMmbId != null) {
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2();
                                    ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                    if(clrSysId != null){
                                        String cd = clrSysId.getCd();
                                        if(StringUtils.isNotEmpty(cd)){
                                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice();
                                            clrSysId1.setCd(cd);
                                            clrSysMmbId1.setClrSysId(clrSysId1);
                                        }
                                    }
                                    String mmbId = clrSysMmbId.getMmbId();
                                    if(StringUtils.isNotEmpty(mmbId)){
                                        clrSysMmbId1.setMmbId(mmbId);
                                    }

                                    finInstnId1.setClrSysMmbId(clrSysMmbId1);
                                }

                                instdAgt1.setFinInstnId(finInstnId1);
                            }

                            txInfAndSts1.setInstdAgt(instdAgt1);
                        }

                        OriginalTransactionReference20 orgnlTxRef = txInfAndSts.getOrgnlTxRef();
                        if(orgnlTxRef!= null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalTransactionReference20 orgnlTxRef1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalTransactionReference20();
                            ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt = orgnlTxRef.getIntrBkSttlmAmt();
                            if(intrBkSttlmAmt!= null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ActiveOrHistoricCurrencyAndAmount();
                                String ccy = intrBkSttlmAmt.getCcy();
                                if(StringUtils.isNotEmpty(ccy)){
                                    intrBkSttlmAmt1.setCcy(ccy);
                                }
                                if(intrBkSttlmAmt.getValue() != null){
                                    Decimal decimal = DecimalTypeUtils.toDecimal(intrBkSttlmAmt.getValue());
                                    intrBkSttlmAmt1.setValue(decimal);
                                }
                                orgnlTxRef1.setIntrBkSttlmAmt(intrBkSttlmAmt1);

                            }
                            XMLGregorianCalendar intrBkSttlmDt = orgnlTxRef.getIntrBkSttlmDt();
                            if(intrBkSttlmDt != null){
                                orgnlTxRef1.setIntrBkSttlmDt((intrBkSttlmDt).toXMLFormat());
                            }

                            SettlementInstruction1 sttlmInf = orgnlTxRef.getSttlmInf();
                            if(sttlmInf != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.SettlementInstruction1 sttlmInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.SettlementInstruction1();
                                SettlementMethod1Code sttlmMtd = sttlmInf.getSttlmMtd();
                                if(sttlmMtd != null){
                                    String sttlmMtd1 = sttlmMtd.value();
                                    if(StringUtils.isNotEmpty(sttlmMtd1)){
                                        sttlmInf1.setSttlmMtd(sttlmMtd1);
                                    }
                                }

                                CashAccount24 sttlmAcct = sttlmInf.getSttlmAcct();
                                if(sttlmAcct != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24 sttlmAcct1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24();
                                    AccountIdentification4Choice id = sttlmAcct.getId();
                                    if(id != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice id1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice();

                                        String iban = id.getIBAN();
                                        if(StringUtils.isNotEmpty(iban)){
                                            id1.setIBAN(iban);
                                        }
                                        GenericAccountIdentification1 othr = id.getOthr();
                                        if(othr != null){
                                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1 othr1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1();
                                            String id2 = othr.getId();
                                            if(StringUtils.isNotEmpty(id2)){
                                                othr1.setId(id2);
                                            }
                                            id1.setOthr(othr1);
                                        }
                                        sttlmAcct1.setId(id1);
                                    }

                                    sttlmInf1.setSttlmAcct(sttlmAcct1);
                                }


                                orgnlTxRef1.setSttlmInf(sttlmInf1);
                            }

                            PaymentTypeInformation25 pmtTpInf = orgnlTxRef.getPmtTpInf();
                            if(pmtTpInf != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTypeInformation25 pmtTpInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTypeInformation25();

                                Priority2Code instrPrty = pmtTpInf.getInstrPrty();
                                if(instrPrty != null){
                                    String instrPrty1 = instrPrty.value();
                                    if(StringUtils.isNotEmpty(instrPrty1)){
                                        pmtTpInf1.setInstrPrty(instrPrty1);
                                    }
                                }

                                ServiceLevel8Choice svcLvl = pmtTpInf.getSvcLvl();
                                if(svcLvl != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ServiceLevel8Choice svcLvl1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ServiceLevel8Choice();
                                    String svcLvlCd = svcLvl.getCd();
                                    if(StringUtils.isNotEmpty(svcLvlCd)){
                                        svcLvl1.setCd(svcLvlCd);
                                    }
                                    pmtTpInf1.setSvcLvl(svcLvl1);
                                }

                                LocalInstrument2Choice lclInstrm = pmtTpInf.getLclInstrm();
                                if(lclInstrm!= null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.LocalInstrument2Choice lclInstrm1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.LocalInstrument2Choice();
                                    String prtry = lclInstrm.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        lclInstrm1.setPrtry(prtry);
                                    }
                                    pmtTpInf1.setLclInstrm(lclInstrm1);
                                }

                                orgnlTxRef1.setPmtTpInf(pmtTpInf1);
                            }

                            RemittanceInformation10 rmtInf = orgnlTxRef.getRmtInf();
                            if(rmtInf != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.RemittanceInformation10 rmtInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.RemittanceInformation10();

                                List<StructuredRemittanceInformation12> strdLst = rmtInf.getStrd();

                                if(strdLst != null) {
                                    List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StructuredRemittanceInformation12> strd1 = new ArrayList<>();
                                    for (StructuredRemittanceInformation12 strd : strdLst) {
                                        List<String> addtlRmtInfList = strd.getAddtlRmtInf();
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StructuredRemittanceInformation12 structuredRemittanceInformation12 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StructuredRemittanceInformation12();
                                        if (addtlRmtInfList != null) {
                                            List<String> addtlRmtInfLst1 = new ArrayList<>();
                                            for (String addtlRmtInf : addtlRmtInfList) {
                                                if (StringUtils.isNotEmpty(addtlRmtInf)) {
                                                    addtlRmtInfLst1.add(addtlRmtInf);
                                                }
                                            }
                                            structuredRemittanceInformation12.setAddtlRmtInf(addtlRmtInfLst1);
                                        }
                                        strd1.add(structuredRemittanceInformation12);
                                    }
                                    rmtInf1.setStrd(strd1);
                                }
                                orgnlTxRef1.setRmtInf(rmtInf1);
                            }

                            BranchAndFinancialInstitutionIdentification5 cdtrAgt = orgnlTxRef.getCdtrAgt();

                            if(cdtrAgt != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5 cdtrAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5();
                                FinancialInstitutionIdentification8 finInstnId = cdtrAgt.getFinInstnId();
                                if(finInstnId != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8();
                                    ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                    if(clrSysMmbId != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2();
                                        ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                        if(clrSysId != null){
                                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice();
                                            if(StringUtils.isNotEmpty(clrSysId.getCd())){
                                                clrSysId1.setCd(clrSysId.getCd());
                                            }
                                            clrSysMmbId1.setClrSysId(clrSysId1);
                                        }
                                        String mmbId = clrSysMmbId.getMmbId();
                                        if(StringUtils.isNotEmpty(mmbId)){
                                            clrSysMmbId1.setMmbId(mmbId);
                                        }
                                        finInstnId1.setClrSysMmbId(clrSysMmbId1);
                                    }

                                    cdtrAgt1.setFinInstnId(finInstnId1);
                                }

                                orgnlTxRef1.setCdtrAgt(cdtrAgt1);
                            }

                            CashAccount24 cdtrAcct = orgnlTxRef.getCdtrAcct();

                            if(cdtrAcct != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24 cdtrAcct1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24();
                                AccountIdentification4Choice id = cdtrAcct.getId();
                                if(id != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice id1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice();
                                    if(StringUtils.isNotEmpty(id.getIBAN())){
                                        id1.setIBAN(id.getIBAN());
                                    }
                                    cdtrAcct1.setId(id1);
                                    GenericAccountIdentification1 othr = id.getOthr();
                                    if(othr != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1 othr1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1();
                                        String id2 = othr.getId();
                                        if(StringUtils.isNotEmpty(id2)){
                                            othr1.setId(id2);
                                        }
                                        id1.setOthr(othr1);
                                    }
                                }
                                orgnlTxRef1.setCdtrAcct(cdtrAcct1);
                            }
                            txInfAndSts1.setOrgnlTxRef(orgnlTxRef1);
                        }
                        txInfAndSts1Lst.add(txInfAndSts1);
                     }
                }
                fiToFIPmtStsRpt1.setTxInfAndSts(txInfAndSts1Lst);
            }
            avroMessage.setFIToFIPmtStsRpt(fiToFIPmtStsRpt1);
        }

    }
}
