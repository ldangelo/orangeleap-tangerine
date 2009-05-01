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
    private String masterFieldDefinitionId;
    private String siteName;


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


