/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.validator;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.*;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    private static final String EXTENSIONS = "extensions:";

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;
    
    @Resource(name="addressValidator")
    protected AddressValidator addressValidator;
    
    @Resource(name="phoneValidator")
    protected PhoneValidator phoneValidator;
    
    @Resource(name="emailValidator")
    protected EmailValidator emailValidator;
    
    @Resource(name="paymentSourceValidator")
    protected PaymentSourceValidator paymentSourceValidator;
    
    @Resource(name="siteService")
    protected SiteService siteService;

    protected PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return 
        Constituent.class.equals(clazz) 
        || AbstractPaymentInfoEntity.class.isAssignableFrom(clazz)
        || CommunicationHistory.class.equals(clazz) 
        || GiftInKind.class.equals(clazz) 
        || Address.class.equals(clazz) 
        || Email.class.equals(clazz) 
        || Phone.class.equals(clazz) 
        || PaymentSource.class.equals(clazz)
		        || TangerineForm.class.equals(clazz); // TODO: fix
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validate:");
        }
        validateSubEntities(target, errors);

        Set<String> errorSet = new HashSet<String>();

        if (errors.hasErrors()) {
            List<FieldError> e = errors.getFieldErrors();
            for (FieldError error : e) {
                errorSet.add(error.getField());
            }
        }
        
        AbstractEntity entity = (AbstractEntity) target;
        Map<String, String> fieldLabelMap = entity.getFieldLabelMap();

        // used as a cache to prevent having to use reflection if the value has already been read
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();

        // used to know that a field already has an error, so don't add another

        // first, validate required fields
        if (!entity.isSuppressValidationForRequired()) validateRequiredFields(entity, errors, fieldLabelMap, fieldValueMap, errorSet);

        // next, validate custom validation (regex)  
        validateRegex(entity, errors, fieldLabelMap, fieldValueMap, errorSet);
    }
    
    protected void validateSubEntities(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validateSubEntities:");
        }
        if (target instanceof PaymentSourceAware) {
            PaymentSourceAware obj = (PaymentSourceAware) target;
			if (isNew(obj.getPaymentSource())) {
				paymentSourceValidator.validatePaymentSource(target, errors);
			}
        }
        if (target instanceof AddressAware) {
            AddressAware obj = (AddressAware) target;
	        if (isNew(obj.getAddress())) {
                addressValidator.validateAddress(target, errors);
            }
        }

        if (target instanceof PhoneAware) {
            PhoneAware obj = (PhoneAware) target;
	        if (isNew(obj.getPhone())) {
                phoneValidator.validatePhone(target, errors);
            }
        }

        if (target instanceof EmailAware) {
            EmailAware obj = (EmailAware) target;
	        if (isNew(obj.getEmail())) {
                emailValidator.validateEmail(target, errors);
            }
        }
    }

    protected void validateRequiredFields(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap, Map<String, Object> fieldValueMap, Set<String> errorSet) {
        Map<String, FieldRequired> requiredFieldMap = siteService.readRequiredFields(pageType, tangerineUserHelper.lookupUserRoles());
        if (requiredFieldMap != null) {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            for (String key : requiredFieldMap.keySet()) {
                if (bw.isReadableProperty(key)) {
                    FieldRequired fr = requiredFieldMap.get(key);
                    List<FieldCondition> conditions = fr.getFieldConditions();
                    boolean conditionsMet = this.areFieldConditionsMet(conditions, key, entity, fieldValueMap);
                    if (!conditionsMet) {
                        if (logger.isDebugEnabled()) {
                            logger.debug(key + " validation doesn't apply:  conditions not met");
                        }
                        continue;
                    }
                    if (errorSet.contains(key)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug(key + " validation:  error already exists so don't add another one");
                        }
                        continue;
                    }
                    String propertyString = getPropertyString(key, entity, fieldValueMap);
                    boolean required = fr.isRequired();
                    if ((required && StringUtils.isEmpty(propertyString)) && !errorSet.contains(key)) {
                        errors.rejectValue(key, "fieldRequiredFailure", new String[] { fieldLabelMap.get(key) }, "no message provided for the validation error: fieldRequiredFailure");
                        errorSet.add(key);
                    }
                }
            }
        }
    }
    
    protected boolean areFieldConditionsMet(List<FieldCondition> conditions, String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        boolean conditionsMet = true;
        if (conditions != null) {
            for (FieldCondition fc : conditions) {
                logger.debug("areFieldConditionsMet: key = " + key + ", condition - dependent = " + fc.getDependentFieldDefinition().getFieldName() + ", dependent secondary = " + (fc.getDependentSecondaryFieldDefinition() == null ? "null" : fc.getDependentSecondaryFieldDefinition().getFieldName())
                        + ", dependent value = " + fc.getValue());
                String dependentKey = fc.getDependentFieldDefinition().getFieldName();
                if (fc.getDependentSecondaryFieldDefinition() != null) {
                    dependentKey += "." + fc.getDependentSecondaryFieldDefinition().getFieldName();
                }
                Object dependentProperty = getProperty(dependentKey, entity, fieldValueMap);
                if (fc.getValue() == null) {
                    if (dependentProperty != null) {
                        conditionsMet = false;
                    }
                } else {
                    List fcValues = Arrays.asList(fc.getValue().split("\\|"));
                    if (dependentProperty == null || (!"!null".equals(fc.getValue()) && !fcValues.contains(dependentProperty.toString()))) {
                        conditionsMet = false;
                    }
                }
            }
        }
        return conditionsMet;
    }

    protected void validateRegex(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap, Map<String, Object> fieldValueMap, Set<String> errorSet) {
        Map<String, FieldValidation> validationMap = siteService.readFieldValidations(pageType, tangerineUserHelper.lookupUserRoles());
        if (validationMap != null) {
            for (String key : validationMap.keySet()) {
                FieldValidation fv = validationMap.get(key);
                List<FieldCondition> conditions = fv.getFieldConditions();
                boolean conditionsMet = this.areFieldConditionsMet(conditions, key, entity, fieldValueMap);
                if (!conditionsMet) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation doesn't apply:  conditions not met");
                    }
                    continue;
                }
                if (errorSet.contains(key)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation:  error already exists so don't add another one");
                    }
                    continue;
                }
                String propertyString = getPropertyString(key, entity, fieldValueMap);
                String regex = validationMap.get(key).getRegex();
                boolean valid;

                if (propertyString.length() == 0) {
                	// 'required' is validated in validateRequiredFields()
                	valid = true;
                } else {
                	if (regex.startsWith(EXTENSIONS)) {
                		valid = new ExtendedValidationSupport().validate(propertyString, regex.substring(EXTENSIONS.length()));
                	}
                	else {
                		valid = propertyString.matches(regex);
                	}
                }
                
                if (!valid && !errorSet.contains(key)) {
                    errors.rejectValue(key, "fieldValidationFailure", new String[] { fieldLabelMap.get(key), propertyString }, "no message provided for the validation error: fieldValidationFailure");
                }
            }
        }
    }

    protected Object getProperty(String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        Object property = fieldValueMap.get(key);
        if (property == null) {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            if (beanWrapper.isReadableProperty(key)) {
                property = beanWrapper.getPropertyValue(key);
                if (property instanceof CustomField) {
                    property = ((CustomField) property).getValue();
                }
                fieldValueMap.put(key, property);
            }
        }
        return property;
    }
    
    protected String getPropertyString(String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        Object property = getProperty(key, entity, fieldValueMap);
        return property == null ? StringConstants.EMPTY : property.toString();
    }

	protected boolean isNew(AbstractEntity entity) {
		return entity != null && entity.isNew(); 
	}
}
