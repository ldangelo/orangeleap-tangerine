package com.mpower.domain.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Site;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;

@Entity
@Table(name = "FIELD_DEFINITION")
public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @Column(name = "FIELD_DEFINITION_ID")
    private String id;

    @Column(name = "ENTITY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "DEFAULT_LABEL", nullable = false)
    private String defaultLabel;

    @Column(name = "FIELD_TYPE")
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "ENTITY_ATTRIBUTES")
    private String entityAttributes;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    /**
     * Field Info provides the details needed for some field types: lookup --> contains the Java classname for the entity
     */
    @Column(name = "FIELD_INFO")
    private String fieldInfo;

    // These are the relationships for which the current field is a detailField.  The relationship holds the masterField name and relationship type.
    @OneToMany(cascade=CascadeType.ALL, mappedBy="masterRecordField")
    private List<FieldRelationship> masterFieldRelationships;

    // There should only be one master field relationship active per FieldDefinition (for a particular site).
    @OneToMany(cascade=CascadeType.ALL, mappedBy="detailRecordField")
    private List<FieldRelationship> detailFieldRelationships;
    
    
	// Helper methods
	
	// Returns master fields for this site.
	// There should usually only be one master field relationship per site for this field.
	public List<FieldRelationship> getSiteMasterFieldRelationships(String siteName) {
		List<FieldRelationship> list = getMasterFieldRelationships();
		return getSiteFieldRelationships(siteName, list);
	}
	
	// Returns detail fields for this site.
	public List<FieldRelationship> getSiteDetailFieldRelationships(String siteName) {
		List<FieldRelationship> list = getDetailFieldRelationships();
		return getSiteFieldRelationships(siteName, list);
	}
	
	// Filter for this site.
    private List<FieldRelationship> getSiteFieldRelationships(String siteName, List<FieldRelationship> list) {
    	List<FieldRelationship> result = new ArrayList<FieldRelationship>();
		for (FieldRelationship fr : list) {
			if (fr.getSite() == null) continue;
			if (fr.getSite().getName().equals(siteName))  result.add(fr);
		}
		// If no site specific relationships exist for this field, the default relationships apply.
		if (result.size() == 0) for (FieldRelationship fr : list) {
			if (fr.getSite() == null) result.add(fr);
		}
		return result;
    }
    
 
    private static String CUSTOM_FIELD_MAP = "customFieldMap[";
    
    public boolean isCustom() {
        return (fieldName != null && fieldName.startsWith(CUSTOM_FIELD_MAP));
    }
    
    public boolean isTree(String siteName) {
    	// This must be the parent reference field on the detail record.
    	List<FieldRelationship> list = getSiteMasterFieldRelationships(siteName);
    	for (FieldRelationship fr : list) {
    		if (fr.isRecursive()) return true;
    	}
    	return false;
    }
    
    public boolean isRelationship(String siteName) {
    	return (getSiteMasterFieldRelationships(siteName).size() > 0 || getSiteDetailFieldRelationships(siteName).size() > 0) ;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getFieldInfo() {
        return fieldInfo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getCustomFieldName() {
    	if (!isCustom()) return "";
        return fieldName.substring(CUSTOM_FIELD_MAP.length(), fieldName.length() - 1);
    }

    public String getPropertyName() {
        return entityType.name() + "." + fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getId() {
        return id;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

	public void setMasterFieldRelationships(List<FieldRelationship> masterFieldRelationships) {
		this.masterFieldRelationships = masterFieldRelationships;
	}

	public List<FieldRelationship> getMasterFieldRelationships() {
		return masterFieldRelationships;
	}

	public void setDetailFieldRelationships(List<FieldRelationship> detailFieldRelationships) {
		this.detailFieldRelationships = detailFieldRelationships;
	}

	public List<FieldRelationship> getDetailFieldRelationships() {
		return detailFieldRelationships;
	}

	public void setEntityAttributes(String entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

	public String getEntityAttributes() {
		return entityAttributes;
	}
	
	
}
