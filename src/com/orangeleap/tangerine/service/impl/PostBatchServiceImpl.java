package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.annotation.Resource;
import java.util.*;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {


    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchDAO")
    private PostBatchDao postBatchDao;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;


    @Override
    public Map<String, String> readAllowedGiftSelectFields() {
        // TODO read gift entry screen for custom fields?
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
        // TODO read gift entry screen for custom fields?
        Map<String, String> map = new TreeMap<String, String>();
        map.put("giftStatus", "Gift Status");
        map.put("posted", "Posted");
        map.put("postedDate", "Posted Date");
        return map;
    }

    @Override
    public List<PostBatch> listBatchs(PostBatch postbatch) {
        // TODO implement if we want to list batches for 2-person review, copy create-from, etc.
        return new ArrayList<PostBatch>();
    }

    @Override
    public PostBatch readBatch(Long batchId) {
        logger.debug("readBatch: id = "+batchId);
        if (batchId == null) return null;
        return postBatchDao.readPostBatch(batchId);
    }

    @Override
    public PostBatch maintainBatch(PostBatch postbatch) {
        return postBatchDao.maintainPostBatch(postbatch);
    }

    // Evaluates criteria to create list of matching gifts (snapshot at this moment in time).
    @Override
    public List<Gift> createBatchSelectionList(PostBatch postbatch) {
        Map<String, Object> map = new HashMap();
        for (Map.Entry<String, String> me : postbatch.getWhereConditions().entrySet()) {
            map.put(me.getKey(), me.getValue());
        }
        List<Gift> list = giftService.searchGifts(map);
        // if (list.size() == 200) throw new RuntimeException("Too many items selected"); // see gift.xml for search limit
        // TODO raise this limit from 200 to ?
        for (Gift gift : list) {
            PostBatchReviewSetItem item = new PostBatchReviewSetItem();
            item.setEntityId(gift.getId());
            item.setPostBatchId(postbatch.getId());
            postBatchDao.maintainPostBatchReviewSetItem(item);
        }
        postbatch.setReviewSetGenerated(true);
        postbatch.setReviewSetGeneratedDate(new java.util.Date());
        postbatch.setReviewSetGeneratedById(tangerineUserHelper.lookupUserId());
        postBatchDao.maintainPostBatch(postbatch);
        return list;
    }

    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
    @Override
    public List<Gift> getBatchSelectionList(PostBatch postbatch) {
         List<PostBatchReviewSetItem> list = postBatchDao.readPostBatchReviewSetItems(postbatch.getId());
         List<Gift> result = new ArrayList<Gift>();
         for (PostBatchReviewSetItem item : list) {
             result.add(giftService.readGiftById(item.getEntityId()));
         }
         return result;
    }                                    

    // Sets fields on gifts in reviewed batch list
    @Override
    public PostBatch postBatch(PostBatch postbatch) {
        postbatch.getUpdateErrors().clear();
        List<Gift> list = getBatchSelectionList(postbatch);
        for (Gift gift: list) {
            for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
                siteService.populateDefaultEntityEditorMaps(gift);
                BeanWrapperImpl bw = new BeanWrapperImpl(gift);
                try {
                    bw.setPropertyValue(me.getKey(), me.getValue());    // TODO make work for dates
                    giftService.maintainGift(gift);
                } catch (BindException e) {
                    String msg = gift.getId() + ": " + e.getMessage();
                    logger.error(msg);
                    postbatch.getUpdateErrors().add(msg);
                }
            }
        }
        if (postbatch.getUpdateErrors().size() > 0) {
            // rollback entire batch for now.  otherwise we have to support reprocess partial
            throw new PostBatchUpdateException(postbatch.getUpdateErrors());   
        }
        postbatch.setPosted(true);
        postbatch.setPostedDate(new java.util.Date());
        postbatch.setPostedById(tangerineUserHelper.lookupUserId());
        postBatchDao.maintainPostBatch(postbatch);
        postBatchDao.deletePostBatchItems(postbatch.getId());
        return postbatch;
    }

    public final static class PostBatchUpdateException extends RuntimeException {

        private List<String> errors;

        public PostBatchUpdateException(List<String> errors) {
            super("Errors exist in batch: " + errors.get(0));
            this.errors = errors;
        }
        
        public List<String> getErrors() {
            return errors;
        }

    }

}