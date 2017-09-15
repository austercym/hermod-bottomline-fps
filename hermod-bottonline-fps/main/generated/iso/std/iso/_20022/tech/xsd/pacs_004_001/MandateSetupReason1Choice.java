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
 * <p>Java class for MandateSetupReason1Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MandateSetupReason1Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ExternalMandateSetupReason1Code"/&gt;
 *         &lt;element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max70Text"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MandateSetupReason1Choice", propOrder = {
    "cd",
    "prtry"
})
public class MandateSetupReason1Choice implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Cd")
    protected String cd;
    @XmlElement(name = "Prtry")
    protected String prtry;

    /**
     * Gets the value of the cd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCd(String value) {
        this.cd = value;
    }

    /**
     * Gets the value of the prtry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrtry(String value) {
        this.prtry = value;
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
            String theCd;
            theCd = this.getCd();
            strategy.appendField(locator, this, "cd", buffer, theCd, (this.cd!= null));
        }
        {
            String thePrtry;
            thePrtry = this.getPrtry();
            strategy.appendField(locator, this, "prtry", buffer, thePrtry, (this.prtry!= null));
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
        final MandateSetupReason1Choice that = ((MandateSetupReason1Choice) object);
        {
            String lhsCd;
            lhsCd = this.getCd();
            String rhsCd;
            rhsCd = that.getCd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cd", lhsCd), LocatorUtils.property(thatLocator, "cd", rhsCd), lhsCd, rhsCd, (this.cd!= null), (that.cd!= null))) {
                return false;
            }
        }
        {
            String lhsPrtry;
            lhsPrtry = this.getPrtry();
            String rhsPrtry;
            rhsPrtry = that.getPrtry();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "prtry", lhsPrtry), LocatorUtils.property(thatLocator, "prtry", rhsPrtry), lhsPrtry, rhsPrtry, (this.prtry!= null), (that.prtry!= null))) {
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
            String theCd;
            theCd = this.getCd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cd", theCd), currentHashCode, theCd, (this.cd!= null));
        }
        {
            String thePrtry;
            thePrtry = this.getPrtry();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "prtry", thePrtry), currentHashCode, thePrtry, (this.prtry!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
