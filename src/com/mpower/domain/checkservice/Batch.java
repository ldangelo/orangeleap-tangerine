package com.mpower.domain.checkservice;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Represents a check that need to be sent through
 * the Paperless Payment system. This class should not be instantiated
 * manually. Instead, use the CheckService#createBatch() method, which
 * ensures correct values are set for the transaction.</br>
 * Also, although this class is called "Batch", it is only designed
 * to contain a single Detail section. The class name is aligned with
 * the name of the XML Element used for the web service.
 * @version 1.0
 */
@XmlRootElement(name = "Batch")
@XmlType(propOrder = {"header","detail", "footer"})
public class Batch {

    private int site;
    private int ruleNumber;
    private int merchantId;
    private Header header;
    private Footer footer;
    private Detail detail = null;


    public Batch() {
        header = new Header();
        footer = new Footer();
        Date d = new Date();
        header.setDate(d);
        footer.setDate(d);

    }

    public void isTest(boolean test) {
        if(test) {
            header.setType("TEST");
        } else {
            header.setType("H");
        }
    }

    /**
     * Add a Detail item to the Batch, ignoring null values.
     * This method will ensure the correct values are set for the
     * Site Number, Rule Number and Merchant ID, based on how it was
     * created in the CheckService#createBatch() method
     * @param detail
     */
    public void setDetail(Detail detail) {

        if(detail == null) return;
        this.detail = detail;

        this.detail.setSiteNumber(site);
        this.detail.setRuleNumber(ruleNumber);
        this.detail.setMerchantId(merchantId);
        footer.addRecord();
    }

    /**
     * Sets the unique file number for this batch, which must
     * be a non-negative number with a minimum of one digit and a 
     * maximum of six digits
     * @param fileNumber
     */
    public void setFileNumber(int fileNumber) {
        Padding.validateNumber("fileNumber", fileNumber, 1, 6);

        header.setFileNumber(fileNumber);
        footer.setFileNumber(fileNumber);
    }

    public void setMerchantId(int merchantId) {
        Padding.validateNumber("merchandId", merchantId,4,6);
        this.merchantId = merchantId;
        header.setMerchantId(merchantId);
        footer.setMerchantId(merchantId);
    }

    public void setCompanyName(String companyName) {
        header.setCompanyName(companyName);
        footer.setCompanyName(companyName);
    }

    public void setRuleNumber(int ruleNumber) {
        Padding.validateNumber("ruleNumber",ruleNumber,4,4);
        this.ruleNumber = ruleNumber;
    }

    public void setSiteNumber(int site) {
        Padding.validateNumber("siteNumber",site,3,3);
        this.site = site;
        header.setSiteNumber(site);
        footer.setSiteNumber(site);
    }

    @XmlElement(name = "Header")
    public Header getHeader() {
        return this.header;
    }

    @XmlElement(name = "Footer")
    public Footer getFooter() {
        return footer;
    }

    @XmlElement(name = "Detail")
    public Detail getDetail() {
        return detail;
    }

}
