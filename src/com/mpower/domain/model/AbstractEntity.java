package com.mpower.domain.model;

import com.mpower.domain.model.customization.FieldDefinition;

import java.util.Map;
import java.io.Serializable;

/**
 * Base class for all Entities. It provides base functionality
 * for keeping track of the ID value and returning the type.
 * @version 1.0
 */
public abstract class AbstractEntity implements Serializable {

    protected Long id = null;
    protected Map<String, String> fieldLabelMap = null;
    protected Map<String, Object> fieldValueMap = null;
    protected Map<String, FieldDefinition> fieldTypeMap = null;

    /**
     * Returns the ID, or null if the ID has not been set
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID to the specified non-negative Long value.
     * If the id is negative or null, the internal value will
     * not be changed.
     * @param id the ID of the Entity
     */
    public void setId(Long id) {
        if(id != null && id > 0) this.id = id;
    }

    /**
     * Returns the type of the Entity. This is used in persisting
     * associated fields and elements. The default behavior is to
     * return the lowercase version of this class' simple name.
     * Subclasses should override this if more fine-grained typing
     * is required.
     * @return the type of this Entity
     */
    public String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    /**
     * Returns the field label map for this entity, or null
     * if the field label maps have not been set. The returned
     * Map is a reference to the internal representation, so modifying
     * the entries in the Map will change the underlying values.
     * @return a Map<String,String> of field labels
     */
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    /**
     * Sets the field label map to passed map. This will overwrite
     * any existing field label map, so it is recommended to only
     * call this method one for a given entity instance.
     * @param fieldLabelMap the Map<String,Sring> of field labels
     */
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    /**
     * Returns a map of the field values, or null if the field value
     * map has not been set yet. The returned Map is a reference to
     * the internal representation, so modifying the entries in the
     * Map will change the underlying values.
     * @return a Map<String,Object> of field values
     */
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    /**
     * Sets the field value map to passed map. This will overwrite
     * any existing field value map, so it is recommended to only
     * call this method one for a given entity instance.
     * @param fieldValueMap the Map<String,Sring> of field values
     */
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    /**
     * Returns a map of the field definitions, or null if the field
     * definition map has not been set yet. The returned Map is a
     * reference to the internal representation, so modifying the
     * entriees in the Map will change the underlying values.
     * @return a Map<String,FieldDefinition> of field types
     */
    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
    }

    /**
     * Sets the field type map to passed map. This will overwrite
     * any existing field value map, so it is recommended to only
     * call this method one for a given entity instance.
     * @param fieldTypeMap the Map<String,FieldDefinition> of field types
     */
    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    /**
     * Perform any required actions that need to happen to this
     * Entity before it is persisted. Subclasses should ensure they
     * call super.prePersist() if they override this method. The
     * default implemenation is a no-op
     */
    public void prePersist() {
        // no-op in the base implementation
    }

    /**
     * Called after this Entity has been ready from the persistence
     * store but before it is handed back to the service layer. useful
     * for setting any transient properites. Subclasses should ensure
     * they call super.postRead() if they override this method. The
     * default implementation is a no-op
     */
    public void postRead() {
        // no-op in base implementation    
    }


}
