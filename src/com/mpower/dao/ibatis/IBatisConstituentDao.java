package com.mpower.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.Person;

/** 
 * Corresponds to the PERSON (Constituent) tables
 */
@Repository("constituentDAO")
public class IBatisConstituentDao extends AbstractIBatisDao implements ConstituentDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisConstituentDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }
    
    @Override
    public Person readConstituentById(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentById: id = " + id);
        }
        Map<String, List<Long>> params = new HashMap<String, List<Long>>(1);
        List<Long> personIds = new ArrayList<Long>(1);
        personIds.add(id);
        params.put("personIds", personIds);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readConstituentsByIdsSite(String siteName, List<Long> ids) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentsByIdsSite: siteName = " + siteName + " ids = " + ids);
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("siteName", siteName);
        params.put("personIds", ids);
        return getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_IDS_SITE", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Person> readAllConstituentsBySite(String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllConstituentsBySiteName: siteName = " + siteName);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_CONSTITUENTS_BY_SITE", siteName);
    }

    @Override
    public Person readConstituentByLoginIdSite(String loginId, String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentByLoginIdSite: loginId = " + loginId + " siteName = " + siteName);
        }
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("loginId", loginId);
        params.put("siteName", siteName);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_LOGIN_ID_SITE", params);
    }
}