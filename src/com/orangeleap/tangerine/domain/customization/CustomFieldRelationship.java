package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

/**
 * Holds relationship custom fields for addition of relationship custom field templates.
 *
 **/
public class CustomFieldRelationship extends AbstractCustomizableEntity  {

    private static final long serialVersionUID = 1L;

    private Long fieldRelationshipId;

    public CustomFieldRelationship() {
        super();
    }

	public void setFieldRelationshipId(Long fieldRelationshipId) {
		this.fieldRelationshipId = fieldRelationshipId;
	}

	public Long getFieldRelationshipId() {
		return fieldRelationshipId;
	}


}


