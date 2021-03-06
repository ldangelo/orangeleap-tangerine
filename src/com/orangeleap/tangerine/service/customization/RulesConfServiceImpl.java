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

import edu.emory.mathcs.backport.java.util.Collections;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.codehaus.groovy.runtime.InvokerInvocationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.RuleDao;
import com.orangeleap.tangerine.dao.RuleEventTypeDao;
import com.orangeleap.tangerine.dao.RuleGeneratedCodeDao;
import com.orangeleap.tangerine.dao.RuleSegmentDao;
import com.orangeleap.tangerine.dao.RuleSegmentParmDao;
import com.orangeleap.tangerine.dao.RuleSegmentTypeDao;
import com.orangeleap.tangerine.dao.RuleVersionDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventType;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventTypeXRuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleGeneratedCode;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentTypeParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.OrangeleapJmxNotificationBean;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.service.rule.OrangeLeapConsequenceRuntimeException;
import com.orangeleap.tangerine.service.rule.OrangeLeapRuleSession;
import com.orangeleap.tangerine.type.CacheGroupType;
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

    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;

    @Resource(name = "ruleGeneratedCodeDAO")
    private RuleGeneratedCodeDao ruleGeneratedCodeDao;

    @Resource(name = "ruleEventTypeDAO")
    private RuleEventTypeDao ruleEventTypeDao;

    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "ruleVersionDAO")
    private RuleVersionDao ruleVersionDao;

    @Resource(name = "ruleSegmentDAO")
    private RuleSegmentDao ruleSegmentDao;

    @Resource(name = "ruleSegmentParmDAO")
    private RuleSegmentParmDao ruleSegmentParmDao;

    @Resource(name = "ruleSegmentTypeDAO")
    private RuleSegmentTypeDao ruleSegmentTypeDao;
    
    @Resource(name = "OrangeleapJmxNotificationBean")
    protected OrangeleapJmxNotificationBean orangeleapJmxNotificationBean;

    
    // Groovy class and object instances aren't threadsafe unless they don't have instance variables, which we don't.
    private static final int MAX_ENTRIES = 100;
    private static volatile Map<String, GroovyObject> groovyObjectCache;
    
    @SuppressWarnings("unchecked")
	public static void resetGroovyObjectCache() {
    	Map<String, GroovyObject> agroovyObjectCache = (Map<String, GroovyObject>)Collections.synchronizedMap(
	        new LinkedHashMap<String, GroovyObject>(MAX_ENTRIES + 1, .75F, true) {
					private static final long serialVersionUID = 1L;
					@Override
					public boolean removeEldestEntry(Map.Entry<String, GroovyObject> eldest) {
			            return size() > MAX_ENTRIES;
			        }
	            }
    	);
    	groovyObjectCache = agroovyObjectCache;
    }

    static { 
    	resetGroovyObjectCache();
    }

    
    @Override
	public List<RuleSegmentType> getAvailableRuleSegmentTypes(String ruleEventTypeName) {
    	 RuleEventType ruleEventType = ruleEventTypeDao.readRuleEventTypeByNameId(ruleEventTypeName);
         List<RuleSegmentType> availableSegmentTypes = new ArrayList<RuleSegmentType>();
         List<RuleEventTypeXRuleSegmentType> rxss = ruleEventType.getRuleEventTypeXRuleSegmentTypes();
         for (RuleEventTypeXRuleSegmentType rxs: rxss) {
         	RuleSegmentType ruleSegmentType = ruleSegmentTypeDao.readRuleSegmentTypeById(rxs.getRuleSegmentTypeId());
         	availableSegmentTypes.add(ruleSegmentType);
         }
         return availableSegmentTypes;
	}
    
    private synchronized void incrementVersions(RuleEventNameType rulesEventNameType) {
    	List<Rule> rules =  ruleDao.readByRuleEventTypeNameId(rulesEventNameType.getType());
    	for (Rule rule: rules) {
    		RuleVersion lastVersion = rule.getRuleVersions().get(rule.getRuleVersions().size()-1);
    		copyVersion(lastVersion);
    	}
    }
    
    private void copyVersion(RuleVersion oldRuleVersion) {
    	RuleVersion newRuleVersion = new RuleVersion();
    	BeanUtils.copyProperties(oldRuleVersion, newRuleVersion);
    	newRuleVersion.setRuleVersionSeq(oldRuleVersion.getRuleVersionSeq() + 1);
    	newRuleVersion.setId(0L);
    	ruleVersionDao.maintainRuleVersion(newRuleVersion);
    	List<RuleSegment> segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(oldRuleVersion.getId());
    	for (RuleSegment segment: segments) {
    		copyRuleSegment(segment, newRuleVersion);
    	}
    }
    
	@Override
	public void cloneRule(Rule oldrule, long seqno) {
		
		Rule newRule = new Rule();
    	BeanUtils.copyProperties(oldrule, newRule);
    	newRule.setId(0L);
    	newRule.setRuleDesc("Copy of " + newRule.getRuleDesc());
    	newRule.setRuleVersions(new ArrayList<RuleVersion>());
    	newRule.setRuleIsActive(false);
    	newRule = ruleDao.maintainRule(newRule);
    	
    	RuleVersion newRuleVersion = new RuleVersion();
    	RuleVersion oldRuleVersion = oldrule.getRuleVersions().get(oldrule.getRuleVersions().size()-1); // copy latest version
    	BeanUtils.copyProperties(oldRuleVersion, newRuleVersion);
    	newRuleVersion.setRuleVersionSeq(0L);
    	newRuleVersion.setId(0L);
    	newRuleVersion.setRuleId(newRule.getId());
    	newRuleVersion = ruleVersionDao.maintainRuleVersion(newRuleVersion);
    	
    	List<RuleSegment> segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(oldRuleVersion.getId());
    	for (RuleSegment segment: segments) {
    		copyRuleSegment(segment, newRuleVersion);
    	}
    	
	}
    
    private void copyRuleSegment(RuleSegment oldRuleSegment, RuleVersion newRuleVersion) {
    	RuleSegment newRuleSegment = new RuleSegment();
    	BeanUtils.copyProperties(oldRuleSegment, newRuleSegment);
    	newRuleSegment.setRuleVersionId(newRuleVersion.getId());
    	newRuleSegment.setId(0L);
    	ruleSegmentDao.maintainRuleSegment(newRuleSegment);
    	for (RuleSegmentParm oldRuleSegmentParm: oldRuleSegment.getRuleSegmentParms()) {
    		copyRuleSegmentParm(oldRuleSegmentParm, newRuleSegment);
    	}
    }
    
    private void copyRuleSegmentParm(RuleSegmentParm oldRuleSegmentParm, RuleSegment newRuleSegment) {
    	RuleSegmentParm newRuleSegmentParm = new RuleSegmentParm();
    	BeanUtils.copyProperties(oldRuleSegmentParm, newRuleSegmentParm);
    	newRuleSegmentParm.setRuleSegmentId(newRuleSegment.getId());
    	newRuleSegmentParm.setId(0L);
    	ruleSegmentParmDao.maintainRuleSegmentParm(newRuleSegmentParm);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void fireRulesEvent(RuleEventNameType rulesEventNameType, boolean testMode, Map<String, Object> map) {
		try {
        	orangeleapJmxNotificationBean.incrementStatCount(tangerineUserHelper.lookupUserSiteName(), OrangeleapJmxNotificationBean.RULE_CACHE_ATTEMPTS);
		    String key = buildCacheKey(rulesEventNameType, testMode);
		    GroovyObject groovyObject;
		    synchronized (groovyObjectCache) {
		    	groovyObject = groovyObjectCache.get(key);
		    	if (groovyObject == null) {
		    		String script = readRulesEventScript(rulesEventNameType, testMode);
					Class groovyClass = compileScript(script);
					groovyObject = (GroovyObject) groovyClass.newInstance();
		    	} else {
		        	orangeleapJmxNotificationBean.incrementStatCount(tangerineUserHelper.lookupUserSiteName(), OrangeleapJmxNotificationBean.RULE_CACHE_HITS);
		    	}
		    	groovyObjectCache.put(key, groovyObject); // This will update the position in the cache to MRU.
		    }
			groovyObject.invokeMethod("run", new Object[]{map});
		} catch (InvokerInvocationException e) {
			if (e.getCause() instanceof OrangeLeapConsequenceRuntimeException) {
				throw (OrangeLeapConsequenceRuntimeException)e.getCause();
			}
			throw new RuntimeException(e.getCause());
		} catch (InstantiationException e) { 
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) { 
			throw new RuntimeException(e);
		} catch (RuntimeException e) { 
			e.printStackTrace();
			throw new RuntimeException("Error in rules script execution:", e);
		}
    }

    private String buildCacheKey(RuleEventNameType rulesEventNameType, boolean testMode) {
        String siteName = tangerineUserHelper.lookupUserSiteName();
        siteName = (siteName == null ? "DEFAULT" : siteName);

        StringBuilder builder = new StringBuilder(siteName);
        builder.append(".").append(rulesEventNameType.getType());
        builder.append(".").append(testMode);
        return builder.toString();
    }

    
    private String readRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode) {

		String key = buildCacheKey(rulesEventNameType, testMode);
		RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	return rgc == null ? "" : rgc.getGeneratedCodeText();
    	
    }

    private Class compileScript(String script) {
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class groovyClass = loader.parseClass("class RuleRunner{ void run(Map map) { "+script+" } }", "rules.groovy");
		return groovyClass;
    }
  
    private String generateCode(RuleEventNameType rulesEventType, boolean testMode) {
    	
    	StringBuilder script = new StringBuilder();
    	String result = "";
    	
    	try {
    		
			script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"================================================\");\n"); 
			script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"Rule Event ["+rulesEventType.getType()+"]\");\n"); 
			script.append("boolean b;\n"); 
			script.append("boolean lastb;\n\n"); 
			script.append("long t0;\n\n"); 

    		List<Rule> rules = ruleDao.readByRuleEventTypeNameId(rulesEventType.getType());
	    	for (Rule rule: rules) {
	    		if (rule.getRuleIsActive() && rule.getRuleVersions().size() > 0) {
	    			
	    			// Rule script
	    			String desc = whiteList(rule.getRuleDesc());
    				script.append("// ").append(desc).append("\n");
    				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"------------------------------------------------\");\n"); 
    				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"Rule: \\\""+desc+"\\\"\");\n"); 
    				script.append("t0 = System.currentTimeMillis();\n"); 

    				List<String> conditions = new ArrayList<String>();
	    			List<String> conditionstext = new ArrayList<String>();
	    			List<String> consequences = new ArrayList<String>();
	    			List<String> consequencestext = new ArrayList<String>();
	    			
	    			// Replacement parms
	    			RuleVersion ruleVersion = rule.getRuleVersions().get(rule.getRuleVersions().size()-1);
	    			List<RuleSegment> ruleSegments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(ruleVersion.getId());
	    			for (RuleSegment ruleSegment : ruleSegments) {
	    				
	    				RuleSegmentType ruleSegmentType = ruleSegmentTypeDao.readRuleSegmentTypeById(ruleSegment.getRuleSegmentTypeId());
	    				if (ruleSegmentType == null) throw new RuntimeException("Invalid rule segment type for rule: "+rule.getRuleDesc());
	    				
	    				String code = replaceParms(ruleSegmentType.getRuleSegmentTypeText(), rule, ruleVersion, ruleSegment, ruleSegmentType, true);
	    				String text = replaceParms(ruleSegmentType.getRuleSegmentTypePhrase(), rule, ruleVersion, ruleSegment, ruleSegmentType, false);
	    				if (RuleSegmentType.CONDITION_TYPE.equals(ruleSegmentType.getRuleSegmentTypeType())) {
	    					if (code.trim().contains(";")) throw new RuntimeException("Condition cannot contain ';' : "+code);
	    					conditions.add(code);
	    					conditionstext.add(text);
	    				} else {
	    					consequences.add(code);
	    					consequencestext.add(text);
	    				}
	    				
	    			}
  				
    				// Conditions
    				script.append("b = true;\n"); 
    				script.append("lastb = true;\n"); 
    				for (int i = 0; i < conditions.size();i++) {
        				script.append("b = b && (" + conditions.get(i) + ");\n"); 
        				script.append("if (lastb) map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"   Condition: "+conditionstext.get(i)+" = \" + b);\n"); 
        				script.append("lastb = b;\n"); 
    				}
    				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"---> Rule evaluates to \" + b);\n"); 
    				
    				// Consequences
    				script.append("if (b) { \n");
    				for (int i = 0; i < consequences.size();i++) {
    					if (!testMode) script.append(consequences.get(i)).append("; \n");
        				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"   Consequence: "+consequencestext.get(i)+"\");\n"); 
    				}
    				script.append("} \n");
    				
    				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"Rule time: \" + ((System.currentTimeMillis() - t0)/1000f) + \" sec.\");\n"); 
    				script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"------------------------------------------------\");\n"); 
    				script.append("\n");
	    		}
	    	}
	    	
			script.append("map."+OrangeLeapRuleSession.RULE_EXECUTION_SUMMARY+".add(\"================================================\");\n"); 
	    	
	    	result = script.toString();

	    	// Test compile
	    	compileScript(result); 
	    	logger.debug(result);
    	
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Error in rules script generation: " + (rulesEventType == null ? "Null rules event type" : rulesEventType.getType()) + "\n" + script, e);
    		throw new RuntimeException("Rule is invalid.");
    	}
    	
    	return result;
    	
    }
    
    private String replaceParms(String text, Rule rule, RuleVersion ruleVersion, RuleSegment ruleSegment, RuleSegmentType ruleSegmentType, boolean code) {
    	
    	if (ruleSegment.getRuleSegmentParms().size() < ruleSegmentType.getRuleSegmentTypeParms().size()) {
    		// Allow rule to have extraneous parms as long as it has the minimum number required
    		throw new RuntimeException("Number of defined parameters for rule \"" + rule.getRuleDesc() + "\", \"" + ruleSegmentType.getRuleSegmentTypePhrase() + "\" is not correct.");
    	}
    	
    	String quot;
    	if (code) quot = "\""; else quot = "";
    	
    	for (int i = 0; i < ruleSegmentType.getRuleSegmentTypeParms().size(); i++) {
    		
    		RuleSegmentTypeParm ruleSegmentTypeParm = ruleSegmentType.getRuleSegmentTypeParms().get(i);
    		RuleSegmentParm ruleSegmentParm = ruleSegment.getRuleSegmentParms().get(i);
    		RuleSegmentTypeParmType type = RuleSegmentTypeParmType.valueOf(ruleSegmentTypeParm.getRuleSegmentTypeParmType());
    		
    		if (type.equals(RuleSegmentTypeParmType.STRING) || type.equals(RuleSegmentTypeParmType.PICKLIST) || type.equals(RuleSegmentTypeParmType.SITE_VARIABLE)) {
        		String parmvalue = StringUtils.trimToEmpty(ruleSegmentParm.getRuleSegmentParmStringValue());
        		parmvalue = whiteList(parmvalue); 
    			text = replaceNextParm(text, quot + parmvalue + quot);
    		} else if (type.equals(RuleSegmentTypeParmType.NUMBER)) {
        		BigDecimal parmvalue = ruleSegmentParm.getRuleSegmentParmNumericValue();
    			if (parmvalue != null) text = replaceNextParm(text, parmvalue.toString());
    		} 
    	}
    	return text;
    }
    
    private String replaceNextParm(String text, String value) {
    	int i = text.indexOf("?");
    	if (i == -1) throw new RuntimeException("Invalid number of parameters in rule phrase: " + text);
    	return text.substring(0,i) + value + text.substring(i+1);
    }
    
    // These characters are allowed in groovy script text or picklist value parms - no backslashes, quotes, parens, or semicolons.
    private static String WHITELIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.,-_ <=>:@+/";
    
    private String whiteList(String s) {
    	StringBuilder sb = new StringBuilder();
    	if (s != null) {
	    	for (int i = 0; i < s.length(); i++) {
	    		char c = s.charAt(i);
	    		if (WHITELIST.indexOf(c) > -1) {
	    			sb.append(c);
	    		}
	    	}
    	}
    	return sb.toString();
    }

	@Override
	public void publishEventTypeRules(String ruleEventTypeNameId) {
		boolean testMode = false; // not implemented yet
        RuleEventNameType rulesEventNameType = getRuleEventNameType(ruleEventTypeNameId);
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, testMode);
    	if (rgc == null) {
    		rgc = new RuleGeneratedCode();
    		rgc.setIsTestOnly(testMode);
    		rgc.setRuleEventTypeNameId(rulesEventNameType.getType());
    	}
    	rgc.setGeneratedCodeText(generateCode(rulesEventNameType, testMode));
    	ruleGeneratedCodeDao.maintainRuleGeneratedCode(rgc);
        if (!testMode) incrementVersions(rulesEventNameType);
    }
	
	// Needs to be done after publish rules transaction commits.
	@Override
	public void clearCache() {
        resetGroovyObjectCache();
        cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.RULE_GENERATED_CODE);
	}

	@Override
	public Date getLastPublishedDate(String ruleEventTypeNameId) {
        RuleEventNameType rulesEventNameType = getRuleEventNameType(ruleEventTypeNameId);
        if (rulesEventNameType == null) return null;
    	RuleGeneratedCode rgc = ruleGeneratedCodeDao.readRuleGeneratedCodeByTypeMode(rulesEventNameType, false);
    	return rgc == null ? null : rgc.getUpdateDate();
	}
	
	@Override
	public String getLastPublishedBy(String ruleEventTypeNameId) {
        List<Rule> rules = ruleDao.readByRuleEventTypeNameId(ruleEventTypeNameId);
        if (rules == null || rules.size() == 0) return "";
        List<RuleVersion> versions = rules.get(0).getRuleVersions();
        if (versions == null || versions.size() < 2) return "";
        return versions.get(versions.size()-2).getUpdatedBy();
	}
	
	private static RuleEventNameType getRuleEventNameType(String ruleEventTypeNameId) {
        for (RuleEventNameType r : RuleEventNameType.values()) {
        	if (r.getType().equals(ruleEventTypeNameId)) return r;
        }
        return null;
	}

	@Override
	public void revertRuleToVersion(Long ruleId, Long ruleVersionId) {
		Rule rule = ruleDao.readRuleById(ruleId);
		RuleVersion target = rule.getRuleVersions().get(rule.getRuleVersions().size()-1);
		for (RuleVersion ruleVersion: rule.getRuleVersions()) {
			if (ruleVersion.getId().equals(ruleVersionId)) {
				revertRuleVersion(ruleVersion, target);
			}
		}
	}
	
	private void revertRuleVersion(RuleVersion from, RuleVersion to) {
    	List<RuleSegment> segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(to.getId());
    	for (RuleSegment segment: segments) {
    		ruleSegmentParmDao.deleteSegmentParms(segment.getId());
    		ruleSegmentDao.deleteSegment(segment.getId());
    	}
    	segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(from.getId());
    	for (RuleSegment segment: segments) {
    		copyRuleSegment(segment, to);
    	}
	}

}
