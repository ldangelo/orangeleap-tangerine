package com.orangeleap.tangerine.domain.paymentInfo;

import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Pledge extends Commitment {
    private static final long serialVersionUID = 1L;

    private Date pledgeDate = Calendar.getInstance().getTime();
    private Date pledgeCancelDate;
    private String pledgeCancelReason;
    private String pledgeStatus = STATUS_PENDING;
    private boolean recurring = false;
    private Date projectedDate;


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

    public String getPledgeShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append((new DecimalFormatSymbols(Locale.getDefault())).getCurrencySymbol());
        if (isRecurring()) {
            sb.append(getAmountPerGift()).append(", "); 
        }
        else {
            sb.append(getAmountTotal()).append(", "); 
        }
        sb.append(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(pledgeDate)).append(", ");
        if (isRecurring()) {
            sb.append(" Recurring"); // TODO: localize 'recurring'
        }
        else {
            sb.append(" Not Recurring"); // TODO: localize
        }
        return sb.toString();
    }

    @Override
    public void prePersist() {
        super.prePersist();
        
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

    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
