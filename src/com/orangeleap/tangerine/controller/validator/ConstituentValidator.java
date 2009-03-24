package com.orangeleap.tangerine.controller.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.util.StringConstants;

public class ConstituentValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="addressValidator")
    private AddressValidator addressValidator;

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person constituent = (Person)target;
        validateConstituentAddress(constituent, errors);
        validateOrganization(constituent, errors);
    }
    
    private void validateConstituentAddress(Person constituent, Errors errors) {
        if (constituent.getPrimaryAddress() != null && constituent.getPrimaryAddress().isFieldEntered()) {
            addressValidator.validateAddress(constituent, errors);
        }
    }
    
    private void validateOrganization(Person constituent, Errors errors) {
        if (constituent.isOrganization()) {
            Object minMatch = constituent.getCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH);
            Object maxMatch = constituent.getCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH);
            if (minMatch != null && NumberUtils.isNumber(minMatch.toString()) == false) {
                errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMinGiftMatch", "The Minimum Gift Match must be a valid amount");
            }
            if (maxMatch != null && NumberUtils.isNumber(maxMatch.toString()) == false) {
                errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMaxGiftMatch", "The Maximum Gift Match must be a valid amount");
            }
            if (minMatch != null && NumberUtils.isNumber(minMatch.toString()) && maxMatch != null && NumberUtils.isNumber(maxMatch.toString())) {
                Double min = new Double(minMatch.toString());
                Double max = new Double(maxMatch.toString());
                
                if (max.doubleValue() < min.doubleValue()) {
                    errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMinMaxGiftMatch", "The Maximum Gift Match amount must be greater than or equal to the Minimum Gift Match amount");
                }
            }
        }
    }
}
