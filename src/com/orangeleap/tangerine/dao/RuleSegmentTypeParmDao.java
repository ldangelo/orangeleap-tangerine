package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentTypeParm;

public interface RuleSegmentTypeParmDao {

    public RuleSegmentTypeParm readRuleSegmentTypeParmById(Long id);

    public RuleSegmentTypeParm maintainRuleSegmentTypeParm(RuleSegmentTypeParm ruleSegmentTypeParm);

}