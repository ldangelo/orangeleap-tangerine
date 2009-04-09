package com.orangeleap.tangerine.controller.gift;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    protected GiftService giftService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
        Gift gift = giftService.readGiftByIdCreateIfNull(super.getConstituent(request), request.getParameter(StringConstants.GIFT_ID), 
                request.getParameter(StringConstants.RECURRING_GIFT_ID), request.getParameter(StringConstants.PLEDGE_ID));
        giftService.initGiftAmountDistributionLinesFromPledge(gift, request.getParameter("selectedPledgeId"));
        return gift;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(List.class, "associatedPledgeIds", new AssociationEditor());
    }
    
    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        if (isFormSubmission(request) && errors.hasErrors()) {
            Gift gift = (Gift) command;
            gift.removeEmptyMutableDistributionLines();
        }
        super.onBindAndValidate(request, command, errors);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Gift gift = (Gift) command;
        giftService.checkAssociatedPledgeIds(gift);
        Gift current = giftService.maintainGift(gift);
        return new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request)));
    }
}
