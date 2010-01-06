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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.rollup.RollupValueSource;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.service.impl.ReminderServiceImpl;
import com.orangeleap.tangerine.service.rollup.RollupHelperService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

public class ScheduleEditFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
	@Resource(name = "rollupHelperService")
	private RollupHelperService rollupHelperService;

    @Resource(name="scheduledItemService")
    protected ScheduledItemService scheduledItemService;

    @Resource(name="recurringGiftService")
    protected RecurringGiftService recurringGiftService;

    @Resource(name="pledgeService")
    protected PledgeService pledgeService;

    @Resource(name="constituentService")
    protected ConstituentService constituentService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
       return "";
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        String sourceEntity = request.getParameter("sourceEntity");
        String sourceEntityId = request.getParameter("sourceEntityId");
    	if (sourceEntityId != null) { 
        
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
	        
	        request.setAttribute("scheduledItems", getScheduledItems(schedulable, request));
	        request.setAttribute("sourceEntity", sourceEntity);
	        request.setAttribute("sourceEntityId", sourceEntityId);
            request.setAttribute(StringConstants.CONSTITUENT, constituentService.readConstituentById(new Long(request.getParameter(StringConstants.CONSTITUENT_ID))));
    	}
    	
        return super.showForm(request, response, errors, controlModel);
    }
    
    private List<ScheduledItem> getScheduledItems(Schedulable schedulable, HttpServletRequest request) {
    	List<ScheduledItem> scheduledItems = scheduledItemService.readSchedule(schedulable);
    	if (allCompleted(scheduledItems)) {
    		scheduledItems.add(getScheduledItem(request));
    	}
    	return scheduledItems;
    }
    
    private boolean allCompleted(List<ScheduledItem> scheduledItems) {
    	for (ScheduledItem scheduledItem: scheduledItems) {
    		if (!scheduledItem.isCompleted()) return false;
    	}
    	return true;
    }
    
    private ScheduledItem getScheduledItem(HttpServletRequest request) {
    	
    	ScheduledItem item =  new ScheduledItem();
    	
    	item.setSourceEntity(request.getParameter("sourceEntity"));
    	item.setSourceEntityId(new Long(request.getParameter("sourceEntityId")));
    	item.setId(getLong(request.getParameter("id")));
    	item.setActualScheduledDate(getDate(request.getParameter("actualScheduledDate")));
    	item.setScheduledItemAmount(getBigDecimal(request.getParameter("scheduledItemAmount")));
    	
    	if ( "scheduleditem".equals(request.getParameter("sourceEntity")) ) {
    		item.setScheduledItemType(ReminderServiceImpl.REMINDER); // these are manually created reminders.  if adding a new ScheduledItemType need to add type parameter to page. 
    	}
    	
    	return item;

    }
    
    private Long getLong(String s) {
    	try {
    		return new Long(s);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    private Date getDate(String s) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
    		return sdf.parse(s);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    private BigDecimal getBigDecimal(String s) {
    	try {
    		return new BigDecimal(s);
    	} catch (Exception e) {
    		return new BigDecimal("0.00");
    	}
    }
    
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
        String action = request.getParameter("action");
        boolean save = "save".equals(action);
        boolean add = "add".equals(action);
        boolean delete = "delete".equals(action);
        final String constituentId = request.getParameter(StringConstants.CONSTITUENT_ID);

    	ScheduledItem newScheduledItem = getScheduledItem(request);
    	ScheduledItem originalScheduledItem = scheduledItemService.readScheduledItemById(newScheduledItem.getId());
    	
    	if (delete) {
    		
	        if (originalScheduledItem != null) scheduledItemService.deleteScheduledItem(originalScheduledItem);
	        
    	} else {
    	
	        if (originalScheduledItem == null) {
	        	originalScheduledItem = newScheduledItem;
	        } else {  		
	        	copyUpdatableFields(newScheduledItem, originalScheduledItem);
	        }
	        
	        if (add) {
	        	originalScheduledItem.setId(null);
	        	originalScheduledItem.setOriginalScheduledDate(null);
	        } else {
	        	if (originalScheduledItem.getOriginalScheduledDate() == null) originalScheduledItem.setOriginalScheduledDate(originalScheduledItem.getActualScheduledDate());
	        }
	        
	        
	        scheduledItemService.maintainScheduledItem(originalScheduledItem);
	 
	        // Update recurring gift/pledge etc summary for constituent.
	        rollupHelperService.updateRollupsForConstituentRollupValueSource(new RollupValueSource() {
				@Override
				public Constituent getConstituent() {
					return null;
				}
				@Override
				public Long getConstituentId() {
					return new Long(constituentId);
				}
	        });
        
    	}

        return new ModelAndView("redirect:/scheduleEdit.htm?sourceEntity="+originalScheduledItem.getSourceEntity()+"&sourceEntityId"+originalScheduledItem.getSourceEntityId() +
                "&constituentId=" + constituentId);
    }
    
    private void copyUpdatableFields(ScheduledItem newScheduledItem, ScheduledItem originalScheduledItem) {
        // Copy over updated fields.
        originalScheduledItem.setActualScheduledDate(newScheduledItem.getActualScheduledDate());
        originalScheduledItem.setScheduledItemAmount(newScheduledItem.getScheduledItemAmount());
        
        // Copy over any custom fields 
        Iterator<Map.Entry<String, CustomField>> it = newScheduledItem.getCustomFieldMap().entrySet().iterator();
        while (it.hasNext()) {
        	Map.Entry<String, CustomField> me = it.next();
            originalScheduledItem.setCustomFieldValue(me.getKey(), me.getValue().getValue());
        }
    }
    
	
}
