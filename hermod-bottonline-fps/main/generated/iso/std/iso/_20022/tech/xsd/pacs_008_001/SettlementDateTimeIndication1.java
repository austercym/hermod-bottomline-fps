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
 * <p>Java class for SettlementDateTimeIndication1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SettlementDateTimeIndication1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DbtDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}ISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="CdtDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}ISODateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementDateTimeIndication1", propOrder = {
    "dbtDtTm",
    "cdtDtTm"
})
public class SettlementDateTimeIndication1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DbtDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dbtDtTm;
    @XmlElement(name = "CdtDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cdtDtTm;

    /**
     * Gets the value of the dbtDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDbtDtTm() {
        return dbtDtTm;
    }

    /**
     * Sets the value of the dbtDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDbtDtTm(XMLGregorianCalendar value) {
        this.dbtDtTm = value;
    }

    /**
     * Gets the value of the cdtDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCdtDtTm() {
        return cdtDtTm;
    }

    /**
     * Sets the value of the cdtDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCdtDtTm(XMLGregorianCalendar value) {
        this.cdtDtTm = value;
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
            XMLGregorianCalendar theDbtDtTm;
            theDbtDtTm = this.getDbtDtTm();
            strategy.appendField(locator, this, "dbtDtTm", buffer, theDbtDtTm, (this.dbtDtTm!= null));
        }
        {
            XMLGregorianCalendar theCdtDtTm;
            theCdtDtTm = this.getCdtDtTm();
            strategy.appendField(locator, this, "cdtDtTm", buffer, theCdtDtTm, (this.cdtDtTm!= null));
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
        final SettlementDateTimeIndication1 that = ((SettlementDateTimeIndication1) object);
        {
            XMLGregorianCalendar lhsDbtDtTm;
            lhsDbtDtTm = this.getDbtDtTm();
            XMLGregorianCalendar rhsDbtDtTm;
            rhsDbtDtTm = that.getDbtDtTm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dbtDtTm", lhsDbtDtTm), LocatorUtils.property(thatLocator, "dbtDtTm", rhsDbtDtTm), lhsDbtDtTm, rhsDbtDtTm, (this.dbtDtTm!= null), (that.dbtDtTm!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsCdtDtTm;
            lhsCdtDtTm = this.getCdtDtTm();
            XMLGregorianCalendar rhsCdtDtTm;
            rhsCdtDtTm = that.getCdtDtTm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdtDtTm", lhsCdtDtTm), LocatorUtils.property(thatLocator, "cdtDtTm", rhsCdtDtTm), lhsCdtDtTm, rhsCdtDtTm, (this.cdtDtTm!= null), (that.cdtDtTm!= null))) {
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
            XMLGregorianCalendar theDbtDtTm;
            theDbtDtTm = this.getDbtDtTm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dbtDtTm", theDbtDtTm), currentHashCode, theDbtDtTm, (this.dbtDtTm!= null));
        }
        {
            XMLGregorianCalendar theCdtDtTm;
            theCdtDtTm = this.getCdtDtTm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdtDtTm", theCdtDtTm), currentHashCode, theCdtDtTm, (this.cdtDtTm!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
