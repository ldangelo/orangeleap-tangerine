package com.mpower.domain.entity.customization;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.mpower.domain.entity.Site;
import com.mpower.domain.entity.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "PICKLIST")
public class Picklist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PICKLIST_ID")
    private String id;

    @Column(name = "PICKLIST_NAME")
    private String picklistName;

    @ManyToOne
    @JoinColumn(name = "SITE_ID")
    private Site site;

    @Column(name = "MULTISELECT")
    private Boolean multiselect;

    @OneToMany
    @JoinColumn(name = "PICKLIST_ID")
    @OrderBy("itemOrder")
    private List<PicklistItem> picklistItems;

    public String getId() {
        return id;
    }

    public String getPicklistName() {
        return picklistName;
    }

    public List<PicklistItem> getPicklistItems() {
        return picklistItems;
    }

    public Boolean isMultiselect() {
        return multiselect;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMultiselect(Boolean multiselect) {
        this.multiselect = multiselect;
    }

    public void setPicklistName(String picklistName) {
        this.picklistName = picklistName;
    }

    public void setPicklistItems(List<PicklistItem> picklistItems) {
        this.picklistItems = picklistItems;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
