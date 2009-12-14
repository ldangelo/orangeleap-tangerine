package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleSegmentTypeParmDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentTypeParm;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleSegmentTypeParm table
 */
@Repository("ruleSegmentTypeParmDAO")
public class IBatisRuleSegmentTypeParmDao extends AbstractIBatisDao implements RuleSegmentTypeParmDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleSegmentTypeParmDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleSegmentTypeParm maintainRuleSegmentTypeParm(RuleSegmentTypeParm ruleSegmentTypeParm) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleSegmentTypeParm: ruleSegmentTypeParmId = " + ruleSegmentTypeParm.getId());
        }
        return (RuleSegmentTypeParm)insertOrUpdate(ruleSegmentTypeParm, "RULE_SEGMENT_TYPE_PARM");
    }

    @Override
    public RuleSegmentTypeParm readRuleSegmentTypeParmById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleSegmentTypeParmById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleSegmentTypeParm)getSqlMapClientTemplate().queryForObject("SELECT_RULE_SEGMENT_TYPE_PARM_BY_ID", params);
    }
    
}