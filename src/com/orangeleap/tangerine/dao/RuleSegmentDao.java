package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;

public interface RuleSegmentDao {

    public RuleSegment readRuleSegmentById(Long id);

    public RuleSegment maintainRuleSegment(RuleSegment ruleSegment);

}