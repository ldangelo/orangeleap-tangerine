package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.AddressDao;
import com.mpower.domain.Address;
import com.mpower.domain.Viewable;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.InactivateService;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl implements AddressService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Resource(name = "addressDao")
    private AddressDao addressDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Address saveAddress(Address address) {
        boolean found = false;
        if (address.getId() == null) {
            List<Address> addressList = readAddresses(address.getPerson().getId());
            for (Address a : addressList) {
                if (address.equals(a)) {
                    found = true;
                    Long id = a.getId();
                    try {
                        BeanUtils.copyProperties(a, address);
                        a.setId(id);
                    } catch (Exception e) {
                        logger.debug(e.getMessage(), e);
                    }
                    address = a;
                }
            }
        }
        if (!found) {
            address = addressDao.maintainAddress(address);
            if (address.isInactive()) {
                auditService.auditObjectInactive(address);
            } else {
                auditService.auditObject(address);
            }
        }
        return address;
    }

    public List<Address> readAddresses(Long personId) {
        return addressDao.readAddresses(personId);
    }

    @Override
    public List<Address> filterValidAddresses(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidAddresses: personId = " + personId);
        }
        List<Address> addresses = this.readAddresses(personId);
        List<Address> filteredAddresses = new ArrayList<Address>();
        for (Address address : addresses) {
            if (address.isValid()) {
                filteredAddresses.add(address);
            }
        }
        return filteredAddresses;
    }

    public Address readAddress(Long addressId) {
        return addressDao.readAddress(addressId);
    }

    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean mailOnly) {
        return addressDao.readCurrentAddresses(personId, calendar, mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivateAddresses() {
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
    public Viewable clone(Viewable viewable) {
        Address address = (Address)viewable;
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
