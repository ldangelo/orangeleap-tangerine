package com.mpower.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.PageAccessDao;
import com.mpower.domain.model.customization.PageAccess;

/** 
 * Corresponds to the PAGE_ACCESS table
 */
@Repository("pageAccessDAO")
public class IBatisPageAccessDao extends AbstractIBatisDao implements PageAccessDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisPageAccessDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PageAccess> readPageAccess(String siteName, List<String> roles) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPageAccess: siteName = " + siteName + " roles = " + roles);
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("siteName", siteName);
        params.put("roleNames", roles);
        return getSqlMapClientTemplate().queryForList("SELECT_PAGE_ACCESS_BY_SITE_ROLES", params);
    }
}