//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.19 at 03:41:26 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_002_001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.hermod.bottonline.fps.types.FPSMessage;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIToFIPmtStsRpt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}FIToFIPaymentStatusReportV06"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFIPmtStsRpt"
})
@XmlRootElement(name = "Document")
public class Document
    implements Serializable, FPSMessage
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "FIToFIPmtStsRpt", required = true)
    protected FIToFIPaymentStatusReportV06 fiToFIPmtStsRpt;

    /**
     * Gets the value of the fiToFIPmtStsRpt property.
     * 
     * @return
     *     possible object is
     *     {@link FIToFIPaymentStatusReportV06 }
     *     
     */
    public FIToFIPaymentStatusReportV06 getFIToFIPmtStsRpt() {
        return fiToFIPmtStsRpt;
    }

    /**
     * Sets the value of the fiToFIPmtStsRpt property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIToFIPaymentStatusReportV06 }
     *     
     */
    public void setFIToFIPmtStsRpt(FIToFIPaymentStatusReportV06 value) {
        this.fiToFIPmtStsRpt = value;
    }

}
