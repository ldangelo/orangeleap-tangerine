package com.orangeleap.tangerine.service.relationship;

import java.util.Calendar;
import java.util.Date;

public class DateRangedValue {
	
	// Contains either an id or a String value.  Date ranges have no time values and are inclusive.
	
	private Long id;
	private String value;
	private Date effectiveDate;
	private Date discontinueDate;
	
	public static Date stripTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setEffectiveDate(Date effectiveDate) {
		stripTime(effectiveDate);
		this.effectiveDate = effectiveDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setDiscontinueDate(Date discontinueDate) {
		stripTime(discontinueDate);
		this.discontinueDate = discontinueDate;
	}
	public Date getDiscontinueDate() {
		return discontinueDate;
	}
	
}
