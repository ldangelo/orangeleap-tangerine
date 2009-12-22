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

import java.math.BigDecimal;
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
import com.orangeleap.tangerine.dao.RuleSegmentParmDao;
import com.orangeleap.tangerine.dao.RuleSegmentTypeDao;
import com.orangeleap.tangerine.domain.customization.rule.Rule;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegment;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentParm;
import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.domain.customization.rule.RuleVersion;
import com.orangeleap.tangerine.service.customization.RulesConfService;
import com.orangeleap.tangerine.type.RuleEventNameType;
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

    @Resource(name = "ruleSegmentParmDAO")
    private RuleSegmentParmDao ruleSegmentParmDao;

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
        	
        List<RuleSegmentType> availableSegmentTypes = rulesConfService.getAvailableRuleSegmentTypes(ruleEventTypeName);
        
        ModelAndView mav = new ModelAndView(getSuccessView());
        
        mav.addObject("id", id);
        mav.addObject("ruleEventType", ruleEventTypeName);
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
    private static final String ADD = "add";
    private static final String CHANGE_PARM = "changeparm";
    private static final String CHANGE_RULE_SEGMENT_TYPE = "changeRuleSegmentType";
    
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        if (!ManageRuleEventTypeController.accessAllowed(request)) return null;

        String ruleEventTypeName = request.getParameter("ruleEventType"); 
        RuleEventNameType rulesEventNameType = RuleEventNameType.valueOf(ruleEventTypeName);
        Long id = new Long(request.getParameter("id")); 
        Long ruleSegmentId = new Long(request.getParameter("ruleSegmentId")); 
        String ruleSegmentType = request.getParameter("ruleSegmentType"); 

        String action = ""+request.getParameter("action"); 
        boolean moveUp = MOVE_UP.equals(action);
        boolean add = ADD.equals(action);
        boolean changeParm = action.startsWith(CHANGE_PARM);
        boolean changeRuleSegmentType = CHANGE_RULE_SEGMENT_TYPE.equals(action);
        
        Rule rule = ruleDao.readRuleById(id);
        RuleVersion ruleVersion = rule.getRuleVersions().get(0);
        List<RuleSegment> segments = ruleSegmentDao.readRuleSegmentsByRuleVersionId(ruleVersion.getId());
        long maxSeq = segments.get(segments.size()-1).getRuleSegmentSeq();
        
        RuleSegment ruleSegment = getRuleSegment(ruleSegmentId, segments);
        
        if (moveUp) {
        	for (int i = 0; i < segments.size();i++) {
        		RuleSegment asegment = segments.get(i);
        		if (ruleSegment.equals(asegment) && i > 0) {
        			RuleSegment lastsegment = segments.get(i-1);
        			Long seq = lastsegment.getRuleSegmentSeq();
        			lastsegment.setRuleSegmentSeq(ruleSegment.getRuleSegmentSeq());
        			ruleSegment.setRuleSegmentSeq(seq);
                    ruleSegmentDao.maintainRuleSegment(lastsegment);
                    ruleSegmentDao.maintainRuleSegment(ruleSegment);
        			break;
        		}
        	}
        } else if (add) {
        	
            List<RuleSegmentType> availableSegmentTypes = rulesConfService.getAvailableRuleSegmentTypes(ruleEventTypeName);
        	ruleSegment = new RuleSegment();
            ruleSegment.setRuleVersionId(ruleVersion.getId());
            ruleSegment.setRuleSegmentSeq(maxSeq + 1L);
            ruleSegment.setRuleSegmentTypeId(availableSegmentTypes.get(0).getId());
            ruleSegmentDao.maintainRuleSegment(ruleSegment);
            
        } else if (changeParm) {
        	
        	int index = new Integer(action.substring(action.indexOf('-')+1));
        	RuleSegmentParm ruleSegmentParm = null;
        	if (index < ruleSegment.getRuleSegmentParms().size()) ruleSegmentParm = ruleSegment.getRuleSegmentParms().get(index);
        	while (index >= ruleSegment.getRuleSegmentParms().size()) {
        		ruleSegmentParm = new RuleSegmentParm();
        		ruleSegmentParm.setRuleSegmentId(ruleSegment.getId());
        		ruleSegmentParm.setRuleSegmentParmSeq(new Long(ruleSegment.getRuleSegmentParms().size()));
        		ruleSegmentParm = ruleSegmentParmDao.maintainRuleSegmentParm(ruleSegmentParm);
        		ruleSegment.getRuleSegmentParms().add(ruleSegmentParm);
        	}
            
        	String value = ""+request.getParameter("parm"+index);
        	value = value.replace("\"", "").replace("\'", "").replace("\\", ""); // Important: remove quotes and escape chars
        	
        	
        	ruleSegmentParm.setRuleSegmentParmStringValue(value);
        	try {
        		ruleSegmentParm.setRuleSegmentParmNumericValue(new BigDecimal(value));
        	} catch (Exception e) {
        	}
        	
        	ruleSegmentParmDao.maintainRuleSegmentParm(ruleSegmentParm);
        	
        } else if (changeRuleSegmentType) {
        	
        	ruleSegment.setRuleSegmentTypeId(new Long(ruleSegmentType));
            ruleSegmentDao.maintainRuleSegment(ruleSegment);
            
        }
        
        // need to only do if there are some conditions or will give compile err
        //rulesConfService.generateRulesEventScript(rulesEventNameType, false);
        
        return getModelAndView(request);

    }
    
    private RuleSegment getRuleSegment(Long ruleSegmentId, List<RuleSegment> segments) {
    	 RuleSegment ruleSegment = null;
         for (RuleSegment segment: segments) {
         	if (segment.getId().equals(ruleSegmentId)) {
         		ruleSegment = segment;
         	}
         }
         return ruleSegment;
    }
    
    
}
