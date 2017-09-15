//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_009_001;

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
 * <p>Java class for SupplementaryData1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementaryData1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PlcAndNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}Max350Text" minOccurs="0"/&gt;
 *         &lt;element name="Envlp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.05}SupplementaryDataEnvelope1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementaryData1", propOrder = {
    "plcAndNm",
    "envlp"
})
public class SupplementaryData1 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PlcAndNm")
    protected String plcAndNm;
    @XmlElement(name = "Envlp", required = true)
    protected SupplementaryDataEnvelope1 envlp;

    /**
     * Gets the value of the plcAndNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlcAndNm() {
        return plcAndNm;
    }

    /**
     * Sets the value of the plcAndNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlcAndNm(String value) {
        this.plcAndNm = value;
    }

    /**
     * Gets the value of the envlp property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementaryDataEnvelope1 }
     *     
     */
    public SupplementaryDataEnvelope1 getEnvlp() {
        return envlp;
    }

    /**
     * Sets the value of the envlp property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementaryDataEnvelope1 }
     *     
     */
    public void setEnvlp(SupplementaryDataEnvelope1 value) {
        this.envlp = value;
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
            String thePlcAndNm;
            thePlcAndNm = this.getPlcAndNm();
            strategy.appendField(locator, this, "plcAndNm", buffer, thePlcAndNm, (this.plcAndNm!= null));
        }
        {
            SupplementaryDataEnvelope1 theEnvlp;
            theEnvlp = this.getEnvlp();
            strategy.appendField(locator, this, "envlp", buffer, theEnvlp, (this.envlp!= null));
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
        final SupplementaryData1 that = ((SupplementaryData1) object);
        {
            String lhsPlcAndNm;
            lhsPlcAndNm = this.getPlcAndNm();
            String rhsPlcAndNm;
            rhsPlcAndNm = that.getPlcAndNm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "plcAndNm", lhsPlcAndNm), LocatorUtils.property(thatLocator, "plcAndNm", rhsPlcAndNm), lhsPlcAndNm, rhsPlcAndNm, (this.plcAndNm!= null), (that.plcAndNm!= null))) {
                return false;
            }
        }
        {
            SupplementaryDataEnvelope1 lhsEnvlp;
            lhsEnvlp = this.getEnvlp();
            SupplementaryDataEnvelope1 rhsEnvlp;
            rhsEnvlp = that.getEnvlp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "envlp", lhsEnvlp), LocatorUtils.property(thatLocator, "envlp", rhsEnvlp), lhsEnvlp, rhsEnvlp, (this.envlp!= null), (that.envlp!= null))) {
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
            String thePlcAndNm;
            thePlcAndNm = this.getPlcAndNm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "plcAndNm", thePlcAndNm), currentHashCode, thePlcAndNm, (this.plcAndNm!= null));
        }
        {
            SupplementaryDataEnvelope1 theEnvlp;
            theEnvlp = this.getEnvlp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "envlp", theEnvlp), currentHashCode, theEnvlp, (this.envlp!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
