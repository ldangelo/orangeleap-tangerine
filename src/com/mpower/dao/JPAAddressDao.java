package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.Address;

@Repository("addressDao")
public class JPAAddressDao implements AddressDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Address maintainAddress(Address address) {
        if (address.getId() == null) {
            em.persist(address);
            return address;
        } else {
            return em.merge(address);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readAddresses(Long personId) {
        Query query = em.createNamedQuery("READ_ADDRESSES_BY_PERSON_ID");
        query.setParameter("personId", personId);
        List<Address> addressList = query.getResultList();
        return addressList;
    }

    @Override
    public void deleteAddress(Address address) {
        em.remove(address);
    }

    // @SuppressWarnings("unchecked")
    // @Override
    // public List<Address> readActiveAddresses(Long personId) {
    // Query query = em.createNamedQuery("READ_ACTIVE_ADDRESSES_BY_PERSON_ID");
    // query.setParameter("personId", personId);
    // List<Address> addressList = query.getResultList();
    // return addressList;
    // }
    //
    // public void inactivateAddress(Long addressId) {
    // Address address = em.find(Address.class, addressId);
    // address
    // Query query = em.createNamedQuery("UPDATE_PAYMENT_SOURCE_TO_INACTIVE");
    // query.setParameter("addressId", addressId);
    // query.executeUpdate();
    // }

    // @SuppressWarnings("unchecked")
    // @Override
    // public void removeAddress(Long addressId) {
    // // Logic to determine whether or not we should delete or simply
    // // inactivate a payment source
    // Query query = em.createNamedQuery("READ_GIFTS_BY_PAYMENT_SOURCE_ID");
    // query.setParameter("addressId", addressId);
    // List<Gift> gifts = query.getResultList();
    // if (gifts.size() > 0) {
    // inactivateAddress(addressId);
    // } else {
    // Address address = readAddress(addressId);
    // deleteAddress(address);
    // }
    // }

    @Override
    public Address readAddress(Long addressId) {
        return em.find(Address.class, addressId);
    }
}
