package com.mpower.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PersonDao;
import com.mpower.domain.Person;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.exception.EffectiveDateValidationException;
import com.mpower.service.relationship.DateRangedValue;

@Service("effectiveDateService")
public class EffectiveDateServiceImpl implements EffectiveDateService {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "personDao")
    private PersonDao personDao;

    
    // Read historical or future values of all fields
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=true)
    public Person readPersonAsOfDate(Person person, Date date) {
    	return person;
    }

    
    // Called by quartz batch job to update current custom field values to today's effective values.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = EffectiveDateValidationException.class)
    public void updateFieldsToCurrentDate(Person person) {
    	
    }

    
    // Date ranges do not overlap.  Returns string values for date ranges where there is a value for the field.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=true)
    public List<DateRangedValue> readEffectiveDatesForSingleValuedField(Person person, FieldDefinition fieldDefinition) {
    	return null;
    }
    
    
    // Date ranges (may not overlap) for which the custom field should be set equal to value.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = EffectiveDateValidationException.class)
    public void maintainEffectiveDatesForSingleValuedField(Person person, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException {

    }

    
    // Date ranges do not overlap for the same id.  Returns date ranges for each id where that id reference is one of the field values.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly=true)
    public List<DateRangedValue> readEffectiveDatesForMultiValuedField(Person person, FieldDefinition fieldDefinition) {
    	return null;
    }
    
    
    // Date ranges (may not overlap for the same id) for which the custom field should contain the id reference as one of its values.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = EffectiveDateValidationException.class)
    public void maintainEffectiveDatesForMultiValuedField(Person person, FieldDefinition fieldDefinition, List<DateRangedValue> values) throws EffectiveDateValidationException {

    }
    
    
}
