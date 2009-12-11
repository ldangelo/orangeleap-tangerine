package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.service.rule.OrangeLeapRuleBase;
import com.orangeleap.tangerine.type.RuleEventType;

public interface OrangeLeapRuleAgent {

	public OrangeLeapRuleBase getOrangeLeapRuleBase(RuleEventType ruleEventType, String siteName,  boolean testmode);

}
