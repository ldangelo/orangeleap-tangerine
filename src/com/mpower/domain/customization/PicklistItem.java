package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Auditable;
import com.mpower.domain.Person;
import com.mpower.domain.Site;

@Entity
@Table(name = "PICKLIST_ITEM")
public class PicklistItem implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PICKLIST_ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "DEFAULT_DISPLAY_VALUE")
    private String defaultDisplayValue;

    @Column(name = "REFERENCE_VALUE")
    private String referenceValue;

    @Column(name = "ITEM_ORDER")
    private Integer itemOrder;

    @ManyToOne
    @JoinColumn(name = "PICKLIST_ID")
    private Picklist picklist;
    
    @Transient
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
}
