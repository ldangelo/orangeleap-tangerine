package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class RecurringGift extends Commitment {

	private static final long serialVersionUID = 1L;

	private Date nextRunDate;
	private String recurringGiftStatus = STATUS_PENDING;
	private boolean autoPay = false;

	public RecurringGift() {
	}

	public RecurringGift(Date nextRunDate) {
		this.nextRunDate = nextRunDate;
	}

	public RecurringGift(Long id, String recurringGiftStatus, Date startDate, Date endDate) {
		this.id = id;
		this.recurringGiftStatus = recurringGiftStatus;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getShortDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAmountPerGift()).append(", ");
		sb.append(getFrequency()).append(", ");

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		if (startDate != null) {
			sb.append(df.format(startDate));
		}
		if (endDate != null) {
			sb.append(" - ").append(df.format(endDate));
		}
		return sb.toString();
	}

	@Override
	public void prePersist() {
		super.prePersist();

		if (getAmountPaid() == null) {
			setAmountPaid(BigDecimal.ZERO);
		}
		if (getEndDate() != null) {
			if (getAmountTotal() != null && getAmountPaid() != null) {
				setAmountRemaining(getAmountTotal().subtract(getAmountPaid()));
			}
			else {
				setAmountRemaining(getAmountTotal());
			}
		}
		else {
			setAmountRemaining(null);
			setAmountTotal(null);
		}
	}

	@Override
	public String toString() {
		return super.toString(); // TODO
	}
}
