/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.rule;

import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.drools.agent.RuleAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DroolsRuleAgent implements ApplicationContextAware {
    private static final Log logger = OLLogger.getLog(DroolsRuleAgent.class);

    private Properties droolsProperties;
    private final Map<String, RuleAgent> agentMap;

    private final String pollInterval;
    private final String packageDir;

    public DroolsRuleAgent(final String pollInterval, final String cacheDir, final String packageDir) {
        if (logger.isDebugEnabled()) {
            logger.debug("DroolsRuleAgent: pollInterval = " + pollInterval + " cacheDir = " + cacheDir + " packageDir = " + packageDir);
        }
        this.pollInterval = pollInterval;
        String cacheDir1 = cacheDir;
        this.packageDir = packageDir;
        this.droolsProperties = null;
        agentMap = new HashMap<String, RuleAgent>();
    }

    Properties getDroolsProperties(String siteName) {
        String file = packageDir + "/" + siteName + ".pkg";
        droolsProperties = new Properties();
        droolsProperties.put(RuleAgent.POLL_INTERVAL, pollInterval);
        droolsProperties.put(RuleAgent.FILES, file);
        return droolsProperties;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ApplicationContext applicationContext1 = applicationContext;
    }

    public RuleAgent getRuleAgent(String siteName) {
        RuleAgent agent;

        agent = agentMap.get(siteName);
        if (agent != null) return agent;

        agent = RuleAgent.newRuleAgent(getDroolsProperties(siteName));
        agentMap.put(siteName, agent);

        return agent;
    }


}
