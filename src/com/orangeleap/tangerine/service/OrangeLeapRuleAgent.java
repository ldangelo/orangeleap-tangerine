package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.service.rule.OrangeLeapRuleBase;
import com.orangeleap.tangerine.type.RuleEventNameType;

public interface OrangeLeapRuleAgent {

	public OrangeLeapRuleBase getOrangeLeapRuleBase(RuleEventNameType rulesEventNameType, String siteName,  boolean testmode);

}
