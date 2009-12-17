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
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Journal;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.Postable;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.PostBatchEntryService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("postBatchEntryService")
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {BindException.class})
public class PostBatchEntryServiceImpl extends AbstractTangerineService implements PostBatchEntryService {

    protected final Log logger = OLLogger.getLog(getClass());
    public final static String DEBIT = "debit";
    public final static String CREDIT = "credit";
    public final static String BANK = "bank";
    public final static String ADJUSTED_GIFT = "adjustedgift";
    public final static String DISTRO_LINE = "distributionline";
    public final static String DEFAULT = "_default";
    
    @Resource(name = "siteService")
    private SiteService siteService;

    @Resource(name = "fieldService")
    private FieldService fieldService;

    @Resource(name = "picklistItemService")
    private PicklistItemService picklistItemService;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Resource(name = "journalDAO")
    private JournalDao journalDao;

    @Override
    public boolean executeBatchEntry(PostBatch batch, AbstractCustomizableEntity entity) throws BindException {
        if (logger.isTraceEnabled()) {
            logger.trace("executeBatchEntry: batchId = " + batch.getId() + " entityType = " + entity.getType() + " entityId = " + entity.getId());
        }
        boolean doPost = false;
        boolean executed = false;
        final BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);

        // Record previous values for audit trail
        siteService.populateDefaultEntityEditorMaps(entity);

        // for a new execution of an entity, clear out any existing batch errors
        clearBatchErrorsInEntity(entity);

