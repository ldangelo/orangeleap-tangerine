package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleSegmentDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleSegment table
 */
@Repository("ruleSegmentDAO")
public class IBatisRuleSegmentDao extends AbstractIBatisDao implements RuleSegmentDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleSegmentDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleSegment maintainRuleSegment(RuleSegment ruleSegment) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleSegment: ruleSegmentId = " + ruleSegment.getId());
        }
        return (RuleSegment)insertOrUpdate(ruleSegment, "RULE_SEGMENT");
    }

    @Override
    public RuleSegment readRuleSegmentById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleSegmentById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleSegment)getSqlMapClientTemplate().queryForObject("SELECT_RULE_SEGMENT_BY_ID", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<RuleSegment> readRuleSegmentsByRuleVersionId(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleSegmentsByRuleVersionId: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return getSqlMapClientTemplate().queryForList("SELECT_RULE_SEGMENTS_BY_RULE_VERSION_ID", params);
	}
    
}