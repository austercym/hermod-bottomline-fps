package com.orwellg.hermod.bottomline.fps.services.transform.pacs002;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.pacs008.Pacs008Avro2FPSTransform;
import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Pacs002Avro2FPSTransform {

    public static void transform( com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document avroDocument,
                                  iso.std.iso._20022.tech.xsd.pacs_002_001.Document fpsMessage)  throws ConversionException{

        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FIToFIPaymentStatusReportV06 fiToFIPmtStsRpt = avroDocument.getFIToFIPmtStsRpt();

        if(fiToFIPmtStsRpt!= null){
            iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV06 fiToFIPmtStsRpt1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV06();
            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GroupHeader53 grpHdr = fiToFIPmtStsRpt.getGrpHdr();
            if(grpHdr != null){
                iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader53 groupHeader53 = new iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader53();
                String msgId = grpHdr.getMsgId();
                if(StringUtils.isNotEmpty(msgId)){
                    groupHeader53.setMsgId(msgId);
                }
                Long creDtTm = grpHdr.getCreDtTm();
                if(creDtTm != null){
                    groupHeader53.setCreDtTm((XMLGregorianCalendar)longToXmlCalendar(creDtTm));
                }

                BranchAndFinancialInstitutionIdentification5 instgAgt = grpHdr.getInstgAgt();
                if(instgAgt != null){
                    iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5 instgAgt1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5();
                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8 finInstnId = instgAgt.getFinInstnId();
                    if(finInstnId != null){
                        iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8 finInstnId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8();
                        String bicfi = finInstnId.getBICFI();
                        if(StringUtils.isNotEmpty(bicfi)){
                            finInstnId1.setBICFI(bicfi);
                        }
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                        if(clrSysMmbId != null){
                            iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                            if(clrSysId != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice clrSysId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice();
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

            List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTransaction52> txInfAndStsLst = fiToFIPmtStsRpt.getTxInfAndSts();
            if(txInfAndStsLst != null){
                List<iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction52> txInfAndSts1Lst = new ArrayList<>();
                for(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTransaction52 txInfAndSts: txInfAndStsLst){
                    if(txInfAndSts != null){
                        iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction52 txInfAndSts1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction52();

                        String stsId = txInfAndSts.getStsId();
                        if(StringUtils.isNotEmpty(stsId)){
                            txInfAndSts1.setStsId(stsId);
                        }

                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalGroupInformation3 orgnlGrpInf = txInfAndSts.getOrgnlGrpInf();
                        if(orgnlGrpInf != null){
                            iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalGroupInformation3 orgnlGrpInf1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalGroupInformation3();
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
                        String txSts = txInfAndSts.getTxSts();
                        if(StringUtils.isNotEmpty(txSts)){
                            txInfAndSts1.setTxSts(iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionIndividualStatus3Code.fromValue(txSts));
                        }


                        List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReasonInformation9> stsRsnInfLst = txInfAndSts.getStsRsnInf();
                        if(stsRsnInfLst != null){
                            for (com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReasonInformation9 stsRsnInf: stsRsnInfLst) {
                                iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9 stsRsnInf1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9();
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StatusReason6Choice rsn = stsRsnInf.getRsn();
                                iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReason6Choice value = new iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReason6Choice();
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
                                txInfAndSts1.getStsRsnInf().add(stsRsnInf1);
                            }
                        }


                        String clrSysRef = txInfAndSts.getClrSysRef();
                        if(StringUtils.isNotEmpty(clrSysRef)){
                            txInfAndSts1.setClrSysRef(clrSysRef);
                        }
                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.BranchAndFinancialInstitutionIdentification5 instdAgt = txInfAndSts.getInstdAgt();

                        if(instdAgt != null){
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.FinancialInstitutionIdentification8 finInstnId = instdAgt.getFinInstnId();
                            iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5 instdAgt1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5();
                            if(finInstnId!= null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8 finInstnId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8();
                                String bicfi = finInstnId.getBICFI();
                                if(StringUtils.isNotEmpty(bicfi)){
                                    finInstnId1.setBICFI(bicfi);
                                }
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                if(clrSysMmbId != null) {
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2();
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                    if(clrSysId != null){
                                        String cd = clrSysId.getCd();
                                        if(StringUtils.isNotEmpty(cd)){
                                            iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice clrSysId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice();
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

                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.OriginalTransactionReference20 orgnlTxRef = txInfAndSts.getOrgnlTxRef();
                        if(orgnlTxRef!= null){
                            iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalTransactionReference20 orgnlTxRef1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalTransactionReference20();
                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt = orgnlTxRef.getIntrBkSttlmAmt();
                            if(intrBkSttlmAmt!= null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ActiveOrHistoricCurrencyAndAmount();
                                String ccy = intrBkSttlmAmt.getCcy();
                                if(StringUtils.isNotEmpty(ccy)){
                                    intrBkSttlmAmt1.setCcy(ccy);
                                }
                                Decimal valueDecimal = intrBkSttlmAmt.getValue();
                                if(valueDecimal != null){
                                    intrBkSttlmAmt1.setValue((valueDecimal).getValue());
                                }
                                orgnlTxRef1.setIntrBkSttlmAmt(intrBkSttlmAmt1);

                            }
                            String intrBkSttlmDt = orgnlTxRef.getIntrBkSttlmDt();
                            if(intrBkSttlmDt != null){
                                orgnlTxRef1.setIntrBkSttlmDt((XMLGregorianCalendar) charSequenceToXmlCalendar(intrBkSttlmDt));
                            }

                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.SettlementInstruction1 sttlmInf = orgnlTxRef.getSttlmInf();
                            if(sttlmInf != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.SettlementInstruction1 sttlmInf1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.SettlementInstruction1();
                                String sttlmMtd = sttlmInf.getSttlmMtd();
                                if(sttlmMtd != null){
                                    if(StringUtils.isNotEmpty(sttlmMtd)){
                                        sttlmInf1.setSttlmMtd(iso.std.iso._20022.tech.xsd.pacs_002_001.SettlementMethod1Code.fromValue(sttlmMtd));
                                    }
                                }

                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24 sttlmAcct = sttlmInf.getSttlmAcct();
                                if(sttlmAcct != null){
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.CashAccount24 sttlmAcct1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.CashAccount24();
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice id = sttlmAcct.getId();
                                    if(id != null){
                                        iso.std.iso._20022.tech.xsd.pacs_002_001.AccountIdentification4Choice id1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.AccountIdentification4Choice();

                                        String iban = id.getIBAN();
                                        if(StringUtils.isNotEmpty(iban)){
                                            id1.setIBAN(iban);
                                        }
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1 othr = id.getOthr();
                                        if(othr != null){
                                            iso.std.iso._20022.tech.xsd.pacs_002_001.GenericAccountIdentification1 othr1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.GenericAccountIdentification1();
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

                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.PaymentTypeInformation25 pmtTpInf = orgnlTxRef.getPmtTpInf();
                            if(pmtTpInf != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTypeInformation25 pmtTpInf1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTypeInformation25();

                                String instrPrty = pmtTpInf.getInstrPrty();
                                if(instrPrty != null){
                                    if(StringUtils.isNotEmpty(instrPrty)){
                                        pmtTpInf1.setInstrPrty(iso.std.iso._20022.tech.xsd.pacs_002_001.Priority2Code.fromValue(instrPrty));
                                    }
                                }

                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ServiceLevel8Choice svcLvl = pmtTpInf.getSvcLvl();
                                if(svcLvl != null){
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.ServiceLevel8Choice svcLvl1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ServiceLevel8Choice();
                                    String svcLvlCd = svcLvl.getCd();
                                    if(StringUtils.isNotEmpty(svcLvlCd)){
                                        svcLvl1.setCd(svcLvlCd);
                                    }
                                    pmtTpInf1.setSvcLvl(svcLvl1);
                                }

                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.LocalInstrument2Choice lclInstrm = pmtTpInf.getLclInstrm();
                                if(lclInstrm!= null){
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.LocalInstrument2Choice lclInstrm1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.LocalInstrument2Choice();
                                    String prtry = lclInstrm.getPrtry();
                                    if(StringUtils.isNotEmpty(prtry)){
                                        lclInstrm1.setPrtry(prtry);
                                    }
                                    pmtTpInf1.setLclInstrm(lclInstrm1);
                                }

                                orgnlTxRef1.setPmtTpInf(pmtTpInf1);
                            }

                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.RemittanceInformation10 rmtInf = orgnlTxRef.getRmtInf();
                            if(rmtInf != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.RemittanceInformation10 rmtInf1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.RemittanceInformation10();

                                List<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StructuredRemittanceInformation12> strdLst = rmtInf.getStrd();

                                if(strdLst != null) {
                                    for (com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.StructuredRemittanceInformation12 strd : strdLst) {
                                        List<String> addtlRmtInfList = strd.getAddtlRmtInf();
                                        iso.std.iso._20022.tech.xsd.pacs_002_001.StructuredRemittanceInformation12 structuredRemittanceInformation12 = new iso.std.iso._20022.tech.xsd.pacs_002_001.StructuredRemittanceInformation12();
                                        if (addtlRmtInfList != null) {
                                            for (String addtlRmtInf : addtlRmtInfList) {
                                                if (StringUtils.isNotEmpty(addtlRmtInf)) {
                                                    structuredRemittanceInformation12.getAddtlRmtInf().add(addtlRmtInf);
                                                }
                                            }
                                        }
                                        rmtInf1.getStrd().add(structuredRemittanceInformation12);
                                    }
                                }
                                orgnlTxRef1.setRmtInf(rmtInf1);
                            }


                            BranchAndFinancialInstitutionIdentification5 cdtrAgt = orgnlTxRef.getCdtrAgt();

                            if(cdtrAgt != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5 cdtrAgt1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5();
                                FinancialInstitutionIdentification8 finInstnId = cdtrAgt.getFinInstnId();
                                if(finInstnId != null){
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8 finInstnId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8();
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemMemberIdentification2 clrSysMmbId = finInstnId.getClrSysMmbId();
                                    if(clrSysMmbId != null){
                                        iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2 clrSysMmbId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2();
                                        com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.ClearingSystemIdentification2Choice clrSysId = clrSysMmbId.getClrSysId();
                                        if(clrSysId != null){
                                            iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice clrSysId1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemIdentification2Choice();
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

                            com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.CashAccount24 cdtrAcct = orgnlTxRef.getCdtrAcct();

                            if(cdtrAcct != null){
                                iso.std.iso._20022.tech.xsd.pacs_002_001.CashAccount24 cdtrAcct1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.CashAccount24();
                                com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.AccountIdentification4Choice id = cdtrAcct.getId();
                                if(id != null){
                                    iso.std.iso._20022.tech.xsd.pacs_002_001.AccountIdentification4Choice id1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.AccountIdentification4Choice();
                                    String iban = id.getIBAN();
                                    if(StringUtils.isNotEmpty(iban)){
                                        id1.setIBAN(iban);
                                    }
                                    com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.GenericAccountIdentification1 othr = id.getOthr();
                                    if(othr != null){
                                        iso.std.iso._20022.tech.xsd.pacs_002_001.GenericAccountIdentification1 othr1 = new iso.std.iso._20022.tech.xsd.pacs_002_001.GenericAccountIdentification1();
                                        String id2 = othr.getId();
                                        if(StringUtils.isNotEmpty(id2)){
                                            othr1.setId(id2);
                                        }
                                        id1.setOthr(othr1);
                                    }
                                    cdtrAcct1.setId(id1);
                                }
                                orgnlTxRef1.setCdtrAcct(cdtrAcct1);
                            }
                            txInfAndSts1.setOrgnlTxRef(orgnlTxRef1);
                        }
                        fiToFIPmtStsRpt1.getTxInfAndSts().add(txInfAndSts1);
                     }
                }

            }
            fpsMessage.setFIToFIPmtStsRpt(fiToFIPmtStsRpt1);
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
}
