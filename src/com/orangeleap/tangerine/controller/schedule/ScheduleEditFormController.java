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

package com.orangeleap.tangerine.controller.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.TangerineCustomDateEditor;

public class ScheduleEditFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
	

    @Resource(name="scheduledItemService")
    protected ScheduledItemService scheduledItemService;

    @Resource(name="recurringGiftService")
    protected RecurringGiftService recurringGiftService;

    @Resource(name="pledgeService")
    protected PledgeService pledgeService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
       return "";
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        String sourceEntity = request.getParameter("sourceEntity");
        String sourceEntityId = request.getParameter("sourceEntityId");
        
        Schedulable schedulable = null;

        if (sourceEntity.equals("recurringgift")) {
        	schedulable = recurringGiftService.readRecurringGiftById(new Long(sourceEntityId));
        }
        if (sourceEntity.equals("pledge")) {
        	schedulable = pledgeService.readPledgeById(new Long(sourceEntityId));
        }
        if (sourceEntity.equals("scheduleditem")) {
        	schedulable = scheduledItemService.readScheduledItemById(new Long(sourceEntityId));
        }
        
    	List<ScheduledItem> scheduledItems = scheduledItemService.readSchedule(schedulable);
        request.setAttribute("scheduledItems", scheduledItems);
        request.setAttribute("sourceEntity", sourceEntity);
        request.setAttribute("sourceEntityId", sourceEntityId);
        return super.showForm(request, response, errors, controlModel);
    }
    
    private ScheduledItem getScheduledItem(HttpServletRequest request) {
    	
    	ScheduledItem item =  new ScheduledItem();
    	
    	item.setSourceEntity(request.getParameter("sourceEntity"));
    	item.setSourceEntityId(new Long(request.getParameter("sourceEntityId")));
    	item.setId(new Long(request.getParameter("id")));
    	item.setActualScheduledDate(getDate(request.getParameter("actualScheduledDate")));
    	item.setCustomFieldValue("giftAmountOverride", StringUtils.trimToEmpty((request.getParameter("giftAmountOverride"))));
    	
    	return item;

    }
    
    private Date getDate(String s) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
    		return sdf.parse(s);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        String action = request.getParameter("action");

    	ScheduledItem newScheduledItem = getScheduledItem(request);
    	ScheduledItem originalScheduledItem = scheduledItemService.readScheduledItemById(newScheduledItem.getId());
    	
        if (originalScheduledItem == null) {
        
        	originalScheduledItem = newScheduledItem;
        	originalScheduledItem.setOriginalScheduledDate(originalScheduledItem.getActualScheduledDate());

        } else if ("save".equals(action) || "add".equals(action)) {
        
	        // Copy over updated scheduled date.
	        if (newScheduledItem.getActualScheduledDate() != null) originalScheduledItem.setActualScheduledDate(newScheduledItem.getActualScheduledDate());
	        
	        // Copy over custom fields (e.g. giftOverrideAmount)
	        Iterator<Map.Entry<String, CustomField>> it = newScheduledItem.getCustomFieldMap().entrySet().iterator();
	        while (it.hasNext()) {
	        	Map.Entry<String, CustomField> me = it.next();
	            originalScheduledItem.setCustomFieldValue(me.getKey(), me.getValue().getValue());
	        }
	        
	        if ("add".equals(action)) originalScheduledItem.setId(null);
        
        }
        
        if ("delete".equals(action)) {
        	scheduledItemService.deleteScheduledItem(originalScheduledItem);
        } else {
        	// TODO do we need validate there are no duplicate dates in edited schedule or is this allowed?
        	scheduledItemService.maintainScheduledItem(originalScheduledItem);
        }

        ModelAndView mav = new ModelAndView("redirect:/scheduleEdit.htm?sourceEntity="+originalScheduledItem.getSourceEntity()+"&sourceEntityId"+originalScheduledItem.getSourceEntityId());
        return mav;

    }


	
}
