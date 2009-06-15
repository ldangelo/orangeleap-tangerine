package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.domain.customization.PageAccess;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;

public class IBatisPageAccessDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PageAccessDao pageAccessDao;

    @BeforeMethod
    public void setupMocks() {
        pageAccessDao = (PageAccessDao)super.applicationContext.getBean("pageAccessDAO");
    }

    @Test
    public void testReadPageAccess() throws Exception {
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        List<PageAccess> pageAccessList = pageAccessDao.readPageAccess(roles);
        
        assert pageAccessList != null;
        assert pageAccessList.size() == 3;
        for (PageAccess pageAccess : pageAccessList) {
            assert AccessType.DENIED.equals(pageAccess.getAccessType());
            assert pageAccess.getSite() != null;
            assert "company1".equals(pageAccess.getSite().getName());
            assert "ROLE_USER".equals(pageAccess.getRole());
            assert PageType.giftList.equals(pageAccess.getPageType()) || PageType.giftSearch.equals(pageAccess.getPageType()) || PageType.giftSearchResults.equals(pageAccess.getPageType()); 
        }

        roles = new ArrayList<String>();
        roles.add("ROLE_DOES_NOT_EXIST");
        pageAccessList = pageAccessDao.readPageAccess(roles);
        assert pageAccessList != null && pageAccessList.isEmpty();

        roles = new ArrayList<String>();
        roles.add("ROLE_SUPER_USER");
        roles.add("ROLE_DOOFUS");
        pageAccessList = pageAccessDao.readPageAccess(roles);
        assert pageAccessList != null;
        assert pageAccessList.size() == 4;
        for (PageAccess pageAccess : pageAccessList) {
            if ("ROLE_SUPER_USER".equals(pageAccess.getRole())) {
                assert AccessType.READ_ONLY.equals(pageAccess.getAccessType());
                assert pageAccess.getSite() != null;
                assert "company1".equals(pageAccess.getSite().getName());
                assert "ROLE_SUPER_USER".equals(pageAccess.getRole());
                assert PageType.giftList.equals(pageAccess.getPageType()) || PageType.giftSearch.equals(pageAccess.getPageType()) || PageType.giftSearchResults.equals(pageAccess.getPageType());
            }
            else {
                assert AccessType.DENIED.equals(pageAccess.getAccessType());
                assert pageAccess.getSite() == null;
                assert "ROLE_DOOFUS".equals(pageAccess.getRole());
                assert PageType.constituent.equals(pageAccess.getPageType());
            }
        }
    } 
}
