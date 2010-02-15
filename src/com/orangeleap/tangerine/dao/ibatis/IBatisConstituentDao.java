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
import java.util.Set;

import org.apache.commons.logging.Log;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.EntitySearch;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.DistanceUtil;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;

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

    @Override
    public List<Constituent> readAllConstituentsBySite(String sortPropertyName, String direction, int start, int limit, Locale locale) {
        return readAllConstituentsBySite(sortPropertyName, direction, start, limit, locale, 0);
    }

    @Override
    public List<Constituent> readAllUpdatedConstituentsBySite(String sortPropertyName, String direction, int start, int limit, Locale locale, int recentDays) {
        return readAllConstituentsBySite(sortPropertyName, direction, start, limit, locale, recentDays);
    }

    @SuppressWarnings("unchecked")
    private List<Constituent> readAllConstituentsBySite(String sortPropertyName, String direction, int start, int limit, Locale locale, int recentDays) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllConstituentsBySite: sortPropertyName = " + sortPropertyName + " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String,Object> params = setupSortParams(StringConstants.CONSTITUENT, "CONSTITUENT.CONSTITUENT_RESULT", sortPropertyName, direction, start, limit, locale);
        if (recentDays > 0) params.put("recentDays", recentDays);
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
    public List<Constituent> searchConstituents(Map<String, Object> parameters, String sortPropertyName, String direction, int start, int limit, Locale locale) {
        return searchConstituents(parameters, false, sortPropertyName, direction, start, limit, locale);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Constituent> searchConstituents(Map<String, Object> parameters, boolean parametersStartWith, String sortPropertyName, String direction, int start, int limit, Locale locale) {
        Map<String, Object> params = setupSortParams(StringConstants.CONSTITUENT, "CONSTITUENT.CONSTITUENT_SEARCH_RESULT", sortPropertyName, direction, start, limit, locale);
        List<Map<String,Object>> searchColumnList = setupSearchParams(parameters, parametersStartWith, PropertyAccessorFactory.forBeanPropertyAccess(new Constituent()), "CONSTITUENT.CONSTITUENT_SEARCH_RESULT");
        params.put("searchTerms", searchColumnList);
        addAdditionalWhere(params, parameters);
    	return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_SEARCH_TERMS", params);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Constituent> searchConstituents(Map<String, Object> parameters) {
        Map<String, Object> params = setupParams();
        List<Map<String,Object>> searchColumnList = setupSearchParams(parameters, PropertyAccessorFactory.forBeanPropertyAccess(new Constituent()), "CONSTITUENT.CONSTITUENT_SEARCH_RESULT");
        params.put("searchTerms", searchColumnList);
        addAdditionalWhere(params, parameters);
    	return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_SEARCH_TERMS", params);
    }

    private void addAdditionalWhere(Map<String, Object> returnParameters, Map<String, Object> enteredParameters) {
        if (StringUtils.hasText((String) enteredParameters.get(QueryUtil.ADDITIONAL_WHERE))) {
            returnParameters.put(QueryUtil.ADDITIONAL_WHERE, enteredParameters.get(QueryUtil.ADDITIONAL_WHERE));
        }
    }

	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public List<Constituent> readConstituentsBySegmentationReportIds(final Set<Long> reportIds, String sortPropertyName, String direction,
	                                                     int start, int limit, Locale locale) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("readConstituentsBySegmentationReportIds: reportIds = " + reportIds + " sortPropertyName = " + sortPropertyName +
	                " direction = " + direction + " start = " + start + " limit = " + limit);
	    }
	    Map<String, Object> params = setupSortParams(StringConstants.CONSTITUENT, "CONSTITUENT.CONSTITUENT_RESULT",
	            sortPropertyName, direction, start, limit, locale);
	    params.put("reportIds", new ArrayList<Long>(reportIds));

	    return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENTS_BY_SEGMENTATION_REPORT_ID", params);
	}

	@Override
	public int readCountConstituentsBySegmentationReportIds(final Set<Long> reportIds) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("readCountConstituentsBySegmentationReportIds: reportIds = " + reportIds);
	    }
	    Map<String,Object> params = setupParams();
	    params.put("reportIds", new ArrayList<Long>(reportIds));
	    return (Integer) getSqlMapClientTemplate().queryForObject("COUNT_CONSTITUENTS_BY_SEGMENTATION_REPORT_ID", params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Constituent> readAllConstituentsBySegmentationReportIds(final Set<Long> reportIds) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("readAllConstituentsBySegmentationReportIds: reportIds = " + reportIds);
	    }
	    final Map<String, Object> params = setupParams();
	    params.put("reportIds", new ArrayList<Long>(reportIds));

	    return getSqlMapClientTemplate().queryForList("SELECT_ALL_CONSTITUENTS_BY_SEGMENTATION_REPORT_ID", params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Constituent> readLimitedConstituentsByIds(Set<Long> constituentIds, String sortPropertyName, String direction,
	                                                     int start, int limit, Locale locale) {
	    if (logger.isTraceEnabled()) {
	        logger.trace("readLimitedConstituentsByIds: constituentIds = " + constituentIds + " sortPropertyName = " + sortPropertyName +
	                " direction = " + direction + " start = " + start + " limit = " + limit);
	    }
	    Map<String, Object> params = setupSortParams(StringConstants.CONSTITUENT, "CONSTITUENT.CONSTITUENT_RESULT",
	            sortPropertyName, direction, start, limit, locale);
	    params.put("constituentIds", new ArrayList<Long>(constituentIds));

	    return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_CONSTITUENTS_BY_CONSTITUENT_IDS", params);
	}

	/**
	 * Notes on mysql fulltext index:
     *
     * Words are broken up at non-alphanumeric chars (except underscore and apostrophe)
     * To get any matches, there must be at least 3 records in the db since any search term that matches over a certain percentage of the rows is excluded.
     * There are about 500 common 'stop' words that are never indexed.
     * The result potentially contains rows that match any of the words in the search string, unless the words are prefixed by + (must have) or - (must not have).
     * See also: http://dev.mysql.com/tech-resources/articles/full-text-revealed.html
	 */
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

	@Override
	public Long[] getConstituentIdRange() {
    	Map<String, Object> params = setupParams();
    	Long min = (Long)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_MIN_ID", params);
    	Long max = (Long)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_MAX_ID", params);
    	return new Long[] {min, max};
	}

	@Override
	public void setConstituentFlags() {
        if (logger.isInfoEnabled()) {
            logger.info("setConstituentFlags");
        }
        getSqlMapClientTemplate().queryForObject("SET_CONSTITUENT_FLAGS");
	}

	@Override
	public void currentUpdateDateForConstituent(Long constituentId) {
    	Map<String, Object> params = setupParams();
    	params.put("id", constituentId);
		getSqlMapClientTemplate().update("SET_CONSTITUENT_UPDATE_DATE", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Constituent> getConstituentsWithinMilesOfConstituent(
			Constituent constituent, int miles) {
		List<Constituent> result = new ArrayList<Constituent>();
		Address address = constituent.getPrimaryAddress();
		if (address == null) return result;
		List<String> zips = DistanceUtil.getZipsWithinMilesOfZip(address.getPostalCode(), miles);
		if (zips.size() == 0) return result;
    	Map<String, Object> params = setupParams();
    	params.put("zips", zips);
	    return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_POSTAL_CODE", params);
	}
	
}