package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupAttribute implements GeneratedId, Serializable, Comparable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String attributeNameId;
    private String attributeDesc;
    private String rollupEntityType;
    private String rollupStatType;
    private String fieldName;
    private String customFieldName;
    private String siteName;
	
    public RollupAttribute() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAttributeNameId() {
        return attributeNameId;
    }

    public void setAttributeNameId(String attributeNameId) {
        this.attributeNameId = attributeNameId;
    }

    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
        .append(attributeNameId, a.getAttributeNameId())
        .append(attributeDesc, a.getAttributeDesc())
        .append(rollupEntityType, a.getRollupEntityType())
        .append(rollupStatType, a.getRollupStatType())
        .append(fieldName, a.getFieldName())
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
        .append(""+attributeNameId)
        .append(""+attributeDesc)
        .append(""+rollupEntityType)
        .append(""+rollupStatType)
        .append(""+fieldName)
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
        .append("attributeNameId", ""+attributeNameId)
        .append("attributeDesc", ""+attributeDesc)
        .append("rollupEntityType", ""+rollupEntityType)
        .append("rollupStatType", ""+rollupStatType)
        .append("fieldName", ""+fieldName)
        .append("customFieldName", ""+customFieldName)
        .append("siteName", ""+siteName)
        .toString();
    }


	@Override
	public int compareTo(Object o) {
		return this.getAttributeDesc().compareTo(((RollupAttribute)o).getAttributeDesc());
	}


}