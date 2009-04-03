package com.orangeleap.tangerine.service.rule;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

public class DroolsRuleAgent implements ApplicationContextAware {
	private static final Log logger = LogFactory.getLog(DroolsRuleAgent.class);

	private ApplicationContext applicationContext;
	private final Properties droolsProperties;

	public DroolsRuleAgent(String pollInterval, String cacheDir, String packageDir) {
	    if (logger.isDebugEnabled()) {
	        logger.debug("DroolsRuleAgent: pollInterval = " + pollInterval + " cacheDir = " + cacheDir + " packageDir = " + packageDir);
	    }
		droolsProperties = new Properties();
        droolsProperties.put("newInstance", "false");
        droolsProperties.put("name", "orangeleap");
        String interval = System.getProperty("overrideDroolsInterval");
        if (StringUtils.hasText(interval)) {
            pollInterval = interval;
        }
        droolsProperties.put("poll", pollInterval);
        String cache = System.getProperty("overrideDroolsCache");
        if (StringUtils.hasText(cache)) {
            cacheDir = cache;
        }
        droolsProperties.put("cache", cacheDir);
        String thisPackage = System.getProperty("overrideDroolsPackage");
        if (StringUtils.hasText(thisPackage)) {
            packageDir = thisPackage;
        }
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
