package com.orangeleap.tangerine.controller.customField;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.service.customization.CustomFieldMaintenanceService;
import com.orangeleap.tangerine.type.AccessType;

public class CustomFieldController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="customFieldMaintenanceService")
    private CustomFieldMaintenanceService customFieldMaintenanceService;


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

        return super.showForm(request, response, errors, controlModel);
    }
    
	@Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		if (!accessAllowed(request)) return null; 
		
		CustomFieldRequest customFieldRequest = (CustomFieldRequest)command;
		
		String message = "";
		String errormessage = "";
		
		try {
		
			customFieldMaintenanceService.addCustomField(customFieldRequest);

			message = "New custom field successfully created.";

		} catch (Exception e) {
			errormessage = e.getMessage();
			errors.reject(errormessage, errormessage);
		}
        
		ModelAndView mav = super.onSubmit(command, errors);
        mav.setViewName(super.getFormView());
        mav.addObject("message", message);
        mav.addObject("errormessage", errormessage);
		return mav;
		
        
    }
	    
	
}
