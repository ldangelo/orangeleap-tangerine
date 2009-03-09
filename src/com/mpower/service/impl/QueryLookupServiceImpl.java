package com.mpower.service.impl;

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

import com.mpower.dao.interfaces.CommitmentDao;
import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.dao.interfaces.GiftDao;
import com.mpower.dao.interfaces.QueryLookupDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.QueryLookup;
import com.mpower.domain.model.QueryLookupParam;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.QueryLookupService;
import com.mpower.type.CommitmentType;
import com.mpower.type.EntityType;

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
    
    @Resource(name = "commitmentDAO")
    private CommitmentDao commitmentDao;
    
    
    @Override
    public QueryLookup readQueryLookup(String fieldDefinitionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readQueryLookup: fieldDefinitionId = " + fieldDefinitionId);
        }
        return queryLookupDao.readQueryLookup(fieldDefinitionId);
    }

    @Override
    public List<Object> executeQueryLookup(String fieldDefinitionId, Map<String, String> params) {
        if (logger.isDebugEnabled()) {
            logger.debug("executeQueryLookup: fieldDefinitionId = " + fieldDefinitionId + " params = " + params);
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
            if (where != null && where.trim().length() > 0) filterparms.put("additionalWhere", where);
        	List<Person> persons = constituentDao.searchConstituents(filterparms, null);
        	result.addAll(persons);
        	
        }
        
        if (entityType == EntityType.gift) {
            List<Gift> gifts = giftDao.searchGifts(filterparms);
        	result.addAll(gifts);
        }
        
        if (entityType == EntityType.commitment) {
        	CommitmentType commitmentType = CommitmentType.RECURRING_GIFT;
            List<Commitment> commitments = commitmentDao.searchCommitments(commitmentType, filterparms);
        	result.addAll(commitments);
        }
        
        return result;
        
    }
}