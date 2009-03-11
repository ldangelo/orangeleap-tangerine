package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.type.RelationshipType;

public class IBatisFieldDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private FieldDao fieldDao;

    @BeforeMethod
    public void setupMocks() {
        fieldDao = (FieldDao)super.applicationContext.getBean("fieldDAO");
    }

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldNotRequired() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("person.contactInfo", "person.title", null);
        assert fieldReq == null;
    }

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldRequiredNoSecondary() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("person.contactInfo", "person.firstName", null);
        assert fieldReq != null;
        assert fieldReq.getSite() == null;
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() == null;
        assert fieldReq.isRequired();
        assert "person.contactInfo".equals(fieldReq.getSectionName());
        assert "person.firstName".equals(fieldReq.getFieldDefinition().getId());
        assert EntityType.person.equals(fieldReq.getFieldDefinition().getEntityType());
        assert "firstName".equals(fieldReq.getFieldDefinition().getFieldName());
        assert "First Name".equals(fieldReq.getFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldReq.getFieldDefinition().getFieldType());
        assert "individual".equals(fieldReq.getFieldDefinition().getEntityAttributes());
        assert fieldReq.getFieldDefinition().getSite() == null;
    } 

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldRequiredHasSecondary() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("person.contactInfo", "person.emailMap[home]", "email.emailAddress");
        assert fieldReq != null;
        assert fieldReq.getSite() != null && "company1".equals(fieldReq.getSite().getName());
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() != null;
        assert fieldReq.isRequired();
        assert "person.contactInfo".equals(fieldReq.getSectionName());
        
        assert "person.emailMap[home]".equals(fieldReq.getFieldDefinition().getId());
        assert EntityType.person.equals(fieldReq.getFieldDefinition().getEntityType());
        assert "emailMap[home]".equals(fieldReq.getFieldDefinition().getFieldName());
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
        FieldRequired fieldReq = fieldDao.readFieldRequired("person.contactInfo", "person.title", null);
        assert fieldReq == null;
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldRequiredConditions() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("person.contactInfo", "person.lastName", null);
        assert fieldReq != null;
        assert fieldReq.getSite() == null;
        assert fieldReq.getFieldDefinition() != null;
        assert fieldReq.getSecondaryFieldDefinition() == null;
        assert fieldReq.isRequired();
        assert fieldReq.getFieldConditions() != null && fieldReq.getFieldConditions().isEmpty() == false;
        assert fieldReq.getFieldConditions().size() == 4;
        for (FieldCondition fieldCond : fieldReq.getFieldConditions()) {
            assert fieldCond.getDependentFieldDefinition() != null;
            assert "person.firstName".equals(fieldCond.getDependentFieldDefinition().getId());
            assert "Tom".equals(fieldCond.getValue()) || "Jerry".equals(fieldCond.getValue()) || "Wilma".equals(fieldCond.getValue()) || "Betty".equals(fieldCond.getValue());
            assert "firstName".equals(fieldCond.getDependentFieldDefinition().getFieldName());
        }
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldValidationNoCondition() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("person.contactInfo", "person.title", null);
        assert fieldVal == null;
    }
    
    @Test(groups = { "testFieldCondition" })
    public void testReadFieldValidationConditions() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("person.contactInfo", "person.emailMap[home]", "email.emailAddress");
        assert fieldVal != null;
        assert fieldVal.getSite() != null && "company1".equals(fieldVal.getSite().getName());
        assert fieldVal.getFieldDefinition() != null;
        assert fieldVal.getSecondaryFieldDefinition() != null;
        assert "person.contactInfo".equals(fieldVal.getSectionName());
        assert "extensions:isEmail".equals(fieldVal.getRegex());
        assert fieldVal.getFieldConditions() != null && fieldVal.getFieldConditions().isEmpty() == false;
        assert fieldVal.getFieldConditions().size() == 1;
        for (FieldCondition fieldCond : fieldVal.getFieldConditions()) {
            assert fieldCond.getDependentFieldDefinition() != null;
            assert "person.emailMap[home]".equals(fieldCond.getDependentFieldDefinition().getId());
            assert fieldCond.getDependentSecondaryFieldDefinition() != null;
            assert "email.userCreated".equals(fieldCond.getDependentSecondaryFieldDefinition().getId());
            assert "userCreated".equals(fieldCond.getDependentSecondaryFieldDefinition().getFieldName());
            assert "true".equals(fieldCond.getValue());
        }
    }

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldNoValidation() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("person.contactInfo", "person.title", null);
        assert fieldVal == null;
    }

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldValidationHasSecondary() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("person.contactInfo", "person.emailMap[home]", "email.emailAddress");
        assert fieldVal != null;
        assert fieldVal.getSite() != null && "company1".equals(fieldVal.getSite().getName());
        assert fieldVal.getFieldDefinition() != null;
        assert fieldVal.getSecondaryFieldDefinition() != null;
        assert "person.contactInfo".equals(fieldVal.getSectionName());
        assert "extensions:isEmail".equals(fieldVal.getRegex());
        
        assert "person.emailMap[home]".equals(fieldVal.getFieldDefinition().getId());
        assert EntityType.person.equals(fieldVal.getFieldDefinition().getEntityType());
        assert "emailMap[home]".equals(fieldVal.getFieldDefinition().getFieldName());
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
        List<FieldRelationship> relationships = fieldDao.readMasterFieldRelationships("person.customFieldMap[individual.spouse]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 2;
        for (FieldRelationship relationship : relationships) {
            assert relationship.getSite() == null;
            assert RelationshipType.MANY_TO_MANY.equals(relationship.getRelationshipType()) || RelationshipType.ONE_TO_ONE.equals(relationship.getRelationshipType());
            assert relationship.isRecursive() == false;
            assert "person.customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getId());
            assert EntityType.person.equals(relationship.getMasterRecordField().getEntityType());
            assert ReferenceType.person.equals(relationship.getMasterRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getFieldName());
            assert "Spouse".equals(relationship.getMasterRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getMasterRecordField().getFieldType());
            assert "individual".equals(relationship.getMasterRecordField().getEntityAttributes());
            
            assert "person.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId()) || "person.customFieldMap[individual.siblings]".equals(relationship.getDetailRecordField().getId());
            if ("person.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId())) {
                assert EntityType.person.equals(relationship.getDetailRecordField().getEntityType());
                assert ReferenceType.person.equals(relationship.getDetailRecordField().getReferenceType());
                assert "customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getFieldName());
                assert "Spouse".equals(relationship.getDetailRecordField().getDefaultLabel());
                assert FieldType.QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
                assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());
            }
            else {
                assert EntityType.person.equals(relationship.getDetailRecordField().getEntityType());
                assert ReferenceType.person.equals(relationship.getDetailRecordField().getReferenceType());
                assert "customFieldMap[individual.siblings]".equals(relationship.getDetailRecordField().getFieldName());
                assert "Siblings".equals(relationship.getDetailRecordField().getDefaultLabel());
                assert FieldType.MULTI_QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
                assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());
            }
        }
        
        /* Test for 1 result */
        relationships = fieldDao.readMasterFieldRelationships("person.customFieldMap[headofhousehold.householdMembers]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 1;
    }
    
    @Test(groups = { "testFieldRelationships" })
    public void testReadDetailFieldRelationships() throws Exception {
        List<FieldRelationship> relationships = fieldDao.readDetailFieldRelationships("person.customFieldMap[individual.spouse]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 1;
        for (FieldRelationship relationship : relationships) {
            assert relationship.getSite() == null;
            assert RelationshipType.ONE_TO_ONE.equals(relationship.getRelationshipType());
            assert relationship.isRecursive() == false;
            assert "person.customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getId());
            assert EntityType.person.equals(relationship.getDetailRecordField().getEntityType());
            assert ReferenceType.person.equals(relationship.getDetailRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getDetailRecordField().getFieldName());
            assert "Spouse".equals(relationship.getDetailRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getDetailRecordField().getFieldType());
            assert "individual".equals(relationship.getDetailRecordField().getEntityAttributes());

            assert "person.customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getId());
            assert EntityType.person.equals(relationship.getMasterRecordField().getEntityType());
            assert ReferenceType.person.equals(relationship.getMasterRecordField().getReferenceType());
            assert "customFieldMap[individual.spouse]".equals(relationship.getMasterRecordField().getFieldName());
            assert "Spouse".equals(relationship.getMasterRecordField().getDefaultLabel());
            assert FieldType.QUERY_LOOKUP.equals(relationship.getMasterRecordField().getFieldType());
            assert "individual".equals(relationship.getMasterRecordField().getEntityAttributes());
        }
        
        /* Test for multiple results */
        relationships = fieldDao.readDetailFieldRelationships("person.customFieldMap[individual.siblings]");
        assert relationships != null && relationships.isEmpty() == false && relationships.size() == 2;
    }
}
