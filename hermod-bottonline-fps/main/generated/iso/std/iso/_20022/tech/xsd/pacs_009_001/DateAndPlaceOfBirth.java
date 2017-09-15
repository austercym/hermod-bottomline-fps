//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_009_001;

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
 * <p>Java class for DateAndPlaceOfBirth complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateAndPlaceOfBirth"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BirthDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}ISODate"/&gt;
 *         &lt;element name="PrvcOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CityOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Max35Text"/&gt;
 *         &lt;element name="CtryOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CountryCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateAndPlaceOfBirth", propOrder = {
    "birthDt",
    "prvcOfBirth",
    "cityOfBirth",
    "ctryOfBirth"
})
public class DateAndPlaceOfBirth implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "BirthDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    @XmlElement(name = "PrvcOfBirth")
    protected String prvcOfBirth;
    @XmlElement(name = "CityOfBirth", required = true)
    protected String cityOfBirth;
    @XmlElement(name = "CtryOfBirth", required = true)
    protected String ctryOfBirth;

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    /**
     * Gets the value of the prvcOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrvcOfBirth() {
        return prvcOfBirth;
    }

    /**
     * Sets the value of the prvcOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrvcOfBirth(String value) {
        this.prvcOfBirth = value;
    }

    /**
     * Gets the value of the cityOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityOfBirth() {
        return cityOfBirth;
    }

    /**
     * Sets the value of the cityOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityOfBirth(String value) {
        this.cityOfBirth = value;
    }

    /**
     * Gets the value of the ctryOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtryOfBirth() {
        return ctryOfBirth;
    }

    /**
     * Sets the value of the ctryOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtryOfBirth(String value) {
        this.ctryOfBirth = value;
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
            XMLGregorianCalendar theBirthDt;
            theBirthDt = this.getBirthDt();
            strategy.appendField(locator, this, "birthDt", buffer, theBirthDt, (this.birthDt!= null));
        }
        {
            String thePrvcOfBirth;
            thePrvcOfBirth = this.getPrvcOfBirth();
            strategy.appendField(locator, this, "prvcOfBirth", buffer, thePrvcOfBirth, (this.prvcOfBirth!= null));
        }
        {
            String theCityOfBirth;
            theCityOfBirth = this.getCityOfBirth();
            strategy.appendField(locator, this, "cityOfBirth", buffer, theCityOfBirth, (this.cityOfBirth!= null));
        }
        {
            String theCtryOfBirth;
            theCtryOfBirth = this.getCtryOfBirth();
            strategy.appendField(locator, this, "ctryOfBirth", buffer, theCtryOfBirth, (this.ctryOfBirth!= null));
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
        final DateAndPlaceOfBirth that = ((DateAndPlaceOfBirth) object);
        {
            XMLGregorianCalendar lhsBirthDt;
            lhsBirthDt = this.getBirthDt();
            XMLGregorianCalendar rhsBirthDt;
            rhsBirthDt = that.getBirthDt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "birthDt", lhsBirthDt), LocatorUtils.property(thatLocator, "birthDt", rhsBirthDt), lhsBirthDt, rhsBirthDt, (this.birthDt!= null), (that.birthDt!= null))) {
                return false;
            }
        }
        {
            String lhsPrvcOfBirth;
            lhsPrvcOfBirth = this.getPrvcOfBirth();
            String rhsPrvcOfBirth;
            rhsPrvcOfBirth = that.getPrvcOfBirth();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "prvcOfBirth", lhsPrvcOfBirth), LocatorUtils.property(thatLocator, "prvcOfBirth", rhsPrvcOfBirth), lhsPrvcOfBirth, rhsPrvcOfBirth, (this.prvcOfBirth!= null), (that.prvcOfBirth!= null))) {
                return false;
            }
        }
        {
            String lhsCityOfBirth;
            lhsCityOfBirth = this.getCityOfBirth();
            String rhsCityOfBirth;
            rhsCityOfBirth = that.getCityOfBirth();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cityOfBirth", lhsCityOfBirth), LocatorUtils.property(thatLocator, "cityOfBirth", rhsCityOfBirth), lhsCityOfBirth, rhsCityOfBirth, (this.cityOfBirth!= null), (that.cityOfBirth!= null))) {
                return false;
            }
        }
        {
            String lhsCtryOfBirth;
            lhsCtryOfBirth = this.getCtryOfBirth();
            String rhsCtryOfBirth;
            rhsCtryOfBirth = that.getCtryOfBirth();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ctryOfBirth", lhsCtryOfBirth), LocatorUtils.property(thatLocator, "ctryOfBirth", rhsCtryOfBirth), lhsCtryOfBirth, rhsCtryOfBirth, (this.ctryOfBirth!= null), (that.ctryOfBirth!= null))) {
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
            XMLGregorianCalendar theBirthDt;
            theBirthDt = this.getBirthDt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "birthDt", theBirthDt), currentHashCode, theBirthDt, (this.birthDt!= null));
        }
        {
            String thePrvcOfBirth;
            thePrvcOfBirth = this.getPrvcOfBirth();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "prvcOfBirth", thePrvcOfBirth), currentHashCode, thePrvcOfBirth, (this.prvcOfBirth!= null));
        }
        {
            String theCityOfBirth;
            theCityOfBirth = this.getCityOfBirth();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cityOfBirth", theCityOfBirth), currentHashCode, theCityOfBirth, (this.cityOfBirth!= null));
        }
        {
            String theCtryOfBirth;
            theCtryOfBirth = this.getCtryOfBirth();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ctryOfBirth", theCtryOfBirth), currentHashCode, theCtryOfBirth, (this.ctryOfBirth!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
