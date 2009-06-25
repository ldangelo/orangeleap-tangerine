package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.domain.*;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.service.*;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.dao.JournalDao;
import com.orangeleap.tangerine.dao.PaymentSourceDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

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

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "journalDAO")
    private JournalDao journalDao;

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;


    private final static String ACCOUNT_STRING_1 = "AccountString1";
    private final static String ACCOUNT_STRING_2 = "AccountString2";
    private final static String GL_ACCOUNT_CODE = "GLAccountCode";



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

        return map;
    }

    @Override
    public Map<String, String> readAllowedGiftUpdateFields() {
        // TODO read gift entry screen for custom fields?
        Map<String, String> map = new TreeMap<String, String>();
       map.put("postedDate", "Posted Date");
       map.put("giftStatus", "Gift Status");
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
    public List<AbstractPaymentInfoEntity> createBatchSelectionList(PostBatch postbatch) {
        postBatchDao.deletePostBatchItems(postbatch.getId());
        
        Map<String, Object> map = new HashMap();
        for (Map.Entry<String, String> me : postbatch.getWhereConditions().entrySet()) {
            map.put(me.getKey(), me.getValue());
        }

        //  TODO change to use INSERT INTO table (field) SELECT field2 from table2
        List list = new ArrayList<AbstractPaymentInfoEntity>();
        if (GIFT.equals(postbatch.getEntity())) {
            list = giftService.searchGifts(map);
        } else if (ADJUSTED_GIFT.equals(postbatch.getEntity())) {
            // list = adjustedGiftService.searchAdjustedGifts(map);
        }

        // if (list.size() == 200) throw new RuntimeException("Too many items selected"); // see gift.xml for search limit
        // TODO raise this limit from 200 to ?
        Iterator<AbstractPaymentInfoEntity> it = list.iterator();
        while (it.hasNext()) {
            AbstractPaymentInfoEntity apie = it.next();
            if (!isPosted(apie) && hasProjectCodes(apie)) {
                PostBatchReviewSetItem item = new PostBatchReviewSetItem();
                item.setEntityId(apie.getId());
                item.setPostBatchId(postbatch.getId());
                postBatchDao.maintainPostBatchReviewSetItem(item);
            } else {
                it.remove();
                logger.debug("Excluding item from search results - Missing bank or project codes on id "+apie.getId());
            }
        }
        postbatch.setReviewSetGenerated(true);
        postbatch.setReviewSetGeneratedDate(new java.util.Date());
        postbatch.setReviewSetGeneratedById(tangerineUserHelper.lookupUserId());
        postBatchDao.maintainPostBatch(postbatch);
        return list;
    }

    private boolean isPosted(AbstractPaymentInfoEntity apie) {
        return "true".equals(""+new BeanWrapperImpl(apie).getPropertyValue("posted"));
    }

    private boolean hasProjectCodes(AbstractPaymentInfoEntity apie) {
        for (DistributionLine dl : apie.getDistributionLines()) {
            if (dl.getProjectCode() == null || dl.getProjectCode().trim().length() == 0) return false;
        }
        return true;
    }


    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
    @Override
    public List<AbstractPaymentInfoEntity> getBatchSelectionList(PostBatch postbatch) {
         List<PostBatchReviewSetItem> list = postBatchDao.readPostBatchReviewSetItems(postbatch.getId());
         List<AbstractPaymentInfoEntity> result = new ArrayList<AbstractPaymentInfoEntity>();
         boolean isGift = GIFT.equals(postbatch.getEntity());
         for (PostBatchReviewSetItem item : list) {
             if (isGift) {
                 result.add(giftService.readGiftById(item.getEntityId()));
             } else {
                 result.add(adjustedGiftService.readAdjustedGiftById(item.getEntityId()));
             }
         }
         return result;
    }

    private void createMaps(Map<String, String> bankmap, Map<String, String> codemap) {

        Picklist bankCodes = picklistItemService.getPicklist("customFieldMap[bank]");
        if (bankCodes == null || bankCodes.getActivePicklistItems().size() == 0) {
            throw new RuntimeException("Bank GL account codes not defined.");
        }

        for (PicklistItem item : bankCodes.getPicklistItems()) {
            if (item.getCustomFieldValue(ACCOUNT_STRING_1) == null
                || item.getCustomFieldValue(ACCOUNT_STRING_2) == null
                || item.getCustomFieldValue(GL_ACCOUNT_CODE) == null
            ) {
                throw new RuntimeException("GL account codes not defined for bank code "+item.getDefaultDisplayValue());
            }
            bankmap.put(getKey(item.getItemName(), ACCOUNT_STRING_1), item.getCustomFieldValue(ACCOUNT_STRING_1));
            bankmap.put(getKey(item.getItemName(), ACCOUNT_STRING_2), item.getCustomFieldValue(ACCOUNT_STRING_2));
            bankmap.put(getKey(item.getItemName(), GL_ACCOUNT_CODE), item.getCustomFieldValue(GL_ACCOUNT_CODE));
        }
        if (bankCodes.getPicklistItems().size() > 0) {
            PicklistItem defaultItem = bankCodes.getPicklistItems().get(0);
            bankmap.put(getKey("", ACCOUNT_STRING_1), defaultItem.getCustomFieldValue(ACCOUNT_STRING_1));
            bankmap.put(getKey("", ACCOUNT_STRING_2), defaultItem.getCustomFieldValue(ACCOUNT_STRING_2));
            bankmap.put(getKey("", GL_ACCOUNT_CODE), defaultItem.getCustomFieldValue(GL_ACCOUNT_CODE));
            bankmap.put(DEFAULT, defaultItem.getItemName());
        }

        // Project codes are stored in distribution lines as default display values rather than item names
        Picklist projectCodes = picklistItemService.getPicklist("projectCode");
        if (projectCodes == null || projectCodes.getActivePicklistItems().size() == 0) {
            throw new RuntimeException("Designation codes for GL account codes not defined.");
        }

        for (PicklistItem item : projectCodes.getPicklistItems()) {
            if (item.getCustomFieldValue(ACCOUNT_STRING_1) == null
                || item.getCustomFieldValue(ACCOUNT_STRING_2) == null
                || item.getCustomFieldValue(GL_ACCOUNT_CODE) == null
            ) {
                throw new RuntimeException("GL account code not defined for designation code "+item.getDefaultDisplayValue());
            }
            codemap.put(getKey(item.getDefaultDisplayValue(), ACCOUNT_STRING_1), item.getCustomFieldValue(ACCOUNT_STRING_1));
            codemap.put(getKey(item.getDefaultDisplayValue(), ACCOUNT_STRING_2), item.getCustomFieldValue(ACCOUNT_STRING_2));
            codemap.put(getKey(item.getDefaultDisplayValue(), GL_ACCOUNT_CODE), item.getCustomFieldValue(GL_ACCOUNT_CODE));
        }


    }

    private void saveGift(Gift gift) throws BindException {
        // TODO uncomment once complete TANGERINE-816 gift domain/form object separation
        //giftService.maintainGift(gift);
    }

    private void saveAdjustedGift(AdjustedGift adjustedGift) throws BindException {
        // TODO uncomment once complete TANGERINE-816 adjustedGift domain/form object separation
        //adjustedGiftService.maintainAdjustedGift(adjustedGift);
    }

    // Sets fields on gifts/adjusted gifts in reviewed batch list
    @Override
    public PostBatch postBatch(PostBatch postbatch) {


        boolean isGift = GIFT.equals(postbatch.getEntity());

        Date postDate = new java.util.Date();

        Map<String, String> bankmap = new HashMap<String, String>();
        Map<String, String> codemap = new HashMap<String, String>();
        createMaps(bankmap, codemap);

        postbatch.getUpdateErrors().clear();
        List<AbstractPaymentInfoEntity> list = getBatchSelectionList(postbatch);
        for (AbstractPaymentInfoEntity apie: list) {
            
            if (isGift) {
                apie = giftService.readGiftById(apie.getId());
            } else {
                apie = adjustedGiftService.readAdjustedGiftById(apie.getId());
            }

            siteService.populateDefaultEntityEditorMaps(apie);

            BeanWrapperImpl bw = new BeanWrapperImpl(apie);

            bw.setPropertyValue("posted", true);
            bw.setPropertyValue("postedDate", postDate);

            for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
                bw.setPropertyValue(me.getKey(), me.getValue());    // TODO make work for dates
            }

            try {

                if (isGift) {
                    Gift gift = (Gift)apie;
                    saveGift(gift);
                    createJournalEntries(gift, null, postbatch, codemap, bankmap);
                } else {
                    AdjustedGift ag = (AdjustedGift)apie;
                    saveAdjustedGift(ag);
                    Gift gift = giftService.readGiftById(ag.getOriginalGiftId());
                    createJournalEntries(gift, ag, postbatch, codemap, bankmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
                String msg = apie.getId() + ": " + e.getMessage();
                logger.error(msg);
                postbatch.getUpdateErrors().add(msg);
            }

        }

        if (postbatch.getUpdateErrors().size() > 0) {
            // rollback entire batch for now.  otherwise we have to support reprocess partial
            throw new PostBatchUpdateException(postbatch.getUpdateErrors());   
        }

        postbatch.setPosted(true);
        postbatch.setPostedDate(postDate);
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

    private void createJournalEntries(Gift gift, AdjustedGift ag, PostBatch postbatch, Map<String, String> codemap, Map<String, String> bankmap) {
        createJournalEntry(gift, ag, null, postbatch, codemap, bankmap);
        List<DistributionLine> dls = ag == null ? gift.getDistributionLines() : ag.getDistributionLines();
        for (DistributionLine dl : gift.getDistributionLines()) {
            createJournalEntry(gift, ag, dl, postbatch, codemap, bankmap);
        }
    }

    private static String DEBIT = "debit";
    private static String CREDIT = "credit";
    private static String BANK = "bank";
    private static String GIFT = "gift";
    private static String ADJUSTED_GIFT = "adjustedgift";
    private static String DISTRO_LINE = "distributionline";
    private static String DEFAULT = "_default";


    private void createJournalEntry(Gift gift, AdjustedGift ag, DistributionLine dl, PostBatch postbatch, Map<String, String> codemap, Map<String, String> bankmap) {

        boolean isDebit = gift.getAmount().compareTo(new BigDecimal("0")) >= 0;
        boolean isHeader = dl == null;
        boolean isGift = ag == null;

        Journal journal = new Journal();
        journal.setSiteName(getSiteName());
        journal.setPostedDate(new java.util.Date());
        journal.setPostBatchId(postbatch.getId());

        if (isHeader) {
            
            // Gift or Adjusted Gift

            journal.setJeType(isDebit ? DEBIT : CREDIT);


            if (isGift) {
                journal.setEntity(GIFT);
                journal.setEntityId(gift.getId());
                journal.setAmount(gift.getAmount());
                journal.setCode(getBank(gift, bankmap));
                journal.setDonationDate(gift.getDonationDate());
                journal.setDescription("Gift from " + gift.getConstituent().getRecognitionName());   
                journal.setPaymentMethod(gift.getPaymentType());
                journal.setCcType(gift.getSelectedPaymentSource().getCreditCardType());
            } else {
                journal.setEntity(ADJUSTED_GIFT);
                journal.setEntityId(ag.getId());
                journal.setOrigEntity(GIFT);
                journal.setOrigEntityId(gift.getId());
                journal.setAmount(ag.getAdjustedAmount());
                journal.setCode(getBank(ag, bankmap));
                journal.setAdjustmentDate(ag.getAdjustedTransactionDate());
                journal.setDescription("Adjustment associated with gift ID " + gift.getId() + " from " + gift.getConstituent().getRecognitionName());
                journal.setPaymentMethod(ag.getPaymentType());
                journal.setCcType(ag.getSelectedPaymentSource().getCreditCardType());
            }

            updateJournalCodes(journal, bankmap, journal.getCode(), postbatch);

        } else {

            // Distribution lines

            journal.setJeType(isDebit ? CREDIT : DEBIT);

            journal.setEntity(DISTRO_LINE);
            journal.setEntityId(dl.getId());

            journal.setMasterEntity(isGift ? GIFT : ADJUSTED_GIFT);
            journal.setMasterEntityId(isGift ? gift.getId() : ag.getId());

            journal.setAmount(dl.getAmount());
            journal.setCode(getProjectCode(dl));

            if (isGift) {
                journal.setDescription("Associated with gift ID " + gift.getId() + " from " + gift.getConstituent().getRecognitionName());
            } else {
                journal.setDescription("Adjusted gift ID " + ag.getId() + ", associated with original gift ID " + gift.getId() + " from " + gift.getConstituent().getRecognitionName());
            }
            
            updateJournalCodes(journal, codemap, journal.getCode(), postbatch);

        }

        journalDao.maintainJournal(journal);
    }

    private String getBank(AbstractCustomizableEntity e, Map<String, String> bankmap) {
        String defaultbank = bankmap.get(DEFAULT);
        String bank = e.getCustomFieldValue(BANK);
        if (bank == null) bank = defaultbank;
        bank = (bank == null ? "" : bank.trim());
        if (bank.equalsIgnoreCase("none")) bank = "";
        return bank;
    }

    private String getProjectCode(DistributionLine dl) {
        String pc = dl.getProjectCode();
        pc = (pc == null ? "" : pc.trim());
        if (pc.equalsIgnoreCase("none")) pc = "";
        return pc;
    }

    private void updateJournalCodes(Journal journal, Map<String, String> map, String code, PostBatch postbatch) {

        String glAccount1 = map.get(getKey(code,ACCOUNT_STRING_1));
        if (glAccount1 == null) postbatch.getUpdateErrors().add("Invalid AccountString1 for "+code);
        journal.setGlAccount1(glAccount1);

        String glAccount2 = map.get(getKey(code,ACCOUNT_STRING_2));
        if (glAccount2 == null) postbatch.getUpdateErrors().add("Invalid AccountString2 for "+code);
        journal.setGlAccount2(glAccount2);

        String glCode = map.get(getKey(code,GL_ACCOUNT_CODE));
        if (glCode == null) postbatch.getUpdateErrors().add("Invalid GL Code for "+code);
        journal.setGlCode(glCode);
        
    }

    private String getKey(String s1, String s2) {
        return s1 + " : " + s2;
    }


}