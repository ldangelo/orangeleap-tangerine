package com.orangeleap.tangerine.domain.checkservice;

import org.springframework.core.NestedRuntimeException;

/**
 * Runtime Exception used to catch errors sent to the Paperless Payment Processor
 * @version 1.0
 */
public class PaymentProcessorException extends NestedRuntimeException {

    public PaymentProcessorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PaymentProcessorException(String msg) {
        super(msg);
    }
}
