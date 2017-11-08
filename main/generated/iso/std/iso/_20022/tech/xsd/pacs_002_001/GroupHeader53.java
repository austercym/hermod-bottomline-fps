//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 07:01:55 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_002_001;

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
 * <p>Java class for GroupHeader53 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupHeader53"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Max35Text"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}ISODateTime"/&gt;
 *         &lt;element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader53", propOrder = {
    "msgId",
    "creDtTm",
    "instgAgt",
    "instdAgt"
})
public class GroupHeader53 implements Serializable, Equals2, HashCode2, ToString2
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "CreDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    @XmlElement(name = "InstgAgt")
    protected BranchAndFinancialInstitutionIdentification5 instgAgt;
    @XmlElement(name = "InstdAgt")
    protected BranchAndFinancialInstitutionIdentification5 instdAgt;

    /**
     * Gets the value of the msgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Gets the value of the creDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Gets the value of the instgAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstgAgt() {
        return instgAgt;
    }

    /**
     * Sets the value of the instgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstgAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instgAgt = value;
    }

    /**
     * Gets the value of the instdAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getInstdAgt() {
        return instdAgt;
    }

    /**
     * Sets the value of the instdAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setInstdAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.instdAgt = value;
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
            String theMsgId;
            theMsgId = this.getMsgId();
            strategy.appendField(locator, this, "msgId", buffer, theMsgId, (this.msgId!= null));
        }
        {
            XMLGregorianCalendar theCreDtTm;
            theCreDtTm = this.getCreDtTm();
            strategy.appendField(locator, this, "creDtTm", buffer, theCreDtTm, (this.creDtTm!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgAgt;
            theInstgAgt = this.getInstgAgt();
            strategy.appendField(locator, this, "instgAgt", buffer, theInstgAgt, (this.instgAgt!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdAgt;
            theInstdAgt = this.getInstdAgt();
            strategy.appendField(locator, this, "instdAgt", buffer, theInstdAgt, (this.instdAgt!= null));
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
        final GroupHeader53 that = ((GroupHeader53) object);
        {
            String lhsMsgId;
            lhsMsgId = this.getMsgId();
            String rhsMsgId;
            rhsMsgId = that.getMsgId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "msgId", lhsMsgId), LocatorUtils.property(thatLocator, "msgId", rhsMsgId), lhsMsgId, rhsMsgId, (this.msgId!= null), (that.msgId!= null))) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsCreDtTm;
            lhsCreDtTm = this.getCreDtTm();
            XMLGregorianCalendar rhsCreDtTm;
            rhsCreDtTm = that.getCreDtTm();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "creDtTm", lhsCreDtTm), LocatorUtils.property(thatLocator, "creDtTm", rhsCreDtTm), lhsCreDtTm, rhsCreDtTm, (this.creDtTm!= null), (that.creDtTm!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstgAgt;
            lhsInstgAgt = this.getInstgAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstgAgt;
            rhsInstgAgt = that.getInstgAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instgAgt", lhsInstgAgt), LocatorUtils.property(thatLocator, "instgAgt", rhsInstgAgt), lhsInstgAgt, rhsInstgAgt, (this.instgAgt!= null), (that.instgAgt!= null))) {
                return false;
            }
        }
        {
            BranchAndFinancialInstitutionIdentification5 lhsInstdAgt;
            lhsInstdAgt = this.getInstdAgt();
            BranchAndFinancialInstitutionIdentification5 rhsInstdAgt;
            rhsInstdAgt = that.getInstdAgt();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "instdAgt", lhsInstdAgt), LocatorUtils.property(thatLocator, "instdAgt", rhsInstdAgt), lhsInstdAgt, rhsInstdAgt, (this.instdAgt!= null), (that.instdAgt!= null))) {
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
            String theMsgId;
            theMsgId = this.getMsgId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "msgId", theMsgId), currentHashCode, theMsgId, (this.msgId!= null));
        }
        {
            XMLGregorianCalendar theCreDtTm;
            theCreDtTm = this.getCreDtTm();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "creDtTm", theCreDtTm), currentHashCode, theCreDtTm, (this.creDtTm!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstgAgt;
            theInstgAgt = this.getInstgAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instgAgt", theInstgAgt), currentHashCode, theInstgAgt, (this.instgAgt!= null));
        }
        {
            BranchAndFinancialInstitutionIdentification5 theInstdAgt;
            theInstdAgt = this.getInstdAgt();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "instdAgt", theInstdAgt), currentHashCode, theInstdAgt, (this.instdAgt!= null));
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy2 strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}