package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.type.CacheGroupType;

/** 
 * Corresponds to the CACHE_GROUP table
 */
@Repository("cacheGroupDAO")
public class IBatisCacheGroupDao extends AbstractIBatisDao implements CacheGroupDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisCacheGroupDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<CacheGroup> readCacheGroups() {
        if (logger.isTraceEnabled()) {
            logger.trace("readCacheGroups " );
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_CACHE_GROUPS", params);
    }

    @Override
    public int updateCacheGroupTimestamp(CacheGroupType id) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateCacheGroupTimestamp: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return getSqlMapClientTemplate().update("UPDATE_CACHE_GROUP_BY_ID", params);
    }

}