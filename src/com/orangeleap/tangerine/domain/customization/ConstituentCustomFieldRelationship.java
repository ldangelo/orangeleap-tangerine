package com.orangeleap.tangerine.domain.customization;

import java.util.Date;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

/**
 * Holds custom fields for an instance of a relationship-type custom field row.
 *
 **/
public class ConstituentCustomFieldRelationship extends AbstractCustomizableEntity  {

    private static final long serialVersionUID = 1L;

    private Long constituentId;
    private Long customFieldId;
    private Long fieldRelationshipId;
    private String fieldName;
    private String fieldValue;
    private Date startDate;
    private Date endDate;

    public ConstituentCustomFieldRelationship() {
        super();
    }

	public void setConstituentId(Long constituentId) {
		this.constituentId = constituentId;
	}

	public Long getConstituentId() {
		return constituentId;
	}

	public void setCustomFieldId(Long customFieldId) {
		this.customFieldId = customFieldId;
	}

	public Long getCustomFieldId() {
		return customFieldId;
	}

	public void setFieldRelationshipId(Long fieldRelationshipId) {
		this.fieldRelationshipId = fieldRelationshipId;
	}

	public Long getFieldRelationshipId() {
		return fieldRelationshipId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldValue() {
		return fieldValue;
	}

}


