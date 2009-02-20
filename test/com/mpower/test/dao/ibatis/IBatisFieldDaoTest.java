package com.mpower.test.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.FieldDao;
import com.mpower.domain.model.customization.FieldRequired;
import com.mpower.domain.model.customization.FieldValidation;
import com.mpower.type.EntityType;
import com.mpower.type.FieldType;

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
        FieldRequired fieldReq = fieldDao.readFieldRequired("company1", "person.contactInfo", "person.title", null);
        assert fieldReq == null;
    }

    @Test(groups = { "testFieldRequired" })
    public void testReadFieldRequiredNoSecondary() throws Exception {
        FieldRequired fieldReq = fieldDao.readFieldRequired("company1", "person.contactInfo", "person.firstName", null);
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
        FieldRequired fieldReq = fieldDao.readFieldRequired("company2", "person.contactInfo", "person.emailMap[home]", "email.emailAddress");
        assert fieldReq != null;
        assert fieldReq.getSite() != null && "company2".equals(fieldReq.getSite().getName());
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
        assert fieldReq.getFieldDefinition().getSite() != null && "company2".equals(fieldReq.getFieldDefinition().getSite().getName());

        assert "email.emailAddress".equals(fieldReq.getSecondaryFieldDefinition().getId());
        assert EntityType.email.equals(fieldReq.getSecondaryFieldDefinition().getEntityType());
        assert "emailAddress".equals(fieldReq.getSecondaryFieldDefinition().getFieldName());
        assert "Email Address".equals(fieldReq.getSecondaryFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldReq.getSecondaryFieldDefinition().getFieldType());
        assert fieldReq.getSecondaryFieldDefinition().getEntityAttributes() == null;
        assert fieldReq.getSecondaryFieldDefinition().getSite() == null;
    } 

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldNoValidation() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("company1", "person.contactInfo", "person.title", null);
        assert fieldVal == null;
    }

    @Test(groups = { "testFieldValidation" })
    public void testReadFieldValidationHasSecondary() throws Exception {
        FieldValidation fieldVal = fieldDao.readFieldValidation("company2", "person.contactInfo", "person.emailMap[home]", "email.emailAddress");
        assert fieldVal != null;
        assert fieldVal.getSite() != null && "company2".equals(fieldVal.getSite().getName());
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
        assert fieldVal.getFieldDefinition().getSite() != null && "company2".equals(fieldVal.getFieldDefinition().getSite().getName());

        assert "email.emailAddress".equals(fieldVal.getSecondaryFieldDefinition().getId());
        assert EntityType.email.equals(fieldVal.getSecondaryFieldDefinition().getEntityType());
        assert "emailAddress".equals(fieldVal.getSecondaryFieldDefinition().getFieldName());
        assert "Email Address".equals(fieldVal.getSecondaryFieldDefinition().getDefaultLabel());
        assert FieldType.TEXT.equals(fieldVal.getSecondaryFieldDefinition().getFieldType());
        assert fieldVal.getSecondaryFieldDefinition().getEntityAttributes() == null;
        assert fieldVal.getSecondaryFieldDefinition().getSite() == null;
    } 
}
