package com.orangeleap.tangerine.test.util;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.util.TangerineBeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TangerineBeanUtilsTest {

    @Test
    public void testCheckInnerBeanCreated() {
        Gift gift = new Gift();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(gift);

        TangerineBeanUtils.checkInnerBeanCreated(bw, "amount");
        Assert.assertNotNull(gift);

        TangerineBeanUtils.checkInnerBeanCreated(bw, "constituent.firstName");
        Assert.assertNotNull(gift.getConstituent());

        TangerineBeanUtils.checkInnerBeanCreated(bw, "paymentSource.constituent.firstName");
        Assert.assertNotNull(gift.getPaymentSource());
        Assert.assertNotNull(gift.getPaymentSource().getConstituent());

        TangerineBeanUtils.checkInnerBeanCreated(bw, "paymentHistory.giftId");
        Assert.assertNotNull(gift);
    }
}
