package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.rule.Rule;

public interface RuleDao {

    public Rule readRuleById(Long id);

    public Rule maintainRule(Rule rule);

    public List<Rule> readByRuleEventTypeNameId(String ruleEventTypeNameId);

}