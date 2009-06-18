package com.orangeleap.tangerine.controller.customField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.CustomFieldMaintenanceService;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class CustomFieldController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="customFieldMaintenanceService")
    private CustomFieldMaintenanceService customFieldMaintenanceService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;



	@SuppressWarnings("unchecked")
	public static boolean accessAllowed(HttpServletRequest request) {
		Map<String, AccessType> pageAccess = (Map<String, AccessType>)WebUtils.getSessionAttribute(request, "pageAccess");
		return pageAccess.get("/customField.htm") == AccessType.ALLOWED;
	}

    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new CustomFieldRequest();
    }
	
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
		
    	if (!accessAllowed(request)) return null; 

		ModelAndView mav = super.showForm(request, response, errors, controlModel);
		mav.addObject("fieldDefinitions", getEligibleFieldDefinitions());
		return mav;
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		if (!accessAllowed(request)) return null; 
		
		CustomFieldRequest customFieldRequest = (CustomFieldRequest)command;
		
		String message = "";
		String errormessage = "";
		
		try {
			
			if (StringUtils.trimToNull(customFieldRequest.getEntityType()) == null) throw new RuntimeException("Entity type required.");
			if (!customFieldRequest.getFieldName().matches("^[a-z][a-z0-9]*$")) throw new RuntimeException("Invalid field name.");
			if (StringUtils.trimToNull(customFieldRequest.getLabel()) == null) throw new RuntimeException("Label required.");
		
			customFieldMaintenanceService.addCustomField(customFieldRequest);

			message = "New custom field successfully created.";

		} catch (Exception e) {
			e.printStackTrace();
			errormessage = e.getMessage();
			if (errormessage.contains("Duplicate entry")) errormessage = "Custom Field name is a duplicate of an existing one.";
			errors.reject(errormessage, errormessage);
		}
        
		ModelAndView mav = super.onSubmit(command, errors);
        mav.setViewName(super.getFormView());
        mav.addObject("message", message);
        mav.addObject("errormessage", errormessage);
		mav.addObject("fieldDefinitions", getEligibleFieldDefinitions());
		return mav;
		
    }
	    
    private List<FieldDefinition> getEligibleFieldDefinitions() {
		// Return all field definitions for the constituent maintenance page that are reference types and not involved in a relationship.
		Map<String, FieldDefinition> fieldDefinitionMap = siteService.readFieldTypes(PageType.constituent, tangerineUserHelper.lookupUserRoles());
		List<FieldDefinition> fds = new ArrayList<FieldDefinition>();
		Iterator<Map.Entry<String, FieldDefinition>> it = fieldDefinitionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FieldDefinition> me = it.next();
			FieldDefinition fd = me.getValue();
			if (fd.getFieldType() == FieldType.QUERY_LOOKUP || fd.getFieldType() == FieldType.MULTI_QUERY_LOOKUP) {
				String fieldDefinitionid = fd.getId();
				List<FieldRelationship> frmaster = fieldDao.readMasterFieldRelationships(fieldDefinitionid);
				List<FieldRelationship> frdetail = fieldDao.readDetailFieldRelationships(fieldDefinitionid);
				if (frmaster.size() == 0 && frdetail.size() == 0) {
					fds.add(fd);
				}
			}
		}
		return fds;
    }

}
