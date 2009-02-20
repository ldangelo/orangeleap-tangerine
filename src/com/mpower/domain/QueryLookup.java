package com.mpower.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.customization.FieldDefinition;

@Entity
@Table(name="QUERY_LOOKUP")
public class QueryLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "QUERY_LOOKUP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "FIELD_DEFINITION_ID")
    private FieldDefinition fieldDefinition;
    
    @Column(name = "SECTION_NAME")
    private String sectionName;

    @Column(name = "SQL_QUERY")
    private String sqlQuery;

    @OneToMany(mappedBy = "queryLookup", cascade = CascadeType.ALL)
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
