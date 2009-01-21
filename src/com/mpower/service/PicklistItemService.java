package com.mpower.service;

import java.util.List;

import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;


public interface PicklistItemService {

	List<Picklist> listPicklists(String siteName);

	PicklistItem maintainPicklistItem(PicklistItem picklistItem);

}
