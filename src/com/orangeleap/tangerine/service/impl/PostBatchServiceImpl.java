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

import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchEntry;
import com.orangeleap.tangerine.domain.Segmentation;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PostBatchEntryService;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.theguru.client.GetSegmentationCountByTypeRequest;
import com.orangeleap.theguru.client.GetSegmentationCountByTypeResponse;
import com.orangeleap.theguru.client.GetSegmentationListByTypeRequest;
import com.orangeleap.theguru.client.GetSegmentationListByTypeResponse;
import com.orangeleap.theguru.client.ObjectFactory;
import com.orangeleap.theguru.client.Theguru;
import com.orangeleap.theguru.client.WSClient;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service("postBatchService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
public class PostBatchServiceImpl extends AbstractTangerineService implements PostBatchService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "postBatchDAO")
    private PostBatchDao postBatchDao;

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "postBatchEntryService")
    private PostBatchEntryService postBatchEntryService;

//    public final static String DEBIT = "debit";
//    public final static String CREDIT = "credit";
//    public final static String BANK = "bank";
//    public final static String ADJUSTED_GIFT = "adjustedgift";
//    public final static String DISTRO_LINE = "distributionline";
//    public final static String DEFAULT = "_default";

    @Override
    public PostBatch maintainBatch(PostBatch batch) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainBatch: batchId = " + batch.getId());
        }
        batch.setBatchCreatedById(tangerineUserHelper.lookupUserId());
        batch.setBatchCreatedDate(new Date());
        return postBatchDao.maintainPostBatch(batch);
    }

    @Override
    public void deleteBatch(PostBatch batch) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteBatch: batchId = " + batch.getId());
        }
        if (batch.isExecuted()) {
            throw new RuntimeException("Cannot delete a batch that has already been executed.");
        }
        postBatchDao.deletePostBatch(batch);
    }

    @Override
    public List<PostBatch> readBatchesByStatus(String showBatchStatus, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBatchesByStatus: showBatchStatus = " + showBatchStatus + " sort = " + sort);
        }
        return postBatchDao.readBatchesByStatus(showBatchStatus, sort.getSort(), sort.getDir(), sort.getStart(),
                sort.getLimit(), locale);
    }

    @Override
    public int countBatchesByStatus(String showBatchStatus) {
        if (logger.isTraceEnabled()) {
            logger.trace("countBatchesByStatus: showBatchStatus = " + showBatchStatus);
        }
        return postBatchDao.countBatchesByStatus(showBatchStatus);
    }

    @Override
    public PostBatch readBatch(Long batchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBatch: batchId = " + batchId);
        }
        return postBatchDao.readPostBatchById(batchId);
    }

    @Override
    public PostBatch readBatchCreateIfNull(final Long batchId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBatchCreateIfNull: batchId = " + batchId);
        }

        PostBatch batch;
        if (batchId != null && batchId > 0) {
            batch = readBatch(batchId);
        }
        else {
            batch = new PostBatch();
        }
        return batch;
    }

    @Override
    public List<Map<String, Object>> findSegmentationsForBatchType(PostBatch batch, Set<Long> pickedSegmentationIds, String batchType, String sortField,
                                                                   String sortDirection, int startIndex, int resultCount) {
        if (logger.isTraceEnabled()) {
            logger.trace("findSegmentationsForBatchType: batchType = " + batchType + " sortField = " + sortField +
                    " sortDirection = " + sortDirection + " startIndex = " + startIndex + " resultCount = " + resultCount);
        }
        final List<Segmentation> segmentations = findSegmentations(batchType, sortField, sortDirection, startIndex, resultCount);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (segmentations != null) {
            final SimpleDateFormat sdf = new SimpleDateFormat(StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1);
            for (Segmentation segmentation : segmentations) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put(StringConstants.ID, segmentation.getId());
                returnMap.put(StringConstants.NAME, segmentation.getName());
                returnMap.put("desc", segmentation.getDescription());
                returnMap.put("count", segmentation.getCount());
                returnMap.put("lastDt", segmentation.getLastRunDate() == null ? null : sdf.format(segmentation.getLastRunDate()));
                returnMap.put("lastUser", segmentation.getLastRunByUser());
                returnMap.put("picked", pickedSegmentationIds.contains(segmentation.getId()));
                returnList.add(returnMap);
            }
        }
        return returnList;
    }

    private String resolveGuruSegmentationType(String batchType) {
        String resolvedType = null;
        if (StringConstants.GIFT.equals(batchType)) {
            resolvedType = StringConstants.GIFT_SEGMENTATION;
        }
        else if (StringConstants.ADJUSTED_GIFT.equals(batchType)) {
            resolvedType = StringConstants.ADJUSTED_GIFT_SEGMENTATION;
        }
        return resolvedType;
    }

    @Override
    public long findTotalSegmentations(final String batchType) {
        if (logger.isTraceEnabled()) {
            logger.trace("findTotalSegmentations: batchType = " + batchType);
        }
        final Theguru theGuru = new WSClient().getTheGuru();
        final ObjectFactory objFactory = new ObjectFactory();
        final GetSegmentationCountByTypeRequest req = objFactory.createGetSegmentationCountByTypeRequest();

        final String resolvedType = resolveGuruSegmentationType(batchType);

        long count = 0;
        if (resolvedType != null) {
            final GetSegmentationCountByTypeResponse resp = theGuru.getSegmentationCountByType(req);
            if (resp != null) {
                count = resp.getCount();
            }
        }
        return count;
    }

    @Override
    public List<Segmentation> findSegmentations(String batchType, String sortField, String sortDirection, int startIndex, int resultCount) {
        if (logger.isTraceEnabled()) {
            logger.trace("findSegmentations: batchType = " + batchType + " sortField = " + sortField +
                    " sortDirection = " + sortDirection + " startIndex = " + startIndex + " resultCount = " + resultCount);
        }
        final Theguru theGuru = new WSClient().getTheGuru();
        final ObjectFactory objFactory = new ObjectFactory();
        final GetSegmentationListByTypeRequest req = objFactory.createGetSegmentationListByTypeRequest();

        final List<Segmentation> returnSegmentations = new ArrayList<Segmentation>();
        final String resolvedType = resolveGuruSegmentationType(batchType);
        if (resolvedType != null) {
            req.setType(resolvedType);
            req.setSortField(sortField);
            req.setSortDirection(sortDirection);
            req.setStartIndex(startIndex);
            req.setResultCount(resultCount);

            GetSegmentationListByTypeResponse resp = theGuru.getSegmentationListByType(req);
            if (resp != null) {
                List<com.orangeleap.theguru.client.Segmentation> wsSegmentations = resp.getSegmentation();
                if (wsSegmentations != null) {
                    for (com.orangeleap.theguru.client.Segmentation wsSegmentation : wsSegmentations) {
                        if (wsSegmentation != null) {
                            Segmentation segmentation = new Segmentation(wsSegmentation.getId(), wsSegmentation.getName(),
                                    wsSegmentation.getDescription(), wsSegmentation.getExecutionCount(),
                                    wsSegmentation.getExecutionDate() == null ? null : wsSegmentation.getExecutionDate().toGregorianCalendar().getTime(),
                                    wsSegmentation.getExecutionUser());
                            returnSegmentations.add(segmentation);
                        }
                    }
                }
            }
        }
        return returnSegmentations;
    }


    /**
     * ************* the following will be removed ***************
     */

