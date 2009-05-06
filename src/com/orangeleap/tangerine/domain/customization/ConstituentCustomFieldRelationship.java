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
    private String customFieldValue;
    private Date customFieldStartDate;
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

	public void setCustomFieldValue(String customFieldValue) {
		this.customFieldValue = customFieldValue;
	}

	public String getCustomFieldValue() {
		return customFieldValue;
	}

	public void setCustomFieldStartDate(Date customFieldStartDate) {
		this.customFieldStartDate = customFieldStartDate;
	}

	public Date getCustomFieldStartDate() {
		return customFieldStartDate;
	}


}


