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
 * <p>Java class for AmountType4Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AmountType4Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="InstdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="EqvtAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}EquivalentAmount2"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType4Choice", propOrder = {
    "instdAmt",
    "eqvtAmt"
})
public class AmountType4Choice implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "InstdAmt")
    protected ActiveOrHistoricCurrencyAndAmount instdAmt;
    @XmlElement(name = "EqvtAmt")
    protected EquivalentAmount2 eqvtAmt;

    /**
     * Gets the value of the instdAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getInstdAmt() {
        return instdAmt;
    }

    /**
     * Sets the value of the instdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setInstdAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.instdAmt = value;
    }

    /**
     * Gets the value of the eqvtAmt property.
     * 
     * @return
     *     possible object is
     *     {@link EquivalentAmount2 }
     *     
     */
    public EquivalentAmount2 getEqvtAmt() {
        return eqvtAmt;
    }

    /**
     * Sets the value of the eqvtAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquivalentAmount2 }
     *     
     */
    public void setEqvtAmt(EquivalentAmount2 value) {
        this.eqvtAmt = value;
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
            ActiveOrHistoricCurrencyAndAmount theInstdAmt;
            theInstdAmt = this.getInstdAmt();
            strategy.appendField(locator, this, "instdAmt", buffer, theInstdAmt, (this.instdAmt!= null));
        }
        {
            EquivalentAmount2 theEqvtAmt;
            theEqvtAmt = this.getEqvtAmt();
            strategy.appendField(locator, this, "eqvtAmt", buffer, theEqvtAmt, (this.eqvtAmt!= null));
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
        final AmountType4Choice that = ((AmountType4Choice) object);
        {
            ActiveOrHistoricCurrencyAndAmount lhsInstdAmt;
            lhsInstdAmt = this.getInstdAmt();
            ActiveOrHistoricCurrencyAndAmount rhsInstdAmt;
            rhsInstdAmt = that.getInstdAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instdAmt", lhsInstdAmt), LocatorUtils.property(thatLocator, "instdAmt", rhsInstdAmt), lhsInstdAmt, rhsInstdAmt, (this.instdAmt!= null), (that.instdAmt!= null))) {
                return false;
            }
        }
        {
            EquivalentAmount2 lhsEqvtAmt;
            lhsEqvtAmt = this.getEqvtAmt();
            EquivalentAmount2 rhsEqvtAmt;
            rhsEqvtAmt = that.getEqvtAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "eqvtAmt", lhsEqvtAmt), LocatorUtils.property(thatLocator, "eqvtAmt", rhsEqvtAmt), lhsEqvtAmt, rhsEqvtAmt, (this.eqvtAmt!= null), (that.eqvtAmt!= null))) {
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
            ActiveOrHistoricCurrencyAndAmount theInstdAmt;
            theInstdAmt = this.getInstdAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instdAmt", theInstdAmt), currentHashCode, theInstdAmt, (this.instdAmt!= null));
        }
        {
            EquivalentAmount2 theEqvtAmt;
            theEqvtAmt = this.getEqvtAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "eqvtAmt", theEqvtAmt), currentHashCode, theEqvtAmt, (this.eqvtAmt!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}