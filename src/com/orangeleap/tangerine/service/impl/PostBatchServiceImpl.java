package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.PicklistItemService;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.dao.JournalDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.annotation.Resource;
import java.util.*;
import java.math.BigDecimal;

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

    @Resource(name = "journalDAO")
    private JournalDao journalDao;

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

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


    private final static String ACCOUNT_STRING_1 = "AccountString1";
    private final static String ACCOUNT_STRING_2 = "AccountString2";

    // Sets fields on gifts in reviewed batch list
    @Override
    public PostBatch postBatch(PostBatch postbatch) {

        Picklist projectCodes = picklistItemService.getPicklist("projectCode");
        if (projectCodes == null || projectCodes.getActivePicklistItems().size() == 0) {
            throw new RuntimeException("Designation codes for GL account codes not defined.");
        }
        Map<String, String> codemap = new HashMap<String, String>();
        for (PicklistItem item : projectCodes.getPicklistItems()) {
            if (item.getCustomFieldValue(ACCOUNT_STRING_1) == null ||item.getCustomFieldValue(ACCOUNT_STRING_2) == null) {
                throw new RuntimeException("GL account code not defined for designation code "+item.getDefaultDisplayValue());
            }
            codemap.put(item.getItemName() + ACCOUNT_STRING_1, item.getCustomFieldValue(ACCOUNT_STRING_1));
            codemap.put(item.getItemName() + ACCOUNT_STRING_2, item.getCustomFieldValue(ACCOUNT_STRING_2));
        }

        Picklist bankCodes = picklistItemService.getPicklist("customFieldMap[bank]");
        if (bankCodes == null || bankCodes.getActivePicklistItems().size() == 0) {
            throw new RuntimeException("Bank GL account codes not defined.");
        }
        Map<String, String> bankmap = new HashMap<String, String>();
        for (PicklistItem item : bankCodes.getPicklistItems()) {
            if (item.getCustomFieldValue(ACCOUNT_STRING_1) == null ||item.getCustomFieldValue(ACCOUNT_STRING_2) == null) {
                throw new RuntimeException("GL account code not defined for bank code "+item.getDefaultDisplayValue());
            }
            bankmap.put(item.getItemName() + ACCOUNT_STRING_1, item.getCustomFieldValue(ACCOUNT_STRING_1));
            bankmap.put(item.getItemName() + ACCOUNT_STRING_2, item.getCustomFieldValue(ACCOUNT_STRING_2));
        }

        postbatch.getUpdateErrors().clear();
        List<Gift> list = getBatchSelectionList(postbatch);
        for (Gift gift: list) {
            for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
                siteService.populateDefaultEntityEditorMaps(gift);
                BeanWrapperImpl bw = new BeanWrapperImpl(gift);
                try {
                    bw.setPropertyValue(me.getKey(), me.getValue());    // TODO make work for dates
                    giftService.maintainGift(gift);
                    createJournalEntries(gift, postbatch, codemap, bankmap);
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

    private void createJournalEntries(Gift gift, PostBatch postbatch, Map<String, String> codemap, Map<String, String> bankmap) {
        createJournalEntry(gift, null, postbatch, codemap, bankmap);
        for (DistributionLine dl : gift.getDistributionLines()) {
            createJournalEntry(gift, dl, postbatch, codemap, bankmap);
        }
    }

    private static String DEBIT = "debit";
    private static String CREDIT = "credit";
    private static String BANK = "bank";
    private static String GIFT = "gift";

    private void createJournalEntry(Gift gift, DistributionLine dl, PostBatch postbatch, Map<String, String> codemap, Map<String, String> bankmap) {
        boolean isDebit = gift.getAmount().compareTo(new BigDecimal("0")) >= 0;
        Journal journal = new Journal();
        journal.setEntity(GIFT);
        journal.setEntityId(gift.getId());
        journal.setAmount(gift.getAmount());
        journal.setSiteName(getSiteName());
        journal.setPostedDate(new java.util.Date());
        journal.setPostBatchId(postbatch.getId());
        if (dl == null) {
            journal.setJeType(isDebit?DEBIT:CREDIT);
            String bank = gift.getCustomFieldValue(BANK);
            journal.setGlCode1(bankmap.get(bank+ACCOUNT_STRING_1));
            journal.setGlCode2(bankmap.get(bank+ACCOUNT_STRING_2));
        } else {
            journal.setJeType(isDebit?CREDIT:DEBIT);
            journal.setGlCode1(codemap.get(dl.getProjectCode()+ACCOUNT_STRING_1));
            journal.setGlCode2(codemap.get(dl.getProjectCode()+ACCOUNT_STRING_2));
        }

        journal.setDonationDate(gift.getDonationDate());
        journalDao.maintainJournal(journal);
    }

}