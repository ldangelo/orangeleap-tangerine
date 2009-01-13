package com.mpower.controller.validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.mpower.domain.Address;
import com.mpower.domain.AddressAware;
import com.mpower.domain.Commitment;
import com.mpower.domain.CustomField;
import com.mpower.domain.Email;
import com.mpower.domain.EmailAware;
import com.mpower.domain.Gift;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.PaymentSourceAware;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.domain.PhoneAware;
import com.mpower.domain.Viewable;
import com.mpower.domain.customization.FieldCondition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.CommitmentType;
import com.mpower.type.PageType;

public class EntityValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private SiteService siteService;

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    private PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Person.class.equals(clazz) || Gift.class.equals(clazz) || Commitment.class.equals(clazz) || Address.class.equals(clazz) || Email.class.equals(clazz) || Phone.class.equals(clazz) || PaymentSource.class.equals(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in EntityValidator");
        // PLEDGE doesn't have payment source, address, or phone (considering refactoring Commitment to RecurringGift and Pledge)
        if (!(target instanceof Commitment) || !CommitmentType.PLEDGE.equals(((Commitment) target).getCommitmentType())) {
            if (target instanceof PaymentSourceAware) {
                PaymentSourceAware obj = (PaymentSourceAware) target;
                PaymentSource selectedPaymentSource = obj.getSelectedPaymentSource();
                if (selectedPaymentSource != null && selectedPaymentSource.getId() != null) {
                    PaymentSourceValidator.validatePaymentSource(target, errors);
                }
            }

            if (target instanceof AddressAware) {
                AddressAware obj = (AddressAware) target;
                Address selectedAddress = obj.getSelectedAddress();
                if (selectedAddress != null && selectedAddress.getId() != null) {
                    AddressValidator.validateAddress(target, errors);
                }
            }

            if (target instanceof PhoneAware) {
                PhoneAware obj = (PhoneAware) target;
                Phone selectedPhone = obj.getSelectedPhone();
                if (selectedPhone != null && selectedPhone.getId() != null) {
                    PhoneValidator.validatePhone(target, errors);
                }
            }

            if (target instanceof EmailAware) {
                EmailAware obj = (EmailAware) target;
                Email selectedEmail = obj.getSelectedEmail();
                if (selectedEmail != null && selectedEmail.getId() != null) {
                    EmailValidator.validateEMail(target, errors);
                }
            }

        }

        Set<String> errorSet = new HashSet<String>();

        if (errors.hasErrors()) {
            List<FieldError> e = errors.getFieldErrors();
            for (FieldError error : e) {
                errorSet.add(error.getField());
            }
        }
        
        Viewable viewable = (Viewable) target;
        Map<String, String> fieldLabelMap = viewable.getFieldLabelMap();

        // used as a cache to prevent having to use reflection if the value has already been read
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();

        // used to know that a field already has an error, so don't add another

        // first, validate required fields
        validateRequiredFields(viewable, errors, fieldLabelMap, fieldValueMap, errorSet);

        // next, validate custom validation (regex)  
        validateRegex(viewable, errors, fieldLabelMap, fieldValueMap, errorSet);
    }

    private void validateRequiredFields(Viewable viewable, Errors errors, Map<String, String> fieldLabelMap, Map<String, Object> fieldValueMap, Set<String> errorSet) {
        Map<String, FieldRequired> requiredFieldMap = siteService.readRequiredFields(SessionServiceImpl.lookupUserSiteName(), pageType, SessionServiceImpl.lookupUserRoles());
        if (requiredFieldMap != null) {
            for (String key : requiredFieldMap.keySet()) {
                FieldRequired fr = requiredFieldMap.get(key);
                List<FieldCondition> conditions = fr.getFieldConditions();
                boolean conditionsMet = true;
                if (conditions != null) {
                    for (FieldCondition fc : conditions) {
                        logger.debug("key: " + key + ", condition: dependent=" + fc.getDependentFieldDefinition().getFieldName() + ", dependent secondary=" + (fc.getDependentSecondaryFieldDefinition() == null ? "null" : fc.getDependentSecondaryFieldDefinition().getFieldName())
                                + ", dependent value=" + fc.getValue());
                        String dependentKey = fc.getDependentFieldDefinition().getFieldName();
                        if (fc.getDependentSecondaryFieldDefinition() != null) {
                            dependentKey += "." + fc.getDependentSecondaryFieldDefinition().getFieldName();
                        }
                        Object dependentProperty = getProperty(dependentKey, viewable, fieldValueMap);
                        if (fc.getValue() == null) {
                            if (dependentProperty != null) {
                                conditionsMet = false;
                            }
                        } else {
                            if (dependentProperty == null || (!"!null".equals(fc.getValue()) && !dependentProperty.toString().equals(fc.getValue()))) {
                                conditionsMet = false;
                            }
                        }
                    }
                }
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
                Object property = getProperty(key, viewable, fieldValueMap);
                String propertyString = property == null ? "" : property.toString();
                boolean required = fr.isRequired();
                if ((required && StringUtils.isEmpty(propertyString)) && !errorSet.contains(key)) {
                    errors.rejectValue(key, "fieldRequiredFailure", new String[] { fieldLabelMap.get(key) }, "no message provided for the validation error: fieldRequiredFailure");
                    errorSet.add(key);
                }
            }
        }
    }

    private void validateRegex(Viewable viewable, Errors errors, Map<String, String> fieldLabelMap, Map<String, Object> fieldValueMap, Set<String> errorSet) {
        Map<String, FieldValidation> validationMap = siteService.readFieldValidations(SessionServiceImpl.lookupUserSiteName(), pageType, SessionServiceImpl.lookupUserRoles());
        if (validationMap != null) {
            for (String key : validationMap.keySet()) {
                FieldValidation fv = validationMap.get(key);
                List<FieldCondition> conditions = fv.getFieldConditions();
                boolean conditionsMet = true;
                if (conditions != null) {
                    for (FieldCondition fc : conditions) {
                        logger.debug("key: " + key + ", condition: dependent=" + fc.getDependentFieldDefinition().getFieldName() + ", dependent secondary=" + (fc.getDependentSecondaryFieldDefinition() == null ? "null" : fc.getDependentSecondaryFieldDefinition().getFieldName())
                                + ", dependent value=" + fc.getValue());
                        String dependentKey = fc.getDependentFieldDefinition().getFieldName();
                        if (fc.getDependentSecondaryFieldDefinition() != null) {
                            dependentKey += "." + fc.getDependentSecondaryFieldDefinition().getFieldName();
                        }
                        Object dependentProperty = getProperty(dependentKey, viewable, fieldValueMap);
                        if (fc.getValue() == null) {
                            if (dependentProperty != null) {
                                conditionsMet = false;
                            }
                        } else {
                            if (dependentProperty == null || (!"!null".equals(fc.getValue()) && !dependentProperty.toString().equals(fc.getValue()))) {
                                conditionsMet = false;
                            }
                        }
                    }
                }
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
                Object property = getProperty(key, viewable, fieldValueMap);
                String propertyString = property == null ? "" : property.toString();
                String regex = validationMap.get(key).getRegex();
                boolean valid;
                String validator = "extensions:";
                if (regex.startsWith(validator)) {
                    if (propertyString.length() == 0) {
                        // 'required' is validated in validateRequiredFields()
                        valid = true;
                    } else {
                        valid = new ExtendedValidationSupport().validate(propertyString, regex.substring(validator.length()));
                    }
                } else {
                    valid = propertyString.matches(regex);
                }
                if (!valid && !errorSet.contains(key)) {
                    // String defaultMessage = messageService.lookupMessage(SessionServiceImpl., MessageResourceType.FIELD_VALIDATION, "fieldValidationFailure", null);
                    errors.rejectValue(key, "fieldValidationFailure", new String[] { fieldLabelMap.get(key), propertyString }, "no message provided for the validation error: fieldValidationFailure");
                    // errors.reject("fieldValidationFailure", new String[] { key, propertyString }, "no message provided for the validation error: fieldValidationFailure");
                }
            }
        }
    }

    private Object getProperty(String key, Viewable viewable, Map<String, Object> fieldValueMap) {
        Object property = fieldValueMap.get(key);
        if (property == null) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(viewable);
            property = beanWrapper.getPropertyValue(key);
            // if ((property instanceof String) && beanWrapper.isReadableProperty((String) property)) {
            // property = beanWrapper.getPropertyValue((String) property);
            // }
            if (property instanceof CustomField) {
                property = ((CustomField) property).getValue();
            }
            fieldValueMap.put(key, property);
        }
        return property;
    }
}
