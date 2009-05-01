package com.orangeleap.tangerine.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftAdjustmentController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return adjustedGiftService.createdDefaultAdjustedGift(super.getIdAsLong(request, StringConstants.GIFT_ID));
    }
    
    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        if (isFormSubmission(request) && errors.hasErrors()) {
            AdjustedGift adjustedGift = (AdjustedGift) command;
            adjustedGift.removeEmptyMutableDistributionLines();
        }
        super.onBindAndValidate(request, command, errors);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        AdjustedGift adjustedGift = (AdjustedGift) command;
        adjustedGift.filterValidDistributionLines();
        adjustedGiftService.maintainAdjustedGift(adjustedGift);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + adjustedGift.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
