package com.mpower.controller.importexport.importers;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.service.PersonService;
import com.mpower.service.exception.PersonValidationException;
import com.mpower.type.PageType;
import com.mpower.util.TangerineUserHelper;


public class PersonImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonService personservice;
    private TangerineUserHelper tangerineUserHelper;

	public PersonImporter(String entity, ApplicationContext applicationContext) {
		super(entity, applicationContext);
		personservice = (PersonService)applicationContext.getBean("personService");
		tangerineUserHelper = (TangerineUserHelper)applicationContext.getBean("tangerineUserHelper");
	}
	
	@Override
	public String getIdField() {
		return "accountNumber";
	}
	
	@Override
	protected PageType getPageType() {
	    return PageType.person;
	}



	@Override
	public void importValueMap(String action, Map<String, String> values) throws PersonValidationException {
		
		Person person;
		
		String id = values.get(getIdField());
		if (action.equals(EntityImporter.ACTION_ADD)) {
			person = personservice.createDefaultConstituent();
			logger.debug("Adding new entity...");
		} else {
			if (id == null) {
                throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
            }
			Long lid;
			try {
				lid = new Long(id);
			} catch (Exception e) {
				throw new RuntimeException("Invalid id value "+id);
			}
		    person = personservice.readConstituentById(lid);
			if (person == null) {
                throw new RuntimeException(getIdField() + " " + id + " not found.");
            }
			logger.debug("Importing constituent "+id+"...");
		}
		
		// We want person relationship maintenance, so type maps are required, similar to manual edit screen.
        Map<String, String> fieldLabelMap = siteservice.readFieldLabels(getPageType(), tangerineUserHelper.lookupUserRoles(), Locale.getDefault());
        person.setFieldLabelMap(fieldLabelMap);

        Map<String, Object> valueMap = siteservice.readFieldValues(getPageType(), tangerineUserHelper.lookupUserRoles(), person);
        person.setFieldValueMap(valueMap);

        Map<String, FieldDefinition> typeMap = siteservice.readFieldTypes(getPageType(), tangerineUserHelper.lookupUserRoles());
        person.setFieldTypeMap(typeMap);

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// TODO How to delete or set person to inactive?
		} else {
			mapValuesToObject(values, person);
		}
		
		personservice.maintainConstituent(person);
		
	}
	
	
	
}
