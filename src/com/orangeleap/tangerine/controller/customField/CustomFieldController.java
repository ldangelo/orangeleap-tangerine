package com.orangeleap.tangerine.controller.customField;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.orangeleap.tangerine.type.AccessType;

public class CustomFieldController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    

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
		
		CustomFieldRequest cfr = (CustomFieldRequest)command;
		
		String message = "";
		String errormessage = "";
		
		try {
		
			// TODO move to service method
			
			if (cfr.getConstituentType().equals("organization")) throw new RuntimeException("test");
			
			
			if ("".equals(StringUtils.trimToNull(cfr.getSection()) == null)) {
				// TODO read first section for entity
			}
			
		// INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) 
		// VALUES ('person.customFieldMap[organization.website]', 'person', 'customFieldMap[organization.website]', 'Web Site', 'TEXT', 'organization');

        // INSERT INTO FIELD_VALIDATION (SECTION_NAME, FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('email.info', 'email.emailAddress', 'extensions:isEmail');

		//	INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000001, 'person.customFieldMap[organization.website]', 30080);

			
			// TODO check for duplicate existing field name
			
			// TODO modify clementine views
			
			// TODO flush cache here

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
