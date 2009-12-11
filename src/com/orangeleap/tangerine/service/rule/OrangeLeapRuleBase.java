package com.orangeleap.tangerine.service.rule;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.type.RuleEventType;

public class OrangeLeapRuleBase {
	
	private RuleEventType ruleEventType;
	private String siteName;
	private boolean testMode;
	private ApplicationContext applicationContext;
	
	public OrangeLeapRuleBase(RuleEventType ruleEventType, String siteName, boolean testMode, ApplicationContext applicationContext) {
		this.setRuleEventType(ruleEventType);
		this.setSiteName(siteName);
		this.setTestMode(testMode);
		this.setApplicationContext(applicationContext);
	}
	
	public OrangeLeapRuleSession newRuleSession() {
		return new OrangeLeapRuleSession(this);
	}

	public void setRuleEventType(RuleEventType ruleEventType) {
		this.ruleEventType = ruleEventType;
	}

	public RuleEventType getRuleEventType() {
		return ruleEventType;
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
