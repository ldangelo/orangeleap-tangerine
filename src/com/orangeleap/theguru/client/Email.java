
package com.orangeleap.theguru.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for email complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="email">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.orangeleap.com/orangeleap/services/1.0}abstractCommunicationEntity">
 *       &lt;sequence>
 *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emailDisplay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "email", propOrder = {
    "emailAddress",
    "emailDisplay"
})
public class Email
    extends AbstractCommunicationEntity
{

    @XmlElement(required = true)
    protected String emailAddress;
    protected String emailDisplay;

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the emailDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailDisplay() {
        return emailDisplay;
    }

    /**
     * Sets the value of the emailDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailDisplay(String value) {
        this.emailDisplay = value;
    }

}
