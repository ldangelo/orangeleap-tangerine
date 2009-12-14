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

package com.orangeleap.tangerine.service.customization;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.RuleGeneratedCodeDao;
import com.orangeleap.tangerine.domain.customization.rule.RuleGeneratedCode;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("rulesConfService")
@Transactional
public class RulesConfServiceImpl extends AbstractTangerineService implements RulesConfService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;

    @Resource(name = "ruleGeneratedCodeDAO")
    private RuleGeneratedCodeDao ruleGeneratedCodeDao;

    public String readRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	return rgc == null ? "" : rgc.getGeneratedCodeText();
    }

    public void compileRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	if (rgc == null) {
    		rgc = new RuleGeneratedCode();
    		rgc.setIsTestOnly(testMode);
    		rgc.setRuleEventTypeNameId(rulesEventNameType.getType());
    	}
    	rgc.setGeneratedCodeText(compileCode(rulesEventNameType, testMode));
    	ruleGeneratedCodeDao.maintainRuleGeneratedCode(rgc);
    }
    
    private String compileCode(RuleEventNameType rulesEventType, boolean testMode) {
    	return "";
    }

}
