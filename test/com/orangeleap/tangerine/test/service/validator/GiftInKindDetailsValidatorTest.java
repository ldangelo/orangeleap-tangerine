package com.orangeleap.tangerine.test.service.validator;

import com.orangeleap.tangerine.service.validator.GiftInKindDetailsValidator;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.test.BaseTest;
import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class GiftInKindDetailsValidatorTest extends BaseTest {

    private GiftInKindDetailsValidator validator;
    private BindException errors;

    @BeforeMethod
    public void setup() {
        validator = new GiftInKindDetailsValidator();
    }

    @Test(groups = { "validateGiftInKindDetails" })
    public void testValidateGiftInKindDetails() throws Exception {
        GiftInKind gik = new GiftInKind();
        errors = new BindException(gik, "giftInKind");
        GiftInKindDetail aDetail = new GiftInKindDetail();
        aDetail.setDetailFairMarketValue(new BigDecimal(13.5));
        gik.addDetail(aDetail);
        gik.setFairMarketValue(new BigDecimal(16));
        
        validator.validate(gik, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(gik, "giftInKind");
        aDetail = new GiftInKindDetail();
        aDetail.setDetailFairMarketValue(new BigDecimal(2.5));
        gik = new GiftInKind();
        gik.addDetail(aDetail);
        gik.setFairMarketValue(new BigDecimal(16));        
        validator.validate(gik, errors);
        assert errors.hasErrors() == true;

        errors = new BindException(gik, "giftInKind");
	    gik = new GiftInKind();
        aDetail = new GiftInKindDetail();
        aDetail.setDetailFairMarketValue(new BigDecimal(13.5));
        aDetail.setDescription("bark");
	    gik.addDetail(aDetail);
        aDetail = new GiftInKindDetail();
        aDetail.setDetailFairMarketValue(new BigDecimal(2.5));
        aDetail.setDescription("woof");
	    gik.addDetail(aDetail);
        gik.setFairMarketValue(new BigDecimal(16));        
        validator.validate(gik, errors);
        assert errors.hasErrors() == false;
    }
}
