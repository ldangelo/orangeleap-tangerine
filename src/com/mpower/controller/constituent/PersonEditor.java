package com.mpower.controller.constituent;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Person;
import com.mpower.service.PersonService;

public class PersonEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personService;

    public PersonEditor(PersonService personService) {
        this.personService = personService;
    }

    public void setAsText(String text) throws IllegalArgumentException {
    	if(StringUtils.trimToNull(text)==null) return;
        long personId = Long.valueOf(StringUtils.trimToNull(text));
        Person person = personService.readPersonById(personId);
        setValue(person);
    }
    
   // public String getAsText() {
    //	if (this.getValue() instanceof String) {
    //	    return (String)this.getValue();
    //	}
   // 	return ("" + this.getValue());
   //     }
}
