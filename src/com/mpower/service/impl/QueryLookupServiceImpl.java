package com.mpower.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.QueryLookupDao;
import com.mpower.dao.interfaces.QueryLookupExecutorDao;
import com.mpower.domain.model.QueryLookup;
import com.mpower.domain.model.QueryLookupParam;
import com.mpower.service.QueryLookupService;

@Service("queryLookupService")
@Transactional(propagation = Propagation.REQUIRED)
public class QueryLookupServiceImpl implements QueryLookupService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "queryLookupDAO")
    private QueryLookupDao queryLookupDao;
    
    @Resource(name = "queryLookupExecutorDAO")
    private QueryLookupExecutorDao queryLookupExecutorDao;

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
        QueryLookup ql = readQueryLookup(fieldDefinitionId);
        List<Object> objects = null;
        if (ql != null) {
            StringBuilder query = new StringBuilder(ql.getSqlQuery());
            LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
            List<QueryLookupParam> lookupParams = ql.getQueryLookupParams();
            if (lookupParams != null) {
                for (QueryLookupParam qlp : lookupParams) {
                    String key = qlp.getName();
                    if (params.get(key) != null) {
                        query.append(" AND ");
                        query.append(key);
                        query.append(" LIKE :");
                        query.append(key.replace('.', '_')); // TODO: fix for JDBCTemplate
                        paramsMap.put(key.replace('.', '_'), params.get(key) + "%"); // TODO: fix for JDBCTemplate
                    }
                }
            }

            if (ql != null) {
                objects = queryLookupExecutorDao.executeQueryLookup(query.toString(), paramsMap);
            }
        }
        return objects;
    }
}