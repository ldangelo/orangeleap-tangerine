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

import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
public class Pledge extends Commitment implements Schedulable {
    private static final long serialVersionUID = 1L;

    private Date pledgeDate = Calendar.getInstance().getTime();
    private Date pledgeCancelDate;
    private String pledgeCancelReason;
    private String pledgeStatus = STATUS_PENDING;
    private boolean recurring = false;
    private Date projectedDate;
    private BigDecimal amountPaid;
    private BigDecimal amountRemaining;

    public Pledge() {
    }

    public Pledge(Long id) {
        setId(id);
    }

    public Pledge(BigDecimal amountTotal, BigDecimal amountPaid, String pledgeStatus) {
        setAmountTotal(amountTotal);
        setAmountPaid(amountPaid);
        setPledgeStatus(pledgeStatus);
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
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    @Override
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Override
    public BigDecimal getAmountRemaining() {
        return amountRemaining;
    }

    @Override
    public void setAmountRemaining(BigDecimal amountRemaining) {
        if (amountRemaining != null && amountRemaining.compareTo(BigDecimal.ZERO) < 0) {
            amountRemaining = BigDecimal.ZERO;
        }
        this.amountRemaining = amountRemaining;
    }
    
    @Override
    public BigDecimal getSchedulingAmount() {
    	return isRecurring()? this.amountPerGift: this.amountTotal;
    }
    
    @Override
    public void setSchedulingAmount(BigDecimal schedulingAmount) {
    	throw new RuntimeException("Value not settable.");
    }

    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        if (isRecurring()) {
            sb.append(getAmountPerGift()).append(", ");
        } else {
            sb.append(getAmountTotal()).append(", ");
        }
        sb.append(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(pledgeDate)).append(", ");
        if (isRecurring()) {
            sb.append(TangerineMessageAccessor.getMessage("recurring"));
        } else {
            sb.append(TangerineMessageAccessor.getMessage("notRecurring"));
        }
        return sb.toString();
    }

    @Override
    public void prePersist() {
        super.prePersist();

        if (getAmountPaid() == null) {
            setAmountPaid(BigDecimal.ZERO);
        }
        if (isRecurring()) {
            setProjectedDate(null);
            if (getEndDate() != null) {
                if (getAmountTotal() != null && getAmountPaid() != null) {
                    setAmountRemaining(getAmountTotal().subtract(getAmountPaid()));
                } else {
                    setAmountRemaining(getAmountTotal());
                }
            } else {
                setAmountRemaining(null);
                setAmountTotal(null);
            }
        } else {
        	// Non-recurring pledges have freq = ONE_TIME with start date = projected date.
            //setStartDate(null);
            //setEndDate(null);
            //setFrequency(null);
            setAmountPerGift(null);
            if (getAmountTotal() != null && getAmountPaid() != null) {
                setAmountRemaining(getAmountTotal().subtract(getAmountPaid()));
            } else {
                setAmountRemaining(getAmountTotal());
            }
        }
    }

    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
