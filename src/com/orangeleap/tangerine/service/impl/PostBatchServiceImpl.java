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

    @Override
    @SuppressWarnings("unchecked")
    public PostBatch executeBatch(PostBatch batch) {
        if (logger.isTraceEnabled()) {
            logger.trace("executeBatch: batchId = " + batch.getId());
        }

        List<? extends AbstractCustomizableEntity> entries = null;
        Set<Long> segmentationIds = batch.getEntrySegmentationIds();
        if (StringConstants.GIFT.equals(batch.getBatchType())) {
            // Get by giftIds if segmentationIds do not exist
            if ( ! segmentationIds.isEmpty()) {
                entries = giftService.readAllGiftsBySegmentationReportIds(batch.getEntrySegmentationIds());
            }
            else {
                entries = giftService.readGiftsByIds(batch.getEntryGiftIds());
            }
        }
        else if (StringConstants.ADJUSTED_GIFT.equals(batch.getBatchType())) {
            // Get by giftIds if segmentationIds do not exist
            if ( ! segmentationIds.isEmpty()) {
                entries = adjustedGiftService.readAllAdjustedGiftsBySegmentationReportIds(batch.getEntrySegmentationIds());
            }
            else {
                entries = adjustedGiftService.readAdjustedGiftsByIds(batch.getEntryAdjustedGiftIds());
            }
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
        batch.setHasErrors(false);  // if this was an error batch, just set to an executed batch instead
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
}