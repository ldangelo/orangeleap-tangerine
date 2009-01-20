package com.mpower.service;

import java.util.List;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;


public interface PicklistItemService {

	List<PicklistItem> readPicklistItems(String siteName, String picklistId);

	List<PicklistItem> readPicklistItems(String siteName, String picklistId,
			String startsWith);

	PicklistItem maintainPicklistItem(PicklistItem picklistItem);

	PicklistItem readPicklistItemById(Long id);

	List<PicklistItem> readPicklistItems(String siteName, String picklistId,
			String startsWith, String partialDescription, Boolean inactive);

	List<String> listPicklists(String siteName);

	Picklist readPicklist(String picklistId);
	

}
