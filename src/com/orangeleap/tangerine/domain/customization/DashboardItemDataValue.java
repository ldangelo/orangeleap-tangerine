package com.orangeleap.tangerine.domain.customization;

import java.math.BigDecimal;

// Result of dashboard item query
public class DashboardItemDataValue {
	
	private String label;
	private BigDecimal dataValue;
	
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setDataValue(BigDecimal dataValue) {
		this.dataValue = dataValue;
	}
	public BigDecimal getDataValue() {
		return dataValue;
	}
	
}
