package com.mpower.controller.importexport.exporters;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.service.PersonService;
import com.mpower.type.PageType;


public class PersonExporter extends EntityExporter {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PersonService personservice;

	public PersonExporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		personservice = (PersonService)applicationContext.getBean("personService");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List readAll() {
		return personservice.readAllConstituentsBySite();
	}

	@Override
	protected PageType getPageType() {
	    return PageType.person;
	}


}
