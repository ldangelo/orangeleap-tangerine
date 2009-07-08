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

package com.orangeleap.tangerine.json.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.GiftInKindService;
import com.orangeleap.tangerine.util.HttpUtil;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating the grid of gifts.
 * @version 1.0
 */
@Controller
public class GiftInKindListController {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	private final static Map<String, Object> NAME_MAP = new HashMap<String, Object>();

	static {
		NAME_MAP.put("id", "gik.GIFT_ID");
		NAME_MAP.put("constituentId", "gik.CONSTITUENT_ID");
		NAME_MAP.put("fairmarketvalue", "gik.FAIR_MARKET_VALUE");
		NAME_MAP.put("currencycode", "gik.CURRENCY_CODE");
		NAME_MAP.put("donationdate", "gik.DONATION_DATE");
		NAME_MAP.put("motivationcode", "gik.MOTIVATION_CODE");
		NAME_MAP.put("othermotivation", "gik.OTHER_MOTIVATION");
	}

	private Map<String, Object> giftInKindToMap(GiftInKind gik) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", gik.getId());
		map.put("constituentId", gik.getConstituent().getId());
		map.put("fairmarketvalue", gik.getFairMarketValue());
		map.put("currencycode", gik.getCurrencyCode());
		map.put("donationdate", formatter.format(gik.getDonationDate()));
		map.put("motivationcode", gik.getMotivationCode());
		map.put("othermotivation", HttpUtil.jsEscape(gik.getOther_motivationCode()));

		return map;

	}

	@Resource(name = "giftInKindService")
	private GiftInKindService giftInKindService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/giftInKindList.json")
	public ModelMap getGiftList(HttpServletRequest request, SortInfo sortInfo) {

		List<Map> rows = new ArrayList<Map>();

		// if we're not getting back a valid column name, possible SQL injection,
		// so send back an empty list.
		if (!sortInfo.validateSortField(NAME_MAP.keySet())) {
			logger.warn("getGiftInKindList called with invalid sort column: [" + sortInfo.getSort() + "]");
			return new ModelMap("rows", rows);
		}

		// set the sort to the valid column name, based on the map
		sortInfo.setSort((String) NAME_MAP.get(sortInfo.getSort()));

		String constituentId = request.getParameter("constituentId");
		PaginatedResult result = giftInKindService.readPaginatedGiftsInKindByConstituentId(Long.valueOf(constituentId), sortInfo);

		List<GiftInKind> list = result.getRows();

		for (GiftInKind g : list) {
			rows.add(giftInKindToMap(g));
		}

		ModelMap map = new ModelMap("rows", rows);
		map.put("totalRows", result.getRowCount());
		return map;
	}

}
