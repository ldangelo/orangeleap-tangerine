package com.mpower.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineConstituentAttributesFormController;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.GiftService;
import com.mpower.util.StringConstants;

public class GiftFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    protected GiftService giftService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
        return giftService.readGiftByIdCreateIfNull(request.getParameter(StringConstants.GIFT_ID), request.getParameter(StringConstants.COMMITMENT_ID), super.getConstituent(request));
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBind(request, command, errors);
        Gift gift = (Gift) command;
        gift.removeEmptyDistributionLines();
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Gift gift = (Gift) command;
        Gift current = giftService.maintainGift(gift);
        return new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request)));
    }
}
