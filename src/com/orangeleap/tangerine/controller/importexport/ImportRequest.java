package com.orangeleap.tangerine.controller.importexport;

import java.util.Date;

public class ImportRequest {
	
	private String entity;
	private Date ncoaDate;
	private Date cassDate;
	private boolean convertToProperCase = true;
	
	
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getEntity() {
		return entity;
	}
	public void setNcoaDate(Date ncoaDate) {
		this.ncoaDate = ncoaDate;
	}
	public Date getNcoaDate() {
		return ncoaDate;
	}
	public void setCassDate(Date caasDate) {
		this.cassDate = caasDate;
	}
	public Date getCassDate() {
		return cassDate;
	}
	public void setConvertToProperCase(boolean convertToProperCase) {
		this.convertToProperCase = convertToProperCase;
	}
	public boolean isConvertToProperCase() {
		return convertToProperCase;
	}
	
}
