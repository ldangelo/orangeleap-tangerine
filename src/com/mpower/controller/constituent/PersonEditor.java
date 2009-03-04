package com.mpower.controller.constituent;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.Person;
import com.mpower.service.ConstituentService;

public class PersonEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    private ConstituentService personService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	if(StringUtils.trimToNull(text)==null) {
            return;
        }
        long personId = Long.valueOf(StringUtils.trimToNull(text));
        Person person = personService.readConstituentById(personId);
        setValue(person);
    }
    
   // public String getAsText() {
    //	if (this.getValue() instanceof String) {
    //	    return (String)this.getValue();
    //	}
   // 	return ("" + this.getValue());
   //     }
}
