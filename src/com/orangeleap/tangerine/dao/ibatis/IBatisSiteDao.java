/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.SiteDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Corresponds to the SITE table
 */
@Repository("siteDAO")
public class IBatisSiteDao extends AbstractIBatisDao implements SiteDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisSiteDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Site createSite(Site site) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSite: siteName = " + site.getName());
        }
        getSqlMapClientTemplate().insert("INSERT_SITE", site);
        return site;
    }

    @Override
    public Site readSite(String siteName) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSite: siteName = " + siteName);
        }
        return (Site) getSqlMapClientTemplate().queryForObject("SELECT_BY_SITE_NAME", siteName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Site> readSites() {
        if (logger.isTraceEnabled()) {
            logger.trace("readSites:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_SITES");
    }

    @Override
    public int updateSite(Site site) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateSite: site = " + site);
        }
        return getSqlMapClientTemplate().update("UPDATE_SITE", site);
    }

    @Override
    public EntityDefault createEntityDefault(EntityDefault entityDefault) {
        if (logger.isTraceEnabled()) {
            logger.trace("createEntityDefault: entityDefault = " + entityDefault);
        }
        Long id = (Long) getSqlMapClientTemplate().insert("INSERT_ENTITY_DEFAULT", entityDefault);
        entityDefault.setId(id);
        if (logger.isDebugEnabled()) {
            logger.debug("createEntityDefault: id = " + id);
        }
        return entityDefault;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDefault> readEntityDefaults(List<EntityType> entityTypes) {
        if (logger.isTraceEnabled()) {
            logger.trace("readEntityDefaults: entityTypes = " + entityTypes);
        }
        Map<String, Object> params = setupParams();
        params.put("entityTypes", entityTypes);
        return getSqlMapClientTemplate().queryForList("SELECT_BY_ENTITY_TYPE_AND_SITE", params);
    }

    @Override
    public int updateEntityDefault(EntityDefault entityDefault) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateEntityDefault: entityDefault = " + entityDefault);
        }
        return getSqlMapClientTemplate().update("UPDATE_ENTITY_DEFAULT", entityDefault);
    }
}