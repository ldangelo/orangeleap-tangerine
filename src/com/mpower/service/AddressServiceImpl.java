package com.mpower.service;

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
        auditService.auditObject(address);
        return addressDao.maintainAddress(address);
    }

    public List<Address> readAddresses(Long personId) {
        return addressDao.readAddresses(personId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteAddress(Address address) {
        auditService.auditObjectDelete(address);
        addressDao.deleteAddress(address);
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    // @Override
    // public List<PaymentSource> readActivePaymentSources(Long personId) {
    // return paymentSourceDao.readActivePaymentSources(personId);
    // }
    //
    // @Transactional(propagation = Propagation.REQUIRED)
    // public void inactivateAddress(Long addressId) {
    // addressDao.inactivateAddress(addressId);
    // }

    //
    // @Override
    // public void removeAddress(Long addressId) {
    // addressDao.removeAddress(addressId);
    // }

    @Override
    public Address readAddress(Long addressId) {
        return addressDao.readAddress(addressId);
    }
}
