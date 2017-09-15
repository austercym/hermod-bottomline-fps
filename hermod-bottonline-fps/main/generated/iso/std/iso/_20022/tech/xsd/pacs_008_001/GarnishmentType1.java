//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_008_001;

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
 * <p>Java class for GarnishmentType1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GarnishmentType1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CdOrPrtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}GarnishmentType1Choice"/&gt;
 *         &lt;element name="Issr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}Max35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GarnishmentType1", propOrder = {
    "cdOrPrtry",
    "issr"
})
public class GarnishmentType1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CdOrPrtry", required = true)
    protected GarnishmentType1Choice cdOrPrtry;
    @XmlElement(name = "Issr")
    protected String issr;

    /**
     * Gets the value of the cdOrPrtry property.
     * 
     * @return
     *     possible object is
     *     {@link GarnishmentType1Choice }
     *     
     */
    public GarnishmentType1Choice getCdOrPrtry() {
        return cdOrPrtry;
    }

    /**
     * Sets the value of the cdOrPrtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link GarnishmentType1Choice }
     *     
     */
    public void setCdOrPrtry(GarnishmentType1Choice value) {
        this.cdOrPrtry = value;
    }

    /**
     * Gets the value of the issr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssr() {
        return issr;
    }

    /**
     * Sets the value of the issr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssr(String value) {
        this.issr = value;
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
            GarnishmentType1Choice theCdOrPrtry;
            theCdOrPrtry = this.getCdOrPrtry();
            strategy.appendField(locator, this, "cdOrPrtry", buffer, theCdOrPrtry, (this.cdOrPrtry!= null));
        }
        {
            String theIssr;
            theIssr = this.getIssr();
            strategy.appendField(locator, this, "issr", buffer, theIssr, (this.issr!= null));
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
        final GarnishmentType1 that = ((GarnishmentType1) object);
        {
            GarnishmentType1Choice lhsCdOrPrtry;
            lhsCdOrPrtry = this.getCdOrPrtry();
            GarnishmentType1Choice rhsCdOrPrtry;
            rhsCdOrPrtry = that.getCdOrPrtry();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdOrPrtry", lhsCdOrPrtry), LocatorUtils.property(thatLocator, "cdOrPrtry", rhsCdOrPrtry), lhsCdOrPrtry, rhsCdOrPrtry, (this.cdOrPrtry!= null), (that.cdOrPrtry!= null))) {
                return false;
            }
        }
        {
            String lhsIssr;
            lhsIssr = this.getIssr();
            String rhsIssr;
            rhsIssr = that.getIssr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "issr", lhsIssr), LocatorUtils.property(thatLocator, "issr", rhsIssr), lhsIssr, rhsIssr, (this.issr!= null), (that.issr!= null))) {
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
            GarnishmentType1Choice theCdOrPrtry;
            theCdOrPrtry = this.getCdOrPrtry();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdOrPrtry", theCdOrPrtry), currentHashCode, theCdOrPrtry, (this.cdOrPrtry!= null));
        }
        {
            String theIssr;
            theIssr = this.getIssr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "issr", theIssr), currentHashCode, theIssr, (this.issr!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
