package com.mpower.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.mpower.type.CommunicationHistoryType;
import com.mpower.type.CommunicationMode;
import com.mpower.util.CommunicationHistoryCustomFieldMap;

/*
 * Communication history (including generated correspondence) for Constituent
 */

@Entity
@Table(name = "COMMUNICATION_HISTORY")
public class CommunicationHistory implements SiteAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "COMMUNICATION_HISTORY_ID", updatable = false)
    private Long id;

    // External system id if present
    @Column(name = "TRANSACTION_ID", updatable = false)
    private String transactionId = "";

    @Column(name = "TRANSACTION_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    // Constituent individual or organization
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "PERSON_ID", updatable = false)
    private Person person;

    // Constituent individual contact person
    @ManyToOne(optional = true)
    @JoinColumn(name = "ATTENTION_ID", referencedColumnName = "PERSON_ID", updatable = false)
    private Person attention;

    // Site employee or volunteer
    @ManyToOne(optional = true)
    @JoinColumn(name = "REPRESENTATIVE_ID", referencedColumnName = "PERSON_ID", updatable = false)
    private Person representative;

    @Column(name = "COMMUNICATION_TYPE", updatable = false)
    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.UNSPECIFIED;

    @Column(name = "COMMUNICATION_MODE", updatable = false)
    private CommunicationMode communicationMode = CommunicationMode.UNSPECIFIED;

    @Column(name = "CLIENT_ORIGINATED", updatable = false)
    private boolean clientOriginated;

    @Column(name = "SYSTEM_GENERATED", updatable = false)
    private boolean systemGenerated;

    // The physical address, phone number, email address or other contact routing information where communication was sent.
    // This could change to include pointers to un-editable, historical Address/Email/Phone objects.
    @Column(name = "COMMUNICATION_SENT_TO", updatable = false)
    private String sentTo = "";
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "GIFT_ID", updatable = false)
    private Gift gift;

    @ManyToOne(optional = true)
    @JoinColumn(name = "COMMITMENT_ID", updatable = false)
    private Commitment commitment;

    // Initial communication history id; identifies a conversion thread, default to self
    @ManyToOne(optional = true)
    @JoinColumn(name = "TOPIC_ID", referencedColumnName = "COMMUNICATION_HISTORY_ID", updatable = false)
    private CommunicationHistory topic;

    // E.g. Sales, Inquiry, Complaint, etc.
    @Column(name = "REASON_CODE", updatable = false)
    private String reasonCode;

    @Column(name = "COMMENTS", length = 8000, updatable = false)
    private String comments = "";
    
    
    @OneToMany(mappedBy = "communicationHistory", cascade = CascadeType.ALL)
    private List<CommunicationHistoryCustomField> communicationHistoryCustomFields;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;



    
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



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}



	public Date getTransactionDate() {
		return transactionDate;
	}



	public void setPerson(Person person) {
		this.person = person;
	}



	public Person getPerson() {
		return person;
	}


	
	public void setAttention(Person attention) {
		this.attention = attention;
	}

	

	public Person getAttention() {
		return attention;
	}



	public void setRepresentative(Person representative) {
		this.representative = representative;
	}



	public Person getRepresentative() {
		return representative;
	}



	public void setCommunicationHistoryType(CommunicationHistoryType communicationHistoryType) {
		this.communicationHistoryType = communicationHistoryType;
	}



	public CommunicationHistoryType getCommunicationHistoryType() {
		return communicationHistoryType;
	}



	public void setCommunicationMode(CommunicationMode communicationMode) {
		this.communicationMode = communicationMode;
	}



	public CommunicationMode getCommunicationMode() {
		return communicationMode;
	}



	public void setClientOriginated(boolean clientOriginated) {
		this.clientOriginated = clientOriginated;
	}



	public boolean isClientOriginated() {
		return clientOriginated;
	}



	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}



	public boolean isSystemGenerated() {
		return systemGenerated;
	}



	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}



	public String getSentTo() {
		return sentTo;
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



	public void setTopicId(CommunicationHistory topic) {
		this.topic = topic;
	}



	public CommunicationHistory getTopic() {
		return topic;
	}



	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}



	public String getReasonCode() {
		return reasonCode;
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



    
}
