package com.orwellg.hermod.bottomline.fps.services.transform.pacs007;

import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import iso.std.iso._20022.tech.xsd.pacs_007_001.*;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pacs007FPS2AvroTransform {

    public static void transform(iso.std.iso._20022.tech.xsd.pacs_007_001.Document fpsDocument,
                                 com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.Document avroMessage
                                      )  {

        FIToFIPaymentReversalV05 fiToFIPmtRvsl = fpsDocument.getFIToFIPmtRvsl();

        if(fiToFIPmtRvsl!= null){
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FIToFIPaymentReversalV05 fiToFIPaymentReversalV05 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FIToFIPaymentReversalV05();
            GroupHeader57 grpHdr = fiToFIPmtRvsl.getGrpHdr();
            if(grpHdr != null){
                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GroupHeader57 groupHeader57 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GroupHeader57();
                String msgId = grpHdr.getMsgId();
                if(StringUtils.isNotEmpty(msgId)){
                    groupHeader57.setMsgId(msgId);
                }
                XMLGregorianCalendar creDtTm = grpHdr.getCreDtTm();
                if(creDtTm != null){
                    groupHeader57.setCreDtTm(creDtTm.toGregorianCalendar().getTimeInMillis());
                }

                String nbOfTxs = grpHdr.getNbOfTxs();
                if(StringUtils.isNotEmpty(nbOfTxs)){
                    groupHeader57.setNbOfTxs(nbOfTxs);
                }

                SettlementInstruction1 sttlmInf = grpHdr.getSttlmInf();
                if(sttlmInf != null){

                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.SettlementInstruction1 sttlmInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.SettlementInstruction1();

                    SettlementMethod1Code sttlmMtd = sttlmInf.getSttlmMtd();
                    if(sttlmMtd != null){
                        String value = sttlmMtd.value();
                        if(StringUtils.isNotEmpty(value)){
                            sttlmInf1.setSttlmMtd(value);
                        }

                    }

                    groupHeader57.setSttlmInf(sttlmInf1);
                }


                BranchAndFinancialInstitutionIdentification5 instdAgt = grpHdr.getInstdAgt();
                if(instdAgt != null){
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5 instdAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5();
                    FinancialInstitutionIdentification8 finInstnId = instdAgt.getFinInstnId();
                    if(finInstnId != null){
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8();
                        String bicfi = finInstnId.getBICFI();
                        if(StringUtils.isNotEmpty(bicfi)){
                            finInstnId1.setBICFI(bicfi);
                        }
                        ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                        if(clrSysMmbId != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2();
                            ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                            if(clrSysId != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice();
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

                        instdAgt1.setFinInstnId(finInstnId1);
                    }

                    groupHeader57.setInstdAgt(instdAgt1);
                }

                fiToFIPaymentReversalV05.setGrpHdr(groupHeader57);
            }



            List<PaymentTransaction51> txInfLst = fiToFIPmtRvsl.getTxInf();
            if(txInfLst != null){
                List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentTransaction51> txInf1Lst = new ArrayList<>();
                for(PaymentTransaction51 txInf1: txInfLst){
                    if(txInf1 != null){
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentTransaction51 txInfAndSts1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentTransaction51();

                        String rvslId = txInf1.getRvslId();
                        if(StringUtils.isNotEmpty(rvslId)){
                            txInfAndSts1.setRvslId(rvslId);
                        }

                        String orgnlTxId = txInf1.getOrgnlTxId();
                        if(StringUtils.isNotEmpty(orgnlTxId)){
                            txInfAndSts1.setOrgnlTxId(orgnlTxId);
                        }
                        String clrSysRef = txInf1.getOrgnlClrSysRef();
                        if(StringUtils.isNotEmpty(clrSysRef)){
                            txInfAndSts1.setOrgnlClrSysRef(clrSysRef);
                        }

                        ActiveCurrencyAndAmount rvsdIntrBkSttlmAmt = txInf1.getRvsdIntrBkSttlmAmt();
                        if(rvsdIntrBkSttlmAmt != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ActiveCurrencyAndAmount activeCurrencyAndAmount = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ActiveCurrencyAndAmount();

                            String ccy = rvsdIntrBkSttlmAmt.getCcy();
                            if(StringUtils.isNotEmpty(ccy)){
                                activeCurrencyAndAmount.setCcy(ccy);
                            }
                            BigDecimal value = rvsdIntrBkSttlmAmt.getValue();

                            if(value != null){
                                Decimal decimal = DecimalTypeUtils.toDecimal(value, 2);
                                activeCurrencyAndAmount.setValue(decimal);
                            }
                            txInfAndSts1.setRvsdIntrBkSttlmAmt(activeCurrencyAndAmount);

                        }

                        XMLGregorianCalendar intrBkSttlmDt1 = txInf1.getIntrBkSttlmDt();
                        if(intrBkSttlmDt1 != null){
                            txInfAndSts1.setIntrBkSttlmDt(intrBkSttlmDt1.toXMLFormat());
                        }


                        BranchAndFinancialInstitutionIdentification5 instgAgt = txInf1.getInstgAgt();
                        if(instgAgt != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5 instgAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5();
                            FinancialInstitutionIdentification8 finInstnId = instgAgt.getFinInstnId();
                            if(finInstnId != null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8();
                                String bicfi = finInstnId.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId1.setBICFI(bicfi);
                                }
                                ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                if(clrSysMmbId != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2();
                                    ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                    if(clrSysId != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice();
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

                            txInfAndSts1.setInstdAgt(instgAgt1);
                        }


                        List <PaymentReversalReason7> rvslRsnInfLst = txInf1.getRvslRsnInf();

                        if(rvslRsnInfLst != null){
                            ArrayList <com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentReversalReason7> rvslRsnInf1Lst = new ArrayList <>();
                            for(PaymentReversalReason7 rvslRsnInf: rvslRsnInfLst){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentReversalReason7 rvslRsnInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentReversalReason7();
                                ReversalReason4Choice rsn = rvslRsnInf.getRsn();
                                if(rsn!= null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ReversalReason4Choice rsn1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ReversalReason4Choice();
                                    String cd = rsn.getCd();
                                    if(StringUtils.isNotEmpty(cd)){
                                        rsn1.setCd(cd);
                                    }
                                    String prtry = rsn.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        rsn1.setPrtry(prtry);
                                    }

                                    rvslRsnInf1.setRsn(rsn1);
                                }


                                rvslRsnInf1Lst.add(rvslRsnInf1);


                            }

                            txInfAndSts1.setRvslRsnInf(rvslRsnInf1Lst);
                        }

                        OriginalTransactionReference20 orgnlTxRef = txInf1.getOrgnlTxRef();
                        if(orgnlTxRef!= null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.OriginalTransactionReference20 orgnlTxRef1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.OriginalTransactionReference20();
                            ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt = orgnlTxRef.getIntrBkSttlmAmt();
                            if(intrBkSttlmAmt!= null){
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ActiveOrHistoricCurrencyAndAmount();
                                String ccy = intrBkSttlmAmt.getCcy();
                                if(StringUtils.isNotEmpty(ccy)){
                                    intrBkSttlmAmt1.setCcy(ccy);
                                }
                                if(intrBkSttlmAmt.getValue() != null){
                                    Decimal decimal = DecimalTypeUtils.toDecimal(intrBkSttlmAmt.getValue(), 2);
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
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.SettlementInstruction1 sttlmInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.SettlementInstruction1();
                                SettlementMethod1Code sttlmMtd = sttlmInf.getSttlmMtd();
                                if(sttlmMtd != null){
                                    String sttlmMtd1 = sttlmMtd.value();
                                    if(StringUtils.isNotEmpty(sttlmMtd1)){
                                        sttlmInf1.setSttlmMtd(sttlmMtd1);
                                    }
                                }

                                CashAccount24 sttlmAcct = sttlmInf.getSttlmAcct();
                                if(sttlmAcct != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.CashAccount24 sttlmAcct1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.CashAccount24();
                                    AccountIdentification4Choice id = sttlmAcct.getId();
                                    if(id != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.AccountIdentification4Choice id1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.AccountIdentification4Choice();

                                        String iban = id.getIBAN();
                                        if(StringUtils.isNotEmpty(iban)){
                                            id1.setIBAN(iban);
                                        }
                                        GenericAccountIdentification1 othr = id.getOthr();
                                        if(othr != null){
                                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GenericAccountIdentification1 othr1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GenericAccountIdentification1();
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
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentTypeInformation25 pmtTpInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.PaymentTypeInformation25();

                                Priority2Code instrPrty = pmtTpInf.getInstrPrty();
                                if(instrPrty != null){
                                    String instrPrty1 = instrPrty.value();
                                    if(StringUtils.isNotEmpty(instrPrty1)){
                                        pmtTpInf1.setInstrPrty(instrPrty1);
                                    }
                                }

                                ServiceLevel8Choice svcLvl = pmtTpInf.getSvcLvl();
                                if(svcLvl != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ServiceLevel8Choice svcLvl1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ServiceLevel8Choice();
                                    String svcLvlCd = svcLvl.getCd();
                                    if(StringUtils.isNotEmpty(svcLvlCd)){
                                        svcLvl1.setCd(svcLvlCd);
                                    }
                                    pmtTpInf1.setSvcLvl(svcLvl1);
                                }

                                LocalInstrument2Choice lclInstrm = pmtTpInf.getLclInstrm();
                                if(lclInstrm!= null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.LocalInstrument2Choice lclInstrm1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.LocalInstrument2Choice();
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
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.RemittanceInformation10 rmtInf1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.RemittanceInformation10();

                                List<StructuredRemittanceInformation12> strdLst = rmtInf.getStrd();

                                if(strdLst != null) {
                                    List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.StructuredRemittanceInformation12> strd1 = new ArrayList<>();
                                    for (StructuredRemittanceInformation12 strd : strdLst) {
                                        List<String> addtlRmtInfList = strd.getAddtlRmtInf();
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.StructuredRemittanceInformation12 structuredRemittanceInformation12 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.StructuredRemittanceInformation12();
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
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5 cdtrAgt1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.BranchAndFinancialInstitutionIdentification5();
                                FinancialInstitutionIdentification8 finInstnId = cdtrAgt.getFinInstnId();
                                if(finInstnId != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8 finInstnId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.FinancialInstitutionIdentification8();
                                    ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                    if(clrSysMmbId != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2 clrSysMmbId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemMemberIdentification2();
                                        ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                        if(clrSysId != null){
                                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice clrSysId1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.ClearingSystemIdentification2Choice();
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
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.CashAccount24 cdtrAcct1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.CashAccount24();
                                AccountIdentification4Choice id = cdtrAcct.getId();
                                if(id != null){
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.AccountIdentification4Choice id1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.AccountIdentification4Choice();
                                    if(StringUtils.isNotEmpty(id.getIBAN())){
                                        id1.setIBAN(id.getIBAN());
                                    }
                                    cdtrAcct1.setId(id1);
                                    GenericAccountIdentification1 othr = id.getOthr();
                                    if(othr != null){
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GenericAccountIdentification1 othr1 = new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs007_001_05.GenericAccountIdentification1();
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
                        txInf1Lst.add(txInfAndSts1);
                     }
                }
                fiToFIPaymentReversalV05.setTxInf(txInf1Lst);
            }
            avroMessage.setFIToFIPmtRvsl(fiToFIPaymentReversalV05);
        }

    }
}
