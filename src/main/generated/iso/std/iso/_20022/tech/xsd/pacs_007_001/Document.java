//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.27 at 12:20:50 PM CET 
//


package iso.std.iso._20022.tech.xsd.pacs_007_001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.hermod.bottomline.fps.types.FPSMessage;


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
 *         &lt;element name="FIToFIPmtRvsl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.007.001.05}FIToFIPaymentReversalV05"/&gt;
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
    "fiToFIPmtRvsl"
})
@XmlRootElement(name = "Document")
public class Document
    implements Serializable, FPSMessage
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "FIToFIPmtRvsl", required = true)
    protected FIToFIPaymentReversalV05 fiToFIPmtRvsl;

    /**
     * Gets the value of the fiToFIPmtRvsl property.
     * 
     * @return
     *     possible object is
     *     {@link FIToFIPaymentReversalV05 }
     *     
     */
    public FIToFIPaymentReversalV05 getFIToFIPmtRvsl() {
        return fiToFIPmtRvsl;
    }

    /**
     * Sets the value of the fiToFIPmtRvsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIToFIPaymentReversalV05 }
     *     
     */
    public void setFIToFIPmtRvsl(FIToFIPaymentReversalV05 value) {
        this.fiToFIPmtRvsl = value;
    }

}
