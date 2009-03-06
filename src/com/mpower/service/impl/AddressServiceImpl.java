package com.mpower.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.AddressDao;
import com.mpower.dao.interfaces.CommunicationDao;
import com.mpower.domain.model.communication.Address;
import com.mpower.service.AddressService;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl extends AbstractCommunicationService<Address> implements AddressService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "addressDAO")
    private AddressDao addressDao;

    @Override
    protected CommunicationDao<Address> getDao() {
        return addressDao;
    }

    @Override
    protected Address createEntity(Long constituentId) {
        return new Address(constituentId);
    }
}
