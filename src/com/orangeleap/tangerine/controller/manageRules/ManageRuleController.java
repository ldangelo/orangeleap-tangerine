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

package com.orangeleap.tangerine.controller.manageRules;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.dao.RuleDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.util.OLLogger;

public class ManageRuleController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "rulesConfService")
    private RulesConfService rulesConfService;
    
    private static final String MOVEUP = "moveup";
    private static final String COPY = "copy";

    @SuppressWarnings("unchecked")
	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        
        String ruleEventType = request.getParameter("ruleEventType"); 
        String action = request.getParameter("action"); 
        String id = request.getParameter("id"); 
        
        if (MOVEUP.equals(action)) {
        	Long ruleId = new Long(id);
        	List<Rule> rules = getSelectionList(request);
        	
        	for (int i = 0; i < rules.size(); i++) {
        		Rule rule = rules.get(i);
        		if (i > 0 && rule.getId().equals(ruleId)) {
        			Long lastseq = rules.get(i-1).getRuleSeq();
        			rules.get(i-1).setRuleSeq(rule.getRuleSeq());
        			rule.setRuleSeq(lastseq);
        			ruleDao.maintainRule(rules.get(i-1));
        			ruleDao.maintainRule(rule);
        		}
        	}
        } else if (COPY.equals(action)) {
        	Long ruleId = new Long(id);
        	List<Rule> rules = getSelectionList(request);
        	
        	for (int i = 0; i < rules.size(); i++) {
        		Rule rule = rules.get(i);
        		if (rule.getId().equals(ruleId)) {
        			Long lastseq = rules.get(rules.size()-1).getRuleSeq();
        			rulesConfService.cloneRule(rule, lastseq + 1);
        			break;
        		}
        	}
        }
 
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("ruleEventType", ruleEventType);
        mav.addObject("rules", getSelectionList(request));
        return mav;

    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    private List<Rule> getSelectionList(HttpServletRequest request) {
    	String ruleEventType = request.getParameter("ruleEventType");
    	if (ruleEventType == null || ruleEventType.length() == 0) ruleEventType = "";
        return ruleDao.readByRuleEventTypeNameId(ruleEventType);
    }
    
}
