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
import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.domain.customization.PageAccess;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Corresponds to the PAGE_ACCESS table
 */
@Repository("pageAccessDAO")
public class IBatisPageAccessDao extends AbstractIBatisDao implements PageAccessDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisPageAccessDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PageAccess> readPageAccess(List<String> roles) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPageAccess: roles = " + roles);
        }

        // User has no roles set up
        if (roles == null || roles.size() == 0) {
            return new ArrayList<PageAccess>();
        }

        Map<String, Object> params = setupParams();
        params.put("roleNames", roles);
        return getSqlMapClientTemplate().queryForList("SELECT_PAGE_ACCESS_BY_SITE_ROLES", params);
    }
}