package com.orangeleap.tangerine.service.rule;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DroolsRuleAgent implements ApplicationContextAware {
	private static final Log logger = LogFactory.getLog(DroolsRuleAgent.class);

	private ApplicationContext applicationContext;
	private final Properties droolsProperties;

	public DroolsRuleAgent(final String pollInterval, final String cacheDir, final String packageDir) {
	    if (logger.isDebugEnabled()) {
	        logger.debug("DroolsRuleAgent: pollInterval = " + pollInterval + " cacheDir = " + cacheDir + " packageDir = " + packageDir);
	    }
		droolsProperties = new Properties();
        droolsProperties.put("newInstance", "false");
        droolsProperties.put("name", "orangeleap");
        droolsProperties.put("poll", pollInterval);
        droolsProperties.put("cache", cacheDir);
        droolsProperties.put("dir", packageDir);
	}
	
	public Properties getDroolsProperties() {
        return droolsProperties;
    }
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

    public RuleAgent getRuleAgent() {
        return RuleAgent.newRuleAgent(getDroolsProperties());
    }
}
