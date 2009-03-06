package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.model.Auditable;
import com.mpower.domain.model.Person;
import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.Site;

public class PicklistItem implements Auditable, GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String itemName;
    private String defaultDisplayValue;
    private String referenceValue;
    private String suppressReferenceValue;
    private Integer itemOrder;
    private boolean inactive = false;
    private Picklist picklist;
    private Auditable originalObject;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDefaultDisplayValue() {
        return defaultDisplayValue;
    }

    public void setDefaultDisplayValue(String defaultDisplayValue) {
        this.defaultDisplayValue = defaultDisplayValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer position) {
        this.itemOrder = position;
    }

    public Picklist getPicklist() {
        return picklist;
    }

    public void setPicklist(Picklist picklist) {
        this.picklist = picklist;
    }

	public void setOriginalObject(Auditable originalObject) {
		this.originalObject = originalObject;
	}

	public Auditable getOriginalObject() {
		return originalObject;
	}

	public Site getSite() {
		return picklist.getSite();
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setSuppressReferenceValue(String suppressReferenceValue) {
		this.suppressReferenceValue = suppressReferenceValue;
	}

	public String getSuppressReferenceValue() {
		return suppressReferenceValue;
	}

	@Override
	public Person getPerson() {
		return null;
	}
}
