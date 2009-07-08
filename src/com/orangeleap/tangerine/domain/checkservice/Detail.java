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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Maps to a Detail element in the xml document:
 * <code><pre>
 * &lt;Detail&gt;
 * &lt;Date&gt;02/19/2009&lt;/Date&gt;
 * &lt;Time&gt;1517:57&lt;/Time&gt;
 * &lt;TXNNbr&gt;123456&lt;/TXNNbr&gt;
 * &lt;SiteNbr&gt;853&lt;/SiteNbr&gt;
 * &lt;MID&gt;0001&lt;/MID&gt;
 * &lt;RuleNbr&gt;8965&lt;/RuleNbr&gt;
 * &lt;GroupNbr&gt;22&lt;/GroupNbr&gt;
 * &lt;checkAmt&gt;126.88&lt;/checkAmt&gt;
 * &lt;CashBack&gt;0.00&lt;/CashBack&gt;
 * &lt;VerResult&gt;AUTH&lt;/VerResult&gt;
 * &lt;Account&gt;10288197812345&lt;/Account&gt;
 * &lt;TXNCode&gt;NF&lt;/TXNCode&gt;
 * &lt;CheckNbr&gt;5561&lt;/CheckNbr&gt;
 * &lt;DriverID&gt;DL=TX12345678&lt;/DriverID&gt;
 * &lt;LaneNbr&gt;21&lt;/LaneNbr&gt;
 * &lt;Comments&gt;"t102881978t 12345o 5561"&lt;/Comments&gt;
 * &lt;/Detail&gt;
 * </pre></code><br/>
 * Only some of the fields can be set by the calling client.
 * Others are set by the Batch object to ensure consistency.
 *
 * @version 1.0
 */
@XmlRootElement(name = "Detail")
@XmlType(propOrder = {"date", "time", "transactionNumber", "siteNumber", "merchantId", "ruleNumber",
        "groupNumber", "checkAmount", "cashBack", "verResult", "accountNumber", "transactionCode",
        "checkNumber", "driverId", "laneNumber", "comments"})
public class Detail {
    // used for a default value for some XML elements
    private final static String EMPTY = "";

    private Date date;
    private int transactionNumber = 0;
    private int siteNumber = 0;
    private int merchantId = 0;
    private int ruleNumber = 0;
    private int groupNumber = 0;
    private BigDecimal checkAmount = new BigDecimal("0.00");
    private BigDecimal cashBack = new BigDecimal("0.00");
    private final String verResult = "AUTH"; //per Paperless Payments, always AUTH
    private String accountNumber = EMPTY;
    private final String transactionCode = "NF"; // per Paperless Payments, always NF
    private int checkNumber = 0;
    private String driverId = EMPTY;
    private int laneNumber = 0;
    private String comments = EMPTY;

    @XmlElement(name = "Date")
    public String getDate() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.format(date);
    }

    public void setDateTime(Date date) {
        this.date = date;
    }

    @XmlElement(name = "Time")
    public String getTime() {
        DateFormat formatter = new SimpleDateFormat("kk:mm:ss");
        return formatter.format(date);
    }

    @XmlElement(name = "TXNNbr", required = true)
    public String getTransactionNumber() {
        return Padding.leftPadZero(transactionNumber, 6);
    }

    public void setTransactionNumber(int transactionNumber) {
        Padding.validateNumber("transactionNumber", transactionNumber, 1, 6);
        this.transactionNumber = transactionNumber;
    }

    @XmlElement(name = "SiteNbr")
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

    @XmlElement(name = "RuleNbr")
    public String getRuleNumber() {
        return Integer.toString(ruleNumber);
    }

    void setRuleNumber(int ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    @XmlElement(name = "GroupNbr")
    public String getGroupNumber() {
        return (groupNumber <= 0 ? EMPTY : Integer.toString(groupNumber));
    }

    /**
     * Optional parameter: Group Number
     *
     * @param groupNumber
     */
    public void setGroupNumber(int groupNumber) {
        Padding.validateNumber("groupNumber", groupNumber, 0, 2);
        this.groupNumber = groupNumber;

    }


    @XmlElement(name = "checkAmt")
    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    /**
     * Must be formatted with two (and only two) digits trailing
     * the decimal place, for example 150.00 for $150. Cannot be zero
     * or negative.
     *
     * @param checkAmount the amount of the check
     */
    public void setCheckAmount(BigDecimal checkAmount) {
        if (checkAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot have a zero or negative check amount (currently " +
                    checkAmount.toPlainString() + ")");
        }

        this.checkAmount = checkAmount;
    }

    @XmlElement(name = "CashBack")
    public BigDecimal getCashBack() {
        return cashBack;
    }

    /**
     * Must be formatted with two (and only two) digits trailing
     * the decimal place, for example 150.00 for $150. The default
     * value is 0.00. Cannot be a negative value
     *
     * @param cashBack the amount of cash back for the transaction
     */
    public void setCashBack(BigDecimal cashBack) {

        if (checkAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot have a  negative cashBack amount (currently " +
                    checkAmount.toPlainString() + ")");
        }

        this.cashBack = cashBack;
    }

    /**
     * Always returns the default value of AUTH, as specified by
     * Paperless Payments
     *
     * @return "AUTH"
     */
    @XmlElement(name = "VerResult")
    public String getVerResult() {
        return verResult;
    }

    @XmlElement(name = "Account")
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Set the account/routing number of the check. This should be
     * a nine-digit number contained in a String
     *
     * @param accountNumber the account number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = Padding.validateString("accountNumber", accountNumber, 1, 26);
    }

    /**
     * Always returns the default value of NF, as specified by Paperless Payments
     *
     * @return "NF"
     */
    @XmlElement(name = "TXNCode")
    public String getTransactionCode() {
        return transactionCode;
    }

    @XmlElement(name = "CheckNbr")
    public String getCheckNumber() {

        return (checkNumber <= 0 ? EMPTY : Integer.toString(checkNumber));
    }

    /**
     * Optional parameter: Check Number
     *
     * @param checkNumber
     */
    public void setCheckNumber(int checkNumber) {
        Padding.validateNumber("checkNumber", checkNumber, 1, 9);
        this.checkNumber = checkNumber;
    }

    @XmlElement(name = "DriverID", required = true)
    public String getDriverId() {
        return driverId;
    }

    /**
     * Optional parameter: Driver's License Number
     *
     * @param driverId
     */
    public void setDriverId(String driverId) {
        this.driverId = Padding.validateString("driverId", driverId, 0, 23);
    }

    @XmlElement(name = "LaneNbr")
    public String getLaneNumber() {
        return (laneNumber <= 0 ? EMPTY : Integer.toString(laneNumber));
    }

    /**
     * Optional parameter: Lane Number
     *
     * @param laneNumber
     */
    public void setLaneNumber(int laneNumber) {
        Padding.validateNumber("laneNumber", laneNumber, 0, 2);
        this.laneNumber = laneNumber;
    }

    @XmlElement(name = "Comments")
    public String getComments() {
        return comments;
    }

    /**
     * Optional parameter: Comments
     * Note, Paperless payments expects this String to be be enclosed
     * in quotes inside the XML document, so this method will wrap the
     * String in escaped quotes.
     *
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = "\"" + Padding.validateString("comments", comments, 0, 40) + "\"";
    }
}
