package com.mpower.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
        Query query = em.createNamedQuery("READ_ACTIVE_ADDRESSES_BY_PERSON_ID");
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    @Override
    public void deleteAddress(Address address) {
        em.remove(address);
    }

    @Override
    public Address readAddress(Long addressId) {
        return em.find(Address.class, addressId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readCurrentAddresses(Long personId, Calendar calendar) {
        Query query = em.createNamedQuery("READ_CURRENT_ADDRESSES_BY_PERSON_ID");
        query.setParameter("personId", personId);
        Calendar specifiedTemporaryCal = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        query.setParameter("specifiedTemporaryDate", specifiedTemporaryCal.getTime());
        Calendar specifiedSeasonalCal = new GregorianCalendar(0, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        query.setParameter("specifiedSeasonalDate", specifiedSeasonalCal.getTime());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> readCurrentAddresses(Long personId, Calendar calendar, boolean receiveCorrespondence) {
        Query query = em.createNamedQuery("READ_CURRENT_ADDRESSES_BY_PERSON_ID_AND_CORRESPONDENCE");
        query.setParameter("personId", personId);
        query.setParameter("correspondence", receiveCorrespondence);
        Calendar specifiedTemporaryCal = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        query.setParameter("specifiedTemporaryDate", specifiedTemporaryCal.getTime());
        Calendar specifiedSeasonalCal = new GregorianCalendar(0, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        query.setParameter("specifiedSeasonalDate", specifiedSeasonalCal.getTime());
        return query.getResultList();
    }
}
