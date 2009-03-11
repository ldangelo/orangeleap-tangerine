package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.checkservice.Batch;
import com.orangeleap.tangerine.domain.checkservice.Response;

/**
 * Interface for the Paperless Payments EChex service, used
 * for processing check information via the EChex web service
 * @version 1.0
 */
public interface CheckService {

    /**
     * Create a new Batch to send to Paperless Payments.
     * @param siteNumber the site number for the client processing the check
     * @param merchantId the merchant ID of the client processing the check
     * @param ruleNumber the rule number to be used for the check Detail
     * @param companyName the name of the client company
     * @return a configured Batch object
     */
    public Batch createBatch(int siteNumber, int merchantId, int ruleNumber, String companyName);

    /**
     * Sends the Batch object via the EChex web service. The Batch must have
     * a Detail object set on it. If the send was successful, the Response object
     * will contain the transaction number and a confirmation message. If the service
     * call failed, a PaymentProcessorException will be thrown which will contain
     * details on why it failed.
     * @param batch the Batch to send
     * @return the Response confirmation
     */
    public Response sendBatch(Batch batch);
}
