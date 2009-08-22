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
import com.orangeleap.tangerine.dao.EmailDao;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Locale;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Email> readAllEmailsByConstituentId(Long constituentId, String sortPropertyName, String direction, int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllEmailsByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String,Object> params = setupSortParams(StringConstants.EMAIL, "EMAIL.EMAIL_LIST_RESULT", sortPropertyName, direction, start, limit, locale);
        params.put("asOfDate", getNowDate(locale));
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_EMAILS_BY_CONSITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_EMAIL_COUNT_BY_CONSTITUENT_ID", params);
    }
}