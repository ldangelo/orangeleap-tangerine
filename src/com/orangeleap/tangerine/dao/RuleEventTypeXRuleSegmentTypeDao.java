package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.rule.RuleEventTypeXRuleSegmentType;

public interface RuleEventTypeXRuleSegmentTypeDao {

	  public List<RuleEventTypeXRuleSegmentType> selectRuleSegmentTypesForRuleEventType(Long ruleEventTypeId);
	  public void maintainRuleEventTypeXRuleSegmentType(Long ruleEventTypeId, List<RuleEventTypeXRuleSegmentType> ruleEventTypeXRuleSegmentTypes);
			
}