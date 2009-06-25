package com.orangeleap.tangerine.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.util.StringConstants;

public abstract class AbstractGiftController extends TangerineConstituentAttributesFormController {

    @Resource(name="giftService")
    protected GiftService giftService;
    
    protected String giftUrl;
    protected String giftViewUrl;

    public void setGiftUrl(String giftUrl) {
		this.giftUrl = giftUrl;
	}

	public void setGiftViewUrl(String giftViewUrl) {
		this.giftViewUrl = giftViewUrl;
	}
	
	protected String appendGiftParameters(HttpServletRequest request, String viewName, Gift gift) {
		StringBuilder sb = new StringBuilder(viewName).append("?");
		if (gift != null && gift.getId() != null && gift.getId() > 0) {
			sb.append(StringConstants.GIFT_ID).append("=").append(gift.getId()).append("&");
		}
		return sb.append(StringConstants.CONSTITUENT_ID).append("=").append(super.getConstituentId(request)).toString();
	}
}
