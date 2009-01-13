package com.mpower.domain;

public interface PaymentSourceAware {

    public String getPaymentType();
    
    public void setPaymentType(String paymentType);

    public PaymentSource getPaymentSource();

    public void setPaymentSource(PaymentSource paymentSource);

    public PaymentSource getSelectedPaymentSource();
}
