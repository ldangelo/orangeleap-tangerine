package com.mpower.service.customization;

import com.mpower.domain.model.customization.FieldRequired;
import com.mpower.domain.model.customization.FieldValidation;
import com.mpower.domain.model.customization.Picklist;
import com.mpower.domain.model.customization.SectionField;
import com.mpower.type.EntityType;

public interface FieldService {
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType);

    public FieldRequired lookupFieldRequired(SectionField currentField);

    public FieldValidation lookupFieldValidation(SectionField currentField);
}
