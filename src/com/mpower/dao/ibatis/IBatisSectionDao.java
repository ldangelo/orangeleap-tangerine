package com.mpower.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.SectionDao;
import com.mpower.domain.model.customization.SectionDefinition;
import com.mpower.type.PageType;

/** 
 * Corresponds to the SECTION tables
 */
@Repository("sectionDAO")
public class IBatisSectionDao extends AbstractIBatisDao implements SectionDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisSectionDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitions(String siteName, PageType pageType, List<String> roles) {
        if (logger.isDebugEnabled()) {
            logger.debug("readSectionDefinitions: siteName = " + siteName + " pageType = " + pageType + " roles = " + roles);
        }
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put("siteName", siteName);
        params.put("pageType", pageType);
        params.put("roles", roles);
        return getSqlMapClientTemplate().queryForList("SELECT_BY_PAGE_TYPE_SITE_ROLES", params);
    }
}