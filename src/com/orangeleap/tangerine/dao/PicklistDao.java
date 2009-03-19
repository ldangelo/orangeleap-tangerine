package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.EntityType;

public interface PicklistDao {

    public Picklist readPicklistById(Long picklistId);
    
    public Picklist readPicklistByNameId(String picklistNameId);

	public Picklist maintainPicklist(Picklist picklist);

	public List<Picklist> listPicklists();

    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType);

    public PicklistItem readPicklistItemById(Long picklistItemId);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);
	
	public PicklistItem readPicklistItemByName(String picklistNameId, String picklistItemName);
	
}
