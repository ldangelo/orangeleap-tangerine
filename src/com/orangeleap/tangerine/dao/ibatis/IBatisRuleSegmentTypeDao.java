package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleSegmentTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleSegmentType table
 */
@Repository("ruleSegmentTypeDAO")
public class IBatisRuleSegmentTypeDao extends AbstractIBatisDao implements RuleSegmentTypeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleSegmentTypeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleSegmentType maintainRuleSegmentType(RuleSegmentType ruleSegmentType) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleSegmentType: ruleSegmentTypeId = " + ruleSegmentType.getId());
        }
        return (RuleSegmentType)insertOrUpdate(ruleSegmentType, "RULE_SEGMENT_TYPE");
    }

    @Override
    public RuleSegmentType readRuleSegmentTypeById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleSegmentTypeById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleSegmentType)getSqlMapClientTemplate().queryForObject("SELECT_RULE_SEGMENT_TYPE_BY_ID", params);
    }
    
}