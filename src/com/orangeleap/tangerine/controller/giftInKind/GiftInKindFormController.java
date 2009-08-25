package com.orangeleap.tangerine.controller.giftInKind;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.gift.AbstractMutableGridFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.GiftInKindService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GiftInKindFormController extends AbstractMutableGridFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "giftInKindService")
    protected GiftInKindService giftInKindService;
    
    @Resource(name = "picklistItemService")
    protected PicklistItemService picklistItemService;

	@Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return giftInKindService.readGiftInKindByIdCreateIfNull(request.getParameter(StringConstants.GIFT_IN_KIND_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
        GiftInKind giftInKind = (GiftInKind) form.getDomainObject();

        boolean saved = true;
        try {
            giftInKind = giftInKindService.maintainGiftInKind(giftInKind);
        }
        catch (BindException domainErrors) {
            saved = false;
            bindDomainErrorsToForm(request, formErrors, domainErrors, form, giftInKind);
        }

        ModelAndView mav;
        if (saved) {
            mav = new ModelAndView(super.appendSaved(getSuccessView() + "?" + StringConstants.GIFT_IN_KIND_ID + "=" + giftInKind.getId() + "&" +
		            StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
        }
        else {
            mav = showForm(request, formErrors, getFormView());
        }
        return mav;
    }
}
