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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.EntitySearch;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

/** 
 * Corresponds to the CONSTITUENT tables
 */
@Repository("constituentDAO")
public class IBatisConstituentDao extends AbstractIBatisDao implements ConstituentDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisConstituentDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public Constituent maintainConstituent(Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituent: constituentId = " + constituent.getId());
        }
        return (Constituent)insertOrUpdate(constituent, "CONSTITUENT");
    }

    @Override
    public Constituent readConstituentById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        List<Long> constituentIds = new ArrayList<Long>(1);
        constituentIds.add(id);
        params.put("constituentIds", constituentIds);
        return (Constituent)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @Override
    public Constituent readConstituentByAccountNumber(String accountNumber) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentByAccountNumber: accountNumber = " + accountNumber);
        }
        Map<String, Object> params = setupParams();
        params.put("accountNumber", accountNumber);
        return (Constituent)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_ACCOUNT_NUMBER", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Constituent> readConstituentsByIds(List<Long> ids) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentsByIds: ids = " + ids);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentIds", ids);
        return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Constituent> readAllConstituentsBySite() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsBySite:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_CONSTITUENTS_BY_SITE", getSiteName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Constituent> readAllConstituentsBySite(String sortPropertyName, String direction, int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsBySite: sortPropertyName = " + sortPropertyName + " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String,Object> params = setupSortParams(StringConstants.CONSTITUENT, "CONSTITUENT.CONSTITUENT_RESULT", sortPropertyName, direction, start, limit, locale);
        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_CONSTITUENTS_BY_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<Constituent> readAllConstituentsByAccountRange(Long fromId, Long toId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsByIdRange:");
        }

        Map<String,Object> params = setupParams();
        params.put("fromId", fromId);
        params.put("toId", toId);

        List list = getSqlMapClientTemplate().queryForList("SELECT_LIMITED_CONSTITUENTS_BY_ID_RANGE", params);
        if (list.size() > 5000) {
            throw new RuntimeException("Selection too large, reduce selection range."); // Note this needs to be one less than the 5001 in constituent.xml
        }
        return list;
    }

    @Override
    public int getConstituentCountBySite() {
        String site = getSiteName();
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_COUNT_BY_SITE", site);
    }

    @Override
    public Constituent readConstituentByLoginId(String loginId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readConstituentByLoginId: loginId = " + loginId);
        }
        Map<String, Object> params = setupParams();
        params.put("loginId", loginId);
        return (Constituent)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_LOGIN_ID_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Constituent> searchConstituents(Map<String, Object> searchparams, List<Long> ignoreIds) {
    	Map<String, Object> params = setupParams();
    	if (ignoreIds == null) {
            ignoreIds = new ArrayList<Long>();
        }
    	if (ignoreIds.size() > 0) {
            params.put("ignoreIds", ignoreIds);
        }
    	QueryUtil.translateSearchParamsToIBatisParams(searchparams, params, new SearchFieldMapperFactory().getMapper(EntityType.constituent).getMap());
    	List<Constituent> constituents = getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_SEARCH_TERMS", params);
    	return constituents;
    }

	@Override
	public List<Constituent> findConstituents(Map<String, Object> findparams,
			List<Long> ignoreIds) {
    	Map<String, Object> params = setupParams();
    	if (ignoreIds == null) {
            ignoreIds = new ArrayList<Long>();
        }
    	if (ignoreIds.size() > 0) {
            params.put("ignoreIds", ignoreIds);
        }
    	QueryUtil.translateSearchParamsToIBatisParams(findparams, params, new SearchFieldMapperFactory().getMapper(EntityType.constituent).getMap(),false);
    	List<Constituent> constituents = getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_FIND_TERMS", params);
		return constituents;
	}
   
    @Override
    public List<Constituent> searchConstituents(Map<String, Object> searchparams) {
        return searchConstituents(searchparams, null);
    }

    
    // Notes on mysql fulltext index:
    //
    // Words are broken up at non-alphanumeric chars (except underscore and apostrophe)
    // To get any matches, there must be at least 3 records in the db since any search term that matches over a certain percentage of the rows is excluded.
    // There are about 500 common 'stop' words that are never indexed.
    // The result potentially contains rows that match any of the words in the search string, unless the words are prefixed by + (must have) or - (must not have).
    // See also: http://dev.mysql.com/tech-resources/articles/full-text-revealed.html
    @SuppressWarnings("unchecked")
	@Override
    public List<Constituent> fullTextSearchConstituents(String searchText) {
    	Map<String, Object> params = setupParams();
        params.put("searchText", searchText);
        params.put("entityType", "constituent");
        
    	List<EntitySearch> list = getSqlMapClientTemplate().queryForList("SELECT_ENTITY_SEARCH_BY_SEARCH_STRING", params);
    	
    	List<Constituent> constituents = new ArrayList();
    	for (EntitySearch es: list) constituents.add(this.readConstituentById(es.getEntityId()));
    	return constituents;
    }


}