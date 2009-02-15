package com.mpower.controller.validator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpower.domain.Viewable;
import com.mpower.domain.customization.Code;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.CodeService;
import com.mpower.type.FieldType;
import com.mpower.web.customization.FieldVO;

public class CodeValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private CodeService codeService;
    public static final String PROJECT_CODE = "projectCode";
    public static final String MOTIVATION_CODE = "motivationCode";
    public static final String DISTRIBUTION_LINES = "distributionLines";
   
    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        boolean supports = false;
        Class[] classes = clazz.getInterfaces();
        if (classes != null) {
            for (Class thisClass : classes) {
                if (thisClass.equals(Viewable.class)) {
                    supports = true;
                    break;
                }
            }
        }
        return supports;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (logger.isDebugEnabled()) {
            logger.debug("validate:");
        }
        if (target instanceof Viewable) {
            Viewable viewable = (Viewable)target;
            Map<String, FieldDefinition> map = viewable.getFieldTypeMap();
            if (map != null) {
                for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
                    String key = e.getKey();
                    FieldDefinition fd = e.getValue();
                    if (FieldType.CODE == fd.getFieldType() || FieldType.CODE_OTHER == fd.getFieldType()) {
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(viewable);
                        if (beanWrapper.isReadableProperty(key)) {
                            Object propertyValue = beanWrapper.getPropertyValue(key);
                            boolean isValid = this.isValidCode(key, viewable, fd, propertyValue);
                            
                            if (!isValid && FieldType.CODE_OTHER == fd.getFieldType()) {
                                if (this.hasOtherCode(key, viewable, fd, beanWrapper)) {
                                    isValid = true;
                                }
                            }
                            if (!isValid) {
                                errors.rejectValue(key, "invalidCode", new String[] { (String)propertyValue, fd.getDefaultLabel() }, "'" + propertyValue + "' is an invalid " + fd.getDefaultLabel());
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean isValidCode(String key, Viewable viewable, FieldDefinition fieldDefinition, Object propertyValue) {
        if (logger.isDebugEnabled()) {
            logger.debug("isValidCode: key = " + key + " site = " + (viewable.getSite() != null ? viewable.getSite().getName() : null) + " fieldName = " + fieldDefinition.getFieldName());
        }
        boolean isValid = false;
        
        if (propertyValue != null) {
            String codeValue = (String)propertyValue;
            Code code = codeService.readCodeBySiteTypeValue((viewable.getSite() != null ? viewable.getSite().getName() : null), fieldDefinition.getFieldName(), codeValue);
            if (code != null && codeValue.equals(code.getValue())) {
                isValid = true;
            }
        }
        else {
            // No property, passes validation
            isValid = true;
        }
        return isValid;
    }
    
    public boolean hasOtherCode(String key, Viewable viewable, FieldDefinition fieldDefinition, BeanWrapper beanWrapper) {
        boolean hasOtherCode = false;
        String keyOther = FieldVO.getOtherFieldName(key);
        if (logger.isDebugEnabled()) {
            logger.debug("hasOtherCode: keyOther = " + keyOther);
        }
        if (beanWrapper.isReadableProperty(keyOther)) {
            if (beanWrapper.getPropertyValue(keyOther) != null) { // if other code is entered, don't care what the value is, just that a value exists
                hasOtherCode = true;
            }
        }
        return hasOtherCode;
    }
}
