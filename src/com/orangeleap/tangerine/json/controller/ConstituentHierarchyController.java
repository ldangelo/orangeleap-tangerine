package com.orangeleap.tangerine.json.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.relationship.ConstituentTreeNode;

/**
 * This controller handles JSON requests for populating
 * the grid of gifts.
 * @version 1.0
 */
@Controller
public class ConstituentHierarchyController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @SuppressWarnings("unchecked")
	private ModelMap constituentToMap(Constituent p) {

        ModelMap map = new ModelMap();
        map.put("id", p.getId());    
        map.put("text", p.getOrganizationName());    
        map.put("leaf", false);    
        return map;

    }

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Resource(name="relationshipService")
    private RelationshipService relationshipService;

    @Resource(name="siteService")
    private SiteService siteService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/constituentHeirarchy.json")
    public ModelMap getTree(HttpServletRequest request)  {

    	String id = request.getParameter("node");
    	String memberConstituentId = request.getParameter("memberConstituentId");
    	String fieldDef = request.getParameter("fieldDef");

    	fieldDef = getFieldName(fieldDef);

    	logger.debug("constituentHeirarchy.json: id="+id+", memberConstituentId="+memberConstituentId+", fieldDef="+fieldDef);


    	List<Map> rows = new ArrayList<Map>();

    	try {

    		Constituent constituent;
    		if (id == null || "".equals(id) || "0".equals(id)) {
    			constituent = constituentService.readConstituentById(new Long(memberConstituentId));
    			if (constituent == null) return null;
    			populateMaps(constituent);
    			Constituent head = relationshipService.getHeadOfTree(constituent, fieldDef);
    			rows.add(constituentToMap(head));
    		} else {
    			constituent = constituentService.readConstituentById(new Long(id));
    			if (constituent == null) return null;
    			populateMaps(constituent);
    			ConstituentTreeNode node = relationshipService.getTree(constituent, fieldDef, true, false);
    			for (int i = 0; i < node.getChildren().size(); i++) {
    				rows.add(constituentToMap(node.getChildren().get(i).getConstituent()));
    			}
    		}

    		ModelMap map = new ModelMap();
    		map.put("_root", rows);
    		return map;

    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e);
    		return null;
    	}
    }

    private void populateMaps(Constituent constituent) {
    	siteService.populateDefaultEntityEditorMaps(constituent);
    }
    
    private String getFieldName(String fieldDef) {
    	if (fieldDef == null || fieldDef.length() == 0) return "";
    	fieldDef = fieldDef.substring(fieldDef.indexOf("[")+1);
    	fieldDef = fieldDef.substring(0,fieldDef.indexOf("]"));
    	return fieldDef;
    }

    
}
