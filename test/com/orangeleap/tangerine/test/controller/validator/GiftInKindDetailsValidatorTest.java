package com.orangeleap.tangerine.test.controller.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.controller.validator.GiftInKindDetailsValidator;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.test.BaseTest;

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
        List<GiftInKindDetail> details = new ArrayList<GiftInKindDetail>();
        GiftInKindDetail aDetail = new GiftInKindDetail();
        aDetail.setFairMarketValue(new BigDecimal(13.5));
        details.add(aDetail);
        gik.setMutableDetails(details);
        gik.setFairMarketValue(new BigDecimal(16));
        
        validator.validate(gik, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(gik, "gift");
        aDetail = new GiftInKindDetail();
        aDetail.setFairMarketValue(new BigDecimal(2.5));
        details.add(aDetail);
        gik = new GiftInKind();
        gik.setMutableDetails(details);
        gik.setFairMarketValue(new BigDecimal(16));        
        validator.validate(gik, errors);
        assert errors.hasErrors() == false;
    }
}
