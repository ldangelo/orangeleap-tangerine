package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.EmailDao;
import com.mpower.domain.model.communication.Email;
import com.mpower.util.StringConstants;

/** 
 * Corresponds to the EMAIL table
 */
@Repository("emailDAO")
public class IBatisEmailDao extends AbstractIBatisDao implements EmailDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisEmailDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Email maintainEntity(Email email) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainEntity: email = " + email);
        }
        return (Email)insertOrUpdate(email, "EMAIL");
    }

    @Override
    public Email readById(Long emailId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readById: emailId = " + emailId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.EMAIL_ID, emailId);
        return (Email)getSqlMapClientTemplate().queryForObject("SELECT_EMAIL_BY_EMAIL_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_EMAILS_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readActiveByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActiveByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_EMAILS_BY_CONSTITUENT_ID", params);
    }

    @Override
    public void inactivateEntities() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_EMAILS");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateEntities: number of emails marked inactive = " + rows);
        }
    }
}