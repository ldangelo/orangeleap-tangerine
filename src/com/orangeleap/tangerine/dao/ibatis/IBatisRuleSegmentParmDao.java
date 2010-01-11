package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleSegmentParmDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleSegmentParm table
 */
@Repository("ruleSegmentParmDAO")
public class IBatisRuleSegmentParmDao extends AbstractIBatisDao implements RuleSegmentParmDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleSegmentParmDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleSegmentParm maintainRuleSegmentParm(RuleSegmentParm ruleSegmentParm) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleSegmentParm: ruleSegmentParmId = " + ruleSegmentParm.getId());
        }
        return (RuleSegmentParm)insertOrUpdate(ruleSegmentParm, "RULE_SEGMENT_PARM");
    }

    @Override
    public RuleSegmentParm readRuleSegmentParmById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleSegmentParmById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleSegmentParm)getSqlMapClientTemplate().queryForObject("SELECT_RULE_SEGMENT_PARM_BY_ID", params);
    }

	@Override
	public void deleteSegmentParms(Long segmentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteSegmentParms: segmentId = " + segmentId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", segmentId);
        getSqlMapClientTemplate().delete("DELETE_RULE_SEGMENT_PARMS_BY_RULE_SEGMENT_ID", params);
	}
    
}