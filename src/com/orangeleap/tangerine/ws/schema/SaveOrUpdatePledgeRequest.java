//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.09 at 11:34:24 AM CST 
//


package com.orangeleap.tangerine.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="constituentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pledge" type="{http://www.orangeleap.com/orangeleap/services/1.0}pledge"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "constituentId",
    "pledge"
})
@XmlRootElement(name = "SaveOrUpdatePledgeRequest")
public class SaveOrUpdatePledgeRequest {

    protected long constituentId;
    @XmlElement(required = true)
    protected Pledge pledge;

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
     * Gets the value of the pledge property.
     * 
     * @return
     *     possible object is
     *     {@link Pledge }
     *     
     */
    public Pledge getPledge() {
        return pledge;
    }

    /**
     * Sets the value of the pledge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pledge }
     *     
     */
    public void setPledge(Pledge value) {
        this.pledge = value;
    }

}
