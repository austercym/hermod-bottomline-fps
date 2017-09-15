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
import javax.xml.bind.annotation.XmlSchemaType;
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
 * <p>Java class for SettlementInstruction1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SettlementInstruction1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SttlmMtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}SettlementMethod1Code"/&gt;
 *         &lt;element name="SttlmAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="ClrSys" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ClearingSystemIdentification3Choice" minOccurs="0"/&gt;
 *         &lt;element name="InstgRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstgRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="InstdRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstdRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="ThrdRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="ThrdRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementInstruction1", propOrder = {
    "sttlmMtd",
    "sttlmAcct",
    "clrSys",
    "instgRmbrsmntAgt",
    "instgRmbrsmntAgtAcct",
    "instdRmbrsmntAgt",
    "instdRmbrsmntAgtAcct",
    "thrdRmbrsmntAgt",
    "thrdRmbrsmntAgtAcct"
})
public class SettlementInstruction1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SttlmMtd", required = true)
    @XmlSchemaType(name = "string")
    protected SettlementMethod1Code sttlmMtd;
    @XmlElement(name = "SttlmAcct")
    protected CashAccount24 sttlmAcct;
    @XmlElement(name = "ClrSys")
    protected ClearingSystemIdentification3Choice clrSys;
    @XmlElement(name = "InstgRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification5 instgRmbrsmntAgt;
    @XmlElement(name = "InstgRmbrsmntAgtAcct")
    protected CashAccount24 instgRmbrsmntAgtAcct;
    @XmlElement(name = "InstdRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification5 instdRmbrsmntAgt;
    @XmlElement(name = "InstdRmbrsmntAgtAcct")
    protected CashAccount24 instdRmbrsmntAgtAcct;
    @XmlElement(name = "ThrdRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification5 thrdRmbrsmntAgt;
    @XmlElement(name = "ThrdRmbrsmntAgtAcct")
    protected CashAccount24 thrdRmbrsmntAgtAcct;

    /**
     * Gets the value of the sttlmMtd property.
     * 
     * @return
     *     possible object is
     *     {@link SettlementMethod1Code }
     *     
     */
    public SettlementMethod1Code getSttlmMtd() {
        return sttlmMtd;
    }

    /**
     * Sets the value of the sttlmMtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementMethod1Code }
     *     
     */
    public void setSttlmMtd(SettlementMethod1Code value) {
        this.sttlmMtd = value;
    }

    /**
     * Gets the value of the sttlmAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getSttlmAcct() {
        return sttlmAcct;
    }

    /**
     * Sets the value of the sttlmAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setSttlmAcct(CashAccount24 value) {
        this.sttlmAcct = value;
    }

    /**
     * Gets the value of the clrSys property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemIdentification3Choice }
     *     
     */
    public ClearingSystemIdentification3Choice getClrSys() {
        return clrSys;
    }

    /**
     * Sets the value of the clrSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemIdentification3Choice }
     *     
     */
    public void setClrSys(ClearingSystemIdentification3Choice value) {
        this.clrSys = value;
    }

    /**
     * Gets the value of the instgRmbrsmntAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstgRmbrsmntAgt() {
        return instgRmbrsmntAgt;
    }

    /**
     * Sets the value of the instgRmbrsmntAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstgRmbrsmntAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instgRmbrsmntAgt = value;
    }

    /**
     * Gets the value of the instgRmbrsmntAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getInstgRmbrsmntAgtAcct() {
        return instgRmbrsmntAgtAcct;
    }

    /**
     * Sets the value of the instgRmbrsmntAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setInstgRmbrsmntAgtAcct(CashAccount24 value) {
        this.instgRmbrsmntAgtAcct = value;
    }

    /**
     * Gets the value of the instdRmbrsmntAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstdRmbrsmntAgt() {
        return instdRmbrsmntAgt;
    }

    /**
     * Sets the value of the instdRmbrsmntAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstdRmbrsmntAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instdRmbrsmntAgt = value;
    }

    /**
     * Gets the value of the instdRmbrsmntAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getInstdRmbrsmntAgtAcct() {
        return instdRmbrsmntAgtAcct;
    }

    /**
     * Sets the value of the instdRmbrsmntAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setInstdRmbrsmntAgtAcct(CashAccount24 value) {
        this.instdRmbrsmntAgtAcct = value;
    }

    /**
     * Gets the value of the thrdRmbrsmntAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getThrdRmbrsmntAgt() {
        return thrdRmbrsmntAgt;
    }

    /**
     * Sets the value of the thrdRmbrsmntAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setThrdRmbrsmntAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.thrdRmbrsmntAgt = value;
    }

    /**
     * Gets the value of the thrdRmbrsmntAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getThrdRmbrsmntAgtAcct() {
        return thrdRmbrsmntAgtAcct;
    }

    /**
     * Sets the value of the thrdRmbrsmntAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setThrdRmbrsmntAgtAcct(CashAccount24 value) {
        this.thrdRmbrsmntAgtAcct = value;
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
            SettlementMethod1Code theSttlmMtd;
            theSttlmMtd = this.getSttlmMtd();
            strategy.appendField(locator, this, "sttlmMtd", buffer, theSttlmMtd, (this.sttlmMtd!= null));
        }
        {
            CashAccount24 theSttlmAcct;
            theSttlmAcct = this.getSttlmAcct();
            strategy.appendField(locator, this, "sttlmAcct", buffer, theSttlmAcct, (this.sttlmAcct!= null));
        }
        {
            ClearingSystemIdentification3Choice theClrSys;
            theClrSys = this.getClrSys();
            strategy.appendField(locator, this, "clrSys", buffer, theClrSys, (this.clrSys!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgRmbrsmntAgt;
            theInstgRmbrsmntAgt = this.getInstgRmbrsmntAgt();
            strategy.appendField(locator, this, "instgRmbrsmntAgt", buffer, theInstgRmbrsmntAgt, (this.instgRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theInstgRmbrsmntAgtAcct;
            theInstgRmbrsmntAgtAcct = this.getInstgRmbrsmntAgtAcct();
            strategy.appendField(locator, this, "instgRmbrsmntAgtAcct", buffer, theInstgRmbrsmntAgtAcct, (this.instgRmbrsmntAgtAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdRmbrsmntAgt;
            theInstdRmbrsmntAgt = this.getInstdRmbrsmntAgt();
            strategy.appendField(locator, this, "instdRmbrsmntAgt", buffer, theInstdRmbrsmntAgt, (this.instdRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theInstdRmbrsmntAgtAcct;
            theInstdRmbrsmntAgtAcct = this.getInstdRmbrsmntAgtAcct();
            strategy.appendField(locator, this, "instdRmbrsmntAgtAcct", buffer, theInstdRmbrsmntAgtAcct, (this.instdRmbrsmntAgtAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theThrdRmbrsmntAgt;
            theThrdRmbrsmntAgt = this.getThrdRmbrsmntAgt();
            strategy.appendField(locator, this, "thrdRmbrsmntAgt", buffer, theThrdRmbrsmntAgt, (this.thrdRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theThrdRmbrsmntAgtAcct;
            theThrdRmbrsmntAgtAcct = this.getThrdRmbrsmntAgtAcct();
            strategy.appendField(locator, this, "thrdRmbrsmntAgtAcct", buffer, theThrdRmbrsmntAgtAcct, (this.thrdRmbrsmntAgtAcct!= null));
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
        final SettlementInstruction1 that = ((SettlementInstruction1) object);
        {
            SettlementMethod1Code lhsSttlmMtd;
            lhsSttlmMtd = this.getSttlmMtd();
            SettlementMethod1Code rhsSttlmMtd;
            rhsSttlmMtd = that.getSttlmMtd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "sttlmMtd", lhsSttlmMtd), LocatorUtils.property(thatLocator, "sttlmMtd", rhsSttlmMtd), lhsSttlmMtd, rhsSttlmMtd, (this.sttlmMtd!= null), (that.sttlmMtd!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsSttlmAcct;
            lhsSttlmAcct = this.getSttlmAcct();
            CashAccount24 rhsSttlmAcct;
            rhsSttlmAcct = that.getSttlmAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "sttlmAcct", lhsSttlmAcct), LocatorUtils.property(thatLocator, "sttlmAcct", rhsSttlmAcct), lhsSttlmAcct, rhsSttlmAcct, (this.sttlmAcct!= null), (that.sttlmAcct!= null))) {
                return false;
            }
        }
        {
            ClearingSystemIdentification3Choice lhsClrSys;
            lhsClrSys = this.getClrSys();
            ClearingSystemIdentification3Choice rhsClrSys;
            rhsClrSys = that.getClrSys();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "clrSys", lhsClrSys), LocatorUtils.property(thatLocator, "clrSys", rhsClrSys), lhsClrSys, rhsClrSys, (this.clrSys!= null), (that.clrSys!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstgRmbrsmntAgt;
            lhsInstgRmbrsmntAgt = this.getInstgRmbrsmntAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstgRmbrsmntAgt;
            rhsInstgRmbrsmntAgt = that.getInstgRmbrsmntAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instgRmbrsmntAgt", lhsInstgRmbrsmntAgt), LocatorUtils.property(thatLocator, "instgRmbrsmntAgt", rhsInstgRmbrsmntAgt), lhsInstgRmbrsmntAgt, rhsInstgRmbrsmntAgt, (this.instgRmbrsmntAgt!= null), (that.instgRmbrsmntAgt!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsInstgRmbrsmntAgtAcct;
            lhsInstgRmbrsmntAgtAcct = this.getInstgRmbrsmntAgtAcct();
            CashAccount24 rhsInstgRmbrsmntAgtAcct;
            rhsInstgRmbrsmntAgtAcct = that.getInstgRmbrsmntAgtAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instgRmbrsmntAgtAcct", lhsInstgRmbrsmntAgtAcct), LocatorUtils.property(thatLocator, "instgRmbrsmntAgtAcct", rhsInstgRmbrsmntAgtAcct), lhsInstgRmbrsmntAgtAcct, rhsInstgRmbrsmntAgtAcct, (this.instgRmbrsmntAgtAcct!= null), (that.instgRmbrsmntAgtAcct!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstdRmbrsmntAgt;
            lhsInstdRmbrsmntAgt = this.getInstdRmbrsmntAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstdRmbrsmntAgt;
            rhsInstdRmbrsmntAgt = that.getInstdRmbrsmntAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instdRmbrsmntAgt", lhsInstdRmbrsmntAgt), LocatorUtils.property(thatLocator, "instdRmbrsmntAgt", rhsInstdRmbrsmntAgt), lhsInstdRmbrsmntAgt, rhsInstdRmbrsmntAgt, (this.instdRmbrsmntAgt!= null), (that.instdRmbrsmntAgt!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsInstdRmbrsmntAgtAcct;
            lhsInstdRmbrsmntAgtAcct = this.getInstdRmbrsmntAgtAcct();
            CashAccount24 rhsInstdRmbrsmntAgtAcct;
            rhsInstdRmbrsmntAgtAcct = that.getInstdRmbrsmntAgtAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instdRmbrsmntAgtAcct", lhsInstdRmbrsmntAgtAcct), LocatorUtils.property(thatLocator, "instdRmbrsmntAgtAcct", rhsInstdRmbrsmntAgtAcct), lhsInstdRmbrsmntAgtAcct, rhsInstdRmbrsmntAgtAcct, (this.instdRmbrsmntAgtAcct!= null), (that.instdRmbrsmntAgtAcct!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsThrdRmbrsmntAgt;
            lhsThrdRmbrsmntAgt = this.getThrdRmbrsmntAgt();
            BranchAndFinancialInstitutionIdentification5 rhsThrdRmbrsmntAgt;
            rhsThrdRmbrsmntAgt = that.getThrdRmbrsmntAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "thrdRmbrsmntAgt", lhsThrdRmbrsmntAgt), LocatorUtils.property(thatLocator, "thrdRmbrsmntAgt", rhsThrdRmbrsmntAgt), lhsThrdRmbrsmntAgt, rhsThrdRmbrsmntAgt, (this.thrdRmbrsmntAgt!= null), (that.thrdRmbrsmntAgt!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsThrdRmbrsmntAgtAcct;
            lhsThrdRmbrsmntAgtAcct = this.getThrdRmbrsmntAgtAcct();
            CashAccount24 rhsThrdRmbrsmntAgtAcct;
            rhsThrdRmbrsmntAgtAcct = that.getThrdRmbrsmntAgtAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "thrdRmbrsmntAgtAcct", lhsThrdRmbrsmntAgtAcct), LocatorUtils.property(thatLocator, "thrdRmbrsmntAgtAcct", rhsThrdRmbrsmntAgtAcct), lhsThrdRmbrsmntAgtAcct, rhsThrdRmbrsmntAgtAcct, (this.thrdRmbrsmntAgtAcct!= null), (that.thrdRmbrsmntAgtAcct!= null))) {
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
            SettlementMethod1Code theSttlmMtd;
            theSttlmMtd = this.getSttlmMtd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "sttlmMtd", theSttlmMtd), currentHashCode, theSttlmMtd, (this.sttlmMtd!= null));
        }
        {
            CashAccount24 theSttlmAcct;
            theSttlmAcct = this.getSttlmAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "sttlmAcct", theSttlmAcct), currentHashCode, theSttlmAcct, (this.sttlmAcct!= null));
        }
        {
            ClearingSystemIdentification3Choice theClrSys;
            theClrSys = this.getClrSys();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "clrSys", theClrSys), currentHashCode, theClrSys, (this.clrSys!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgRmbrsmntAgt;
            theInstgRmbrsmntAgt = this.getInstgRmbrsmntAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instgRmbrsmntAgt", theInstgRmbrsmntAgt), currentHashCode, theInstgRmbrsmntAgt, (this.instgRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theInstgRmbrsmntAgtAcct;
            theInstgRmbrsmntAgtAcct = this.getInstgRmbrsmntAgtAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instgRmbrsmntAgtAcct", theInstgRmbrsmntAgtAcct), currentHashCode, theInstgRmbrsmntAgtAcct, (this.instgRmbrsmntAgtAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdRmbrsmntAgt;
            theInstdRmbrsmntAgt = this.getInstdRmbrsmntAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instdRmbrsmntAgt", theInstdRmbrsmntAgt), currentHashCode, theInstdRmbrsmntAgt, (this.instdRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theInstdRmbrsmntAgtAcct;
            theInstdRmbrsmntAgtAcct = this.getInstdRmbrsmntAgtAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instdRmbrsmntAgtAcct", theInstdRmbrsmntAgtAcct), currentHashCode, theInstdRmbrsmntAgtAcct, (this.instdRmbrsmntAgtAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theThrdRmbrsmntAgt;
            theThrdRmbrsmntAgt = this.getThrdRmbrsmntAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "thrdRmbrsmntAgt", theThrdRmbrsmntAgt), currentHashCode, theThrdRmbrsmntAgt, (this.thrdRmbrsmntAgt!= null));
        }
        {
            CashAccount24 theThrdRmbrsmntAgtAcct;
            theThrdRmbrsmntAgtAcct = this.getThrdRmbrsmntAgtAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "thrdRmbrsmntAgtAcct", theThrdRmbrsmntAgtAcct), currentHashCode, theThrdRmbrsmntAgtAcct, (this.thrdRmbrsmntAgtAcct!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
