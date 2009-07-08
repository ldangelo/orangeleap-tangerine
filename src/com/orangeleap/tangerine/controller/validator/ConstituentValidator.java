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

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.util.StringConstants;

public class ConstituentValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="addressValidator")
    private AddressValidator addressValidator;

    @Resource(name="phoneValidator")
    private PhoneValidator phoneValidator;

    @Resource(name="emailValidator")
    private EmailValidator emailValidator;

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Constituent.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Constituent constituent = (Constituent)target;
        validateConstituentAddress(constituent, errors);
        validateConstituentPhone(constituent, errors);
        validateConstituentEmail(constituent, errors);
        validateOrganization(constituent, errors);
    }
    
    private void validateConstituentAddress(Constituent constituent, Errors errors) {
        if (constituent.getPrimaryAddress() != null && constituent.getPrimaryAddress().isFieldEntered()) {
            addressValidator.validateAddress(constituent, errors);
        }
    }
    
    private void validateConstituentPhone(Constituent constituent, Errors errors) {
        if (constituent.getPrimaryPhone() != null && constituent.getPrimaryPhone().isFieldEntered()) {
            phoneValidator.validatePhone(constituent, errors);
        }
    }
    
    private void validateConstituentEmail(Constituent constituent, Errors errors) {
        if (constituent.getPrimaryEmail() != null && constituent.getPrimaryEmail().isFieldEntered()) {
            emailValidator.validateEmail(constituent, errors);
        }
    }
    
    private void validateOrganization(Constituent constituent, Errors errors) {
        if (constituent.isOrganization()) {
            Object minMatch = constituent.getCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH);
            Object maxMatch = constituent.getCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH);
            if (minMatch != null && NumberUtils.isNumber(minMatch.toString()) == false) {
                errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMinGiftMatch", "The Minimum Gift Match must be a valid amount");
            }
            if (maxMatch != null && NumberUtils.isNumber(maxMatch.toString()) == false) {
                errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMaxGiftMatch", "The Maximum Gift Match must be a valid amount");
            }
            if (minMatch != null && NumberUtils.isNumber(minMatch.toString()) && maxMatch != null && NumberUtils.isNumber(maxMatch.toString())) {
                Double min = new Double(minMatch.toString());
                Double max = new Double(maxMatch.toString());
                
                if (max.doubleValue() < min.doubleValue()) {
                    errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END, "invalidMinMaxGiftMatch", "The Maximum Gift Match amount must be greater than or equal to the Minimum Gift Match amount");
                }
            }
        }
    }
}
