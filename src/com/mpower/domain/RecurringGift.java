package com.mpower.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.listener.TemporalTimestampListener;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "RECURRING_GIFT", uniqueConstraints = @UniqueConstraint(columnNames = { "COMMITMENT_ID" }))
public class RecurringGift implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "RECURRING_GIFT_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "COMMITMENT_ID")
    private Commitment commitment;

    @Column(name = "NEXT_RUN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
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
