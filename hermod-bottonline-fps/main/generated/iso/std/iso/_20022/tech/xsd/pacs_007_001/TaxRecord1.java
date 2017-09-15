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
 * <p>Java class for TaxRecord1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxRecord1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Ctgy" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CtgyDtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="DbtrSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CertId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="FrmsCd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Prd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}TaxPeriod1" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}TaxAmount1" minOccurs="0"/&gt;
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}Max140Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxRecord1", propOrder = {
    "tp",
    "ctgy",
    "ctgyDtls",
    "dbtrSts",
    "certId",
    "frmsCd",
    "prd",
    "taxAmt",
    "addtlInf"
})
public class TaxRecord1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Tp")
    protected String tp;
    @XmlElement(name = "Ctgy")
    protected String ctgy;
    @XmlElement(name = "CtgyDtls")
    protected String ctgyDtls;
    @XmlElement(name = "DbtrSts")
    protected String dbtrSts;
    @XmlElement(name = "CertId")
    protected String certId;
    @XmlElement(name = "FrmsCd")
    protected String frmsCd;
    @XmlElement(name = "Prd")
    protected TaxPeriod1 prd;
    @XmlElement(name = "TaxAmt")
    protected TaxAmount1 taxAmt;
    @XmlElement(name = "AddtlInf")
    protected String addtlInf;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Gets the value of the ctgy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgy() {
        return ctgy;
    }

    /**
     * Sets the value of the ctgy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgy(String value) {
        this.ctgy = value;
    }

    /**
     * Gets the value of the ctgyDtls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgyDtls() {
        return ctgyDtls;
    }

    /**
     * Sets the value of the ctgyDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgyDtls(String value) {
        this.ctgyDtls = value;
    }

    /**
     * Gets the value of the dbtrSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbtrSts() {
        return dbtrSts;
    }

    /**
     * Sets the value of the dbtrSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbtrSts(String value) {
        this.dbtrSts = value;
    }

    /**
     * Gets the value of the certId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertId() {
        return certId;
    }

    /**
     * Sets the value of the certId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertId(String value) {
        this.certId = value;
    }

    /**
     * Gets the value of the frmsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrmsCd() {
        return frmsCd;
    }

    /**
     * Sets the value of the frmsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrmsCd(String value) {
        this.frmsCd = value;
    }

    /**
     * Gets the value of the prd property.
     * 
     * @return
     *     possible object is
     *     {@link TaxPeriod1 }
     *     
     */
    public TaxPeriod1 getPrd() {
        return prd;
    }

    /**
     * Sets the value of the prd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPeriod1 }
     *     
     */
    public void setPrd(TaxPeriod1 value) {
        this.prd = value;
    }

    /**
     * Gets the value of the taxAmt property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAmount1 }
     *     
     */
    public TaxAmount1 getTaxAmt() {
        return taxAmt;
    }

    /**
     * Sets the value of the taxAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAmount1 }
     *     
     */
    public void setTaxAmt(TaxAmount1 value) {
        this.taxAmt = value;
    }

    /**
     * Gets the value of the addtlInf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddtlInf() {
        return addtlInf;
    }

    /**
     * Sets the value of the addtlInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddtlInf(String value) {
        this.addtlInf = value;
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
            String theTp;
            theTp = this.getTp();
            strategy.appendField(locator, this, "tp", buffer, theTp, (this.tp!= null));
        }
        {
            String theCtgy;
            theCtgy = this.getCtgy();
            strategy.appendField(locator, this, "ctgy", buffer, theCtgy, (this.ctgy!= null));
        }
        {
            String theCtgyDtls;
            theCtgyDtls = this.getCtgyDtls();
            strategy.appendField(locator, this, "ctgyDtls", buffer, theCtgyDtls, (this.ctgyDtls!= null));
        }
        {
            String theDbtrSts;
            theDbtrSts = this.getDbtrSts();
            strategy.appendField(locator, this, "dbtrSts", buffer, theDbtrSts, (this.dbtrSts!= null));
        }
        {
            String theCertId;
            theCertId = this.getCertId();
            strategy.appendField(locator, this, "certId", buffer, theCertId, (this.certId!= null));
        }
        {
            String theFrmsCd;
            theFrmsCd = this.getFrmsCd();
            strategy.appendField(locator, this, "frmsCd", buffer, theFrmsCd, (this.frmsCd!= null));
        }
        {
            TaxPeriod1 thePrd;
            thePrd = this.getPrd();
            strategy.appendField(locator, this, "prd", buffer, thePrd, (this.prd!= null));
        }
        {
            TaxAmount1 theTaxAmt;
            theTaxAmt = this.getTaxAmt();
            strategy.appendField(locator, this, "taxAmt", buffer, theTaxAmt, (this.taxAmt!= null));
        }
        {
            String theAddtlInf;
            theAddtlInf = this.getAddtlInf();
            strategy.appendField(locator, this, "addtlInf", buffer, theAddtlInf, (this.addtlInf!= null));
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
        final TaxRecord1 that = ((TaxRecord1) object);
        {
            String lhsTp;
            lhsTp = this.getTp();
            String rhsTp;
            rhsTp = that.getTp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "tp", lhsTp), LocatorUtils.property(thatLocator, "tp", rhsTp), lhsTp, rhsTp, (this.tp!= null), (that.tp!= null))) {
                return false;
            }
        }
        {
            String lhsCtgy;
            lhsCtgy = this.getCtgy();
            String rhsCtgy;
            rhsCtgy = that.getCtgy();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ctgy", lhsCtgy), LocatorUtils.property(thatLocator, "ctgy", rhsCtgy), lhsCtgy, rhsCtgy, (this.ctgy!= null), (that.ctgy!= null))) {
                return false;
            }
        }
        {
            String lhsCtgyDtls;
            lhsCtgyDtls = this.getCtgyDtls();
            String rhsCtgyDtls;
            rhsCtgyDtls = that.getCtgyDtls();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ctgyDtls", lhsCtgyDtls), LocatorUtils.property(thatLocator, "ctgyDtls", rhsCtgyDtls), lhsCtgyDtls, rhsCtgyDtls, (this.ctgyDtls!= null), (that.ctgyDtls!= null))) {
                return false;
            }
        }
        {
            String lhsDbtrSts;
            lhsDbtrSts = this.getDbtrSts();
            String rhsDbtrSts;
            rhsDbtrSts = that.getDbtrSts();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dbtrSts", lhsDbtrSts), LocatorUtils.property(thatLocator, "dbtrSts", rhsDbtrSts), lhsDbtrSts, rhsDbtrSts, (this.dbtrSts!= null), (that.dbtrSts!= null))) {
                return false;
            }
        }
        {
            String lhsCertId;
            lhsCertId = this.getCertId();
            String rhsCertId;
            rhsCertId = that.getCertId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "certId", lhsCertId), LocatorUtils.property(thatLocator, "certId", rhsCertId), lhsCertId, rhsCertId, (this.certId!= null), (that.certId!= null))) {
                return false;
            }
        }
        {
            String lhsFrmsCd;
            lhsFrmsCd = this.getFrmsCd();
            String rhsFrmsCd;
            rhsFrmsCd = that.getFrmsCd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "frmsCd", lhsFrmsCd), LocatorUtils.property(thatLocator, "frmsCd", rhsFrmsCd), lhsFrmsCd, rhsFrmsCd, (this.frmsCd!= null), (that.frmsCd!= null))) {
                return false;
            }
        }
        {
            TaxPeriod1 lhsPrd;
            lhsPrd = this.getPrd();
            TaxPeriod1 rhsPrd;
            rhsPrd = that.getPrd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "prd", lhsPrd), LocatorUtils.property(thatLocator, "prd", rhsPrd), lhsPrd, rhsPrd, (this.prd!= null), (that.prd!= null))) {
                return false;
            }
        }
        {
            TaxAmount1 lhsTaxAmt;
            lhsTaxAmt = this.getTaxAmt();
            TaxAmount1 rhsTaxAmt;
            rhsTaxAmt = that.getTaxAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "taxAmt", lhsTaxAmt), LocatorUtils.property(thatLocator, "taxAmt", rhsTaxAmt), lhsTaxAmt, rhsTaxAmt, (this.taxAmt!= null), (that.taxAmt!= null))) {
                return false;
            }
        }
        {
            String lhsAddtlInf;
            lhsAddtlInf = this.getAddtlInf();
            String rhsAddtlInf;
            rhsAddtlInf = that.getAddtlInf();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "addtlInf", lhsAddtlInf), LocatorUtils.property(thatLocator, "addtlInf", rhsAddtlInf), lhsAddtlInf, rhsAddtlInf, (this.addtlInf!= null), (that.addtlInf!= null))) {
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
            String theTp;
            theTp = this.getTp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "tp", theTp), currentHashCode, theTp, (this.tp!= null));
        }
        {
            String theCtgy;
            theCtgy = this.getCtgy();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ctgy", theCtgy), currentHashCode, theCtgy, (this.ctgy!= null));
        }
        {
            String theCtgyDtls;
            theCtgyDtls = this.getCtgyDtls();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ctgyDtls", theCtgyDtls), currentHashCode, theCtgyDtls, (this.ctgyDtls!= null));
        }
        {
            String theDbtrSts;
            theDbtrSts = this.getDbtrSts();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dbtrSts", theDbtrSts), currentHashCode, theDbtrSts, (this.dbtrSts!= null));
        }
        {
            String theCertId;
            theCertId = this.getCertId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "certId", theCertId), currentHashCode, theCertId, (this.certId!= null));
        }
        {
            String theFrmsCd;
            theFrmsCd = this.getFrmsCd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "frmsCd", theFrmsCd), currentHashCode, theFrmsCd, (this.frmsCd!= null));
        }
        {
            TaxPeriod1 thePrd;
            thePrd = this.getPrd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "prd", thePrd), currentHashCode, thePrd, (this.prd!= null));
        }
        {
            TaxAmount1 theTaxAmt;
            theTaxAmt = this.getTaxAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "taxAmt", theTaxAmt), currentHashCode, theTaxAmt, (this.taxAmt!= null));
        }
        {
            String theAddtlInf;
            theAddtlInf = this.getAddtlInf();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "addtlInf", theAddtlInf), currentHashCode, theAddtlInf, (this.addtlInf!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