//    private Map<String, Object> createSearchMap(Map<String, String> map) {
//
//    	Map<String, Object> result = new HashMap<String, Object>();
//        for (Map.Entry<String, String> me : map.entrySet()) {
//
//    		String key = me.getKey();
//            String value = me.getValue();
//            if (value == null || value.trim().length() == 0) continue;
//
//            if (key.equals(POSTED)) {
//            	boolean posted = false;
//            	value = value.toLowerCase();
//            	if (value.equals("true") || value.equals("t") || value.equals("y") || value.equals("yes") || value.equals("1")) {
//            		posted = true;
//            	}
//            	result.put(key, posted);
//            } else if (key.equals(IDS)) {
//            	result.put(key, value.split(","));
//            } else if (key.toLowerCase().contains("date")) {
//                if (value.length() != PostBatchServiceImpl.DATE_FORMAT.length()) throw new RuntimeException("Invalid Date.");
//            	DateFormat formatter = new SimpleDateFormat(PostBatchServiceImpl.DATE_FORMAT);
//            	try {
//            		Date adate = formatter.parse(value);
//            		result.put(key, adate);
//            	} catch (Exception e) {
//            		throw new RuntimeException("Invalid Date.");
//            	}
//            } else if (key.toLowerCase().startsWith("amount")) {
//            	BigDecimal bd = new BigDecimal(value);
//        		result.put(key, bd);
//            } else {
//                result.put(key, value);
//            }
//
//        }
//
//        return result;
//    }

