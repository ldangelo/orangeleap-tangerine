package com.mpower.service;

import java.util.List;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;


public interface PicklistItemService {

	List<Picklist> listPicklists(String siteName);

	Picklist getPicklist(String siteName, String picklistId);

	PicklistItem maintainPicklistItem(String siteName, PicklistItem picklistItem);

}
