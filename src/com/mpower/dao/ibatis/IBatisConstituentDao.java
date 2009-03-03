package com.mpower.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
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

    @Override
    public Person readConstituentByLoginId(String loginId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readConstituentByLoginId: loginId = " + loginId);
        }
        Map<String, Object> params = setupParams();
        params.put("loginId", loginId);
        return (Person)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_BY_LOGIN_ID_SITE", params);
    }

    @Override
    public void setLapsedDonor(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("setLapsedDonor: constituentId = " + constituentId);
        }
        getSqlMapClientTemplate().update("SET_LAPSED_DONOR", constituentId);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Person> searchPersons(Map<String, Object> searchparams, List<Long> ignoreIds) {
    	
    	if (ignoreIds == null) ignoreIds = new ArrayList<Long>();
        Map<String, Object> addressParams = new HashMap<String, Object>();
        Map<String, Object> phoneParams = new HashMap<String, Object>();
        Map<String, Object> emailParams = new HashMap<String, Object>();
        Map<String, String> customParams = new HashMap<String, String>();
        Map<String, String> stringParams = new HashMap<String, String>();
        Map<String, Object> nonStringParams = new HashMap<String, Object>();
        
        if (searchparams != null) {
            for (Map.Entry<String, Object> pair : searchparams.entrySet()) {
                String key = pair.getKey();
                Object value = pair.getValue();

                boolean isString = true;
                if (value instanceof String) {
                    if (GenericValidator.isBlankOrNull((String) value)) {
                        continue;
                    }
                } 
                else {
                    if (value == null) {
                        continue;
                    }
                    isString = false;
                }
                if (key.startsWith("addressMap[")) {
                    addressParams.put(key.substring(key.indexOf('.') + 1), "%" + value + "%");
                } 
                else if (key.startsWith("phoneMap[")) {
                    phoneParams.put(key.substring(key.indexOf('.') + 1), "%" + value + "%");
                } 
                else if (key.startsWith("emailMap[")) {
                    emailParams.put(key.substring(key.indexOf('.') + 1), "%" + value + "%");
                } 
                else if (key.startsWith("customFieldMap[")) {
                    customParams.put(key.substring(key.indexOf('[') + 1, key.indexOf(']')), "%" + value + "%");
                } 
                else {
                    if (isString) {
                    	// These use LIKE
                    	stringParams.put(key, "%" + value + "%");
                    } else {
                    	// These use =
                    	nonStringParams.put(key, value);
                    }
                }
            }
        }
        
        mapParmsToColumns(addressParams);
        mapParmsToColumns(phoneParams);
        mapParmsToColumns(emailParams);
        mapParmsToColumns(stringParams);
        mapParmsToColumns(nonStringParams);
        
    	Map<String, Object> params = setupParams();
        if (addressParams.size() > 0) params.put("addressParams", addressParams.entrySet().toArray());
        if (phoneParams.size() > 0) params.put("phoneParams", phoneParams.entrySet().toArray());
        if (emailParams.size() > 0) params.put("emailParams", emailParams.entrySet().toArray());
        if (customParams.size() > 0) params.put("customParams", customParams.entrySet().toArray());
        if (stringParams.size() > 0) params.put("stringParams", stringParams.entrySet().toArray());
        if (nonStringParams.size() > 0) params.put("nonStringParams", nonStringParams.entrySet().toArray());
    	if (ignoreIds.size() > 0) params.put("ignoreIds", ignoreIds);
    	
    	
    	List<Person> persons = getSqlMapClientTemplate().queryForList("SELECT_CONSTITUENT_BY_SEARCH_TERMS", params);
    	return persons;
        
    }
    
    @SuppressWarnings("unchecked")
	private void mapParmsToColumns(Map params) {
    	Map result = new HashMap();
    	Iterator it = params.entrySet().iterator();
    	while (it.hasNext()) {
    		Map.Entry e = (Map.Entry)it.next();
    		String key = (String) e.getKey();
    		Object value = e.getValue();
    		String columnKey = (String) fieldMap.get(key);
    		if (columnKey != null) result.put(columnKey, value);
    	}
    	params.clear();
    	params.putAll(result);
    }
    
    // These are the fields we support for searching.
    private Map<String, String> fieldMap = new HashMap<String, String>();
    {
    	
    	// Constituent
    	fieldMap.put("accountNumber", "PERSON_ID");
    	fieldMap.put("firstName", "FIRST_NAME");
    	fieldMap.put("lastName", "LAST_NAME");
    	fieldMap.put("organizationName", "ORGANIZATION_NAME");

    	// Email
    	fieldMap.put("email", "EMAIL_ADDRESS");

    	// Address
    	fieldMap.put("addressLine1", "ADDRESS_LINE_1");
    	fieldMap.put("city", "CITY");
    	fieldMap.put("stateProvince", "STATE_PROVINCE");
    	fieldMap.put("postalCode", "POSTAL_CODE");

    	// Phone
    	fieldMap.put("number", "NUMBER");
    	
    }

    
}