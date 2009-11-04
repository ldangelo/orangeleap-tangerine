package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RollupAttributeDao;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RollupAttribute table
 */
@Repository("rollupAttributeDAO")
public class IBatisRollupAttributeDao extends AbstractIBatisDao implements RollupAttributeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRollupAttributeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRollupAttribute: rollupAttributeId = " + rollupAttribute.getId());
        }
        return (RollupAttribute)insertOrUpdate(rollupAttribute, "ROLLUP_ATTRIBUTE");
    }

    @Override
    public RollupAttribute readRollupAttributeById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRollupAttributeById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RollupAttribute)getSqlMapClientTemplate().queryForObject("SELECT_ROLLUP_ATTRIBUTE_BY_ID", params);
    }
    
}