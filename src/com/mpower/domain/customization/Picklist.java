package com.mpower.domain.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Site;
import com.mpower.type.EntityType;

@Entity
@Table(name = "PICKLIST", uniqueConstraints = @UniqueConstraint(columnNames = { "SITE_NAME", "PICKLIST_NAME", "ENTITY_TYPE" }))
public class Picklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @Column(name = "PICKLIST_ID")
    private String id;

    @Column(name = "PICKLIST_NAME")
    private String picklistName;

    @Column(name="ENTITY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    @Column(name = "MULTISELECT")
    private Boolean multiselect;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PICKLIST_ID")
    @OrderBy("itemOrder")
    private List<PicklistItem> picklistItems;

    public String getId() {
        return id;
    }

    public String getPicklistName() {
        return picklistName;
    }

    public List<PicklistItem> getActivePicklistItems() {
    	List<PicklistItem> list = new ArrayList<PicklistItem>();
    	for (PicklistItem item: getPicklistItems()) if (!item.isInactive()) list.add(item);
        return list;
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

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
