//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.04 at 05:32:07 PM CST 
//


package com.orangeleap.tangerine.ws.schema.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 					Retrieve the list of existing payment sources for a given constituent id.
 * 					
 * 					Requires a valid constituentid.
 * 					
 * 					Returns the list of existing payment sources.
 * 					
 * 					The recommendation by the orange leap team is that before you try and add a payment source to a gift, you
 * 					first do a lookup of existing payment sources to determine it does not currently exist.  If it does currently
 * 					exist you would use this payment source instead of creating a new one.
 * 					
 * 					If you try and create a new payment source that already exists you will receive a SoapFault.
 * 				
 * 
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPaymentSourcesByConstituentIdRequest")
public class GetPaymentSourcesByConstituentIdRequest {

    @XmlAttribute(required = true)
    protected long constituentId;

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

}
