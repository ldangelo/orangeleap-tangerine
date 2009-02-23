package com.mpower.service;

import com.mpower.domain.checkservice.Batch;
import com.mpower.domain.checkservice.Response;

/**
 * @version 1.0
 */
public interface CheckService {

    public Batch createBatch(int siteNumber, int merchantId, int ruleNumber, String companyName);

    public Response sendBatch(Batch batch);
}
