package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupAttribute implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String attributeDesc;
    private String groupByType;
    private String rollupEntityType;
    private String rollupStatType;
    private String customFieldName;
    private String siteName;
	
    public RollupAttribute() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    public String getGroupByType() {
        return groupByType;
    }

    public void setGroupByType(String groupByType) {
        this.groupByType = groupByType;
    }

    public String getRollupEntityType() {
        return rollupEntityType;
    }

    public void setRollupEntityType(String rollupEntityType) {
        this.rollupEntityType = rollupEntityType;
    }
    
	public String getRollupStatType() {
		return rollupStatType;
	}

	public void setRollupStatType(String rollupStatType) {
		this.rollupStatType = rollupStatType;
	}

    public String getCustomFieldName() {
        return customFieldName;
    }

    public void setCustomFieldName(String customFieldName) {
        this.customFieldName = customFieldName;
    }
	
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RollupAttribute)) {
            return false;
        }
        RollupAttribute a = (RollupAttribute) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(attributeDesc, a.getAttributeDesc())
        .append(groupByType, a.getGroupByType())
        .append(rollupEntityType, a.getRollupEntityType())
        .append(rollupStatType, a.getRollupStatType())
        .append(customFieldName, a.getCustomFieldName())
        .append(siteName, a.getSiteName())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+attributeDesc)
        .append(""+groupByType)
        .append(""+rollupEntityType)
        .append(""+rollupStatType)
        .append(""+customFieldName)
        .append(""+siteName)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("attributeDesc", ""+attributeDesc)
        .append("groupByType", ""+groupByType)
        .append("rollupEntityType", ""+rollupEntityType)
        .append("rollupStatType", ""+rollupStatType)
        .append("customFieldName", ""+customFieldName)
        .append("siteName", ""+siteName)
        .toString();
    }


}