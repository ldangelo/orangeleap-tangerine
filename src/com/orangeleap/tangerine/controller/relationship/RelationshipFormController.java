package com.orangeleap.tangerine.controller.relationship;

import java.util.Iterator;
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

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
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
    
    
	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
        Long personId = new Long(request.getParameter("personId"));
		Person person = constituentService.readConstituentById(personId);
		if (person == null) return null;

        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
        String fieldname = fieldDefinition.getCustomFieldName();
        String fieldlabel = fieldDefinition.getDefaultLabel();
        
        List<CustomField> list = constituentCustomFieldRelationshipService.readAllCustomFieldsByConstituent(new Long(personId));
        Iterator<CustomField> it = list.iterator();
        while (it.hasNext()) {
        	CustomField cf = it.next();
        	boolean valid = cf.getName().equals(fieldname);
        	if (!valid) it.remove();
        }
        
        if (list.size() == 0) {
        	CustomField cf = new CustomField();
        	cf.setName(fieldname);
        	cf.setEntityId(person.getId());
        	cf.setEntityType("person");
        	list.add(cf);
        }
        
        RelationshipForm form = new RelationshipForm();
        form.setCustomFieldList(list);
        form.setPerson(person);
        form.setFieldDefinition(fieldDefinition);
        form.setFieldLabel(fieldlabel);
        
        ModelAndView mav = new ModelAndView(super.getSuccessView());
        mav.addObject("form", form);
        return mav;
        
    }

    
	@Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
       return "";
    }

    @Override
    // Validate date ranges don't overlap if a single-valued custom field, modify custom field values, and maintain other side of relationships.
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
    	
    	// Passed-in blank values are the same as deletions
    	
    	// TODO validate all date ranges, modify custom field values, and maintain other side.
    	
        ModelAndView mav = new ModelAndView(getSuccessView());
        return mav;
        
    }
    
}
