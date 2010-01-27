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

package com.orangeleap.tangerine.test.service.impl;

import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchEntry;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.service.ExecuteBatchService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.PostBatchService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.BatchProvider;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.common.SortInfo;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExecuteBatchServiceImplTest extends BaseTest {

    @Autowired
    private ExecuteBatchService executeBatchService;

	@Autowired
	private PostBatchService postBatchService;

    @Autowired
    private GiftService giftService;

	@Autowired
	private CommunicationHistoryService communicationHistoryService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private PicklistItemService picklistItemService;

    @Autowired
    private SiteService siteService;

	private void checkUneditedGiftValues(Gift gift) throws Exception {
        if (gift.getId() == 5000L) {
            Assert.assertEquals(gift.getAmount().floatValue(), new BigDecimal("35").floatValue());
            Assert.assertTrue(gift.isPosted());
            Assert.assertNull(gift.getDonationDate());
            Assert.assertNull(gift.getCheckNumber());
        }
        else if (gift.getId() == 5001L) {
            Assert.assertEquals(gift.getAmount().floatValue(), new BigDecimal("25").floatValue());
            Assert.assertFalse(gift.isPosted());
            Assert.assertNull(gift.getDonationDate());
            Assert.assertNull(gift.getCheckNumber());
        }
        else if (gift.getId() == 5002L) {
            Assert.assertEquals(gift.getAmount().floatValue(), new BigDecimal("15").floatValue());
            Assert.assertFalse(gift.isPosted());
            Assert.assertNull(gift.getDonationDate());
            Assert.assertNull(gift.getCheckNumber());
        }
        else if (gift.getId() == 5003L) {
            Assert.assertEquals(gift.getAmount().floatValue(), new BigDecimal("5").floatValue());
            Assert.assertTrue(gift.isPosted());
            Assert.assertNull(gift.getDonationDate());
            Assert.assertNull(gift.getCheckNumber());
        }
    }

    private Map<Long, Set<String>> groupByGiftId(List<Map<String, Object>> errors) {
        Map<Long, Set<String>> groupedMap = new HashMap<Long, Set<String>>();
        for (Map<String, Object> thisError : errors) {
            Long giftId = new Long(thisError.get(StringConstants.GIFT_ID).toString());
            Set<String> errorMsgs = groupedMap.get(giftId);
            if (errorMsgs == null) {
                errorMsgs = new HashSet<String>();
                groupedMap.put(giftId, errorMsgs);
            }
            errorMsgs.add(thisError.get("errorMsg").toString());
        }
        return groupedMap;
    }

    @Test(dataProvider = "setupBatchForGiftsWithInvalidPostedDate", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchErrorsA" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch" })
    public void testExecuteBatchForGiftsWithInvalidPostedDate(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
        Assert.assertEquals(new Long(100L), savedBatch.getExecutedById());
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertNull(savedBatch.getPostedById());
        Assert.assertNull(savedBatch.getPostedDate());
        Assert.assertNotNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

        PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
        Assert.assertNotNull(errorBatch);
        Assert.assertTrue(errorBatch.getId() > 0);
        Assert.assertTrue(errorBatch.isAnErrorBatch());
	    Assert.assertFalse(errorBatch.isForTouchPoints());
        Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
        Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 4);
        Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());
        Assert.assertEquals(errorBatch.getUpdateFieldValue(StringConstants.POSTED_DATE), batch.getUpdateFieldValue(StringConstants.POSTED_DATE));

        for (PostBatchEntry entry : errorBatch.getPostBatchEntries()) {
            Assert.assertTrue(entry.getGiftId() == 5000L || entry.getGiftId() == 5001L || entry.getGiftId() == 5002L || entry.getGiftId() == 5003L);
        }

        List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
        Assert.assertNotNull(errors);
        Assert.assertFalse(errors.isEmpty());

        Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
        Assert.assertTrue(groupedMap.keySet().contains(5000L));
        Assert.assertTrue(groupedMap.keySet().contains(5001L));
        Assert.assertTrue(groupedMap.keySet().contains(5002L));
        Assert.assertTrue(groupedMap.keySet().contains(5003L));

        Assert.assertTrue(groupedMap.get(5000L).contains(TangerineMessageAccessor.getMessage("invalidPostedDate", "01/01/blee")));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidPostedDate", "01/01/blee")));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidPostedDate", "01/01/blee")));
        Assert.assertTrue(groupedMap.get(5003L).contains(TangerineMessageAccessor.getMessage("invalidPostedDate", "01/01/blee")));
    }

    @Test(dataProvider = "setupBatchForGiftsWithPostedGifts", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchErrorsB" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsA" })
    public void testExecuteBatchForGiftsWithPostedGifts(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertTrue(savedBatch.isPosted());
        Assert.assertEquals(savedBatch.getPostedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getPostedDate());

        Assert.assertNotNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

        PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
        Assert.assertNotNull(errorBatch);
        Assert.assertTrue(errorBatch.getId() > 0);
        Assert.assertTrue(errorBatch.isAnErrorBatch());
	    Assert.assertFalse(errorBatch.isForTouchPoints());
        Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
        Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 4);
        Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

        for (PostBatchEntry entry : errorBatch.getPostBatchEntries()) {
            Assert.assertTrue(entry.getGiftId() == 5000L || entry.getGiftId() == 5001L || entry.getGiftId() == 5002L || entry.getGiftId() == 5003L);
        }

        List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
        Assert.assertNotNull(errors);
        Assert.assertFalse(errors.isEmpty());

        Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
        Assert.assertTrue(groupedMap.keySet().contains(5000L));
        Assert.assertTrue(groupedMap.keySet().contains(5001L));
        Assert.assertTrue(groupedMap.keySet().contains(5002L));
        Assert.assertTrue(groupedMap.keySet().contains(5003L));

        Assert.assertTrue(groupedMap.get(5000L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "5000")));
        Assert.assertFalse(groupedMap.get(5000L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "jow", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertFalse(groupedMap.get(5000L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "8", fieldService.readFieldDefinition("gift.postmarkDate").getDefaultLabel())));
        Assert.assertFalse(groupedMap.get(5000L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));

        Assert.assertFalse(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "5001")));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "jow", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "8", fieldService.readFieldDefinition("gift.postmarkDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));

        Assert.assertFalse(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "5002")));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "jow", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "8", fieldService.readFieldDefinition("gift.postmarkDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));

        Assert.assertTrue(groupedMap.get(5003L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "5003")));
        Assert.assertFalse(groupedMap.get(5003L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "jow", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertFalse(groupedMap.get(5003L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "8", fieldService.readFieldDefinition("gift.postmarkDate").getDefaultLabel())));
        Assert.assertFalse(groupedMap.get(5003L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));
    }

    @Test(dataProvider = "setupBatchForGiftsWithInvalidAmountsDates", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchErrorsC" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsA" })
    public void testExecuteBatchForGiftsWithInvalidAmountsDates(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertNull(savedBatch.getPostedById());
        Assert.assertNull(savedBatch.getPostedDate());

        Assert.assertNotNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

        PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
        Assert.assertNotNull(errorBatch);
        Assert.assertTrue(errorBatch.getId() > 0);
        Assert.assertTrue(errorBatch.isAnErrorBatch());
	    Assert.assertFalse(errorBatch.isForTouchPoints());
        Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
        Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 2);
        Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

        List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
        Assert.assertNotNull(errors);
        Assert.assertFalse(errors.isEmpty());

        Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
        Assert.assertTrue(groupedMap.keySet().contains(8000L));
        Assert.assertTrue(groupedMap.get(8000L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "0", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(8000L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));

        Assert.assertTrue(groupedMap.keySet().contains(8001L));
        Assert.assertTrue(groupedMap.get(8001L).contains(TangerineMessageAccessor.getMessage("invalidDateField", "0", fieldService.readFieldDefinition("gift.donationDate").getDefaultLabel())));
        Assert.assertTrue(groupedMap.get(8001L).contains(TangerineMessageAccessor.getMessage("invalidField", "xyz", fieldService.readFieldDefinition("gift.amount").getDefaultLabel())));
    }

    /**
     * This test assumes the customFieldMap[bank] and projectCode picklists are not defined in testData.sql
     * @param batch
     * @throws Exception
     */
    @Test(dataProvider = "setupBatchForGiftsWithInvalidJournalGLAccounts", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchErrorsD" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsC" })
    public void testExecuteBatchForGiftsWithInvalidJournalGLAccounts(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertTrue(savedBatch.isPosted());
        Assert.assertEquals(savedBatch.getPostedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getPostedDate());

        Assert.assertNotNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

        PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
        Assert.assertNotNull(errorBatch);
        Assert.assertTrue(errorBatch.getId() > 0);
        Assert.assertTrue(errorBatch.isAnErrorBatch());
	    Assert.assertFalse(errorBatch.isForTouchPoints());
        Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
        Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 2);
        Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

        List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
        Assert.assertNotNull(errors);
        Assert.assertFalse(errors.isEmpty());

        Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
        Assert.assertTrue(groupedMap.keySet().contains(5001L));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidAccountString1", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidAccountString2", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));
        Assert.assertTrue(groupedMap.get(5001L).contains(TangerineMessageAccessor.getMessage("invalidGLCode", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));

        Assert.assertTrue(groupedMap.keySet().contains(5002L));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidAccountString1", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidAccountString2", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));
        Assert.assertTrue(groupedMap.get(5002L).contains(TangerineMessageAccessor.getMessage("invalidGLCode", TangerineMessageAccessor.getMessage("bank"), "Chase").trim()));
    }

    @Test(dataProvider = "setupBatchForGifts", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatch" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsD" })
    public void testExecuteBatchForGifts(PostBatch batch) throws Exception {
        setupBankProjectCodePicklists();
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertTrue(savedBatch.isPosted());
        Assert.assertEquals(savedBatch.getPostedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getPostedDate());

        Assert.assertNotNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getErrorBatchId() > 0);
        PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
        Assert.assertNotNull(errorBatch);
        Assert.assertTrue(errorBatch.getId() > 0);
        Assert.assertTrue(errorBatch.isAnErrorBatch());
	    Assert.assertFalse(errorBatch.isForTouchPoints());
        Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
        Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 2);
        Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

        List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
        Assert.assertNotNull(errors);
        Assert.assertFalse(errors.isEmpty());

        Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
        Assert.assertTrue(groupedMap.keySet().contains(6000L));
        Assert.assertTrue(groupedMap.get(6000L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "6000")));

        Assert.assertTrue(groupedMap.keySet().contains(6003L));
        Assert.assertTrue(groupedMap.get(6003L).contains(TangerineMessageAccessor.getMessage("cannotRepost", "Gift", "6003")));

        /* Check that 2 not-posted gifts got updated (IDs 6001 and 6002) whereas the 2 posted gifts (6000, 6003) did not */
        List<Gift> gifts = giftService.readGiftsByIds(savedBatch.getEntryGiftIds());
        Assert.assertNotNull(gifts);
        for (Gift gift : gifts) {
            if (gift.getId() == 6001L || gift.getId() == 6002L) {
                // These should be updated with the new fields
                Assert.assertEquals(gift.getAmount(), new BigDecimal("150.59"));
                Assert.assertTrue(gift.isPosted());
                Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(gift.getPostedDate()), "01/01/2000");
                Assert.assertEquals(gift.getCustomFieldValue("bank"), "Chase");
                Assert.assertEquals(gift.getCustomFieldValue("source"), "Online");
                Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(gift.getDonationDate()), "12/31/2000");
                Assert.assertEquals(gift.getGiftStatus(), "Not Paid");
                Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(gift.getPostmarkDate()), "06/30/2000");

                if (gift.getId() == 6001L) {
                    Assert.assertEquals(gift.getPaymentType(), "Check");
                }
                else if (gift.getId() == 6002L) {
                    Assert.assertEquals(gift.getPaymentType(), "Cash");
                }
                // TODO: test for journal entries
            }
            else {
                // These should not have been updated
                Assert.assertEquals(gift.getPaymentType(), "Cash");
                Assert.assertTrue(gift.isPosted());
                Assert.assertNotNull(gift.getPostedDate());
                if (gift.getId() == 6000L) {
                    Assert.assertEquals(gift.getAmount().intValue(), new BigDecimal("35").intValue());
                }
                else if (gift.getId() == 6003L) {
                    Assert.assertEquals(gift.getAmount().intValue(), new BigDecimal("5").intValue());
                }
                Assert.assertNull(gift.getCustomFieldValue("bank"));
                Assert.assertNull(gift.getCustomFieldValue("source"));
                Assert.assertNull(gift.getDonationDate());
                Assert.assertNull(gift.getGiftStatus());
                Assert.assertNull(gift.getCheckNumber());
                Assert.assertNull(gift.getPostmarkDate());
            }
        }
    }

    @Test(dataProvider = "setupBatchForGiftsNoPosting", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatch" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsD" })
    public void testExecuteBatchForGiftsNoPosting(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertFalse(savedBatch.isPosted());
        Assert.assertNull(savedBatch.getPostedById());
        Assert.assertNull(savedBatch.getPostedDate());

        Assert.assertNull(savedBatch.getErrorBatchId());
        List<Gift> gifts = giftService.readGiftsByIds(savedBatch.getEntryGiftIds());
        Assert.assertNotNull(gifts);
        for (Gift gift : gifts) {
            if (gift.getId() == 7000L || gift.getId() == 7001L) {
                // These should be updated with the new fields
                Assert.assertEquals(gift.getAmount(), new BigDecimal("29.99"));
                if (gift.getId() == 7000L) {
                    Assert.assertTrue(gift.isPosted());
                    Assert.assertNotNull(gift.getPostedDate());
                }
                else {
                    Assert.assertFalse(gift.isPosted());
                    Assert.assertNull(gift.getPostedDate());
                }
                Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(gift.getDonationDate()), "12/31/2000");
                Assert.assertEquals(gift.getGiftStatus(), "Pending");
                Assert.assertNull(gift.getCheckNumber());
                Assert.assertNull(gift.getPostmarkDate());

                if (gift.getId() == 7000L) {
                    Assert.assertEquals(gift.getPaymentType(), "Cash");
                }
                else if (gift.getId() == 7001L) {
                    Assert.assertEquals(gift.getPaymentType(), "Check");
                }
            }
        }
    }

    @Test(dataProvider = "setupGiftBatchWithGiftIdsNoSegmentationIds", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatch" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchErrorsD" })
    public void testExecuteBatchWithGiftIdsNoSegmentationIds(PostBatch batch) throws Exception {
        batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
        PostBatch savedBatch = executeBatchService.executeBatch(batch);
        Assert.assertNotNull(savedBatch);
        Assert.assertFalse(savedBatch.isAnErrorBatch());
        Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertFalse(savedBatch.isForTouchPoints());
        Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
        Assert.assertNotNull(savedBatch.getExecutedDate());

        Assert.assertFalse(savedBatch.isPosted());
        Assert.assertNull(savedBatch.getPostedById());
        Assert.assertNull(savedBatch.getPostedDate());

        Assert.assertNull(savedBatch.getErrorBatchId());
        Assert.assertTrue(savedBatch.getEntrySegmentationIds().isEmpty());
        Assert.assertFalse(savedBatch.getEntryGiftIds().isEmpty());
        List<Gift> gifts = giftService.readGiftsByIds(savedBatch.getEntryGiftIds());
        Assert.assertNotNull(gifts);
        for (Gift gift : gifts) {
            if (gift.getId() == 9000L || gift.getId() == 9001L) {
                // These should be updated with the new fields
                Assert.assertEquals(gift.getAmount(), new BigDecimal("1.01"));
                if (gift.getId() == 9000L) {
                    Assert.assertTrue(gift.isPosted());
                    Assert.assertNotNull(gift.getPostedDate());
                }
                else {
                    Assert.assertFalse(gift.isPosted());
                    Assert.assertNull(gift.getPostedDate());
                }
                Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(gift.getDonationDate()), "03/30/2009");
                Assert.assertEquals(gift.getGiftStatus(), "Not Paid");

                if (gift.getId() == 9000L) {
                    Assert.assertEquals(gift.getPaymentType(), "Cash");
                }
                else if (gift.getId() == 9001L) {
                    Assert.assertEquals(gift.getPaymentType(), "Check");
                }
            }
        }
    }

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentInvalidDateNumber", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPointErrorsA" })//, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentInvalidDateNumber(PostBatch batch) throws Exception {
	    batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
	    PostBatch savedBatch = executeBatchService.executeBatch(batch);
	    Assert.assertNotNull(savedBatch);
	    Assert.assertFalse(savedBatch.isAnErrorBatch());
	    Assert.assertTrue(savedBatch.isExecuted());
	    Assert.assertEquals(new Long(100L), savedBatch.getExecutedById());
	    Assert.assertNotNull(savedBatch.getExecutedDate());

	    Assert.assertFalse(savedBatch.isPosted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
	    Assert.assertNull(savedBatch.getPostedById());
	    Assert.assertNull(savedBatch.getPostedDate());
	    Assert.assertNotNull(savedBatch.getErrorBatchId());
	    Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

	    PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
	    Assert.assertNotNull(errorBatch);
	    Assert.assertTrue(errorBatch.getId() > 0);
	    Assert.assertTrue(errorBatch.isAnErrorBatch());
		Assert.assertTrue(errorBatch.isForTouchPoints());
	    Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
	    Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 4);
	    Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());
	    Assert.assertEquals(errorBatch.getUpdateFieldValue("entryType"), batch.getUpdateFieldValue("entryType"));

	    for (PostBatchEntry entry : errorBatch.getPostBatchEntries()) {
	        Assert.assertTrue(entry.getGiftId() == 5000L || entry.getGiftId() == 5001L || entry.getGiftId() == 5002L || entry.getGiftId() == 5003L);
	    }

	    List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
	    Assert.assertNotNull(errors);
	    Assert.assertFalse(errors.isEmpty());

	    Map<Long, Set<String>> groupedMap = groupByGiftId(errors);

		for (int x = 5000; x < 5004; x++) {
			Assert.assertTrue(groupedMap.keySet().contains( (long) x));
		    Assert.assertTrue(groupedMap.get( (long) x).contains(TangerineMessageAccessor.getMessage("invalidDateField", "01/01/blee", "Date")));
			Assert.assertTrue(groupedMap.get( (long) x).contains(TangerineMessageAccessor.getMessage("invalidField", "blarg", "Rank")));
		}
	}

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentNoAddresses", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPointErrorsC" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsA" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentNoAddresses(PostBatch batch) throws Exception {
		batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
		PostBatch savedBatch = executeBatchService.executeBatch(batch);
		Assert.assertNotNull(savedBatch);
		Assert.assertFalse(savedBatch.isAnErrorBatch());
		Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
		Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
		Assert.assertNotNull(savedBatch.getExecutedDate());

		Assert.assertNotNull(savedBatch.getErrorBatchId());
		Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

		PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
		Assert.assertNotNull(errorBatch);
		Assert.assertTrue(errorBatch.getId() > 0);
		Assert.assertTrue(errorBatch.isAnErrorBatch());
		Assert.assertTrue(errorBatch.isForTouchPoints());
		Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
		Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 4);
		Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

		List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
		Assert.assertNotNull(errors);
		Assert.assertFalse(errors.isEmpty());

		Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
		for (int x = 5000; x < 5004; x++) {
			Assert.assertTrue(groupedMap.keySet().contains( (long) x));
			Assert.assertTrue(groupedMap.get( (long) x).contains(TangerineMessageAccessor.getMessage("invalidCommunication", TangerineMessageAccessor.getMessage("address"), "Mr. Howdy Doody").trim()));
		}
	}

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentNoPrimaryPhone", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPointErrorsD" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsC" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentNoPrimaryPhone(PostBatch batch) throws Exception {
		batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
		PostBatch savedBatch = executeBatchService.executeBatch(batch);
		Assert.assertNotNull(savedBatch);
		Assert.assertFalse(savedBatch.isAnErrorBatch());
		Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
		Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
		Assert.assertNotNull(savedBatch.getExecutedDate());

		Assert.assertNotNull(savedBatch.getErrorBatchId());
		Assert.assertTrue(savedBatch.getErrorBatchId() > 0);

		PostBatch errorBatch = postBatchService.readBatch(savedBatch.getErrorBatchId());
		Assert.assertNotNull(errorBatch);
		Assert.assertTrue(errorBatch.getId() > 0);
		Assert.assertTrue(errorBatch.isAnErrorBatch());
		Assert.assertTrue(errorBatch.isForTouchPoints());
		Assert.assertFalse(errorBatch.getPostBatchEntries().isEmpty());
		Assert.assertEquals(errorBatch.getPostBatchEntries().size(), 4);
		Assert.assertFalse(errorBatch.getUpdateFields().isEmpty());

		List<Map<String, Object>> errors = postBatchService.readPostBatchEntryErrorsByBatchId(errorBatch.getId(), new SortInfo(StringConstants.GIFT_ID, StringConstants.ASC, 100, 0));
		Assert.assertNotNull(errors);
		Assert.assertFalse(errors.isEmpty());

		Map<Long, Set<String>> groupedMap = groupByGiftId(errors);
		for (int x = 5000; x < 5004; x++) {
			Assert.assertTrue(groupedMap.keySet().contains( (long) x));
			Assert.assertTrue(groupedMap.get( (long) x).contains(TangerineMessageAccessor.getMessage("invalidPrimary", TangerineMessageAccessor.getMessage("phone"), "Mr. Howdy Doody").trim()));
		}
	}

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentAllEmails", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPoint" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsD" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentAllEmails(PostBatch batch) throws Exception {
	    batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
	    PostBatch savedBatch = executeBatchService.executeBatch(batch);
	    Assert.assertNotNull(savedBatch);
	    Assert.assertFalse(savedBatch.isAnErrorBatch());
	    Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
	    Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
	    Assert.assertNotNull(savedBatch.getExecutedDate());

	    Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertNull(savedBatch.getPostedById());
	    Assert.assertNull(savedBatch.getPostedDate());

	    Assert.assertNull(savedBatch.getErrorBatchId());
	    List<CommunicationHistory> touchPoints = communicationHistoryService.readCommunicationHistoryByBatchId(savedBatch.getId(), new SortInfo("createDate", "desc", 100, 0), Locale.getDefault());
	    Assert.assertNotNull(touchPoints);
		Assert.assertFalse(touchPoints.isEmpty());
	    for (CommunicationHistory touchPoint : touchPoints) {
		    Assert.assertEquals(touchPoint.getBatchId(), savedBatch.getId());
		    Assert.assertEquals(touchPoint.getEntryType(), StringConstants.EMAIL_CAMEL_CASE);
		    Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(touchPoint.getRecordDate()), "01/02/2003");
		    Assert.assertEquals(touchPoint.getComments(), "hi mom");
		    Assert.assertEquals(touchPoint.getCustomFieldValue("template"), "blarg");
		    Assert.assertEquals(touchPoint.getCustomFieldValue("assignedTo"), "100");
		    Assert.assertEquals(touchPoint.getConstituentId(), new Long(300L));
		    Assert.assertTrue(touchPoint.getGiftId() >= 6000L && touchPoint.getGiftId() <= 6003L);
		    Assert.assertTrue(touchPoint.getEmail().getId().equals(new Long(400L)) || touchPoint.getEmail().getId().equals(new Long(500L)));
	    }
	}

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentPrimaryEmail", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPoint" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsD" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentPrimaryEmail(PostBatch batch) throws Exception {
	    batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
	    PostBatch savedBatch = executeBatchService.executeBatch(batch);
	    Assert.assertNotNull(savedBatch);
	    Assert.assertFalse(savedBatch.isAnErrorBatch());
	    Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
	    Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
	    Assert.assertNotNull(savedBatch.getExecutedDate());

	    Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertNull(savedBatch.getPostedById());
	    Assert.assertNull(savedBatch.getPostedDate());

	    Assert.assertNull(savedBatch.getErrorBatchId());
	    List<CommunicationHistory> touchPoints = communicationHistoryService.readCommunicationHistoryByBatchId(savedBatch.getId(), new SortInfo("createDate", "desc", 100, 0), Locale.getDefault());
	    Assert.assertNotNull(touchPoints);
		Assert.assertFalse(touchPoints.isEmpty());
	    for (CommunicationHistory touchPoint : touchPoints) {
		    Assert.assertEquals(touchPoint.getBatchId(), savedBatch.getId());
		    Assert.assertEquals(touchPoint.getEntryType(), StringConstants.EMAIL_CAMEL_CASE);
		    Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(touchPoint.getRecordDate()), "02/03/2004");
		    Assert.assertEquals(touchPoint.getComments(), "hi dad");
		    Assert.assertEquals(touchPoint.getCustomFieldValue("template"), "wow");
		    Assert.assertEquals(touchPoint.getCustomFieldValue("assignedTo"), "100");
		    Assert.assertEquals(touchPoint.getConstituentId(), new Long(300L));
		    Assert.assertTrue(touchPoint.getGiftId().equals(7000L) || touchPoint.getGiftId().equals(7001L));
		    Assert.assertEquals(touchPoint.getEmail().getId(), new Long(500L));
	    }
	}

	@Test(dataProvider = "setupBatchForTouchPointGiftsWithConstituentNote", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPoint" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsD" })
	public void testExecuteBatchForTouchPointGiftsWithConstituentNote(PostBatch batch) throws Exception {
	    batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
	    PostBatch savedBatch = executeBatchService.executeBatch(batch);
	    Assert.assertNotNull(savedBatch);
	    Assert.assertFalse(savedBatch.isAnErrorBatch());
	    Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
	    Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
	    Assert.assertNotNull(savedBatch.getExecutedDate());

	    Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertNull(savedBatch.getPostedById());
	    Assert.assertNull(savedBatch.getPostedDate());

	    Assert.assertNull(savedBatch.getErrorBatchId());
	    List<CommunicationHistory> touchPoints = communicationHistoryService.readCommunicationHistoryByBatchId(savedBatch.getId(), new SortInfo("createDate", "desc", 100, 0), Locale.getDefault());
	    Assert.assertNotNull(touchPoints);
		Assert.assertFalse(touchPoints.isEmpty());
	    for (CommunicationHistory touchPoint : touchPoints) {
		    Assert.assertEquals(touchPoint.getBatchId(), savedBatch.getId());
		    Assert.assertEquals(touchPoint.getEntryType(), "Note");
		    Assert.assertEquals(new SimpleDateFormat(StringConstants.MM_DD_YYYY_FORMAT).format(touchPoint.getRecordDate()), "03/04/2005");
		    Assert.assertEquals(touchPoint.getComments(), "hi kid");
		    Assert.assertEquals(touchPoint.getCustomFieldValue("noteType"), "whiteboard");
		    Assert.assertNull(touchPoint.getCustomFieldValue("assignedTo"));
		    Assert.assertEquals(touchPoint.getConstituentId(), new Long(300L));
		    Assert.assertTrue(touchPoint.getGiftId().equals(8000L) || touchPoint.getGiftId().equals(8001L));
		    Assert.assertNull(touchPoint.getEmail());
		    Assert.assertNull(touchPoint.getAddress());
		    Assert.assertNull(touchPoint.getPhone());
	    }
	}

	@Test(dataProvider = "setupGiftBatchWithTouchPointGiftIdsNoSegmentationIds", dataProviderClass = BatchProvider.class, groups = { "testExecuteBatchForTouchPoint" }, dependsOnGroups = { "testMaintainConstituent", "testMaintainPostBatch", "testExecuteBatchForTouchPointErrorsD" })
	public void testExecuteBatchWithTouchPointGiftIdsNoSegmentationIds(PostBatch batch) throws Exception {
	    batch = postBatchService.maintainBatch(batch); // need to save the batch first to get an ID
	    PostBatch savedBatch = executeBatchService.executeBatch(batch);
	    Assert.assertNotNull(savedBatch);
	    Assert.assertFalse(savedBatch.isAnErrorBatch());
	    Assert.assertTrue(savedBatch.isExecuted());
		Assert.assertTrue(savedBatch.isForTouchPoints());
	    Assert.assertEquals(savedBatch.getExecutedById(), new Long(100L));
	    Assert.assertNotNull(savedBatch.getExecutedDate());

	    Assert.assertFalse(savedBatch.isPosted());
	    Assert.assertNull(savedBatch.getPostedById());
	    Assert.assertNull(savedBatch.getPostedDate());

	    Assert.assertNull(savedBatch.getErrorBatchId());
	    Assert.assertTrue(savedBatch.getEntrySegmentationIds().isEmpty());
	    Assert.assertFalse(savedBatch.getEntryGiftIds().isEmpty());
		List<CommunicationHistory> touchPoints = communicationHistoryService.readCommunicationHistoryByBatchId(savedBatch.getId(), new SortInfo("createDate", "desc", 100, 0), Locale.getDefault());
		Assert.assertNotNull(touchPoints);
		Assert.assertFalse(touchPoints.isEmpty());
		for (CommunicationHistory touchPoint : touchPoints) {
			Assert.assertEquals(touchPoint.getBatchId(), savedBatch.getId());
			Assert.assertEquals(touchPoint.getEntryType(), "Event");
			Assert.assertEquals(touchPoint.getCustomFieldValue("event"), "Juicer");
			Assert.assertEquals(touchPoint.getCustomFieldValue("eventParticipation"), "Attendee");
			Assert.assertEquals(touchPoint.getConstituentId(), new Long(300L));
			Assert.assertTrue(touchPoint.getGiftId().equals(9000L) || touchPoint.getGiftId().equals(9001L));
			Assert.assertNull(touchPoint.getEmail());
			Assert.assertNull(touchPoint.getAddress());
			Assert.assertNull(touchPoint.getPhone());
		}
	}

    private void setupBankProjectCodePicklists() {
        /* This assumes the customFieldMap[bank] and projectCode picklists are not defined in testData.sql */
        Site site = siteService.readSite("company1");
        Picklist bankPicklist = new Picklist();
        List<PicklistItem> items = new ArrayList<PicklistItem>();
        bankPicklist.setPicklistNameId(StringConstants.BANK_CUSTOM_FIELD);
        bankPicklist.setPicklistName(StringConstants.BANK_CUSTOM_FIELD);
        bankPicklist.setPicklistDesc("Bank");
        bankPicklist.setSite(site);
        PicklistItem bankItem = new PicklistItem();
        bankItem.setItemName("Chase");
        bankItem.setDefaultDisplayValue("Chase");
        bankItem.setItemOrder(0);
        bankItem.addCustomFieldValue(StringConstants.ACCOUNT_STRING_1, "12345");
        bankItem.addCustomFieldValue(StringConstants.ACCOUNT_STRING_2, "987");
        bankItem.addCustomFieldValue(StringConstants.GL_ACCOUNT_CODE, "1000000");
        items.add(bankItem);
        bankPicklist.setPicklistItems(items);
        picklistItemService.maintainPicklist(bankPicklist);

        Picklist projectPicklist = new Picklist();
        items = new ArrayList<PicklistItem>();
        projectPicklist.setPicklistNameId(StringConstants.PROJECT_CODE);
        projectPicklist.setPicklistName(StringConstants.PROJECT_CODE);
        projectPicklist.setPicklistDesc("Designation Code");
        projectPicklist.setSite(site);
        PicklistItem projectItem = new PicklistItem();
        projectItem.setItemName("01000");
        projectItem.setDefaultDisplayValue("01000");
        projectItem.setItemOrder(0);
        projectItem.addCustomFieldValue(StringConstants.ACCOUNT_STRING_1, "54321");
        projectItem.addCustomFieldValue(StringConstants.ACCOUNT_STRING_2, "789");
        projectItem.addCustomFieldValue(StringConstants.GL_ACCOUNT_CODE, "0000001");
        items.add(projectItem);
        projectPicklist.setPicklistItems(items);
        picklistItemService.maintainPicklist(projectPicklist);
    }
}
