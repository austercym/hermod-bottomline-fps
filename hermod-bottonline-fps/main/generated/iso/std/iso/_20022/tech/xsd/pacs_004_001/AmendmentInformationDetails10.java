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
 * <p>Java class for AmendmentInformationDetails10 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AmendmentInformationDetails10"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrgnlMndtId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlCdtrSchmeId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlCdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlCdtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlDbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlDbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlDbtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlFnlColltnDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlFrqcy" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Frequency21Choice" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlRsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}MandateSetupReason1Choice" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmendmentInformationDetails10", propOrder = {
    "orgnlMndtId",
    "orgnlCdtrSchmeId",
    "orgnlCdtrAgt",
    "orgnlCdtrAgtAcct",
    "orgnlDbtr",
    "orgnlDbtrAcct",
    "orgnlDbtrAgt",
    "orgnlDbtrAgtAcct",
    "orgnlFnlColltnDt",
    "orgnlFrqcy",
    "orgnlRsn"
})
public class AmendmentInformationDetails10 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "OrgnlMndtId")
    protected String orgnlMndtId;
    @XmlElement(name = "OrgnlCdtrSchmeId")
    protected PartyIdentification43 orgnlCdtrSchmeId;
    @XmlElement(name = "OrgnlCdtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 orgnlCdtrAgt;
    @XmlElement(name = "OrgnlCdtrAgtAcct")
    protected CashAccount24 orgnlCdtrAgtAcct;
    @XmlElement(name = "OrgnlDbtr")
    protected PartyIdentification43 orgnlDbtr;
    @XmlElement(name = "OrgnlDbtrAcct")
    protected CashAccount24 orgnlDbtrAcct;
    @XmlElement(name = "OrgnlDbtrAgt")
    protected BranchAndFinancialInstitutionIdentification5 orgnlDbtrAgt;
    @XmlElement(name = "OrgnlDbtrAgtAcct")
    protected CashAccount24 orgnlDbtrAgtAcct;
    @XmlElement(name = "OrgnlFnlColltnDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar orgnlFnlColltnDt;
    @XmlElement(name = "OrgnlFrqcy")
    protected Frequency21Choice orgnlFrqcy;
    @XmlElement(name = "OrgnlRsn")
    protected MandateSetupReason1Choice orgnlRsn;

    /**
     * Gets the value of the orgnlMndtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMndtId() {
        return orgnlMndtId;
    }

    /**
     * Sets the value of the orgnlMndtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMndtId(String value) {
        this.orgnlMndtId = value;
    }

    /**
     * Gets the value of the orgnlCdtrSchmeId property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getOrgnlCdtrSchmeId() {
        return orgnlCdtrSchmeId;
    }

    /**
     * Sets the value of the orgnlCdtrSchmeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setOrgnlCdtrSchmeId(PartyIdentification43 value) {
        this.orgnlCdtrSchmeId = value;
    }

    /**
     * Gets the value of the orgnlCdtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getOrgnlCdtrAgt() {
        return orgnlCdtrAgt;
    }

    /**
     * Sets the value of the orgnlCdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setOrgnlCdtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.orgnlCdtrAgt = value;
    }

    /**
     * Gets the value of the orgnlCdtrAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getOrgnlCdtrAgtAcct() {
        return orgnlCdtrAgtAcct;
    }

    /**
     * Sets the value of the orgnlCdtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setOrgnlCdtrAgtAcct(CashAccount24 value) {
        this.orgnlCdtrAgtAcct = value;
    }

    /**
     * Gets the value of the orgnlDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification43 }
     *     
     */
    public PartyIdentification43 getOrgnlDbtr() {
        return orgnlDbtr;
    }

    /**
     * Sets the value of the orgnlDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification43 }
     *     
     */
    public void setOrgnlDbtr(PartyIdentification43 value) {
        this.orgnlDbtr = value;
    }

    /**
     * Gets the value of the orgnlDbtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getOrgnlDbtrAcct() {
        return orgnlDbtrAcct;
    }

    /**
     * Sets the value of the orgnlDbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setOrgnlDbtrAcct(CashAccount24 value) {
        this.orgnlDbtrAcct = value;
    }

    /**
     * Gets the value of the orgnlDbtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getOrgnlDbtrAgt() {
        return orgnlDbtrAgt;
    }

    /**
     * Sets the value of the orgnlDbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setOrgnlDbtrAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.orgnlDbtrAgt = value;
    }

    /**
     * Gets the value of the orgnlDbtrAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getOrgnlDbtrAgtAcct() {
        return orgnlDbtrAgtAcct;
    }

    /**
     * Sets the value of the orgnlDbtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setOrgnlDbtrAgtAcct(CashAccount24 value) {
        this.orgnlDbtrAgtAcct = value;
    }

    /**
     * Gets the value of the orgnlFnlColltnDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrgnlFnlColltnDt() {
        return orgnlFnlColltnDt;
    }

    /**
     * Sets the value of the orgnlFnlColltnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrgnlFnlColltnDt(XMLGregorianCalendar value) {
        this.orgnlFnlColltnDt = value;
    }

    /**
     * Gets the value of the orgnlFrqcy property.
     * 
     * @return
     *     possible object is
     *     {@link Frequency21Choice }
     *     
     */
    public Frequency21Choice getOrgnlFrqcy() {
        return orgnlFrqcy;
    }

    /**
     * Sets the value of the orgnlFrqcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Frequency21Choice }
     *     
     */
    public void setOrgnlFrqcy(Frequency21Choice value) {
        this.orgnlFrqcy = value;
    }

    /**
     * Gets the value of the orgnlRsn property.
     * 
     * @return
     *     possible object is
     *     {@link MandateSetupReason1Choice }
     *     
     */
    public MandateSetupReason1Choice getOrgnlRsn() {
        return orgnlRsn;
    }

    /**
     * Sets the value of the orgnlRsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateSetupReason1Choice }
     *     
     */
    public void setOrgnlRsn(MandateSetupReason1Choice value) {
        this.orgnlRsn = value;
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
            String theOrgnlMndtId;
            theOrgnlMndtId = this.getOrgnlMndtId();
            strategy.appendField(locator, this, "orgnlMndtId", buffer, theOrgnlMndtId, (this.orgnlMndtId!= null));
        }
        {
            PartyIdentification43 theOrgnlCdtrSchmeId;
            theOrgnlCdtrSchmeId = this.getOrgnlCdtrSchmeId();
            strategy.appendField(locator, this, "orgnlCdtrSchmeId", buffer, theOrgnlCdtrSchmeId, (this.orgnlCdtrSchmeId!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theOrgnlCdtrAgt;
            theOrgnlCdtrAgt = this.getOrgnlCdtrAgt();
            strategy.appendField(locator, this, "orgnlCdtrAgt", buffer, theOrgnlCdtrAgt, (this.orgnlCdtrAgt!= null));
        }
        {
            CashAccount24 theOrgnlCdtrAgtAcct;
            theOrgnlCdtrAgtAcct = this.getOrgnlCdtrAgtAcct();
            strategy.appendField(locator, this, "orgnlCdtrAgtAcct", buffer, theOrgnlCdtrAgtAcct, (this.orgnlCdtrAgtAcct!= null));
        }
        {
            PartyIdentification43 theOrgnlDbtr;
            theOrgnlDbtr = this.getOrgnlDbtr();
            strategy.appendField(locator, this, "orgnlDbtr", buffer, theOrgnlDbtr, (this.orgnlDbtr!= null));
        }
        {
            CashAccount24 theOrgnlDbtrAcct;
            theOrgnlDbtrAcct = this.getOrgnlDbtrAcct();
            strategy.appendField(locator, this, "orgnlDbtrAcct", buffer, theOrgnlDbtrAcct, (this.orgnlDbtrAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theOrgnlDbtrAgt;
            theOrgnlDbtrAgt = this.getOrgnlDbtrAgt();
            strategy.appendField(locator, this, "orgnlDbtrAgt", buffer, theOrgnlDbtrAgt, (this.orgnlDbtrAgt!= null));
        }
        {
            CashAccount24 theOrgnlDbtrAgtAcct;
            theOrgnlDbtrAgtAcct = this.getOrgnlDbtrAgtAcct();
            strategy.appendField(locator, this, "orgnlDbtrAgtAcct", buffer, theOrgnlDbtrAgtAcct, (this.orgnlDbtrAgtAcct!= null));
        }
        {
            XMLGregorianCalendar theOrgnlFnlColltnDt;
            theOrgnlFnlColltnDt = this.getOrgnlFnlColltnDt();
            strategy.appendField(locator, this, "orgnlFnlColltnDt", buffer, theOrgnlFnlColltnDt, (this.orgnlFnlColltnDt!= null));
        }
        {
            Frequency21Choice theOrgnlFrqcy;
            theOrgnlFrqcy = this.getOrgnlFrqcy();
            strategy.appendField(locator, this, "orgnlFrqcy", buffer, theOrgnlFrqcy, (this.orgnlFrqcy!= null));
        }
        {
            MandateSetupReason1Choice theOrgnlRsn;
            theOrgnlRsn = this.getOrgnlRsn();
            strategy.appendField(locator, this, "orgnlRsn", buffer, theOrgnlRsn, (this.orgnlRsn!= null));
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
        final AmendmentInformationDetails10 that = ((AmendmentInformationDetails10) object);
        {
            String lhsOrgnlMndtId;
            lhsOrgnlMndtId = this.getOrgnlMndtId();
            String rhsOrgnlMndtId;
            rhsOrgnlMndtId = that.getOrgnlMndtId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlMndtId", lhsOrgnlMndtId), LocatorUtils.property(thatLocator, "orgnlMndtId", rhsOrgnlMndtId), lhsOrgnlMndtId, rhsOrgnlMndtId, (this.orgnlMndtId!= null), (that.orgnlMndtId!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsOrgnlCdtrSchmeId;
            lhsOrgnlCdtrSchmeId = this.getOrgnlCdtrSchmeId();
            PartyIdentification43 rhsOrgnlCdtrSchmeId;
            rhsOrgnlCdtrSchmeId = that.getOrgnlCdtrSchmeId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlCdtrSchmeId", lhsOrgnlCdtrSchmeId), LocatorUtils.property(thatLocator, "orgnlCdtrSchmeId", rhsOrgnlCdtrSchmeId), lhsOrgnlCdtrSchmeId, rhsOrgnlCdtrSchmeId, (this.orgnlCdtrSchmeId!= null), (that.orgnlCdtrSchmeId!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsOrgnlCdtrAgt;
            lhsOrgnlCdtrAgt = this.getOrgnlCdtrAgt();
            BranchAndFinancialInstitutionIdentification5 rhsOrgnlCdtrAgt;
            rhsOrgnlCdtrAgt = that.getOrgnlCdtrAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlCdtrAgt", lhsOrgnlCdtrAgt), LocatorUtils.property(thatLocator, "orgnlCdtrAgt", rhsOrgnlCdtrAgt), lhsOrgnlCdtrAgt, rhsOrgnlCdtrAgt, (this.orgnlCdtrAgt!= null), (that.orgnlCdtrAgt!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsOrgnlCdtrAgtAcct;
            lhsOrgnlCdtrAgtAcct = this.getOrgnlCdtrAgtAcct();
            CashAccount24 rhsOrgnlCdtrAgtAcct;
            rhsOrgnlCdtrAgtAcct = that.getOrgnlCdtrAgtAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlCdtrAgtAcct", lhsOrgnlCdtrAgtAcct), LocatorUtils.property(thatLocator, "orgnlCdtrAgtAcct", rhsOrgnlCdtrAgtAcct), lhsOrgnlCdtrAgtAcct, rhsOrgnlCdtrAgtAcct, (this.orgnlCdtrAgtAcct!= null), (that.orgnlCdtrAgtAcct!= null))) {
                return false;
            }
        }
        {
            PartyIdentification43 lhsOrgnlDbtr;
            lhsOrgnlDbtr = this.getOrgnlDbtr();
            PartyIdentification43 rhsOrgnlDbtr;
            rhsOrgnlDbtr = that.getOrgnlDbtr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlDbtr", lhsOrgnlDbtr), LocatorUtils.property(thatLocator, "orgnlDbtr", rhsOrgnlDbtr), lhsOrgnlDbtr, rhsOrgnlDbtr, (this.orgnlDbtr!= null), (that.orgnlDbtr!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsOrgnlDbtrAcct;
            lhsOrgnlDbtrAcct = this.getOrgnlDbtrAcct();
            CashAccount24 rhsOrgnlDbtrAcct;
            rhsOrgnlDbtrAcct = that.getOrgnlDbtrAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlDbtrAcct", lhsOrgnlDbtrAcct), LocatorUtils.property(thatLocator, "orgnlDbtrAcct", rhsOrgnlDbtrAcct), lhsOrgnlDbtrAcct, rhsOrgnlDbtrAcct, (this.orgnlDbtrAcct!= null), (that.orgnlDbtrAcct!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsOrgnlDbtrAgt;
            lhsOrgnlDbtrAgt = this.getOrgnlDbtrAgt();
            BranchAndFinancialInstitutionIdentification5 rhsOrgnlDbtrAgt;
            rhsOrgnlDbtrAgt = that.getOrgnlDbtrAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlDbtrAgt", lhsOrgnlDbtrAgt), LocatorUtils.property(thatLocator, "orgnlDbtrAgt", rhsOrgnlDbtrAgt), lhsOrgnlDbtrAgt, rhsOrgnlDbtrAgt, (this.orgnlDbtrAgt!= null), (that.orgnlDbtrAgt!= null))) {
                return false;
            }
        }
        {
            CashAccount24 lhsOrgnlDbtrAgtAcct;
            lhsOrgnlDbtrAgtAcct = this.getOrgnlDbtrAgtAcct();
            CashAccount24 rhsOrgnlDbtrAgtAcct;
            rhsOrgnlDbtrAgtAcct = that.getOrgnlDbtrAgtAcct();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlDbtrAgtAcct", lhsOrgnlDbtrAgtAcct), LocatorUtils.property(thatLocator, "orgnlDbtrAgtAcct", rhsOrgnlDbtrAgtAcct), lhsOrgnlDbtrAgtAcct, rhsOrgnlDbtrAgtAcct, (this.orgnlDbtrAgtAcct!= null), (that.orgnlDbtrAgtAcct!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsOrgnlFnlColltnDt;
            lhsOrgnlFnlColltnDt = this.getOrgnlFnlColltnDt();
            XMLGregorianCalendar rhsOrgnlFnlColltnDt;
            rhsOrgnlFnlColltnDt = that.getOrgnlFnlColltnDt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlFnlColltnDt", lhsOrgnlFnlColltnDt), LocatorUtils.property(thatLocator, "orgnlFnlColltnDt", rhsOrgnlFnlColltnDt), lhsOrgnlFnlColltnDt, rhsOrgnlFnlColltnDt, (this.orgnlFnlColltnDt!= null), (that.orgnlFnlColltnDt!= null))) {
                return false;
            }
        }
        {
            Frequency21Choice lhsOrgnlFrqcy;
            lhsOrgnlFrqcy = this.getOrgnlFrqcy();
            Frequency21Choice rhsOrgnlFrqcy;
            rhsOrgnlFrqcy = that.getOrgnlFrqcy();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlFrqcy", lhsOrgnlFrqcy), LocatorUtils.property(thatLocator, "orgnlFrqcy", rhsOrgnlFrqcy), lhsOrgnlFrqcy, rhsOrgnlFrqcy, (this.orgnlFrqcy!= null), (that.orgnlFrqcy!= null))) {
                return false;
            }
        }
        {
            MandateSetupReason1Choice lhsOrgnlRsn;
            lhsOrgnlRsn = this.getOrgnlRsn();
            MandateSetupReason1Choice rhsOrgnlRsn;
            rhsOrgnlRsn = that.getOrgnlRsn();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlRsn", lhsOrgnlRsn), LocatorUtils.property(thatLocator, "orgnlRsn", rhsOrgnlRsn), lhsOrgnlRsn, rhsOrgnlRsn, (this.orgnlRsn!= null), (that.orgnlRsn!= null))) {
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
            String theOrgnlMndtId;
            theOrgnlMndtId = this.getOrgnlMndtId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlMndtId", theOrgnlMndtId), currentHashCode, theOrgnlMndtId, (this.orgnlMndtId!= null));
        }
        {
            PartyIdentification43 theOrgnlCdtrSchmeId;
            theOrgnlCdtrSchmeId = this.getOrgnlCdtrSchmeId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlCdtrSchmeId", theOrgnlCdtrSchmeId), currentHashCode, theOrgnlCdtrSchmeId, (this.orgnlCdtrSchmeId!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theOrgnlCdtrAgt;
            theOrgnlCdtrAgt = this.getOrgnlCdtrAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlCdtrAgt", theOrgnlCdtrAgt), currentHashCode, theOrgnlCdtrAgt, (this.orgnlCdtrAgt!= null));
        }
        {
            CashAccount24 theOrgnlCdtrAgtAcct;
            theOrgnlCdtrAgtAcct = this.getOrgnlCdtrAgtAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlCdtrAgtAcct", theOrgnlCdtrAgtAcct), currentHashCode, theOrgnlCdtrAgtAcct, (this.orgnlCdtrAgtAcct!= null));
        }
        {
            PartyIdentification43 theOrgnlDbtr;
            theOrgnlDbtr = this.getOrgnlDbtr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlDbtr", theOrgnlDbtr), currentHashCode, theOrgnlDbtr, (this.orgnlDbtr!= null));
        }
        {
            CashAccount24 theOrgnlDbtrAcct;
            theOrgnlDbtrAcct = this.getOrgnlDbtrAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlDbtrAcct", theOrgnlDbtrAcct), currentHashCode, theOrgnlDbtrAcct, (this.orgnlDbtrAcct!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theOrgnlDbtrAgt;
            theOrgnlDbtrAgt = this.getOrgnlDbtrAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlDbtrAgt", theOrgnlDbtrAgt), currentHashCode, theOrgnlDbtrAgt, (this.orgnlDbtrAgt!= null));
        }
        {
            CashAccount24 theOrgnlDbtrAgtAcct;
            theOrgnlDbtrAgtAcct = this.getOrgnlDbtrAgtAcct();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlDbtrAgtAcct", theOrgnlDbtrAgtAcct), currentHashCode, theOrgnlDbtrAgtAcct, (this.orgnlDbtrAgtAcct!= null));
        }
        {
            XMLGregorianCalendar theOrgnlFnlColltnDt;
            theOrgnlFnlColltnDt = this.getOrgnlFnlColltnDt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlFnlColltnDt", theOrgnlFnlColltnDt), currentHashCode, theOrgnlFnlColltnDt, (this.orgnlFnlColltnDt!= null));
        }
        {
            Frequency21Choice theOrgnlFrqcy;
            theOrgnlFrqcy = this.getOrgnlFrqcy();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlFrqcy", theOrgnlFrqcy), currentHashCode, theOrgnlFrqcy, (this.orgnlFrqcy!= null));
        }
        {
            MandateSetupReason1Choice theOrgnlRsn;
            theOrgnlRsn = this.getOrgnlRsn();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlRsn", theOrgnlRsn), currentHashCode, theOrgnlRsn, (this.orgnlRsn!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
