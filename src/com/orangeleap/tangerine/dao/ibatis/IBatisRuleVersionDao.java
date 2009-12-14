package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RuleVersionDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RuleVersion table
 */
@Repository("ruleVersionDAO")
public class IBatisRuleVersionDao extends AbstractIBatisDao implements RuleVersionDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRuleVersionDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RuleVersion maintainRuleVersion(RuleVersion ruleVersion) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRuleVersion: ruleVersionId = " + ruleVersion.getId());
        }
        return (RuleVersion)insertOrUpdate(ruleVersion, "RULE_VERSION");
    }

    @Override
    public RuleVersion readRuleVersionById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRuleVersionById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RuleVersion)getSqlMapClientTemplate().queryForObject("SELECT_RULE_VERSION_BY_ID", params);
    }
    
}