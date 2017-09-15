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
 * <p>Java class for PaymentIdentification3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentIdentification3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstrId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="EndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}Max35Text"/&gt;
 *         &lt;element name="TxId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}Max35Text"/&gt;
 *         &lt;element name="ClrSysRef" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05}Max35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentIdentification3", propOrder = {
    "instrId",
    "endToEndId",
    "txId",
    "clrSysRef"
})
public class PaymentIdentification3 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "InstrId")
    protected String instrId;
    @XmlElement(name = "EndToEndId", required = true)
    protected String endToEndId;
    @XmlElement(name = "TxId", required = true)
    protected String txId;
    @XmlElement(name = "ClrSysRef")
    protected String clrSysRef;

    /**
     * Gets the value of the instrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrId() {
        return instrId;
    }

    /**
     * Sets the value of the instrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstrId(String value) {
        this.instrId = value;
    }

    /**
     * Gets the value of the endToEndId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndToEndId() {
        return endToEndId;
    }

    /**
     * Sets the value of the endToEndId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndToEndId(String value) {
        this.endToEndId = value;
    }

    /**
     * Gets the value of the txId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxId() {
        return txId;
    }

    /**
     * Sets the value of the txId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxId(String value) {
        this.txId = value;
    }

    /**
     * Gets the value of the clrSysRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClrSysRef() {
        return clrSysRef;
    }

    /**
     * Sets the value of the clrSysRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClrSysRef(String value) {
        this.clrSysRef = value;
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
            String theInstrId;
            theInstrId = this.getInstrId();
            strategy.appendField(locator, this, "instrId", buffer, theInstrId, (this.instrId!= null));
        }
        {
            String theEndToEndId;
            theEndToEndId = this.getEndToEndId();
            strategy.appendField(locator, this, "endToEndId", buffer, theEndToEndId, (this.endToEndId!= null));
        }
        {
            String theTxId;
            theTxId = this.getTxId();
            strategy.appendField(locator, this, "txId", buffer, theTxId, (this.txId!= null));
        }
        {
            String theClrSysRef;
            theClrSysRef = this.getClrSysRef();
            strategy.appendField(locator, this, "clrSysRef", buffer, theClrSysRef, (this.clrSysRef!= null));
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
        final PaymentIdentification3 that = ((PaymentIdentification3) object);
        {
            String lhsInstrId;
            lhsInstrId = this.getInstrId();
            String rhsInstrId;
            rhsInstrId = that.getInstrId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instrId", lhsInstrId), LocatorUtils.property(thatLocator, "instrId", rhsInstrId), lhsInstrId, rhsInstrId, (this.instrId!= null), (that.instrId!= null))) {
                return false;
            }
        }
        {
            String lhsEndToEndId;
            lhsEndToEndId = this.getEndToEndId();
            String rhsEndToEndId;
            rhsEndToEndId = that.getEndToEndId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "endToEndId", lhsEndToEndId), LocatorUtils.property(thatLocator, "endToEndId", rhsEndToEndId), lhsEndToEndId, rhsEndToEndId, (this.endToEndId!= null), (that.endToEndId!= null))) {
                return false;
            }
        }
        {
            String lhsTxId;
            lhsTxId = this.getTxId();
            String rhsTxId;
            rhsTxId = that.getTxId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "txId", lhsTxId), LocatorUtils.property(thatLocator, "txId", rhsTxId), lhsTxId, rhsTxId, (this.txId!= null), (that.txId!= null))) {
                return false;
            }
        }
        {
            String lhsClrSysRef;
            lhsClrSysRef = this.getClrSysRef();
            String rhsClrSysRef;
            rhsClrSysRef = that.getClrSysRef();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "clrSysRef", lhsClrSysRef), LocatorUtils.property(thatLocator, "clrSysRef", rhsClrSysRef), lhsClrSysRef, rhsClrSysRef, (this.clrSysRef!= null), (that.clrSysRef!= null))) {
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
            String theInstrId;
            theInstrId = this.getInstrId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instrId", theInstrId), currentHashCode, theInstrId, (this.instrId!= null));
        }
        {
            String theEndToEndId;
            theEndToEndId = this.getEndToEndId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "endToEndId", theEndToEndId), currentHashCode, theEndToEndId, (this.endToEndId!= null));
        }
        {
            String theTxId;
            theTxId = this.getTxId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "txId", theTxId), currentHashCode, theTxId, (this.txId!= null));
        }
        {
            String theClrSysRef;
            theClrSysRef = this.getClrSysRef();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "clrSysRef", theClrSysRef), currentHashCode, theClrSysRef, (this.clrSysRef!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
