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
import com.orangeleap.tangerine.dao.AddressDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * iBatis version of the AddressDao
 * @version 1.0
 */
@Repository("addressDAO")
public class IBatisAddressDao extends AbstractIBatisDao implements AddressDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisAddressDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Address maintainEntity(Address address) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainEntity: addressId = " + address.getId());
        }
        return (Address) insertOrUpdate(address, "ADDRESS");
    }

    @Override
    public Address readById(Long addressId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: addressId = " + addressId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.ADDRESS_ID, addressId);
        return (Address) getSqlMapClientTemplate().queryForObject("SELECT_ADDRESS_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_ADDRESSES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readActiveByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readActiveByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_ADDRESSES_BY_CONSTITUENT_ID", params);
    }

    @Override
    public void inactivateEntities() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_ADDRESSES");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateEntities: number of addresses marked inactive = " + rows);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readAllAddressesByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllAddressesByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.ADDRESS, "ADDRESS.ADDRESS_LIST_RESULT", sortPropertyName, direction, start, limit, locale);
        params.put("asOfDate", getNowDate(locale));
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_ADDRESSES_BY_CONSITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_ADDRESS_COUNT_BY_CONSTITUENT_ID", params);
    }
}
