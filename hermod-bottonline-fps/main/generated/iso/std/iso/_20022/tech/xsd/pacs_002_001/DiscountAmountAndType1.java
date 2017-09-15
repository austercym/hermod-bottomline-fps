//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_002_001;

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
 * <p>Java class for DiscountAmountAndType1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiscountAmountAndType1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}DiscountAmountType1Choice" minOccurs="0"/&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscountAmountAndType1", propOrder = {
    "tp",
    "amt"
})
public class DiscountAmountAndType1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Tp")
    protected DiscountAmountType1Choice tp;
    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link DiscountAmountType1Choice }
     *     
     */
    public DiscountAmountType1Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscountAmountType1Choice }
     *     
     */
    public void setTp(DiscountAmountType1Choice value) {
        this.tp = value;
    }

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
            DiscountAmountType1Choice theTp;
            theTp = this.getTp();
            strategy.appendField(locator, this, "tp", buffer, theTp, (this.tp!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theAmt;
            theAmt = this.getAmt();
            strategy.appendField(locator, this, "amt", buffer, theAmt, (this.amt!= null));
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
        final DiscountAmountAndType1 that = ((DiscountAmountAndType1) object);
        {
            DiscountAmountType1Choice lhsTp;
            lhsTp = this.getTp();
            DiscountAmountType1Choice rhsTp;
            rhsTp = that.getTp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "tp", lhsTp), LocatorUtils.property(thatLocator, "tp", rhsTp), lhsTp, rhsTp, (this.tp!= null), (that.tp!= null))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsAmt;
            lhsAmt = this.getAmt();
            ActiveOrHistoricCurrencyAndAmount rhsAmt;
            rhsAmt = that.getAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "amt", lhsAmt), LocatorUtils.property(thatLocator, "amt", rhsAmt), lhsAmt, rhsAmt, (this.amt!= null), (that.amt!= null))) {
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
            DiscountAmountType1Choice theTp;
            theTp = this.getTp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "tp", theTp), currentHashCode, theTp, (this.tp!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theAmt;
            theAmt = this.getAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "amt", theAmt), currentHashCode, theAmt, (this.amt!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
