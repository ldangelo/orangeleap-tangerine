package com.orangeleap.tangerine.controller.importexport;

import java.util.Date;

public class ExportRequest {
	
	private String entity;
	private Date fromDate;
	private Date toDate;
	private Long fromId;
	private Long toId;
	
	
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
	
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public Long getFromId() {
		return fromId;
	}
	public void setToId(Long toId) {
		this.toId = toId;
	}
	public Long getToId() {
		return toId;
	}
	
	
}
