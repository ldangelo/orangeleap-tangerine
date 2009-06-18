package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.EmailDao;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.StringConstants;

/** 
 * Corresponds to the EMAIL table
 */
@Repository("emailDAO")
public class IBatisEmailDao extends AbstractIBatisDao implements EmailDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisEmailDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Email maintainEntity(Email email) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainEntity: emailId = " + email.getId());
        }
        return (Email)insertOrUpdate(email, "EMAIL");
    }

    @Override
    public Email readById(Long emailId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: emailId = " + emailId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.EMAIL_ID, emailId);
        return (Email)getSqlMapClientTemplate().queryForObject("SELECT_EMAIL_BY_EMAIL_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_EMAILS_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readActiveByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readActiveByConstituentId: constituentId = " + constituentId);
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