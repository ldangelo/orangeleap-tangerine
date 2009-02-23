package com.mpower.domain.checkservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Header section of the XML Payload for Paperless Payments. Tyically looks like this:
 * <code><pre>
   &lt;Header&gt;
     &lt;type&gt;TEST&lt;/type&gt;
     &lt;FileNbr&gt;000001&lt;/FileNbr&gt;
     &lt;CoName&gt;MPower&lt;/CoName&gt;
     &lt;Date&gt;02/19/2009&lt;/Date&gt;
     &lt;Site&gt;853&lt;/Site&gt;
     &lt;MID&gt;0001&lt;/MID&gt;
   &lt;/Header&gt;
   </pre></code>
  * By default, the properties are all set to default values
 * representing a transmission from Orange Leap.
 * All that needs to be set is the filenumber and a Date.
 * @version 1.0
 */
@XmlRootElement(name="Header")
@XmlType(propOrder = {"type","fileNumber", "companyName", "date", "siteNumber", "merchantId"})
public class Header {

    private String type = "H"; // H = Header, TEST = Test Message
    private int fileNumber;
    private String companyName = "Orange Leap";
    private Date date;
    private int siteNumber;
    private int merchantId;

    @XmlElement(name="type")
    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @XmlElement(name="FileNbr")
    public String getFileNumber() {
        return Padding.leftPadZero(fileNumber,6);
    }

    void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    @XmlElement(name="CoName")
    public String getCompanyName() {
        return companyName;
    }

    void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @XmlElement(name="Date")
    public String getDate() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.format(date);
    }

    void setDate(Date date) {
        this.date = date;
    }

    @XmlElement(name="Site")
    public String getSiteNumber() {
        return Integer.toString(siteNumber);
    }

    void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    @XmlElement(name="MID")
    public String getMerchantId() {
        return Integer.toString(merchantId);
    }

    void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }
}
