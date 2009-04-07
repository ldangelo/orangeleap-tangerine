package com.orangeleap.tangerine.domain.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.type.EntityType;

public class Picklist extends AbstractCustomizableEntity
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String picklistNameId;
    private String picklistName;
    private String picklistDesc;
    private EntityType entityType;
    private Site site;
    private boolean multiselect;
    private List<PicklistItem> picklistItems;

    public String getPicklistNameId() {
        return picklistNameId;
    }

    public void setPicklistNameId(String picklistNameId) {
        this.picklistNameId = picklistNameId;
    }

    public String getPicklistName() {
        return picklistName;
    }

    public void setPicklistName(String picklistName) {
        this.picklistName = picklistName;
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
    	return picklistItems != null && picklistItems.size() > 0 && picklistItems.get(0).getId() != null;
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

    public void setPicklistItems(List<PicklistItem> picklistItems) {
        this.picklistItems = picklistItems;
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
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

	public void setPicklistDesc(String picklistDesc) {
		this.picklistDesc = picklistDesc;
	}

	public String getPicklistDesc() {
		return picklistDesc;
	}
	
    @Override
    public String getAuditShortDesc() {
    	return getPicklistDesc();
    }

	
	public String toString() {
	   return getPicklistDesc();
	}
}
