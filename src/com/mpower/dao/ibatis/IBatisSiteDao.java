package com.mpower.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.SiteDao;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.customization.EntityDefault;
import com.mpower.type.EntityType;

/** 
 * Corresponds to the SITE table
 */
@Repository("siteDAO")
public class IBatisSiteDao extends AbstractIBatisDao implements SiteDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisSiteDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    public Site createSite(Site site) {
        if (logger.isDebugEnabled()) {
            logger.debug("readSite: siteName = " + site.getName());
        }
        getSqlMapClientTemplate().insert("INSERT_SITE", site);
        return site;
    }

    @Override
    public Site readSite(String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("readSite: siteName = " + siteName);
        }
        return (Site)getSqlMapClientTemplate().queryForObject("SELECT_BY_SITE_NAME", siteName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Site> readSites() {
        if (logger.isDebugEnabled()) {
            logger.debug("readSites:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_SITES");
    }
    
    @Override
    public int updateSite(Site site) {
        if (logger.isDebugEnabled()) {
            logger.debug("updateSite: site = " + site);
        }
        return getSqlMapClientTemplate().update("UPDATE_SITE", site);
    }

    @Override
    public EntityDefault createEntityDefault(EntityDefault entityDefault) {
        if (logger.isDebugEnabled()) {
            logger.debug("createEntityDefault: entityDefault = " + entityDefault);
        }
        Long id = (Long)getSqlMapClientTemplate().insert("INSERT_ENTITY_DEFAULT", entityDefault);
        entityDefault.setId(id);
        if (logger.isDebugEnabled()) {
            logger.debug("createEntityDefault: id = " + id);
        }
        return entityDefault;
    }    

    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDefault> readEntityDefaults(String siteName, List<EntityType> entityTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("readEntityDefaults: siteName = " + siteName);
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("siteName", siteName);
        params.put("entityTypes", entityTypes);
        return getSqlMapClientTemplate().queryForList("SELECT_BY_ENTITY_TYPE_AND_SITE", params);
    }
    
    @Override
    public int updateEntityDefault(EntityDefault entityDefault) {
        if (logger.isDebugEnabled()) {
            logger.debug("updateEntityDefault: entityDefault = " + entityDefault);
        }
        return getSqlMapClientTemplate().update("UPDATE_ENTITY_DEFAULT", entityDefault);
    }
}