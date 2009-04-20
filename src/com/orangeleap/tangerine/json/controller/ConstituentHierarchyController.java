package com.orangeleap.tangerine.json.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.relationship.PersonTreeNode;
import com.orangeleap.tangerine.web.common.SortInfo;

/**
 * This controller handles JSON requests for populating
 * the grid of gifts.
 * @version 1.0
 */
@Controller
public class ConstituentHierarchyController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private Map<String,Object> personToMap(Person p) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", p.getId());    
        map.put("text", p.getOrganizationName());    
        return map;

    }

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Resource(name="relationshipService")
    private RelationshipService relationshipService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/constituentHeirarchy.json")
    public ModelMap getTree(HttpServletRequest request, SortInfo sortInfo)  {
    	
        String id = request.getParameter("id");
        String memberPersonId = request.getParameter("memberPersonId");
        String fieldDef = request.getParameter("fieldDef");
        
        fieldDef = getFieldName(fieldDef);

        logger.debug("constituentHeirarchy.json: id="+id+", memberPersonId="+memberPersonId+", fieldDef="+fieldDef);
        
        
    	List<Map> rows = new ArrayList<Map>();

        try {

	        Person person;
	        if (id == null || "".equals(id) || "0".equals(id)) {
	           person = constituentService.readConstituentById(new Long(memberPersonId));
	           if (person == null) return null;
	       	   person = relationshipService.getHeadOfTree(person, fieldDef);
	        } else {
		        person = constituentService.readConstituentById(new Long(id));
	        }
		    if (person == null) return null;
	        	
        	PersonTreeNode node = relationshipService.getTree(person, fieldDef, true, false);
        	for (int i = 0; i < node.getChildren().size(); i++) {
        		rows.add(personToMap(node.getChildren().get(i).getPerson()));
        	}
        	return new ModelMap("rows", rows);
        
        } catch (Exception e) {
        	return null;
        }
    }
    
    private String getFieldName(String fieldDef) {
    	fieldDef = fieldDef.substring(fieldDef.indexOf("[")+1);
    	fieldDef = fieldDef.substring(0,fieldDef.length()-1);
    	return fieldDef;
    }

    
}
