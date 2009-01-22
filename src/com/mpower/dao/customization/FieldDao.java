package com.mpower.dao.customization;

import java.util.List;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.type.EntityType;

public interface FieldDao {
	
    public FieldDefinition readFieldById(String fieldId);

    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType);

    public FieldRequired readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

    public FieldValidation readFieldValidation(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId);

    
	public Picklist readPicklistById(String picklistId);

	public PicklistItem readPicklistItemById(Long picklistItemId);

	public Picklist maintainPicklist(Picklist picklist);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);

	public List<Picklist> listPicklists(String siteName);

	void flush();

}
