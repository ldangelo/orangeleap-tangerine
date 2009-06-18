package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.AddressDao;
import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.service.AddressService;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl extends AbstractCommunicationService<Address> implements AddressService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

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
