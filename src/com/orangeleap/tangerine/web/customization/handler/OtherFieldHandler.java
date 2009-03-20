package com.orangeleap.tangerine.web.customization.handler;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.orangeleap.tangerine.web.customization.FieldVO;

/**
 * Used only for single-valued fields (picklist, querylist, etc) where the user can type in and use a free-form value instead of an existing pre-defined one
 */
public class OtherFieldHandler {

    public static void handleOtherField(FieldVO fieldVO, Object model) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        
        String fieldName = fieldVO.getFieldName();
        if (bw.isReadableProperty(fieldName)) {
            Object propertyValue = bw.getPropertyValue(fieldName);
            
            /* If the property value and ID are not defined, see if the 'other' field is populated and use that value instead */
            if (fieldVO.getId() == null && propertyValue == null) {
                if (bw.isReadableProperty(fieldVO.getOtherFieldName())) {
                    Object otherFieldValue = bw.getPropertyValue(fieldVO.getOtherFieldName());
                    if (otherFieldValue != null) {
                        fieldVO.setDisplayValue(otherFieldValue);
                    }
                }
            }
        }
    }
}
