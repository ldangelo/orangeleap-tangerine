package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleEventTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventType;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleEventType table
 */
@Repository("ruleEventTypeDAO")
public class IBatisRuleEventTypeDao extends AbstractIBatisDao implements RuleEventTypeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleEventTypeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleEventType maintainRuleEventType(RuleEventType ruleEventType) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleEventType: ruleEventTypeId = " + ruleEventType.getId());
        }
        return (RuleEventType)insertOrUpdate(ruleEventType, "RULE_EVENT_TYPE");
    }

    @Override
    public RuleEventType readRuleEventTypeById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleEventTypeById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleEventType)getSqlMapClientTemplate().queryForObject("SELECT_RULE_EVENT_TYPE_BY_ID", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<RuleEventType> readAllRuleEventTypes() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllRuleEventTypes:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_RULE_EVENT_TYPES", params);
	}

	@Override
	public RuleEventType readRuleEventTypeByNameId(String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleEventTypeByNameId: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleEventType)getSqlMapClientTemplate().queryForObject("SELECT_RULE_EVENT_TYPE_BY_NAME_ID", params);
	}

}