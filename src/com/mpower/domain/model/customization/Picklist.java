package com.mpower.domain.model.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.Site;
import com.mpower.type.EntityType;

public class Picklist implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(getClass());

    private String id;

    private String picklistName;

    private EntityType entityType;

    private Site site;

    private Boolean multiselect;

    private List<PicklistItem> picklistItems;

    public String getId() {
        return id;
    }

    public String getPicklistName() {
        return picklistName;
    }

    public String getPicklistViewName() {
    	String result = picklistName;
    	if (result == null) {
            result = "";
        }
    	int i = result.indexOf("[");
   		if (i > -1) {
   			result = result.substring(i+1);
   			result = result.substring(0, result.length()-1);
   		}
   		result = toSeparateWords(result);
   		result = toInitialUpperCase(result);
        return result;
    }

    private String toSeparateWords(String s) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
    		if (c == Character.toUpperCase(c)) {
                sb.append(" ");
            }
    		sb.append(c);
    	}
    	return sb.toString();
    }

    private String toInitialUpperCase(String s) {
   		s = s.replace(".", " ");
    	StringBuilder sb = new StringBuilder();
    	boolean initial = true;
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
    		if (initial) {
                c = Character.toUpperCase(c);
            }
    		initial = (c == ' ');
    		sb.append(c);
    	}
    	return sb.toString();
    }

    public boolean isPersisted() {
    	return picklistItems != null && picklistItems.size() > 0 && picklistItems.get(0).getId() > 0;
    }

    public List<PicklistItem> getActivePicklistItems() {
    	List<PicklistItem> list = new ArrayList<PicklistItem>();
    	for (PicklistItem item: getPicklistItems()) {
            if (!item.isInactive()) {
                list.add(item);
            }
        }
        return list;
    }

    public List<PicklistItem> getPicklistItems() {
        return picklistItems;
    }

    public Boolean isMultiselect() {
        return multiselect;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMultiselect(Boolean multiselect) {
        this.multiselect = multiselect;
    }

    public void setPicklistName(String picklistName) {
        this.picklistName = picklistName;
    }

    public void setPicklistItems(List<PicklistItem> picklistItems) {
        this.picklistItems = picklistItems;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
