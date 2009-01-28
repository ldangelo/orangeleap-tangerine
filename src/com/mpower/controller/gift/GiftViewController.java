package com.mpower.controller.gift;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Gift;
import com.mpower.domain.Viewable;
import com.mpower.service.GiftService;
import com.mpower.util.StringConstants;

public class GiftViewController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private GiftService giftService;

    @Override
    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }
    
    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Gift gift = (Gift)super.formBackingObject(request);
        
        if (!super.isFormSubmission(request)) {
            // Copy the existing email object to the selected one if this is a GET
            if (gift.getEmail().isValid()) {
                gift.setSelectedEmail(gift.getEmail());
            }
        }
        return gift;
    }
}
