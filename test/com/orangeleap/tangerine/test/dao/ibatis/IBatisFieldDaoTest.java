package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.type.RelationshipType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IBatisFieldDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    private FieldDao fieldDao;

    @BeforeMethod
    public void setupMocks() {
        fieldDao = (FieldDao)super.applicationContext.getBean("fieldDAO");
    }

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldNotRequired() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("constituent.contactInfo", "constituent.title", null);
        assert fieldReq == null;
    }

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldRequiredNoSecondary() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("constituent.contactInfo", "constituent.firstName", null);
        assert fieldReq != null;
        assert fieldReq.getSite() == null;
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() == null;
        assert fieldReq.isRequired();
        assert "constituent.contactInfo".equals(fieldReq.getSectionName());
        assert "constituent.firstName".equals(fieldReq.getFieldDefinition().getId());
        assert EntityType.constituent.equals(fieldReq.getFieldDefinition().getEntityType());
        assert "firstName".equals(fieldReq.getFieldDefinition().getFieldName());
        assert "First Name".equals(fieldReq.getFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldReq.getFieldDefinition().getFieldType());
        assert "individual".equals(fieldReq.getFieldDefinition().getEntityAttributes());
        assert fieldReq.getFieldDefinition().getSite() == null;
    } 

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldRequiredHasSecondary() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("constituent.contactInfo", "constituent.primaryEmail", "email.emailAddress");
        assert fieldReq != null;
        assert fieldReq.getSite() != null && "company1".equals(fieldReq.getSite().getName());
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() != null;
        assert fieldReq.isRequired();
        assert "constituent.contactInfo".equals(fieldReq.getSectionName());
        
        assert "constituent.primaryEmail".equals(fieldReq.getFieldDefinition().getId());
        assert EntityType.constituent.equals(fieldReq.getFieldDefinition().getEntityType());
        assert "primaryEmail".equals(fieldReq.getFieldDefinition().getFieldName());
        assert "Email".equals(fieldReq.getFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldReq.getFieldDefinition().getFieldType());
        assert fieldReq.getFieldDefinition().getEntityAttributes() == null;
        assert fieldReq.getFieldDefinition().getSite() != null && "company1".equals(fieldReq.getFieldDefinition().getSite().getName());

        assert "email.emailAddress".equals(fieldReq.getSecondaryFieldDefinition().getId());
        assert EntityType.email.equals(fieldReq.getSecondaryFieldDefinition().getEntityType());
        assert "emailAddress".equals(fieldReq.getSecondaryFieldDefinition().getFieldName());
        assert "Email Address".equals(fieldReq.getSecondaryFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldReq.getSecondaryFieldDefinition().getFieldType());
        assert fieldReq.getSecondaryFieldDefinition().getEntityAttributes() == null;
        assert fieldReq.getSecondaryFieldDefinition().getSite() == null;
    } 
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldRequiredNoCondition() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("constituent.contactInfo", "constituent.title", null);
        assert fieldReq == null;
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldRequiredConditions() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("constituent.contactInfo", "constituent.lastName", null);
        assert fieldReq != null;
        assert fieldReq.getSite() == null;
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() == null;
        assert fieldReq.isRequired();
        assert fieldReq.getFieldConditions() != null && fieldReq.getFieldConditions().isEmpty() == false;
        assert fieldReq.getFieldConditions().size() == 4;
        for (FieldCondition fieldCond : fieldReq.getFieldConditions()) {
            assert fieldCond.getDependentFieldDefinition() != null;
            assert "constituent.firstName".equals(fieldCond.getDependentFieldDefinition().getId());
            assert "Tom".equals(fieldCond.getValue()) || "Jerry".equals(fieldCond.getValue()) || "Wilma".equals(fieldCond.getValue()) || "Betty".equals(fieldCond.getValue());
            assert "firstName".equals(fieldCond.getDependentFieldDefinition().getFieldName());
        }
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldValidationNoCondition() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("constituent.contactInfo", "constituent.title", null);
        assert fieldVal == null;
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldValidationConditions() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("constituent.contactInfo", "constituent.primaryEmail", "email.emailAddress");
        assert fieldVal != null;
        assert fieldVal.getSite() != null && "company1".equals(fieldVal.getSite().getName());
        assert fieldVal.getFieldDefinition() != null;
        assert fieldVal.getSecondaryFieldDefinition() != null;
        assert "constituent.contactInfo".equals(fieldVal.getSectionName());
        assert "extensions:isEmail".equals(fieldVal.getRegex());
        assert fieldVal.getFieldConditions() != null && fieldVal.getFieldConditions().isEmpty() == false;
        assert fieldVal.getFieldConditions().size() == 1;
        for (FieldCondition fieldCond : fieldVal.getFieldConditions()) {
            assert fieldCond.getDependentFieldDefinition() != null;
            assert "constituent.primaryEmail".equals(fieldCond.getDependentFieldDefinition().getId());
            assert fieldCond.getDependentSecondaryFieldDefinition() != null;
            assert "email.userCreated".equals(fieldCond.getDependentSecondaryFieldDefinition().getId());
            assert "userCreated".equals(fieldCond.getDependentSecondaryFieldDefinition().getFieldName());
            assert "true".equals(fieldCond.getValue());
        }
    }

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldNoValidation() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("constituent.contactInfo", "constituent.title", null);
        assert fieldVal == null;
    }

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldValidationHasSecondary() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("constituent.contactInfo", "constituent.primaryEmail", "email.emailAddress");
        assert fieldVal != null;
        assert fieldVal.getSite() != null && "company1".equals(fieldVal.getSite().getName());
        assert fieldVal.getFieldDefinition() != null;
        assert fieldVal.getSecondaryFieldDefinition() != null;
        assert "constituent.contactInfo".equals(fieldVal.getSectionName());
        assert "extensions:isEmail".equals(fieldVal.getRegex());
        
        assert "constituent.primaryEmail".equals(fieldVal.getFieldDefinition().getId());
        assert EntityType.constituent.equals(fieldVal.getFieldDefinition().getEntityType());
        assert "primaryEmail".equals(fieldVal.getFieldDefinition().getFieldName());
        assert "Email".equals(fieldVal.getFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldVal.getFieldDefinition().getFieldType());
        assert fieldVal.getFieldDefinition().getEntityAttributes() == null;
        assert fieldVal.getFieldDefinition().getSite() != null && "company1".equals(fieldVal.getFieldDefinition().getSite().getName());

        assert "email.emailAddress".equals(fieldVal.getSecondaryFieldDefinition().getId());
        assert EntityType.email.equals(fieldVal.getSecondaryFieldDefinition().getEntityType());
        assert "emailAddress".equals(fieldVal.getSecondaryFieldDefinition().getFieldName());
        assert "Email Address".equals(fieldVal.getSecondaryFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldVal.getSecondaryFieldDefinition().getFieldType());
        assert fieldVal.getSecondaryFieldDefinition().getEntityAttributes() == null;
        assert fieldVal.getSecondaryFieldDefinition().getSite() == null;
    } 

    @Test(groups = { "testFieldRelationships" })
    public void testReadMasterFieldRelationships() throws Exception {
        List<FieldRelationship> relationships = fieldDao.readMasterFieldRelationships("constituent.customFieldMap[individual.spouse]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 2;
        for (FieldRelationship relationship : relationships) {
            assert relationship.getSite() == null;
            assert RelationshipType.MANY_TO_MANY.equals(relationship.getRelationshipType()) || RelationshipType.ONE_TO_ONE.equals(relationship.getRelationshipType());
            assert relationship.isRecursive() == false;
            assert "constituent.customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getId());
            assert EntityType.constituent.equals(relationship.getMasterRecordField().getEntityType());
            assert ReferenceType.constituent.equals(relationship.getMasterRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getFieldName());
            assert "Spouse".equals(relationship.getMasterRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getMasterRecordField().getFieldType());
            assert "individual".equals(relationship.getMasterRecordField().getEntityAttributes());
            
            assert "constituent.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId()) || "constituent.customFieldMap[individual.siblings]".equals(relationship.getDetailRecordField().getId());
            if ("constituent.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId())) {
                assert EntityType.constituent.equals(relationship.getDetailRecordField().getEntityType());
                assert ReferenceType.constituent.equals(relationship.getDetailRecordField().getReferenceType());
                assert "customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getFieldName());
                assert "Spouse".equals(relationship.getDetailRecordField().getDefaultLabel());
                assert FieldType.QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
                assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());
            }
            else {
                assert EntityType.constituent.equals(relationship.getDetailRecordField().getEntityType());
                assert ReferenceType.constituent.equals(relationship.getDetailRecordField().getReferenceType());
                assert "customFieldMap[individual.siblings]".equals(relationship.getDetailRecordField().getFieldName());
                assert "Siblings".equals(relationship.getDetailRecordField().getDefaultLabel());
                assert FieldType.MULTI_QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
                assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());
            }
        }
        
        /* Test for 1 result */
        relationships = fieldDao.readMasterFieldRelationships("constituent.customFieldMap[headofhousehold.householdMembers]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 1;
    }
    
    @Test(groups = { "testFieldRelationships" })
    public void testReadDetailFieldRelationships() throws Exception {
        List<FieldRelationship> relationships = fieldDao.readDetailFieldRelationships("constituent.customFieldMap[individual.spouse]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 1;
        for (FieldRelationship relationship : relationships) {
            assert relationship.getSite() == null;
            assert RelationshipType.ONE_TO_ONE.equals(relationship.getRelationshipType());
            assert relationship.isRecursive() == false;
            assert "constituent.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId());
            assert EntityType.constituent.equals(relationship.getDetailRecordField().getEntityType());
            assert ReferenceType.constituent.equals(relationship.getDetailRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getFieldName());
            assert "Spouse".equals(relationship.getDetailRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
            assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());

            assert "constituent.customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getId());
            assert EntityType.constituent.equals(relationship.getMasterRecordField().getEntityType());
            assert ReferenceType.constituent.equals(relationship.getMasterRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getFieldName());
            assert "Spouse".equals(relationship.getMasterRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getMasterRecordField().getFieldType());
            assert "individual".equals(relationship.getMasterRecordField().getEntityAttributes());
        }
        
        /* Test for multiple results */
        relationships = fieldDao.readDetailFieldRelationships("constituent.customFieldMap[individual.siblings]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 2;
    }

}
