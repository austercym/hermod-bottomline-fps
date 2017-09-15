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
 * <p>Java class for RemittanceInformation10 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemittanceInformation10"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ustrd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Max140Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Strd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}StructuredRemittanceInformation12" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceInformation10", propOrder = {
    "ustrd",
    "strd"
})
public class RemittanceInformation10 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Ustrd")
    protected List<String> ustrd;
    @XmlElement(name = "Strd")
    protected List<StructuredRemittanceInformation12> strd;

    /**
     * Gets the value of the ustrd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ustrd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUstrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getUstrd() {
        if (ustrd == null) {
            ustrd = new ArrayList<String>();
        }
        return this.ustrd;
    }

    /**
     * Gets the value of the strd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the strd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructuredRemittanceInformation12 }
     * 
     * 
     */
    public List<StructuredRemittanceInformation12> getStrd() {
        if (strd == null) {
            strd = new ArrayList<StructuredRemittanceInformation12>();
        }
        return this.strd;
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
            List<String> theUstrd;
            theUstrd = (((this.ustrd!= null)&&(!this.ustrd.isEmpty()))?this.getUstrd():null);
            strategy.appendField(locator, this, "ustrd", buffer, theUstrd, ((this.ustrd!= null)&&(!this.ustrd.isEmpty())));
        }
        {
            List<StructuredRemittanceInformation12> theStrd;
            theStrd = (((this.strd!= null)&&(!this.strd.isEmpty()))?this.getStrd():null);
            strategy.appendField(locator, this, "strd", buffer, theStrd, ((this.strd!= null)&&(!this.strd.isEmpty())));
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
        final RemittanceInformation10 that = ((RemittanceInformation10) object);
        {
            List<String> lhsUstrd;
            lhsUstrd = (((this.ustrd!= null)&&(!this.ustrd.isEmpty()))?this.getUstrd():null);
            List<String> rhsUstrd;
            rhsUstrd = (((that.ustrd!= null)&&(!that.ustrd.isEmpty()))?that.getUstrd():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ustrd", lhsUstrd), LocatorUtils.property(thatLocator, "ustrd", rhsUstrd), lhsUstrd, rhsUstrd, ((this.ustrd!= null)&&(!this.ustrd.isEmpty())), ((that.ustrd!= null)&&(!that.ustrd.isEmpty())))) {
                return false;
            }
        }
        {
            List<StructuredRemittanceInformation12> lhsStrd;
            lhsStrd = (((this.strd!= null)&&(!this.strd.isEmpty()))?this.getStrd():null);
            List<StructuredRemittanceInformation12> rhsStrd;
            rhsStrd = (((that.strd!= null)&&(!that.strd.isEmpty()))?that.getStrd():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "strd", lhsStrd), LocatorUtils.property(thatLocator, "strd", rhsStrd), lhsStrd, rhsStrd, ((this.strd!= null)&&(!this.strd.isEmpty())), ((that.strd!= null)&&(!that.strd.isEmpty())))) {
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
            List<String> theUstrd;
            theUstrd = (((this.ustrd!= null)&&(!this.ustrd.isEmpty()))?this.getUstrd():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ustrd", theUstrd), currentHashCode, theUstrd, ((this.ustrd!= null)&&(!this.ustrd.isEmpty())));
        }
        {
            List<StructuredRemittanceInformation12> theStrd;
            theStrd = (((this.strd!= null)&&(!this.strd.isEmpty()))?this.getStrd():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "strd", theStrd), currentHashCode, theStrd, ((this.strd!= null)&&(!this.strd.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
