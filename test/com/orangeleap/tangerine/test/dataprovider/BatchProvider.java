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

package com.orangeleap.tangerine.test.dataprovider;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchEntry;
import com.orangeleap.tangerine.util.StringConstants;
import org.testng.annotations.DataProvider;

public class BatchProvider {

    @DataProvider(name = "setupBatchForGiftsWithInvalidPostedDate")
    public static Object[][] createBatchForGiftsWithInvalidPostedDate() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(5000L));
        batch.addUpdateField(StringConstants.POSTED_DATE, "01/01/blee");
        batch.addUpdateField("amount", "xyz");
        batch.addUpdateField("posted", "true");
        batch.addUpdateField(StringConstants.BANK_CUSTOM_FIELD, "Chase");
        batch.addUpdateField("customFieldMap[source]", "Online");
        batch.addUpdateField("donationDate", "jow");
        batch.addUpdateField("giftStatus", "Not Paid");
        batch.addUpdateField("postmarkDate", "8");
        batch.addUpdateField("checkNumber", "foo");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupBatchForGiftsWithPostedGifts")
    public static Object[][] createBatchForGiftsWithPostedGifts() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(5000L));
        batch.addUpdateField(StringConstants.POSTED_DATE, "01/01/2000");
        batch.addUpdateField("amount", "xyz");
        batch.addUpdateField("posted", "true");
        batch.addUpdateField(StringConstants.BANK_CUSTOM_FIELD, "Chase");
        batch.addUpdateField("customFieldMap[source]", "Online");
        batch.addUpdateField("donationDate", "jow");
        batch.addUpdateField("giftStatus", "Not Paid");
        batch.addUpdateField("postmarkDate", "8");
        batch.addUpdateField("checkNumber", "foo");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupBatchForGiftsWithInvalidJournalGLAccounts")
    public static Object[][] createBatchForGiftsWithInvalidJournalGLAccounts() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(10000L));
        batch.addUpdateField(StringConstants.POSTED_DATE, "01/01/2000");
        batch.addUpdateField("amount", "99.99");
        batch.addUpdateField("posted", "true");
        batch.addUpdateField(StringConstants.BANK_CUSTOM_FIELD, "Chase");
        batch.addUpdateField("customFieldMap[source]", "Online");
        batch.addUpdateField("donationDate", "12/20/2000");
        batch.addUpdateField("giftStatus", "Not Paid");
        batch.addUpdateField("postmarkDate", "06/30/2000");
        batch.addUpdateField("checkNumber", "19");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupBatchForGifts")
    public static Object[][] createBatchForGifts() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(6000L));
        batch.addUpdateField("amount", "150.59");
        batch.addUpdateField(StringConstants.POSTED_DATE, "01/01/2000");
        batch.addUpdateField("posted", "true");
        batch.addUpdateField(StringConstants.BANK_CUSTOM_FIELD, "Chase");
        batch.addUpdateField("customFieldMap[source]", "Online");
        batch.addUpdateField("donationDate", "12/31/2000");
        batch.addUpdateField("giftStatus", "Not Paid");
        batch.addUpdateField("postmarkDate", "06/30/2000");
        batch.addUpdateField("checkNumber", "15");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupBatchForGiftsNoPosting")
    public static Object[][] createBatchForGiftsNoPosting() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(7000L));
        batch.addUpdateField("amount", "29.99");
        batch.addUpdateField("donationDate", "12/31/2000");
        batch.addUpdateField("giftStatus", "Pending");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupBatchForGiftsWithInvalidAmountsDates")
    public static Object[][] createBatchForGiftsWithInvalidAmountsDates() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);
        batch.addPostBatchEntry(new PostBatchEntry(8000L));
        batch.addUpdateField("amount", "xyz");
        batch.addUpdateField("donationDate", "0");
        batch.addUpdateField("giftStatus", "Pending");

        return new Object[][] { new Object[] { batch } };
    }

    @DataProvider(name = "setupGiftBatchWithGiftIdsNoSegmentationIds")
    public static Object[][] createBatchWithGiftIdsNoSegmentationIds() {
        PostBatch batch = new PostBatch("This batch description for gifts", StringConstants.GIFT);

        PostBatchEntry entry = new PostBatchEntry();
        entry.setGiftId(9000L);
        batch.addPostBatchEntry(entry);

        entry = new PostBatchEntry();
        entry.setGiftId(9001L);
        batch.addPostBatchEntry(entry);

        batch.addUpdateField("amount", "1.01");
        batch.addUpdateField("donationDate", "03/30/2009");
        batch.addUpdateField("giftStatus", "Not Paid");

        return new Object[][] { new Object[] { batch } };
    }
}
