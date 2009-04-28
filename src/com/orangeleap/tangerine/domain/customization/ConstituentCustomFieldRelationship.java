package com.orangeleap.tangerine.domain.customization;

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


}


