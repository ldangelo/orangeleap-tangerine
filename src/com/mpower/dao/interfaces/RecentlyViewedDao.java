package com.mpower.dao.interfaces;

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
