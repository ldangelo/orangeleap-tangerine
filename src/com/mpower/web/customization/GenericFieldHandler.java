package com.mpower.web.customization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.Person;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.PersonService;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;
import com.mpower.type.MessageResourceType;

public class GenericFieldHandler implements FieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected FieldService fieldService;
    protected MessageService messageService;
    protected PersonService personService;

    public GenericFieldHandler(ApplicationContext appContext) {
        personService = (PersonService) appContext.getBean("personService");
        messageService = (MessageService) appContext.getBean("messageService");
        fieldService = (FieldService) appContext.getBean("fieldService");
    }

    public String getFieldLabelName(SectionField sectionField) {
        return sectionField.getFieldLabelName();
    }

    public String getFieldRequiredName(SectionField sectionField) {
        return sectionField.getFieldRequiredName();
    }

    public String getFieldPropertyName(SectionField sectionField) {
        String fieldPropertyName = sectionField.getFieldPropertyName();

        if (sectionField.getFieldDefinition().isCustom()) {
            fieldPropertyName += ".value";
        }
        return fieldPropertyName;
    }

    public FieldType getFieldType(SectionField sectionField) {
        return sectionField.getFieldType();
    }

    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setFieldName(getFieldPropertyName(currentField));
        fieldVO.setFieldType(getFieldType(currentField));

        String fieldLabelName = getFieldLabelName(currentField);
        fieldVO.setHelpText(messageService.lookupMessage(siteName, MessageResourceType.FIELD_HELP, fieldLabelName, locale));
        fieldVO.setHelpAvailable(!GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

        String labelText = messageService.lookupMessage(siteName, MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
        if (GenericValidator.isBlankOrNull(labelText)) {
            if (!currentField.isCompoundField()) {
                labelText = currentField.getFieldDefinition().getDefaultLabel();
            } else {
                labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
            }
        }
        fieldVO.setLabelText(labelText);
        
        // For custom fields that depend on an entity attribute being selected to show.
        fieldVO.setEntityAttributes(currentField.getFieldDefinition().getEntityAttributes());


        FieldRequired fr = fieldService.lookupFieldRequired(siteName, currentField);
        fieldVO.setRequired(fr != null && fr.isRequired() && !fr.hasConditions());

        if (!FieldType.SPACER.equals(fieldVO.getFieldType())) {
            String fieldProperty = fieldVO.getFieldName();
            BeanWrapper modelBeanWrapper = new BeanWrapperImpl(model);
            Object propertyValue = modelBeanWrapper.getPropertyValue(fieldProperty);
            fieldVO.setFieldValue(propertyValue);
            if (propertyValue!=null) {
            	FieldType fieldType = currentField.getFieldDefinition().getFieldType();
            	EntityType entityType = currentField.getFieldDefinition().getEntityType();
            	boolean isCustom = currentField.getFieldDefinition().isCustom();

            	BeanWrapper propBeanWrappermodel = new BeanWrapperImpl(propertyValue);
	            if (propBeanWrappermodel.isReadableProperty("id")) {
	            	fieldVO.setId((Long)propBeanWrappermodel.getPropertyValue("id"));
	            }
	            if (propBeanWrappermodel.isReadableProperty("displayValue")) {
	            	fieldVO.setDisplayValue(propBeanWrappermodel.getPropertyValue("displayValue"));
	            }
	            if (isCustom) {
   	            	if (fieldType == FieldType.QUERY_LOOKUP || fieldType == FieldType.MULTI_QUERY_LOOKUP) {
   	            		List<Long> list = new ArrayList<Long>();
   	            		fieldVO.setIds(list);
	            		String[] ids = ((String)propertyValue).split(",");
	            		StringBuffer sb = new StringBuffer();
	            		for (String id : ids) {
	            			if (sb.length() > 0) sb.append("<br>");
	            			Long longid = Long.valueOf(id);
	            			sb.append(resolve(longid, entityType));
		            	    fieldVO.setId(longid);  
		            	    list.add(longid);
	            		}
	            	    fieldVO.setDisplayValue(sb.toString());
	            	}
	            }
	            if (propBeanWrappermodel.isReadableProperty("entityName")) {
	            	fieldVO.setEntityName(propBeanWrappermodel.getPropertyValue("entityName").toString());
	            }
            }
        }

        return fieldVO;
    }
    
    private String resolve(Long id, EntityType entityType) {
	    if (entityType == EntityType.person) {
	    	Person person = personService.readPersonById(id);
	    	return person.getDisplayValue();
	    }
	    return ""+id;
    }
    
    
    
}
