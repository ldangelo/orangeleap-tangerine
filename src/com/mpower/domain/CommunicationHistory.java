package com.mpower.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.type.CommunicationHistoryType;
import com.mpower.util.CommunicationHistoryCustomFieldMap;

/*
 * Communication history (including generated correspondence) for Constituent
 */

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "COMMUNICATION_HISTORY")
@Deprecated
public class CommunicationHistory implements SiteAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "COMMUNICATION_HISTORY_ID", updatable = false)
    private Long id;

    @Column(name = "RECORD_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDate;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", updatable = false)
    private Person person;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "PERSON_ID", updatable = false)
    private Person assignedTo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "RECORDED_BY", referencedColumnName = "PERSON_ID", updatable = false)
    private Person recordedBy;

    @Column(name = "COMMUNICATION_TYPE", updatable = false)
    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.MANUAL;

    @Column(name = "SYSTEM_GENERATED", updatable = false)
    private boolean systemGenerated = false;

    @ManyToOne(optional = true)
    @JoinColumn(name = "GIFT_ID", updatable = false)
    private Gift gift;

    @ManyToOne(optional = true)
    @JoinColumn(name = "COMMITMENT_ID", updatable = false)
    private Commitment commitment;

    @Column(name = "COMMENTS", length = 8000, updatable = false)
    private String comments = "";

    
    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    
    @OneToMany(mappedBy = "communicationHistory", cascade = CascadeType.ALL)
    private List<CommunicationHistoryCustomField> communicationHistoryCustomFields;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;
    
    @Transient
    private Map<String, FieldDefinition> fieldTypeMap = null;
    
	@Override
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


    public List<CommunicationHistoryCustomField> getCommunicationHistoryCustomFields() {
        if (communicationHistoryCustomFields == null) {
        	communicationHistoryCustomFields = new ArrayList<CommunicationHistoryCustomField>();
        }
        return communicationHistoryCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = CommunicationHistoryCustomFieldMap.buildCustomFieldMap(getCommunicationHistoryCustomFields(), this);
        }
        return customFieldMap;
    }

    @Override
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    @Override
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    @Override
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    @Override
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    @Override
    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    @Override
    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
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


	public void setAssignedTo(Person assignedTo) {
		this.assignedTo = assignedTo;
	}


	public Person getAssignedTo() {
		return assignedTo;
	}


	public void setRecordedBy(Person recordedBy) {
		this.recordedBy = recordedBy;
	}


	public Person getRecordedBy() {
		return recordedBy;
	}


	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	public Date getRecordDate() {
		return recordDate;
	}



    
}
