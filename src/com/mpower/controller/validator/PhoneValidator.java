package com.mpower.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.Phone;
import com.mpower.domain.PhoneAware;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class PhoneValidator implements Validator {

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
        return Phone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in PhoneValidator");
        validatePhone(target, errors);
    }

    public static void validatePhone(Object target, Errors errors) {
        Phone phone = null;
        String inPath = errors.getNestedPath();
        if (target instanceof Phone) {
            phone = (Phone) target;
        } else if (target instanceof PhoneAware) {
            phone = ((PhoneAware) target).getPhone();
            errors.setNestedPath("phone");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "invalidNumber", "Number is required");
        if (!errors.hasErrors()) {
            if ("seasonal".equals(phone.getActivationStatus())) {
                if (phone.getSeasonalStartDate() == null) {
                    errors.rejectValue("seasonalStartDate", "invalidSeasonalStartDate", "Seasonal Start Date is required");
                }
                if (phone.getSeasonalEndDate() == null) {
                    errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDate", "Seasonal End Date is required");
                }
                if (phone.getSeasonalStartDate() != null && phone.getSeasonalEndDate() != null) {
                    if (!phone.getSeasonalEndDate().after(phone.getSeasonalStartDate())) {
                        errors.rejectValue("seasonalEndDate", "invalidSeasonalEndDateBeforeStartDate", "Seasonal End Date must be after Seasonal Start Date");
                    }
                }
            } else if ("temporary".equals(phone.getActivationStatus())) {
                if (phone.getTemporaryStartDate() == null) {
                    errors.rejectValue("temporaryStartDate", "invalidTemporaryStartDate", "Temporary Start Date is required");
                }
                if (phone.getTemporaryEndDate() == null) {
                    errors.rejectValue("temporaryEndDate", "invalidTemporaryEndDate", "Temporary End Date is required");
                }
            }
        }
        errors.setNestedPath(inPath);
    }
}
