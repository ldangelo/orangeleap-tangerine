package com.orangeleap.tangerine.type;

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
