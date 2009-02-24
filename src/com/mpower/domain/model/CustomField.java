package com.mpower.domain.model;

import java.io.Serializable;

import com.mpower.domain.GeneratedId;

public class CustomField implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    /**
     * This column should only be 'longtext' for types that need it, which is a good reason to break out this table
     * into separate ones for different entity types and/or field types. 
     */
    private String value;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
