package com.mpower.domain.model;

import java.util.Date;

public class RecurringGift extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private Commitment commitment;

    private Date nextRunDate;

    public RecurringGift() {
    }

    public RecurringGift(Commitment commitment) {
        this.commitment = commitment;
    }

    public Commitment getCommitment() {
        return commitment;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }

    public Date getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(Date nextRunDate) {
        this.nextRunDate = nextRunDate;
    }
}