//    private void setField(boolean isGift, AbstractPaymentInfoEntity apie, String key, String value) {
//       if (key.equals(POSTED_DATE)) {
//           DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
//           Date postedDate;
//           try {
//        	   postedDate = dateFormat.parse(value);
//           } catch (Exception e) {
//        	   throw new RuntimeException("Invalid post date: "+value);
//           }
//           if (isGift) {
//               ((Gift)apie).setPostedDate(postedDate);
//           } else {
//               ((AdjustedGift)apie).setPostedDate(postedDate);
//           }
//       } else if (key.equals(SOURCE)) {
//    	   apie.setCustomFieldValue(SOURCE, value);
//       } else {
//    	   if (isGift) {
//    		    setGiftField((Gift)apie, key, value);
//    	   } else {
//   		    	setAdjustedGiftField((AdjustedGift)apie, key, value);
//    	   }
//       }
//    }
//
//    // Evaluates criteria to create list of matching gifts (snapshot at this moment in time).
//    @Override
//    public List<AbstractPaymentInfoEntity> createBatchSelectionList(PostBatch postbatch) {
//
//        boolean isGift = GIFT.equals(postbatch.getBatchType());
//
//        postBatchDao.deletePostBatchItems(postbatch.getId());
//
////        Map<String, Object> searchmap = createSearchMap(postbatch.getWhereConditions());
//
////        if (isGift) {
////            postBatchDao.insertIntoPostBatchFromGiftSelect(postbatch, searchmap);
////        } else {
////            postBatchDao.insertIntoPostBatchFromAdjustedGiftSelect(postbatch, searchmap);
////        }
//
//
////        postbatch.setReviewSetGenerated(true);
////        postbatch.setBatchCreatedDate(new java.util.Date());
////        postbatch.setBatchCreatedById(tangerineUserHelper.lookupUserId());
////        postbatch.setReviewSetSize(postBatchDao.getReviewSetSize(postbatch.getId()));
//        postBatchDao.maintainPostBatch(postbatch);
//
//        // Gift list uses json to display a paginated list.
//        if (isGift) return new ArrayList<AbstractPaymentInfoEntity>();
//
//        return getBatchSelectionList(postbatch);
//    }
//    // Reads previous list of matched gifts. Does not re-evaluate any criteria.
//    @Override
//    public List<AbstractPaymentInfoEntity> getBatchSelectionList(PostBatch postbatch) {
//         List<PostBatchEntry> list = postBatchDao.readPostBatchReviewSetItems(postbatch.getId());
//         List<AbstractPaymentInfoEntity> result = new ArrayList<AbstractPaymentInfoEntity>();
//         boolean isGift = GIFT.equals(postbatch.getBatchType());
//         for (PostBatchEntry item : list) {
//             if (isGift) {
//                 result.add(giftService.readGiftById(item.getSegmentationId()));
//             } else {
//                 result.add(adjustedGiftService.readAdjustedGiftById(item.getSegmentationId()));
//             }
//         }
//         return result;
//    }

//    @Override
//	public PaginatedResult getBatchSelectionList(long postbatchId, SortInfo sortInfo) {
//        return postBatchDao.readPostBatchReviewSetItems(postbatchId, sortInfo);
//	}

    // THis is really execute
    // Sets fields on gifts/adjusted gifts in reviewed batch list

    @Override
    @SuppressWarnings("unchecked")
    public PostBatch executeBatch(PostBatch batch) {
        if (logger.isTraceEnabled()) {
            logger.trace("executeBatch: batchId = " + batch.getId());
        }

        List<? extends AbstractCustomizableEntity> entries = null;
//        Set<Long> segmentationIds = batch.getEntrySegmentationIds();
        if (StringConstants.GIFT.equals(batch.getBatchType())) {
            // TODO: get by gift Ids if segmentation Ids do not exist
            entries = giftService.readAllGiftsBySegmentationReportIds(batch.getEntrySegmentationIds());
        }
        else if (StringConstants.ADJUSTED_GIFT.equals(batch.getBatchType())) {
            entries = adjustedGiftService.readAllAdjustedGiftsBySegmentationReportIds(batch.getEntrySegmentationIds());
        }
        final List<AbstractCustomizableEntity> erroredEntities = new ArrayList<AbstractCustomizableEntity>();
        if (entries != null) {
            for (AbstractCustomizableEntity entity : entries) {
                boolean executed;
                try {
                    executed = postBatchEntryService.executeBatchEntry(batch, entity);
                }
                catch (Exception e) {
                    executed = false;
                    logger.warn("executeBatch: exception occurred during saving of entity", e);
                }
                if ( ! executed) {
                    AbstractCustomizableEntity originalEntity = copyEntityBatchErrorsToCleanEntity(entity);
                    if (originalEntity != null) {
                        erroredEntities.add(originalEntity);
                    }
                }
            }
        }
        if ( ! erroredEntities.isEmpty()) {
            PostBatch errorBatch = createErrorBatch(batch, erroredEntities);
            if (errorBatch != null && errorBatch.getId() != null) {
                batch.setErrorBatchId(errorBatch.getId());
            }
        }

        Long thisUserId = tangerineUserHelper.lookupUserId();
        if (StringUtils.hasText(batch.getUpdateFieldValue(StringConstants.POSTED_DATE)) &&
                postBatchEntryService.isValidPostedDate(batch.getUpdateFieldValue(StringConstants.POSTED_DATE))) {
            batch.setPostedFields(thisUserId);
        }
        batch.setExecutionFields(thisUserId);
        return postBatchDao.maintainPostBatch(batch);
    }

    @Override
    public void checkPreExecuteBatchErrors(PostBatch batch) {
        if (logger.isTraceEnabled()) {
            logger.trace("checkPreExecuteBatchErrors: batchId = " + batch.getId());
        }
        if (batch != null) {
            batch.clearUpdateErrors();

            if (batch.isExecuted()) {
                batch.addUpdateError(TangerineMessageAccessor.getMessage("errorBatchExecuted"));
            }
            else {
                if (StringConstants.GIFT.equals(batch.getBatchType()) || StringConstants.ADJUSTED_GIFT.equals(batch.getBatchType())) {
                    final String postedDateString = batch.getUpdateFieldValue(StringConstants.POSTED_DATE);

                    final Map<String, String> bankCodeMap = new HashMap<String, String>();
                    final Map<String, String> projectCodeMap = new HashMap<String, String>();
                    if (StringUtils.hasText(postedDateString)) {
                        if ( ! postBatchEntryService.isValidPostedDate(postedDateString)) {
                            batch.addUpdateError(TangerineMessageAccessor.getMessage("invalidPostedDate", postedDateString));
                        }
                        postBatchEntryService.createBankProjectCodeMaps(bankCodeMap, projectCodeMap);
                        if (bankCodeMap.isEmpty()) {
                            batch.addUpdateError(TangerineMessageAccessor.getMessage("invalidBankCode"));
                        }
                        if (projectCodeMap.isEmpty()) {
                            batch.addUpdateError(TangerineMessageAccessor.getMessage("invalidDesignationCode"));
                        }
                    }
                }
            }
        }
    }

