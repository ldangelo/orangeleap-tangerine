package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.SiteOptionDao;
import com.orangeleap.tangerine.domain.SiteOption;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the SiteOption table
 */
@Repository("siteOptionDAO")
public class IBatisSiteOptionDao extends AbstractIBatisDao implements SiteOptionDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisSiteOptionDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public SiteOption maintainSiteOption(SiteOption siteOption) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainSiteOption: siteOptionId = " + siteOption.getId());
        }
        return (SiteOption)insertOrUpdate(siteOption, "SITE_OPTION");
    }

    @Override
    public SiteOption readSiteOptionById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSiteOptionById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (SiteOption)getSqlMapClientTemplate().queryForObject("SELECT_SITE_OPTION_BY_ID", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SiteOption> readSiteOptions() {
        if (logger.isTraceEnabled()) {
            logger.trace("readSiteOptions:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_SITE_OPTIONS", params);
    }

    
}