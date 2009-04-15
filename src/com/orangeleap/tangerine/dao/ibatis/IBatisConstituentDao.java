package com.orangeleap.tangerine.dao.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.type.EntityType;

/** 
 * Corresponds to the CONSTITUENT tables
 */
@Repository("constituentDAO")
public class IBatisConstituentDao extends AbstractIBatisDao implements ConstituentDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisConstituentDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public Person maintainConstituent(Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainConstituent: constituent = " + constituent);
        }
        return (Person)insertOrUpdate(constituent, "CONSTITUENT");
    }

    @Override
    public Person readConstituentById(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        List<Long> personIds = new ArrayList<Long>(1);
        personIds.add(id);
        params.put("personIds", personIds);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @Override
    public Person readConstituentByCustomId(String id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentByCustomId: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("customId", id);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_CUSTOM_ID", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readConstituentsByIds(List<Long> ids) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentsByIds: ids = " + ids);
        }
        Map<String, Object> params = setupParams();
        params.put("personIds", ids);
        return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readAllConstituentsBySite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsBySite:");
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_CONSTITUENTS_BY_SITE", getSiteName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readAllConstituentsBySite(String sortColumn, String dir, int start, int limit) {

        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsBySite:");
        }

        Map<String,Object> params = setupParams();
        params.put("sortColumn", sortColumn);
        params.put("sortDir", dir);
        params.put("offset", start);
        params.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_CONSTITUENTS_BY_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<Person> readAllConstituentsByIdRange(String fromId, String toId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsByIdRange:");
        }

        Map<String,Object> params = setupParams();
        params.put("fromId", fromId);
        params.put("toId", toId);

        List list = getSqlMapClientTemplate().queryForList("SELECT_LIMITED_CONSTITUENTS_BY_ID_RANGE", params);
        if (list.size() > 5000) throw new RuntimeException("Selection too large, reduce selection range."); // Note this needs to be one less than the 5001 in constituent.xml
        return list;
    }

    @Override
    public int getConstituentCountBySite() {
        String site = getSiteName();
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_COUNT_BY_SITE", site);
    }

    @Override
    public Person readConstituentByLoginId(String loginId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentByLoginId: loginId = " + loginId);
        }
        Map<String, Object> params = setupParams();
        params.put("loginId", loginId);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_LOGIN_ID_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Person> searchConstituents(Map<String, Object> searchparams, List<Long> ignoreIds) {
    	Map<String, Object> params = setupParams();
    	if (ignoreIds == null) {
            ignoreIds = new ArrayList<Long>();
        }
    	if (ignoreIds.size() > 0) {
            params.put("ignoreIds", ignoreIds);
        }
    	QueryUtil.translateSearchParamsToIBatisParams(searchparams, params, new SearchFieldMapperFactory().getMapper(EntityType.person).getMap());
    	List<Person> persons = getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_SEARCH_TERMS", params);
    	return persons;
    }
   

}