//    private void createBankProjectCodeMaps(final Map<String, String> bankCodeMap, final Map<String, String> projectCodeMap) {
//        Picklist bankCodes = picklistItemService.getPicklist(StringConstants.BANK_CUSTOM_FIELD);
//        addBankProjectCodesToMap(bankCodes, bankCodeMap);
//
//        Picklist projectCodes = picklistItemService.getPicklist(StringConstants.PROJECT_CODE);
//        addBankProjectCodesToMap(projectCodes, projectCodeMap);
//    }
//
//    private void addBankProjectCodesToMap(Picklist picklist, Map<String, String> map) {
//        if (picklist != null && picklist.getActivePicklistItems() != null) {
//            for (PicklistItem item : picklist.getActivePicklistItems()) {
//                if (item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1) == null ||
//                        item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2) == null ||
//                        item.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE) == null) {
//                    continue;
//                }
//                map.put(getKey(item.getItemName(), StringConstants.ACCOUNT_STRING_1), item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1));
//                map.put(getKey(item.getItemName(), StringConstants.ACCOUNT_STRING_2), item.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2));
//                map.put(getKey(item.getItemName(), StringConstants.GL_ACCOUNT_CODE), item.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE));
//            }
//            if ( ! picklist.getActivePicklistItems().isEmpty()) {
//                PicklistItem defaultItem = picklist.getActivePicklistItems().get(0);
//                map.put(getKey(StringConstants.EMPTY, StringConstants.ACCOUNT_STRING_1), defaultItem.getCustomFieldValue(StringConstants.ACCOUNT_STRING_1));
//                map.put(getKey(StringConstants.EMPTY, StringConstants.ACCOUNT_STRING_2), defaultItem.getCustomFieldValue(StringConstants.ACCOUNT_STRING_2));
//                map.put(getKey(StringConstants.EMPTY, StringConstants.GL_ACCOUNT_CODE), defaultItem.getCustomFieldValue(StringConstants.GL_ACCOUNT_CODE));
//                map.put(DEFAULT, defaultItem.getItemName());
//            }
//        }
//    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {BindException.class})
//    public boolean executeBatchEntry(PostBatch batch, AbstractCustomizableEntity entity) throws BindException {
//        if (logger.isTraceEnabled()) {
//            logger.trace("executeBatchEntry: batchId = " + batch.getId() + " entityType = " + entity.getType() + " entityId = " + entity.getId());
//        }
//        boolean doPost = false;
//        boolean executed = false;
//        final BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
//
//        // Record previous values for audit trail
//        siteService.populateDefaultEntityEditorMaps(entity);
//
//        // for a new execution of an entity, clear out any existing batch errors
//        clearBatchErrorsInEntity(entity);
//
//        if (entity instanceof Postable) { // Gift and AdjustedGift are the only Postable objects
//            final String postedDateString = batch.getUpdateFieldValue(StringConstants.POSTED_DATE);
//
//            if (StringUtils.hasText(postedDateString)) {
//                Date postedDate = null;
//
//                if (isValidPostedDate(postedDateString)) {
//                    postedDate = parsePostedDate(postedDateString);
//                    doPost = true;
//                }
//                else {
//                    addBatchErrorToEntity(entity, "invalidPostedDate", postedDateString);
//                }
//                Boolean isPosted = ((Postable) entity).isPosted();
//                if (postedDate != null && isPosted) {
//                    // Don't allow double posting
//                    addBatchErrorToEntity(entity, "cannotRepost", StringUtils.capitalize(entity.getType()), StringConstants.EMPTY + entity.getId());
//                }
//                else {
//                    ((Postable) entity).setPostedDate(postedDate);
//                    ((Postable) entity).setPosted(true);
//                }
//            }
//        }
//        if ( ! hasBatchErrorsInEntity(entity)) {
//            for (Map.Entry<String, String> updateFldEntry : batch.getUpdateFields().entrySet()) {
//                if ( ! updateFldEntry.getKey().equals(StringConstants.POSTED_DATE)) { // posted date is handled above
//                    // the batchType + updateField key is the FieldDefinitionID; we need to resolve the FieldName from it
//                    String fieldDefinitionId = new StringBuilder(batch.getBatchType()).append(".").append(updateFldEntry.getKey()).toString();
//
//                    final FieldDefinition fieldDef = fieldService.readFieldDefinition(fieldDefinitionId);
//                    if (fieldDef != null && bw.isWritableProperty(fieldDef.getFieldName())) {
//                        String propertyName = fieldDef.getFieldName();
//                        if (propertyName.indexOf(StringConstants.CUSTOM_FIELD_MAP_START) > -1) {
//                            propertyName += StringConstants.DOT_VALUE;
//                        }
//                        Class clazz = bw.getPropertyType(propertyName);
//                        if (String.class.equals(clazz)) {
//                            bw.setPropertyValue(propertyName, updateFldEntry.getValue());
//                        }
//                        else if (Date.class.equals(clazz)) {
//                            try {
//                                Date date = DateUtils.parseDate(updateFldEntry.getValue(), new String[] { StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_1,
//                                        StringConstants.YYYY_MM_DD_HH_MM_SS_FORMAT_2, StringConstants.YYYY_MM_DD_FORMAT,
//                                        StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_1, StringConstants.MM_DD_YYYY_HH_MM_SS_FORMAT_2,
//                                        StringConstants.MM_DD_YYYY_FORMAT});
//                                bw.setPropertyValue(propertyName, date);
//                            }
//                            catch (Exception exception) {
//                                addBatchErrorToEntity(entity, "invalidDateField", updateFldEntry.getValue(), fieldDef.getDefaultLabel());
//                            }
//                        }
//                        else if (Boolean.class.equals(clazz) || Boolean.TYPE.equals(clazz)) {
//                            bw.setPropertyValue(propertyName, Boolean.parseBoolean(updateFldEntry.getValue()));
//                        }
//                        else if (Byte.class.equals(clazz) || Integer.class.equals(clazz) || Short.class.equals(clazz) || Long.class.equals(clazz) ||
//                                Byte.TYPE.equals(clazz) || Integer.TYPE.equals(clazz) || Short.TYPE.equals(clazz) || Long.TYPE.equals(clazz) ||
//                                Double.class.equals(clazz) || Float.class.equals(clazz) || Double.TYPE.equals(clazz) || Float.TYPE.equals(clazz) ||
//                                BigDecimal.class.equals(clazz)) {
//                            if (NumberUtils.isNumber(updateFldEntry.getValue())) {
//                                if (BigDecimal.class.equals(clazz)) {
//                                    bw.setPropertyValue(propertyName, new BigDecimal(updateFldEntry.getValue()));
//                                }
//                                else {
//                                    bw.setPropertyValue(propertyName, NumberUtils.createNumber(updateFldEntry.getValue()));
//                                }
//                            }
//                            else {
//                                addBatchErrorToEntity(entity, "invalidField", updateFldEntry.getValue(), fieldDef.getDefaultLabel());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if ( ! hasBatchErrorsInEntity(entity)) {
//            entity.setSuppressValidation(true);
//
//            if (entity instanceof Gift) {
//                Gift gift = (Gift) entity;
//                gift = giftService.editGift(gift);
//                if (doPost) {
//                    createJournalEntries(gift, null, batch);
//                }
//            }
//            else if (entity instanceof AdjustedGift) {
//                AdjustedGift adjustedGift = (AdjustedGift) entity;
//                adjustedGift = adjustedGiftService.editAdjustedGift(adjustedGift);
//                if (doPost) {
//                    Gift gift = giftService.readGiftById(adjustedGift.getOriginalGiftId());
//                    createJournalEntries(gift, adjustedGift, batch);
//                }
//            }
//            executed = true;
//        }
//        if (hasBatchErrorsInEntity(entity)) {
//            throw new PostBatchUpdateException(getBatchErrorsInEntity(entity));
//        }
//        return executed;
//    }

