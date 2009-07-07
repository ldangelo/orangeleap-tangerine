//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.01 at 04:14:29 PM CDT 
//


package com.orangeleap.tangerine.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.orangeleap.com/orangeleap/services/1.0}address" minOccurs="0"/>
 *         &lt;element name="primaryPhone" type="{http://www.orangeleap.com/orangeleap/services/1.0}phone" minOccurs="0"/>
 *         &lt;element name="primaryEmail" type="{http://www.orangeleap.com/orangeleap/services/1.0}email" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "firstName",
        "lastName",
        "primaryAddress",
        "primaryPhone",
        "primaryEmail"
})
@XmlRootElement(name = "FindConstituentsRequest")
public class FindConstituentsRequest {

    protected String firstName;
    protected String lastName;
    protected Address primaryAddress;
    protected Phone primaryPhone;
    protected Email primaryEmail;

    /**
     * Gets the value of the firstName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the primaryAddress property.
     *
     * @return possible object is
     *         {@link Address }
     */
    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Sets the value of the primaryAddress property.
     *
     * @param value allowed object is
     *              {@link Address }
     */
    public void setPrimaryAddress(Address value) {
        this.primaryAddress = value;
    }

    /**
     * Gets the value of the primaryPhone property.
     *
     * @return possible object is
     *         {@link Phone }
     */
    public Phone getPrimaryPhone() {
        return primaryPhone;
    }

    /**
     * Sets the value of the primaryPhone property.
     *
     * @param value allowed object is
     *              {@link Phone }
     */
    public void setPrimaryPhone(Phone value) {
        this.primaryPhone = value;
    }

    /**
     * Gets the value of the primaryEmail property.
     *
     * @return possible object is
     *         {@link Email }
     */
    public Email getPrimaryEmail() {
        return primaryEmail;
    }

    /**
     * Sets the value of the primaryEmail property.
     *
     * @param value allowed object is
     *              {@link Email }
     */
    public void setPrimaryEmail(Email value) {
        this.primaryEmail = value;
    }

}
