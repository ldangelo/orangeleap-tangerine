package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.RuleGeneratedCode;
import com.orangeleap.tangerine.type.RuleEventType;

public interface RuleGeneratedCodeDao {

    public RuleGeneratedCode readRuleGeneratedCodeById(Long id);

    public RuleGeneratedCode maintainRuleGeneratedCode(RuleGeneratedCode ruleGeneratedCode);
    
    public RuleGeneratedCode readRuleGeneratedCodeByTypeMode(RuleEventType rulesEventType, boolean testMode);

}