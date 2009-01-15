package com.mpower.controller.importexport.importers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.Person;
import com.mpower.service.PersonService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.service.impl.SessionServiceImpl;


public class PersonImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personservice;

	public PersonImporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		personservice = (PersonService)applicationContext.getBean("personService");
	}
	
	@Override
	public String getIdField() {
		return "accountNumber";
	}

	// TODO move to superclass
	@Override
	public void importValueMap(String action, Map<String, String> values) throws PersonValidationException {
		
		Person person;
		
		String id = values.get(getIdField());
		if (action.equals(EntityImporter.ACTION_ADD)) {
			person = personservice.createDefaultPerson(SessionServiceImpl.lookupUserSiteName());
			logger.debug("Adding new entity...");
		} else {
			if (id == null) throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
		    person = personservice.readPersonById(new Long(id));
			logger.debug("Importing entity "+id+"...");
		}
		
		

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// How to delete or set person to inactive?
		} else {
			mapValuesToObject(values, person);
		}
		
		personservice.maintainPerson(person);
		
		
	}
	
	protected void mapValuesToObject(Map<String, String> values, Object o) {
		// TODO
	}

}
