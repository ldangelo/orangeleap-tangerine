package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.util.StringConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        GiftInKind giftInKind = new GiftInKind(new BigDecimal(4.45), "USD", sdf.parse("02/01/2008"), "999", null, false, "Hi mom");
        giftInKind.setConstituent(new Constituent(200L, new Site("company1")));
        
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        assert giftInKind.getId() > 0;
        
        GiftInKind readGiftInKind = giftInKindDao.readGiftInKindById(giftInKind.getId());
        assert readGiftInKind != null;
        assert giftInKind.getId().equals(readGiftInKind.getId());
        assert 4.45d == readGiftInKind.getFairMarketValue().doubleValue();
        assert "USD".equals(readGiftInKind.getCurrencyCode());
        assert sdf.parse("02/01/2008").equals(readGiftInKind.getDonationDate());
        assert "999".equals(readGiftInKind.getMotivationCode());
        assert readGiftInKind.getOther_motivationCode() == null;
        assert readGiftInKind.isAnonymous() == false;
        assert "Hi mom".equals(readGiftInKind.getRecognitionName());
        assert readGiftInKind.getGiftId() == null;
        assert readGiftInKind.getConstituent() != null && readGiftInKind.getConstituent().getId() == 200L;
        IBatisConstituentDaoTest.testConstituentId200(readGiftInKind.getConstituent());
        assert readGiftInKind.getDetails() != null && readGiftInKind.getDetails().isEmpty();
        
        // Update
        giftInKind = readGiftInKind;
        giftInKind.setMotivationCode(null);
        giftInKind.setOther_motivationCode("bits");
        giftInKind.setDonationDate(null);
        giftInKind.setAnonymous(true);
        giftInKind.setRecognitionName(null);
        
        GiftInKindDetail detail = new GiftInKindDetail(new BigDecimal(.50), "my description", "xyz", false, giftInKind.getId(), null, "joe", new Integer(3)); 
        List<GiftInKindDetail> list = new ArrayList<GiftInKindDetail>();
        list.add(detail);
        giftInKind.setDetails(list);
        
        giftInKind = giftInKindDao.maintainGiftInKind(giftInKind);
        readGiftInKind = giftInKindDao.readGiftInKindById(giftInKind.getId());
        assert readGiftInKind != null;
        assert 4.45d == readGiftInKind.getFairMarketValue().doubleValue();
        assert "USD".equals(readGiftInKind.getCurrencyCode());
        assert readGiftInKind.getDonationDate() == null;
        assert readGiftInKind.getMotivationCode() == null;
        assert "bits".equals(readGiftInKind.getOther_motivationCode());
        assert readGiftInKind.isAnonymous();
        assert readGiftInKind.getGiftId() == null;
        assert readGiftInKind.getRecognitionName().equals(StringConstants.ANONYMOUS_CAMEL_CASE);
        assert readGiftInKind.getConstituent() != null && readGiftInKind.getConstituent().getId() == 200L;
        IBatisConstituentDaoTest.testConstituentId200(readGiftInKind.getConstituent());
        assert readGiftInKind.getDetails() != null && readGiftInKind.getDetails().size() == 1;
        
        for (GiftInKindDetail readDetail : readGiftInKind.getDetails()) {
            assert "my description".equals(readDetail.getDescription());
            assert .50d == readDetail.getDetailFairMarketValue().doubleValue();
            assert readDetail.getFmvMethod() == null;
            assert "joe".equals(readDetail.getGikCategory());
            assert 3 == readDetail.getQuantity();
            assert readDetail.isTaxDeductible() == false;
            assert readGiftInKind.getId().equals(readDetail.getGiftInKindId());
            assert "xyz".equals(readDetail.getProjectCode());
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
        assert giftInKind.getOther_motivationCode() == null;
        assert giftInKind.isAnonymous();
        assert giftInKind.getRecognitionName() == null;
        assert giftInKind.getConstituent() != null && giftInKind.getConstituent().getId() == 100L;
        IBatisConstituentDaoTest.testConstituentId100(giftInKind.getConstituent());
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
            assert 300L == gik.getConstituent().getId();
            
            switch (gik.getId().intValue()) {
                case 200:
                    assert gik.getDetails() != null && gik.getDetails().isEmpty();
                    assert gik.getMotivationCode() == null;
                    assert "blarg".equals(gik.getOther_motivationCode());
                    assert gik.getGiftId() == 100L;
                    break;
                case 300:
                    assert gik.getDetails() != null && gik.getDetails().size() == 2;
                    assert gik.getGiftId() == null;
                    assert "4321".equals(gik.getMotivationCode());
                    assert gik.getOther_motivationCode() == null;
                    for (GiftInKindDetail detail : gik.getDetails()) {
                        assert "foo".equals(detail.getDescription()) || "bar".equals(detail.getDescription());
                        assert 10 == detail.getDetailFairMarketValue().intValue() || 40 == detail.getDetailFairMarketValue().intValue();
                        assert null == detail.getFmvMethod() || "risk analysis".equals(detail.getFmvMethod());
                        assert "bo".equals(detail.getGikCategory()) || "jackson".equals(detail.getGikCategory());
                        assert 1 == detail.getQuantity() || 2 == detail.getQuantity();
                        assert 300L == detail.getGiftInKindId();
                        assert "12345".equals(detail.getProjectCode()) || "987654321".equals(detail.getProjectCode());
                    }
                    break;
            }
        }
    }
}
