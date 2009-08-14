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

package com.orangeleap.tangerine.controller.siteSettings;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.SiteOption;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class SiteDefaultsController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "siteService")
    private SiteService siteService;
    
    @Resource(name = "siteDAO")
    private SiteDao siteDao;
    
    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    
    


    @SuppressWarnings("unchecked")
    public static boolean accessAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        return pageAccess.get("/siteSettings.htm") == AccessType.ALLOWED;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {

        if (!accessAllowed(request)) return null;

        ModelAndView mav = super.showForm(request, response, errors, controlModel);
        mav.addObject("map", getEditableEntityDefaultsMap());
        return mav;
    }

    private Map<String, String> getEditableEntityDefaultsMap() {
    	Map<String, String> result = new TreeMap<String, String>();
    	List<EntityDefault> list = getEditableEntityDefaults();
    	for (EntityDefault ed : list) {
    		result.put(getFriendlyName(ed), ed.getDefaultValue());
    	}
    	return result;
    }
    
    private String getFriendlyName(EntityDefault ed) {
    	return getFriendlyName(ed.getEntityType()) + " - " + getFriendlyName(ed.getEntityFieldName());
    }
    
    private String getFriendlyName(String s) {
    	int ind = s.indexOf("[");
    	if (ind > -1) {
    		s = s.substring(ind).replace("[", "").replace("]", "");
    	}
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < s.length();i++) {
    		char c = s.charAt(i);
    		if (Character.isUpperCase(c)) {
    			sb.append(" ");
    		}
    		if (i == 0) c = Character.toUpperCase(c);
			sb.append(c);
    	}
    	return sb.toString();
    }
    
    private List<EntityDefault> getEditableEntityDefaults() {
    	List<EntityDefault> list = siteDao.readEntityDefaults(null);
    	Iterator<EntityDefault> it = list.iterator();
    	while (it.hasNext()) {
    		EntityDefault ed = it.next();
    		if (ed.getConditionExp() != null 
    				|| ed.getDefaultValue().contains(":") 
    				|| !ed.getEntityFieldName().contains("customFieldMap[")) {
    			it.remove();
    		}
    	}
    	return list;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        if (!accessAllowed(request)) return null;

        String message = "";
        String errormessage = "";

        try {

            saveOptions(getMap(request));

            message = "Site defaults updated.";

        } catch (Exception e) {
            e.printStackTrace();
            errormessage = e.getMessage();
            errors.reject(errormessage, errormessage);
        }


        ModelAndView mav = super.onSubmit(command, errors);
        mav.setViewName(super.getFormView());
        mav.addObject("map", getEditableEntityDefaultsMap());
        mav.addObject("message", message);
        mav.addObject("errormessage", errormessage);
        return mav;

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

    private void saveOptions(Map<String, String> map) {
    	
        List<EntityDefault> list = getEditableEntityDefaults();
        for (EntityDefault ed: list) {
        	String friendlyName = getFriendlyName(ed);
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
            	Map.Entry<String, String> me = it.next();
            	if (me.getKey().equals(friendlyName)) {
		        	String value = me.getValue();
		        	if (value.length() != 0) {
		        		ed.setDefaultValue(value);
		            	siteDao.updateEntityDefault(ed);
		        	}
            	}
            }
        }

    }
    


}
