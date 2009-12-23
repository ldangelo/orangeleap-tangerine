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
import com.orangeleap.tangerine.type.RuleEventNameType;
import com.orangeleap.tangerine.util.OLLogger;

public class ManageRuleDescController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "rulesConfService")
    private RulesConfService rulesConfService;
  

    
    @SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        String id = request.getParameter("id"); 
        String ruleEventType = request.getParameter("ruleEventType"); 
        return getModelAndView(new Long(id), ruleEventType);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
	private ModelAndView getModelAndView(Long id, String ruleEventType) {
    	
        Rule rule = ruleDao.readRuleById(id);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("rule", rule);
        mav.addObject("id", id);
        mav.addObject("ruleEventType", ruleEventType);
        
        return mav;
    }
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        
        Long id = new Long(request.getParameter("id")); 
        String ruleEventType = request.getParameter("ruleEventType"); 

        String newDesc = request.getParameter("desc"); 
        String newActive = request.getParameter("active"); 

        Rule rule = ruleDao.readRuleById(id);
        rule.setRuleIsActive("true".equals(newActive));
        rule.setRuleDesc(newDesc);
		ruleDao.maintainRule(rule);
        
        ModelAndView mav = getModelAndView(id, ruleEventType);
        return mav;

    }
    
   
}
