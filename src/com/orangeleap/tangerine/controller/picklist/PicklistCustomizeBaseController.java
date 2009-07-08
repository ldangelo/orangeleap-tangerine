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

package com.orangeleap.tangerine.controller.picklist;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.type.AccessType;

public class PicklistCustomizeBaseController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
	public static final String BLANK = "<blank>";
	public static final String ITEM_TEMPLATE = "item-template-";
	public final static String GL_ACCOUNT_CODE = "GLAccountCode";
	public static final String PARENT_LIST = "parentList";
	public static final String PARENT_VALUE = "parentValue";
	
	

    @Resource(name="picklistItemService")
    protected PicklistItemService picklistItemService;
    
	@SuppressWarnings("unchecked")
	public static boolean picklistEditAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/picklistItems.htm") == AccessType.ALLOWED;
	}

    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }
	
    protected boolean isGLCoded(Picklist picklist) {
    	return picklist.getPicklistNameId().endsWith("projectCode")
            || picklist.getPicklistNameId().endsWith("customFieldMap[bank]");
    }

	protected Map<String, String> getMap(Map<String, CustomField> map) {
		Map<String, String> result = new TreeMap<String, String>();
		for (Map.Entry<String, CustomField> entry : map.entrySet()) {
			result.put(entry.getValue().getName(), entry.getValue().getValue());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> getMap(HttpServletRequest request) {
		Map map = new TreeMap<String, String>();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parm = (String)e.nextElement();
			if (parm.startsWith("cfname")) {
				String fieldnum = parm.substring(6);
				String key = request.getParameter(parm).trim();
				String value = request.getParameter("cfvalue"+fieldnum).trim();
				if (key.length() > 0) map.put(key, value);
			}
		}
		return map;
	}
	
	protected void updateCustomFieldMap(Map<String, String> map, AbstractCustomizableEntity entity) {
		entity.getCustomFieldMap().clear();
		for (Map.Entry<String, String> e : map.entrySet()) {
			entity.setCustomFieldValue(e.getKey(), e.getValue());
		}
	}
	
}
