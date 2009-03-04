package com.mpower.domain.model.customization;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import com.mpower.domain.GeneratedId;

/**
 * Implementation of a Custom Field which tracks the Entity it
 * is associated with via the Enitity ID and Type.
 */
public class CustomField implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String entityType;
    private Long entityId;
    private String value;

    public CustomField() {
        super();
    }

    public CustomField(String name) {
        super();
        this.name = name;
    }

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

    public String getEntityType() {
        return entityType;
    }

    /**
     * The type of entity this custom field is associated with
     * @param entityType the entity type
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    /**
     * The Id of the entity this custom field is associated with.
     * The combination of the EntityType and Entity ID are what
     * can be used to determine the association of this custom field.
     * @param entityId the Long ID of the associated entity
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("name", name).append("entityType", entityType).append("entityId", entityId).append("value", value).toString();
    }
}


