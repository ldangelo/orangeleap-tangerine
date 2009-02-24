package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.Auditable;
import com.mpower.domain.GeneratedId;
import com.mpower.domain.Inactivatible;
import com.mpower.domain.Person;

public class Code implements GeneratedId, Auditable, Inactivatible, Serializable 
{

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long codeTypeId;

    private String value;

    private boolean inactive = false;

    private String description;

    private Auditable originalObject;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValue() {
        return (this.value != null && this.description != null ? (new StringBuilder().append(this.value).append(" - ").append(this.description).toString()) : this.id.toString());
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Auditable getOriginalObject() {
        return originalObject;
    }

    public void setOriginalObject(Auditable originalObject) {
        this.originalObject = originalObject;
    }

    public Long getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeType(Long codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    @Override
    public Person getPerson() {
        return null;
    }

}
