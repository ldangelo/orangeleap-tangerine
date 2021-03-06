
package com.orangeleap.theguru.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for abstractPaymentInfoEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="abstractPaymentInfoEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.orangeleap.com/orangeleap/services/1.0}abstractCustomizableEntity">
 *       &lt;sequence>
 *         &lt;element name="acknowledgmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.orangeleap.com/orangeleap/services/1.0}address" minOccurs="0"/>
 *         &lt;element name="checkNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distributionLines" type="{http://www.orangeleap.com/orangeleap/services/1.0}distributionLine" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.orangeleap.com/orangeleap/services/1.0}email" minOccurs="0"/>
 *         &lt;element name="paymentSource" type="{http://www.orangeleap.com/orangeleap/services/1.0}paymentSource" minOccurs="0"/>
 *         &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="constituentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="phone" type="{http://www.orangeleap.com/orangeleap/services/1.0}phone" minOccurs="0"/>
 *         &lt;element name="sendAcknowledgment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractPaymentInfoEntity", propOrder = {
    "acknowledgmentDate",
    "address",
    "checkNumber",
    "comments",
    "currencyCode",
    "distributionLines",
    "email",
    "paymentSource",
    "paymentType",
    "constituentId",
    "phone",
    "sendAcknowledgment"
})
@XmlSeeAlso({
    Gift.class
})
public abstract class AbstractPaymentInfoEntity
    extends AbstractCustomizableEntity
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar acknowledgmentDate;
    protected Address address;
    protected Integer checkNumber;
    protected String comments;
    protected String currencyCode;
    @XmlElement(nillable = true)
    protected List<DistributionLine> distributionLines;
    protected Email email;
    protected PaymentSource paymentSource;
    protected String paymentType;
    protected long constituentId;
    protected Phone phone;
    protected boolean sendAcknowledgment;

    /**
     * Gets the value of the acknowledgmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAcknowledgmentDate() {
        return acknowledgmentDate;
    }

    /**
     * Sets the value of the acknowledgmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAcknowledgmentDate(XMLGregorianCalendar value) {
        this.acknowledgmentDate = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the checkNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCheckNumber() {
        return checkNumber;
    }

    /**
     * Sets the value of the checkNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCheckNumber(Integer value) {
        this.checkNumber = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the distributionLines property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distributionLines property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistributionLines().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DistributionLine }
     * 
     * 
     */
    public List<DistributionLine> getDistributionLines() {
        if (distributionLines == null) {
            distributionLines = new ArrayList<DistributionLine>();
        }
        return this.distributionLines;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setEmail(Email value) {
        this.email = value;
    }

    /**
     * Gets the value of the paymentSource property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSource }
     *     
     */
    public PaymentSource getPaymentSource() {
        return paymentSource;
    }

    /**
     * Sets the value of the paymentSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSource }
     *     
     */
    public void setPaymentSource(PaymentSource value) {
        this.paymentSource = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the constituentId property.
     * 
     */
    public long getConstituentId() {
        return constituentId;
    }

    /**
     * Sets the value of the constituentId property.
     * 
     */
    public void setConstituentId(long value) {
        this.constituentId = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link Phone }
     *     
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phone }
     *     
     */
    public void setPhone(Phone value) {
        this.phone = value;
    }

    /**
     * Gets the value of the sendAcknowledgment property.
     * 
     */
    public boolean isSendAcknowledgment() {
        return sendAcknowledgment;
    }

    /**
     * Sets the value of the sendAcknowledgment property.
     * 
     */
    public void setSendAcknowledgment(boolean value) {
        this.sendAcknowledgment = value;
    }

}
