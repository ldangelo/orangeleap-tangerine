package com.mpower.service.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;

public class PersonValidator {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    public PersonValidator() {
    }

    public static void validatePerson(Person person) throws PersonValidationException {
        PersonValidationException ex = new PersonValidationException();
        if (person.getSite() == null) {
            ex.addValidationResult(PersonValidationException.SITE_REQUIRED);
        }
        if (ex.containsValidationResults()) {
            throw ex;
        }
    }
}
