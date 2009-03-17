package com.orangeleap.tangerine.test.dao.ibatis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.type.FormBeanType;

public class IBatisGiftInKindDaoTest extends AbstractIBatisTest {
    
    private GiftInKindDao giftInKindDao;

    @BeforeMethod
    public void setup() {
        giftInKindDao = (GiftInKindDao)super.applicationContext.getBean("giftInKindDAO");
    }
    
    @Test(groups = { "testMaintainGiftInKind" }, dependsOnGroups = { "testReadGiftInKind" })
    public void testMaintainGiftInKind() throws Exception {
        // Insert
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        GiftInKind giftInKind = new GiftInKind(new BigDecimal(4.45), "USD", sdf.parse("02/01/2008"), "999", false, "Hi mom", false, null, FormBeanType.NONE);
        giftInKind.setPerson(new Person(200L, new Site("company1")));
        
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        assert giftInKind.getId() > 0;
        
        GiftInKind readGiftInKind = giftInKindDao.readGiftInKindById(giftInKind.getId());
        assert readGiftInKind != null;
        assert giftInKind.getId().equals(readGiftInKind.getId());
        assert 4.45d == readGiftInKind.getFairMarketValue().doubleValue();
        assert "USD".equals(readGiftInKind.getCurrencyCode());
        assert sdf.parse("02/01/2008").equals(readGiftInKind.getDonationDate());
        assert "999".equals(readGiftInKind.getMotivationCode());
        assert readGiftInKind.isAnonymous() == false;
        assert "Hi mom".equals(readGiftInKind.getRecognitionName());
        assert readGiftInKind.isSendAcknowledgment() == false;
        assert readGiftInKind.getGiftId() == null;
        assert readGiftInKind.getAcknowledgmentDate() == null;
        assert readGiftInKind.getSelectedEmail() != null && readGiftInKind.getSelectedEmail().getId() == null;
        assert readGiftInKind.getPerson() != null && readGiftInKind.getPerson().getId() == 200L;
        IBatisConstituentDaoTest.testConstituentId200(readGiftInKind.getPerson());
        assert readGiftInKind.getDetails() != null && readGiftInKind.getDetails().isEmpty();
        
        // Update
        giftInKind = readGiftInKind;
        giftInKind.setDonationDate(null);
        giftInKind.setAnonymous(true);
        giftInKind.setRecognitionName(null);
        Email email = new Email();
        email.setId(100L);
        giftInKind.setSelectedEmail(email);
        
        GiftInKindDetail detail = new GiftInKindDetail("my description", new BigDecimal(.50), null, "joe", new Integer(3), false, giftInKind.getId()); 
        List<GiftInKindDetail> list = new ArrayList<GiftInKindDetail>();
        list.add(detail);
        giftInKind.setDetails(list);
        
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        readGiftInKind = giftInKindDao.readGiftInKindById(giftInKind.getId());
        assert readGiftInKind != null;
        assert 4.45d == readGiftInKind.getFairMarketValue().doubleValue();
        assert "USD".equals(readGiftInKind.getCurrencyCode());
        assert readGiftInKind.getDonationDate() == null;
        assert "999".equals(readGiftInKind.getMotivationCode());
        assert readGiftInKind.isAnonymous();
        assert readGiftInKind.getGiftId() == null;
        assert readGiftInKind.getRecognitionName() == null;
        assert readGiftInKind.isSendAcknowledgment() == false;
        assert readGiftInKind.getAcknowledgmentDate() == null;
        assert readGiftInKind.getSelectedEmail() != null && readGiftInKind.getSelectedEmail().getId() == 100L;
        assert readGiftInKind.getPerson() != null && readGiftInKind.getPerson().getId() == 200L;
        IBatisConstituentDaoTest.testConstituentId200(readGiftInKind.getPerson());
        assert readGiftInKind.getDetails() != null && readGiftInKind.getDetails().size() == 1;
        
        for (GiftInKindDetail readDetail : readGiftInKind.getDetails()) {
            assert "my description".equals(readDetail.getDescription());
            assert .50d == readDetail.getFairMarketValue().doubleValue();
            assert readDetail.getFmvMethod() == null;
            assert "joe".equals(readDetail.getGikCategory());
            assert 3 == readDetail.getQuantity();
            assert readDetail.isTaxDeductible() == false;
            assert readGiftInKind.getId().equals(readDetail.getGiftInKindId());
        }
    }
    
    @Test(groups = { "testReadGiftInKind" })
    public void testReadGiftInKindById() throws Exception {
        GiftInKind giftInKind = giftInKindDao.readGiftInKindById(0L);
        assert giftInKind == null;
        
        giftInKind = giftInKindDao.readGiftInKindById(100L);
        assert giftInKind != null;
        assert 100L == giftInKind.getId();
        assert 15.59 == giftInKind.getFairMarketValue().doubleValue();
        assert giftInKind.getGiftId() == null;
        assert "USD".equals(giftInKind.getCurrencyCode());
        assert giftInKind.getDonationDate() != null;
        assert "1234".equals(giftInKind.getMotivationCode());
        assert giftInKind.isAnonymous();
        assert giftInKind.getRecognitionName() == null;
        assert giftInKind.isSendAcknowledgment() == false;
        assert giftInKind.getAcknowledgmentDate() == null;
        assert giftInKind.getPerson() != null && giftInKind.getPerson().getId() == 100L;
        IBatisConstituentDaoTest.testConstituentId100(giftInKind.getPerson());
        assert giftInKind.getSelectedEmail() != null && giftInKind.getSelectedEmail().getId() == 100L;
        IBatisEmailDaoTest.testEmailId100(giftInKind.getSelectedEmail());
        assert giftInKind.getDetails() != null && giftInKind.getDetails().isEmpty();
    }
    
    @Test(groups = { "testReadGiftInKind" })
    public void testReadGiftsInKindByConstituentId() throws Exception {
        List<GiftInKind> giftsInKind = giftInKindDao.readGiftsInKindByConstituentId(0L);
        assert giftsInKind != null && giftsInKind.isEmpty();
        
        giftsInKind = giftInKindDao.readGiftsInKindByConstituentId(300L);
        assert giftsInKind != null && giftsInKind.size() == 2;
        for (GiftInKind gik : giftsInKind) {
            assert gik.getId() == 200L || gik.getId() == 300L;
            assert 0.99 == gik.getFairMarketValue().doubleValue() || 50 == gik.getFairMarketValue().doubleValue();
            assert 300L == gik.getPerson().getId();
            assert 400L == gik.getSelectedEmail().getId();
            
            switch (gik.getId().intValue()) {
                case 200:
                    assert gik.getDetails() != null && gik.getDetails().isEmpty();
                    assert gik.getGiftId() == 100L;
                    break;
                case 300:
                    assert gik.getDetails() != null && gik.getDetails().size() == 2;
                    assert gik.getGiftId() == null;
                    for (GiftInKindDetail detail : gik.getDetails()) {
                        assert "foo".equals(detail.getDescription()) || "bar".equals(detail.getDescription());
                        assert 10 == detail.getFairMarketValue().intValue() || 40 == detail.getFairMarketValue().intValue();
                        assert null == detail.getFmvMethod() || "risk analysis".equals(detail.getFmvMethod());
                        assert "bo".equals(detail.getGikCategory()) || "jackson".equals(detail.getGikCategory());
                        assert 1 == detail.getQuantity() || 2 == detail.getQuantity();
                        assert 300L == detail.getGiftInKindId();
                    }
                    break;
            }
        }
    }
}
