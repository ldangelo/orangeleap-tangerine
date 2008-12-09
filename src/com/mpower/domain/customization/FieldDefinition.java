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

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    /**
     * Field Info provides the details needed for some field types: lookup --> contains the Java classname for the entity
     */
    @Column(name = "FIELD_INFO")
    private String fieldInfo;

    // These are the relationships for which the current field is a detailField.  The relationship holds the masterField name and relationship type.
    @OneToMany(cascade=CascadeType.ALL, mappedBy="masterField")
    private List<FieldRelationship> detailFieldRelationships;

    // There should only be one master field relationship active per FieldDefinition (for a particular site).
    @OneToMany(cascade=CascadeType.ALL, mappedBy="detailField")
    private List<FieldRelationship> masterFieldRelationships;
    
    
	// Helper methods
	
	// Returns master fields for this site.
	// There should usually only be one master field relationship per site for this field.
	public List<FieldRelationship> getSiteMasterFieldRelationships(Site site) {
		List<FieldRelationship> list = getMasterFieldRelationships();
		return getSiteFieldRelationships(site, list);
	}
	
	// Returns detail fields for this site.
	public List<FieldRelationship> getSiteDetailFieldRelationships(Site site) {
		List<FieldRelationship> list = getDetailFieldRelationships();
		return getSiteFieldRelationships(site, list);
	}
	
	// Filter for this site.
    private List<FieldRelationship> getSiteFieldRelationships(Site site, List<FieldRelationship> list) {
    	List<FieldRelationship> result = new ArrayList<FieldRelationship>();
		for (FieldRelationship fr : list) {
			if (fr.getSite() == null) continue;
			if (fr.getSite().getName().equals(site.getName()))  result.add(fr);
		}
		// If no site specific relationships exist for this field, the default relationships apply.
		if (result.size() == 0) for (FieldRelationship fr : list) {
			if (fr.getSite() == null) result.add(fr);
		}
		return result;
    }
    
    // If a custom field name is like "customFieldMap[organization,donor.taxid]" the entity attribute css classes that activate this field are "ea-donor ea-employee"
    public String getEntityAttributes() {
    	String entityAttributes = "";
    	if (isCustom()) {
        	String name = getFieldName();
        	int start = name.indexOf("[") + 1;
        	int end = name.indexOf(".", start);
        	if (start < 1 || end < 1) return entityAttributes;
        	String[] ea = name.substring(start, end).split(",");
        	for (String s: ea) entityAttributes += ((entityAttributes.length() > 0) ? " " : "") + "ea-" + s;
        	return entityAttributes;
    	} else {
    		return entityAttributes;
    	}
    }

    
    
    public boolean isCustom() {
        return (fieldName != null && fieldName.startsWith("customFieldMap"));
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
	
	
}
