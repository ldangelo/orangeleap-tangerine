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
import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Corresponds to the CACHE_GROUP table
 */
@Repository("cacheGroupDAO")
public class IBatisCacheGroupDao extends AbstractIBatisDao implements CacheGroupDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisCacheGroupDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CacheGroup> readCacheGroups() {
        if (logger.isTraceEnabled()) {
            logger.trace("readCacheGroups ");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_CACHE_GROUPS", params);
    }

    @Override
    public int updateCacheGroupTimestamp(CacheGroupType id) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateCacheGroupTimestamp: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return getSqlMapClientTemplate().update("UPDATE_CACHE_GROUP_BY_ID", params);
    }

}