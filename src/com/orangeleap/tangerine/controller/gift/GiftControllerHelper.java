/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.PaymentType;
import com.orangeleap.tangerine.util.StringConstants;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.ServletRequestDataBinder;

public class GiftControllerHelper {

    @Resource(name="giftService")
    protected GiftService giftService;

	@Resource(name = "pledgeService")
	protected PledgeService pledgeService;

	@Resource(name = "recurringGiftService")
	protected RecurringGiftService recurringGiftService;
	
    protected String giftUrl;
    protected String giftPaidUrl;
    protected String giftPostedUrl;

    protected String adjustedGiftUrl;
    protected String adjustedGiftPaidUrl;
    protected String adjustedGiftPostedUrl;

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public String getGiftPaidUrl() {
        return giftPaidUrl;
    }

    public void setGiftPaidUrl(String giftPaidUrl) {
        this.giftPaidUrl = giftPaidUrl;
    }

    public String getGiftPostedUrl() {
        return giftPostedUrl;
    }

    public void setGiftPostedUrl(String giftPostedUrl) {
        this.giftPostedUrl = giftPostedUrl;
    }

    public String getAdjustedGiftUrl() {
        return adjustedGiftUrl;
    }

    public void setAdjustedGiftUrl(String adjustedGiftUrl) {
        this.adjustedGiftUrl = adjustedGiftUrl;
    }

    public String getAdjustedGiftPaidUrl() {
        return adjustedGiftPaidUrl;
    }

    public void setAdjustedGiftPaidUrl(String adjustedGiftPaidUrl) {
        this.adjustedGiftPaidUrl = adjustedGiftPaidUrl;
    }

    public String getAdjustedGiftPostedUrl() {
        return adjustedGiftPostedUrl;
    }

    public void setAdjustedGiftPostedUrl(String adjustedGiftPostedUrl) {
        this.adjustedGiftPostedUrl = adjustedGiftPostedUrl;
    }

    public String appendGiftParameters(String viewName, Gift gift, Long constituentId) {
		StringBuilder sb = new StringBuilder(viewName).append("?");
		if (gift != null && gift.getId() != null && gift.getId() > 0) {
			sb.append(StringConstants.GIFT_ID).append("=").append(gift.getId()).append("&");
		}
		return sb.append(StringConstants.CONSTITUENT_ID).append("=").append(constituentId).toString();
	}

    public String appendAdjustedGiftParameters(String viewName, AdjustedGift adjustedGift, Long constituentId) {
		StringBuilder sb = new StringBuilder(viewName).append("?");
		if (adjustedGift != null && adjustedGift.getId() != null && adjustedGift.getId() > 0) {
			sb.append(StringConstants.ADJUSTED_GIFT_ID).append("=").append(adjustedGift.getId()).append("&");
		}
		return sb.append(StringConstants.CONSTITUENT_ID).append("=").append(constituentId).toString();
	}

    public boolean isEnteredGift(Gift gift) {
    	return gift != null && !gift.isNew();
    }

    public boolean showGiftPostedView(Gift gift) {
        return isEnteredGift(gift) && gift.isPosted();
    }

    public boolean showGiftPaidView(Gift gift) {
    	return isEnteredGift(gift) && Gift.STATUS_PAID.equals(gift.getGiftStatus()) && 
			    (PaymentType.OTHER.getPaymentName().equals(gift.getPaymentType()) ||
			    PaymentType.CASH.getPaymentName().equals(gift.getPaymentType()) ||
			    PaymentType.CHECK.getPaymentName().equals(gift.getPaymentType()) ||
    			((PaymentType.ACH.getPaymentName().equals(gift.getPaymentType()) ||
                        PaymentType.CREDIT_CARD.getPaymentName().equals(gift.getPaymentType())) &&
                        Gift.PAY_STATUS_APPROVED.equals(gift.getPaymentStatus())));
    }

    public boolean isEnteredAdjustedGift(AdjustedGift adjustedGift) {
    	return adjustedGift != null && ! adjustedGift.isNew();
    }

    public boolean showAdjustedGiftPaidView(AdjustedGift adjustedGift) {
    	return isEnteredAdjustedGift(adjustedGift) && Gift.STATUS_PAID.equals(adjustedGift.getAdjustedStatus());
    }

    public boolean showAdjustedGiftPostedView(AdjustedGift adjustedGift) {
        return isEnteredAdjustedGift(adjustedGift) && adjustedGift.isPosted();
    }

    public void validateGiftStatusChange(Gift gift) {
        if (gift != null && !gift.isNew()) {
            Gift oldGift = giftService.readGiftById(gift.getId());
            if (oldGift != null) {
                if (Gift.STATUS_PAID.equals(oldGift.getGiftStatus()) && !Gift.STATUS_PAID.equals(gift.getGiftStatus())) {
                    // Can't change from Paid to non-Paid in view
                    gift.setGiftStatus(oldGift.getGiftStatus());
                }
            }
        }
    }

	@SuppressWarnings("unchecked")
	public void addSelectedPledgeRecurringGiftRefData(final HttpServletRequest request, final Map refMap) {
		String selectedPledgeId = request.getParameter("selectedPledgeId");
		String selectedRecurringGiftId = request.getParameter("selectedRecurringGiftId");
		if (NumberUtils.isDigits(selectedPledgeId)) {
		    Pledge pledge = pledgeService.readPledgeById(Long.parseLong(selectedPledgeId));
		    refMap.put("associatedPledge", pledge);
		}
		else if (NumberUtils.isDigits(selectedRecurringGiftId)) {
		    RecurringGift recurringGift = recurringGiftService.readRecurringGiftById(Long.parseLong(selectedRecurringGiftId));
		    refMap.put("associatedRecurringGift", recurringGift);
		}
	}

    public void initBinderAssociations(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(List.class, "associatedPledgeIds", new AssociationEditor());
        binder.registerCustomEditor(List.class, "associatedRecurringGiftIds", new AssociationEditor());
    }

	public void checkAssociations(Gift gift) {
	    giftService.checkAssociatedPledgeIds(gift);
	    giftService.checkAssociatedRecurringGiftIds(gift);
	}
}