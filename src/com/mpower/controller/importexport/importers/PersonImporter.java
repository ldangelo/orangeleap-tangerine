package com.mpower.controller.importexport.importers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.service.PersonService;


public class PersonImporter implements EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personservice;

	public PersonImporter(ApplicationContext applicationContext) {
		personservice = (PersonService)applicationContext.getBean("personService");
	}
	

	@Override
	public void importValueMap(Map<String, String> m) {
		logger.debug("Importing person...");
		
	}

}