//    private boolean isValidPostedDate(final String postedDateString) {
//        boolean isValid;
//        try {
//            DateUtils.parseDate(postedDateString, new String[] { StringConstants.MM_DD_YYYY_FORMAT, StringConstants.YYYY_MM_DD_FORMAT });
//            isValid = true;
//        }
//        catch (Exception ex) {
//            // should not get thrown if check for errors run first
//            logger.warn("isValidPostedDate: Could not parse postedDate " + postedDateString);
//            isValid = false;
//        }
//        return isValid;
//    }
//
//    private Date parsePostedDate(final String postedDateString) {
//        Date date = null;
//        try {
//            date = DateUtils.parseDate(postedDateString, new String[] { StringConstants.MM_DD_YYYY_FORMAT, StringConstants.YYYY_MM_DD_FORMAT });
//        }
//        catch (Exception ex) {
//            // should not get thrown if isValidPostedDate() run first
//            logger.warn("parsePostedDate: Could not parse postedDate " + postedDateString);
//        }
//        return date;
//    }

//    private void clearBatchErrorsInEntity(AbstractCustomizableEntity entity) {
//        if (hasBatchErrorsInEntity(entity)) {
//            entity.setCustomFieldValue(StringConstants.BATCH_ERROR, StringConstants.EMPTY);
//        }
//    }

