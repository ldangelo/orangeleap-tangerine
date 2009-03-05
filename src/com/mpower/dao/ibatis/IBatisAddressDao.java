package com.mpower.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.AddressDao;
import com.mpower.domain.model.communication.Address;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_ADDRESSES_BY_CONSTITUENT_ID", constituentId);
    }

    @Override
    public Address readById(Long addressId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readById: addressId = " + addressId);
        }
        return (Address) getSqlMapClientTemplate().queryForObject("SELECT_ADDRESS_BY_ID", addressId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readActiveByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActiveByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_ADDRESSES_BY_CONSTITUENT_ID", constituentId);
    }

    @Override
    public void inactivateEntities() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_ADDRESSES");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateEntities: number of addresses marked inactive = " + rows);
        }
    }
}
