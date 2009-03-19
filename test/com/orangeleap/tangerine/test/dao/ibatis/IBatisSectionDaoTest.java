package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.LayoutType;
import com.orangeleap.tangerine.type.PageType;

public class IBatisSectionDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private SectionDao sectionDao;

    @BeforeMethod
    public void setupMocks() {
        sectionDao = (SectionDao)super.applicationContext.getBean("sectionDAO");
    }

    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsNoRole() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_DOOFUS");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions(PageType.person, roles);
        assert sections != null && sections.isEmpty();
    }
    
    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsSuperManager() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_SUPER_MANAGER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions(PageType.person, roles);
        assert sections != null && sections.size() == 3;
        for (SectionDefinition secDef : sections) {
            assert PageType.person.equals(secDef.getPageType());
            assert "person.contactInfo".equals(secDef.getSectionName()) || "person.demographics".equals(secDef.getSectionName()) || "person.relationshipInfo".equals(secDef.getSectionName());
            assert "Contact Details".equals(secDef.getDefaultLabel()) || "Demographic Information".equals(secDef.getDefaultLabel()) || "Relationship Information".equals(secDef.getDefaultLabel());
            assert 1 == secDef.getSectionOrder() || 2 == secDef.getSectionOrder() || 3 == secDef.getSectionOrder();
            assert LayoutType.TWO_COLUMN.equals(secDef.getLayoutType());
            assert "ROLE_SUPER_MANAGER".equals(secDef.getRole());
            assert secDef.getSite() == null;
        }
    } 

    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsNone() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_USER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions(PageType.pledgeView, roles);
        assert sections != null && sections.isEmpty();
    }
    
    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsUser() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_USER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions(PageType.gift, roles);
        assert sections != null && sections.size() == 2;
        for (SectionDefinition secDef : sections) {
            assert PageType.gift.equals(secDef.getPageType());
            assert "gift.donation".equals(secDef.getSectionName()) || "gift.payment".equals(secDef.getSectionName());
            assert "Donation Information".equals(secDef.getDefaultLabel()) || "Payment Information".equals(secDef.getDefaultLabel());
            assert 1 == secDef.getSectionOrder() || 2 == secDef.getSectionOrder();
            assert LayoutType.TWO_COLUMN.equals(secDef.getLayoutType()) || LayoutType.ONE_COLUMN.equals(secDef.getLayoutType());
            assert "ROLE_USER".equals(secDef.getRole());
            assert secDef.getSite() != null && "company1".equals(secDef.getSite().getName());
        }
    } 
    
    @Test(groups = { "testReadSectionFields" })
    public void testReadCustomizedSectionFieldsNoSecondary() throws Exception {
        List<SectionField> sectionFields = sectionDao.readCustomizedSectionFields(200L);
        assert sectionFields != null && sectionFields.isEmpty() == false;
        assert sectionFields.size() == 1;
        assert 6000 == sectionFields.get(0).getFieldOrder();
        FieldDefinition fieldDef = sectionFields.get(0).getFieldDefinition();
        assert fieldDef != null;
        assert "person.recognitionName".equals(fieldDef.getId());
        assert EntityType.person.equals(fieldDef.getEntityType());
        assert "recognitionName".equals(fieldDef.getFieldName());
        assert "Recognition Name".equals(fieldDef.getDefaultLabel());
        assert FieldType.TEXT.equals(fieldDef.getFieldType());
        assert "individual".equals(fieldDef.getEntityAttributes());
        assert "company1".equals(fieldDef.getSite().getName());
        
        assert sectionFields.get(0).getSecondaryFieldDefinition() == null;
        
        SectionDefinition sectionDef = sectionFields.get(0).getSectionDefinition();
        assert sectionDef != null;
        assert 200L == sectionDef.getId();
        assert PageType.person.equals(sectionDef.getPageType());
        assert "person.demographics".equals(sectionDef.getSectionName());
        assert "Demographic Information".equals(sectionDef.getDefaultLabel());
        assert 2 == sectionDef.getSectionOrder();
        assert LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType());
        assert "ROLE_SUPER_MANAGER".equals(sectionDef.getRole());
        assert sectionDef.getSite() == null;
    }
    
    @Test(groups = { "testReadSectionFields" })
    public void testReadCustomizedSectionFieldsHasSecondary() throws Exception {
        List<SectionField> sectionFields = sectionDao.readCustomizedSectionFields(100L);
        assert sectionFields != null && sectionFields.isEmpty() == false;
        assert sectionFields.size() == 1;
        assert 7000 == sectionFields.get(0).getFieldOrder();
        FieldDefinition fieldDef = sectionFields.get(0).getFieldDefinition();
        assert fieldDef != null;
        assert "person.primaryEmail".equals(fieldDef.getId());
        assert EntityType.person.equals(fieldDef.getEntityType());
        assert "primaryEmail".equals(fieldDef.getFieldName());
        assert "Email".equals(fieldDef.getDefaultLabel());
        assert FieldType.TEXT.equals(fieldDef.getFieldType());
        assert fieldDef.getEntityAttributes() == null;
        assert "company1".equals(fieldDef.getSite().getName());
        
        FieldDefinition secFieldDef = sectionFields.get(0).getSecondaryFieldDefinition();
        assert secFieldDef != null;
        assert "email.emailAddress".equals(secFieldDef.getId());
        assert EntityType.email.equals(secFieldDef.getEntityType());
        assert "emailAddress".equals(secFieldDef.getFieldName());
        assert "Email Address".equals(secFieldDef.getDefaultLabel());
        assert FieldType.TEXT.equals(secFieldDef.getFieldType());
        assert secFieldDef.getEntityAttributes() == null;
        assert secFieldDef.getSite() == null;
        
        SectionDefinition sectionDef = sectionFields.get(0).getSectionDefinition();
        assert sectionDef != null;
        assert 100L == sectionDef.getId();
        assert PageType.person.equals(sectionDef.getPageType());
        assert "person.contactInfo".equals(sectionDef.getSectionName());
        assert "Contact Details".equals(sectionDef.getDefaultLabel());
        assert 1 == sectionDef.getSectionOrder();
        assert LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType());
        assert "ROLE_SUPER_MANAGER".equals(sectionDef.getRole());
        assert sectionDef.getSite() == null;
    }
    
    @Test(groups = { "testReadSectionFields" })
    public void testReadOutOfBoxSectionFields() throws Exception {
        List<SectionField> sectionFields = sectionDao.readOutOfBoxSectionFields(PageType.person, "person.contactInfo");
        assert sectionFields != null && sectionFields.isEmpty() == false;
        assert sectionFields.size() == 5;
        
        for (SectionField secFld : sectionFields) {
            assert secFld.getFieldOrder() >= 1000 && secFld.getFieldOrder() <= 5000;
            FieldDefinition fieldDef = secFld.getFieldDefinition();
            assert fieldDef != null;
            assert "person.title".equals(fieldDef.getId()) || "person.firstName".equals(fieldDef.getId()) || "person.middleName".equals(fieldDef.getId()) || 
                "person.lastName".equals(fieldDef.getId()) || "person.suffix".equals(fieldDef.getId());
            assert EntityType.person.equals(fieldDef.getEntityType());
            if ("person.title".equals(fieldDef.getId()) || "person.suffix".equals(fieldDef.getId())) {
                assert FieldType.PICKLIST.equals(fieldDef.getFieldType());
            }
            else {
                assert FieldType.TEXT.equals(fieldDef.getFieldType());
            }
            assert "individual".equals(fieldDef.getEntityAttributes());
            assert fieldDef.getSite() == null;
            
            assert secFld.getSecondaryFieldDefinition() == null;
            
            SectionDefinition sectionDef = secFld.getSectionDefinition();
            assert sectionDef != null;
            assert 100L == sectionDef.getId();
            assert PageType.person.equals(sectionDef.getPageType());
            assert "person.contactInfo".equals(sectionDef.getSectionName());
            assert "Contact Details".equals(sectionDef.getDefaultLabel());
            assert 1 == sectionDef.getSectionOrder();
            assert LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType());
            assert "ROLE_SUPER_MANAGER".equals(sectionDef.getRole());
            assert sectionDef.getSite() == null;
        }
    }
}
