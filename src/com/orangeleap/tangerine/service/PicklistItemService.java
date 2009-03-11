package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;

public interface PicklistItemService {

	List<Picklist> listPicklists();

	Picklist getPicklist(String picklistId);

	PicklistItem maintainPicklistItem(PicklistItem picklistItem);

}
