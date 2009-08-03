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
import com.orangeleap.tangerine.util.StringConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractGiftController extends AbstractMutableGridFormController {

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