package com.orangeleap.tangerine.test.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.joda.time.DateMidnight;
import org.testng.annotations.Test;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.service.impl.AbstractCommunicationService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.type.ActivationType;

public class AbstractCommunicationServiceTest extends BaseTest {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    private List<Address> entities;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
    @Test
    public void testFilterByActivationType() throws Exception {
        MockCommunicationServiceImpl service = new MockCommunicationServiceImpl();
        entities = new ArrayList<Address>();
        
        // Permanent
        Address permAddress = new Address();
        permAddress.setId(999L);
        permAddress.setActivationStatus(ActivationType.permanent);
        permAddress.setEffectiveDate(sdf.parse("01/01/3000"));
        entities.add(permAddress);
        
        List<Address> readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.isEmpty();
        
        permAddress.setEffectiveDate(null);
        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.size() == 1;
        assert readEntities.get(0).getId() == 999L;
        
        permAddress.setEffectiveDate(sdf.parse("01/01/1980"));
        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.size() == 1;
        assert readEntities.get(0).getId() == 999L;

        // Temporary
        entities = new ArrayList<Address>();
        Address tempAddress = new Address();
        tempAddress.setId(555L);
        tempAddress.setActivationStatus(ActivationType.temporary);
        tempAddress.setTemporaryStartDate(sdf.parse("01/01/2000"));
        tempAddress.setTemporaryEndDate(sdf.parse("01/02/2000"));
        entities.add(tempAddress);
        
        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.isEmpty();
        
        tempAddress.setTemporaryEndDate(sdf.parse("01/01/3000"));

        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.size() == 1;
        assert readEntities.get(0).getId() == 555L;

        // Seasonal
        entities = new ArrayList<Address>();
        Address seasonalAddress = new Address();
        seasonalAddress.setId(111L);
        seasonalAddress.setActivationStatus(ActivationType.seasonal);
        seasonalAddress.setSeasonalStartDate(sdf.parse("06/15/2009"));
        seasonalAddress.setSeasonalEndDate(sdf.parse("07/15/2009"));
        entities.add(seasonalAddress);

        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.isEmpty();
        
        seasonalAddress.setSeasonalStartDate(sdf.parse("01/01/2008"));
        seasonalAddress.setSeasonalEndDate(sdf.parse("02/01/2008"));

        readEntities = service.filter(entities, false);
        assert readEntities != null && readEntities.size() == 1;
        assert readEntities.get(0).getId() == 111L;
        
        // All 3 types, plus unknown
        entities = new ArrayList<Address>();
        Address unknownAddress = new Address();
        unknownAddress.setId(12345L);
        unknownAddress.setActivationStatus(ActivationType.unknown);

        entities.add(unknownAddress);
        entities.add(permAddress);
        entities.add(tempAddress);
        entities.add(seasonalAddress);

        readEntities = service.filter(entities, false);
        assert readEntities != null;
        for (Address entity : readEntities) {
            assert entity.getId() == 111L || entity.getId() == 555L || entity.getId() == 999L;
        }
        
        // Filter for mail
        readEntities = service.filter(entities, true);
        assert readEntities != null && readEntities.isEmpty();
        
        permAddress.setReceiveCorrespondence(true);
        tempAddress.setReceiveCorrespondence(true);
        seasonalAddress.setReceiveCorrespondence(false);
        
        readEntities = service.filter(entities, true);
        assert readEntities != null;
        for (Address entity : readEntities) {
            assert entity.getId() == 555L || entity.getId() == 999L;
        }
    }
    
    class MockCommunicationServiceImpl extends AbstractCommunicationService<Address> {
        public List<Address> filter(List<Address> entities, boolean mailOnly) {
            return filterByActivationType(entities, mailOnly);
        }

        @Override
        protected void validate(Address entity) throws BindException {
        }

        @Override
        protected CommunicationDao<Address> getDao() {
            return null;
        }

        @Override
        protected Address createEntity(Long constituentId) {
            return null;
        }

        @Override
        public DateMidnight getNowDateMidnight() {
            final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date d = null;
            try {
                d = sdf.parse("01/02/2009");
            }
            catch (ParseException pe) {}
            return new DateMidnight(d);           
        }
    }
}
