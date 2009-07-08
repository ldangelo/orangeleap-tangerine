/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain.checkservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the Footer section of the XML payload for Paperless Payment. The XML
 * fragment would look like this:
 * <code><pre>
 * &lt;Footer&gt;
 * &lt;type&gt;F&lt;/type&gt;
 * &lt;FileNbr&gt;000001&lt;/FileNbr&gt;
 * &lt;CoName&gt;OrangeLeap&lt;/CoName&gt;
 * &lt;Date&gt;02/19/2009&lt;/Date&gt;
 * &lt;Site&gt;853&lt;/Site&gt;
 * &lt;MID&gt;0001&lt;/MID&gt;
 * &lt;RecCount&gt;00001&lt;/RecCount&gt;
 * &lt;/Footer&gt;
 * </pre></code>
 *
 * @version 1.0
 */
@XmlRootElement(name = "Footer")
@XmlType(propOrder = {"type", "fileNumber", "companyName", "date", "siteNumber", "merchantId", "recordCount"})
public class Footer {

    private String type = "F"; // F = Header
    private int fileNumber;
    private String companyName = "Orange Leap";
    private Date date;
    private int siteNumber;
    private int merchantId;
    private int recordCount = 0;

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "FileNbr")
    public String getFileNumber() {
        return Padding.leftPadZero(fileNumber, 6);
    }

    void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    @XmlElement(name = "CoName")
    public String getCompanyName() {
        return companyName;
    }

    void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @XmlElement(name = "Date")
    public String getDate() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.format(date);
    }

    void setDate(Date date) {
        this.date = date;
    }

    @XmlElement(name = "Site")
    public String getSiteNumber() {
        return Integer.toString(siteNumber);
    }

    void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    @XmlElement(name = "MID")
    public String getMerchantId() {
        return Integer.toString(merchantId);
    }

    void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public void addRecord() {
        recordCount++;
    }

    @XmlElement(name = "RecCount")
    public String getRecordCount() {
        return Padding.leftPadZero(recordCount, 5);

    }

}
