package com.orangeleap.tangerine.controller.giftInKind;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.GiftInKindService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftInKindFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftInKindService")
    protected GiftInKindService giftInKindService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return giftInKindService.readGiftInKindByIdCreateIfNull(request.getParameter(StringConstants.GIFT_IN_KIND_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        GiftInKind giftInKind = (GiftInKind) command;
        
        boolean saved = true;
        GiftInKind current = null;
        try {
        	current = giftInKindService.maintainGiftInKind(giftInKind);
        } 
        catch (BindException e) {
            saved = false;
            current = giftInKind;
            errors.addAllErrors(e);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.GIFT_IN_KIND_ID + "=" + current.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        else {
			current.removeEmptyMutableDetails();
			mav = showForm(request, errors, getFormView());
        }
        return mav;
    }
}
