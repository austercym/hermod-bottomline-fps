//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_002_001;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 * <p>Java class for TaxInformation4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxInformation4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}TaxParty1" minOccurs="0"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}TaxParty2" minOccurs="0"/&gt;
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}TaxParty2" minOccurs="0"/&gt;
 *         &lt;element name="AdmstnZone" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="RefNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="Mtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="TtlTaxblBaseAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="TtlTaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="SeqNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Number" minOccurs="0"/&gt;
 *         &lt;element name="Rcrd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}TaxRecord1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxInformation4", propOrder = {
    "cdtr",
    "dbtr",
    "ultmtDbtr",
    "admstnZone",
    "refNb",
    "mtd",
    "ttlTaxblBaseAmt",
    "ttlTaxAmt",
    "dt",
    "seqNb",
    "rcrd"
})
public class TaxInformation4 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Cdtr")
    protected TaxParty1 cdtr;
    @XmlElement(name = "Dbtr")
    protected TaxParty2 dbtr;
    @XmlElement(name = "UltmtDbtr")
    protected TaxParty2 ultmtDbtr;
    @XmlElement(name = "AdmstnZone")
    protected String admstnZone;
    @XmlElement(name = "RefNb")
    protected String refNb;
    @XmlElement(name = "Mtd")
    protected String mtd;
    @XmlElement(name = "TtlTaxblBaseAmt")
    protected ActiveOrHistoricCurrencyAndAmount ttlTaxblBaseAmt;
    @XmlElement(name = "TtlTaxAmt")
    protected ActiveOrHistoricCurrencyAndAmount ttlTaxAmt;
    @XmlElement(name = "Dt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    @XmlElement(name = "SeqNb")
    protected BigDecimal seqNb;
    @XmlElement(name = "Rcrd")
    protected List<TaxRecord1> rcrd;

    /**
     * Gets the value of the cdtr property.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty1 }
     *     
     */
    public TaxParty1 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty1 }
     *     
     */
    public void setCdtr(TaxParty1 value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the dbtr property.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty2 }
     *     
     */
    public TaxParty2 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty2 }
     *     
     */
    public void setDbtr(TaxParty2 value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty2 }
     *     
     */
    public TaxParty2 getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty2 }
     *     
     */
    public void setUltmtDbtr(TaxParty2 value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the admstnZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmstnZone() {
        return admstnZone;
    }

    /**
     * Sets the value of the admstnZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdmstnZone(String value) {
        this.admstnZone = value;
    }

    /**
     * Gets the value of the refNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNb() {
        return refNb;
    }

    /**
     * Sets the value of the refNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNb(String value) {
        this.refNb = value;
    }

    /**
     * Gets the value of the mtd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtd() {
        return mtd;
    }

    /**
     * Sets the value of the mtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMtd(String value) {
        this.mtd = value;
    }

    /**
     * Gets the value of the ttlTaxblBaseAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTtlTaxblBaseAmt() {
        return ttlTaxblBaseAmt;
    }

    /**
     * Sets the value of the ttlTaxblBaseAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setTtlTaxblBaseAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.ttlTaxblBaseAmt = value;
    }

    /**
     * Gets the value of the ttlTaxAmt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTtlTaxAmt() {
        return ttlTaxAmt;
    }

    /**
     * Sets the value of the ttlTaxAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setTtlTaxAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.ttlTaxAmt = value;
    }

    /**
     * Gets the value of the dt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Gets the value of the seqNb property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSeqNb() {
        return seqNb;
    }

    /**
     * Sets the value of the seqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSeqNb(BigDecimal value) {
        this.seqNb = value;
    }

    /**
     * Gets the value of the rcrd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rcrd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRcrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxRecord1 }
     * 
     * 
     */
    public List<TaxRecord1> getRcrd() {
        if (rcrd == null) {
            rcrd = new ArrayList<TaxRecord1>();
        }
        return this.rcrd;
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
            TaxParty1 theCdtr;
            theCdtr = this.getCdtr();
            strategy.appendField(locator, this, "cdtr", buffer, theCdtr, (this.cdtr!= null));
        }
        {
            TaxParty2 theDbtr;
            theDbtr = this.getDbtr();
            strategy.appendField(locator, this, "dbtr", buffer, theDbtr, (this.dbtr!= null));
        }
        {
            TaxParty2 theUltmtDbtr;
            theUltmtDbtr = this.getUltmtDbtr();
            strategy.appendField(locator, this, "ultmtDbtr", buffer, theUltmtDbtr, (this.ultmtDbtr!= null));
        }
        {
            String theAdmstnZone;
            theAdmstnZone = this.getAdmstnZone();
            strategy.appendField(locator, this, "admstnZone", buffer, theAdmstnZone, (this.admstnZone!= null));
        }
        {
            String theRefNb;
            theRefNb = this.getRefNb();
            strategy.appendField(locator, this, "refNb", buffer, theRefNb, (this.refNb!= null));
        }
        {
            String theMtd;
            theMtd = this.getMtd();
            strategy.appendField(locator, this, "mtd", buffer, theMtd, (this.mtd!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theTtlTaxblBaseAmt;
            theTtlTaxblBaseAmt = this.getTtlTaxblBaseAmt();
            strategy.appendField(locator, this, "ttlTaxblBaseAmt", buffer, theTtlTaxblBaseAmt, (this.ttlTaxblBaseAmt!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theTtlTaxAmt;
            theTtlTaxAmt = this.getTtlTaxAmt();
            strategy.appendField(locator, this, "ttlTaxAmt", buffer, theTtlTaxAmt, (this.ttlTaxAmt!= null));
        }
        {
            XMLGregorianCalendar theDt;
            theDt = this.getDt();
            strategy.appendField(locator, this, "dt", buffer, theDt, (this.dt!= null));
        }
        {
            BigDecimal theSeqNb;
            theSeqNb = this.getSeqNb();
            strategy.appendField(locator, this, "seqNb", buffer, theSeqNb, (this.seqNb!= null));
        }
        {
            List<TaxRecord1> theRcrd;
            theRcrd = (((this.rcrd!= null)&&(!this.rcrd.isEmpty()))?this.getRcrd():null);
            strategy.appendField(locator, this, "rcrd", buffer, theRcrd, ((this.rcrd!= null)&&(!this.rcrd.isEmpty())));
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
        final TaxInformation4 that = ((TaxInformation4) object);
        {
            TaxParty1 lhsCdtr;
            lhsCdtr = this.getCdtr();
            TaxParty1 rhsCdtr;
            rhsCdtr = that.getCdtr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "cdtr", lhsCdtr), LocatorUtils.property(thatLocator, "cdtr", rhsCdtr), lhsCdtr, rhsCdtr, (this.cdtr!= null), (that.cdtr!= null))) {
                return false;
            }
        }
        {
            TaxParty2 lhsDbtr;
            lhsDbtr = this.getDbtr();
            TaxParty2 rhsDbtr;
            rhsDbtr = that.getDbtr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dbtr", lhsDbtr), LocatorUtils.property(thatLocator, "dbtr", rhsDbtr), lhsDbtr, rhsDbtr, (this.dbtr!= null), (that.dbtr!= null))) {
                return false;
            }
        }
        {
            TaxParty2 lhsUltmtDbtr;
            lhsUltmtDbtr = this.getUltmtDbtr();
            TaxParty2 rhsUltmtDbtr;
            rhsUltmtDbtr = that.getUltmtDbtr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ultmtDbtr", lhsUltmtDbtr), LocatorUtils.property(thatLocator, "ultmtDbtr", rhsUltmtDbtr), lhsUltmtDbtr, rhsUltmtDbtr, (this.ultmtDbtr!= null), (that.ultmtDbtr!= null))) {
                return false;
            }
        }
        {
            String lhsAdmstnZone;
            lhsAdmstnZone = this.getAdmstnZone();
            String rhsAdmstnZone;
            rhsAdmstnZone = that.getAdmstnZone();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "admstnZone", lhsAdmstnZone), LocatorUtils.property(thatLocator, "admstnZone", rhsAdmstnZone), lhsAdmstnZone, rhsAdmstnZone, (this.admstnZone!= null), (that.admstnZone!= null))) {
                return false;
            }
        }
        {
            String lhsRefNb;
            lhsRefNb = this.getRefNb();
            String rhsRefNb;
            rhsRefNb = that.getRefNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "refNb", lhsRefNb), LocatorUtils.property(thatLocator, "refNb", rhsRefNb), lhsRefNb, rhsRefNb, (this.refNb!= null), (that.refNb!= null))) {
                return false;
            }
        }
        {
            String lhsMtd;
            lhsMtd = this.getMtd();
            String rhsMtd;
            rhsMtd = that.getMtd();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "mtd", lhsMtd), LocatorUtils.property(thatLocator, "mtd", rhsMtd), lhsMtd, rhsMtd, (this.mtd!= null), (that.mtd!= null))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsTtlTaxblBaseAmt;
            lhsTtlTaxblBaseAmt = this.getTtlTaxblBaseAmt();
            ActiveOrHistoricCurrencyAndAmount rhsTtlTaxblBaseAmt;
            rhsTtlTaxblBaseAmt = that.getTtlTaxblBaseAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ttlTaxblBaseAmt", lhsTtlTaxblBaseAmt), LocatorUtils.property(thatLocator, "ttlTaxblBaseAmt", rhsTtlTaxblBaseAmt), lhsTtlTaxblBaseAmt, rhsTtlTaxblBaseAmt, (this.ttlTaxblBaseAmt!= null), (that.ttlTaxblBaseAmt!= null))) {
                return false;
            }
        }
        {
            ActiveOrHistoricCurrencyAndAmount lhsTtlTaxAmt;
            lhsTtlTaxAmt = this.getTtlTaxAmt();
            ActiveOrHistoricCurrencyAndAmount rhsTtlTaxAmt;
            rhsTtlTaxAmt = that.getTtlTaxAmt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ttlTaxAmt", lhsTtlTaxAmt), LocatorUtils.property(thatLocator, "ttlTaxAmt", rhsTtlTaxAmt), lhsTtlTaxAmt, rhsTtlTaxAmt, (this.ttlTaxAmt!= null), (that.ttlTaxAmt!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsDt;
            lhsDt = this.getDt();
            XMLGregorianCalendar rhsDt;
            rhsDt = that.getDt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dt", lhsDt), LocatorUtils.property(thatLocator, "dt", rhsDt), lhsDt, rhsDt, (this.dt!= null), (that.dt!= null))) {
                return false;
            }
        }
        {
            BigDecimal lhsSeqNb;
            lhsSeqNb = this.getSeqNb();
            BigDecimal rhsSeqNb;
            rhsSeqNb = that.getSeqNb();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "seqNb", lhsSeqNb), LocatorUtils.property(thatLocator, "seqNb", rhsSeqNb), lhsSeqNb, rhsSeqNb, (this.seqNb!= null), (that.seqNb!= null))) {
                return false;
            }
        }
        {
            List<TaxRecord1> lhsRcrd;
            lhsRcrd = (((this.rcrd!= null)&&(!this.rcrd.isEmpty()))?this.getRcrd():null);
            List<TaxRecord1> rhsRcrd;
            rhsRcrd = (((that.rcrd!= null)&&(!that.rcrd.isEmpty()))?that.getRcrd():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "rcrd", lhsRcrd), LocatorUtils.property(thatLocator, "rcrd", rhsRcrd), lhsRcrd, rhsRcrd, ((this.rcrd!= null)&&(!this.rcrd.isEmpty())), ((that.rcrd!= null)&&(!that.rcrd.isEmpty())))) {
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
            TaxParty1 theCdtr;
            theCdtr = this.getCdtr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "cdtr", theCdtr), currentHashCode, theCdtr, (this.cdtr!= null));
        }
        {
            TaxParty2 theDbtr;
            theDbtr = this.getDbtr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dbtr", theDbtr), currentHashCode, theDbtr, (this.dbtr!= null));
        }
        {
            TaxParty2 theUltmtDbtr;
            theUltmtDbtr = this.getUltmtDbtr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ultmtDbtr", theUltmtDbtr), currentHashCode, theUltmtDbtr, (this.ultmtDbtr!= null));
        }
        {
            String theAdmstnZone;
            theAdmstnZone = this.getAdmstnZone();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "admstnZone", theAdmstnZone), currentHashCode, theAdmstnZone, (this.admstnZone!= null));
        }
        {
            String theRefNb;
            theRefNb = this.getRefNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "refNb", theRefNb), currentHashCode, theRefNb, (this.refNb!= null));
        }
        {
            String theMtd;
            theMtd = this.getMtd();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "mtd", theMtd), currentHashCode, theMtd, (this.mtd!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theTtlTaxblBaseAmt;
            theTtlTaxblBaseAmt = this.getTtlTaxblBaseAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ttlTaxblBaseAmt", theTtlTaxblBaseAmt), currentHashCode, theTtlTaxblBaseAmt, (this.ttlTaxblBaseAmt!= null));
        }
        {
            ActiveOrHistoricCurrencyAndAmount theTtlTaxAmt;
            theTtlTaxAmt = this.getTtlTaxAmt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ttlTaxAmt", theTtlTaxAmt), currentHashCode, theTtlTaxAmt, (this.ttlTaxAmt!= null));
        }
        {
            XMLGregorianCalendar theDt;
            theDt = this.getDt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dt", theDt), currentHashCode, theDt, (this.dt!= null));
        }
        {
            BigDecimal theSeqNb;
            theSeqNb = this.getSeqNb();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "seqNb", theSeqNb), currentHashCode, theSeqNb, (this.seqNb!= null));
        }
        {
            List<TaxRecord1> theRcrd;
            theRcrd = (((this.rcrd!= null)&&(!this.rcrd.isEmpty()))?this.getRcrd():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "rcrd", theRcrd), currentHashCode, theRcrd, ((this.rcrd!= null)&&(!this.rcrd.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
