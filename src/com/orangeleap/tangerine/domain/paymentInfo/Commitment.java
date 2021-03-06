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

import com.orangeleap.tangerine.type.CommitmentType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.springframework.core.style.ToStringCreator;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public abstract class Commitment extends AbstractPaymentInfoEntity {  

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_EXPIRED = "Expired";
    public static final String STATUS_FULFILLED = "Fulfilled";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_IN_PROGRESS = "In Progress";

    public static final String FREQUENCY_ONE_TIME = "one time";
    public static final String FREQUENCY_WEEKLY = "weekly";

    public static final String FREQUENCY_MONTHLY = "monthly";
    public static final String FREQUENCY_QUARTERLY = "quarterly";
    public static final String FREQUENCY_TWICE_ANNUALLY = "twice annually";
    public static final String FREQUENCY_ANNUALLY = "annually";
    public static final String FREQUENCY_UNSPECIFIED = "unspecified";

    private CommitmentType commitmentType;
    protected BigDecimal amountTotal = null;
    protected BigDecimal amountPerGift;
    protected BigDecimal amountPaid;
    protected BigDecimal amountRemaining;
    protected Date startDate = Calendar.getInstance().getTime();
    protected Date endDate;
    protected String frequency;
    private List<Gift> gifts;
    private List<Long> associatedGiftIds;
	protected Date cancelDate;
	protected String cancelReason;

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

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(BigDecimal amountRemaining) {
        if (amountRemaining != null && amountRemaining.compareTo(BigDecimal.ZERO) < 0) {
            amountRemaining = BigDecimal.ZERO;
        }
        this.amountRemaining = amountRemaining;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

	public void setCancelDate(Date cancelDate) {
	    this.cancelDate = cancelDate;
	}

	public Date getCancelDate() {
	    return cancelDate;
	}

	public void setCancelReason(String cancelReason) {
	    this.cancelReason = cancelReason;
	}

	public String getCancelReason() {
	    return cancelReason;
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

    public void addGift(Gift g) {
    	List<Gift> gifts = getGifts();
    	gifts.add(g);
    	setGifts(gifts);
    }

    public List<Long> getAssociatedGiftIds() {
        return associatedGiftIds;
    }

    public void setAssociatedGiftIds(List<Long> associatedGiftIds) {
        this.associatedGiftIds = associatedGiftIds;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("commitmentType", commitmentType).append("amountPerGift", amountPerGift).append("startDate", startDate).
               append("endDate", endDate).append("createDate", getCreateDate()).append("updateDate", getUpdateDate()).
               append("frequency", frequency).
               toString();
    }
}
