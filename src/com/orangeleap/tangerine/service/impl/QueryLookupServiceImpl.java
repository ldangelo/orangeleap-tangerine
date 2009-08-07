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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.QueryLookupService;
import com.orangeleap.tangerine.type.EntityType;

@Service("queryLookupService")
@Transactional(propagation = Propagation.REQUIRED)
public class QueryLookupServiceImpl extends AbstractTangerineService implements QueryLookupService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "queryLookupDAO")
    private QueryLookupDao queryLookupDao;
    
    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;
    
    @Resource(name = "giftDAO")
    private GiftDao giftDao;
    
    @Resource(name = "recurringGiftDAO")
    private RecurringGiftDao recurringGiftDao;
    
    @Resource(name = "pledgeDAO")
    private PledgeDao pledgeDao;
    
    
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
        
        String searchOption = params.get("searchOption");
        String searchValue = params.get(searchOption);
        
        Map<String, Object> filterparms = new HashMap<String, Object>();
        filterparms.put("siteName", getSiteName());
        
        // Check request is a valid lookup field for lookup screen
        QueryLookup ql = readQueryLookup(fieldDefinitionId);
        if (ql != null) {
            List<QueryLookupParam> lookupParams = ql.getQueryLookupParams();
            if (lookupParams != null) {
                for (QueryLookupParam qlp : lookupParams) {
                    String key = qlp.getName();
                    if (key.equals(searchOption)) {
                        filterparms.put(searchOption, searchValue);
                    }
                }
            }
        }
        
        EntityType entityType = ql.getEntityType();
    	List<Object> result = new ArrayList<Object>();
        
        if (entityType == EntityType.constituent) {
            String where = ql.getSqlWhere();
            if (where != null && where.trim().length() > 0) {
                filterparms.put(QueryUtil.ADDITIONAL_WHERE, where);
            }
        	List<Constituent> constituents = constituentDao.searchConstituents(filterparms);
        	result.addAll(constituents);
        }
        
        else if (entityType == EntityType.gift) {
            List<Gift> gifts = giftDao.searchGifts(filterparms);
        	result.addAll(gifts);
        }
        
        else if (entityType == EntityType.recurringGift) {
            List<RecurringGift> recurringGifts = recurringGiftDao.searchRecurringGifts(filterparms);
        	result.addAll(recurringGifts);
        }
        
        else if (entityType == EntityType.pledge) {
            List<Pledge> pledges = pledgeDao.searchPledges(filterparms);
            result.addAll(pledges);
        }
        
        return result;
    }
}