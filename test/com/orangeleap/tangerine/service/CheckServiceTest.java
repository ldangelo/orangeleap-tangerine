package com.orangeleap.tangerine.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.Date;

import com.orangeleap.tangerine.domain.checkservice.*;
import com.orangeleap.tangerine.service.CheckService;
import com.orangeleap.tangerine.service.impl.CheckServiceImpl;

/**
 * @version 1.0
 */
public class CheckServiceTest {

    private CheckService checkService;

    @Before
    public void setup() {

        CheckServiceImpl impl = new CheckServiceImpl();
        impl.setUrl("http://www.echex.net/wsv/batch102.cfc");
        checkService = impl;

    }

    // Return a Batch configured for TEST (to be safe)
    public Batch getTestBatch() {
        Batch batch = checkService.createBatch(853,11229,5000,"Orange Leap");
        batch.isTest(true);
        batch.setFileNumber(1);
        return batch;

    }


    @Test
    public void testSingleSend() {

        Batch batch = getTestBatch();
        Detail detail = new Detail();
        detail.setDateTime(new Date());
        detail.setAccountNumber("121000358");
        detail.setCheckAmount( new BigDecimal("19.95") );
        detail.setTransactionNumber(123456);
        batch.setDetail(detail);

        Response response = null;

        try {
            response = checkService.sendBatch(batch);
        } catch(PaymentProcessorException exception) {
            Assert.fail("Exception thrown: " + exception);
        }

        Assert.assertEquals("Transaction numbers don't match", 123456, response.getTransactionNumber() );
        Assert.assertTrue("Messages doesn't contain Accepted", response.getMessage().contains("Accepted"));
        Assert.assertTrue("Response#isAccepted() returned false", response.isAccepted());
        Assert.assertTrue("Response#isTestResponse() returned false", response.isTestResponse());

    }



}
