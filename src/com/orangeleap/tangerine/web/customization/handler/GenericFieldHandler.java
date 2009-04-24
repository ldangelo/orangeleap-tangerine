package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class GenericFieldHandler implements FieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected FieldService fieldService;
    protected MessageService messageService;
    protected ConstituentService constituentService;
    protected RelationshipService relationshipService;

    public GenericFieldHandler(ApplicationContext appContext) {
        constituentService = (ConstituentService) appContext.getBean("constituentService");
        messageService = (MessageService) appContext.getBean("messageService");
        fieldService = (FieldService) appContext.getBean("fieldService");
        relationshipService = (RelationshipService) appContext.getBean("relationshipService");
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
        Object propertyValue = null;
        if (modelBeanWrapper.isReadableProperty(fieldProperty)) {
            propertyValue = modelBeanWrapper.getPropertyValue(fieldProperty);
        }
        return propertyValue;
    }
    
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = new FieldVO();

        fieldVO.setModel(model);
        fieldVO.setFieldName(getFieldPropertyName(currentField));
        fieldVO.setFieldType(getFieldType(currentField));

        String fieldLabelName = getFieldLabelName(currentField);
        fieldVO.setHelpText(messageService.lookupMessage(MessageResourceType.FIELD_HELP, fieldLabelName, locale));
        fieldVO.setHelpAvailable(!GenericValidator.isBlankOrNull(fieldVO.getHelpText()));

        String labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, fieldLabelName, locale);
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


        FieldRequired fr = fieldService.lookupFieldRequired(currentField);
        fieldVO.setRequired(fr != null && fr.isRequired());
        fieldVO.setHierarchy(relationshipService.isHierarchy(currentField.getFieldDefinition()));
        fieldVO.setDisabled(fieldService.isFieldDisabled(currentField, model));

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
