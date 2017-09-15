//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_009_001;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * <p>Java class for OrganisationIdentification8 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganisationIdentification8"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AnyBIC" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}AnyBICIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}GenericOrganisationIdentification1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganisationIdentification8", propOrder = {
    "anyBIC",
    "othr"
})
public class OrganisationIdentification8 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "AnyBIC")
    protected String anyBIC;
    @XmlElement(name = "Othr")
    protected List<GenericOrganisationIdentification1> othr;

    /**
     * Gets the value of the anyBIC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnyBIC() {
        return anyBIC;
    }

    /**
     * Sets the value of the anyBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnyBIC(String value) {
        this.anyBIC = value;
    }

    /**
     * Gets the value of the othr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the othr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOthr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericOrganisationIdentification1 }
     * 
     * 
     */
    public List<GenericOrganisationIdentification1> getOthr() {
        if (othr == null) {
            othr = new ArrayList<GenericOrganisationIdentification1>();
        }
        return this.othr;
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
            String theAnyBIC;
            theAnyBIC = this.getAnyBIC();
            strategy.appendField(locator, this, "anyBIC", buffer, theAnyBIC, (this.anyBIC!= null));
        }
        {
            List<GenericOrganisationIdentification1> theOthr;
            theOthr = (((this.othr!= null)&&(!this.othr.isEmpty()))?this.getOthr():null);
            strategy.appendField(locator, this, "othr", buffer, theOthr, ((this.othr!= null)&&(!this.othr.isEmpty())));
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
        final OrganisationIdentification8 that = ((OrganisationIdentification8) object);
        {
            String lhsAnyBIC;
            lhsAnyBIC = this.getAnyBIC();
            String rhsAnyBIC;
            rhsAnyBIC = that.getAnyBIC();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "anyBIC", lhsAnyBIC), LocatorUtils.property(thatLocator, "anyBIC", rhsAnyBIC), lhsAnyBIC, rhsAnyBIC, (this.anyBIC!= null), (that.anyBIC!= null))) {
                return false;
            }
        }
        {
            List<GenericOrganisationIdentification1> lhsOthr;
            lhsOthr = (((this.othr!= null)&&(!this.othr.isEmpty()))?this.getOthr():null);
            List<GenericOrganisationIdentification1> rhsOthr;
            rhsOthr = (((that.othr!= null)&&(!that.othr.isEmpty()))?that.getOthr():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "othr", lhsOthr), LocatorUtils.property(thatLocator, "othr", rhsOthr), lhsOthr, rhsOthr, ((this.othr!= null)&&(!this.othr.isEmpty())), ((that.othr!= null)&&(!that.othr.isEmpty())))) {
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
            String theAnyBIC;
            theAnyBIC = this.getAnyBIC();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "anyBIC", theAnyBIC), currentHashCode, theAnyBIC, (this.anyBIC!= null));
        }
        {
            List<GenericOrganisationIdentification1> theOthr;
            theOthr = (((this.othr!= null)&&(!this.othr.isEmpty()))?this.getOthr():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "othr", theOthr), currentHashCode, theOthr, ((this.othr!= null)&&(!this.othr.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
