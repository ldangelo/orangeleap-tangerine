package com.mpower.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.EmailDao;
import com.mpower.domain.model.communication.Email;

/** 
 * Corresponds to the EMAIL table
 */
@Repository("emailDAO")
public class IBatisEmailDao extends AbstractIBatisDao implements EmailDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisEmailDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    public Email maintainEmail(Email email) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainEmail: email = " + email);
        }
        return (Email)insertOrUpdate(email, "EMAIL");
    }

    @Override
    public Email readEmailById(Long emailId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readEmailById: emailId = " + emailId);
        }
        return (Email)getSqlMapClientTemplate().queryForObject("SELECT_EMAIL_BY_EMAIL_ID", emailId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readEmailsByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readEmailsByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_EMAILS_BY_CONSTITUENT_ID", constituentId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readActiveEmailsByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActiveEmailsByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_EMAILS_BY_CONSTITUENT_ID", constituentId);
    }

    @Override
    public void inactivateEmails() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_EMAILS");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateEmails: number of emails marked inactive = " + rows);
        }
    }
}