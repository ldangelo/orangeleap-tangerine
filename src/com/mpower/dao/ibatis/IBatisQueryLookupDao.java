package com.mpower.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.QueryLookupDao;
import com.mpower.domain.model.QueryLookup;

/** 
 * Corresponds to the QUERY_LOOKUP tables
 */
@Repository("queryLookupDAO")
public class IBatisQueryLookupDao extends AbstractIBatisDao implements QueryLookupDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisQueryLookupDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }
    
    @Override
    public QueryLookup readQueryLookup(String fieldDefinitionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readQueryLookup: fieldDefinitionId = " + fieldDefinitionId);
        }
        Map<String, Object> params = setupParams();
        params.put("fieldDefinitionId", fieldDefinitionId);
        return (QueryLookup)getSqlMapClientTemplate().queryForObject("SELECT_QUERY_LOOKUP_BY_SITE_FLD_DEF_ID", params);
    }
}