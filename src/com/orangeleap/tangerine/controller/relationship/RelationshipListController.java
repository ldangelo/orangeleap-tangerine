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
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

import edu.emory.mathcs.backport.java.util.Collections;

public class RelationshipListController extends SimpleFormController {
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

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
		
		String sConstituentId = request.getParameter("constituentId");
		Constituent constituent = null;
		if (sConstituentId != null) {
			Long constituentId = new Long(sConstituentId);
			constituent = constituentService.readConstituentById(constituentId);
		}
		
		// Return all field definitions for the constituent maintenance page that are involved in a relationship.
		Map<String, FieldDefinition> fieldDefinitionMap = siteService.readFieldTypes(PageType.constituent, tangerineUserHelper.lookupUserRoles());
		List<FieldDefinition> fds = new ArrayList<FieldDefinition>();
		Iterator<Map.Entry<String, FieldDefinition>> it = fieldDefinitionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FieldDefinition> me = it.next();
			FieldDefinition fd = me.getValue();
			String fieldDefinitionid = fd.getId();
			List<FieldRelationship> frmaster = fieldDao.readMasterFieldRelationships(fieldDefinitionid);
			List<FieldRelationship> frdetail = fieldDao.readDetailFieldRelationships(fieldDefinitionid);
			if (frmaster.size() > 0 || (sConstituentId != null && frdetail.size() > 0)) {
				fds.add(fd);
			}
		}
		
// Support for editing date ranges on these two non-relationship custom fields since they may affect the visibility of other relationship custom fields.
/*
		FieldDefinition fd1 = fieldDao.readFieldDefinition("constituent.customFieldMap[constituentIndividualRoles]");
		fd1.setDefaultLabel(fd1.getDefaultLabel() + " (Individual)");
		fds.add(fd1);
		FieldDefinition fd2 = fieldDao.readFieldDefinition("constituent.customFieldMap[constituentOrganizationRoles]");
		fd2.setDefaultLabel(fd2.getDefaultLabel() + " (Organization)");
		fds.add(fd2);
*/
		
		Collections.sort(fds, new Comparator<FieldDefinition>() {
			@Override
			public int compare(FieldDefinition o1, FieldDefinition o2) {
				return o1.getDefaultLabel().compareTo(o2.getDefaultLabel());
			}
		});
		
        mav.addObject("fieldDefinitions", fds);
        mav.addObject("constituent", constituent);
        return mav;
    }
	
    @Override
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
        Long constituentId = new Long(request.getParameter("constituentId"));
        String fieldDefinitionId = request.getParameter("fieldDefinitionId");
        String customize = request.getParameter("customize");

        ModelAndView mav = new ModelAndView("redirect:/relationship.htm?constituentId="+constituentId+"&fieldDefinitionId="+fieldDefinitionId);
		return mav;
        
    }	
}
