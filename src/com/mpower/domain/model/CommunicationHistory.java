package com.mpower.domain.model;

import java.util.Date;

import com.mpower.type.CommunicationHistoryType;
import com.mpower.util.StringConstants;

/*
 * Communication history (including generated correspondence) for Constituent
 */
// recordedBy and assignedTo have been moved to custom fields
public class CommunicationHistory extends AbstractCustomizableEntity {
    private static final long serialVersionUID = 1L;

    private Date recordDate;
    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.MANUAL;
    private boolean systemGenerated = false;
    private String comments = StringConstants.EMPTY;
    private Date createDate;
    private Date updateDate;
    private Person person;
    private Long giftId;
    private Long commitmentId;

	public Site getSite() {
		return getPerson().getSite();
	}

    public Person getPerson() {
        return person;
    }

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public CommunicationHistoryType getCommunicationHistoryType() {
	    return communicationHistoryType;
	}
	
	public void setCommunicationHistoryType(CommunicationHistoryType communicationHistoryType) {
		this.communicationHistoryType = communicationHistoryType;
	}
	
	public boolean isSystemGenerated() {
	    return systemGenerated;
	}

	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public String getComments() {
		return comments;
	}

    public void setComments(String comments) {
        this.comments = comments;
    }

	public Date getCreateDate() {
		return createDate;
	}

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Date getUpdateDate() {
		return updateDate;
	}

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public Date getRecordDate() {
		return recordDate;
	}

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(Long commitmentId) {
        this.commitmentId = commitmentId;
    }
}
