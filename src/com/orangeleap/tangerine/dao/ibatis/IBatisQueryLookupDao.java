package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;

/** 
 * Corresponds to the QUERY_LOOKUP tables
 */
@Repository("queryLookupDAO")
public class IBatisQueryLookupDao extends AbstractIBatisDao implements QueryLookupDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisQueryLookupDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public QueryLookup readQueryLookup(String fieldDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readQueryLookup: fieldDefinitionId = " + fieldDefinitionId);
        }
        Map<String, Object> params = setupParams();
        params.put("fieldDefinitionId", fieldDefinitionId);
        // There should only be 0, 1, or 2 records (one this site and one null site) returned from this query.
        List<QueryLookup> list = getSqlMapClientTemplate().queryForList("SELECT_QUERY_LOOKUP_BY_SITE_FLD_DEF_ID", params);
        if (list.isEmpty()) {
            return null;
        }
        for (QueryLookup ql : list) {
        	if (ql.getSite() != null) {
        		return ql;
        	}
        }
        return list.get(0);
    }
    
    @Override
	public QueryLookup maintainQueryLookup(QueryLookup queryLookup) {
        return (QueryLookup)insertOrUpdate(queryLookup, "QUERY_LOOKUP");
    }
    
    @Override
	public void maintainQueryLookupParam(QueryLookupParam queryLookupParam) {
        insertOrUpdate(queryLookupParam, "QUERY_LOOKUP_PARAM");
    }

    

}