//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.08 at 01:45:10 PM CET 
//


package iso.std.iso._20022.tech.xsd.pacs_009_001;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CreditTransferTransaction17 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditTransferTransaction17"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PmtId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}PaymentIdentification3"/&gt;
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}PaymentTypeInformation21" minOccurs="0"/&gt;
 *         &lt;element name="IntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}ActiveCurrencyAndAmount"/&gt;
 *         &lt;element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="SttlmPrty" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Priority3Code" minOccurs="0"/&gt;
 *         &lt;element name="SttlmTmIndctn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}SettlementDateTimeIndication1" minOccurs="0"/&gt;
 *         &lt;element name="SttlmTmReq" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}SettlementTimeRequest2" minOccurs="0"/&gt;
 *         &lt;element name="PrvsInstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="PrvsInstgAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt1" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt1Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt2" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt2Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt3" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="IntrmyAgt3Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5"/&gt;
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="CdtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5"/&gt;
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstrForCdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}InstructionForCreditorAgent2" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="InstrForNxtAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}InstructionForNextAgent1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}RemittanceInformation2" minOccurs="0"/&gt;
 *         &lt;element name="UndrlygCstmrCdtTrf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CreditTransferTransaction18" minOccurs="0"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditTransferTransaction17", propOrder = {
    "pmtId",
    "pmtTpInf",
    "intrBkSttlmAmt",
    "intrBkSttlmDt",
    "sttlmPrty",
    "sttlmTmIndctn",
    "sttlmTmReq",
    "prvsInstgAgt",
    "prvsInstgAgtAcct",
    "instgAgt",
    "instdAgt",
    "intrmyAgt1",
    "intrmyAgt1Acct",
    "intrmyAgt2",
    "intrmyAgt2Acct",
    "intrmyAgt3",
    "intrmyAgt3Acct",
    "ultmtDbtr",
    "dbtr",
    "dbtrAcct",
    "dbtrAgt",
    "dbtrAgtAcct",
    "cdtrAgt",
    "cdtrAgtAcct",
    "cdtr",
    "cdtrAcct",
    "ultmtCdtr",
    "instrForCdtrAgt",
    "instrForNxtAgt",
    "rmtInf",
    "undrlygCstmrCdtTrf",
    "splmtryData"
})
public class CreditTransferTransaction17
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PmtId", required = true)
    protected PaymentIdentification3 pmtId;
    @XmlElement(name = "PmtTpInf")
    protected PaymentTypeInformation21 pmtTpInf;
    @XmlElement(name = "IntrBkSttlmAmt", required = true)
    protected ActiveCurrencyAndAmount intrBkSttlmAmt;
    @XmlElement(name = "IntrBkSttlmDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    @XmlElement(name = "SttlmPrty")
    @XmlSchemaType(name = "string")
    protected Priority3Code sttlmPrty;
    @XmlElement(name = "SttlmTmIndctn")
    protected SettlementDateTimeIndication1 sttlmTmIndctn;
    @XmlElement(name = "SttlmTmReq")
    protected SettlementTimeRequest2 sttlmTmReq;
    @XmlElement(name = "PrvsInstgAgt")
    protected BranchAndFinancialInstitutionIdentification5 prvsInstgAgt;
    @XmlElement(name = "PrvsInstgAgtAcct")
    protected CashAccount24 prvsInstgAgtAcct;
    @XmlElement(name = "InstgAgt")
    protected BranchAndFinancialInstitutionIdentification5 instgAgt;
    @XmlElement(name = "InstdAgt")
    protected BranchAndFinancialInstitutionIdentification5 instdAgt;
    @XmlElement(name = "IntrmyAgt1")
    protected BranchAndFinancialInstitutionIdentification5 intrmyAgt1;
    @XmlElement(name = "IntrmyAgt1Acct")
    protected CashAccount24 intrmyAgt1Acct;
    @XmlElement(name = "IntrmyAgt2")
    protected BranchAndFinancialInstitutionIdentification5 intrmyAgt2;
    @XmlElement(name = "IntrmyAgt2Acct")
    protected CashAccount24 intrmyAgt2Acct;
    @XmlElement(name = "IntrmyAgt3")
    protected BranchAndFinancialInstitutionIdentification5 intrmyAgt3;
    @XmlElement(name = "IntrmyAgt3Acct")
    protected CashAccount24 intrmyAgt3Acct;
    @XmlElement(name = "UltmtDbtr")
    protected BranchAndFinancialInstitutionIdentification5 ultmtDbtr;
    @XmlElement(name = "Dbtr", required = true)
    protected BranchAndFinancialInstitutionIdentification5 dbtr;
    @XmlElement(name = "DbtrAcct")
    protected CashAccount24 dbtrAcct;
    @XmlElement(name = "DbtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 dbtrAgt;
    @XmlElement(name = "DbtrAgtAcct")
    protected CashAccount24 dbtrAgtAcct;
    @XmlElement(name = "CdtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 cdtrAgt;
    @XmlElement(name = "CdtrAgtAcct")
    protected CashAccount24 cdtrAgtAcct;
    @XmlElement(name = "Cdtr", required = true)
    protected BranchAndFinancialInstitutionIdentification5 cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CashAccount24 cdtrAcct;
    @XmlElement(name = "UltmtCdtr")
    protected BranchAndFinancialInstitutionIdentification5 ultmtCdtr;
    @XmlElement(name = "InstrForCdtrAgt")
    protected List<InstructionForCreditorAgent2> instrForCdtrAgt;
    @XmlElement(name = "InstrForNxtAgt")
    protected List<InstructionForNextAgent1> instrForNxtAgt;
    @XmlElement(name = "RmtInf")
    protected RemittanceInformation2 rmtInf;
    @XmlElement(name = "UndrlygCstmrCdtTrf")
    protected CreditTransferTransaction18 undrlygCstmrCdtTrf;
    @XmlElement(name = "SplmtryData")
    protected List<SupplementaryData1> splmtryData;

    /**
     * Gets the value of the pmtId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdentification3 }
     *     
     */
    public PaymentIdentification3 getPmtId() {
        return pmtId;
    }

    /**
     * Sets the value of the pmtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdentification3 }
     *     
     */
    public void setPmtId(PaymentIdentification3 value) {
        this.pmtId = value;
    }

    /**
     * Gets the value of the pmtTpInf property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTypeInformation21 }
     *     
     */
    public PaymentTypeInformation21 getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTypeInformation21 }
     *     
     */
    public void setPmtTpInf(PaymentTypeInformation21 value) {
        this.pmtTpInf = value;
    }

    /**
     * Gets the value of the intrBkSttlmAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public ActiveCurrencyAndAmount getIntrBkSttlmAmt() {
        return intrBkSttlmAmt;
    }

    /**
     * Sets the value of the intrBkSttlmAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public void setIntrBkSttlmAmt(ActiveCurrencyAndAmount value) {
        this.intrBkSttlmAmt = value;
    }

    /**
     * Gets the value of the intrBkSttlmDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIntrBkSttlmDt() {
        return intrBkSttlmDt;
    }

    /**
     * Sets the value of the intrBkSttlmDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIntrBkSttlmDt(XMLGregorianCalendar value) {
        this.intrBkSttlmDt = value;
    }

    /**
     * Gets the value of the sttlmPrty property.
     * 
     * @return
     *     possible object is
     *     {@link Priority3Code }
     *     
     */
    public Priority3Code getSttlmPrty() {
        return sttlmPrty;
    }

    /**
     * Sets the value of the sttlmPrty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority3Code }
     *     
     */
    public void setSttlmPrty(Priority3Code value) {
        this.sttlmPrty = value;
    }

    /**
     * Gets the value of the sttlmTmIndctn property.
     * 
     * @return
     *     possible object is
     *     {@link SettlementDateTimeIndication1 }
     *     
     */
    public SettlementDateTimeIndication1 getSttlmTmIndctn() {
        return sttlmTmIndctn;
    }

    /**
     * Sets the value of the sttlmTmIndctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementDateTimeIndication1 }
     *     
     */
    public void setSttlmTmIndctn(SettlementDateTimeIndication1 value) {
        this.sttlmTmIndctn = value;
    }

    /**
     * Gets the value of the sttlmTmReq property.
     * 
     * @return
     *     possible object is
     *     {@link SettlementTimeRequest2 }
     *     
     */
    public SettlementTimeRequest2 getSttlmTmReq() {
        return sttlmTmReq;
    }

    /**
     * Sets the value of the sttlmTmReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementTimeRequest2 }
     *     
     */
    public void setSttlmTmReq(SettlementTimeRequest2 value) {
        this.sttlmTmReq = value;
    }

    /**
     * Gets the value of the prvsInstgAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getPrvsInstgAgt() {
        return prvsInstgAgt;
    }

    /**
     * Sets the value of the prvsInstgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setPrvsInstgAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.prvsInstgAgt = value;
    }

    /**
     * Gets the value of the prvsInstgAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getPrvsInstgAgtAcct() {
        return prvsInstgAgtAcct;
    }

    /**
     * Sets the value of the prvsInstgAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setPrvsInstgAgtAcct(CashAccount24 value) {
        this.prvsInstgAgtAcct = value;
    }

    /**
     * Gets the value of the instgAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstgAgt() {
        return instgAgt;
    }

    /**
     * Sets the value of the instgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstgAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instgAgt = value;
    }

    /**
     * Gets the value of the instdAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstdAgt() {
        return instdAgt;
    }

    /**
     * Sets the value of the instdAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstdAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instdAgt = value;
    }

    /**
     * Gets the value of the intrmyAgt1 property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getIntrmyAgt1() {
        return intrmyAgt1;
    }

    /**
     * Sets the value of the intrmyAgt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setIntrmyAgt1(BranchAndFinancialInstitutionIdentification5 value) {
        this.intrmyAgt1 = value;
    }

    /**
     * Gets the value of the intrmyAgt1Acct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getIntrmyAgt1Acct() {
        return intrmyAgt1Acct;
    }

    /**
     * Sets the value of the intrmyAgt1Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setIntrmyAgt1Acct(CashAccount24 value) {
        this.intrmyAgt1Acct = value;
    }

    /**
     * Gets the value of the intrmyAgt2 property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getIntrmyAgt2() {
        return intrmyAgt2;
    }

    /**
     * Sets the value of the intrmyAgt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setIntrmyAgt2(BranchAndFinancialInstitutionIdentification5 value) {
        this.intrmyAgt2 = value;
    }

    /**
     * Gets the value of the intrmyAgt2Acct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getIntrmyAgt2Acct() {
        return intrmyAgt2Acct;
    }

    /**
     * Sets the value of the intrmyAgt2Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setIntrmyAgt2Acct(CashAccount24 value) {
        this.intrmyAgt2Acct = value;
    }

    /**
     * Gets the value of the intrmyAgt3 property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getIntrmyAgt3() {
        return intrmyAgt3;
    }

    /**
     * Sets the value of the intrmyAgt3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setIntrmyAgt3(BranchAndFinancialInstitutionIdentification5 value) {
        this.intrmyAgt3 = value;
    }

    /**
     * Gets the value of the intrmyAgt3Acct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getIntrmyAgt3Acct() {
        return intrmyAgt3Acct;
    }

    /**
     * Sets the value of the intrmyAgt3Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setIntrmyAgt3Acct(CashAccount24 value) {
        this.intrmyAgt3Acct = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setUltmtDbtr(BranchAndFinancialInstitutionIdentification5 value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the dbtr property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setDbtr(BranchAndFinancialInstitutionIdentification5 value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setDbtrAcct(CashAccount24 value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the dbtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.dbtrAgt = value;
    }

    /**
     * Gets the value of the dbtrAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getDbtrAgtAcct() {
        return dbtrAgtAcct;
    }

    /**
     * Sets the value of the dbtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setDbtrAgtAcct(CashAccount24 value) {
        this.dbtrAgtAcct = value;
    }

    /**
     * Gets the value of the cdtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.cdtrAgt = value;
    }

    /**
     * Gets the value of the cdtrAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getCdtrAgtAcct() {
        return cdtrAgtAcct;
    }

    /**
     * Sets the value of the cdtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setCdtrAgtAcct(CashAccount24 value) {
        this.cdtrAgtAcct = value;
    }

    /**
     * Gets the value of the cdtr property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setCdtr(BranchAndFinancialInstitutionIdentification5 value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setCdtrAcct(CashAccount24 value) {
        this.cdtrAcct = value;
    }

    /**
     * Gets the value of the ultmtCdtr property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getUltmtCdtr() {
        return ultmtCdtr;
    }

    /**
     * Sets the value of the ultmtCdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setUltmtCdtr(BranchAndFinancialInstitutionIdentification5 value) {
        this.ultmtCdtr = value;
    }

    /**
     * Gets the value of the instrForCdtrAgt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrForCdtrAgt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstrForCdtrAgt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionForCreditorAgent2 }
     * 
     * 
     */
    public List<InstructionForCreditorAgent2> getInstrForCdtrAgt() {
        if (instrForCdtrAgt == null) {
            instrForCdtrAgt = new ArrayList<InstructionForCreditorAgent2>();
        }
        return this.instrForCdtrAgt;
    }

    /**
     * Gets the value of the instrForNxtAgt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrForNxtAgt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstrForNxtAgt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionForNextAgent1 }
     * 
     * 
     */
    public List<InstructionForNextAgent1> getInstrForNxtAgt() {
        if (instrForNxtAgt == null) {
            instrForNxtAgt = new ArrayList<InstructionForNextAgent1>();
        }
        return this.instrForNxtAgt;
    }

    /**
     * Gets the value of the rmtInf property.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation2 }
     *     
     */
    public RemittanceInformation2 getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation2 }
     *     
     */
    public void setRmtInf(RemittanceInformation2 value) {
        this.rmtInf = value;
    }

    /**
     * Gets the value of the undrlygCstmrCdtTrf property.
     * 
     * @return
     *     possible object is
     *     {@link CreditTransferTransaction18 }
     *     
     */
    public CreditTransferTransaction18 getUndrlygCstmrCdtTrf() {
        return undrlygCstmrCdtTrf;
    }

    /**
     * Sets the value of the undrlygCstmrCdtTrf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditTransferTransaction18 }
     *     
     */
    public void setUndrlygCstmrCdtTrf(CreditTransferTransaction18 value) {
        this.undrlygCstmrCdtTrf = value;
    }

    /**
     * Gets the value of the splmtryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementaryData1 }
     * 
     * 
     */
    public List<SupplementaryData1> getSplmtryData() {
        if (splmtryData == null) {
            splmtryData = new ArrayList<SupplementaryData1>();
        }
        return this.splmtryData;
    }

}