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
    public Address maintainAddress(Address address) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainAddress: address = " + address);
        }
        return (Address) insertOrUpdate(address, "ADDRESS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readAddressesByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAddressesByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_ADDRESSES_BY_CONSTITUENT_ID", constituentId);
    }

    @Override
    public Address readAddressById(Long addressId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAddressById: addressId = " + addressId);
        }
        return (Address) getSqlMapClientTemplate().queryForObject("SELECT_ADDRESS_BY_ID", addressId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readActiveAddressesByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActiveAddressesByConstituentId: constituentId = " + constituentId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_ACTIVE_ADDRESSES_BY_CONSTITUENT_ID", constituentId);
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean mailOnly) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("readCurrentAddresses: personId = " + personId);
//        }
//        Map<String, Object> params = new HashMap<String, Object>(3);
//        params.put("personId", personId);
//        params.put("effectiveDate", calendar.getTime());
//        params.put("mailOnly", mailOnly);
//
//        return getSqlMapClientTemplate().queryForList("SELECT_BY_CURRENT_ADDRESS", params);
//    }

    @Override
    public void inactivateAddresses() {
        int rows = getSqlMapClientTemplate().update("INACTIVATE_ADDRESSES");
        if (logger.isInfoEnabled()) {
            logger.info("inactivateAddresses: number of addresses marked inactive = " + rows);
        }
    }
}
