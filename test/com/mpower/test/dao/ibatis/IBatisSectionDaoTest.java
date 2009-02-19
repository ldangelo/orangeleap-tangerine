package com.mpower.test.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.SectionDao;
import com.mpower.domain.model.customization.SectionDefinition;
import com.mpower.type.LayoutType;
import com.mpower.type.PageType;

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
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions("company1", PageType.person, roles);
        assert sections != null && sections.isEmpty();
    }
    
    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsSuperManager() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_SUPER_MANAGER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions("company1", PageType.person, roles);
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
    public void testReadSectionDefinitionsNoSite() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_USER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions("companyDoesNotExist", PageType.gift, roles);
        assert sections != null && sections.isEmpty();
    }
    
    @Test(groups = { "testReadSectionDefinitions" })
    public void testReadSectionDefinitionsUser() throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("ROLE_USER");
        List<SectionDefinition> sections = sectionDao.readSectionDefinitions("company1", PageType.gift, roles);
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
}
