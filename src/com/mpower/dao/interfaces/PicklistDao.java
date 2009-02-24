package com.mpower.dao.interfaces;

import com.mpower.domain.customization.*;
import com.mpower.type.EntityType;
import java.util.List;

/**
 * @version 1.0
 */
public interface PicklistDao {

    public Picklist readPicklistById(String picklistId);

	public Picklist maintainPicklist(Picklist picklist);

	public List<Picklist> listPicklists();

    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType);

    public PicklistItem readPicklistItemById(Long picklistItemId);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);
}
