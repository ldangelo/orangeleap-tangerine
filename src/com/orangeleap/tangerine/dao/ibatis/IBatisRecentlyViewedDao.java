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
import com.orangeleap.tangerine.dao.RecentlyViewedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
@Repository("recentlyViewedDAO")
public class IBatisRecentlyViewedDao extends AbstractIBatisDao implements RecentlyViewedDao {

    // the maxium length of the CSV string. Should correspond
    // to the DDL value for the varchar length used for holding it
    private final int MAX_LEN = 255;

    @Autowired
    public IBatisRecentlyViewedDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map> addRecentlyViewed(String userName, Long acctNumber, int max) {
        // get the current MRU list
        List<Long> recentlyViewed = getRecentlyViewedIds(userName);

        // de-dupe so we don't insert a value already on the list
        if (recentlyViewed.contains(acctNumber)) {
            return getRecentlyViewedNames(recentlyViewed, userName);
        }

        // if we don't have a dupe, ensure we have space for the new accountNumber
        while (recentlyViewed.size() >= max) {
            recentlyViewed.remove(recentlyViewed.size() - 1);
        }

        // flatten the bugger out to a CSV String
        String flatList = flattenList(recentlyViewed, acctNumber);

        // Sanity Check: we're only using a varchar(MAX_LEN) to hold the list, so
        // ensure the value is not too long by stripping off more accounts if necessary
        if (flatList.length() > MAX_LEN) {
            flatList = shortenList(recentlyViewed, acctNumber);
        }

        Map<String, Object> params = setupParams();
        params.put("userName", userName);
        params.put("recentlyViewed", flatList);

        getSqlMapClientTemplate().insert("INSERT_RECENTLY_VIEWED", params);

        // Finally, create the List to return, which has the new value at the head of the list
        List<Long> constituentIds = new ArrayList<Long>();
        constituentIds.add(acctNumber);
        constituentIds.addAll(recentlyViewed);

        return getRecentlyViewedNames(constituentIds, userName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map> getRecentlyViewed(String userName) {

        List<Long> constituentIds = getRecentlyViewedIds(userName);
        return getRecentlyViewedNames(constituentIds, userName);
    }

    /*
        Return a list of the recently veiwed constituent IDs for the
        given user name
     */
    private List<Long> getRecentlyViewedIds(String userName) {

        Map<String, Object> params = setupParams();
        params.put("userName", userName);

        String stringList = (String) getSqlMapClientTemplate().queryForObject("SELECT_RECENTLY_VIEWED", params);

        String[] splitList = StringUtils.commaDelimitedListToStringArray(stringList);

        List<Long> ret = new ArrayList<Long>();

        for (String s : splitList) {
            ret.add(Long.valueOf(s));
        }

        return ret;
    }

    /*
        Return a List of Maps with the recently viewed contituents for
        the user name. The map contains the id, firstName and lastName, which
        is all that is needed for the MRU list.
     */
    @SuppressWarnings("unchecked")
    private List<Map> getRecentlyViewedNames(List<Long> constituentIds, String userName) {

        if (constituentIds.size() == 0) {
            return new ArrayList<Map>();
        }

        Map<String, Object> params = setupParams();
        params.put("userName", userName);
        params.put("constituentIds", constituentIds);
        List<Map> people = getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_FLYWEIGHT", params);

        // the People list is not ordered correctly, since it relies
        // on a SQL 'IN' clause. Put it back into the correct order
        // before returning it.
        List<Map> ret = new ArrayList<Map>();

        for (Long id : constituentIds) {

            for (Map map : people) {
                if (id.equals(map.get("id"))) {
                    ret.add(map);
                    break;
                }
            }
        }

        return ret;
    }


    /*
       Utility method that flattens the given list into a CSV string, with
       the first value in the string being the newValue
    */
    public String flattenList(List<Long> list, Long newValue) {

        StringBuilder builder = new StringBuilder();
        builder.append(newValue);
        for (Long longValue : list) {
            builder.append(",").append(longValue);
        }

        return builder.toString();
    }

    /*
        Utility method to trim down a CSV string that is longer than
        the MAX_LEN constant, using the values in the given list to
        adjust it. The maximum length is shortened by removing values
        from the end of the list, then running the flatten operation again.
     */
    public String shortenList(List<Long> list, Long newValue) {

        String current = flattenList(list, newValue);

        while (current.length() > MAX_LEN) {
            list.remove(list.size() - 1);
            current = flattenList(list, newValue);
        }

        return current;
    }

}
