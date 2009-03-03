package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.customization.Picklist;
import com.mpower.domain.model.customization.PicklistItem;

public interface PicklistItemService {

	List<Picklist> listPicklists();

	Picklist getPicklist(String picklistId);

	PicklistItem maintainPicklistItem(PicklistItem picklistItem);

}
