package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;

public interface RuleSegmentParmDao {

    public RuleSegmentParm readRuleSegmentParmById(Long id);

    public RuleSegmentParm maintainRuleSegmentParm(RuleSegmentParm ruleSegmentParm);

}