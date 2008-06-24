package com.mpower.entity.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mpower.entity.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "PICKLIST_ITEM")
public class PicklistItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "PICKLIST_ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "DEFAULT_DISPLAY_VALUE", nullable = false)
    private String defaultDisplayValue;

    @Column(name = "ITEM_ORDER")
    private Integer itemOrder;

    @ManyToOne
    @JoinColumn(name = "PICKLIST_ID")
    private Picklist picklist;

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

    public String getDefaultDisplayValue() {
        return defaultDisplayValue;
    }

    public void setDefaultDisplayValue(String defaultDisplayValue) {
        this.defaultDisplayValue = defaultDisplayValue;
    }
}
