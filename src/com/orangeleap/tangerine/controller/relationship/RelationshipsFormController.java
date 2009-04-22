package com.orangeleap.tangerine.controller.relationship;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;

public class RelationshipsFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="constituentCustomFieldRelationshipService")
    protected ConstituentCustomFieldRelationshipService constituentCustomFieldRelationshipService;

    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       
        String personId = request.getParameter("personId");
        
        List<ConstituentCustomFieldRelationship> list = constituentCustomFieldRelationshipService.readAllByConstituent(new Long(personId));
        
        RelationshipForm form = new RelationshipForm();
        form.setRelationshipList(list);
        
        return form;
        
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
    	RelationshipForm form = (RelationshipForm) command;
    	List<ConstituentCustomFieldRelationship> list = constituentCustomFieldRelationshipService.maintainConstituentCustomFieldRelationships(form.getRelationshipList());
    	form.setRelationshipList(list);
    	
        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("form", form);
        return mav;
        
    }
    
}
