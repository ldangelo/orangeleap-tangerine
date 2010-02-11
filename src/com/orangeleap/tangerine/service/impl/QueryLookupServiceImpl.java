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

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.QueryLookupService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("queryLookupService")
@Transactional(propagation = Propagation.REQUIRED)
public class QueryLookupServiceImpl extends AbstractTangerineService implements QueryLookupService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "queryLookupDAO")
    private QueryLookupDao queryLookupDao;
    
	@Resource(name = "constituentService")
	private ConstituentService constituentService;

    @Resource(name = "giftService")
    private GiftService giftService;
    
    @Resource(name = "recurringGiftService")
    private RecurringGiftService recurringGiftService;
    
    @Resource(name = "pledgeService")
    private PledgeService pledgeService;

	private final List<String> SUPPORTED_CONSTITUENT_FIELD_NAMES;

	public QueryLookupServiceImpl() {
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES = new ArrayList<String>();
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES.add(StringConstants.ACCOUNT_NUMBER);
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES.add(StringConstants.FIRST_NAME);
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES.add(StringConstants.LAST_NAME);
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES.add(StringConstants.ORGANIZATION_NAME);
		this.SUPPORTED_CONSTITUENT_FIELD_NAMES.add(StringConstants.FULLTEXT);
	}

	@Override
	public boolean isSupportedConstituentFieldName(String fieldName) {
		return SUPPORTED_CONSTITUENT_FIELD_NAMES.contains(fieldName);
	}

	@Override
    public QueryLookup readQueryLookup(String fieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readQueryLookup: fieldDefinitionId = " + fieldDefinitionId);
        }
        return queryLookupDao.readQueryLookup(fieldDefinitionId);
    }

    @Override
    public List<Object> executeQueryLookup(String fieldDefinitionId, Map<String, String> params) {
        if (logger.isTraceEnabled()) {
            logger.trace("executeQueryLookup: fieldDefinitionId = " + fieldDefinitionId + " params = " + params);
        }
	    final List<Object> result = new ArrayList<Object>();

        QueryLookup queryLookup = readQueryLookup(fieldDefinitionId);
        if (queryLookup != null) {
			Map<String, Object> filteredParams = filterParameters(params, queryLookup);
			EntityType entityType = queryLookup.getEntityType();

			if (entityType == EntityType.constituent) {
				String where = queryLookup.getSqlWhere();
				if (where != null && where.trim().length() > 0) {
					filteredParams.put(QueryUtil.ADDITIONAL_WHERE, where);
				}
				List<Constituent> constituents = constituentService.searchConstituents(filteredParams);
				result.addAll(constituents);
			}
			else if (entityType == EntityType.gift) {
				List<Gift> gifts = giftService.searchGifts(filteredParams);
				result.addAll(gifts);
			}
			else if (entityType == EntityType.recurringGift) {
				List<RecurringGift> recurringGifts = recurringGiftService.searchRecurringGifts(filteredParams);
				result.addAll(recurringGifts);
			}
			else if (entityType == EntityType.pledge) {
				List<Pledge> pledges = pledgeService.searchPledges(filteredParams);
				result.addAll(pledges);
			}
        }
        return result;
    }

	private Map<String, Object> filterParameters(Map<String, String> params, QueryLookup lookup) {
		final Map<String, Object> filteredParams = new HashMap<String, Object>();
		if (lookup != null) {
			if (EntityType.constituent == lookup.getEntityType()) {
				for (String key : params.keySet()) {
					if (isSupportedConstituentFieldName(key)) {
						filteredParams.put(key, params.get(key));
					}
				}
			}
			else {
				// Check request is a valid lookup field for lookup screen
				// TODO: doesn't appear Gift/Recurring Gift/Pledge Query Lookups are used; below code will never be invoked
				List<QueryLookupParam> lookupParams = lookup.getQueryLookupParams();
				if (lookupParams != null) {
					String searchOption = params.get("searchOption");
					String searchValue = params.get(searchOption);
				    for (QueryLookupParam lookupParam : lookupParams) {
				        String key = lookupParam.getName();
				        if (key.equals(searchOption)) {
				            filteredParams.put(searchOption, searchValue);
				        }
				    }
				}
			}
		}
		filteredParams.put(StringConstants.SITE_NAME, getSiteName());
		return filteredParams;
	}
}