//    private void addBatchErrorToEntity(AbstractCustomizableEntity entity, String errorMsgKey, String... args) {
//        entity.addCustomFieldValue(StringConstants.BATCH_ERROR, TangerineMessageAccessor.getMessage(errorMsgKey, args));
//    }
//
//    private boolean hasBatchErrorsInEntity(AbstractCustomizableEntity entity) {
//        return StringUtils.hasText(getBatchErrorsInEntity(entity));
//    }
//
//    private String getBatchErrorsInEntity(AbstractCustomizableEntity entity) {
//        return entity.getCustomFieldValue(StringConstants.BATCH_ERROR);
//    }

    private AbstractCustomizableEntity copyEntityBatchErrorsToCleanEntity(AbstractCustomizableEntity dirtyEntity) {
        /** Copy the 'batchError' custom field to a 'clean' dirtyEntity; the regular 'dirtyEntity' may have errors and not be savable */
        AbstractCustomizableEntity cleanEntity = null;
        if (dirtyEntity instanceof Gift) {
            cleanEntity = giftService.readGiftById(dirtyEntity.getId());
        }
        else if (dirtyEntity instanceof AdjustedGift) {
            cleanEntity = adjustedGiftService.readAdjustedGiftById(dirtyEntity.getId());
        }
        if (cleanEntity != null) {
            cleanEntity.addCustomFieldValue(StringConstants.BATCH_ERROR, postBatchEntryService.getBatchErrorsInEntity(dirtyEntity));
        }
        return cleanEntity;
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PostBatch createErrorBatch(PostBatch originalBatch, List<AbstractCustomizableEntity> entities) {
        PostBatch batchForErrors = new PostBatch(TangerineMessageAccessor.getMessage("errorBatch"), originalBatch.getBatchType());
        batchForErrors.setHasErrors(true);

        for (AbstractCustomizableEntity entity : entities) {
            // Save the batchError field in the entity
            entity.setSuppressValidation(true);
            try {
                PostBatchEntry batchEntry = new PostBatchEntry();
                if (entity instanceof Gift) {
                    entity = giftService.editGift((Gift) entity);
                    batchEntry.setGiftId(entity.getId());
                }
                else if (entity instanceof AdjustedGift) {
                    entity = adjustedGiftService.editAdjustedGift((AdjustedGift) entity);
                    batchEntry.setAdjustedGiftId(entity.getId());
                }
                batchForErrors.addPostBatchEntry(batchEntry);
            }
            catch (Exception ex) {
                logger.error("createErrorBatch: could not save entityId = " + entity.getId() + " type = " + entity.getType() + " to the batch for errors due to exception", ex);
            }
        }
        return maintainBatch(batchForErrors);
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public PostBatch executeBatchEntry(PostBatch batch) {
//        if (logger.isTraceEnabled()) {
//            logger.trace("executeBatchEntry: batchId = " + batch.getId());
//        }
//        final DateFormat dateFormat = new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT);
//        final String postedDateString = batch.getUpdateFieldValue(StringConstants.POSTED_DATE);
//        final boolean doPost = postedDateString != null;
//
//        Date postedDate = null;
//        final Map<String, String> bankMap = new HashMap<String, String>();
//        final Map<String, String> codeMap = new HashMap<String, String>();
//
//        if (doPost) {
//            try {
//                postedDate = dateFormat.parse(postedDateString);
//            }
//            catch (Exception e) {
//                throw new RuntimeException("Invalid posted date.");
//            }
//            createBankProjectCodeMaps(bankMap, codeMap);
//        }
//        else {
//            // Can't update these fields if not posting.
//            batch.removeUpdateField(StringConstants.POSTED_DATE);
//        }
//
//        // Get list to update
//        final List<Gift> gifts = giftService.readAllGiftsBySegmentationReportIds(batch.getEntrySegmentationIds());
//
//        List<PostBatchEntry> list = postBatchDao.readPostBatchReviewSetItems(batch.getId()); // TODO: grab gifts/adjusted gifts based on segmentations
//        if (list.size() > 2000) {
//            throw new RuntimeException("PostBatch size limit exceeded.");   // TODO remove size limit when supporting partial updates
//        }
//        for (PostBatchEntry entry : list) {
//            try {
//                processUpdateItem(entry, batch, doPost, codeMap, bankMap);
//            }
//            catch (Exception e) {
//                String msg = new StringBuilder(entry.getSegmentationId().toString()).append(": ").append(e.getMessage()).toString();
//                logger.error(msg, e);
//                batch.addUpdateError(msg);
//            }
//        }
//
//        if ( ! batch.getUpdateErrors().isEmpty()) {
//            // rollback entire batch for now.  otherwise we have to support reprocess partial
//            throw new PostBatchUpdateException(batch.getUpdateErrors());
//        }
//
//        if (doPost) {
//            batch.setPosted(true);
//            batch.setPostedDate(postedDate);
//            batch.setPostedById(tangerineUserHelper.lookupUserId());
//        }
//
//        batch.setExecuted(true);
//        batch.setExecutedDate(new java.util.Date());
//        batch.setExecutedById(tangerineUserHelper.lookupUserId());
//
//        // Update
//        batch = postBatchDao.maintainPostBatch(batch);
//        return batch;
//    }
//
//    private void processUpdateItem(PostBatchEntry entry, PostBatch batch, boolean doPost,
//                                   Map<String, String> codeMap, Map<String, String> bankMap) throws Exception {
//        boolean isGift = StringConstants.GIFT.equals(batch.getBatchType());
//        AbstractPaymentInfoEntity entity;
//
//        boolean wasPreviouslyPosted;
//        if (isGift) {
//            entity = giftService.readGiftById(entry.getSegmentationId());
//            wasPreviouslyPosted = ((Gift) entity).isPosted();
//        }
//        else {
//            entity = adjustedGiftService.readAdjustedGiftById(entry.getSegmentationId());
//            wasPreviouslyPosted = ((AdjustedGift) entity).isPosted();
//        }
//
//        // Record previous values for audit trail
//        siteService.populateDefaultEntityEditorMaps(entity);
//
//        if (doPost) {
//            // Don't allow double posting.
//            if (wasPreviouslyPosted) {
//                String msg = "Item " + entity.getId() + " previously posted - cannot re-doPost.";
//                throw new RuntimeException(msg);
//            }
//            ((Postable) entity).setPosted(true);
//        }
//
//        // Set update values.
//        for (Map.Entry<String, String> me : batch.getUpdateFields().entrySet()) {
//            // TODO: use a BeanWrapper here instead
////        	setField(isGift, entity, me.getKey(), me.getValue());
//        }
//
//        // Update record.
//        if (isGift) {
//            Gift gift = (Gift) entity;
//            saveGift(gift);
//            if (doPost) {
//                createJournalEntries(gift, null, batch);
//            }
//        }
//        else {
//            AdjustedGift adjustedGift = (AdjustedGift) entity;
//            saveAdjustedGift(adjustedGift);
//            Gift gift = giftService.readGiftById(adjustedGift.getOriginalGiftId());
//            if (doPost) {
//                createJournalEntries(gift, adjustedGift, batch);
//            }
//        }
//    }
//
//    public final static class PostBatchUpdateException extends RuntimeException {
//        private static final long serialVersionUID = 1L;
//        private Collection<String> errors;
//
//        public PostBatchUpdateException(Collection<String> errors) {
//            super("Errors exist in batch: " + StringUtils.collectionToCommaDelimitedString(errors));
//            this.errors = errors;
//        }
//
//        @SuppressWarnings("unchecked")
//        public PostBatchUpdateException(String errors) {
//            super("Errors exist in batch: " + errors);
//            this.errors = StringUtils.commaDelimitedListToSet(errors);
//        }
//
//        public Collection<String> getErrors() {
//            return errors;
//        }
//    }

//    private void createJournalEntries(Gift gift, AdjustedGift adjustedGift, PostBatch batch) {
//        final Map<String, String> bankCodeMap = new HashMap<String, String>();
//        final Map<String, String> projectCodeMap = new HashMap<String, String>();
//        createBankProjectCodeMaps(bankCodeMap, projectCodeMap);
//
//        createJournalEntry(gift, adjustedGift, null, batch, bankCodeMap, projectCodeMap);
//        List<DistributionLine> distributionLines = adjustedGift == null ? gift.getDistributionLines() : adjustedGift.getDistributionLines();
//        for (DistributionLine line : distributionLines) {
//            createJournalEntry(gift, adjustedGift, line, batch, bankCodeMap, projectCodeMap);
//        }
//    }
//
//    private void createJournalEntry(Gift gift, AdjustedGift adjustedGift, DistributionLine distributionLine,
//                                    PostBatch batch, Map<String, String> bankCodeMap, Map<String, String> projectCodeMap) {
//        boolean isDebit = gift.getAmount().compareTo(BigDecimal.ZERO) >= 0;
//        boolean isHeader = distributionLine == null;
//        boolean isGift = adjustedGift == null;
//
//        Journal journal = new Journal();
//        journal.setSiteName(getSiteName());
//        journal.setPostedDate(new java.util.Date());
//        journal.setPostBatchId(batch.getId());
//
//        journal.setDonationDate(gift.getDonationDate());
//        journal.setPaymentMethod(gift.getPaymentType());
//        if (gift.getPaymentSource() != null) {
//            journal.setCcType(gift.getPaymentSource().getCreditCardType());
//        }
//
//        if (isHeader) {
//            // Gift or Adjusted Gift
//            if (isGift) {
//                journal.setJeType(isDebit ? DEBIT : CREDIT);
//                journal.setEntity(StringConstants.GIFT);
//                journal.setEntityId(gift.getId());
//                journal.setAmount(gift.getAmount());
//                journal.setCode(getBankCode(gift, bankCodeMap));
//                journal.setDescription(TangerineMessageAccessor.getMessage("journalGiftDescription", gift.getConstituent().getRecognitionName()));
//            }
//            else {
//                journal.setJeType(isDebit ? CREDIT : DEBIT);
//                journal.setEntity(ADJUSTED_GIFT);
//                journal.setEntityId(adjustedGift.getId());
//                journal.setOrigEntity(StringConstants.GIFT);
//                journal.setOrigEntityId(gift.getId());
//                journal.setAmount(adjustedGift.getAdjustedAmount());
//                journal.setCode(getBankCode(adjustedGift, bankCodeMap));
//                journal.setAdjustmentDate(adjustedGift.getAdjustedTransactionDate());
//                journal.setDescription(TangerineMessageAccessor.getMessage("journalAdjustedGiftDescription", gift.getId().toString(), gift.getConstituent().getRecognitionName()));
//            }
//            updateJournalCodes(journal, bankCodeMap, journal.getCode(), isGift ? gift : adjustedGift);
//        }
//        else {
//            // Distribution lines
//            journal.setEntity(DISTRO_LINE);
//            journal.setEntityId(distributionLine.getId());
//
//            journal.setMasterEntity(isGift ? StringConstants.GIFT : ADJUSTED_GIFT);
//            journal.setMasterEntityId(isGift ? gift.getId() : adjustedGift.getId());
//
//            journal.setAmount(distributionLine.getAmount());
//            journal.setCode(getProjectCode(distributionLine, projectCodeMap));
//
//            if (isGift) {
//                journal.setJeType(isDebit ? CREDIT : DEBIT);
//                journal.setDescription(TangerineMessageAccessor.getMessage("journalDistroLineGiftDescription", gift.getId().toString(), gift.getConstituent().getRecognitionName()));
//            }
//            else {
//                journal.setJeType(isDebit ? DEBIT : CREDIT);
//                journal.setDescription(TangerineMessageAccessor.getMessage("journalDistroLineAdjustedGiftDescription", adjustedGift.getId().toString(), gift.getId().toString(), gift.getConstituent().getRecognitionName()));
//                journal.setAdjustmentDate(adjustedGift.getAdjustedTransactionDate());
//                journal.setOrigEntity(StringConstants.GIFT);
//                journal.setOrigEntityId(gift.getId());
//            }
//            updateJournalCodes(journal, projectCodeMap, journal.getCode(), isGift ? gift : adjustedGift);
//        }
//        journalDao.maintainJournal(journal);
//    }

//    private String getBankCode(AbstractCustomizableEntity entity, Map<String, String> bankCodeMap) {
//        String defaultBank = bankCodeMap.get(DEFAULT);
//        String bank = entity.getCustomFieldValue(BANK);
//        if (bank == null) {
//            bank = defaultBank;
//        }
//        bank = (bank == null ? StringConstants.EMPTY : bank.trim());
//        if (bank.equalsIgnoreCase(StringConstants.NONE)) {
//            bank = StringConstants.EMPTY;
//        }
//        return bank;
//    }
//
//    private String getProjectCode(DistributionLine distributionLine, Map<String, String> projectCodeMap) {
//        String defaultCode = projectCodeMap.get(DEFAULT);
//        String projectCode = distributionLine.getProjectCode();
//        if (projectCode == null) {
//            projectCode = defaultCode;
//        }
//        projectCode = (projectCode == null ? StringConstants.EMPTY : projectCode.trim());
//        if (projectCode.equalsIgnoreCase(StringConstants.NONE)) {
//            projectCode = StringConstants.EMPTY;
//        }
//        return projectCode;
//    }
//
//    private void updateJournalCodes(Journal journal, Map<String, String> map, String code, AbstractCustomizableEntity entity) {
//        String glAccount1 = map.get(getKey(code, StringConstants.ACCOUNT_STRING_1));
//        if (glAccount1 == null) {
//            addBatchErrorToEntity(entity, "invalidAccountString1", code);
//        }
//        journal.setGlAccount1(glAccount1);
//
//        String glAccount2 = map.get(getKey(code, StringConstants.ACCOUNT_STRING_2));
//        if (glAccount2 == null) {
//            addBatchErrorToEntity(entity, "invalidAccountString2", code);
//        }
//        journal.setGlAccount2(glAccount2);
//
//        String glCode = map.get(getKey(code, StringConstants.GL_ACCOUNT_CODE));
//        if (glCode == null) {
//            addBatchErrorToEntity(entity, "invalidGLCode", code);
//        }
//        journal.setGlCode(glCode);
//    }
//
//    private String getKey(String s1, String s2) {
//        return new StringBuilder(s1).append(" : ").append(s2).toString();
//    }
}