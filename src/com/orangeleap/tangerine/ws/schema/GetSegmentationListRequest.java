//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.01 at 04:14:29 PM CDT 
//


package com.orangeleap.tangerine.ws.schema;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="dummy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "dummy"
})
@XmlRootElement(name = "GetSegmentationListRequest")
public class GetSegmentationListRequest {

    @XmlElement(required = true)
    protected String dummy;

    /**
     * Gets the value of the dummy property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDummy() {
        return dummy;
    }

    /**
     * Sets the value of the dummy property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDummy(String value) {
        this.dummy = value;
    }

}
