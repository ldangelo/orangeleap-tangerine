package com.mpower.controller.constituent;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Person;
import com.mpower.service.PersonService;

public abstract class RequiresConstituentEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected PersonService personService;
    protected String personId;

    public RequiresConstituentEditor() {
        super();
    }

    public RequiresConstituentEditor(PersonService personService, String personId) {
        super();
        this.personService = personService;
        this.personId = personId;
    }

    protected Person getPerson() {
        return personService.readPersonById(new Long(personId));
    }
}
