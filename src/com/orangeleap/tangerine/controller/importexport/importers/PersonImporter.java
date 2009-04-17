package com.orangeleap.tangerine.controller.importexport.importers;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;


public class PersonImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private ConstituentService constituentService;
    private TangerineUserHelper tangerineUserHelper;

	public PersonImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		super(importRequest, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
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
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException {
		
		Person constituent;
		
		String id = values.get(getIdField());
		if (action.equals(EntityImporter.ACTION_ADD)) {
			constituent = constituentService.createDefaultConstituent();
			logger.debug("Adding new entity...");
		} else {
			if (id == null) {
                throw new RuntimeException(getIdField() + " field is required for CHANGE or DELETE action.");
            }
		    constituent = constituentService.readConstituentByAccountNumber(id);
			if (constituent == null) {
                throw new RuntimeException(getIdField() + " " + id + " not found.");
            }
			logger.debug("Importing constituent "+id+"...");
		}
		
		// We want person relationship maintenance, so type maps are required, similar to manual edit screen.
        Map<String, String> fieldLabelMap = siteservice.readFieldLabels(getPageType(), tangerineUserHelper.lookupUserRoles(), Locale.getDefault());
        constituent.setFieldLabelMap(fieldLabelMap);

        Map<String, Object> valueMap = siteservice.readFieldValues(getPageType(), tangerineUserHelper.lookupUserRoles(), constituent);
        constituent.setFieldValueMap(valueMap);

        Map<String, FieldDefinition> typeMap = siteservice.readFieldTypes(getPageType(), tangerineUserHelper.lookupUserRoles());
        constituent.setFieldTypeMap(typeMap);

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// TODO How to delete or set person to inactive?
		} else {
			mapValuesToObject(values, constituent);
		}
		
		constituentService.maintainConstituent(constituent);
		
	}
	
	
	
}
