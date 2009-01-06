package com.mpower.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mpower.domain.Commitment;
import com.mpower.service.SiteService;
import com.mpower.type.CommitmentType;
import com.mpower.type.PageType;

public class CommitmentValidator implements Validator {

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
        return Commitment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("in CommitmentValidator");
        Commitment commitment = (Commitment) target;
        
        boolean isNonRecurringPledge = CommitmentType.PLEDGE.equals(commitment.getCommitmentType()) && !commitment.isRecurring();
        if (!isNonRecurringPledge) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "invalidStartDate", "Start Date is required");
        }
        
        if (CommitmentType.RECURRING_GIFT.equals(commitment.getCommitmentType())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amountPerGift", "invalidAmountPerGift", "Amount per gift is required");
        }
        
        if (!errors.hasErrors()) {
            if (commitment.getEndDate() != null) {
                if (commitment.getEndDate().before(commitment.getStartDate())) {
                    errors.rejectValue("endDate", "invalidEndDate", "Commitment start date must be before end date");
                }
            }
        }
    }
}
