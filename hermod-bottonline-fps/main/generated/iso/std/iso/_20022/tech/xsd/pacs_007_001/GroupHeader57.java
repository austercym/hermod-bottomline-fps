//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_007_001;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy2;
import org.jvnet.jaxb2_commons.lang.HashCode2;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy2;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString2;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy2;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for GroupHeader57 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupHeader57"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}ISODateTime"/&gt;
 *         &lt;element name="Authstn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Authorisation1Choice" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="BtchBookg" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}BatchBookingIndicator" minOccurs="0"/&gt;
 *         &lt;element name="NbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max15NumericText"/&gt;
 *         &lt;element name="CtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}DecimalNumber" minOccurs="0"/&gt;
 *         &lt;element name="GrpRvsl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}TrueFalseIndicator" minOccurs="0"/&gt;
 *         &lt;element name="TtlRvsdIntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}ActiveCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="SttlmInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}SettlementInstruction1"/&gt;
 *         &lt;element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader57", propOrder = {
    "msgId",
    "creDtTm",
    "authstn",
    "btchBookg",
    "nbOfTxs",
    "ctrlSum",
    "grpRvsl",
    "ttlRvsdIntrBkSttlmAmt",
    "intrBkSttlmDt",
    "sttlmInf",
    "instgAgt",
    "instdAgt"
})
public class GroupHeader57 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "CreDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    @XmlElement(name = "Authstn")
    protected List<Authorisation1Choice> authstn;
    @XmlElement(name = "BtchBookg")
    protected Boolean btchBookg;
    @XmlElement(name = "NbOfTxs", required = true)
    protected String nbOfTxs;
    @XmlElement(name = "CtrlSum")
    protected BigDecimal ctrlSum;
    @XmlElement(name = "GrpRvsl")
    protected Boolean grpRvsl;
    @XmlElement(name = "TtlRvsdIntrBkSttlmAmt")
    protected ActiveCurrencyAndAmount ttlRvsdIntrBkSttlmAmt;
    @XmlElement(name = "IntrBkSttlmDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    @XmlElement(name = "SttlmInf", required = true)
    protected SettlementInstruction1 sttlmInf;
    @XmlElement(name = "InstgAgt")
    protected BranchAndFinancialInstitutionIdentification5 instgAgt;
    @XmlElement(name = "InstdAgt")
    protected BranchAndFinancialInstitutionIdentification5 instdAgt;

    /**
     * Gets the value of the msgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Gets the value of the creDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Gets the value of the authstn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authstn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthstn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Authorisation1Choice }
     * 
     * 
     */
    public List<Authorisation1Choice> getAuthstn() {
        if (authstn == null) {
            authstn = new ArrayList<Authorisation1Choice>();
        }
        return this.authstn;
    }

    /**
     * Gets the value of the btchBookg property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBtchBookg() {
        return btchBookg;
    }

    /**
     * Sets the value of the btchBookg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBtchBookg(Boolean value) {
        this.btchBookg = value;
    }

    /**
     * Gets the value of the nbOfTxs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbOfTxs() {
        return nbOfTxs;
    }

    /**
     * Sets the value of the nbOfTxs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbOfTxs(String value) {
        this.nbOfTxs = value;
    }

    /**
     * Gets the value of the ctrlSum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCtrlSum() {
        return ctrlSum;
    }

    /**
     * Sets the value of the ctrlSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCtrlSum(BigDecimal value) {
        this.ctrlSum = value;
    }

    /**
     * Gets the value of the grpRvsl property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGrpRvsl() {
        return grpRvsl;
    }

    /**
     * Sets the value of the grpRvsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGrpRvsl(Boolean value) {
        this.grpRvsl = value;
    }

    /**
     * Gets the value of the ttlRvsdIntrBkSttlmAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public ActiveCurrencyAndAmount getTtlRvsdIntrBkSttlmAmt() {
        return ttlRvsdIntrBkSttlmAmt;
    }

    /**
     * Sets the value of the ttlRvsdIntrBkSttlmAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveCurrencyAndAmount }
     *     
     */
    public void setTtlRvsdIntrBkSttlmAmt(ActiveCurrencyAndAmount value) {
        this.ttlRvsdIntrBkSttlmAmt = value;
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
     * Gets the value of the sttlmInf property.
     * 
     * @return
     *     possible object is
     *     {@link SettlementInstruction1 }
     *     
     */
    public SettlementInstruction1 getSttlmInf() {
        return sttlmInf;
    }

    /**
     * Sets the value of the sttlmInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementInstruction1 }
     *     
     */
    public void setSttlmInf(SettlementInstruction1 value) {
        this.sttlmInf = value;
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

    public String toString() {
        final ToStringStrategy2 strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy2 strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy2 strategy) {
        {
            String theMsgId;
            theMsgId = this.getMsgId();
            strategy.appendField(locator, this, "msgId", buffer, theMsgId, (this.msgId!= null));
        }
        {
            XMLGregorianCalendar theCreDtTm;
            theCreDtTm = this.getCreDtTm();
            strategy.appendField(locator, this, "creDtTm", buffer, theCreDtTm, (this.creDtTm!= null));
        }
        {
            List<Authorisation1Choice> theAuthstn;
            theAuthstn = (((this.authstn!= null)&&(!this.authstn.isEmpty()))?this.getAuthstn():null);
            strategy.appendField(locator, this, "authstn", buffer, theAuthstn, ((this.authstn!= null)&&(!this.authstn.isEmpty())));
        }
        {
            Boolean theBtchBookg;
            theBtchBookg = this.isBtchBookg();
            strategy.appendField(locator, this, "btchBookg", buffer, theBtchBookg, (this.btchBookg!= null));
        }
        {
            String theNbOfTxs;
            theNbOfTxs = this.getNbOfTxs();
            strategy.appendField(locator, this, "nbOfTxs", buffer, theNbOfTxs, (this.nbOfTxs!= null));
        }
        {
            BigDecimal theCtrlSum;
            theCtrlSum = this.getCtrlSum();
            strategy.appendField(locator, this, "ctrlSum", buffer, theCtrlSum, (this.ctrlSum!= null));
        }
        {
            Boolean theGrpRvsl;
            theGrpRvsl = this.isGrpRvsl();
            strategy.appendField(locator, this, "grpRvsl", buffer, theGrpRvsl, (this.grpRvsl!= null));
        }
        {
            ActiveCurrencyAndAmount theTtlRvsdIntrBkSttlmAmt;
            theTtlRvsdIntrBkSttlmAmt = this.getTtlRvsdIntrBkSttlmAmt();
            strategy.appendField(locator, this, "ttlRvsdIntrBkSttlmAmt", buffer, theTtlRvsdIntrBkSttlmAmt, (this.ttlRvsdIntrBkSttlmAmt!= null));
        }
        {
            XMLGregorianCalendar theIntrBkSttlmDt;
            theIntrBkSttlmDt = this.getIntrBkSttlmDt();
            strategy.appendField(locator, this, "intrBkSttlmDt", buffer, theIntrBkSttlmDt, (this.intrBkSttlmDt!= null));
        }
        {
            SettlementInstruction1 theSttlmInf;
            theSttlmInf = this.getSttlmInf();
            strategy.appendField(locator, this, "sttlmInf", buffer, theSttlmInf, (this.sttlmInf!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgAgt;
            theInstgAgt = this.getInstgAgt();
            strategy.appendField(locator, this, "instgAgt", buffer, theInstgAgt, (this.instgAgt!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdAgt;
            theInstdAgt = this.getInstdAgt();
            strategy.appendField(locator, this, "instdAgt", buffer, theInstdAgt, (this.instdAgt!= null));
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy2 strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GroupHeader57 that = ((GroupHeader57) object);
        {
            String lhsMsgId;
            lhsMsgId = this.getMsgId();
            String rhsMsgId;
            rhsMsgId = that.getMsgId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "msgId", lhsMsgId), LocatorUtils.property(thatLocator, "msgId", rhsMsgId), lhsMsgId, rhsMsgId, (this.msgId!= null), (that.msgId!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsCreDtTm;
            lhsCreDtTm = this.getCreDtTm();
            XMLGregorianCalendar rhsCreDtTm;
            rhsCreDtTm = that.getCreDtTm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "creDtTm", lhsCreDtTm), LocatorUtils.property(thatLocator, "creDtTm", rhsCreDtTm), lhsCreDtTm, rhsCreDtTm, (this.creDtTm!= null), (that.creDtTm!= null))) {
                return false;
            }
        }
        {
            List<Authorisation1Choice> lhsAuthstn;
            lhsAuthstn = (((this.authstn!= null)&&(!this.authstn.isEmpty()))?this.getAuthstn():null);
            List<Authorisation1Choice> rhsAuthstn;
            rhsAuthstn = (((that.authstn!= null)&&(!that.authstn.isEmpty()))?that.getAuthstn():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "authstn", lhsAuthstn), LocatorUtils.property(thatLocator, "authstn", rhsAuthstn), lhsAuthstn, rhsAuthstn, ((this.authstn!= null)&&(!this.authstn.isEmpty())), ((that.authstn!= null)&&(!that.authstn.isEmpty())))) {
                return false;
            }
        }
        {
            Boolean lhsBtchBookg;
            lhsBtchBookg = this.isBtchBookg();
            Boolean rhsBtchBookg;
            rhsBtchBookg = that.isBtchBookg();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "btchBookg", lhsBtchBookg), LocatorUtils.property(thatLocator, "btchBookg", rhsBtchBookg), lhsBtchBookg, rhsBtchBookg, (this.btchBookg!= null), (that.btchBookg!= null))) {
                return false;
            }
        }
        {
            String lhsNbOfTxs;
            lhsNbOfTxs = this.getNbOfTxs();
            String rhsNbOfTxs;
            rhsNbOfTxs = that.getNbOfTxs();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "nbOfTxs", lhsNbOfTxs), LocatorUtils.property(thatLocator, "nbOfTxs", rhsNbOfTxs), lhsNbOfTxs, rhsNbOfTxs, (this.nbOfTxs!= null), (that.nbOfTxs!= null))) {
                return false;
            }
        }
        {
            BigDecimal lhsCtrlSum;
            lhsCtrlSum = this.getCtrlSum();
            BigDecimal rhsCtrlSum;
            rhsCtrlSum = that.getCtrlSum();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ctrlSum", lhsCtrlSum), LocatorUtils.property(thatLocator, "ctrlSum", rhsCtrlSum), lhsCtrlSum, rhsCtrlSum, (this.ctrlSum!= null), (that.ctrlSum!= null))) {
                return false;
            }
        }
        {
            Boolean lhsGrpRvsl;
            lhsGrpRvsl = this.isGrpRvsl();
            Boolean rhsGrpRvsl;
            rhsGrpRvsl = that.isGrpRvsl();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "grpRvsl", lhsGrpRvsl), LocatorUtils.property(thatLocator, "grpRvsl", rhsGrpRvsl), lhsGrpRvsl, rhsGrpRvsl, (this.grpRvsl!= null), (that.grpRvsl!= null))) {
                return false;
            }
        }
        {
            ActiveCurrencyAndAmount lhsTtlRvsdIntrBkSttlmAmt;
            lhsTtlRvsdIntrBkSttlmAmt = this.getTtlRvsdIntrBkSttlmAmt();
            ActiveCurrencyAndAmount rhsTtlRvsdIntrBkSttlmAmt;
            rhsTtlRvsdIntrBkSttlmAmt = that.getTtlRvsdIntrBkSttlmAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ttlRvsdIntrBkSttlmAmt", lhsTtlRvsdIntrBkSttlmAmt), LocatorUtils.property(thatLocator, "ttlRvsdIntrBkSttlmAmt", rhsTtlRvsdIntrBkSttlmAmt), lhsTtlRvsdIntrBkSttlmAmt, rhsTtlRvsdIntrBkSttlmAmt, (this.ttlRvsdIntrBkSttlmAmt!= null), (that.ttlRvsdIntrBkSttlmAmt!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsIntrBkSttlmDt;
            lhsIntrBkSttlmDt = this.getIntrBkSttlmDt();
            XMLGregorianCalendar rhsIntrBkSttlmDt;
            rhsIntrBkSttlmDt = that.getIntrBkSttlmDt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "intrBkSttlmDt", lhsIntrBkSttlmDt), LocatorUtils.property(thatLocator, "intrBkSttlmDt", rhsIntrBkSttlmDt), lhsIntrBkSttlmDt, rhsIntrBkSttlmDt, (this.intrBkSttlmDt!= null), (that.intrBkSttlmDt!= null))) {
                return false;
            }
        }
        {
            SettlementInstruction1 lhsSttlmInf;
            lhsSttlmInf = this.getSttlmInf();
            SettlementInstruction1 rhsSttlmInf;
            rhsSttlmInf = that.getSttlmInf();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "sttlmInf", lhsSttlmInf), LocatorUtils.property(thatLocator, "sttlmInf", rhsSttlmInf), lhsSttlmInf, rhsSttlmInf, (this.sttlmInf!= null), (that.sttlmInf!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstgAgt;
            lhsInstgAgt = this.getInstgAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstgAgt;
            rhsInstgAgt = that.getInstgAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instgAgt", lhsInstgAgt), LocatorUtils.property(thatLocator, "instgAgt", rhsInstgAgt), lhsInstgAgt, rhsInstgAgt, (this.instgAgt!= null), (that.instgAgt!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstdAgt;
            lhsInstdAgt = this.getInstdAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstdAgt;
            rhsInstdAgt = that.getInstdAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instdAgt", lhsInstdAgt), LocatorUtils.property(thatLocator, "instdAgt", rhsInstdAgt), lhsInstdAgt, rhsInstdAgt, (this.instdAgt!= null), (that.instdAgt!= null))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy2 strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy2 strategy) {
        int currentHashCode = 1;
        {
            String theMsgId;
            theMsgId = this.getMsgId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "msgId", theMsgId), currentHashCode, theMsgId, (this.msgId!= null));
        }
        {
            XMLGregorianCalendar theCreDtTm;
            theCreDtTm = this.getCreDtTm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "creDtTm", theCreDtTm), currentHashCode, theCreDtTm, (this.creDtTm!= null));
        }
        {
            List<Authorisation1Choice> theAuthstn;
            theAuthstn = (((this.authstn!= null)&&(!this.authstn.isEmpty()))?this.getAuthstn():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "authstn", theAuthstn), currentHashCode, theAuthstn, ((this.authstn!= null)&&(!this.authstn.isEmpty())));
        }
        {
            Boolean theBtchBookg;
            theBtchBookg = this.isBtchBookg();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "btchBookg", theBtchBookg), currentHashCode, theBtchBookg, (this.btchBookg!= null));
        }
        {
            String theNbOfTxs;
            theNbOfTxs = this.getNbOfTxs();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "nbOfTxs", theNbOfTxs), currentHashCode, theNbOfTxs, (this.nbOfTxs!= null));
        }
        {
            BigDecimal theCtrlSum;
            theCtrlSum = this.getCtrlSum();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ctrlSum", theCtrlSum), currentHashCode, theCtrlSum, (this.ctrlSum!= null));
        }
        {
            Boolean theGrpRvsl;
            theGrpRvsl = this.isGrpRvsl();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "grpRvsl", theGrpRvsl), currentHashCode, theGrpRvsl, (this.grpRvsl!= null));
        }
        {
            ActiveCurrencyAndAmount theTtlRvsdIntrBkSttlmAmt;
            theTtlRvsdIntrBkSttlmAmt = this.getTtlRvsdIntrBkSttlmAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ttlRvsdIntrBkSttlmAmt", theTtlRvsdIntrBkSttlmAmt), currentHashCode, theTtlRvsdIntrBkSttlmAmt, (this.ttlRvsdIntrBkSttlmAmt!= null));
        }
        {
            XMLGregorianCalendar theIntrBkSttlmDt;
            theIntrBkSttlmDt = this.getIntrBkSttlmDt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "intrBkSttlmDt", theIntrBkSttlmDt), currentHashCode, theIntrBkSttlmDt, (this.intrBkSttlmDt!= null));
        }
        {
            SettlementInstruction1 theSttlmInf;
            theSttlmInf = this.getSttlmInf();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "sttlmInf", theSttlmInf), currentHashCode, theSttlmInf, (this.sttlmInf!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgAgt;
            theInstgAgt = this.getInstgAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instgAgt", theInstgAgt), currentHashCode, theInstgAgt, (this.instgAgt!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdAgt;
            theInstdAgt = this.getInstdAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instdAgt", theInstdAgt), currentHashCode, theInstdAgt, (this.instdAgt!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
