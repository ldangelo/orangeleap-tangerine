package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.domain.Person;
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
    protected final Log logger = LogFactory.getLog(getClass());

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
        
        if (entityType == EntityType.person) {
            String where = ql.getSqlWhere();
            if (where != null && where.trim().length() > 0) {
                filterparms.put(QueryUtil.ADDITIONAL_WHERE, where);
            }
        	List<Person> persons = constituentDao.searchConstituents(filterparms);
        	result.addAll(persons);
        	
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