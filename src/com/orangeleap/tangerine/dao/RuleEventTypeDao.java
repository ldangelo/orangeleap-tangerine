package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.rule.RuleEventType;

public interface RuleEventTypeDao {

    public RuleEventType readRuleEventTypeById(Long id);

    public RuleEventType readRuleEventTypeByNameId(String id);

    public List<RuleEventType> readAllRuleEventTypes();

    public RuleEventType maintainRuleEventType(RuleEventType ruleEventType);
    
}