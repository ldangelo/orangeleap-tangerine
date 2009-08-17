package com.orangeleap.tangerine.domain;

import java.util.Date;

public interface Schedulable extends Customizable {
	
	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getEndDate();

	public void setEndDate(Date endDate);

	public String getFrequency();

	public void setFrequency(String frequency);

}
