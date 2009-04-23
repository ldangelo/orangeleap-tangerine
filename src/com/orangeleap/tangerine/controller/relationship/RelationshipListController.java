package com.orangeleap.tangerine.controller.relationship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class RelationshipListController extends ParameterizableViewController {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

	@Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Return all field definitions for the person maint. page that are involved in a relationship.
		Map<String, FieldDefinition> fieldDefinitionMap = siteService.readFieldTypes(PageType.person, tangerineUserHelper.lookupUserRoles());
		List<FieldRelationship> frs = new ArrayList<FieldRelationship>();
		Iterator<Map.Entry<String, FieldDefinition>> it = fieldDefinitionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FieldDefinition> me = it.next();
			String fieldDefinitionid = me.getValue().getId();
			List<FieldRelationship> frmaster = fieldDao.readMasterFieldRelationships(fieldDefinitionid);
			List<FieldRelationship> frdetail = fieldDao.readDetailFieldRelationships(fieldDefinitionid);
			if (frmaster.size() > 0) {
				frs.add(frmaster.get(0));
			} else if (frdetail.size() > 0) {
				frs.add(frdetail.get(0));
			}
		}
		
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("fieldRelationships", frs);
        return mav;
    }
}
