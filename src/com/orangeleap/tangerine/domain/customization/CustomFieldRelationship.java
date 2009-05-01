package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

/**
 * Holds relationship custom fields for addition of relationship custom field templates.
 *
 **/
public class CustomFieldRelationship extends AbstractCustomizableEntity  {

    private static final long serialVersionUID = 1L;

    private String masterFieldDefinitionId;
    private String siteName;

    public CustomFieldRelationship() {
        super();
    }

	public void setMasterFieldDefinitionId(String masterFieldDefinitionId) {
		this.masterFieldDefinitionId = masterFieldDefinitionId;
	}

	public String getMasterFieldDefinitionId() {
		return masterFieldDefinitionId;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
	}



}


