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
 * <p>Java class for DocumentAdjustment1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentAdjustment1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CreditDebitCode" minOccurs="0"/&gt;
 *         &lt;element name="Rsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max4Text" minOccurs="0"/&gt;
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max140Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentAdjustment1", propOrder = {
    "amt",
    "cdtDbtInd",
    "rsn",
    "addtlInf"
})
public class DocumentAdjustment1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd")
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "Rsn")
    protected String rsn;
    @XmlElement(name = "AddtlInf")
    protected String addtlInf;

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDebitCode }
     *     
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDebitCode }
     *     
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the rsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsn() {
        return rsn;
    }

    /**
     * Sets the value of the rsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsn(String value) {
        this.rsn = value;
    }

    /**
     * Gets the value of the addtlInf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddtlInf() {
        return addtlInf;
    }

    /**
     * Sets the value of the addtlInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddtlInf(String value) {
        this.addtlInf = value;
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
            ActiveOrHistoricCurrencyAndAmount theAmt;
            theAmt = this.getAmt();
            strategy.appendField(locator, this, "amt", buffer, theAmt, (this.amt!= null));
        }
        {
            CreditDebitCode theCdtDbtInd;
            theCdtDbtInd = this.getCdtDbtInd();
            strategy.appendField(locator, this, "cdtDbtInd", buffer, theCdtDbtInd, (this.cdtDbtInd!= null));
        }
        {
            String theRsn;
            theRsn = this.getRsn();
            strategy.appendField(locator, this, "rsn", buffer, theRsn, (this.rsn!= null));
        }
        {
            String theAddtlInf;
            theAddtlInf = this.getAddtlInf();
            strategy.appendField(locator, this, "addtlInf", buffer, theAddtlInf, (this.addtlInf!= null));
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
        final DocumentAdjustment1 that = ((DocumentAdjustment1) object);
        {
            ActiveOrHistoricCurrencyAndAmount lhsAmt;
            lhsAmt = this.getAmt();
            ActiveOrHistoricCurrencyAndAmount rhsAmt;
            rhsAmt = that.getAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "amt", lhsAmt), LocatorUtils.property(thatLocator, "amt", rhsAmt), lhsAmt, rhsAmt, (this.amt!= null), (that.amt!= null))) {
                return false;
            }
        }
        {
            CreditDebitCode lhsCdtDbtInd;
            lhsCdtDbtInd = this.getCdtDbtInd();
            CreditDebitCode rhsCdtDbtInd;
            rhsCdtDbtInd = that.getCdtDbtInd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdtDbtInd", lhsCdtDbtInd), LocatorUtils.property(thatLocator, "cdtDbtInd", rhsCdtDbtInd), lhsCdtDbtInd, rhsCdtDbtInd, (this.cdtDbtInd!= null), (that.cdtDbtInd!= null))) {
                return false;
            }
        }
        {
            String lhsRsn;
            lhsRsn = this.getRsn();
            String rhsRsn;
            rhsRsn = that.getRsn();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rsn", lhsRsn), LocatorUtils.property(thatLocator, "rsn", rhsRsn), lhsRsn, rhsRsn, (this.rsn!= null), (that.rsn!= null))) {
                return false;
            }
        }
        {
            String lhsAddtlInf;
            lhsAddtlInf = this.getAddtlInf();
            String rhsAddtlInf;
            rhsAddtlInf = that.getAddtlInf();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "addtlInf", lhsAddtlInf), LocatorUtils.property(thatLocator, "addtlInf", rhsAddtlInf), lhsAddtlInf, rhsAddtlInf, (this.addtlInf!= null), (that.addtlInf!= null))) {
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
            ActiveOrHistoricCurrencyAndAmount theAmt;
            theAmt = this.getAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "amt", theAmt), currentHashCode, theAmt, (this.amt!= null));
        }
        {
            CreditDebitCode theCdtDbtInd;
            theCdtDbtInd = this.getCdtDbtInd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdtDbtInd", theCdtDbtInd), currentHashCode, theCdtDbtInd, (this.cdtDbtInd!= null));
        }
        {
            String theRsn;
            theRsn = this.getRsn();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rsn", theRsn), currentHashCode, theRsn, (this.rsn!= null));
        }
        {
            String theAddtlInf;
            theAddtlInf = this.getAddtlInf();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "addtlInf", theAddtlInf), currentHashCode, theAddtlInf, (this.addtlInf!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
