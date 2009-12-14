package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.Rule;

public interface RuleDao {

    public Rule readRuleById(Long id);

    public Rule maintainRule(Rule rule);

}