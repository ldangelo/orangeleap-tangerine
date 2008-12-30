package com.mpower.test.controller.validator;

import com.mpower.domain.PaymentSource;

public class PaymentSourceValidatorTestMockService {
    public PaymentSource findPaymentSourceProfile(Long personId, String profile) {
        if (personId == 1L && profile.equals("MyProfile")) {
            return new PaymentSource();
        }
        return null;
    }
}
