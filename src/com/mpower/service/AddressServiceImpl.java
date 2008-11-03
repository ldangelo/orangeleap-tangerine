package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.AddressDao;
import com.mpower.domain.Address;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressDao")
    private AddressDao addressDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Address saveAddress(Address address) {
        address = addressDao.maintainAddress(address);
        if (address.isInactive()) {
            auditService.auditObjectInactive(address);
        } else {
            auditService.auditObject(address);
        }
        return address;
    }

    public List<Address> readAddresses(Long personId) {
        return addressDao.readAddresses(personId);
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public Address readAddress(Long addressId) {
        return addressDao.readAddress(addressId);
    }

    public List<Address> getCurrentAddresses(Long personId, Calendar calendar, boolean receiveCorrespondence) {
        return addressDao.readCurrentAddresses(personId, calendar, receiveCorrespondence);
    }
}
