package com.mpower.domain.model;

import java.io.Serializable;

public class QueryLookupParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long queryLookupId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQueryLookupId() {
        return queryLookupId;
    }

    public void setQueryLookupId(Long queryLookupId) {
        this.queryLookupId = queryLookupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
