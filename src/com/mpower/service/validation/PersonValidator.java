package com.mpower.service.validation;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;

public class PersonValidator {

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
