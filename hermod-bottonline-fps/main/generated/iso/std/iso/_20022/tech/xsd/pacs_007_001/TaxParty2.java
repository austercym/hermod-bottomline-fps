//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_007_001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 * <p>Java class for TaxParty2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxParty2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="RegnId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="TaxTp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Authstn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}TaxAuthorisation1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxParty2", propOrder = {
    "taxId",
    "regnId",
    "taxTp",
    "authstn"
})
public class TaxParty2 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TaxId")
    protected String taxId;
    @XmlElement(name = "RegnId")
    protected String regnId;
    @XmlElement(name = "TaxTp")
    protected String taxTp;
    @XmlElement(name = "Authstn")
    protected TaxAuthorisation1 authstn;

    /**
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Gets the value of the regnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegnId() {
        return regnId;
    }

    /**
     * Sets the value of the regnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegnId(String value) {
        this.regnId = value;
    }

    /**
     * Gets the value of the taxTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxTp() {
        return taxTp;
    }

    /**
     * Sets the value of the taxTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxTp(String value) {
        this.taxTp = value;
    }

    /**
     * Gets the value of the authstn property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAuthorisation1 }
     *     
     */
    public TaxAuthorisation1 getAuthstn() {
        return authstn;
    }

    /**
     * Sets the value of the authstn property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAuthorisation1 }
     *     
     */
    public void setAuthstn(TaxAuthorisation1 value) {
        this.authstn = value;
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
            String theTaxId;
            theTaxId = this.getTaxId();
            strategy.appendField(locator, this, "taxId", buffer, theTaxId, (this.taxId!= null));
        }
        {
            String theRegnId;
            theRegnId = this.getRegnId();
            strategy.appendField(locator, this, "regnId", buffer, theRegnId, (this.regnId!= null));
        }
        {
            String theTaxTp;
            theTaxTp = this.getTaxTp();
            strategy.appendField(locator, this, "taxTp", buffer, theTaxTp, (this.taxTp!= null));
        }
        {
            TaxAuthorisation1 theAuthstn;
            theAuthstn = this.getAuthstn();
            strategy.appendField(locator, this, "authstn", buffer, theAuthstn, (this.authstn!= null));
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
        final TaxParty2 that = ((TaxParty2) object);
        {
            String lhsTaxId;
            lhsTaxId = this.getTaxId();
            String rhsTaxId;
            rhsTaxId = that.getTaxId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "taxId", lhsTaxId), LocatorUtils.property(thatLocator, "taxId", rhsTaxId), lhsTaxId, rhsTaxId, (this.taxId!= null), (that.taxId!= null))) {
                return false;
            }
        }
        {
            String lhsRegnId;
            lhsRegnId = this.getRegnId();
            String rhsRegnId;
            rhsRegnId = that.getRegnId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "regnId", lhsRegnId), LocatorUtils.property(thatLocator, "regnId", rhsRegnId), lhsRegnId, rhsRegnId, (this.regnId!= null), (that.regnId!= null))) {
                return false;
            }
        }
        {
            String lhsTaxTp;
            lhsTaxTp = this.getTaxTp();
            String rhsTaxTp;
            rhsTaxTp = that.getTaxTp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "taxTp", lhsTaxTp), LocatorUtils.property(thatLocator, "taxTp", rhsTaxTp), lhsTaxTp, rhsTaxTp, (this.taxTp!= null), (that.taxTp!= null))) {
                return false;
            }
        }
        {
            TaxAuthorisation1 lhsAuthstn;
            lhsAuthstn = this.getAuthstn();
            TaxAuthorisation1 rhsAuthstn;
            rhsAuthstn = that.getAuthstn();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "authstn", lhsAuthstn), LocatorUtils.property(thatLocator, "authstn", rhsAuthstn), lhsAuthstn, rhsAuthstn, (this.authstn!= null), (that.authstn!= null))) {
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
            String theTaxId;
            theTaxId = this.getTaxId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "taxId", theTaxId), currentHashCode, theTaxId, (this.taxId!= null));
        }
        {
            String theRegnId;
            theRegnId = this.getRegnId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "regnId", theRegnId), currentHashCode, theRegnId, (this.regnId!= null));
        }
        {
            String theTaxTp;
            theTaxTp = this.getTaxTp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "taxTp", theTaxTp), currentHashCode, theTaxTp, (this.taxTp!= null));
        }
        {
            TaxAuthorisation1 theAuthstn;
            theAuthstn = this.getAuthstn();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "authstn", theAuthstn), currentHashCode, theAuthstn, (this.authstn!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
