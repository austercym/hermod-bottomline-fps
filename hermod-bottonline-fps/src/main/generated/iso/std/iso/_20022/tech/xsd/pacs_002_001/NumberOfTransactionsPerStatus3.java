//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.15 at 02:03:13 PM CEST 
//


package iso.std.iso._20022.tech.xsd.pacs_002_001;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NumberOfTransactionsPerStatus3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumberOfTransactionsPerStatus3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DtldNbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}Max15NumericText"/&gt;
 *         &lt;element name="DtldSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}TransactionIndividualStatus3Code"/&gt;
 *         &lt;element name="DtldCtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.06}DecimalNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberOfTransactionsPerStatus3", propOrder = {
    "dtldNbOfTxs",
    "dtldSts",
    "dtldCtrlSum"
})
public class NumberOfTransactionsPerStatus3
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DtldNbOfTxs", required = true)
    protected String dtldNbOfTxs;
    @XmlElement(name = "DtldSts", required = true)
    @XmlSchemaType(name = "string")
    protected TransactionIndividualStatus3Code dtldSts;
    @XmlElement(name = "DtldCtrlSum")
    protected BigDecimal dtldCtrlSum;

    /**
     * Gets the value of the dtldNbOfTxs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtldNbOfTxs() {
        return dtldNbOfTxs;
    }

    /**
     * Sets the value of the dtldNbOfTxs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtldNbOfTxs(String value) {
        this.dtldNbOfTxs = value;
    }

    /**
     * Gets the value of the dtldSts property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public TransactionIndividualStatus3Code getDtldSts() {
        return dtldSts;
    }

    /**
     * Sets the value of the dtldSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionIndividualStatus3Code }
     *     
     */
    public void setDtldSts(TransactionIndividualStatus3Code value) {
        this.dtldSts = value;
    }

    /**
     * Gets the value of the dtldCtrlSum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDtldCtrlSum() {
        return dtldCtrlSum;
    }

    /**
     * Sets the value of the dtldCtrlSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDtldCtrlSum(BigDecimal value) {
        this.dtldCtrlSum = value;
    }

}
