package com.orangeleap.tangerine.controller.importexport;

import java.util.Date;

public class ExportRequest {
	
	private String entity;
	private Date fromDate;
	private Date toDate;
	private Long fromAccount;
	private Long toAccount;
	private Date exportNcoaDate;
	private Date exportCassDate;
	
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getEntity() {
		return entity;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getToDate() {
		return toDate;
	}
	
	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Long getFromAccount() {
		return fromAccount;
	}
	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}
	public Long getToAccount() {
		return toAccount;
	}
	public void setExportNcoaDate(Date exportNcoaDate) {
		this.exportNcoaDate = exportNcoaDate;
	}
	public Date getExportNcoaDate() {
		return exportNcoaDate;
	}
	public void setExportCassDate(Date exportCassDate) {
		this.exportCassDate = exportCassDate;
	}
	public Date getExportCassDate() {
		return exportCassDate;
	}
	
	
}
