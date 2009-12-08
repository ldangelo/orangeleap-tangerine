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

package com.orangeleap.tangerine.domain.paymentInfo;

import com.orangeleap.tangerine.domain.Postable;
import com.orangeleap.tangerine.util.StringConstants;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings("serial")
public class AdjustedGift extends AbstractPaymentInfoEntity implements Postable {

    private BigDecimal originalAmount;
    private BigDecimal currentTotalAdjustedAmount; 
    private BigDecimal adjustedAmount;
    private Date adjustedTransactionDate;
    private String adjustedReason;
    private String adjustedType;
    private String adjustedStatus;
    private boolean adjustedPaymentRequired = false;
    private String adjustedPaymentTo;
    private Long originalGiftId;
    private String authCode = StringConstants.EMPTY;
    private String txRefNum;
    private String paymentStatus = StringConstants.EMPTY;
    private String paymentMessage;
	private boolean posted;
	private Date postedDate = new Date();

    public AdjustedGift() {
        super();
    }

    public AdjustedGift(Gift originalGift) {
        this();
        setOriginalAmount(originalGift.getAmount());
        setOriginalGiftId(originalGift.getId());
        setConstituent(originalGift.getConstituent());
        setAdjustedTransactionDate(Calendar.getInstance(Locale.getDefault()).getTime());
        setPaymentSource(originalGift.getPaymentSource());
        setAddress(originalGift.getAddress());
        setPhone(originalGift.getPhone());
        setPaymentType(originalGift.getPaymentType());
        setCurrencyCode(originalGift.getCurrencyCode());
        
        if (originalGift.getDistributionLines() != null) {
            for (DistributionLine originalLine : originalGift.getDistributionLines()) {
                originalLine.resetIdToNull();
                originalLine.setAmount(null);
                originalLine.setGiftId(null);
                addDistributionLine(originalLine);
            }
        }
    }

    public AdjustedGift(BigDecimal adjustedAmount) {
        this();
        this.adjustedAmount = adjustedAmount;
    }

    /** Used only in unit tests */
    public AdjustedGift(BigDecimal adjustedAmount, BigDecimal originalAmount, Long originalGiftId, List<DistributionLine> lines) {
        this(adjustedAmount);
        this.originalAmount = originalAmount;
        this.originalGiftId = originalGiftId;
        setDistributionLines(lines);
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getCurrentTotalAdjustedAmount() {
        return currentTotalAdjustedAmount;
    }

    public void setCurrentTotalAdjustedAmount(BigDecimal currentTotalAdjustedAmount) {
        this.currentTotalAdjustedAmount = currentTotalAdjustedAmount;
    }

    public BigDecimal getAdjustedAmount() {
        return adjustedAmount;
    }

    public void setAdjustedAmount(BigDecimal adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    public Date getAdjustedTransactionDate() {
        return adjustedTransactionDate;
    }

    public void setAdjustedTransactionDate(Date adjustedTransactionDate) {
        this.adjustedTransactionDate = adjustedTransactionDate;
    }

    public String getAdjustedReason() {
        return adjustedReason;
    }

    public void setAdjustedReason(String adjustedReason) {
        this.adjustedReason = adjustedReason;
    }

    public String getAdjustedType() {
        return adjustedType;
    }

    public void setAdjustedType(String adjustedType) {
        this.adjustedType = adjustedType;
    }

    public String getAdjustedStatus() {
        return adjustedStatus;
    }

    public void setAdjustedStatus(String adjustedStatus) {
        this.adjustedStatus = adjustedStatus;
    }

    public boolean isAdjustedPaymentRequired() {
        return adjustedPaymentRequired;
    }

    public void setAdjustedPaymentRequired(boolean adjustedPaymentRequired) {
        this.adjustedPaymentRequired = adjustedPaymentRequired;
    }

    public String getAdjustedPaymentTo() {
        return adjustedPaymentTo;
    }

    public void setAdjustedPaymentTo(String adjustedPaymentTo) {
        this.adjustedPaymentTo = adjustedPaymentTo;
    }

    public Long getOriginalGiftId() {
        return originalGiftId;
    }

    public void setOriginalGiftId(Long originalGiftId) {
        this.originalGiftId = originalGiftId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTxRefNum() {
        return txRefNum;
    }

    public void setTxRefNum(String txRefNum) {
        this.txRefNum = txRefNum;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    @Override
	public boolean isPosted() {
		return posted;
	}

    @Override
	public void setPosted(boolean posted) {
		this.posted = posted;
	}

    @Override
	public Date getPostedDate() {
		return postedDate;
	}

    @Override
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

    @Override
    public void prePersist() {
        super.prePersist();
        if (!adjustedPaymentRequired) {
            setAdjustedPaymentTo(null);
            setAuthCode(null);
            setCheckNumber(null);
            setPaymentMessage(null);
            setPaymentStatus(null);
            setTxRefNum(null);
            setPaymentType(null);
            setPaymentSource(null);
        }
		if (!posted) {
			setPostedDate(null);
		}
    }
    
    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAdjustedAmount()).append(", "); 
        sb.append(getAdjustedStatus()).append(", ");
        sb.append(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(getAdjustedTransactionDate()));
        return sb.toString();
    }
}
