package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RollupValueDao;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RollupValue table
 */
@Repository("rollupValueDAO")
public class IBatisRollupValueDao extends AbstractIBatisDao implements RollupValueDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRollupValueDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RollupValue maintainRollupValue(RollupValue rollupValue) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRollupValue: rollupValueId = " + rollupValue.getId());
        }
        return (RollupValue)insertOrUpdate(rollupValue, "ROLLUP_VALUE");
    }

    @Override
    public RollupValue readRollupValueById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRollupValueById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RollupValue)getSqlMapClientTemplate().queryForObject("SELECT_ROLLUP_VALUE_BY_ID", params);
    }
    
}