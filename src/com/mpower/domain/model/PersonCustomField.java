package com.mpower.domain.model;

import java.io.Serializable;

public class PersonCustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long personId;
    private CustomField customField;

    public PersonCustomField() { }

    public PersonCustomField(Long personId, CustomField customField) {
        this.personId = personId;
        this.customField = customField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }
}
