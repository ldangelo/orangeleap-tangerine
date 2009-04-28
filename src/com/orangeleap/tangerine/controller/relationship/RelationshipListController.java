package com.orangeleap.tangerine.controller.relationship;

import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

import edu.emory.mathcs.backport.java.util.Collections;

public class RelationshipListController extends SimpleFormController {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

	@Override
    public Object formBackingObject(HttpServletRequest request) throws ServletException {
       return "";
    }

	@SuppressWarnings("unchecked")
	@Override
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
		
		ModelAndView mav = super.showForm(request, response, errors, controlModel);
		
		Long personId = new Long(request.getParameter("personId"));
		Person person = constituentService.readConstituentById(personId);
		
		// Return all field definitions for the constituent maintenance page that are involved in a relationship.
		Map<String, FieldDefinition> fieldDefinitionMap = siteService.readFieldTypes(PageType.person, tangerineUserHelper.lookupUserRoles());
		List<FieldDefinition> fds = new ArrayList<FieldDefinition>();
		Iterator<Map.Entry<String, FieldDefinition>> it = fieldDefinitionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FieldDefinition> me = it.next();
			FieldDefinition fd = me.getValue();
			String fieldDefinitionid = fd.getId();
			List<FieldRelationship> frmaster = fieldDao.readMasterFieldRelationships(fieldDefinitionid);
			List<FieldRelationship> frdetail = fieldDao.readDetailFieldRelationships(fieldDefinitionid);
			if (frmaster.size() > 0 || frdetail.size() > 0) {
				fds.add(fd);
			}
		}
		
		Collections.sort(fds, new Comparator<FieldDefinition>() {
			@Override
			public int compare(FieldDefinition o1, FieldDefinition o2) {
				return o1.getDefaultLabel().compareTo(o2.getDefaultLabel());
			}
		});
		
        mav.addObject("fieldDefinitions", fds);
        mav.addObject("person", person);
        return mav;
    }
	
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        Long personId = new Long(request.getParameter("personId"));
        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        String customize = request.getParameter("customize");

        ModelAndView mav = new ModelAndView("redirect:/relationship.htm?personId="+personId+"&fieldDefinitionId="+fieldDefinitionId);
		return mav;
        
    }	
}
