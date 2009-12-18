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

import java.util.ArrayList;
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
import com.orangeleap.tangerine.dao.RuleEventTypeDao;
import com.orangeleap.tangerine.dao.RuleSegmentDao;
import com.orangeleap.tangerine.dao.RuleSegmentTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventType;
import com.orangeleap.tangerine.domain.customization.rule.RuleEventTypeXRuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class ManageRuleSegmentsController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

   
    @Resource(name = "ruleDAO")
    private RuleDao ruleDao;

    @Resource(name = "ruleSegmentDAO")
    private RuleSegmentDao ruleSegmentDao;

    @Resource(name = "ruleSegmentTypeDAO")
    private RuleSegmentTypeDao ruleSegmentTypeDao;

    @Resource(name = "ruleEventTypeDAO")
    private RuleEventTypeDao ruleEventTypeDao;

    @Resource(name = "rulesConfService")
    private RulesConfService rulesConfService;
    
  
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
        RuleVersion ruleVersion = rule.getRuleVersions().get(0);
        
        List<RuleSegment> segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(ruleVersion.getId());
        List<SegmentView> segmentViews = new ArrayList<SegmentView>();
        for (RuleSegment segment: segments) segmentViews.add(new SegmentView(segment));
        	
        RuleEventType ruleEventType = ruleEventTypeDao.readRuleEventTypeByNameId(ruleEventTypeName);
        List<RuleSegmentType> availableSegmentTypes = new ArrayList<RuleSegmentType>();
        List<RuleEventTypeXRuleSegmentType> rxss = ruleEventType.getRuleEventTypeXRuleSegmentTypes();
        for (RuleEventTypeXRuleSegmentType rxs: rxss) {
        	RuleSegmentType ruleSegmentType = ruleSegmentTypeDao.readRuleSegmentTypeById(rxs.getRuleSegmentTypeId());
        	availableSegmentTypes.add(ruleSegmentType);
        }
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        
        mav.addObject("id", id);
        mav.addObject("ruleEventType", ruleEventType.getRuleEventTypeNameId());
        mav.addObject("ruleSegmentList", segmentViews);
        mav.addObject("ruleSegmentTypes", availableSegmentTypes);
        
        return mav;
    }
    
    public static final class SegmentView {
    	
		private RuleSegment ruleSegment;
    	private String[] parms = new String[]{"","","","",""};

    	public SegmentView(RuleSegment ruleSegment) {
    		setRuleSegment(ruleSegment);
    		for (int i = 0; i < ruleSegment.getRuleSegmentParms().size(); i++) {
    			RuleSegmentParm p = ruleSegment.getRuleSegmentParms().get(i);
    			String value = "";
    			if (p.getRuleSegmentParmNumericValue() != null) {
        			value = p.getRuleSegmentParmNumericValue().toString();
    			} else {
        			value = p.getRuleSegmentParmStringValue();
    			}
    			if (value == null) value = "";
    			getParms()[i] = value;
    		}
    	}

		public void setRuleSegment(RuleSegment ruleSegment) {
			this.ruleSegment = ruleSegment;
		}

		public RuleSegment getRuleSegment() {
			return ruleSegment;
		}

		public void setParms(String[] parms) {
			this.parms = parms;
		}

		public String[] getParms() {
			return parms;
		}

		public Long getId() {
			return ruleSegment.getId();
		}

		public Long getRuleSegmentTypeId() {
			return ruleSegment.getRuleSegmentTypeId();
		}

    }
    
   
    private static final String MOVE_UP = "moveup";
    
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;
        
        String id = request.getParameter("id"); 
        String fieldName = request.getParameter("fieldName"); 
        String action = request.getParameter("action"); 
        boolean moveUp = MOVE_UP.equals(action);
        
        int index = 0;
        if (moveUp && index > 0) {
        	
        }
        
        return new ModelAndView(getSuccessView());

    }
    
    
}
