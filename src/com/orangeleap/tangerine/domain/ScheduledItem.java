package com.orangeleap.tangerine.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class ScheduledItem extends AbstractCustomizableEntity implements Schedulable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String sourceEntity;
    private Long sourceEntityId;
    private String scheduledItemType;
    private BigDecimal scheduledItemAmount;
    private String resultEntity;
    private Long resultEntityId;
    private Date originalScheduledDate;
    private Date actualScheduledDate;
    private Date completionDate;
    private String completionStatus;
    private Long modifiedBy;
    private Date createDate;
    private Date updateDate;
	
    public ScheduledItem() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    
    public Long getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(Long sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    
	public void setScheduledItemType(String scheduledItemType) {
		this.scheduledItemType = scheduledItemType;
	}


	public String getScheduledItemType() {
		return scheduledItemType;
	}

	
	public void setScheduledItemAmount(BigDecimal scheduledItemAmount) {
		this.scheduledItemAmount = scheduledItemAmount;
	}

	public BigDecimal getScheduledItemAmount() {
		return scheduledItemAmount;
	}

    
    public String getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(String resultEntity) {
        this.resultEntity = resultEntity;
    }

    
    public Long getResultEntityId() {
        return resultEntityId;
    }

    public void setResultEntityId(Long resultEntityId) {
        this.resultEntityId = resultEntityId;
    }

    
    public Date getOriginalScheduledDate() {
        return originalScheduledDate;
    }

    public void setOriginalScheduledDate(Date originalScheduledDate) {
        this.originalScheduledDate = originalScheduledDate;
    }

    
    public Date getActualScheduledDate() {
        return actualScheduledDate;
    }

    public void setActualScheduledDate(Date actualScheduledDate) {
        this.actualScheduledDate = actualScheduledDate;
    }

    
    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    
    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    
    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
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
    
    public boolean isCompleted() {
    	return completionDate != null;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ScheduledItem)) {
            return false;
        }
        ScheduledItem a = (ScheduledItem) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(sourceEntity, a.getSourceEntity())
        .append(sourceEntityId, a.getSourceEntityId())
        .append(scheduledItemType, a.getScheduledItemType())
        .append(scheduledItemAmount, a.getScheduledItemAmount())
        .append(resultEntity, a.getResultEntity())
        .append(resultEntityId, a.getResultEntityId())
        .append(originalScheduledDate, a.getOriginalScheduledDate())
        .append(actualScheduledDate, a.getActualScheduledDate())
        .append(completionDate, a.getCompletionDate())
        .append(completionStatus, a.getCompletionStatus())
        .append(modifiedBy, a.getModifiedBy())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+sourceEntity)
        .append(""+sourceEntityId)
        .append(""+scheduledItemType)
        .append(""+scheduledItemAmount)
        .append(""+resultEntity)
        .append(""+resultEntityId)
        .append(""+originalScheduledDate)
        .append(""+actualScheduledDate)
        .append(""+completionDate)
        .append(""+completionStatus)
        .append(""+modifiedBy)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ToStringCreator(this)
        .append("ActualScheduledDate", sdf.format(actualScheduledDate))
        .append(super.toString())
        .append("id", ""+id)
        .append("sourceEntity", ""+sourceEntity)
        .append("sourceEntityId", ""+sourceEntityId)
        .append("scheduledItemType", ""+scheduledItemType)
        .append("scheduledItemAmount", ""+scheduledItemAmount)
        .append("resultEntity", ""+resultEntity)
        .append("resultEntityId", ""+resultEntityId)
        .append("originalScheduledDate", ""+originalScheduledDate)
        .append("actualScheduledDate", ""+actualScheduledDate)
        .append("completionDate", ""+completionDate)
        .append("completionStatus", ""+completionStatus)
        .append("modifiedBy", ""+modifiedBy)
        .toString();
    }

    // Schedulable interface (can be used to set up child item schedules off of individual parent scheduled items)
    
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String FREQUENCY = "frequency";
    private static final String SCHEDULING_AMOUNT = "schedulingAmount";
    
	@Override
	public Date getEndDate() {
		return getCustomFieldAsDate(END_DATE);
	}


	@Override
	public String getFrequency() {
		return getCustomFieldValue(FREQUENCY);
	}


	@Override
	public Date getStartDate() {
		return getCustomFieldAsDate(START_DATE);
	}

	@Override
	public BigDecimal getSchedulingAmount() {
		return getCustomFieldAsBigDecimal(SCHEDULING_AMOUNT);
	}



	@Override
	public void setEndDate(Date endDate) {
		setCustomFieldAsDate(END_DATE, endDate);
	}


	@Override
	public void setFrequency(String frequency) {
		setCustomFieldValue(FREQUENCY, frequency);
	}


	@Override
	public void setStartDate(Date startDate) {
		setCustomFieldAsDate(START_DATE, startDate);
	}

	@Override
	public void setSchedulingAmount(BigDecimal schedulingAmount) {
		setCustomFieldAsBigDecimal(SCHEDULING_AMOUNT, schedulingAmount);
	}



}