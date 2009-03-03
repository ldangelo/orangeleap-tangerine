package com.mpower.controller.constituent;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.Person;
import com.mpower.service.PersonService;

public abstract class RequiresConstituentEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="personService")
    protected PersonService personService;
    protected String personId;

    public RequiresConstituentEditor() {
        super();
    }

    public RequiresConstituentEditor(String personId) {
        super();
        this.personId = personId;
    }

    protected Person getPerson() {
        return personService.readConstituentById(new Long(personId));
    }
}
