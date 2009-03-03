package com.mpower.test.service.impl;

import java.lang.reflect.Method;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.AuditService;
import com.mpower.service.impl.AuditServiceImpl;
import com.mpower.test.BaseTest;

public class AuditServiceImplTest extends BaseTest {

    @Autowired
    private AuditService auditService;

    @Test(groups = { "isAuditable" })
    public void testIsAuditable() throws Exception {
        PaymentSource ps = new PaymentSource();
        assert Boolean.TRUE.equals(invoke(ps, "profile"));
        assert Boolean.TRUE.equals(invoke(ps, "type"));
        assert Boolean.TRUE.equals(invoke(ps, "creditCardHolderName"));
        assert Boolean.TRUE.equals(invoke(ps, "creditCardType"));

        assert Boolean.FALSE.equals(invoke(ps, "creditCardNumber"));
        assert Boolean.FALSE.equals(invoke(ps, "creditCardNumberEncrypted"));
        assert Boolean.FALSE.equals(invoke(ps, "creditCardSecurityCode"));
        assert Boolean.FALSE.equals(invoke(ps, "achRoutingNumber"));
        assert Boolean.FALSE.equals(invoke(ps, "achAccountNumber"));
        assert Boolean.FALSE.equals(invoke(ps, "achAccountNumberEncrypted"));
        
        Gift gift = new Gift();
        assert Boolean.TRUE.equals(invoke(gift, "comments"));
        assert Boolean.TRUE.equals(invoke(gift, "amount"));
        assert Boolean.TRUE.equals(invoke(gift, "paymentSource.creditCardHolderName"));
        assert Boolean.TRUE.equals(invoke(gift, "paymentSource.creditCardType"));

        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.creditCardNumber"));
        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.creditCardNumberEncrypted"));
        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.creditCardSecurityCode"));
        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.achRoutingNumber"));
        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.achAccountNumber"));
        assert Boolean.FALSE.equals(invoke(gift, "paymentSource.achAccountNumberEncrypted"));
    }

    private Object invoke(Object o, String fieldName) throws Exception {
        Method method = ((AuditServiceImpl)auditService).getClass().getDeclaredMethod("isAuditable", BeanWrapper.class, String.class);
        method.setAccessible(true);
        return method.invoke(auditService, PropertyAccessorFactory.forBeanPropertyAccess(o), fieldName);
    }
}
