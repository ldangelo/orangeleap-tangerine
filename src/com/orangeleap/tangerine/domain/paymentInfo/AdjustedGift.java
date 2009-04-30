package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.orangeleap.tangerine.util.StringConstants;

@SuppressWarnings("serial")
public class AdjustedGift extends AbstractPaymentInfoEntity {

    public AdjustedGift() {
        super();
    }

    public AdjustedGift(Gift originalGift) {
        this();
        setOriginalAmount(originalGift.getAmount());
        setOriginalGiftId(originalGift.getId());
        setPerson(originalGift.getPerson());
        setAdjustedTransactionDate(Calendar.getInstance(Locale.getDefault()).getTime());
        
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        if (originalGift.getDistributionLines() != null) {
            for (DistributionLine originalLine : originalGift.getDistributionLines()) {
                originalLine.resetIdToNull();
                originalLine.setAmount(null);
                lines.add(originalLine);
            }
        }
        setDistributionLines(lines);
    }

    private BigDecimal adjustedAmount;
    private BigDecimal originalAmount;
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

    public BigDecimal getAdjustedAmount() {
        return adjustedAmount;
    }

    public void setAdjustedAmount(BigDecimal adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
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
    public void prePersist() {
        super.prePersist();
    }
}
