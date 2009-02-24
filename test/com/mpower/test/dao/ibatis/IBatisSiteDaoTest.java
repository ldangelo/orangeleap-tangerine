package com.mpower.test.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.customization.EntityDefault;
import com.mpower.type.EntityType;

public class IBatisSiteDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private SiteDao siteDao;

    @BeforeMethod
    public void setupMocks() {
        siteDao = (SiteDao)super.applicationContext.getBean("siteDAO");
    }

    @Test(groups = { "testCreateSite" })
    public void testCreateSite() throws Exception {
        Site site = new Site("company999", "12345", null);
        Site site1 = siteDao.createSite(site);
        assert site1 != null;
        assert "company999".equals(site1.getName());
        assert "12345".equals(site1.getMerchantNumber());
        assert site1.getMerchantBin() == null;
        assert site1.getParentSite() == null;
        assert site1.getCreateDate() == null;
        assert site1.getUpdateDate() == null;
        
        site = new Site("company1A", "foo", "sss", site1);
        Site site1A = siteDao.createSite(site);
        assert site1A != null;
        assert "company1A".equals(site1A.getName());
        assert "foo".equals(site1A.getMerchantNumber());
        assert "sss".equals(site1A.getMerchantBin());
        assert site1A.getParentSite() != null;
        assert "company999".equals(site1A.getParentSite().getName());
        assert site1A.getCreateDate() == null;
        assert site1A.getUpdateDate() == null;
    } 

    @Test(groups = { "testReadSite" }, dependsOnGroups = { "testCreateSite" })
    public void testReadSite() throws Exception {
        Site site1 = siteDao.readSite("company999");
        assert site1 != null;
        assert "company999".equals(site1.getName());
        assert "12345".equals(site1.getMerchantNumber());
        assert site1.getMerchantBin() == null;
        assert site1.getParentSite() == null;
        assert site1.getCreateDate() != null;
        assert site1.getUpdateDate() != null;

        Site site1A = siteDao.readSite("company1A");
        assert site1A != null;
        assert "company1A".equals(site1A.getName());
        assert "foo".equals(site1A.getMerchantNumber());
        assert "sss".equals(site1A.getMerchantBin());
        assert site1A.getParentSite() != null;
        assert "company999".equals(site1A.getParentSite().getName());
        assert site1A.getCreateDate() != null;
        assert site1A.getUpdateDate() != null;
    } 
    
    @Test(groups = { "testReadSite" }, dependsOnGroups = { "testCreateSite" })
    public void testReadSites() throws Exception {
        List<Site> sites = siteDao.readSites();
        assert sites != null;
        assert sites.size() == 4;
        for (Site site : sites) {
            assert "company1A".equals(site.getName()) || "company999".equals(site.getName()) || "company1".equals(site.getName()) || "company2".equals(site.getName());
        }
    }
    
    @Test(groups = { "testUpdateSite" }, dependsOnGroups = { "testReadSite" })
    public void testUpdateSite() throws Exception {
        Site site999 = siteDao.readSite("company999");
        assert site999 != null;
        assert "company999".equals(site999.getName());
        assert "12345".equals(site999.getMerchantNumber());
        assert site999.getParentSite() == null;
        
        site999.setMerchantNumber("bar");
        site999.setParentSite(new Site("company1A"));
        siteDao.updateSite(site999);
        
        site999 = siteDao.readSite("company999");
        assert site999 != null;
        assert "company999".equals(site999.getName());
        assert "bar".equals(site999.getMerchantNumber());
        assert site999.getParentSite() != null;
        assert "company1A".equals(site999.getParentSite().getName());

        site999.setMerchantNumber(null);
        site999.setParentSite(null);
        siteDao.updateSite(site999);
        
        site999 = siteDao.readSite("company999");
        assert "company999".equals(site999.getName());
        assert site999.getMerchantNumber() == null;
        assert site999.getParentSite() == null;
    }

    @Test(groups = { "testCreateEntityDefault" })
    public void testCreateEntityDefault() throws Exception {
        EntityDefault entityDefault = new EntityDefault("check", "paymentType", EntityType.gift.toString(), "company1");
        entityDefault = siteDao.createEntityDefault(entityDefault);
        assert entityDefault != null;
        assert "check".equals(entityDefault.getDefaultValue());
        assert "paymentType".equals(entityDefault.getEntityFieldName());
        assert EntityType.gift.toString().equals(entityDefault.getEntityType());
        assert "company1".equals(entityDefault.getSiteName());
        assert entityDefault.getId() != null;
        assert entityDefault.getId() > 0;
    } 

    @Test(groups = { "testReadEntityDefaults" }, dependsOnGroups = { "testCreateEntityDefault" })
    public void testReadEntityDefaults() throws Exception {
        List<EntityType> types = new ArrayList<EntityType>(1);
        types.add(EntityType.gift);
        List<EntityDefault> entityDefaultList = siteDao.readEntityDefaults(types);
 
        assert entityDefaultList != null;
        assert entityDefaultList.size() == 1;
        EntityDefault entityDefault = entityDefaultList.get(0);
        assert entityDefault != null;
        assert "check".equals(entityDefault.getDefaultValue());
        assert "paymentType".equals(entityDefault.getEntityFieldName());
        assert EntityType.gift.toString().equals(entityDefault.getEntityType());
        assert "company1".equals(entityDefault.getSiteName());
        assert entityDefault.getId() != null;
        assert entityDefault.getId() > 0;
    } 

    @Test(groups = { "testUpdateEntityDefault" }, dependsOnGroups = { "testReadEntityDefaults" })
    public void testUpdateEntityDefault() throws Exception {
        List<EntityType> types = new ArrayList<EntityType>(1);
        types.add(EntityType.gift);
        List<EntityDefault> entityDefaultList = siteDao.readEntityDefaults(types);
 
        assert entityDefaultList != null;
        assert entityDefaultList.size() == 1;
        EntityDefault entityDefault = entityDefaultList.get(0);
        assert entityDefault != null;
        entityDefault.setDefaultValue("cash");
        siteDao.updateEntityDefault(entityDefault);
        
        entityDefaultList = siteDao.readEntityDefaults(types);
        assert entityDefaultList != null;
        assert entityDefaultList.size() == 1;
        entityDefault = entityDefaultList.get(0);
        assert "cash".equals(entityDefault.getDefaultValue());
        assert "paymentType".equals(entityDefault.getEntityFieldName());
        assert EntityType.gift.toString().equals(entityDefault.getEntityType());
        assert "company1".equals(entityDefault.getSiteName());
        assert entityDefault.getId() != null;
        assert entityDefault.getId() > 0;
    } 
    
    @Test(groups = { "testInsertException" }, dependsOnGroups = { "testCreateSite" }, expectedExceptions = org.springframework.dao.DataAccessException.class )
    public void testInsertException() throws DataAccessException {
        Site site = new Site("company999", "12345", null);
        siteDao.createSite(site);
    }
    
    @Test(groups = { "testNoInsertForUpdate" }, dependsOnGroups = { "testCreateSite" } )
    public void testNoInsertForUpdate() throws Exception {
        Site site = new Site("companyNotExistingYet", "12345", null);
        siteDao.updateSite(site);
        
        site = siteDao.readSite("companyNotExistingYet");
        assert site == null;
   }
}
