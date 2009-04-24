package com.orangeleap.tangerine.service.customization;

import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;

public interface FieldService {
    public Picklist readPicklistByFieldNameEntityType(String fieldName, EntityType entityType);

    public FieldRequired lookupFieldRequired(SectionField currentField);

    public FieldValidation lookupFieldValidation(SectionField currentField);
    
    public boolean isFieldDisabled(SectionField sectionField, Object model);
}
