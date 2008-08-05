package com.mpower.dao.customization;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.Picklist;
import com.mpower.domain.customization.Validation;

@Repository("fieldDao")
public class JPAFieldDao implements FieldDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    @PersistenceContext
    private EntityManager em;

    public FieldDefinition readFieldById(String fieldId) {
        return em.find(FieldDefinition.class, fieldId);
    }

    @SuppressWarnings("unchecked")
    public Picklist readPicklistBySiteAndFieldName(String siteName, String fieldName) {
        Query query = em.createNamedQuery("READ_PICKLIST_BY_SITE_AND_FIELDNAME");
        query.setParameter("siteName", siteName);
        query.setParameter("fieldName", fieldName);
        List<Picklist> picklists = query.getResultList();
        if (picklists.size() > 0) {
            if (picklists.size() == 1) {
                return picklists.get(0);
            } else {
                for (Picklist picklist : picklists) {
                    if (picklist.getSite() != null) {
                        return picklist;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean readFieldRequired(String siteName, String sectionName, String fieldDefinitionId, String secondaryFieldDefinitionId) {
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
                return fields.get(0).isRequired();
            } else {
                for (FieldRequired fr : fields) {
                    if (fr.getSiteName() != null) {
                        return fr.isRequired();
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String readFieldValidation(String siteName, Long sectionFieldId) {
        Query query = em.createNamedQuery("QUERY_VALIDATION_BY_SECTION_FIELD");
        List<Validation> validations = query.getResultList();
        if (validations.size() > 0) {
            if (validations.size() == 1) {
                return validations.get(0).getRegex();
            } else {
                for (Validation v : validations) {
                    if (v.getSiteName() != null) {
                        return v.getRegex();
                    }
                }
            }
        }
        return null;
    }
}
