package com.orangeleap.tangerine.service.rule;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.type.RuleEventNameType;

public class OrangeLeapRuleBase {
	
	private RuleEventNameType rulesEventNameType;
	private String siteName;
	private boolean testMode;
	private ApplicationContext applicationContext;
	
	public OrangeLeapRuleBase(RuleEventNameType rulesEventNameType, String siteName, boolean testMode, ApplicationContext applicationContext) {
		this.setRuleEventNameType(rulesEventNameType);
		this.setSiteName(siteName);
		this.setTestMode(testMode);
		this.setApplicationContext(applicationContext);
	}
	
	public OrangeLeapRuleSession newRuleSession() {
		return new OrangeLeapRuleSession(this);
	}

	public void setRuleEventNameType(RuleEventNameType rulesEventNameType) {
		this.rulesEventNameType = rulesEventNameType;
	}

	public RuleEventNameType getRuleEventNameType() {
		return rulesEventNameType;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	public boolean isTestMode() {
		return testMode;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
