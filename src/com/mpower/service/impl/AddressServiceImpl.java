package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.AddressDao;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.Address;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.InactivateService;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl extends AbstractCommunicationService<Address> implements AddressService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressDAO")
    private AddressDao addressDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Address saveAddress(Address address) {
        if (logger.isDebugEnabled()) {
            logger.debug("saveAddress: address = " + address);
        }
        boolean found = false;
        if (address.getId() == null) {
            List<Address> addressList = readAddressesByConstituentId(address.getPersonId());
            for (Address a : addressList) {
                if (address.equals(a)) {
                    found = true;
                    Long id = a.getId();
                    try {
                        BeanUtils.copyProperties(a, address);
                        a.setId(id);
                    } 
                    catch (Exception e) {
                        if (logger.isDebugEnabled()) {
                            logger.debug(e.getMessage(), e);
                        }
                    }
                    address = a;
                }
            }
        }
        if (!found) {
            address = addressDao.maintainAddress(address);
            if (address.isInactive()) {
                auditService.auditObjectInactive(address);
            } 
            else {
                auditService.auditObject(address);
            }
        }
        return address;
    }

    @Override
    public List<Address> readAddressesByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAddressesByConstituentId: constituentId = " + constituentId);
        }
        return addressDao.readAddressesByConstituentId(constituentId);
    }

    @Override
    public List<Address> filterValidAddresses(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidAddresses: constituentId = " + constituentId);
        }
        List<Address> addresses = this.readAddressesByConstituentId(constituentId);
        List<Address> filteredAddresses = new ArrayList<Address>();
        for (Address address : addresses) {
            if (address.isValid()) {
                filteredAddresses.add(address);
            }
        }
        return filteredAddresses;
    }

    @Override
    public Address readAddress(Long addressId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAddress: addressId = " + addressId);
        }
        return addressDao.readAddressById(addressId);
    }

    @Override
    public List<Address> readCurrentAddresses(Long constituentId, boolean mailOnly) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCurrentAddresses: constituentId = " + constituentId + " mailOnly = " + mailOnly);
        }
        return filterByActivationType(addressDao.readActiveAddressesByConstituentId(constituentId), mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    // TODO: is this being used?
    public void inactivateAddresses() {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivateAddresses:");
        }
        addressDao.inactivateAddresses();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivate(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate: id = " + id);
        }
        Address addr = this.readAddress(id);
        addr.setInactive(true);
        this.saveAddress(addr);
    }

    @Override
    public AbstractEntity clone(AbstractEntity entity) {
        if (logger.isDebugEnabled()) {
            logger.debug("clone: entity = " + entity);
        }
        Address address = (Address)entity;
        if (address.getId() != null) {
            Address originalAddress = this.readAddress(address.getId());

            try {
                address = (Address)BeanUtils.cloneBean(originalAddress);
                address.setId(null);
            }
            catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("clone: Exception occurred", e);
                }
            }
        }
        return address;
    }
}
