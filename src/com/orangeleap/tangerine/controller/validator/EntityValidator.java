package com.orangeleap.tangerine.controller.validator;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.NewAddressAware;
import com.orangeleap.tangerine.domain.NewEmailAware;
import com.orangeleap.tangerine.domain.NewPhoneAware;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.PhoneAware;
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
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class EntityValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
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
        Person.class.equals(clazz) 
        || AbstractPaymentInfoEntity.class.isAssignableFrom(clazz)
        || CommunicationHistory.class.equals(clazz) 
        || GiftInKind.class.equals(clazz) 
        || Address.class.equals(clazz) 
        || Email.class.equals(clazz) 
        || Phone.class.equals(clazz) 
        || PaymentSource.class.equals(clazz);
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
            if (FormBeanType.NEW.equals(obj.getPaymentSourceType())) {
                paymentSourceValidator.validatePaymentSource(target, errors);
            }
            else if (FormBeanType.EXISTING.equals(obj.getPaymentSourceType())) {
                // TODO: validate ID > 0
            }
        }
        if (target instanceof AddressAware) {
            AddressAware obj = (AddressAware) target;
            if (FormBeanType.NEW.equals(obj.getAddressType()) && obj instanceof NewAddressAware) {
                addressValidator.validateAddress(target, errors);
            }
            else if (FormBeanType.EXISTING.equals(obj.getAddressType())) {
                // TODO: validate ID > 0
            }
        }

        if (target instanceof PhoneAware) {
            PhoneAware obj = (PhoneAware) target;
            if (FormBeanType.NEW.equals(obj.getPhoneType()) && obj instanceof NewPhoneAware) {
                phoneValidator.validatePhone(target, errors);
            }
            else if (FormBeanType.EXISTING.equals(obj.getPhoneType())) {
                // TODO: validate ID > 0
            }
        }

        if (target instanceof EmailAware) {
            EmailAware obj = (EmailAware) target;
            if (FormBeanType.NEW.equals(obj.getEmailType()) && obj instanceof NewEmailAware) {
                emailValidator.validateEmail(target, errors);
            }
            else if (FormBeanType.EXISTING.equals(obj.getEmailType())) {
                // TODO: validate ID > 0
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
}
