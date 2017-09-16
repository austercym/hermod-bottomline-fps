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
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIToFIPmtRvsl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}FIToFIPaymentReversalV05"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFIPmtRvsl"
})
public class Document implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "FIToFIPmtRvsl", required = true)
    protected FIToFIPaymentReversalV05 fiToFIPmtRvsl;

    /**
     * Gets the value of the fiToFIPmtRvsl property.
     * 
     * @return
     *     possible object is
     *     {@link FIToFIPaymentReversalV05 }
     *     
     */
    public FIToFIPaymentReversalV05 getFIToFIPmtRvsl() {
        return fiToFIPmtRvsl;
    }

    /**
     * Sets the value of the fiToFIPmtRvsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIToFIPaymentReversalV05 }
     *     
     */
    public void setFIToFIPmtRvsl(FIToFIPaymentReversalV05 value) {
        this.fiToFIPmtRvsl = value;
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
            FIToFIPaymentReversalV05 theFIToFIPmtRvsl;
            theFIToFIPmtRvsl = this.getFIToFIPmtRvsl();
            strategy.appendField(locator, this, "fiToFIPmtRvsl", buffer, theFIToFIPmtRvsl, (this.fiToFIPmtRvsl!= null));
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
        final Document that = ((Document) object);
        {
            FIToFIPaymentReversalV05 lhsFIToFIPmtRvsl;
            lhsFIToFIPmtRvsl = this.getFIToFIPmtRvsl();
            FIToFIPaymentReversalV05 rhsFIToFIPmtRvsl;
            rhsFIToFIPmtRvsl = that.getFIToFIPmtRvsl();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "fiToFIPmtRvsl", lhsFIToFIPmtRvsl), LocatorUtils.property(thatLocator, "fiToFIPmtRvsl", rhsFIToFIPmtRvsl), lhsFIToFIPmtRvsl, rhsFIToFIPmtRvsl, (this.fiToFIPmtRvsl!= null), (that.fiToFIPmtRvsl!= null))) {
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
            FIToFIPaymentReversalV05 theFIToFIPmtRvsl;
            theFIToFIPmtRvsl = this.getFIToFIPmtRvsl();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "fiToFIPmtRvsl", theFIToFIPmtRvsl), currentHashCode, theFIToFIPmtRvsl, (this.fiToFIPmtRvsl!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}