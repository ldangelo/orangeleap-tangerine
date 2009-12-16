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

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentTypeParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.service.rule.OrangeLeapConsequenceRuntimeException;
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.type.RuleSegmentTypeParmType;
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

    //TODO cache scripts and groovy classes
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

    
    
    @Override
    public String readRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {
    	
    	generateRulesEventScript(rulesEventNameType, testMode);  // TODO remove once UI rules admin wizard updates trigger compile
    	
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	return rgc == null ? "" : rgc.getGeneratedCodeText();
    	
    }

    @Override
    public Class compileScript(String script) {
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class groovyClass = loader.parseClass("class RuleRunner{ void run(Map map) { "+script+" } }", "rules.groovy");
		return groovyClass;
    }

    @Override
    public void runScript(String script, Map<String, Object> map) {
		try {
			Class groovyClass = compileScript(script);
			GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
			Object[] args = {map};
			groovyObject.invokeMethod("run", args);
		} catch (OrangeLeapConsequenceRuntimeException e) {
			throw e;
		} catch (InstantiationException e) { 
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) { 
			throw new RuntimeException(e);
		} catch (RuntimeException e) { 
			e.printStackTrace();
			throw new RuntimeException("Error in rules script execution:", e);
		}
    }

    @Override
    public void generateRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	if (rgc == null) {
    		rgc = new RuleGeneratedCode();
    		rgc.setIsTestOnly(testMode);
    		rgc.setRuleEventTypeNameId(rulesEventNameType.getType());
    	}
    	rgc.setGeneratedCodeText(generateCode(rulesEventNameType, testMode));
    	ruleGeneratedCodeDao.maintainRuleGeneratedCode(rgc);
    }
    
    private String generateCode(RuleEventNameType rulesEventType, boolean testMode) {
    	
    	StringBuilder script = new StringBuilder();
    	String result = "";
    	
    	try {
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
	    				text = replaceParms(text, rule, ruleVersion, ruleSegment, ruleSegmentType);
	    				if (RuleSegmentType.CONDITION_TYPE.equals(ruleSegmentType.getRuleSegmentTypeType())) {
	    					if (conditions.length() > 0) conditions.append("&& ");
	    					conditions.append("(").append(text).append(") \\\n");
	    				} else {
	    					consequences.append(text).append("; \n");
	    				}
	    			}
    				script.append("// ").append(rule.getRuleDesc()).append("\n");
    				script.append("map.logger.debug(\"Running rule id "+rule.getId()+"\");\n"); // do not print desc here
    				script.append("if ( \\\n").append(conditions.toString()).append("   ) { \n").append(consequences.toString()).append("} \n\n");
	    		}
	    	}
	    	
	    	result = script.toString();
	    	compileScript(result); // Test compile
	    	logger.debug(result);
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error in rules script generation: " + (rulesEventType == null ? "Null rules event type" : rulesEventType.getType()) + "\n" + script, e);
    	}
    	
    	return result;
    	
    }
    
    private String replaceParms(String text, Rule rule, RuleVersion ruleVersion, RuleSegment ruleSegment, RuleSegmentType ruleSegmentType) {
    	
    	if (ruleSegment.getRuleSegmentParms().size() != ruleSegmentType.getRuleSegmentTypeParms().size()) {
    		throw new RuntimeException("Number of parameters for rule \"" + rule.getRuleDesc() + "\", \"" + ruleSegmentType.getRuleSegmentTypePhrase() + "\" is not correct.");
    	}
    	
    	for (int i = 0; i < ruleSegmentType.getRuleSegmentTypeParms().size(); i++) {
    		
    		RuleSegmentTypeParm ruleSegmentTypeParm = ruleSegmentType.getRuleSegmentTypeParms().get(i);
    		RuleSegmentParm ruleSegmentParm = ruleSegment.getRuleSegmentParms().get(i);
    		RuleSegmentTypeParmType type = RuleSegmentTypeParmType.valueOf(ruleSegmentTypeParm.getRuleSegmentTypeParmType());
    		
    		if (type.equals(RuleSegmentTypeParmType.STRING) || type.equals(RuleSegmentTypeParmType.PICKLIST) || type.equals(RuleSegmentTypeParmType.SITE_VARIABLE)) {
        		String parmvalue = ruleSegmentParm.getRuleSegmentParmStringValue();
        		parmvalue = whiteList(parmvalue); 
    			text = replaceNextParm(text, parmvalue);
    		} else if (type.equals(RuleSegmentTypeParmType.NUMBER)) {
        		BigDecimal parmvalue = ruleSegmentParm.getRuleSegmentParmNumericValue();
    			if (parmvalue != null) text = replaceNextParm(text, parmvalue.toString());
    		} 
    		// TODO support other parm types
    		
    	}
    	return text;
    }
    
    private String replaceNextParm(String text, String value) {
    	int i = text.indexOf("?");
    	if (i == -1) throw new RuntimeException("Invalid number of parameters in rule phrase: " + text);
    	return text.substring(0,i) + value + text.substring(i+1);
    }
    
    // These characters are allowed in groovy script text or picklist value parms - no backslashes, quotes, parens, or semicolons.
    private static String WHITELIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.-_ ";
    
    private String whiteList(String s) {
    	StringBuilder sb = new StringBuilder();
    	if (s == null) return "";
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
    		if (WHITELIST.indexOf(c) > -1) {
    			sb.append(c);
    		}
    	}
    	return sb.toString();
    }

}
