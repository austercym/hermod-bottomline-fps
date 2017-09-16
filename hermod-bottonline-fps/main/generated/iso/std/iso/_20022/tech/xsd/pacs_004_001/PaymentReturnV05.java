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
 * <p>Java class for PaymentReturnV05 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentReturnV05"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}GroupHeader54"/&gt;
 *         &lt;element name="OrgnlGrpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}OriginalGroupHeader2" minOccurs="0"/&gt;
 *         &lt;element name="TxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PaymentTransaction50" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentReturnV05", propOrder = {
    "grpHdr",
    "orgnlGrpInf",
    "txInf",
    "splmtryData"
})
public class PaymentReturnV05 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader54 grpHdr;
    @XmlElement(name = "OrgnlGrpInf")
    protected OriginalGroupHeader2 orgnlGrpInf;
    @XmlElement(name = "TxInf")
    protected List<PaymentTransaction50> txInf;
    @XmlElement(name = "SplmtryData")
    protected List<SupplementaryData1> splmtryData;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader54 }
     *     
     */
    public GroupHeader54 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader54 }
     *     
     */
    public void setGrpHdr(GroupHeader54 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlGrpInf property.
     * 
     * @return
     *     possible object is
     *     {@link OriginalGroupHeader2 }
     *     
     */
    public OriginalGroupHeader2 getOrgnlGrpInf() {
        return orgnlGrpInf;
    }

    /**
     * Sets the value of the orgnlGrpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginalGroupHeader2 }
     *     
     */
    public void setOrgnlGrpInf(OriginalGroupHeader2 value) {
        this.orgnlGrpInf = value;
    }

    /**
     * Gets the value of the txInf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the txInf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTxInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTransaction50 }
     * 
     * 
     */
    public List<PaymentTransaction50> getTxInf() {
        if (txInf == null) {
            txInf = new ArrayList<PaymentTransaction50>();
        }
        return this.txInf;
    }

    /**
     * Gets the value of the splmtryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementaryData1 }
     * 
     * 
     */
    public List<SupplementaryData1> getSplmtryData() {
        if (splmtryData == null) {
            splmtryData = new ArrayList<SupplementaryData1>();
        }
        return this.splmtryData;
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
            GroupHeader54 theGrpHdr;
            theGrpHdr = this.getGrpHdr();
            strategy.appendField(locator, this, "grpHdr", buffer, theGrpHdr, (this.grpHdr!= null));
        }
        {
            OriginalGroupHeader2 theOrgnlGrpInf;
            theOrgnlGrpInf = this.getOrgnlGrpInf();
            strategy.appendField(locator, this, "orgnlGrpInf", buffer, theOrgnlGrpInf, (this.orgnlGrpInf!= null));
        }
        {
            List<PaymentTransaction50> theTxInf;
            theTxInf = (((this.txInf!= null)&&(!this.txInf.isEmpty()))?this.getTxInf():null);
            strategy.appendField(locator, this, "txInf", buffer, theTxInf, ((this.txInf!= null)&&(!this.txInf.isEmpty())));
        }
        {
            List<SupplementaryData1> theSplmtryData;
            theSplmtryData = (((this.splmtryData!= null)&&(!this.splmtryData.isEmpty()))?this.getSplmtryData():null);
            strategy.appendField(locator, this, "splmtryData", buffer, theSplmtryData, ((this.splmtryData!= null)&&(!this.splmtryData.isEmpty())));
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
        final PaymentReturnV05 that = ((PaymentReturnV05) object);
        {
            GroupHeader54 lhsGrpHdr;
            lhsGrpHdr = this.getGrpHdr();
            GroupHeader54 rhsGrpHdr;
            rhsGrpHdr = that.getGrpHdr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "grpHdr", lhsGrpHdr), LocatorUtils.property(thatLocator, "grpHdr", rhsGrpHdr), lhsGrpHdr, rhsGrpHdr, (this.grpHdr!= null), (that.grpHdr!= null))) {
                return false;
            }
        }
        {
            OriginalGroupHeader2 lhsOrgnlGrpInf;
            lhsOrgnlGrpInf = this.getOrgnlGrpInf();
            OriginalGroupHeader2 rhsOrgnlGrpInf;
            rhsOrgnlGrpInf = that.getOrgnlGrpInf();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "orgnlGrpInf", lhsOrgnlGrpInf), LocatorUtils.property(thatLocator, "orgnlGrpInf", rhsOrgnlGrpInf), lhsOrgnlGrpInf, rhsOrgnlGrpInf, (this.orgnlGrpInf!= null), (that.orgnlGrpInf!= null))) {
                return false;
            }
        }
        {
            List<PaymentTransaction50> lhsTxInf;
            lhsTxInf = (((this.txInf!= null)&&(!this.txInf.isEmpty()))?this.getTxInf():null);
            List<PaymentTransaction50> rhsTxInf;
            rhsTxInf = (((that.txInf!= null)&&(!that.txInf.isEmpty()))?that.getTxInf():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "txInf", lhsTxInf), LocatorUtils.property(thatLocator, "txInf", rhsTxInf), lhsTxInf, rhsTxInf, ((this.txInf!= null)&&(!this.txInf.isEmpty())), ((that.txInf!= null)&&(!that.txInf.isEmpty())))) {
                return false;
            }
        }
        {
            List<SupplementaryData1> lhsSplmtryData;
            lhsSplmtryData = (((this.splmtryData!= null)&&(!this.splmtryData.isEmpty()))?this.getSplmtryData():null);
            List<SupplementaryData1> rhsSplmtryData;
            rhsSplmtryData = (((that.splmtryData!= null)&&(!that.splmtryData.isEmpty()))?that.getSplmtryData():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "splmtryData", lhsSplmtryData), LocatorUtils.property(thatLocator, "splmtryData", rhsSplmtryData), lhsSplmtryData, rhsSplmtryData, ((this.splmtryData!= null)&&(!this.splmtryData.isEmpty())), ((that.splmtryData!= null)&&(!that.splmtryData.isEmpty())))) {
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
            GroupHeader54 theGrpHdr;
            theGrpHdr = this.getGrpHdr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "grpHdr", theGrpHdr), currentHashCode, theGrpHdr, (this.grpHdr!= null));
        }
        {
            OriginalGroupHeader2 theOrgnlGrpInf;
            theOrgnlGrpInf = this.getOrgnlGrpInf();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "orgnlGrpInf", theOrgnlGrpInf), currentHashCode, theOrgnlGrpInf, (this.orgnlGrpInf!= null));
        }
        {
            List<PaymentTransaction50> theTxInf;
            theTxInf = (((this.txInf!= null)&&(!this.txInf.isEmpty()))?this.getTxInf():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "txInf", theTxInf), currentHashCode, theTxInf, ((this.txInf!= null)&&(!this.txInf.isEmpty())));
        }
        {
            List<SupplementaryData1> theSplmtryData;
            theSplmtryData = (((this.splmtryData!= null)&&(!this.splmtryData.isEmpty()))?this.getSplmtryData():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "splmtryData", theSplmtryData), currentHashCode, theSplmtryData, ((this.splmtryData!= null)&&(!this.splmtryData.isEmpty())));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}