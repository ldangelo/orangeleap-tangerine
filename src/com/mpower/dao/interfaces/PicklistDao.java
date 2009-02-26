package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.customization.Picklist;
import com.mpower.domain.model.customization.PicklistItem;
import com.mpower.type.EntityType;

public interface PicklistDao {

    public Picklist readPicklistById(String picklistId);

	public Picklist maintainPicklist(Picklist picklist);

	public List<Picklist> listPicklists();

    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType);

    public PicklistItem readPicklistItemById(Long picklistItemId);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);
}
