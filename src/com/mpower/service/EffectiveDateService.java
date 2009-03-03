package com.mpower.service;

import java.util.Date;
import java.util.List;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.service.exception.EffectiveDateValidationException;
import com.mpower.service.relationship.DateRangedValue;

public interface EffectiveDateService {
	
    public Person readConstituentAsOfDate(Person constituent, Date date);
 
    public void updateFieldsToCurrentDate(Person constituent);

    public List<DateRangedValue> readEffectiveDatesForSingleValuedField(Person constituent, FieldDefinition fieldDefinition);
    
    public void maintainEffectiveDatesForSingleValuedField(Person constituent, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException;

    public List<DateRangedValue> readEffectiveDatesForMultiValuedField(Person constituent, FieldDefinition fieldDefinition);

    public void maintainEffectiveDatesForMultiValuedField(Person constituent, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException;

}
