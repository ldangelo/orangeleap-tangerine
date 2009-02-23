package com.mpower.domain.model;

import java.io.Serializable;
import java.util.List;

import com.mpower.domain.model.customization.FieldDefinition;

public class QueryLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Site site;
    private FieldDefinition fieldDefinition;
    private String sectionName;
    private String sqlQuery;
    private List<QueryLookupParam> queryLookupParams;

    public Long getId() {
        return id;
    }

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

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
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
}
