package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.type.EntityType;

public class QueryLookup implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Site site;
    private FieldDefinition fieldDefinition;
    private String sectionName;
    private EntityType entityType; 
    private String sqlWhere;
    private List<QueryLookupParam> queryLookupParams = new ArrayList<QueryLookupParam>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(FieldDefinition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public String getSqlWhere() {
        return sqlWhere;
    }

    public void setSqlWhere(String sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public List<QueryLookupParam> getQueryLookupParams() {
        return queryLookupParams;
    }

    public void setQueryLookupParams(List<QueryLookupParam> queryLookupParams) {
        this.queryLookupParams = queryLookupParams;
    }

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public EntityType getEntityType() {
		return entityType;
	}
}
