package com.orangeleap.tangerine.controller.validator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import com.orangeleap.tangerine.type.ActivationType;

public abstract class AbstractCommunicationValidator<T extends AbstractCommunicationEntity> implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected void validateDates(T entity, Errors errors) {
        if (ActivationType.seasonal.equals(entity.getActivationStatus())) {
            checkNullDate(entity.getSeasonalStartDate(), "seasonalStartDate", "invalidSeasonalStartDate", "Seasonal Start Date is required", errors);
            checkNullDate(entity.getSeasonalEndDate(), "seasonalEndDate", "invalidSeasonalEndDate", "Seasonal End Date is required", errors);
            checkDateRange(entity.getSeasonalStartDate(), entity.getSeasonalEndDate(), "seasonalEndDate", "invalidSeasonalEndDateBeforeStartDate", "The Seasonal End Date must be after the Seasonal Start Date", errors);
        } 
        else if (ActivationType.temporary.equals(entity.getActivationStatus())) {
            checkNullDate(entity.getTemporaryStartDate(), "temporaryStartDate", "invalidTemporaryStartDate", "Temporary Start Date is required", errors);
            checkNullDate(entity.getTemporaryEndDate(), "temporaryEndDate", "invalidTemporaryEndDate", "Temporary End Date is required", errors);
            checkDateRange(entity.getTemporaryStartDate(), entity.getTemporaryEndDate(), "temporaryEndDate", "invalidTemporaryEndDateBeforeStartDate", "The Temporary End Date must be after the Temporary Start Date", errors);
        }
    }
    
    private void checkNullDate(Date date, String field, String msgKey, String msgDefault, Errors errors) {
        if (date == null) {
            errors.rejectValue(field, msgKey, msgDefault);
        }
    }
    
    private void checkDateRange(Date startDate, Date endDate, String field, String msgKey, String msgDefault, Errors errors) {
        if (startDate != null && endDate != null) {
            if (!endDate.after(startDate)) {
                errors.rejectValue(field, msgKey, msgDefault);
            }
        }
    }
}
