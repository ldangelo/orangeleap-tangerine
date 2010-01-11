package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;

public interface RuleSegmentDao {

    public RuleSegment readRuleSegmentById(Long id);

    public List<RuleSegment> readRuleSegmentsByRuleVersionId(Long id);

    public RuleSegment maintainRuleSegment(RuleSegment ruleSegment);

	public void deleteSegment(Long id);

}