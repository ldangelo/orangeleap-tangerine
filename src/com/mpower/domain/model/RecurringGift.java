package com.mpower.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.mpower.domain.GeneratedId;

public class RecurringGift implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Commitment commitment;

    private Date nextRunDate;

    public RecurringGift() {
    }

    public RecurringGift(Commitment commitment) {
        this.commitment = commitment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
