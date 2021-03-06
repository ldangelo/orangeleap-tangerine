package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.AddressDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.OLLogger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IBatisAddressDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    private AddressDao addressDao;

    @BeforeMethod
    public void setup() {
        addressDao = (AddressDao)super.applicationContext.getBean("addressDAO");
    }
    
    @Test(groups = { "testMaintainAddress" }, dependsOnGroups = { "testReadAddress" })
    public void testMaintainAddress() throws Exception {
        // Insert
        Address address = new Address(300L, "1234 Fake Dr", "New York", "NY", "20211", "US");
	    address.setInactive(true);
        address = addressDao.maintainEntity(address);
        assert address.getId() > 0;
        Address readAddress = addressDao.readById(address.getId());
        assert readAddress != null;
        assert address.getId().equals(readAddress.getId());
        assert 300L == readAddress.getConstituentId();
        assert "1234 Fake Dr".equals(readAddress.getAddressLine1());
        assert "New York".equals(readAddress.getCity());
        assert "NY".equals(readAddress.getStateProvince());
        assert "20211".equals(readAddress.getPostalCode());
        assert "US".equals(readAddress.getCountry());
        assert ActivationType.permanent.equals(readAddress.getActivationStatus());
        assert readAddress.getAddressLine2() == null;
        assert readAddress.getAddressLine3() == null;
        assert readAddress.getCreateDate() != null;
        assert readAddress.getUpdateDate() != null;
        assert readAddress.isReceiveCorrespondence() == false;
        Assert.assertTrue(readAddress.isInactive());
        assert readAddress.getComments() == null;
        assert readAddress.getEffectiveDate() != null;
        assert readAddress.getSeasonalStartDate() == null;
        assert readAddress.getSeasonalEndDate() == null;
        assert readAddress.getTemporaryStartDate() == null;
        assert readAddress.getTemporaryEndDate() == null;
        
        // Update
        address.setCity("San Francisco");
        address.setStateProvince("CA");
        address.setPostalCode("92111");
        address = addressDao.maintainEntity(address);
        readAddress = addressDao.readById(address.getId());
        assert readAddress.getId() > 0;
        assert "1234 Fake Dr".equals(readAddress.getAddressLine1());
        assert "San Francisco".equals(readAddress.getCity());
        assert "CA".equals(readAddress.getStateProvince());
        assert "92111".equals(readAddress.getPostalCode());
        assert "US".equals(readAddress.getCountry());
        assert address.getId().equals(readAddress.getId());
        assert 300L == readAddress.getConstituentId();
        assert ActivationType.permanent.equals(readAddress.getActivationStatus());
        assert readAddress.getAddressLine2() == null;
        assert readAddress.getAddressLine3() == null;
        assert readAddress.getCreateDate() != null;
        assert readAddress.getUpdateDate() != null;
        assert readAddress.isReceiveCorrespondence() == false;
	    Assert.assertTrue(readAddress.isInactive());
        assert readAddress.getComments() == null;
        assert readAddress.getEffectiveDate() != null;
        assert readAddress.getSeasonalStartDate() == null;
        assert readAddress.getSeasonalEndDate() == null;
        assert readAddress.getTemporaryStartDate() == null;
        assert readAddress.getTemporaryEndDate() == null;
    }

    @Test(groups = { "testReadAddress" })
    public void testReadAddress() throws Exception {
        Address address = addressDao.readById(100L);
        assert address != null;
        assert 100L == address.getId();
        assert "3726 THIRD ST".equals(address.getAddressLine1());
        assert "Dallas".equals(address.getCity());
        assert "TX".equals(address.getStateProvince());
        assert "75554".equals(address.getPostalCode());
        assert "US".equals(address.getCountry());
        assert address.getAddressLine2() == null;
        assert address.getAddressLine3() == null;
        assert address.getCreateDate() != null;
        assert address.getUpdateDate() != null;
        assert 100L == address.getConstituentId();
        assert address.isReceiveCorrespondence() == false;
        assert ActivationType.permanent.equals(address.getActivationStatus());
        assert address.isInactive() == false;
        assert address.getComments() == null;
        assert address.getEffectiveDate() == null;
        assert address.getSeasonalStartDate() == null;
        assert address.getSeasonalEndDate() == null;
        assert address.getTemporaryStartDate() == null;
        assert address.getTemporaryEndDate() == null;
    } 

    @Test(groups = { "testReadAddress" })
    public void testReadAddressesByConstituentId() throws Exception {
        List<Address> addresses = addressDao.readByConstituentId(100L); 
        assert addresses != null && addresses.size() == 4;
        for (Address address : addresses) {
            if (address.getId() >= 100L && address.getId() <= 400L) {
                assert address.getUpdateDate() != null;
                assert address.getCreateDate() != null;
                assert 100L == address.getConstituentId();
                assert "Dallas".equals(address.getCity());
                assert "TX".equals(address.getStateProvince());
                assert "US".equals(address.getCountry());
                assert address.getAddressLine2() == null;
                assert address.getAddressLine3() == null;
            }
            switch (address.getId().intValue()) {
                case 100:
                    assert "3726 THIRD ST".equals(address.getAddressLine1());
                    assert "75554".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == false;
                    assert address.isInactive() == false;
                    assert ActivationType.permanent.equals(address.getActivationStatus());
                    break;
                case 200:
                    assert "406 FAIR OAK DR".equals(address.getAddressLine1());
                    assert "75479".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == true;
                    assert address.isInactive() == false;
                    assert ActivationType.temporary.equals(address.getActivationStatus());
                    break;
                case 300:
                    assert "3709 ASPEN BLVD".equals(address.getAddressLine1());
                    assert "75238".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == false;
                    assert address.isInactive() == true;
                    assert ActivationType.seasonal.equals(address.getActivationStatus());
                    break;
                case 400:
                    assert "5908 ELM RD".equals(address.getAddressLine1());
                    assert "75347".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == false;
                    assert address.isInactive() == true;
                    assert ActivationType.permanent.equals(address.getActivationStatus());
                    break;
                default:
                    assert false == true;
            }
        }
        
        addresses = addressDao.readByConstituentId(200L);
        assert addresses != null && addresses.size() == 3;
    }
    
    @Test(groups = { "testReadAddress" })
    public void testReadActiveAddresssByConstituentId() throws Exception {
        List<Address> addresses = addressDao.readActiveByConstituentId(0L);
        assert addresses != null && addresses.isEmpty();
        
        addresses = addressDao.readActiveByConstituentId(100L);
        assert addresses != null && addresses.size() == 2;
        for (Address address : addresses) {
            if (address.getId() >= 100L && address.getId() <= 400L) {
                assert address.getUpdateDate() != null;
                assert address.getCreateDate() != null;
                assert 100L == address.getConstituentId();
                assert "Dallas".equals(address.getCity());
                assert "TX".equals(address.getStateProvince());
                assert "US".equals(address.getCountry());
                assert address.getAddressLine2() == null;
                assert address.getAddressLine3() == null;
            }
            switch (address.getId().intValue()) {
                case 100:
                    assert "3726 THIRD ST".equals(address.getAddressLine1());
                    assert "75554".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == false;
                    assert address.isInactive() == false;
                    assert ActivationType.permanent.equals(address.getActivationStatus());
                    break;
                case 200:
                    assert "406 FAIR OAK DR".equals(address.getAddressLine1());
                    assert "75479".equals(address.getPostalCode());
                    assert address.isReceiveCorrespondence() == true;
                    assert address.isInactive() == false;
                    assert ActivationType.temporary.equals(address.getActivationStatus());
                    break;
            }
        }
    }
    
    @Test(groups = { "testInactivateAddress" }, dependsOnGroups = { "testReadAddress", "testMaintainAddress" })
    public void testInactivateAddress() throws Exception {
        Address address = new Address(300L, "1 Main Street", "Chicago", "IL", "34444", "US");
        address.setActivationStatus(ActivationType.temporary);
        address.setInactive(false);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date d = sdf.parse("01/01/1990");
        address.setTemporaryEndDate(d);
        
        address = addressDao.maintainEntity(address);
        assert address.getId() > 0;
        
        Address readAddress = addressDao.readById(address.getId());
        assert readAddress != null;
        assert address.getId().equals(readAddress.getId());
        assert ActivationType.temporary.equals(readAddress.getActivationStatus());
        assert readAddress.isInactive() == false;
        assert d.equals(readAddress.getTemporaryEndDate());
        assert "1 Main Street".equals(readAddress.getAddressLine1());
        assert 300L == readAddress.getConstituentId();
        
        addressDao.inactivateEntities();
        
        readAddress = addressDao.readById(address.getId());
        assert readAddress != null;
        assert address.getId().equals(readAddress.getId());
        assert ActivationType.temporary.equals(readAddress.getActivationStatus());
        assert readAddress.isInactive();
        assert d.equals(readAddress.getTemporaryEndDate());
        assert "1 Main Street".equals(readAddress.getAddressLine1());
        assert 300L == readAddress.getConstituentId();
    }
}
