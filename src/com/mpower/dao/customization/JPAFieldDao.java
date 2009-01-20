package com.mpower.dao.customization;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.customization.Code;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.PicklistItem;
import com.mpower.type.EntityType;

@Repository("fieldDao")
public class JPAFieldDao implements FieldDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public FieldDefinition readFieldById(String fieldId) {
        return em.find(FieldDefinition.class, fieldId);
    }

    @Override
    public Picklist readPicklist(String picklistId) {
    	return em.find(Picklist.class, picklistId);
    }

    @Override
    public PicklistItem readPicklistItem(Long picklistItemId) {
    	return em.find(PicklistItem.class, picklistItemId);
    }

    @Override
	public PicklistItem maintainPicklistItem(PicklistItem picklistItem) {
		return em.merge(picklistItem);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPicklists(String siteName) {
		Query query = em.createNamedQuery("READ_SITE_PICKLISTS");
		query.setParameter("siteName", siteName);
		return query.getResultList();
	}
    
    @SuppressWarnings("unchecked")
    @Override
    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName, EntityType entityType) {
        Query query = em.createNamedQuery("READ_PICKLIST_BY_SITE_AND_FIELDNAME");
        query.setParameter("siteName", siteName);
        query.setParameter("fieldName", fieldName);
        query.setParameter("entityType", entityType);
        List<Picklist> picklists = query.getResultList();
        return picklists.size() > 0 ? picklists.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FieldRequired readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        Query query = null;
        if (secondaryFieldDefinitionId == null) {
            query = em.createNamedQuery("QUERY_FIELD_REQUIRED_BY_SITE_AND_SECTION_NAME_AND_FIELD_DEFINITION");
            query.setParameter("siteName", siteName);
            query.setParameter("sectionName", sectionName);
            query.setParameter("fieldDefinitionId", fieldDefinitionId);
        } else {
            query = em.createNamedQuery("QUERY_FIELD_REQUIRED_BY_SITE_AND_SECTION_NAME_AND_FIELD_DEF_AND_SECONDARY_FIELD_DEF");
            query.setParameter("siteName", siteName);
            query.setParameter("sectionName", sectionName);
            query.setParameter("fieldDefinitionId", fieldDefinitionId);
            query.setParameter("secondaryFieldDefinitionId", secondaryFieldDefinitionId);
        }
        List<FieldRequired> fields = query.getResultList();
        if (fields.size() > 0) {
            if (fields.size() == 1) {
                return fields.get(0);
            } else {
                for (FieldRequired fr : fields) {
                    if (fr.getSiteName() != null) {
                        return fr;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FieldValidation readFieldValidation(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
        Query query = null;
        if (secondaryFieldDefinitionId == null) {
            query = em.createNamedQuery("QUERY_FIELD_VALIDATION_BY_SITE_AND_SECTION_NAME_AND_FIELD_DEFINITION");
            query.setParameter("siteName", siteName);
            query.setParameter("sectionName", sectionName);
            query.setParameter("fieldDefinitionId", fieldDefinitionId);
        } else {
            query = em.createNamedQuery("QUERY_FIELD_VALIDATION_BY_SITE_AND_SECTION_NAME_AND_FIELD_DEF_AND_SECONDARY_FIELD_DEF");
            query.setParameter("siteName", siteName);
            query.setParameter("sectionName", sectionName);
            query.setParameter("fieldDefinitionId", fieldDefinitionId);
            query.setParameter("secondaryFieldDefinitionId", secondaryFieldDefinitionId);
        }
        List<FieldValidation> fields = query.getResultList();
        if (fields.size() > 0) {
            if (fields.size() == 1) {
                return fields.get(0);
            } else {
                for (FieldValidation fr : fields) {
                    if (fr.getSiteName() != null) {
                        return fr;
                    }
                }
            }
        }
        return null;
    }

}
