package com.mpower.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.QueryLookupDao;
import com.mpower.domain.QueryLookup;
import com.mpower.domain.QueryLookupParam;

@Service("queryLookupService")
public class QueryLookupServiceImpl implements QueryLookupService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "queryLookupDao")
    private QueryLookupDao queryLookupDao;

    // @Resource(name = "siteDao")
    // private SiteDao siteDao;

    public QueryLookup readQueryLookup(String siteName, String fieldDefinitionId) {
        return queryLookupDao.readQueryLookup(siteName, fieldDefinitionId);
    }

    public List<Object> executeQueryLookup(String siteName, String fieldDefinitionId, Map<String, String> params) {
        QueryLookup ql = readQueryLookup(siteName, fieldDefinitionId);
        List<Object> objects = null;
        if (ql != null) {
            StringBuilder query = new StringBuilder(ql.getJpaQuery());
            LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("siteName", siteName);
            List<QueryLookupParam> lookupParams = ql.getQueryLookupParams();
            if (lookupParams != null) {
                for (QueryLookupParam qlp : lookupParams) {
                    String key = qlp.getName();
                    if (params.get(key) != null) {
                        query.append(" AND ");
                        query.append(key);
                        query.append(" LIKE :");
                        query.append(key.replace('.', '_'));
                        paramsMap.put(key.replace('.', '_'), params.get(key) + "%");
                    }
                }
            }

            if (ql != null) {
                objects = queryLookupDao.executeQueryLookup(query.toString(), paramsMap);
            }
        }
        return objects;
    }
}