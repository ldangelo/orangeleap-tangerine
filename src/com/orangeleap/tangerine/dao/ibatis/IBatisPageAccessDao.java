package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.domain.customization.PageAccess;

/** 
 * Corresponds to the PAGE_ACCESS table
 */
@Repository("pageAccessDAO")
public class IBatisPageAccessDao extends AbstractIBatisDao implements PageAccessDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisPageAccessDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PageAccess> readPageAccess(List<String> roles) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPageAccess: roles = " + roles);
        }
        Map<String, Object> params = setupParams();
        params.put("roleNames", roles);
        return getSqlMapClientTemplate().queryForList("SELECT_PAGE_ACCESS_BY_SITE_ROLES", params);
    }
}