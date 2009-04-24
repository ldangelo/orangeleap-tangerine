package com.orangeleap.tangerine.controller.relationship;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;
import com.orangeleap.tangerine.service.ConstituentService;

public class RelationshipFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name="constituentCustomFieldRelationshipService")
    protected ConstituentCustomFieldRelationshipService constituentCustomFieldRelationshipService;
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	
        Map returnMap = new HashMap();
        
        Long personId = new Long(request.getParameter("personId"));
		Person person = constituentService.readConstituentById(personId);
		if (person == null) return null;

        String relationshipId = request.getParameter("fieldRelationshipId");
        FieldRelationship fieldRelationship = fieldDao.readFieldRelationship(new Long(relationshipId));
        String masterfieldname = fieldRelationship.getMasterRecordField().getCustomFieldName();
        
        String fieldname = fieldRelationship.getMasterRecordField().getDefaultLabel();
        
        List<CustomField> list = constituentCustomFieldRelationshipService.readAllCustomFieldsByConstituent(new Long(personId));
        // Filter for custom fields involved in this relationship. There should only be either all master or all detail fields.
        Iterator<CustomField> it = list.iterator();
        while (it.hasNext()) {
        	CustomField cf = it.next();
        	boolean valid = cf.getName().equals(masterfieldname);
        	if (!valid) it.remove();
        }
        
        RelationshipForm form = new RelationshipForm();
        form.setRelationshipList(list);
        form.setPerson(person);
        form.setFieldRelationship(fieldRelationship);
        form.setFieldLabel(fieldname);
        
        returnMap.put("form", form);
        
        return returnMap;
    }

    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       return "";
    }

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
    	// Passed-in blank values are the same as deletions
    	
    	// TODO validate all date ranges, modify custom field values, and maintain other side.
    	
        ModelAndView mav = new ModelAndView(getSuccessView());
        return mav;
        
    }
    
}
