package com.orangeleap.tangerine.controller.picklist;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.type.AccessType;

public class PicklistCustomizeBaseController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
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
    	return picklist.getPicklistNameId().endsWith("projectCode");
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
