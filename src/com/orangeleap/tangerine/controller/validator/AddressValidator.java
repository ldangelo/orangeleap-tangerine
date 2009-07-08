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

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.NewAddressAware;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.StringConstants;

public class AddressValidator extends AbstractCommunicationValidator<Address> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("in AddressValidator");
        validateAddress(target, errors);
    }

    public void validateAddress(Object target, Errors errors) {
        Address address = null;
        String inPath = errors.getNestedPath();
        if (target instanceof Address) {
            address = (Address) target;
        } 
        else if (target instanceof NewAddressAware) {
            address = ((NewAddressAware) target).getAddress();
            errors.setNestedPath("address");
        }
        else if (target instanceof AbstractCommunicatorEntity) {
            address = ((AbstractCommunicatorEntity) target).getPrimaryAddress();
            errors.setNestedPath("primaryAddress");
        }

        if (StringUtils.hasText(address.getCustomFieldValue(StringConstants.ADDRESS_TYPE)) == false) {
            errors.rejectValue(StringConstants.CUSTOM_FIELD_MAP_START + StringConstants.ADDRESS_TYPE + StringConstants.CUSTOM_FIELD_MAP_END, "errorAddressTypeRequired");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressLine1", "invalidAddressLine1", "Address Line 1 is required"); // TODO: why are the error codes not in the messageResource table?
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "invalidCity", "City is required");
        if (stateRequired(address)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateProvince", "invalidStateProvince", "State/Province is required");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "invalidPostalCode", "Postal/Zip Code is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "invalidCountry", "Country is required");
        
        validateDates(address, errors);
        errors.setNestedPath(inPath);
    }
    
    private static boolean stateRequired(Address address) {
        return "US".equals(address.getCountry()) || "CA".equals(address.getCountry());
    }

}
