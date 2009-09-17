package com.orangeleap.tangerine.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

public class EntitySearch implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String entityType;
    private Long entityId;
    private String siteName;
    private String searchText;
	
    public EntitySearch() { }

    
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    
    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntitySearch)) {
            return false;
        }
        EntitySearch a = (EntitySearch) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(entityType, a.getEntityType())
        .append(entityId, a.getEntityId())
        .append(siteName, a.getSiteName())
        .append(searchText, a.getSearchText())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+entityType)
        .append(""+entityId)
        .append(""+siteName)
        .append(""+searchText)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("entityType", ""+entityType)
        .append("entityId", ""+entityId)
        .append("siteName", ""+siteName)
        .append("searchText", ""+searchText)
        .toString();
    }

}