        if (entity instanceof Postable) { // Gift and AdjustedGift are the only Postable objects
            final String postedDateString = batch.getUpdateFieldValue(StringConstants.POSTED_DATE);

            if (StringUtils.hasText(postedDateString)) {
                Date postedDate = null;

                if (isValidPostedDate(postedDateString)) {
                    postedDate = parsePostedDate(postedDateString);
                    doPost = true;
                }
                else {
                    addBatchErrorToEntity(entity, "invalidPostedDate", postedDateString);
                }
                Boolean isPosted = ((Postable) entity).isPosted();
                if (postedDate != null && isPosted) {
                    // Don't allow double posting
                    addBatchErrorToEntity(entity, "cannotRepost", StringUtils.capitalize(entity.getType()), StringConstants.EMPTY + entity.getId());
                }
                else {
                    ((Postable) entity).setPostedDate(postedDate);
                    ((Postable) entity).setPosted(true);
                }
            }
        }
        if ( ! hasBatchErrorsInEntity(entity)) {
            for (Map.Entry<String, String> updateFldEntry : batch.getUpdateFields().entrySet()) {
                if ( ! updateFldEntry.getKey().equals(StringConstants.POSTED_DATE)) { // posted date is handled above
                    // the batchType + updateField key is the FieldDefinitionID; we need to resolve the FieldName from it
                    String fieldDefinitionId = new StringBuilder(batch.getBatchType()).append(".").append(updateFldEntry.getKey()).toString();

                    final FieldDefinition fieldDef = fieldService.readFieldDefinition(fieldDefinitionId);
                    if (fieldDef != null && bw.isWritableProperty(fieldDef.getFieldName())) {
                        String propertyName = fieldDef.getFieldName();
                        if (propertyName.indexOf(StringConstants.CUSTOM_FIELD_MAP_START) > -1) {
                            propertyName += StringConstants.DOT_VALUE;
                        }
                        Class clazz = bw.getPropertyType(propertyName);
                        if (String.class.equals(clazz)) {
                            bw.setPropertyValue(propertyName, updateFldEntry.getValue());
                        }
                        else if (Date.class.equals(clazz)) {
                            try {
                                Date date = DateUtils.parseDate(updateFldEntry.getValue(), new String[] { StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1,
                                        StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_2, StringConstants.YYYY_MM_DD_FORMAT,
                                        StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_1, StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_2,
                                        StringConstants.MM_DD_YYYY_FORMAT});
                                bw.setPropertyValue(propertyName, date);
                            }
                            catch (Exception exception) {
                                addBatchErrorToEntity(entity, "invalidDateField", updateFldEntry.getValue(), fieldDef.getDefaultLabel());
                            }
                        }
                        else if (Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)) {
                            bw.setPropertyValue(propertyName, Boolean.parseBoolean(updateFldEntry.getValue()));
                        }
                        else if (Byte.class.equals(clazz) || Integer.class.equals(clazz) || Short.class.equals(clazz) || Long.class.equals(clazz) ||
                                Byte.TYPE.equals(clazz) || Integer.TYPE.equals(clazz) || Short.TYPE.equals(clazz) || Long.TYPE.equals(clazz) ||
                                Double.class.equals(clazz) || Float.class.equals(clazz) || Double.TYPE.equals(clazz) || Float.TYPE.equals(clazz) ||
                                BigDecimal.class.equals(clazz)) {
                            if (NumberUtils.isNumber(updateFldEntry.getValue())) {
                                if (BigDecimal.class.equals(clazz)) {
                                    bw.setPropertyValue(propertyName, new BigDecimal(updateFldEntry.getValue()));
                                }
                                else {
                                    bw.setPropertyValue(propertyName, NumberUtils.createNumber(updateFldEntry.getValue()));
                                }
                            }
                            else {
                                addBatchErrorToEntity(entity, "invalidField", updateFldEntry.getValue(), fieldDef.getDefaultLabel());
                            }
                        }
                    }
                }
            }
        }
        if ( ! hasBatchErrorsInEntity(entity)) {
            entity.setSuppressValidation(true);

            if (entity instanceof Gift) {
                Gift gift = (Gift) entity;
                gift = giftService.editGift(gift);
                if (doPost) {
                    createJournalEntries(gift, null, batch);
                }
            }
            else if (entity instanceof AdjustedGift) {
                AdjustedGift adjustedGift = (AdjustedGift) entity;
                adjustedGift = adjustedGiftService.editAdjustedGift(adjustedGift);
                if (doPost) {
                    Gift gift = giftService.readGiftById(adjustedGift.getOriginalGiftId());
                    createJournalEntries(gift, adjustedGift, batch);
                }
            }
            executed = true;
        }
        if (hasBatchErrorsInEntity(entity)) {
            throw new PostBatchUpdateException(getBatchErrorsInEntity(entity));
        }
        return executed;
    }

    private void createJournalEntries(Gift gift, AdjustedGift adjustedGift, PostBatch batch) {
        final Map<String, String> bankCodeMap = new HashMap<String, String>();
        final Map<String, String> projectCodeMap = new HashMap<String, String>();
        createBankProjectCodeMaps(bankCodeMap, projectCodeMap);

        createJournalEntry(gift, adjustedGift, null, batch, bankCodeMap, projectCodeMap);
        List<DistributionLine> distributionLines = adjustedGift == null ? gift.getDistributionLines() : adjustedGift.getDistributionLines();
        for (DistributionLine line : distributionLines) {
            createJournalEntry(gift, adjustedGift, line, batch, bankCodeMap, projectCodeMap);
        }
    }

    private void createJournalEntry(Gift gift, AdjustedGift adjustedGift, DistributionLine distributionLine,
                                    PostBatch batch, Map<String, String> bankCodeMap, Map<String, String> projectCodeMap) {
        boolean isDebit = gift.getAmount().compareTo(BigDecimal.ZERO) >= 0;
        boolean isHeader = distributionLine == null;
        boolean isGift = adjustedGift == null;

        Journal journal = new Journal();
        journal.setSiteName(getSiteName());
        journal.setPostedDate(new java.util.Date());
        journal.setPostBatchId(batch.getId());

        journal.setDonationDate(gift.getDonationDate());
        journal.setPaymentMethod(gift.getPaymentType());
        if (gift.getPaymentSource() != null) {
            journal.setCcType(gift.getPaymentSource().getCreditCardType());
        }

        if (isHeader) {
            // Gift or Adjusted Gift
            if (isGift) {
                journal.setJeType(isDebit ? DEBIT : CREDIT);
                journal.setEntity(StringConstants.GIFT);
                journal.setEntityId(gift.getId());
                journal.setAmount(gift.getAmount());
                journal.setCode(getBankCode(gift, bankCodeMap));
                journal.setDescription(TangerineMessageAccessor.getMessage("journalGiftDescription", gift.getConstituent().getRecognitionName()));
            }
            else {
                journal.setJeType(isDebit ? CREDIT : DEBIT);
                journal.setEntity(ADJUSTED_GIFT);
                journal.setEntityId(adjustedGift.getId());
                journal.setOrigEntity(StringConstants.GIFT);
                journal.setOrigEntityId(gift.getId());
                journal.setAmount(adjustedGift.getAdjustedAmount());
                journal.setCode(getBankCode(adjustedGift, bankCodeMap));
                journal.setAdjustmentDate(adjustedGift.getAdjustedTransactionDate());
                journal.setDescription(TangerineMessageAccessor.getMessage("journalAdjustedGiftDescription", gift.getId().toString(), gift.getConstituent().getRecognitionName()));
            }
            updateJournalCodes(journal, bankCodeMap, journal.getCode(), isGift ? gift : adjustedGift);
        }
        else {
            // Distribution lines
            journal.setEntity(DISTRO_LINE);
            journal.setEntityId(distributionLine.getId());

            journal.setMasterEntity(isGift ? StringConstants.GIFT : ADJUSTED_GIFT);
            journal.setMasterEntityId(isGift ? gift.getId() : adjustedGift.getId());

            journal.setAmount(distributionLine.getAmount());
            journal.setCode(getProjectCode(distributionLine, projectCodeMap));

            if (isGift) {
                journal.setJeType(isDebit ? CREDIT : DEBIT);
                journal.setDescription(TangerineMessageAccessor.getMessage("journalDistroLineGiftDescription", gift.getId().toString(), gift.getConstituent().getRecognitionName()));
            }
            else {
                journal.setJeType(isDebit ? DEBIT : CREDIT);
                journal.setDescription(TangerineMessageAccessor.getMessage("journalDistroLineAdjustedGiftDescription", adjustedGift.getId().toString(), gift.getId().toString(), gift.getConstituent().getRecognitionName()));
                journal.setAdjustmentDate(adjustedGift.getAdjustedTransactionDate());
                journal.setOrigEntity(StringConstants.GIFT);
                journal.setOrigEntityId(gift.getId());
            }
            updateJournalCodes(journal, projectCodeMap, journal.getCode(), isGift ? gift : adjustedGift);
        }
        journalDao.maintainJournal(journal);
    }

    @Override
    public void clearBatchErrorsInEntity(AbstractCustomizableEntity entity) {
        if (hasBatchErrorsInEntity(entity)) {
            entity.setCustomFieldValue(StringConstants.BATCH_ERROR, StringConstants.EMPTY);
        }
    }
    
    private String getBankCode(AbstractCustomizableEntity entity, Map<String, String> bankCodeMap) {
        String defaultBank = bankCodeMap.get(DEFAULT);
        String bank = entity.getCustomFieldValue(BANK);
        if (bank == null) {
            bank = defaultBank;
        }
        bank = (bank == null ? StringConstants.EMPTY : bank.trim());
        if (bank.equalsIgnoreCase(StringConstants.NONE)) {
            bank = StringConstants.EMPTY;
        }
        return bank;
    }

    private String getProjectCode(DistributionLine distributionLine, Map<String, String> projectCodeMap) {
        String defaultCode = projectCodeMap.get(DEFAULT);
        String projectCode = distributionLine.getProjectCode();
        if (projectCode == null) {
            projectCode = defaultCode;
        }
        projectCode = (projectCode == null ? StringConstants.EMPTY : projectCode.trim());
        if (projectCode.equalsIgnoreCase(StringConstants.NONE)) {
            projectCode = StringConstants.EMPTY;
        }
        return projectCode;
    }

    private void updateJournalCodes(Journal journal, Map<String, String> map, String code, AbstractCustomizableEntity entity) {
        String glAccount1 = map.get(getKey(code, StringConstants.ACCOUNT_STRING_1));
        if (glAccount1 == null) {
            addBatchErrorToEntity(entity, "invalidAccountString1", code);
        }
        journal.setGlAccount1(glAccount1);

        String glAccount2 = map.get(getKey(code, StringConstants.ACCOUNT_STRING_2));
        if (glAccount2 == null) {
            addBatchErrorToEntity(entity, "invalidAccountString2", code);
        }
        journal.setGlAccount2(glAccount2);

        String glCode = map.get(getKey(code, StringConstants.GL_ACCOUNT_CODE));
        if (glCode == null) {
            addBatchErrorToEntity(entity, "invalidGLCode", code);
        }
        journal.setGlCode(glCode);
    }

    private String getKey(String s1, String s2) {
        return new StringBuilder(s1).append(" : ").append(s2).toString();
    }

    @Override
    public boolean isValidPostedDate(final String postedDateString) {
        boolean isValid;
        try {
            DateUtils.parseDate(postedDateString, new String[] { StringConstants.MM_DD_YYYY_FORMAT,
                    StringConstants.YYYY_MM_DD_FORMAT,
                    StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1,
                    StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_2 });
            isValid = true;
        }
        catch (Exception ex) {
            // should not get thrown if check for errors run first
            logger.warn("isValidPostedDate: Could not parse postedDate " + postedDateString);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public Date parsePostedDate(final String postedDateString) {
        Date date = null;
        try {
            date = DateUtils.parseDate(postedDateString, new String[] { StringConstants.MM_DD_YYYY_FORMAT,
                    StringConstants.YYYY_MM_DD_FORMAT,
                    StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1,
                    StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_2 });
        }
        catch (Exception ex) {
            // should not get thrown if isValidPostedDate() run first
            logger.warn("parsePostedDate: Could not parse postedDate " + postedDateString);
        }
        return date;
    }

    @Override
    public void addBatchErrorToEntity(AbstractCustomizableEntity entity, String errorMsgKey, String... args) {
        entity.addCustomFieldValue(StringConstants.BATCH_ERROR, TangerineMessageAccessor.getMessage(errorMsgKey, args));
    }

    @Override
    public boolean hasBatchErrorsInEntity(AbstractCustomizableEntity entity) {
        return StringUtils.hasText(getBatchErrorsInEntity(entity));
    }

    @Override
    public String getBatchErrorsInEntity(AbstractCustomizableEntity entity) {
        return entity.getCustomFieldValue(StringConstants.BATCH_ERROR);
    }

    @Override
    public void createBankProjectCodeMaps(final Map<String, String> bankCodeMap, final Map<String, String> projectCodeMap) {
        Picklist bankCodes = picklistItemService.getPicklist(StringConstants.BANK_CUSTOM_FIELD);
        addBankProjectCodesToMap(bankCodes, bankCodeMap);

        Picklist projectCodes = picklistItemService.getPicklist(StringConstants.PROJECT_CODE);
        addBankProjectCodesToMap(projectCodes, projectCodeMap);
    }

    @Override
    public void addBankProjectCodesToMap(Picklist picklist, Map<String, String> map) {
        if (picklist != null && picklist.getActivePicklistItems() != null) {
            for (PicklistItem item : picklist.getActivePicklistItems()) {
                if (item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1) == null ||
                        item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2) == null ||
                        item.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE) == null) {
                    continue;
                }
                map.put(getKey(item.getItemName(), StringConstants.ACCOUNT_STRING_1), item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1));
                map.put(getKey(item.getItemName(), StringConstants.ACCOUNT_STRING_2), item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2));
                map.put(getKey(item.getItemName(), StringConstants.GL_ACCOUNT_CODE), item.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE));
            }
            if ( ! picklist.getActivePicklistItems().isEmpty()) {
                PicklistItem defaultItem = picklist.getActivePicklistItems().get(0);
                map.put(getKey(StringConstants.EMPTY, StringConstants.ACCOUNT_STRING_1), defaultItem.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1));
                map.put(getKey(StringConstants.EMPTY, StringConstants.ACCOUNT_STRING_2), defaultItem.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2));
                map.put(getKey(StringConstants.EMPTY, StringConstants.GL_ACCOUNT_CODE), defaultItem.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE));
                map.put(DEFAULT, defaultItem.getItemName());
            }
        }
    }

    public final static class PostBatchUpdateException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private Collection<String> errors;

        public PostBatchUpdateException(Collection<String> errors) {
            super("Errors exist in batch: " + StringUtils.collectionToCommaDelimitedString(errors));
            this.errors = errors;
        }

        @SuppressWarnings("unchecked")
        public PostBatchUpdateException(String errors) {
            super("Errors exist in batch: " + errors);
            this.errors = StringUtils.commaDelimitedListToSet(errors);
        }

        public Collection<String> getErrors() {
            return errors;
        }
    }
}
