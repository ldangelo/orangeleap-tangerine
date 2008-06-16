package com.mpower.service.customization;

import java.util.Locale;

import com.mpower.domain.entity.Site;
import com.mpower.domain.entity.customization.FieldDefinition;
import com.mpower.domain.entity.customization.Picklist;
import com.mpower.domain.entity.customization.SectionField;


public interface FieldService {
	public FieldDefinition readFieldById(String fieldId);

	public Picklist readPicklistBySiteAndFieldName(Site site, String fieldName);

    public boolean lookupFieldRequired(Site site, SectionField currentField);

    public Object lookupFieldDefaultValue(Site site, Locale locale, String fieldId);
}
