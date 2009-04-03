package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.type.CommitmentType;

public abstract class Commitment extends AbstractPaymentInfoEntity {  

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_CANCELLED = "cancelled";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_FULFILLED = "fulfilled";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_IN_PROGRESS = "inProgress";

    public static final String FREQUENCY_ONE_TIME = "one time";
    public static final String FREQUENCY_WEEKLY = "weekly";
    public static final String FREQUENCY_TWICE_MONTHLY = "twice monthly";
    public static final String FREQUENCY_MONTHLY = "monthly";
    public static final String FREQUENCY_QUARTERLY = "quarterly";
    public static final String FREQUENCY_TWICE_ANNUALLY = "twice annually";
    public static final String FREQUENCY_ANNUALLY = "annually";
    public static final String FREQUENCY_UNSPECIFIED = "unspecified";

    private CommitmentType commitmentType;
    private BigDecimal amountTotal = null;
    protected BigDecimal amountPerGift;
    protected Date startDate = Calendar.getInstance().getTime();
    protected Date endDate;
    private Date createDate;
    private Date updateDate;
    protected String frequency;
    private Date lastEntryDate;
    private List<Gift> gifts;
    
    public Commitment() { 
        super();
    }

    public Commitment(CommitmentType commitmentType) {
        this();
        this.commitmentType = commitmentType;
    }

    public CommitmentType getCommitmentType() {
        return commitmentType;
    }

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
    }
    
    public BigDecimal getAmountTotal() {
        if (amountTotal == null) {
            if (startDate != null && endDate != null && amountPerGift != null && frequency != null) {
                DateTime dtStart = new DateTime( startDate.getTime() );
                DateTime dtEnd = new DateTime( endDate.getTime() );
                int multiplier = 1; // there is always at least one payment
    
                //TODO: this is pretty ugly. Move to a typesafe enum at some point
                //NOTE: we skip FREQUENCY_ONE_TIME, as it means a multiplier of 1
                if (frequency.equals(FREQUENCY_WEEKLY)) {
                    Weeks weeks = Weeks.weeksBetween(dtStart,dtEnd);
                    multiplier += weeks.getWeeks();
                } 
                else if (frequency.equals(FREQUENCY_TWICE_MONTHLY)) {
                    // this is the only one that can be a bit tricky, given a month
                    // can potentially have more than 4 weeks. Use for now
                    Weeks weeks = Weeks.weeksBetween(dtStart,dtEnd);
                    multiplier += weeks.getWeeks() / 2;
                } 
                else if (frequency.equals(FREQUENCY_MONTHLY)) {
                    Months months = Months.monthsBetween(dtStart,dtEnd);
                    multiplier += months.getMonths();
                } 
                else if (frequency.equals(FREQUENCY_QUARTERLY)) {
                    Months months = Months.monthsBetween(dtStart,dtEnd);
                    multiplier += months.getMonths() / 3;
                } 
                else if (frequency.equals(FREQUENCY_TWICE_ANNUALLY)) {
                    Months months = Months.monthsBetween(dtStart,dtEnd);
                    multiplier += months.getMonths() / 6;
                } 
                else if (frequency.equals(FREQUENCY_ANNUALLY)) {
                    Months months = Months.monthsBetween(dtStart,dtEnd);
                    multiplier += months.getMonths() / 12;
                } 
                else if (frequency.equals(FREQUENCY_UNSPECIFIED)) {
                    multiplier = 0;
                }
                amountTotal = amountPerGift.multiply(new BigDecimal(multiplier));
            }
        }
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getAmountPerGift() {
        return amountPerGift;
    }

    public void setAmountPerGift(BigDecimal amountPerGift) {
        this.amountPerGift = amountPerGift;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

//    public boolean canRecur() {
//        boolean recur = true;
//        if (getEndDate() != null && getEndDate().before(Calendar.getInstance().getTime())) {
//            recur = false;
//        } 
//        else if (STATUS_CANCELLED.equals(getStatus()) || STATUS_EXPIRED.equals(getStatus())) { // TODO: fix to look at recurringGiftStatus or pledgeStatus
//            recur = false;
//        }
//        return recur;
//    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }

    public List<Gift> getGifts() {
        if (gifts == null) {
            gifts = new ArrayList<Gift>();
        }
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public BigDecimal getAmountPaid() {
        BigDecimal amount = BigDecimal.ZERO;
        if (getGifts() != null) {
            for (Gift gift : getGifts()) {
                amount = amount.add(gift.getAmount());
            }
        }
        return amount;
    }

    public BigDecimal getAmountRemaining() {
        BigDecimal amount = getAmountTotal();
        if (amount != null) {
            amount = amount.subtract(getAmountPaid());
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                amount = BigDecimal.ZERO;
            }
        }
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("commitmentType", commitmentType).append("amountPerGift", amountPerGift).append("startDate", startDate).
               append("endDate", endDate).append("createDate", createDate).append("updateDate", updateDate).
               append("frequency", frequency).
               append("lastEntryDate", lastEntryDate).
               toString();
    }
}
