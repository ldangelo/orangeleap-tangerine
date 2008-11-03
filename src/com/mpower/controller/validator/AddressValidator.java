package com.mpower.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.Address;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class AddressValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unused")
    private SiteService siteService;

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @SuppressWarnings("unused")
    private PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return Address.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in AddressValidator");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressLine1", "invalidAddressLine1", "Address Line 1 is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "invalidCity", "City is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateProvince", "invalidStateProvince", "State is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "invalidPostalCode", "Zipcode is required");
        if (!errors.hasErrors()) {
            Address address = (Address) target;
            if ("seasonal".equals(address.getActivationStatus())) {
                if (address.getSeasonalStartDate() == null) {
                    errors.rejectValue("seasonalStartDate", "invalidSeasonalStartDate", "Seasonal Start Date is required");
                }
                if (address.getSeasonalEndDate() == null) {
                    errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDate", "Seasonal End Date is required");
                }
                if (address.getSeasonalStartDate() != null && address.getSeasonalEndDate() != null) {
                    if (!address.getSeasonalEndDate().after(address.getSeasonalStartDate())) {
                        errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDateBeforeStartDate", "Seasonal End Date must be after Seasonal Start Date");
                    }
                }
            } else if ("temporary".equals(address.getActivationStatus())) {
                if (address.getTemporaryStartDate() == null) {
                    errors.rejectValue("temporaryStartDate", "invalidTemporaryStartDate", "Temporary Start Date is required");
                }
                if (address.getTemporaryEndDate() == null) {
                    errors.rejectValue("temporaryEndDate", "invalidTemporaryEndDate", "Temporary End Date is required");
                }
            }
        }
    }
}
