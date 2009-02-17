package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.Site;

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
        Site site1 = siteDao.createSite("company999", "12345", null);
        assert site1 != null;
        assert "company999".equals(site1.getName());
        assert "12345".equals(site1.getMerchantNumber());
        assert site1.getParentSite() == null;
        
        Site site1A = siteDao.createSite("company1A", "foo", site1);
        assert site1A != null;
        assert "company1A".equals(site1A.getName());
        assert "foo".equals(site1A.getMerchantNumber());
        assert site1A.getParentSite() != null;
        assert "company999".equals(site1A.getParentSite().getName());
    } 

    @Test(groups = { "testReadSite" }, dependsOnGroups = { "testCreateSite" })
    public void testReadSite() throws Exception {
        Site site1 = siteDao.readSite("company999");
        assert site1 != null;
        assert "company999".equals(site1.getName());
        assert "12345".equals(site1.getMerchantNumber());
        assert site1.getParentSite() == null;

        Site site1A = siteDao.readSite("company1A");
        assert site1A != null;
        assert "company1A".equals(site1A.getName());
        assert "foo".equals(site1A.getMerchantNumber());
        assert site1A.getParentSite() != null;
        assert "company999".equals(site1A.getParentSite().getName());
    } 
    
    @Test(groups = { "testReadSite" }, dependsOnGroups = { "testCreateSite" })
    public void testReadSites() throws Exception {
        List<Site> sites = siteDao.readSites();
        assert sites != null;
        assert sites.size() == 2;
        for (Site site : sites) {
            assert "company1A".equals(site.getName()) || "company999".equals(site.getName());
        }
    }
}
