package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.EntityType;

public interface PicklistDao {

    public Picklist readPicklistById(String picklistId);

	public Picklist maintainPicklist(Picklist picklist);

	public List<Picklist> listPicklists();

    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType);

    public PicklistItem readPicklistItemById(Long picklistItemId);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);
	
	public PicklistItem readPicklistItemByName(String picklistId, String picklistItemName);
	
}
