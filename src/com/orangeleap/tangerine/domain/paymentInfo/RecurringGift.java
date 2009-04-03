package com.orangeleap.tangerine.domain.paymentInfo;

import java.util.Date;

public class RecurringGift extends Commitment {

    private static final long serialVersionUID = 1L;

    private Date nextRunDate;
    private String recurringGiftStatus = STATUS_PENDING; 
    private boolean autoPay = false;

    public RecurringGift() { }

    public RecurringGift(Date nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public Date getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(Date nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public String getRecurringGiftStatus() {
        return recurringGiftStatus;
    }

    public void setRecurringGiftStatus(String recurringGiftStatus) {
        this.recurringGiftStatus = recurringGiftStatus;
    }

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }
    
    @Override
    public void prePersist() {
        super.prePersist();
        setAutoPay(true);
    }

    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
