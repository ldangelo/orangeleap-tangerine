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

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.type.PaymentType;
import com.orangeleap.tangerine.util.StringConstants;

import javax.annotation.Resource;

public class GiftControllerHelper {

    @Resource(name="giftService")
    protected GiftService giftService;

    protected String giftUrl;
    protected String giftPaidUrl;
    protected String giftPostedUrl;

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

    public String appendGiftParameters(String viewName, Gift gift, Long constituentId) {
		StringBuilder sb = new StringBuilder(viewName).append("?");
		if (gift != null && gift.getId() != null && gift.getId() > 0) {
			sb.append(StringConstants.GIFT_ID).append("=").append(gift.getId()).append("&");
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
}