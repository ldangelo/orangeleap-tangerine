package com.mpower.dao.ibatis;

import com.mpower.dao.interfaces.AddressDao;
import com.mpower.domain.model.communication.Address;
import com.ibatis.sqlmap.client.SqlMapClient;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    //@Override
    public Address maintainAddress(Address address) {

        if (logger.isDebugEnabled()) {
            logger.debug("maintainAddress: address = " + address);
        }

        return (Address) insertOrUpdate(address, "ADDRESS");
    }

    @SuppressWarnings("unchecked")
    //@Override
    public List<Address> readAddresses(Long personId) {

        if (logger.isDebugEnabled()) {
            logger.debug("readAddresses: personId = " + personId);
        }

        return getSqlMapClientTemplate().queryForList("ADDRESS.SELECT_BY_PERSONID", personId);
    }

    //@Override
    public void deleteAddress(Address address) {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteAddress: addressId = " + address.getId());
        }

        getSqlMapClientTemplate().delete("ADDRESS.DELETE_ADDRESS", address.getId());
    }

    //@Override
    public Address readAddress(Long addressId) {

        if (logger.isDebugEnabled()) {
            logger.debug("readAddress: addressId = " + addressId);
        }

        return (Address) getSqlMapClientTemplate().queryForObject("ADDRESS.SELECT_BY_ID", addressId);
    }

    @SuppressWarnings("unchecked")
    //@Override
    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean mailOnly) {

        if (logger.isDebugEnabled()) {
            logger.debug("readCurrentAddresses: personId = " + personId);
        }
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put("personId", personId);
        params.put("effectiveDate", calendar.getTime());
        params.put("mailOnly", mailOnly);

        return getSqlMapClientTemplate().queryForList("ADDRESS.SELECT_BY_CURRENT_ADDRESS", params);
    }

    //@Override
    public void inactivateAddresses() {

        int rows = getSqlMapClientTemplate().update("ADDRESS.INACTIVATE_ADDRESSES");

        if (logger.isDebugEnabled()) {
            logger.debug("inactivateAddressJob: number of addresses marked inactive = " + rows);
        }
    }
}
