package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.Site;
import com.mpower.type.RelationshipType;

public class FieldRelationship implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private RelationshipType relationshipType;

    /**
     * For ONE_TO_MANY relationships, if this flag is true, the following rule prohibiting loops in the tree will be enforced:
     * User can't set the master (parent) field of an object to any of its children, or children's children, etc.
     * (Adding a new detail child will also check this rule for the child.)
     */
    private boolean recursive;
    private FieldDefinition masterRecordField;
    private FieldDefinition detailRecordField;
    private Site site;

    @Override
	public void setId(Long id) {
		this.id = id;
	}

    @Override
	public Long getId() {
		return id;
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public boolean isRecursive() {
		return recursive;
	}

    public FieldDefinition getMasterRecordField() {
        return masterRecordField;
    }

	public void setMasterRecordField(FieldDefinition masterRecordField) {
		this.masterRecordField = masterRecordField;
	}

	public FieldDefinition getDetailRecordField() {
		return detailRecordField;
	}

    public void setDetailRecordField(FieldDefinition detailRecordField) {
        this.detailRecordField = detailRecordField;
    }

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}
}
