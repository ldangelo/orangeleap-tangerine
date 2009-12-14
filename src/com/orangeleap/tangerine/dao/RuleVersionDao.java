package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;

public interface RuleVersionDao {

    public RuleVersion readRuleVersionById(Long id);

    public RuleVersion maintainRuleVersion(RuleVersion ruleVersion);

}