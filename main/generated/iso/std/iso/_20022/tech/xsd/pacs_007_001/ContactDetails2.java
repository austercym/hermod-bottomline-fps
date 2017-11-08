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
 * <p>Java class for ContactDetails2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactDetails2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NmPrfx" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}NamePrefix1Code" minOccurs="0"/&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="PhneNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="MobNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="FaxNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="EmailAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max2048Text" minOccurs="0"/&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactDetails2", propOrder = {
    "nmPrfx",
    "nm",
    "phneNb",
    "mobNb",
    "faxNb",
    "emailAdr",
    "othr"
})
public class ContactDetails2 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "NmPrfx")
    @XmlSchemaType(name = "string")
    protected NamePrefix1Code nmPrfx;
    @XmlElement(name = "Nm")
    protected String nm;
    @XmlElement(name = "PhneNb")
    protected String phneNb;
    @XmlElement(name = "MobNb")
    protected String mobNb;
    @XmlElement(name = "FaxNb")
    protected String faxNb;
    @XmlElement(name = "EmailAdr")
    protected String emailAdr;
    @XmlElement(name = "Othr")
    protected String othr;

    /**
     * Gets the value of the nmPrfx property.
     * 
     * @return
     *     possible object is
     *     {@link NamePrefix1Code }
     *     
     */
    public NamePrefix1Code getNmPrfx() {
        return nmPrfx;
    }

    /**
     * Sets the value of the nmPrfx property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamePrefix1Code }
     *     
     */
    public void setNmPrfx(NamePrefix1Code value) {
        this.nmPrfx = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the phneNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhneNb() {
        return phneNb;
    }

    /**
     * Sets the value of the phneNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhneNb(String value) {
        this.phneNb = value;
    }

    /**
     * Gets the value of the mobNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobNb() {
        return mobNb;
    }

    /**
     * Sets the value of the mobNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobNb(String value) {
        this.mobNb = value;
    }

    /**
     * Gets the value of the faxNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaxNb() {
        return faxNb;
    }

    /**
     * Sets the value of the faxNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaxNb(String value) {
        this.faxNb = value;
    }

    /**
     * Gets the value of the emailAdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAdr() {
        return emailAdr;
    }

    /**
     * Sets the value of the emailAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAdr(String value) {
        this.emailAdr = value;
    }

    /**
     * Gets the value of the othr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOthr(String value) {
        this.othr = value;
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
            NamePrefix1Code theNmPrfx;
            theNmPrfx = this.getNmPrfx();
            strategy.appendField(locator, this, "nmPrfx", buffer, theNmPrfx, (this.nmPrfx!= null));
        }
        {
            String theNm;
            theNm = this.getNm();
            strategy.appendField(locator, this, "nm", buffer, theNm, (this.nm!= null));
        }
        {
            String thePhneNb;
            thePhneNb = this.getPhneNb();
            strategy.appendField(locator, this, "phneNb", buffer, thePhneNb, (this.phneNb!= null));
        }
        {
            String theMobNb;
            theMobNb = this.getMobNb();
            strategy.appendField(locator, this, "mobNb", buffer, theMobNb, (this.mobNb!= null));
        }
        {
            String theFaxNb;
            theFaxNb = this.getFaxNb();
            strategy.appendField(locator, this, "faxNb", buffer, theFaxNb, (this.faxNb!= null));
        }
        {
            String theEmailAdr;
            theEmailAdr = this.getEmailAdr();
            strategy.appendField(locator, this, "emailAdr", buffer, theEmailAdr, (this.emailAdr!= null));
        }
        {
            String theOthr;
            theOthr = this.getOthr();
            strategy.appendField(locator, this, "othr", buffer, theOthr, (this.othr!= null));
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
        final ContactDetails2 that = ((ContactDetails2) object);
        {
            NamePrefix1Code lhsNmPrfx;
            lhsNmPrfx = this.getNmPrfx();
            NamePrefix1Code rhsNmPrfx;
            rhsNmPrfx = that.getNmPrfx();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "nmPrfx", lhsNmPrfx), LocatorUtils.property(thatLocator, "nmPrfx", rhsNmPrfx), lhsNmPrfx, rhsNmPrfx, (this.nmPrfx!= null), (that.nmPrfx!= null))) {
                return false;
            }
        }
        {
            String lhsNm;
            lhsNm = this.getNm();
            String rhsNm;
            rhsNm = that.getNm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "nm", lhsNm), LocatorUtils.property(thatLocator, "nm", rhsNm), lhsNm, rhsNm, (this.nm!= null), (that.nm!= null))) {
                return false;
            }
        }
        {
            String lhsPhneNb;
            lhsPhneNb = this.getPhneNb();
            String rhsPhneNb;
            rhsPhneNb = that.getPhneNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "phneNb", lhsPhneNb), LocatorUtils.property(thatLocator, "phneNb", rhsPhneNb), lhsPhneNb, rhsPhneNb, (this.phneNb!= null), (that.phneNb!= null))) {
                return false;
            }
        }
        {
            String lhsMobNb;
            lhsMobNb = this.getMobNb();
            String rhsMobNb;
            rhsMobNb = that.getMobNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "mobNb", lhsMobNb), LocatorUtils.property(thatLocator, "mobNb", rhsMobNb), lhsMobNb, rhsMobNb, (this.mobNb!= null), (that.mobNb!= null))) {
                return false;
            }
        }
        {
            String lhsFaxNb;
            lhsFaxNb = this.getFaxNb();
            String rhsFaxNb;
            rhsFaxNb = that.getFaxNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "faxNb", lhsFaxNb), LocatorUtils.property(thatLocator, "faxNb", rhsFaxNb), lhsFaxNb, rhsFaxNb, (this.faxNb!= null), (that.faxNb!= null))) {
                return false;
            }
        }
        {
            String lhsEmailAdr;
            lhsEmailAdr = this.getEmailAdr();
            String rhsEmailAdr;
            rhsEmailAdr = that.getEmailAdr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "emailAdr", lhsEmailAdr), LocatorUtils.property(thatLocator, "emailAdr", rhsEmailAdr), lhsEmailAdr, rhsEmailAdr, (this.emailAdr!= null), (that.emailAdr!= null))) {
                return false;
            }
        }
        {
            String lhsOthr;
            lhsOthr = this.getOthr();
            String rhsOthr;
            rhsOthr = that.getOthr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "othr", lhsOthr), LocatorUtils.property(thatLocator, "othr", rhsOthr), lhsOthr, rhsOthr, (this.othr!= null), (that.othr!= null))) {
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
            NamePrefix1Code theNmPrfx;
            theNmPrfx = this.getNmPrfx();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "nmPrfx", theNmPrfx), currentHashCode, theNmPrfx, (this.nmPrfx!= null));
        }
        {
            String theNm;
            theNm = this.getNm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "nm", theNm), currentHashCode, theNm, (this.nm!= null));
        }
        {
            String thePhneNb;
            thePhneNb = this.getPhneNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "phneNb", thePhneNb), currentHashCode, thePhneNb, (this.phneNb!= null));
        }
        {
            String theMobNb;
            theMobNb = this.getMobNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "mobNb", theMobNb), currentHashCode, theMobNb, (this.mobNb!= null));
        }
        {
            String theFaxNb;
            theFaxNb = this.getFaxNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "faxNb", theFaxNb), currentHashCode, theFaxNb, (this.faxNb!= null));
        }
        {
            String theEmailAdr;
            theEmailAdr = this.getEmailAdr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "emailAdr", theEmailAdr), currentHashCode, theEmailAdr, (this.emailAdr!= null));
        }
        {
            String theOthr;
            theOthr = this.getOthr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "othr", theOthr), currentHashCode, theOthr, (this.othr!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}