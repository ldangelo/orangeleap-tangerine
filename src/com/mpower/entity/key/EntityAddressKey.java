package com.mpower.entity.key;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EntityAddressKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long entityId;
    private String fieldName;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
