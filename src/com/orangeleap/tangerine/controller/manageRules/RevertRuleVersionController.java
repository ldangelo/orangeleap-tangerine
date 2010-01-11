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
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class RevertRuleVersionController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

   
    @Resource(name = "rulesConfService")
    private RulesConfService rulesConfService;
    
    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    
    @SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        return getModelAndView(request);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    private ModelAndView getModelAndView(HttpServletRequest request) {
    	
    	
        Long id = new Long(request.getParameter("id")); 
        String ruleEventTypeName = request.getParameter("ruleEventType"); 
        
        Rule rule = ruleDao.readRuleById(id);
        List<RuleVersion> ruleVersions = rule.getRuleVersions();
        ruleVersions.remove(ruleVersions.size()-1);  // cant copy from current version
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        
        mav.addObject("id", id);
        mav.addObject("ruleEventType", ruleEventTypeName);
        mav.addObject("ruleVersions", ruleVersions);
        
        return mav;
    }
   
    private static final String REVERT_RULE_VERSION = "revertRuleVersion";
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;

        Long id = new Long(request.getParameter("id")); 
        String ruleVersionId = request.getParameter("ruleVersionId"); 

        String action = ""+request.getParameter("action"); 
        boolean revertRuleVersion = REVERT_RULE_VERSION.equals(action);
    
        if (revertRuleVersion) {
        	rulesConfService.revertRuleToVersion(id, new Long(ruleVersionId));
        } 
        
        ModelAndView mav = getModelAndView(request);
       
        return mav;

    }
    
    
}
