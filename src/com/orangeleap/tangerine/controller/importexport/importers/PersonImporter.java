package com.orangeleap.tangerine.controller.importexport.importers;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;


public class PersonImporter extends EntityImporter {
	
    protected final Log logger = LogFactory.getLog(getClass());

    private ConstituentService constituentService;
    private SiteService siteService;
    private PicklistItemService picklistItemService;

	public PersonImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		super(importRequest, applicationContext);
		constituentService = (ConstituentService)applicationContext.getBean("constituentService");
		siteService = (SiteService)applicationContext.getBean("siteService");
	    picklistItemService = (PicklistItemService)applicationContext.getBean("picklistItemService");
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
	public void importValueMap(String action, Map<String, String> values) throws ConstituentValidationException, BindException {
		
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
		siteservice.populateDefaultEntityEditorMaps(constituent);

		if (action.equals(EntityImporter.ACTION_DELETE)) {
			// TODO How to delete or set person to inactive?
		} else {
			mapValuesToObject(values, constituent);
		}
		
		setPicklistDefaultsForRequiredFields(constituent);
		constituent.setSuppressValidation(true);
		
		constituentService.maintainConstituent(constituent);
		
	}
	

	
	private void setPicklistDefaultsForRequiredFields(Person entity) {
		
		Map<String, FieldRequired> requiredFieldMap = siteService.readRequiredFields(PageType.person, tangerineUserHelper.lookupUserRoles());
		if (requiredFieldMap != null) {
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
			for (String key : requiredFieldMap.keySet()) {
				if (bw.isReadableProperty(key)) {
					Object ovalue = bw.getPropertyValue(key);
					String svalue = ovalue.toString();
					if (ovalue instanceof CustomField) {
						svalue = ((CustomField)ovalue).getValue();
					}
					if (StringUtils.trimToNull(""+svalue) == null) {		                			
						FieldRequired fr = requiredFieldMap.get(key);
						if (fr.isRequired()) {
							if (fr.getFieldDefinition().getFieldType().equals(FieldType.PICKLIST)) {
								Picklist picklist = picklistItemService.getPicklist(fr.getFieldDefinition().getFieldName());
								if (picklist != null) {
									List<PicklistItem> items = picklist.getActivePicklistItems();
									if (items.size() > 0) {
										String defaultvalue = items.get(0).getItemName();
										if (ovalue instanceof CustomField) {
											((CustomField)ovalue).setValue(defaultvalue);
										} else {
											bw.setPropertyValue(key, defaultvalue);
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}
	
	
	
}
