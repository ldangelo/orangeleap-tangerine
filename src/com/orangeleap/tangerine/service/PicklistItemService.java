package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;

public interface PicklistItemService {

	List<Picklist> listPicklists();

	Picklist getPicklist(String picklistNameId);

	Picklist getPicklistById(Long picklistId);

	Picklist maintainPicklist(Picklist picklist);
	
	PicklistItem maintainPicklistItem(PicklistItem picklistItem);

	PicklistItem getPicklistItem(String picklistId, String picklistItemName);

	PicklistItem getPicklistItem(Long picklistItemId);
	
	List<PicklistItem> getPicklistItems(String picklistNameId, String picklistItemName, String description, Boolean showInactive);

}
