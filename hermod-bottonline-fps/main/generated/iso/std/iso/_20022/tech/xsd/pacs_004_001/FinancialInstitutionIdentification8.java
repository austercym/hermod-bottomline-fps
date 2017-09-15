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
 * <p>Java class for FinancialInstitutionIdentification8 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FinancialInstitutionIdentification8"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BICFI" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}BICFIIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="ClrSysMmbId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}ClearingSystemMemberIdentification2" minOccurs="0"/&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="PstlAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}PostalAddress6" minOccurs="0"/&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.05}GenericFinancialIdentification1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionIdentification8", propOrder = {
    "bicfi",
    "clrSysMmbId",
    "nm",
    "pstlAdr",
    "othr"
})
public class FinancialInstitutionIdentification8 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "BICFI")
    protected String bicfi;
    @XmlElement(name = "ClrSysMmbId")
    protected ClearingSystemMemberIdentification2 clrSysMmbId;
    @XmlElement(name = "Nm")
    protected String nm;
    @XmlElement(name = "PstlAdr")
    protected PostalAddress6 pstlAdr;
    @XmlElement(name = "Othr")
    protected GenericFinancialIdentification1 othr;

    /**
     * Gets the value of the bicfi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBICFI() {
        return bicfi;
    }

    /**
     * Sets the value of the bicfi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBICFI(String value) {
        this.bicfi = value;
    }

    /**
     * Gets the value of the clrSysMmbId property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemMemberIdentification2 }
     *     
     */
    public ClearingSystemMemberIdentification2 getClrSysMmbId() {
        return clrSysMmbId;
    }

    /**
     * Sets the value of the clrSysMmbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemMemberIdentification2 }
     *     
     */
    public void setClrSysMmbId(ClearingSystemMemberIdentification2 value) {
        this.clrSysMmbId = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the pstlAdr property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddress6 }
     *     
     */
    public PostalAddress6 getPstlAdr() {
        return pstlAdr;
    }

    /**
     * Sets the value of the pstlAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddress6 }
     *     
     */
    public void setPstlAdr(PostalAddress6 value) {
        this.pstlAdr = value;
    }

    /**
     * Gets the value of the othr property.
     * 
     * @return
     *     possible object is
     *     {@link GenericFinancialIdentification1 }
     *     
     */
    public GenericFinancialIdentification1 getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericFinancialIdentification1 }
     *     
     */
    public void setOthr(GenericFinancialIdentification1 value) {
        this.othr = value;
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
            String theBICFI;
            theBICFI = this.getBICFI();
            strategy.appendField(locator, this, "bicfi", buffer, theBICFI, (this.bicfi!= null));
        }
        {
            ClearingSystemMemberIdentification2 theClrSysMmbId;
            theClrSysMmbId = this.getClrSysMmbId();
            strategy.appendField(locator, this, "clrSysMmbId", buffer, theClrSysMmbId, (this.clrSysMmbId!= null));
        }
        {
            String theNm;
            theNm = this.getNm();
            strategy.appendField(locator, this, "nm", buffer, theNm, (this.nm!= null));
        }
        {
            PostalAddress6 thePstlAdr;
            thePstlAdr = this.getPstlAdr();
            strategy.appendField(locator, this, "pstlAdr", buffer, thePstlAdr, (this.pstlAdr!= null));
        }
        {
            GenericFinancialIdentification1 theOthr;
            theOthr = this.getOthr();
            strategy.appendField(locator, this, "othr", buffer, theOthr, (this.othr!= null));
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
        final FinancialInstitutionIdentification8 that = ((FinancialInstitutionIdentification8) object);
        {
            String lhsBICFI;
            lhsBICFI = this.getBICFI();
            String rhsBICFI;
            rhsBICFI = that.getBICFI();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "bicfi", lhsBICFI), LocatorUtils.property(thatLocator, "bicfi", rhsBICFI), lhsBICFI, rhsBICFI, (this.bicfi!= null), (that.bicfi!= null))) {
                return false;
            }
        }
        {
            ClearingSystemMemberIdentification2 lhsClrSysMmbId;
            lhsClrSysMmbId = this.getClrSysMmbId();
            ClearingSystemMemberIdentification2 rhsClrSysMmbId;
            rhsClrSysMmbId = that.getClrSysMmbId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "clrSysMmbId", lhsClrSysMmbId), LocatorUtils.property(thatLocator, "clrSysMmbId", rhsClrSysMmbId), lhsClrSysMmbId, rhsClrSysMmbId, (this.clrSysMmbId!= null), (that.clrSysMmbId!= null))) {
                return false;
            }
        }
        {
            String lhsNm;
            lhsNm = this.getNm();
            String rhsNm;
            rhsNm = that.getNm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "nm", lhsNm), LocatorUtils.property(thatLocator, "nm", rhsNm), lhsNm, rhsNm, (this.nm!= null), (that.nm!= null))) {
                return false;
            }
        }
        {
            PostalAddress6 lhsPstlAdr;
            lhsPstlAdr = this.getPstlAdr();
            PostalAddress6 rhsPstlAdr;
            rhsPstlAdr = that.getPstlAdr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "pstlAdr", lhsPstlAdr), LocatorUtils.property(thatLocator, "pstlAdr", rhsPstlAdr), lhsPstlAdr, rhsPstlAdr, (this.pstlAdr!= null), (that.pstlAdr!= null))) {
                return false;
            }
        }
        {
            GenericFinancialIdentification1 lhsOthr;
            lhsOthr = this.getOthr();
            GenericFinancialIdentification1 rhsOthr;
            rhsOthr = that.getOthr();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "othr", lhsOthr), LocatorUtils.property(thatLocator, "othr", rhsOthr), lhsOthr, rhsOthr, (this.othr!= null), (that.othr!= null))) {
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
            String theBICFI;
            theBICFI = this.getBICFI();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "bicfi", theBICFI), currentHashCode, theBICFI, (this.bicfi!= null));
        }
        {
            ClearingSystemMemberIdentification2 theClrSysMmbId;
            theClrSysMmbId = this.getClrSysMmbId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "clrSysMmbId", theClrSysMmbId), currentHashCode, theClrSysMmbId, (this.clrSysMmbId!= null));
        }
        {
            String theNm;
            theNm = this.getNm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "nm", theNm), currentHashCode, theNm, (this.nm!= null));
        }
        {
            PostalAddress6 thePstlAdr;
            thePstlAdr = this.getPstlAdr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "pstlAdr", thePstlAdr), currentHashCode, thePstlAdr, (this.pstlAdr!= null));
        }
        {
            GenericFinancialIdentification1 theOthr;
            theOthr = this.getOthr();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "othr", theOthr), currentHashCode, theOthr, (this.othr!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
