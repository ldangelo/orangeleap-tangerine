package com.mpower.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.SectionField;
import com.mpower.web.customization.FieldVO;

public class LookupOtherFieldHandler extends LookupFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public LookupOtherFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, siteName, model);
        BeanWrapper bw = new BeanWrapperImpl(model);
        
        String fieldName = fieldVO.getFieldName();
        Object propertyValue = bw.getPropertyValue(fieldName);
        
        /* If the property value and ID are not defined, see if the 'other' field is populated and use that value instead */
        if (fieldVO.getId() == null && propertyValue == null) {
            Object otherFieldValue = bw.getPropertyValue(fieldVO.getOtherFieldName());
            if (otherFieldValue != null) {
                fieldVO.setDisplayValue(otherFieldValue);
            }
        }
        return fieldVO;
    }
}
