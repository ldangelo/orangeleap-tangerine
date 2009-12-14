package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleEventType;

public interface RuleEventTypeDao {

    public RuleEventType readRuleEventTypeById(Long id);

    public RuleEventType maintainRuleEventType(RuleEventType ruleEventType);

}