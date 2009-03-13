package com.orangeleap.tangerine.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.communication.Address;

public class AddressValidator extends AbstractCommunicationValidator<Address> {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in AddressValidator");
        validateAddress(target, errors);
    }

    public void validateAddress(Object target, Errors errors) {
        Address address = null;
        String inPath = errors.getNestedPath();
        if (target instanceof Address) {
            address = (Address) target;
        } 
        else if (target instanceof AddressAware) {
            address = ((AddressAware) target).getAddress();
            errors.setNestedPath("address");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressLine1", "invalidAddressLine1", "Address Line 1 is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "invalidCity", "City is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateProvince", "invalidStateProvince", "State/Province is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "invalidPostalCode", "Postal/Zip Code is required");
        
        validateDates(address, errors);
        errors.setNestedPath(inPath);
    }
}
