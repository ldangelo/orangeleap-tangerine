package com.mpower.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.mpower.domain.GeneratedId;
import com.mpower.type.CommunicationHistoryType;

/*
 * Communication history (including generated correspondence) for Constituent
 */

public class CommunicationHistory extends AbstractCustomizableEntity {

	// recordedBy and assignedTo have been moved to custom fields
	
    private static final long serialVersionUID = 1L;


    private Date recordDate;

    private Person person;

    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.MANUAL;

    private boolean systemGenerated = false;

    private Gift gift;

    private Commitment commitment;

    private String comments = "";

    
    private Date createDate;

    private Date updateDate;


	public Site getSite() {
		return getPerson().getSite();
	}


	public void setId(Long id) {
		this.id = id;
	}



	public Long getId() {
		return id;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public Person getPerson() {
		return person;
	}


	
	public void setCommunicationHistoryType(CommunicationHistoryType communicationHistoryType) {
		this.communicationHistoryType = communicationHistoryType;
	}



	public CommunicationHistoryType getCommunicationHistoryType() {
		return communicationHistoryType;
	}


	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}



	public boolean isSystemGenerated() {
		return systemGenerated;
	}



	public void setGift(Gift gift) {
		this.gift = gift;
	}



	public Gift getGift() {
		return gift;
	}



	public void setCommitment(Commitment commitment) {
		this.commitment = commitment;
	}



	public Commitment getCommitment() {
		return commitment;
	}



	public void setComments(String comments) {
		this.comments = comments;
	}



	public String getComments() {
		return comments;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}

	
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	public Date getRecordDate() {
		return recordDate;
	}



    
}
