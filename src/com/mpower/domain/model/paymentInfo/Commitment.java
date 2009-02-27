package com.mpower.domain.model.paymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mpower.type.CommitmentType;

public class Commitment extends AbstractPaymentInfoEntity { //SiteAware, PaymentSourceAware, AddressAware, PhoneAware, EmailAware, Customizable, Viewable TODO: for Ibatis 

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_CANCELED = "canceled";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_FULFILLED = "fulfilled";

    public static final String PLEDGE_STATUS_PENDING = "pending";
    public static final String PLEDGE_STATUS_IN_PROGRESS= "inProgress";
    public static final String PLEDGE_STATUS_FULFILLED = "fulfilled";

    public static final String FREQUENCY_ONE_TIME = "one time";
    public static final String FREQUENCY_WEEKLY = "weekly";
    public static final String FREQUENCY_TWICE_MONTHLY = "twice monthly";
    public static final String FREQUENCY_MONTHLY = "monthly";
    public static final String FREQUENCY_QUARTERLY = "quarterly";
    public static final String FREQUENCY_TWICE_ANNUALLY = "twice annually";
    public static final String FREQUENCY_ANNUALLY = "annually";
    public static final String FREQUENCY_UNSPECIFIED = "unspecified";

    private CommitmentType commitmentType;
    private List<Gift> gifts;
    private BigDecimal amountPerGift;
    private BigDecimal amountTotal = null;
    private Date startDate = Calendar.getInstance().getTime();
    private Date endDate;
    private Date pledgeDate = Calendar.getInstance().getTime();
    private Date pledgeCancelDate;
    private String pledgeCancelReason;
    private String status = STATUS_ACTIVE;
    private String pledgeStatus = PLEDGE_STATUS_PENDING;
    private Date createDate;
    private Date updateDate;
    private boolean autoPay = false;
    private String notes;
    private String frequency;
    private RecurringGift recurringGift;
    private Date lastEntryDate;
    private boolean recurring = false;
    private Date projectedDate;

    public Commitment() { }

    public Commitment(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
    }

    public CommitmentType getCommitmentType() {
        return commitmentType;
    }

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public RecurringGift getRecurringGift() {
        return recurringGift;
    }

    public void setRecurringGift(RecurringGift recurringGift) {
        this.recurringGift = recurringGift;
    }

    public boolean canRecur() {
        boolean recur = true;
        if (getEndDate() != null && getEndDate().before(Calendar.getInstance().getTime())) {
            recur = false;
        } else if ("canceled".equals(getStatus()) || "expired".equals(getStatus())) {
            recur = false;
        }
        return recur;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Date getProjectedDate() {
        return projectedDate;
    }

    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
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

	public void setPledgeStatus(String pledgeStatus) {
		this.pledgeStatus = pledgeStatus;
	}

	public String getPledgeStatus() {
		return pledgeStatus;
	}

	public void setPledgeDate(Date pledgeDate) {
		this.pledgeDate = pledgeDate;
	}

	public Date getPledgeDate() {
		return pledgeDate;
	}

	public void setPledgeCancelDate(Date pledgeCancelDate) {
		this.pledgeCancelDate = pledgeCancelDate;
	}

	public Date getPledgeCancelDate() {
		return pledgeCancelDate;
	}

	public void setPledgeCancelReason(String pledgeCancelReason) {
		this.pledgeCancelReason = pledgeCancelReason;
	}

	public String getPledgeCancelReason() {
		return pledgeCancelReason;
	}
	
    @Override
	public void prePersist() {
        super.prePersist();
        if (CommitmentType.RECURRING_GIFT.equals(getCommitmentType())) {
            setAmountTotal(null);
            setAutoPay(true);
            setProjectedDate(null);
        } 
        else if (CommitmentType.PLEDGE.equals(getCommitmentType())) {
            setAutoPay(false);
            setAddress(null);
            setPaymentSource(null);
            setPhone(null);
            setSelectedAddress(null);
            setSelectedPaymentSource(null);
            setSelectedPhone(null);
            if (isRecurring()) {
                setProjectedDate(null);
                setAmountTotal(null);
            } 
            else {
                setStartDate(null);
                setEndDate(null);
                setAmountPerGift(null);
                setFrequency(null);
            }
        } 
        else if (CommitmentType.MEMBERSHIP.equals(getCommitmentType())) {
            setAutoPay(false);
        }
    }
}
