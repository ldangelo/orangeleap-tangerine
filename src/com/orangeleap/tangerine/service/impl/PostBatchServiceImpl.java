/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.impl;

import com.orangeleap.tangerine.dao.JournalDao;
import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchReviewSetItem;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.*;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeanWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {

    public final static String DATE_FORMAT = "MM/dd/yyyy";


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

        boolean isGift = GIFT.equals(postbatch.getEntity());

        postBatchDao.deletePostBatchItems(postbatch.getId());
        
        Map<String, Object> searchmap = new HashMap();
        for (Map.Entry<String, String> me : postbatch.getWhereConditions().entrySet()) {
            searchmap.put(me.getKey(), me.getValue());
        }

        // TODO support search params
        if (isGift) {
            postBatchDao.insertIntoPostBatchFromGiftSelect(postbatch, searchmap); 
        } else {
            postBatchDao.insertIntoPostBatchFromAdjustedGiftSelect(postbatch, searchmap);
        }


        postbatch.setReviewSetGenerated(true);
        postbatch.setReviewSetGeneratedDate(new java.util.Date());
        postbatch.setReviewSetGeneratedById(tangerineUserHelper.lookupUserId());
        postbatch.setReviewSetSize(postBatchDao.getReviewSetSize(postbatch.getId()));
        postBatchDao.maintainPostBatch(postbatch);

        return getBatchSelectionList(postbatch);
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
        if (projectCodes.getPicklistItems().size() > 0) {
            PicklistItem defaultItem = projectCodes.getPicklistItems().get(0);
            codemap.put(getKey("", ACCOUNT_STRING_1), defaultItem.getCustomFieldValue(ACCOUNT_STRING_1));
            codemap.put(getKey("", ACCOUNT_STRING_2), defaultItem.getCustomFieldValue(ACCOUNT_STRING_2));
            codemap.put(getKey("", GL_ACCOUNT_CODE), defaultItem.getCustomFieldValue(GL_ACCOUNT_CODE));
            codemap.put(DEFAULT, defaultItem.getDefaultDisplayValue());
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

    private BeanWrapper addPropertyEditors(BeanWrapper bw) {
        bw.registerCustomEditor(java.util.Date.class, new java.beans.PropertyEditorSupport() {
            private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

            public void setAsText(java.lang.String s) throws java.lang.IllegalArgumentException {
                try {
                    this.setValue(dateFormat.parse(s));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            public java.lang.String getAsText() {
                return  dateFormat.format((Date)this.getValue());
            }

        });
        return bw;
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



    private void processUpdate(PostBatchReviewSetItem item, PostBatch postbatch, boolean post, Map<String, String> codemap, Map<String, String> bankmap) throws Exception {

        boolean isGift = GIFT.equals(postbatch.getEntity());
        AbstractPaymentInfoEntity apie;

        if (isGift) {
            apie = giftService.readGiftById(item.getEntityId());
        } else {
            apie = adjustedGiftService.readAdjustedGiftById(item.getEntityId());
        }

        // Record previous values for audit trail
        siteService.populateDefaultEntityEditorMaps(apie);

        BeanWrapper bw = addPropertyEditors(new BeanWrapperImpl(apie));

        boolean wasPreviouslyPosted = (Boolean)bw.getPropertyValue(POSTED);

        if (post) {

            // Don't allow double posting.
            if (wasPreviouslyPosted) {
                String msg = "Item "+apie.getId()+" previously posted - cannot re-post.";
                throw new RuntimeException(msg);
            }

            bw.setPropertyValue(POSTED, true);

        }

        // Set update values.  
        for (Map.Entry<String, String> me : postbatch.getUpdateFields().entrySet()) {
            bw.setPropertyValue(me.getKey(), me.getValue());
        }
        
        // Update record.
        if (isGift) {
            Gift gift = (Gift)apie;
            saveGift(gift);
            if (post) createJournalEntries(gift, null, postbatch, codemap, bankmap);
        } else {
            AdjustedGift ag = (AdjustedGift)apie;
            saveAdjustedGift(ag);
            Gift gift = giftService.readGiftById(ag.getOriginalGiftId());
            if (post) createJournalEntries(gift, ag, postbatch, codemap, bankmap);
        }

    }

    // Sets fields on gifts/adjusted gifts in reviewed batch list
    @Override
    public PostBatch updateBatch(PostBatch postbatch, boolean post) {

        postbatch.getUpdateErrors().clear();

        Date postedDate = null;
        Map<String, String> bankmap = new HashMap<String, String>();
        Map<String, String> codemap = new HashMap<String, String>();

        // Set properties 'posted' and optionally 'postedDate' if posting.
        if (post) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            postbatch.getUpdateFields().put(POSTED, "true");
            if (postbatch.getUpdateFields().get(POSTED_DATE) == null) {
                postbatch.getUpdateFields().put(POSTED_DATE, dateFormat.format(new java.util.Date()));
            }
            try {
                postedDate = dateFormat.parse(postbatch.getUpdateFields().get(POSTED_DATE));
            } catch (Exception e) {
                throw new RuntimeException("Invalid posted date.");
            }
            createMaps(bankmap, codemap);
        } else {
           // Can't update these fields if not posting.
           postbatch.getUpdateFields().remove(POSTED);
           postbatch.getUpdateFields().remove(POSTED_DATE);
        }
        

        // Get list to update
        // TODO process in single transactions and support partial batch updates.
        List<PostBatchReviewSetItem> list = postBatchDao.readPostBatchReviewSetItems(postbatch.getId());
        if (list.size() > 1000) throw new RuntimeException("Batch size limit exceeded.");   // TODO remove size limit

        for (PostBatchReviewSetItem item: list) {
            
            try {

                processUpdate(item, postbatch, post, codemap, bankmap);

            } catch (Exception e) {
                e.printStackTrace();
                String msg = item.getEntityId() + ": " + e.getMessage();
                logger.error(msg);
                postbatch.getUpdateErrors().add(msg);
            }

        }

        if (postbatch.getUpdateErrors().size() > 0) {
            // rollback entire batch for now.  otherwise we have to support reprocess partial
            throw new PostBatchUpdateException(postbatch.getUpdateErrors());   
        }

        if (post) {
            postbatch.setPosted(true);
            postbatch.setPostedDate(postedDate);
            postbatch.setPostedById(tangerineUserHelper.lookupUserId());
        } else {
            postbatch.setBatchUpdated(true);
            postbatch.setBatchUpdatedDate(new java.util.Date());
            postbatch.setBatchUpdatedById(tangerineUserHelper.lookupUserId());
        }
        
        // Update
        postbatch.getUpdateFields().remove(POSTED);  // This is a hidden update field for posting - don't show in list.
        postbatch = postBatchDao.maintainPostBatch(postbatch);
        
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
    private static String POSTED_DATE = "postedDate";
    private static String POSTED = "posted";
    private static String NONE = "none";


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
            journal.setCode(getProjectCode(dl, codemap));

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
        if (bank.equalsIgnoreCase(NONE)) bank = "";
        return bank;
    }

    private String getProjectCode(DistributionLine dl, Map<String, String> codemap) {
        String defaultcode = codemap.get(DEFAULT);
        String pc = dl.getProjectCode();
        if (pc == null) pc = defaultcode;
        pc = (pc == null ? "" : pc.trim());
        if (pc.equalsIgnoreCase(NONE)) pc = "";
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