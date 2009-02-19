package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.model.Site;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;
import com.mpower.type.ReferenceType;
import com.mpower.util.StringConstants;

public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private EntityType entityType;
    private ReferenceType referenceType;
    private String fieldName;
    private String defaultLabel;
    private FieldType fieldType;
    private String entityAttributes;
    private Site site;
    /**
     * Field Info provides the details needed for some field types: lookup --> contains the Java classname for the entity
     */
    private String fieldInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public String getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
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

    public void setEntityAttributes(String entityAttributes) {
        this.entityAttributes = entityAttributes;
    }

    public String getEntityAttributes() {
        return entityAttributes;
    }
    
    // Helper Methods
    public String getCustomFieldName() {
        if (!isCustom()) {
            return "";
        }
        return fieldName.substring(StringConstants.CUSTOM_FIELD_MAP.length(), fieldName.length() - 1);
    }

    public String getPropertyName() {
        return entityType.name() + "." + fieldName;
    }
    
    public boolean isCustom() {
        return (fieldName != null && fieldName.startsWith(StringConstants.CUSTOM_FIELD_MAP));
    }


    // TODO: move below to RelationshipService
//  // These are the relationships for which the current field is a detailField.  The relationship holds the masterField name and relationship type.
//  private List<FieldRelationship> masterFieldRelationships;
//
//  // There should only be one master field relationship active per FieldDefinition (for a particular site).
//  private List<FieldRelationship> detailFieldRelationships;

//    public void setMasterFieldRelationships(List<FieldRelationship> masterFieldRelationships) {
//        this.masterFieldRelationships = masterFieldRelationships;
//    }
//
//    public List<FieldRelationship> getMasterFieldRelationships() {
//        return masterFieldRelationships;
//    }
//
//    public void setDetailFieldRelationships(List<FieldRelationship> detailFieldRelationships) {
//        this.detailFieldRelationships = detailFieldRelationships;
//    }
//
//    public List<FieldRelationship> getDetailFieldRelationships() {
//        return detailFieldRelationships;
//    }

	// Returns master fields for this site.
	// There should usually only be one master field relationship per site for this field.
//	public List<FieldRelationship> getSiteMasterFieldRelationships(String siteName) {
//		List<FieldRelationship> list = getMasterFieldRelationships();
//		return getSiteFieldRelationships(siteName, list);
//	}
	
	// Returns detail fields for this site.
//	public List<FieldRelationship> getSiteDetailFieldRelationships(String siteName) {
//		List<FieldRelationship> list = getDetailFieldRelationships();
//		return getSiteFieldRelationships(siteName, list);
//	}
	
	// Filter for this site.
//    private List<FieldRelationship> getSiteFieldRelationships(String siteName, List<FieldRelationship> list) {
//    	List<FieldRelationship> result = new ArrayList<FieldRelationship>();
//		for (FieldRelationship fr : list) {
//			if (fr.getSite() == null) {
//                continue;
//            }
//			if (fr.getSite().getName().equals(siteName)) {
//                result.add(fr);
//            }
//		}
//		// If no site specific relationships exist for this field, the default relationships apply.
//		if (result.size() == 0) {
//            for (FieldRelationship fr : list) {
//            	if (fr.getSite() == null) {
//                    result.add(fr);
//                }
//            }
//        }
//		return result;
//    }
//    
//    public boolean isTree(String siteName) {
//    	// This must be the parent reference field on the detail record.
//    	List<FieldRelationship> list = getSiteDetailFieldRelationships(siteName);
//    	for (FieldRelationship fr : list) {
//    		if (fr.isRecursive()) {
//    			return true;
//    		}
//    	}
//    	return false;
//    }
//    
//    public boolean isRelationship(String siteName) {
//    	return (getSiteMasterFieldRelationships(siteName).size() > 0 || getSiteDetailFieldRelationships(siteName).size() > 0) ;
//    }
}
