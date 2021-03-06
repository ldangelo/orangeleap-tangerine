
package com.orangeleap.theguru.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for communicationHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="communicationHistory">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.orangeleap.com/orangeleap/services/1.0}abstractCustomizableEntity">
 *       &lt;sequence>
 *         &lt;element name="addressType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="communicationHistoryType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emailType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="giftId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="constituentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pledgeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="recordDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recurringGiftId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="selectedAddress" type="{http://www.orangeleap.com/orangeleap/services/1.0}address" minOccurs="0"/>
 *         &lt;element name="selectedEmail" type="{http://www.orangeleap.com/orangeleap/services/1.0}email" minOccurs="0"/>
 *         &lt;element name="selectedPhone" type="{http://www.orangeleap.com/orangeleap/services/1.0}phone" minOccurs="0"/>
 *         &lt;element name="systemGenerated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "communicationHistory", propOrder = {
    "addressType",
    "comments",
    "communicationHistoryType",
    "emailType",
    "entryType",
    "giftId",
    "constituentId",
    "pledgeId",
    "recordDate",
    "recurringGiftId",
    "selectedAddress",
    "selectedEmail",
    "selectedPhone",
    "systemGenerated"
})
public class CommunicationHistory
    extends AbstractCustomizableEntity
{

    protected String addressType;
    protected String comments;
    @XmlElement(required = true)
    protected String communicationHistoryType;
    protected String emailType;
    @XmlElement(required = true)
    protected String entryType;
    protected Long giftId;
    protected long constituentId;
    protected Long pledgeId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar recordDate;
    protected Long recurringGiftId;
    protected Address selectedAddress;
    protected Email selectedEmail;
    protected Phone selectedPhone;
    protected boolean systemGenerated;

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressType(String value) {
        this.addressType = value;
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
     * Gets the value of the communicationHistoryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunicationHistoryType() {
        return communicationHistoryType;
    }

    /**
     * Sets the value of the communicationHistoryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunicationHistoryType(String value) {
        this.communicationHistoryType = value;
    }

    /**
     * Gets the value of the emailType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailType() {
        return emailType;
    }

    /**
     * Sets the value of the emailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailType(String value) {
        this.emailType = value;
    }

    /**
     * Gets the value of the entryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryType() {
        return entryType;
    }

    /**
     * Sets the value of the entryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryType(String value) {
        this.entryType = value;
    }

    /**
     * Gets the value of the giftId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getGiftId() {
        return giftId;
    }

    /**
     * Sets the value of the giftId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setGiftId(Long value) {
        this.giftId = value;
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
     * Gets the value of the pledgeId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPledgeId() {
        return pledgeId;
    }

    /**
     * Sets the value of the pledgeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPledgeId(Long value) {
        this.pledgeId = value;
    }

    /**
     * Gets the value of the recordDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecordDate() {
        return recordDate;
    }

    /**
     * Sets the value of the recordDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecordDate(XMLGregorianCalendar value) {
        this.recordDate = value;
    }

    /**
     * Gets the value of the recurringGiftId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRecurringGiftId() {
        return recurringGiftId;
    }

    /**
     * Sets the value of the recurringGiftId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRecurringGiftId(Long value) {
        this.recurringGiftId = value;
    }

    /**
     * Gets the value of the selectedAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getSelectedAddress() {
        return selectedAddress;
    }

    /**
     * Sets the value of the selectedAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setSelectedAddress(Address value) {
        this.selectedAddress = value;
    }

    /**
     * Gets the value of the selectedEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public Email getSelectedEmail() {
        return selectedEmail;
    }

    /**
     * Sets the value of the selectedEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setSelectedEmail(Email value) {
        this.selectedEmail = value;
    }

    /**
     * Gets the value of the selectedPhone property.
     * 
     * @return
     *     possible object is
     *     {@link Phone }
     *     
     */
    public Phone getSelectedPhone() {
        return selectedPhone;
    }

    /**
     * Sets the value of the selectedPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phone }
     *     
     */
    public void setSelectedPhone(Phone value) {
        this.selectedPhone = value;
    }

    /**
     * Gets the value of the systemGenerated property.
     * 
     */
    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    /**
     * Sets the value of the systemGenerated property.
     * 
     */
    public void setSystemGenerated(boolean value) {
        this.systemGenerated = value;
    }

}
