package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.PostBatchService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.PostBatchDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {


    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchDAO")
    private PostBatchDao postBatchDao;


    @Override
    public Map<String, String> readAllowedGiftSelectFields() {
        // TODO read gift entry screen for custom fields.
        Map<String, String> map = new TreeMap<String, String>();
        map.put("amount", "Amount");
        map.put("currencyCode", "Currency Code");
        map.put("createDate", "Create date");
        map.put("constituent.id", "Constituent Id");
        map.put("giftStatus", "Gift Status");
        map.put("donationDate", "Donation Date");
        map.put("postmarkDate", "Postmark Date");
        map.put("paymentStatus", "Payment Status");
        map.put("posted", "Posted");
        return map;
    }

    @Override
    public Map<String, String> readAllowedGiftUpdateFields() {
        // TODO read gift entry screen for custom fields.
        Map<String, String> map = new TreeMap<String, String>();
        map.put("giftStatus", "Gift Status");
        map.put("posted", "Posted");
        map.put("postedDate", "Posted Date");
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
        // todo save/retrieve custom fields from lists
        return postBatchDao.maintainPostBatch(postbatch);
    }

    // Evaluates criteria to create list of matching gifts at this moment.
    @Override
    public List<Gift> createBatchSelectionList(PostBatch postbatch) {
        // TODO call gift serach?
        return new ArrayList<Gift>();
    }

    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
    @Override
    public List<Gift> getBatchSelectionList(PostBatch postbatch) {
         return new ArrayList<Gift>();
    }

    // Sets fields on gifts in reviewed batch list
    @Override
    public PostBatch postBatch(PostBatch postbatch) {
         return postbatch;
    }
}