package com.mpower.domain.model.customization;

import com.mpower.domain.Auditable;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PicklistItem implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(getClass());

    private Long id;

    private String itemName;

    private String defaultDisplayValue;

    private String referenceValue;

    private String suppressReferenceValue;

    private Integer itemOrder;

    private boolean inactive = false;

    private Picklist picklist;

    private Auditable originalObject;

    public Long getId() {
        return id;
    }

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

	@Override
	public Person getPerson() {
		return null;
	}

	@Override
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
}
