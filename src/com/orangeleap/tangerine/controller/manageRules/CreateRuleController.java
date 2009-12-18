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
import com.orangeleap.tangerine.dao.RuleSegmentDao;
import com.orangeleap.tangerine.dao.RuleVersionDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class CreateRuleController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;
 
    @Resource(name = "ruleVersionDAO")
    private RuleVersionDao ruleVersionDao;

    @Resource(name = "ruleSegmentDAO")
    private RuleSegmentDao ruleSegmentDao;

    @Resource(name = "rulesConfService")
    private RulesConfService rulesConfService;
  
    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    
    @SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
    	if (!ManageRuleEventTypeController.accessAllowed(request)) return null;

    	return super.showForm(request, errors, getSuccessView());
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	return "";
    }
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        
        String ruleEventType = request.getParameter("ruleEventType"); 
        List<Rule> rules = ruleDao.readByRuleEventTypeNameId(ruleEventType, false);
        
        Rule rule = new Rule();
        rule.setRuleIsActive(false);
        rule.setRuleDesc("New Rule");
        rule.setRuleEventTypeNameId(ruleEventType);
        rule.setRuleSeq(new Long(rules.size()));
        rule = ruleDao.maintainRule(rule);
        
        RuleVersion ruleVersion = new RuleVersion();
        ruleVersion.setRuleId(rule.getId());
        ruleVersion.setRuleVersionIsTestOnly(false);
        ruleVersion.setRuleVersionSeq(0L);
        ruleVersion.setUpdatedBy(tangerineUserHelper.lookupUserName());
        ruleVersionDao.maintainRuleVersion(ruleVersion);

        RuleSegment ruleSegment = new RuleSegment();
        List<RuleSegmentType> availableSegmentTypes = rulesConfService.getAvailableRuleSegmentTypes(ruleEventType);
        ruleSegment.setRuleVersionId(ruleVersion.getId());
        ruleSegment.setRuleSegmentSeq(0L);
        ruleSegment.setRuleSegmentTypeId(availableSegmentTypes.get(0).getId());
        ruleSegmentDao.maintainRuleSegment(ruleSegment);
        
        return new ModelAndView(getSuccessView());
    	
    }
    
}
