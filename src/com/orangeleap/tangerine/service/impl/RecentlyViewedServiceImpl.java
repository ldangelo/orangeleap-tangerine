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

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.dao.RecentlyViewedDao;
import com.orangeleap.tangerine.service.RecentlyViewedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
@Service("recentlyViewedService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecentlyViewedServiceImpl extends AbstractTangerineService implements RecentlyViewedService {

    @Resource(name = "recentlyViewedDAO")
    private RecentlyViewedDao recentlyViewedDao;

    private int maxRows = 15; // max values in recently used

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Map> addRecentlyViewed(Long acctNumber) {

        String userName = tangerineUserHelper.lookupUserName();
        return recentlyViewedDao.addRecentlyViewed(userName, acctNumber, maxRows);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Map> getRecentlyViewed() {

        String userName = tangerineUserHelper.lookupUserName();
        return recentlyViewedDao.getRecentlyViewed(userName);

    }
}
