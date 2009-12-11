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

package com.orangeleap.tangerine.service.impl;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.service.OrangeLeapRuleAgent;
import com.orangeleap.tangerine.service.rule.OrangeLeapRuleBase;
import com.orangeleap.tangerine.type.RuleEventType;
import com.orangeleap.tangerine.util.OLLogger;

@Service("OrangeLeapRuleAgent")
public class OrangeLeapRuleAgentImpl implements OrangeLeapRuleAgent, ApplicationContextAware {
	
    protected final Log logger = OLLogger.getLog(getClass());

    private ApplicationContext applicationContext;
	
	@Override
	public OrangeLeapRuleBase getOrangeLeapRuleBase(RuleEventType ruleEventType, String siteName, boolean testMode) {
		return new OrangeLeapRuleBase(ruleEventType, siteName, testMode, applicationContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	
}