package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.type.FormBeanType;

public interface PaymentSourceAware {

    public String getPaymentType();
    
    public void setPaymentType(String paymentType);

    public PaymentSource getPaymentSource();

    public void setPaymentSource(PaymentSource paymentSource);

    public PaymentSource getSelectedPaymentSource();

    public void setSelectedPaymentSource(PaymentSource paymentSource);

    public void setPaymentSourceType(FormBeanType type);
    
    public FormBeanType getPaymentSourceType();
    
    public void setPaymentSourcePaymentType();
    
    public void setPaymentSourceAwarePaymentType();
}
