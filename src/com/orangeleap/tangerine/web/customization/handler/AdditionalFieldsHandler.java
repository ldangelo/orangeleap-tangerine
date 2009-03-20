package com.orangeleap.tangerine.web.customization.handler;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.orangeleap.tangerine.web.customization.FieldVO;

/**
 * Used only for multi-valued fields (multi-picklist, multi-querylist, etc) where the user can type in values in additional to the existing pre-defined ones
 */
public class AdditionalFieldsHandler {

    public static void handleAdditionalFields(FieldVO fieldVO, Object model) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        String additionalFieldName = fieldVO.getAdditionalFieldName();
        
        if (bw.isReadableProperty(additionalFieldName)) {
            Object propertyValue = bw.getPropertyValue(additionalFieldName);
            
            if (propertyValue != null) {
                fieldVO.setAdditionalDisplayValues(propertyValue.toString());
            }
        }
    }
}
