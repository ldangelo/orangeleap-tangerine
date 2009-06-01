package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum PaymentType {
    ACH("ACH"),
    CASH("Cash"),
    CHECK("Check"),
    CREDIT_CARD("Credit Card");
    
    private String paymentName;

    private PaymentType(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentName() {
        return paymentName;
    }
}
