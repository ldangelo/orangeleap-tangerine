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

package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

/**
 * DAO used to track Most Recently Used (MRU) accounts for a user
 * of a site.
 * @version 1.0
 */
public interface RecentlyViewedDao {

    /**
     * Add the given acctNumber to the MRU list and return a new version
     * of the list with the new account number in the first position.
     * @param userName the user name to associate the list with
     * @param acctNumber the account number to add to the list
     * @param max the maximum size of the list
     * @return the list of MRU account numbers
     */
    @SuppressWarnings("unchecked")
    public List<Map> addRecentlyViewed(String userName, Long acctNumber, int max);

    /**
     * Retrieve the list of MRU acounts for the user
     * @param userName the user name to get the list for
     * @return the MRU list of account numbers
     */
    @SuppressWarnings("unchecked")
    public List<Map> getRecentlyViewed(String userName);


}
