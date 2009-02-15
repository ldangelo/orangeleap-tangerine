package com.mpower.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.PersonService;
import com.mpower.service.customization.FieldService;
import com.mpower.service.customization.MessageService;
import com.mpower.type.FieldType;
import com.mpower.type.MessageResourceType;
import com.mpower.web.customization.FieldVO;

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

    protected Object getPropertyValue(Object model, FieldVO fieldVO) {
        BeanWrapper modelBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(model);
        String fieldProperty = fieldVO.getFieldName();
        Object propertyValue = modelBeanWrapper.getPropertyValue(fieldProperty);
        return propertyValue;
    }
    
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setModel(model);
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

        // For fields that depend on an entity attribute being selected to show.
        // For entity attributes like "employee,donor" the entity attribute css classes that activate this field are "ea-employee ea-donor"
        String entityAttributes = currentField.getFieldDefinition().getEntityAttributes();
        StringBuilder entityAttributesStyle = new StringBuilder();
        if (entityAttributes != null) {
            String[] entityAttributesArray = entityAttributes.split(",");
            for (String ea : entityAttributesArray) {
                entityAttributesStyle.append(" ea-"+ea);
            }
        }
        fieldVO.setEntityAttributes(entityAttributesStyle.toString().trim());


        FieldRequired fr = fieldService.lookupFieldRequired(siteName, currentField);
        fieldVO.setRequired(fr != null && fr.isRequired());
        fieldVO.setHierarchy(currentField.getFieldDefinition().isTree(siteName));
        fieldVO.setRelationship(currentField.getFieldDefinition().isRelationship(siteName));

        if (!FieldType.SPACER.equals(fieldVO.getFieldType()) && model != null) {
            Object propertyValue = getPropertyValue(model, fieldVO);
            fieldVO.setFieldValue(propertyValue);
            if (propertyValue != null) {
                BeanWrapper propBeanWrappermodel = new BeanWrapperImpl(propertyValue);
                if (propBeanWrappermodel.isReadableProperty("id")) {
                    fieldVO.setId((Long)propBeanWrappermodel.getPropertyValue("id"));
                }
                if (propBeanWrappermodel.isReadableProperty("displayValue")) {
                    fieldVO.setDisplayValue(propBeanWrappermodel.getPropertyValue("displayValue"));
                }
                if (propBeanWrappermodel.isReadableProperty("entityName")) {
                    fieldVO.setEntityName(propBeanWrappermodel.getPropertyValue("entityName").toString());
                }
            }
        }

        return fieldVO;
    }
}
