package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.AddressDao;
import com.mpower.domain.model.communication.Address;

public class IBatisAddressDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private AddressDao addressDao;

    @BeforeMethod
    public void setupMocks() {
    	addressDao = (AddressDao)super.applicationContext.getBean("addressDAO");
    }
    
    private final Long TEST_PERSON_ID = 100l;
    
    @Test(groups = { "testCreateAddressEntry" })
    public void testCreateAddressEntry() throws Exception {
    	Address address = new Address();
    	address.setAddressLine1("123 Main St.");
    	address.setPersonId(TEST_PERSON_ID);
    	address.setAddressType("home");
    	address.setPostalCode("77777");
    	address.setCity("Tulsa");
    	address.setInactive(false);
    	address.setCountry("USA");
    	address.setStateProvince("OK");
    	addressDao.maintainAddress(address);
    } 

    @Test(groups = { "testReadAddressEntry" }, dependsOnGroups = { "testCreateAddressEntry" })
    public void testReadAddressEntry() throws Exception {
    	List<Address> list = addressDao.readAddresses(TEST_PERSON_ID);
//        assert list != null && list.size() > 0 && list.get(0).getPersonId() == TEST_PERSON_ID;
    } 
    
    
}
