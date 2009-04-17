package com.orangeleap.tangerine.controller.importexport;

import java.util.Date;

public class ImportRequest {
	
	private String entity;
	private Date ncoaDate;
	private Date caasDate;
	
	
	
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
	public void setCaasDate(Date caasDate) {
		this.caasDate = caasDate;
	}
	public Date getCaasDate() {
		return caasDate;
	}
	
}
