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
 * <p>Java class for StructuredRemittanceInformation12 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructuredRemittanceInformation12"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RfrdDocInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}ReferredDocumentInformation6" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="RfrdDocAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}RemittanceAmount2" minOccurs="0"/&gt;
 *         &lt;element name="CdtrRefInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}CreditorReferenceInformation2" minOccurs="0"/&gt;
 *         &lt;element name="Invcr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="Invcee" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="TaxRmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}TaxInformation4" minOccurs="0"/&gt;
 *         &lt;element name="GrnshmtRmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Garnishment1" minOccurs="0"/&gt;
 *         &lt;element name="AddtlRmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Max140Text" maxOccurs="3" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructuredRemittanceInformation12", propOrder = {
    "rfrdDocInf",
    "rfrdDocAmt",
    "cdtrRefInf",
    "invcr",
    "invcee",
    "taxRmt",
    "grnshmtRmt",
    "addtlRmtInf"
})
public class StructuredRemittanceInformation12 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "RfrdDocInf")
    protected List<ReferredDocumentInformation6> rfrdDocInf;
    @XmlElement(name = "RfrdDocAmt")
    protected RemittanceAmount2 rfrdDocAmt;
    @XmlElement(name = "CdtrRefInf")
    protected CreditorReferenceInformation2 cdtrRefInf;
    @XmlElement(name = "Invcr")
    protected PartyIdentification43 invcr;
    @XmlElement(name = "Invcee")
    protected PartyIdentification43 invcee;
    @XmlElement(name = "TaxRmt")
    protected TaxInformation4 taxRmt;
    @XmlElement(name = "GrnshmtRmt")
    protected Garnishment1 grnshmtRmt;
    @XmlElement(name = "AddtlRmtInf")
    protected List<String> addtlRmtInf;

    /**
     * Gets the value of the rfrdDocInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rfrdDocInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRfrdDocInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferredDocumentInformation6 }
     * 
     * 
     */
    public List<ReferredDocumentInformation6> getRfrdDocInf() {
        if (rfrdDocInf == null) {
            rfrdDocInf = new ArrayList<ReferredDocumentInformation6>();
        }
        return this.rfrdDocInf;
    }

    /**
     * Gets the value of the rfrdDocAmt property.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceAmount2 }
     *     
     */
    public RemittanceAmount2 getRfrdDocAmt() {
        return rfrdDocAmt;
    }

    /**
     * Sets the value of the rfrdDocAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceAmount2 }
     *     
     */
    public void setRfrdDocAmt(RemittanceAmount2 value) {
        this.rfrdDocAmt = value;
    }

    /**
     * Gets the value of the cdtrRefInf property.
     * 
     * @return
     *     possible object is
     *     {@link CreditorReferenceInformation2 }
     *     
     */
    public CreditorReferenceInformation2 getCdtrRefInf() {
        return cdtrRefInf;
    }

    /**
     * Sets the value of the cdtrRefInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorReferenceInformation2 }
     *     
     */
    public void setCdtrRefInf(CreditorReferenceInformation2 value) {
        this.cdtrRefInf = value;
    }

    /**
     * Gets the value of the invcr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getInvcr() {
        return invcr;
    }

    /**
     * Sets the value of the invcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setInvcr(PartyIdentification43 value) {
        this.invcr = value;
    }

    /**
     * Gets the value of the invcee property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getInvcee() {
        return invcee;
    }

    /**
     * Sets the value of the invcee property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setInvcee(PartyIdentification43 value) {
        this.invcee = value;
    }

    /**
     * Gets the value of the taxRmt property.
     * 
     * @return
     *     possible object is
     *     {@link TaxInformation4 }
     *     
     */
    public TaxInformation4 getTaxRmt() {
        return taxRmt;
    }

    /**
     * Sets the value of the taxRmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxInformation4 }
     *     
     */
    public void setTaxRmt(TaxInformation4 value) {
        this.taxRmt = value;
    }

    /**
     * Gets the value of the grnshmtRmt property.
     * 
     * @return
     *     possible object is
     *     {@link Garnishment1 }
     *     
     */
    public Garnishment1 getGrnshmtRmt() {
        return grnshmtRmt;
    }

    /**
     * Sets the value of the grnshmtRmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Garnishment1 }
     *     
     */
    public void setGrnshmtRmt(Garnishment1 value) {
        this.grnshmtRmt = value;
    }

    /**
     * Gets the value of the addtlRmtInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addtlRmtInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddtlRmtInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddtlRmtInf() {
        if (addtlRmtInf == null) {
            addtlRmtInf = new ArrayList<String>();
        }
        return this.addtlRmtInf;
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
            List<ReferredDocumentInformation6> theRfrdDocInf;
            theRfrdDocInf = (((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty()))?this.getRfrdDocInf():null);
            strategy.appendField(locator, this, "rfrdDocInf", buffer, theRfrdDocInf, ((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty())));
        }
        {
            RemittanceAmount2 theRfrdDocAmt;
            theRfrdDocAmt = this.getRfrdDocAmt();
            strategy.appendField(locator, this, "rfrdDocAmt", buffer, theRfrdDocAmt, (this.rfrdDocAmt!= null));
        }
        {
            CreditorReferenceInformation2 theCdtrRefInf;
            theCdtrRefInf = this.getCdtrRefInf();
            strategy.appendField(locator, this, "cdtrRefInf", buffer, theCdtrRefInf, (this.cdtrRefInf!= null));
        }
        {
            PartyIdentification43 theInvcr;
            theInvcr = this.getInvcr();
            strategy.appendField(locator, this, "invcr", buffer, theInvcr, (this.invcr!= null));
        }
        {
            PartyIdentification43 theInvcee;
            theInvcee = this.getInvcee();
            strategy.appendField(locator, this, "invcee", buffer, theInvcee, (this.invcee!= null));
        }
        {
            TaxInformation4 theTaxRmt;
            theTaxRmt = this.getTaxRmt();
            strategy.appendField(locator, this, "taxRmt", buffer, theTaxRmt, (this.taxRmt!= null));
        }
        {
            Garnishment1 theGrnshmtRmt;
            theGrnshmtRmt = this.getGrnshmtRmt();
            strategy.appendField(locator, this, "grnshmtRmt", buffer, theGrnshmtRmt, (this.grnshmtRmt!= null));
        }
        {
            List<String> theAddtlRmtInf;
            theAddtlRmtInf = (((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty()))?this.getAddtlRmtInf():null);
            strategy.appendField(locator, this, "addtlRmtInf", buffer, theAddtlRmtInf, ((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty())));
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
        final StructuredRemittanceInformation12 that = ((StructuredRemittanceInformation12) object);
        {
            List<ReferredDocumentInformation6> lhsRfrdDocInf;
            lhsRfrdDocInf = (((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty()))?this.getRfrdDocInf():null);
            List<ReferredDocumentInformation6> rhsRfrdDocInf;
            rhsRfrdDocInf = (((that.rfrdDocInf!= null)&&(!that.rfrdDocInf.isEmpty()))?that.getRfrdDocInf():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rfrdDocInf", lhsRfrdDocInf), LocatorUtils.property(thatLocator, "rfrdDocInf", rhsRfrdDocInf), lhsRfrdDocInf, rhsRfrdDocInf, ((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty())), ((that.rfrdDocInf!= null)&&(!that.rfrdDocInf.isEmpty())))) {
                return false;
            }
        }
        {
            RemittanceAmount2 lhsRfrdDocAmt;
            lhsRfrdDocAmt = this.getRfrdDocAmt();
            RemittanceAmount2 rhsRfrdDocAmt;
            rhsRfrdDocAmt = that.getRfrdDocAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rfrdDocAmt", lhsRfrdDocAmt), LocatorUtils.property(thatLocator, "rfrdDocAmt", rhsRfrdDocAmt), lhsRfrdDocAmt, rhsRfrdDocAmt, (this.rfrdDocAmt!= null), (that.rfrdDocAmt!= null))) {
                return false;
            }
        }
        {
            CreditorReferenceInformation2 lhsCdtrRefInf;
            lhsCdtrRefInf = this.getCdtrRefInf();
            CreditorReferenceInformation2 rhsCdtrRefInf;
            rhsCdtrRefInf = that.getCdtrRefInf();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdtrRefInf", lhsCdtrRefInf), LocatorUtils.property(thatLocator, "cdtrRefInf", rhsCdtrRefInf), lhsCdtrRefInf, rhsCdtrRefInf, (this.cdtrRefInf!= null), (that.cdtrRefInf!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsInvcr;
            lhsInvcr = this.getInvcr();
            PartyIdentification43 rhsInvcr;
            rhsInvcr = that.getInvcr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "invcr", lhsInvcr), LocatorUtils.property(thatLocator, "invcr", rhsInvcr), lhsInvcr, rhsInvcr, (this.invcr!= null), (that.invcr!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsInvcee;
            lhsInvcee = this.getInvcee();
            PartyIdentification43 rhsInvcee;
            rhsInvcee = that.getInvcee();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "invcee", lhsInvcee), LocatorUtils.property(thatLocator, "invcee", rhsInvcee), lhsInvcee, rhsInvcee, (this.invcee!= null), (that.invcee!= null))) {
                return false;
            }
        }
        {
            TaxInformation4 lhsTaxRmt;
            lhsTaxRmt = this.getTaxRmt();
            TaxInformation4 rhsTaxRmt;
            rhsTaxRmt = that.getTaxRmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "taxRmt", lhsTaxRmt), LocatorUtils.property(thatLocator, "taxRmt", rhsTaxRmt), lhsTaxRmt, rhsTaxRmt, (this.taxRmt!= null), (that.taxRmt!= null))) {
                return false;
            }
        }
        {
            Garnishment1 lhsGrnshmtRmt;
            lhsGrnshmtRmt = this.getGrnshmtRmt();
            Garnishment1 rhsGrnshmtRmt;
            rhsGrnshmtRmt = that.getGrnshmtRmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "grnshmtRmt", lhsGrnshmtRmt), LocatorUtils.property(thatLocator, "grnshmtRmt", rhsGrnshmtRmt), lhsGrnshmtRmt, rhsGrnshmtRmt, (this.grnshmtRmt!= null), (that.grnshmtRmt!= null))) {
                return false;
            }
        }
        {
            List<String> lhsAddtlRmtInf;
            lhsAddtlRmtInf = (((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty()))?this.getAddtlRmtInf():null);
            List<String> rhsAddtlRmtInf;
            rhsAddtlRmtInf = (((that.addtlRmtInf!= null)&&(!that.addtlRmtInf.isEmpty()))?that.getAddtlRmtInf():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "addtlRmtInf", lhsAddtlRmtInf), LocatorUtils.property(thatLocator, "addtlRmtInf", rhsAddtlRmtInf), lhsAddtlRmtInf, rhsAddtlRmtInf, ((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty())), ((that.addtlRmtInf!= null)&&(!that.addtlRmtInf.isEmpty())))) {
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
            List<ReferredDocumentInformation6> theRfrdDocInf;
            theRfrdDocInf = (((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty()))?this.getRfrdDocInf():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rfrdDocInf", theRfrdDocInf), currentHashCode, theRfrdDocInf, ((this.rfrdDocInf!= null)&&(!this.rfrdDocInf.isEmpty())));
        }
        {
            RemittanceAmount2 theRfrdDocAmt;
            theRfrdDocAmt = this.getRfrdDocAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rfrdDocAmt", theRfrdDocAmt), currentHashCode, theRfrdDocAmt, (this.rfrdDocAmt!= null));
        }
        {
            CreditorReferenceInformation2 theCdtrRefInf;
            theCdtrRefInf = this.getCdtrRefInf();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdtrRefInf", theCdtrRefInf), currentHashCode, theCdtrRefInf, (this.cdtrRefInf!= null));
        }
        {
            PartyIdentification43 theInvcr;
            theInvcr = this.getInvcr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "invcr", theInvcr), currentHashCode, theInvcr, (this.invcr!= null));
        }
        {
            PartyIdentification43 theInvcee;
            theInvcee = this.getInvcee();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "invcee", theInvcee), currentHashCode, theInvcee, (this.invcee!= null));
        }
        {
            TaxInformation4 theTaxRmt;
            theTaxRmt = this.getTaxRmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "taxRmt", theTaxRmt), currentHashCode, theTaxRmt, (this.taxRmt!= null));
        }
        {
            Garnishment1 theGrnshmtRmt;
            theGrnshmtRmt = this.getGrnshmtRmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "grnshmtRmt", theGrnshmtRmt), currentHashCode, theGrnshmtRmt, (this.grnshmtRmt!= null));
        }
        {
            List<String> theAddtlRmtInf;
            theAddtlRmtInf = (((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty()))?this.getAddtlRmtInf():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "addtlRmtInf", theAddtlRmtInf), currentHashCode, theAddtlRmtInf, ((this.addtlRmtInf!= null)&&(!this.addtlRmtInf.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}