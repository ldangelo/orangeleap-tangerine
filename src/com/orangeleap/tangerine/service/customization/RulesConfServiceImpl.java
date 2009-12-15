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

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.RuleDao;
import com.orangeleap.tangerine.dao.RuleGeneratedCodeDao;
import com.orangeleap.tangerine.dao.RuleSegmentDao;
import com.orangeleap.tangerine.dao.RuleSegmentTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.domain.customization.rule.RuleGeneratedCode;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
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

    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "ruleSegmentDAO")
    private RuleSegmentDao ruleSegmentDao;

    @Resource(name = "ruleSegmentTypeDAO")
    private RuleSegmentTypeDao ruleSegmentTypeDao;

    
    
    
    
    public String readRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {
    	
    	compileRulesEventScript(rulesEventNameType, testMode);  // TODO remove once UI rules admin wizard updates trigger compile
    	
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
    	StringBuilder script = new StringBuilder();
    	List<Rule> rules = ruleDao.readByRuleEventTypeNameId(rulesEventType.getType(), testMode);
    	for (Rule rule: rules) {
    		if (rule.getRuleIsActive() && rule.getRuleVersions().size() > 0) {
    			StringBuilder conditions = new StringBuilder();
    			StringBuilder consequences = new StringBuilder();
    			RuleVersion ruleVersion = rule.getRuleVersions().get(rule.getRuleVersions().size()-1);
    			List<RuleSegment> ruleSegments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(ruleVersion.getId());
    			for (RuleSegment ruleSegment : ruleSegments) {
    				RuleSegmentType ruleSegmentType = ruleSegmentTypeDao.readRuleSegmentTypeById(ruleSegment.getRuleSegmentTypeId());
    				if (ruleSegmentType == null) throw new RuntimeException("Invalid rule segment type for rule: "+rule.getRuleDesc());
    				String text = ruleSegmentType.getRuleSegmentTypeText();
    				// TODO replacement parms
    				if (RuleSegmentType.CONDITION_TYPE.equals(ruleSegmentType.getRuleSegmentTypeType())) {
    					if (conditions.length() > 0) conditions.append("&& ");
    					conditions.append("(").append(text).append(")\n");
    				} else {
    					consequences.append(text).append(";\n");
    				}
    				script.append("// ").append(rule.getRuleDesc());
    				script.append("if ( \n").append(conditions.toString()).append("   ) {\n").append(consequences.toString()).append("}\n\n");
    			}
    		}
    	}
    	return script.toString();
    }

}
