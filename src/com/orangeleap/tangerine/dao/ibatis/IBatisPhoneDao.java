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
import com.orangeleap.tangerine.dao.PhoneDao;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Locale;

/** 
 * Corresponds to the PHONE table
 */
@Repository("phoneDAO")
public class IBatisPhoneDao extends AbstractIBatisDao implements PhoneDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisPhoneDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public Phone maintainEntity(Phone phone) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainEntity: phoneId = " + phone.getId());
        }
        return (Phone)insertOrUpdate(phone, "PHONE");
    }

    @Override
    public Phone readById(Long phoneId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: phoneId = " + phoneId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.PHONE_ID, phoneId);
        return (Phone)getSqlMapClientTemplate().queryForObject("SELECT_PHONE_BY_PHONE_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Phone> readByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_PHONES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Phone> readActiveByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readActiveByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_PHONES_BY_CONSTITUENT_ID", params);
    }
    
    @Override
    public void inactivateEntities() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_PHONES");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateEntities: number of phones marked inactive = " + rows);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Phone> readAllPhonesByConstituentId(Long constituentId, String sortPropertyName, String direction, int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPhonesByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String,Object> params = setupSortParams(StringConstants.PHONE, "PHONE.PHONE_LIST_RESULT", sortPropertyName, direction, start, limit, locale);
        params.put("asOfDate", getNowDate(locale));
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_PHONES_BY_CONSITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_PHONE_COUNT_BY_CONSTITUENT_ID", params);
    }
}