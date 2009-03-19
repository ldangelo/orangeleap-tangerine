package com.orangeleap.tangerine.test.dao.ibatis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PhoneDao;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.ActivationType;

public class IBatisPhoneDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PhoneDao phoneDao;

    @BeforeMethod
    public void setup() {
        phoneDao = (PhoneDao)super.applicationContext.getBean("phoneDAO");
    }
    
    @Test(groups = { "testMaintainPhone" }, dependsOnGroups = { "testReadPhone" })
    public void testMaintainPhone() throws Exception {
        // Insert
        Phone phone = new Phone(300L, "911-911-9110");
        phone = phoneDao.maintainEntity(phone);
        assert phone.getId() > 0;
        Phone readPhone = phoneDao.readById(phone.getId());
        assert readPhone != null;
        assert phone.getId().equals(readPhone.getId());
        assert 300L == readPhone.getPersonId();
        assert "911-911-9110".equals(readPhone.getNumber());
        assert ActivationType.permanent.equals(readPhone.getActivationStatus());
        assert readPhone.getCreateDate() != null;
        assert readPhone.getUpdateDate() != null;
        assert readPhone.isReceiveMail() == false;
        assert readPhone.isInactive() == false;
        assert readPhone.getComments() == null;
        assert readPhone.getEffectiveDate() != null;
        assert readPhone.getProvider() == null;
        assert readPhone.getSeasonalStartDate() == null;
        assert readPhone.getSeasonalEndDate() == null;
        assert readPhone.getSms() == null;
        assert readPhone.getTemporaryStartDate() == null;
        assert readPhone.getTemporaryEndDate() == null;
        
        // Update
        phone.setNumber("000-000-0000");
        phone = phoneDao.maintainEntity(phone);
        readPhone = phoneDao.readById(phone.getId());
        assert "000-000-0000".equals(readPhone.getNumber());
        assert readPhone.getId() > 0;
        assert phone.getId().equals(readPhone.getId());
        assert 300L == readPhone.getPersonId();
        assert ActivationType.permanent.equals(readPhone.getActivationStatus());
        assert readPhone.getCreateDate() != null;
        assert readPhone.getUpdateDate() != null;
        assert readPhone.isReceiveMail() == false;
        assert readPhone.isInactive() == false;
        assert readPhone.getComments() == null;
        assert readPhone.getEffectiveDate() != null;
        assert readPhone.getProvider() == null;
        assert readPhone.getSeasonalStartDate() == null;
        assert readPhone.getSeasonalEndDate() == null;
        assert readPhone.getSms() == null;
        assert readPhone.getTemporaryStartDate() == null;
        assert readPhone.getTemporaryEndDate() == null;
    }

    @Test(groups = { "testReadPhone" })
    public void testReadPhone() throws Exception {
        Phone phone = phoneDao.readById(100L);
        assert phone != null;
        assert 100L == phone.getId();
        assert "214-443-6829".equals(phone.getNumber());
        assert phone.getCreateDate() != null;
        assert phone.getUpdateDate() != null;
        assert 100L == phone.getPersonId();
        assert phone.isReceiveMail() == false;
        assert ActivationType.permanent.equals(phone.getActivationStatus());
        assert phone.isInactive() == false;
        assert phone.getComments() == null;
        assert phone.getEffectiveDate() == null;
        assert phone.getProvider() == null;
        assert phone.getSeasonalStartDate() == null;
        assert phone.getSeasonalEndDate() == null;
        assert phone.getSms() == null;
        assert phone.getTemporaryStartDate() == null;
        assert phone.getTemporaryEndDate() == null;
    } 

    @Test(groups = { "testReadPhone" })
    public void testReadPhonesByConstituentId() throws Exception {
        List<Phone> phones = phoneDao.readByConstituentId(100L); 
        assert phones != null && phones.size() == 6;
        for (Phone phone : phones) {
            if (phone.getId() >= 100L && phone.getId() <= 600L) {
                assert phone.getUpdateDate() != null;
                assert phone.getCreateDate() != null;
                assert 100L == phone.getPersonId();
                assert phone.isReceiveMail() == false;
                assert phone.isInactive() == false;
                assert ActivationType.permanent.equals(phone.getActivationStatus());
            }
            switch (phone.getId().intValue()) {
                case 100:
                    assert "214-443-6829".equals(phone.getNumber());
                    break;
                case 200:
                    assert "214-105-6590".equals(phone.getNumber());
                    break;
                case 300:
                    assert "214-911-6681".equals(phone.getNumber());
                    break;
                case 400:
                    assert "214-129-9781".equals(phone.getNumber());
                    break;
                case 500:
                    assert "214-548-0929".equals(phone.getNumber());
                    break;
                case 600:
                    assert "214-878-1663".equals(phone.getNumber());
                    break;
                default:
                    assert false == true;
            }
        }
        
        phones = phoneDao.readByConstituentId(200L);
        assert phones != null && phones.size() == 2;
    }
    
    @Test(groups = { "testReadPhone" })
    public void testReadActivePhonesByConstituentId() throws Exception {
        List<Phone> phones = phoneDao.readActiveByConstituentId(200L);
        assert phones != null && phones.size() == 1;
        for (Phone phone : phones) {
            assert 700L == phone.getId();
            assert phone.getUpdateDate() != null;
            assert phone.getCreateDate() != null;
            assert 200L == phone.getPersonId();
            assert phone.isReceiveMail() == false;
            assert phone.isInactive() == false;
            assert ActivationType.permanent.equals(phone.getActivationStatus());
            assert "214-113-2542".equals(phone.getNumber());
        }
    }
    
    @Test(groups = { "testInactivatePhone" }, dependsOnGroups = { "testReadPhone", "testMaintainPhone" })
    public void testInactivatePhone() throws Exception {
        Phone phone = new Phone(300L, "123-123-1234");
        phone.setActivationStatus(ActivationType.temporary);
        phone.setInactive(false);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date d = sdf.parse("01/01/1990");
        phone.setTemporaryEndDate(d);
        
        phone = phoneDao.maintainEntity(phone);
        assert phone.getId() > 0;
        
        Phone readPhone = phoneDao.readById(phone.getId());
        assert readPhone != null;
        assert phone.getId().equals(readPhone.getId());
        assert ActivationType.temporary.equals(readPhone.getActivationStatus());
        assert readPhone.isInactive() == false;
        assert d.equals(readPhone.getTemporaryEndDate());
        assert "123-123-1234".equals(readPhone.getNumber());
        assert 300L == readPhone.getPersonId();
        
        phoneDao.inactivateEntities();
        
        readPhone = phoneDao.readById(phone.getId());
        assert readPhone != null;
        assert phone.getId().equals(readPhone.getId());
        assert ActivationType.temporary.equals(readPhone.getActivationStatus());
        assert readPhone.isInactive();
        assert d.equals(readPhone.getTemporaryEndDate());
        assert "123-123-1234".equals(readPhone.getNumber());
        assert 300L == readPhone.getPersonId();
    }
}
