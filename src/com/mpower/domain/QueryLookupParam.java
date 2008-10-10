package com.mpower.domain;

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

@Entity
@Table(name="QUERY_LOOKUP_PARAM")
public class QueryLookupParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "QUERY_LOOKUP_PARAM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "QUERY_LOOKUP_ID")
    private QueryLookup queryLookup;

    @Column(name = "PARAM_NAME")
    private String name;

    @Column(name = "PARAM_ORDER")
    private Integer paramOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QueryLookup getQueryLookup() {
        return queryLookup;
    }

    public void setQueryLookup(QueryLookup queryLookup) {
        this.queryLookup = queryLookup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParamOrder() {
        return paramOrder;
    }

    public void setParamOrder(Integer paramOrder) {
        this.paramOrder = paramOrder;
    }
}
