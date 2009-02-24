package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.PersonCustomField;
import com.mpower.domain.model.Site;
import com.mpower.util.StringConstants;

public class IBatisConstituentDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConstituentDao constituentDao;

    @BeforeMethod
    public void setup() {
        constituentDao = (ConstituentDao)super.applicationContext.getBean("constituentDAO");
    }

    @Test(groups = { "testMaintainConstituent" }, dependsOnGroups = { "testReadConstituent" })
    public void testMaintainConstituent() throws Exception {
        Person constituent = new Person();
        constituent.setFirstName("Joe");
        constituent.setLastName("Bob");
        constituent.setSite(new Site("company1"));
        constituent.setConstituentType(Person.INDIVIDUAL);
        constituent.setTitle("Sir");
        
        constituent = constituentDao.maintainConstituent(constituent);
        assert constituent.getId() > 0;
        
        Person readConstituent = constituentDao.readConstituentById(constituent.getId());
        assert readConstituent != null;
        assert constituent.getFirstName().equals(readConstituent.getFirstName());
        assert constituent.getLastName().equals(readConstituent.getLastName());
        assert constituent.getSite().getName().equals(readConstituent.getSite().getName());
        assert constituent.getConstituentType().equals(readConstituent.getConstituentType());
        assert constituent.getTitle().equals(readConstituent.getTitle());
        
        assert StringConstants.EMPTY.equals(readConstituent.getConstituentIndividualRoles());
        assert StringConstants.EMPTY.equals(readConstituent.getConstituentOrganizationRoles());
        assert readConstituent.getLegalName() == null;
        assert readConstituent.getLoginId() == null;
        assert readConstituent.isMajorDonor() == false;
        assert StringConstants.UNKNOWN.equals(readConstituent.getMaritalStatus());
        assert readConstituent.getMiddleName() == null;
        assert readConstituent.getNcaisCode() == null;
        assert readConstituent.getOrganizationName() == null;
        assert readConstituent.getPreferredPhoneType() == null;
        assert readConstituent.getRecognitionName() == null;
        assert readConstituent.getSuffix() == null;
        assert readConstituent.getCreateDate() != null;
        assert readConstituent.getUpdateDate() != null;
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByIdInvalid() throws Exception {
        Person constituent = constituentDao.readConstituentById(0L);
        assert constituent == null;
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentById() throws Exception {
        Person constituent = constituentDao.readConstituentById(100L);
        assert constituent != null;
        assert constituent.getId() == 100;
        assert "Billy Graham Ministries".equals(constituent.getOrganizationName());
        assert "Graham".equals(constituent.getLastName());
        assert "Billy".equals(constituent.getFirstName());
        assert constituent.getMiddleName() == null;
        assert constituent.getSuffix() == null;
        assert "company1".equals(constituent.getSite().getName());
        
        assert constituent.getPersonCustomFields() != null && constituent.getPersonCustomFields().size() == 2;
        for (PersonCustomField personCustomField : constituent.getPersonCustomFields()) {
            if (personCustomField.getId() == 1000) {
                assert "organization.parent".equals(personCustomField.getCustomField().getName());
                assert "100005".equals(personCustomField.getCustomField().getValue());
            }
            else if (personCustomField.getId() == 2000) {
                assert "organization.subsidiaryList".equals(personCustomField.getCustomField().getName());
                assert "100002,100003,100004".equals(personCustomField.getCustomField().getValue());
            }
        }
    } 
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByLoginIdInvalid() throws Exception {
        Person constituent = constituentDao.readConstituentByLoginId("pablo@companyDoesNotExist.com");
        assert constituent == null;
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadConstituentByLoginId() throws Exception {
        Person constituent = constituentDao.readConstituentByLoginId("pablo@company1.com");
        assert constituent != null;
        assert constituent.getId() == 200;
        assert "Painters, Inc.".equals(constituent.getOrganizationName());
        assert "Picasso".equals(constituent.getLastName());
        assert "Pablo".equals(constituent.getFirstName());
        assert constituent.getMiddleName() == null;
        assert "Sr".equals(constituent.getSuffix());
        assert "company1".equals(constituent.getSite().getName());

        assert constituent.getPersonCustomFields() != null && constituent.getPersonCustomFields().isEmpty();
    }
    
    @Test(groups = { "testReadConstituent" })
    public void testReadAllConstituentsBySiteName() throws Exception {
        List<Person> constituents = constituentDao.readAllConstituentsBySite();
        assert constituents != null && constituents.size() == 3;
        for (Person constituent : constituents) {
            assert "company1".equals(constituent.getSite().getName());
            assert constituent.getId() == 200 || constituent.getId() == 300 || constituent.getId() == 100;
            assert "Painters, Inc.".equals(constituent.getOrganizationName()) || "Howdy Doody Inc".equals(constituent.getOrganizationName()) || "Billy Graham Ministries".equals(constituent.getOrganizationName());
            assert "Picasso".equals(constituent.getLastName()) || "Doody".equals(constituent.getLastName()) || "Graham".equals(constituent.getLastName());
            assert "Pablo".equals(constituent.getFirstName()) || "Howdy".equals(constituent.getFirstName()) || "Billy".equals(constituent.getFirstName());
            assert constituent.getMiddleName() == null;
            assert "Sr".equals(constituent.getSuffix()) || constituent.getSuffix() == null;
            
            if (constituent.getId() == 100) {
                for (PersonCustomField personCustomField : constituent.getPersonCustomFields()) {
                    if (personCustomField.getId() == 1000) {
                        assert "organization.parent".equals(personCustomField.getCustomField().getName());
                        assert "100005".equals(personCustomField.getCustomField().getValue());
                    }
                    else if (personCustomField.getId() == 2000) {
                        assert "organization.subsidiaryList".equals(personCustomField.getCustomField().getName());
                        assert "100002,100003,100004".equals(personCustomField.getCustomField().getValue());
                    }
                }
            }
            else if (constituent.getId() == 300) {
                assert constituent.getPersonCustomFields() != null && constituent.getPersonCustomFields().size() == 1;
                for (PersonCustomField personCustomField : constituent.getPersonCustomFields()) {
                    if (personCustomField.getId() == 3000) {
                        assert "organization.parent".equals(personCustomField.getCustomField().getName());
                        assert "100005".equals(personCustomField.getCustomField().getValue());
                    }
                }
            }
            else {
                assert constituent.getPersonCustomFields() != null && constituent.getPersonCustomFields().isEmpty();
            }
        }
    }
}
