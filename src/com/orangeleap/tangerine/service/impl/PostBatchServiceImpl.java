package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.PostBatchService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {


    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public Map<String, String> readAllowedGiftSelectFields() {
        // TODO read gift entry screen for custom fields.
        Map<String, String> map = new TreeMap<String, String>();
        map.put("giftStatus", "Gift Status");
        map.put("paymentStatus", "Payment Status");
        map.put("amount", "Amount");
        map.put("donationDate", "Donation Date");
        return map;
    }

    @Override
    public Map<String, String> readAllowedGiftUpdateFields() {
        // TODO read gift entry screen for custom fields.
        Map<String, String> map = new TreeMap<String, String>();
        map.put("giftStatus", "Gift Status");
        map.put("paymentStatus", "Payment Status");
        return map;
    }

    @Override
    public List<PostBatch> listBatchs(PostBatch postbatch) {
        return new ArrayList<PostBatch>();
    }

    @Override
    public PostBatch readBatch(Long batchId) {
        logger.debug("readBatch");
        return null;
    }

    @Override
    public PostBatch maintainBatch(PostBatch postbatch) {
        return postbatch;
    }

    // Evaluates criteria to create list of matching gifts at this moment.
    @Override
    public List<Gift> createBatchSelectionList(PostBatch postbatch) {
         return new ArrayList<Gift>();
    }

    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
    @Override
    public List<Gift> getBatchSelectionList(PostBatch postbatch) {
         return new ArrayList<Gift>();
    }

    @Override
    public PostBatch postBatch(PostBatch postbatch) {
         return postbatch;
    }
}