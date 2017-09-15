//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_004_001;

import java.io.Serializable;
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
 * <p>Java class for Garnishment1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Garnishment1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}GarnishmentType1"/&gt;
 *         &lt;element name="Grnshee" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="GrnshmtAdmstr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="RefNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="RmtdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="FmlyMdclInsrncInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}TrueFalseIndicator" minOccurs="0"/&gt;
 *         &lt;element name="MplyeeTermntnInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}TrueFalseIndicator" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Garnishment1", propOrder = {
    "tp",
    "grnshee",
    "grnshmtAdmstr",
    "refNb",
    "dt",
    "rmtdAmt",
    "fmlyMdclInsrncInd",
    "mplyeeTermntnInd"
})
public class Garnishment1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Tp", required = true)
    protected GarnishmentType1 tp;
    @XmlElement(name = "Grnshee")
    protected PartyIdentification43 grnshee;
    @XmlElement(name = "GrnshmtAdmstr")
    protected PartyIdentification43 grnshmtAdmstr;
    @XmlElement(name = "RefNb")
    protected String refNb;
    @XmlElement(name = "Dt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    @XmlElement(name = "RmtdAmt")
    protected ActiveOrHistoricCurrencyAndAmount rmtdAmt;
    @XmlElement(name = "FmlyMdclInsrncInd")
    protected Boolean fmlyMdclInsrncInd;
    @XmlElement(name = "MplyeeTermntnInd")
    protected Boolean mplyeeTermntnInd;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link GarnishmentType1 }
     *     
     */
    public GarnishmentType1 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link GarnishmentType1 }
     *     
     */
    public void setTp(GarnishmentType1 value) {
        this.tp = value;
    }

    /**
     * Gets the value of the grnshee property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getGrnshee() {
        return grnshee;
    }

    /**
     * Sets the value of the grnshee property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setGrnshee(PartyIdentification43 value) {
        this.grnshee = value;
    }

    /**
     * Gets the value of the grnshmtAdmstr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getGrnshmtAdmstr() {
        return grnshmtAdmstr;
    }

    /**
     * Sets the value of the grnshmtAdmstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setGrnshmtAdmstr(PartyIdentification43 value) {
        this.grnshmtAdmstr = value;
    }

    /**
     * Gets the value of the refNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNb() {
        return refNb;
    }

    /**
     * Sets the value of the refNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNb(String value) {
        this.refNb = value;
    }

    /**
     * Gets the value of the dt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Gets the value of the rmtdAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getRmtdAmt() {
        return rmtdAmt;
    }

    /**
     * Sets the value of the rmtdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setRmtdAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.rmtdAmt = value;
    }

    /**
     * Gets the value of the fmlyMdclInsrncInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFmlyMdclInsrncInd() {
        return fmlyMdclInsrncInd;
    }

    /**
     * Sets the value of the fmlyMdclInsrncInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFmlyMdclInsrncInd(Boolean value) {
        this.fmlyMdclInsrncInd = value;
    }

    /**
     * Gets the value of the mplyeeTermntnInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMplyeeTermntnInd() {
        return mplyeeTermntnInd;
    }

    /**
     * Sets the value of the mplyeeTermntnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMplyeeTermntnInd(Boolean value) {
        this.mplyeeTermntnInd = value;
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
            GarnishmentType1 theTp;
            theTp = this.getTp();
            strategy.appendField(locator, this, "tp", buffer, theTp, (this.tp!= null));
        }
        {
            PartyIdentification43 theGrnshee;
            theGrnshee = this.getGrnshee();
            strategy.appendField(locator, this, "grnshee", buffer, theGrnshee, (this.grnshee!= null));
        }
        {
            PartyIdentification43 theGrnshmtAdmstr;
            theGrnshmtAdmstr = this.getGrnshmtAdmstr();
            strategy.appendField(locator, this, "grnshmtAdmstr", buffer, theGrnshmtAdmstr, (this.grnshmtAdmstr!= null));
        }
        {
            String theRefNb;
            theRefNb = this.getRefNb();
            strategy.appendField(locator, this, "refNb", buffer, theRefNb, (this.refNb!= null));
        }
        {
            XMLGregorianCalendar theDt;
            theDt = this.getDt();
            strategy.appendField(locator, this, "dt", buffer, theDt, (this.dt!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theRmtdAmt;
            theRmtdAmt = this.getRmtdAmt();
            strategy.appendField(locator, this, "rmtdAmt", buffer, theRmtdAmt, (this.rmtdAmt!= null));
        }
        {
            Boolean theFmlyMdclInsrncInd;
            theFmlyMdclInsrncInd = this.isFmlyMdclInsrncInd();
            strategy.appendField(locator, this, "fmlyMdclInsrncInd", buffer, theFmlyMdclInsrncInd, (this.fmlyMdclInsrncInd!= null));
        }
        {
            Boolean theMplyeeTermntnInd;
            theMplyeeTermntnInd = this.isMplyeeTermntnInd();
            strategy.appendField(locator, this, "mplyeeTermntnInd", buffer, theMplyeeTermntnInd, (this.mplyeeTermntnInd!= null));
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
        final Garnishment1 that = ((Garnishment1) object);
        {
            GarnishmentType1 lhsTp;
            lhsTp = this.getTp();
            GarnishmentType1 rhsTp;
            rhsTp = that.getTp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "tp", lhsTp), LocatorUtils.property(thatLocator, "tp", rhsTp), lhsTp, rhsTp, (this.tp!= null), (that.tp!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsGrnshee;
            lhsGrnshee = this.getGrnshee();
            PartyIdentification43 rhsGrnshee;
            rhsGrnshee = that.getGrnshee();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "grnshee", lhsGrnshee), LocatorUtils.property(thatLocator, "grnshee", rhsGrnshee), lhsGrnshee, rhsGrnshee, (this.grnshee!= null), (that.grnshee!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsGrnshmtAdmstr;
            lhsGrnshmtAdmstr = this.getGrnshmtAdmstr();
            PartyIdentification43 rhsGrnshmtAdmstr;
            rhsGrnshmtAdmstr = that.getGrnshmtAdmstr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "grnshmtAdmstr", lhsGrnshmtAdmstr), LocatorUtils.property(thatLocator, "grnshmtAdmstr", rhsGrnshmtAdmstr), lhsGrnshmtAdmstr, rhsGrnshmtAdmstr, (this.grnshmtAdmstr!= null), (that.grnshmtAdmstr!= null))) {
                return false;
            }
        }
        {
            String lhsRefNb;
            lhsRefNb = this.getRefNb();
            String rhsRefNb;
            rhsRefNb = that.getRefNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "refNb", lhsRefNb), LocatorUtils.property(thatLocator, "refNb", rhsRefNb), lhsRefNb, rhsRefNb, (this.refNb!= null), (that.refNb!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsDt;
            lhsDt = this.getDt();
            XMLGregorianCalendar rhsDt;
            rhsDt = that.getDt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dt", lhsDt), LocatorUtils.property(thatLocator, "dt", rhsDt), lhsDt, rhsDt, (this.dt!= null), (that.dt!= null))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsRmtdAmt;
            lhsRmtdAmt = this.getRmtdAmt();
            ActiveOrHistoricCurrencyAndAmount rhsRmtdAmt;
            rhsRmtdAmt = that.getRmtdAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rmtdAmt", lhsRmtdAmt), LocatorUtils.property(thatLocator, "rmtdAmt", rhsRmtdAmt), lhsRmtdAmt, rhsRmtdAmt, (this.rmtdAmt!= null), (that.rmtdAmt!= null))) {
                return false;
            }
        }
        {
            Boolean lhsFmlyMdclInsrncInd;
            lhsFmlyMdclInsrncInd = this.isFmlyMdclInsrncInd();
            Boolean rhsFmlyMdclInsrncInd;
            rhsFmlyMdclInsrncInd = that.isFmlyMdclInsrncInd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "fmlyMdclInsrncInd", lhsFmlyMdclInsrncInd), LocatorUtils.property(thatLocator, "fmlyMdclInsrncInd", rhsFmlyMdclInsrncInd), lhsFmlyMdclInsrncInd, rhsFmlyMdclInsrncInd, (this.fmlyMdclInsrncInd!= null), (that.fmlyMdclInsrncInd!= null))) {
                return false;
            }
        }
        {
            Boolean lhsMplyeeTermntnInd;
            lhsMplyeeTermntnInd = this.isMplyeeTermntnInd();
            Boolean rhsMplyeeTermntnInd;
            rhsMplyeeTermntnInd = that.isMplyeeTermntnInd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "mplyeeTermntnInd", lhsMplyeeTermntnInd), LocatorUtils.property(thatLocator, "mplyeeTermntnInd", rhsMplyeeTermntnInd), lhsMplyeeTermntnInd, rhsMplyeeTermntnInd, (this.mplyeeTermntnInd!= null), (that.mplyeeTermntnInd!= null))) {
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
            GarnishmentType1 theTp;
            theTp = this.getTp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "tp", theTp), currentHashCode, theTp, (this.tp!= null));
        }
        {
            PartyIdentification43 theGrnshee;
            theGrnshee = this.getGrnshee();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "grnshee", theGrnshee), currentHashCode, theGrnshee, (this.grnshee!= null));
        }
        {
            PartyIdentification43 theGrnshmtAdmstr;
            theGrnshmtAdmstr = this.getGrnshmtAdmstr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "grnshmtAdmstr", theGrnshmtAdmstr), currentHashCode, theGrnshmtAdmstr, (this.grnshmtAdmstr!= null));
        }
        {
            String theRefNb;
            theRefNb = this.getRefNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "refNb", theRefNb), currentHashCode, theRefNb, (this.refNb!= null));
        }
        {
            XMLGregorianCalendar theDt;
            theDt = this.getDt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dt", theDt), currentHashCode, theDt, (this.dt!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theRmtdAmt;
            theRmtdAmt = this.getRmtdAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rmtdAmt", theRmtdAmt), currentHashCode, theRmtdAmt, (this.rmtdAmt!= null));
        }
        {
            Boolean theFmlyMdclInsrncInd;
            theFmlyMdclInsrncInd = this.isFmlyMdclInsrncInd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "fmlyMdclInsrncInd", theFmlyMdclInsrncInd), currentHashCode, theFmlyMdclInsrncInd, (this.fmlyMdclInsrncInd!= null));
        }
        {
            Boolean theMplyeeTermntnInd;
            theMplyeeTermntnInd = this.isMplyeeTermntnInd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "mplyeeTermntnInd", theMplyeeTermntnInd), currentHashCode, theMplyeeTermntnInd, (this.mplyeeTermntnInd!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
