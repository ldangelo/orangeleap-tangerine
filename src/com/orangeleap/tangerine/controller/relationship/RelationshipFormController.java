package com.orangeleap.tangerine.controller.relationship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.RelationshipService;

import edu.emory.mathcs.backport.java.util.Collections;

public class RelationshipFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;
 
	@SuppressWarnings("unchecked")
	@Override
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
		
		ModelAndView mav = super.showForm(request, response, errors, controlModel);
		
        mav.addObject("form", readForm(request));
        return mav;
        
    }
	
    
	@Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
       return "";
    }
	

    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {

        ModelAndView mav = new ModelAndView(getSuccessView());

		Long personId = new Long(request.getParameter("personId"));
		Person person = constituentService.readConstituentById(personId);
		if (person == null) return null;
		String fieldDefinitionId = request.getParameter("fieldDefinitionId");
		FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
		String fieldName = fieldDefinition.getCustomFieldName();
    	List<CustomField> list = getMap(request, personId, fieldName);

    	try {
    		relationshipService.maintainCustomFieldsByConstituentAndFieldDefinition(person.getId(), fieldDefinitionId, list, new ArrayList<Long>());
    	} catch (Exception e) {
			mav.addObject("message", e.getMessage());
    	}
    	
        mav.addObject("form", readForm(request));
        return mav;
        
    }
    
	private RelationshipForm readForm(HttpServletRequest request) {
		
        Long personId = new Long(request.getParameter("personId"));
		Person person = constituentService.readConstituentById(personId);
		if (person == null) return null;

        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        FieldDefinition fieldDefinition = fieldDao.readFieldDefinition(fieldDefinitionId);
        String fieldname = fieldDefinition.getCustomFieldName();
        String fieldlabel = fieldDefinition.getDefaultLabel();
        
        List<CustomField> list = relationshipService.readCustomFieldsByConstituentAndFieldName(new Long(personId), fieldname);
        
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
        
        return form;
        
	}

	@SuppressWarnings("unchecked")
	private List<CustomField> getMap(HttpServletRequest request, Long personId, String fieldName) {
		List<CustomField> list = new ArrayList<CustomField>();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parm = (String)e.nextElement();
			if (parm.startsWith("cfFieldValue")) {
				String fieldnum = parm.substring("cfFieldValue".length());
				int ifieldnum = Integer.parseInt(fieldnum.replace('[',' ').replace(']',' ').trim());
				CustomField cf = new CustomField();
				cf.setEntityId(personId);
				cf.setEntityType("person");
				cf.setName(fieldName);
				cf.setValue(request.getParameter(parm).trim());
				cf.setSequenceNumber(ifieldnum-1);
				if (cf.getValue().length() > 0) {
					list.add(cf);
					cf.setDisplayStartDate(request.getParameter("cfStartDate"+fieldnum));
					cf.setDisplayEndDate(request.getParameter("cfEndDate"+fieldnum));
				}
			}
		}
		Collections.sort(list, new Comparator<CustomField>() {
			@Override
			public int compare(CustomField o1, CustomField o2) {
				return o1.getSequenceNumber() - o2.getSequenceNumber();
			}
		});
		return list;
	}
	
	

  
    
}
