package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleGeneratedCodeDao;
import com.orangeleap.tangerine.domain.RuleGeneratedCode;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleGeneratedCode table
 */
@Repository("ruleGeneratedCodeDAO")
public class IBatisRuleGeneratedCodeDao extends AbstractIBatisDao implements RuleGeneratedCodeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleGeneratedCodeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleGeneratedCode maintainRuleGeneratedCode(RuleGeneratedCode ruleGeneratedCode) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleGeneratedCode: ruleGeneratedCodeId = " + ruleGeneratedCode.getId());
        }
        ruleGeneratedCode.setSiteName(getSiteName());
        return (RuleGeneratedCode)insertOrUpdate(ruleGeneratedCode, "RULE_GENERATED_CODE");
    }

    @Override
    public RuleGeneratedCode readRuleGeneratedCodeById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleGeneratedCodeById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleGeneratedCode)getSqlMapClientTemplate().queryForObject("SELECT_RULE_GENERATED_CODE_BY_ID", params);
    }

	@Override
	public RuleGeneratedCode readRuleGeneratedCodeByTypeMode(
			RuleEventNameType rulesEventNameType, boolean testMode) {
        Map<String, Object> params = setupParams();
        params.put("type", rulesEventNameType.getType());
        params.put("mode", testMode);
        return (RuleGeneratedCode)getSqlMapClientTemplate().queryForObject("SELECT_RULE_GENERATED_CODE_BY_TYPE_MODE", params);
	}
    
}