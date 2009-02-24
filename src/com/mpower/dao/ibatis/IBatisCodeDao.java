package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.CodeDao;
import com.mpower.domain.model.customization.Code;
import com.mpower.domain.model.customization.CodeType;

/** 
 * Corresponds to the CODE and Code_TYPE tables
 */
@Repository("codeDAO")
public class IBatisCodeDao extends AbstractIBatisDao implements CodeDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisCodeDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }
    
/// Code Types    
    
    @SuppressWarnings("unchecked")
	@Override
	public List<String> listCodeTypes() {
        if (logger.isDebugEnabled()) {
            logger.debug("listCodeTypes:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_CODE_TYPE_DISTINCT", params);
	}

	@Override
	public CodeType readCodeType(String codeType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodeType: " + codeType);
        }
        Map<String, Object> params = setupParams();
        params.put("name", codeType);
        return (CodeType)getSqlMapClientTemplate().queryForObject("SELECT_CODE_TYPE_BY_NAME", params);
	}
	
////  Codes
	
    @SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String codeType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodes: " + codeType);
        }
        Map<String, Object> params = setupParams();
        params.put("codeType", codeType);
        return getSqlMapClientTemplate().queryForList("SELECT_CODES_BY_CODE_TYPE", params);
	}

	@Override
	public Code readCodeBySiteTypeValue(String codeType, String codeValue) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodeBySiteTypeValue: " + codeType + "," + codeValue);
        }
        Map<String, Object> params = setupParams();
        
        params.put("codeType", codeType);
        params.put("value", codeValue);
        return (Code)getSqlMapClientTemplate().queryForObject("SELECT_CODE_BY_ACTIVE_CODE_AND_CODE_TYPE_AND_CODE_VALUE", params);
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String codeType,
			String startsWith) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodes: " + codeType);
        }
        Map<String, Object> params = setupParams();
        params.put("codeType", codeType);
        params.put("value", startsWith + "%");

        return getSqlMapClientTemplate().queryForList("SELECT_CODES_BY_CODE_TYPE_AND_CODE_VALUE", params);
	}


    @SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String codeType,
			String startsWith, String partialDescription, Boolean inactive) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodes: " + codeType);
        }
        Map<String, Object> params = setupParams();
        String query;
		if (inactive!=null) {
			query = "SELECT_CODES_BY_ACTIVE_AND_CODE_TYPE_AND_CODE_VALUE_AND_CODE_DESCRIPTION";
			params.put("inactive", inactive);
		} else {
			query = "SELECT_CODES_BY_CODE_TYPE_AND_CODE_VALUE_AND_CODE_DESCRIPTION";
		}
        params.put("codeType", codeType);
		params.put("value", startsWith + "%");
		params.put("description", "%" + partialDescription + "%");

        return getSqlMapClientTemplate().queryForList(query, params);
	}
    

	
	
	@Override
	public Code maintainCode(Code code) {
		return (Code)insertOrUpdate(code, "CODE");
	}

	@Override
	public Code readCode(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCode: "+id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (Code)getSqlMapClientTemplate().queryForObject("SELECT_CODE_BY_ID", params);
	}



}