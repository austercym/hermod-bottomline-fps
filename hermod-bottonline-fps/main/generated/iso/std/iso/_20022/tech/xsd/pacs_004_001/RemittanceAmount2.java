//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_004_001;

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
 * <p>Java class for RemittanceAmount2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemittanceAmount2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DuePyblAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="DscntApldAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}DiscountAmountAndType1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CdtNoteAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}TaxAmountAndType1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="AdjstmntAmtAndRsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}DocumentAdjustment1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RmtdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceAmount2", propOrder = {
    "duePyblAmt",
    "dscntApldAmt",
    "cdtNoteAmt",
    "taxAmt",
    "adjstmntAmtAndRsn",
    "rmtdAmt"
})
public class RemittanceAmount2 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DuePyblAmt")
    protected ActiveOrHistoricCurrencyAndAmount duePyblAmt;
    @XmlElement(name = "DscntApldAmt")
    protected List<DiscountAmountAndType1> dscntApldAmt;
    @XmlElement(name = "CdtNoteAmt")
    protected ActiveOrHistoricCurrencyAndAmount cdtNoteAmt;
    @XmlElement(name = "TaxAmt")
    protected List<TaxAmountAndType1> taxAmt;
    @XmlElement(name = "AdjstmntAmtAndRsn")
    protected List<DocumentAdjustment1> adjstmntAmtAndRsn;
    @XmlElement(name = "RmtdAmt")
    protected ActiveOrHistoricCurrencyAndAmount rmtdAmt;

    /**
     * Gets the value of the duePyblAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getDuePyblAmt() {
        return duePyblAmt;
    }

    /**
     * Sets the value of the duePyblAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setDuePyblAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.duePyblAmt = value;
    }

    /**
     * Gets the value of the dscntApldAmt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dscntApldAmt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDscntApldAmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiscountAmountAndType1 }
     * 
     * 
     */
    public List<DiscountAmountAndType1> getDscntApldAmt() {
        if (dscntApldAmt == null) {
            dscntApldAmt = new ArrayList<DiscountAmountAndType1>();
        }
        return this.dscntApldAmt;
    }

    /**
     * Gets the value of the cdtNoteAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getCdtNoteAmt() {
        return cdtNoteAmt;
    }

    /**
     * Sets the value of the cdtNoteAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setCdtNoteAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.cdtNoteAmt = value;
    }

    /**
     * Gets the value of the taxAmt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxAmt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxAmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxAmountAndType1 }
     * 
     * 
     */
    public List<TaxAmountAndType1> getTaxAmt() {
        if (taxAmt == null) {
            taxAmt = new ArrayList<TaxAmountAndType1>();
        }
        return this.taxAmt;
    }

    /**
     * Gets the value of the adjstmntAmtAndRsn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adjstmntAmtAndRsn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdjstmntAmtAndRsn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentAdjustment1 }
     * 
     * 
     */
    public List<DocumentAdjustment1> getAdjstmntAmtAndRsn() {
        if (adjstmntAmtAndRsn == null) {
            adjstmntAmtAndRsn = new ArrayList<DocumentAdjustment1>();
        }
        return this.adjstmntAmtAndRsn;
    }

    /**
     * Gets the value of the rmtdAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getRmtdAmt() {
        return rmtdAmt;
    }

    /**
     * Sets the value of the rmtdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setRmtdAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.rmtdAmt = value;
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
            ActiveOrHistoricCurrencyAndAmount theDuePyblAmt;
            theDuePyblAmt = this.getDuePyblAmt();
            strategy.appendField(locator, this, "duePyblAmt", buffer, theDuePyblAmt, (this.duePyblAmt!= null));
        }
        {
            List<DiscountAmountAndType1> theDscntApldAmt;
            theDscntApldAmt = (((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty()))?this.getDscntApldAmt():null);
            strategy.appendField(locator, this, "dscntApldAmt", buffer, theDscntApldAmt, ((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty())));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theCdtNoteAmt;
            theCdtNoteAmt = this.getCdtNoteAmt();
            strategy.appendField(locator, this, "cdtNoteAmt", buffer, theCdtNoteAmt, (this.cdtNoteAmt!= null));
        }
        {
            List<TaxAmountAndType1> theTaxAmt;
            theTaxAmt = (((this.taxAmt!= null)&&(!this.taxAmt.isEmpty()))?this.getTaxAmt():null);
            strategy.appendField(locator, this, "taxAmt", buffer, theTaxAmt, ((this.taxAmt!= null)&&(!this.taxAmt.isEmpty())));
        }
        {
            List<DocumentAdjustment1> theAdjstmntAmtAndRsn;
            theAdjstmntAmtAndRsn = (((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty()))?this.getAdjstmntAmtAndRsn():null);
            strategy.appendField(locator, this, "adjstmntAmtAndRsn", buffer, theAdjstmntAmtAndRsn, ((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty())));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theRmtdAmt;
            theRmtdAmt = this.getRmtdAmt();
            strategy.appendField(locator, this, "rmtdAmt", buffer, theRmtdAmt, (this.rmtdAmt!= null));
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
        final RemittanceAmount2 that = ((RemittanceAmount2) object);
        {
            ActiveOrHistoricCurrencyAndAmount lhsDuePyblAmt;
            lhsDuePyblAmt = this.getDuePyblAmt();
            ActiveOrHistoricCurrencyAndAmount rhsDuePyblAmt;
            rhsDuePyblAmt = that.getDuePyblAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "duePyblAmt", lhsDuePyblAmt), LocatorUtils.property(thatLocator, "duePyblAmt", rhsDuePyblAmt), lhsDuePyblAmt, rhsDuePyblAmt, (this.duePyblAmt!= null), (that.duePyblAmt!= null))) {
                return false;
            }
        }
        {
            List<DiscountAmountAndType1> lhsDscntApldAmt;
            lhsDscntApldAmt = (((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty()))?this.getDscntApldAmt():null);
            List<DiscountAmountAndType1> rhsDscntApldAmt;
            rhsDscntApldAmt = (((that.dscntApldAmt!= null)&&(!that.dscntApldAmt.isEmpty()))?that.getDscntApldAmt():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dscntApldAmt", lhsDscntApldAmt), LocatorUtils.property(thatLocator, "dscntApldAmt", rhsDscntApldAmt), lhsDscntApldAmt, rhsDscntApldAmt, ((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty())), ((that.dscntApldAmt!= null)&&(!that.dscntApldAmt.isEmpty())))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsCdtNoteAmt;
            lhsCdtNoteAmt = this.getCdtNoteAmt();
            ActiveOrHistoricCurrencyAndAmount rhsCdtNoteAmt;
            rhsCdtNoteAmt = that.getCdtNoteAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdtNoteAmt", lhsCdtNoteAmt), LocatorUtils.property(thatLocator, "cdtNoteAmt", rhsCdtNoteAmt), lhsCdtNoteAmt, rhsCdtNoteAmt, (this.cdtNoteAmt!= null), (that.cdtNoteAmt!= null))) {
                return false;
            }
        }
        {
            List<TaxAmountAndType1> lhsTaxAmt;
            lhsTaxAmt = (((this.taxAmt!= null)&&(!this.taxAmt.isEmpty()))?this.getTaxAmt():null);
            List<TaxAmountAndType1> rhsTaxAmt;
            rhsTaxAmt = (((that.taxAmt!= null)&&(!that.taxAmt.isEmpty()))?that.getTaxAmt():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "taxAmt", lhsTaxAmt), LocatorUtils.property(thatLocator, "taxAmt", rhsTaxAmt), lhsTaxAmt, rhsTaxAmt, ((this.taxAmt!= null)&&(!this.taxAmt.isEmpty())), ((that.taxAmt!= null)&&(!that.taxAmt.isEmpty())))) {
                return false;
            }
        }
        {
            List<DocumentAdjustment1> lhsAdjstmntAmtAndRsn;
            lhsAdjstmntAmtAndRsn = (((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty()))?this.getAdjstmntAmtAndRsn():null);
            List<DocumentAdjustment1> rhsAdjstmntAmtAndRsn;
            rhsAdjstmntAmtAndRsn = (((that.adjstmntAmtAndRsn!= null)&&(!that.adjstmntAmtAndRsn.isEmpty()))?that.getAdjstmntAmtAndRsn():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "adjstmntAmtAndRsn", lhsAdjstmntAmtAndRsn), LocatorUtils.property(thatLocator, "adjstmntAmtAndRsn", rhsAdjstmntAmtAndRsn), lhsAdjstmntAmtAndRsn, rhsAdjstmntAmtAndRsn, ((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty())), ((that.adjstmntAmtAndRsn!= null)&&(!that.adjstmntAmtAndRsn.isEmpty())))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsRmtdAmt;
            lhsRmtdAmt = this.getRmtdAmt();
            ActiveOrHistoricCurrencyAndAmount rhsRmtdAmt;
            rhsRmtdAmt = that.getRmtdAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rmtdAmt", lhsRmtdAmt), LocatorUtils.property(thatLocator, "rmtdAmt", rhsRmtdAmt), lhsRmtdAmt, rhsRmtdAmt, (this.rmtdAmt!= null), (that.rmtdAmt!= null))) {
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
            ActiveOrHistoricCurrencyAndAmount theDuePyblAmt;
            theDuePyblAmt = this.getDuePyblAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "duePyblAmt", theDuePyblAmt), currentHashCode, theDuePyblAmt, (this.duePyblAmt!= null));
        }
        {
            List<DiscountAmountAndType1> theDscntApldAmt;
            theDscntApldAmt = (((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty()))?this.getDscntApldAmt():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dscntApldAmt", theDscntApldAmt), currentHashCode, theDscntApldAmt, ((this.dscntApldAmt!= null)&&(!this.dscntApldAmt.isEmpty())));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theCdtNoteAmt;
            theCdtNoteAmt = this.getCdtNoteAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdtNoteAmt", theCdtNoteAmt), currentHashCode, theCdtNoteAmt, (this.cdtNoteAmt!= null));
        }
        {
            List<TaxAmountAndType1> theTaxAmt;
            theTaxAmt = (((this.taxAmt!= null)&&(!this.taxAmt.isEmpty()))?this.getTaxAmt():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "taxAmt", theTaxAmt), currentHashCode, theTaxAmt, ((this.taxAmt!= null)&&(!this.taxAmt.isEmpty())));
        }
        {
            List<DocumentAdjustment1> theAdjstmntAmtAndRsn;
            theAdjstmntAmtAndRsn = (((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty()))?this.getAdjstmntAmtAndRsn():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "adjstmntAmtAndRsn", theAdjstmntAmtAndRsn), currentHashCode, theAdjstmntAmtAndRsn, ((this.adjstmntAmtAndRsn!= null)&&(!this.adjstmntAmtAndRsn.isEmpty())));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theRmtdAmt;
            theRmtdAmt = this.getRmtdAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rmtdAmt", theRmtdAmt), currentHashCode, theRmtdAmt, (this.rmtdAmt!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
