package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.Person;

public class IBatisConstituentDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConstituentDao constituentDao;

    @BeforeMethod
    public void setup() {
        constituentDao = (ConstituentDao)super.applicationContext.getBean("constituentDAO");
    }

    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByIdInvalid() throws Exception {
        Person person = constituentDao.readConstituentById(0L);
        assert person == null;
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentById() throws Exception {
        Person person = constituentDao.readConstituentById(100L);
        assert person != null;
        assert person.getId() == 100;
        assert "Billy Graham Ministries".equals(person.getOrganizationName());
        assert "Graham".equals(person.getLastName());
        assert "Billy".equals(person.getFirstName());
        assert person.getMiddleName() == null;
        assert person.getSuffix() == null;
        assert "company2".equals(person.getSite().getName());
    } 
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByLoginIdSiteInvalid() throws Exception {
        Person person = constituentDao.readConstituentByLoginIdSite("pablo@company1.com", "company2");
        assert person == null;
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByLoginIdSite() throws Exception {
        Person person = constituentDao.readConstituentByLoginIdSite("pablo@company1.com", "company1");
        assert person != null;
        assert person.getId() == 200;
        assert "Painters, Inc.".equals(person.getOrganizationName());
        assert "Picasso".equals(person.getLastName());
        assert "Pablo".equals(person.getFirstName());
        assert person.getMiddleName() == null;
        assert "Sr".equals(person.getSuffix());
        assert "company1".equals(person.getSite().getName());
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadAllConstituentsBySiteNameInvalid() throws Exception {
        List<Person> constituents = constituentDao.readAllConstituentsBySite("companyDoesNotExist");
        assert constituents != null && constituents.isEmpty();
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadAllConstituentsBySiteName() throws Exception {
        List<Person> constituents = constituentDao.readAllConstituentsBySite("company1");
        assert constituents != null && constituents.size() == 2;
        for (Person constituent : constituents) {
            assert "company1".equals(constituent.getSite().getName());
            assert constituent.getId() == 200 || constituent.getId() == 300;
            assert "Painters, Inc.".equals(constituent.getOrganizationName()) || "Howdy Doody Inc".equals(constituent.getOrganizationName());
            assert "Picasso".equals(constituent.getLastName()) || "Doody".equals(constituent.getLastName());
            assert "Pablo".equals(constituent.getFirstName()) || "Howdy".equals(constituent.getFirstName());
            assert constituent.getMiddleName() == null;
            assert "Sr".equals(constituent.getSuffix()) || constituent.getSuffix() == null;
        }
    }
}
