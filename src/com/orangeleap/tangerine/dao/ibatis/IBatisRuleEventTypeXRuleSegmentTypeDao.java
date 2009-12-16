package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleEventTypeXRuleSegmentTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventTypeXRuleSegmentType;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleEventTypeXRuleSegmentType table
 */
@Repository("ruleEventTypeXRuleSegmentTypeDAO")
public class IBatisRuleEventTypeXRuleSegmentTypeDao extends AbstractIBatisDao implements RuleEventTypeXRuleSegmentTypeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleEventTypeXRuleSegmentTypeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<RuleEventTypeXRuleSegmentType> selectRuleSegmentTypesForRuleEventType(Long ruleEventTypeId) {
        Map<String, Object> params = setupParams();
        params.put("ruleEventTypeId", ruleEventTypeId);
		return getSqlMapClientTemplate().queryForList("SELECT_RULE_SEGMENT_TYPE_X_RULE_EVENT_TYPE_BY_RULE_EVENT_TYPE_ID", params);
	}
    
	@Override
    public void maintainRuleEventTypeXRuleSegmentType(Long ruleEventTypeId, List<RuleEventTypeXRuleSegmentType> ruleEventTypeXRuleSegmentTypes) {
		
        Map<String, Object> params = setupParams();
        params.put("ruleEventTypeId", ruleEventTypeId);
        
        getSqlMapClientTemplate().delete("DELETE_RULE_SEGMENT_TYPE_X_RULE_EVENT_TYPE_BY_RULE_EVENT_TYPE_ID", params);
        
        for (RuleEventTypeXRuleSegmentType ruleEventTypeXRuleSegmentType : ruleEventTypeXRuleSegmentTypes)
        	getSqlMapClientTemplate().insert("INSERT_RULE_SEGMENT_TYPE_X_RULE_EVENT_TYPE", ruleEventTypeXRuleSegmentType);
		
    }
    
}