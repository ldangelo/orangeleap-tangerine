package com.mpower.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.domain.Person;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.exception.EffectiveDateValidationException;
import com.mpower.service.relationship.DateRangedValue;

public interface EffectiveDateService {
	
    public Person readPersonAsOfDate(Person person, Date date);
 
    public void updateFieldsToCurrentDate(Person person);

    public List<DateRangedValue> readEffectiveDatesForSingleValuedField(Person person, FieldDefinition fieldDefinition);
    
    public void maintainEffectiveDatesForSingleValuedField(Person person, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException;

    public List<DateRangedValue> readEffectiveDatesForMultiValuedField(Person person, FieldDefinition fieldDefinition);

    public void maintainEffectiveDatesForMultiValuedField(Person person, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException;

}
