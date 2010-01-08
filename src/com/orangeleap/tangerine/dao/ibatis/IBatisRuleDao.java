package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the Rule table
 */
@Repository("ruleDAO")
public class IBatisRuleDao extends AbstractIBatisDao implements RuleDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public Rule maintainRule(Rule rule) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRule: ruleId = " + rule.getId());
        }
        rule.setSiteName(getSiteName());
        return (Rule)insertOrUpdate(rule, "RULE");
    }

    @Override
    public Rule readRuleById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (Rule)getSqlMapClientTemplate().queryForObject("SELECT_RULE_BY_ID", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Rule> readByRuleEventTypeNameId(String ruleEventTypeNameId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByRuleEventTypeNameId: id = " + ruleEventTypeNameId);
        }
        Map<String, Object> params = setupParams();
        params.put("ruleEventTypeNameId", ruleEventTypeNameId);
        return getSqlMapClientTemplate().queryForList("SELECT_RULES_BY_RULE_EVENT_TYPE_NAME_ID", params);
	}
    
}