package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.AddressDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.StringConstants;

/**
 * iBatis version of the AddressDao
 * @version 1.0
 */
@Repository("addressDAO")
public class IBatisAddressDao extends AbstractIBatisDao implements AddressDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisAddressDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Address maintainEntity(Address address) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainEntity: address = " + address);
        }
        return (Address) insertOrUpdate(address, "ADDRESS");
    }

    @Override
    public Address readById(Long addressId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readById: addressId = " + addressId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.ADDRESS_ID, addressId);
        return (Address) getSqlMapClientTemplate().queryForObject("SELECT_ADDRESS_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_ADDRESSES_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readActiveByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActiveByConstituentId: constituentId = " + constituentId);
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
}
