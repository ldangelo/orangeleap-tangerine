package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;

public interface RuleSegmentTypeDao {

    public RuleSegmentType readRuleSegmentTypeById(Long id);

    public RuleSegmentType maintainRuleSegmentType(RuleSegmentType ruleSegmentType);

}