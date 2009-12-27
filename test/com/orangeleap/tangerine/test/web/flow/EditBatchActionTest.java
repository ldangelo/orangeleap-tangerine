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

package com.orangeleap.tangerine.test.web.flow;

import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.web.flow.batch.EditBatchAction;
import org.junit.Assert;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.test.MockRequestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class EditBatchActionTest extends BaseTest {

    private RequestContext mockRequestContext;
    private EditBatchAction action;
    private PostBatch batch;

    @BeforeMethod
    public void setupMocks() {
        mockRequestContext = new MockRequestContext();
        action = new EditBatchAction();
        batch = new PostBatch();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSyncPickedSegmentationIds1() throws Exception {
        /* Test a new batch without any picked IDs set */
        invokeSyncPickedSegmentationIds(batch, "1,2,3,83", "3,4,5,83,6");
        Assert.assertEquals("1,2,3,83", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Remove 2,3 and add 5,7,9 */
        invokeSyncPickedSegmentationIds(batch, "5,7,9", "99,2,3");
        Assert.assertEquals("1,5,7,9,83", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Add 8 and don't remove anything */
        invokeSyncPickedSegmentationIds(batch, "8", "");
        Assert.assertEquals("1,5,7,8,9,83", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Remove 1,5,7,8,9 and don't add anything */
        invokeSyncPickedSegmentationIds(batch, "", "7,9,8,5,1");
        Assert.assertEquals("83", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Remove 83 and don't add anything */
        invokeSyncPickedSegmentationIds(batch, "", "83");
        Assert.assertEquals("", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSyncPickedSegmentationIds2() throws Exception {
        Set<Long> previouslyPickedSegmentationIds = new HashSet<Long>();
        previouslyPickedSegmentationIds.add(100L);
        previouslyPickedSegmentationIds.add(200L);
        batch.clearAddAllPostBatchEntriesForSegmentations(previouslyPickedSegmentationIds);

        /* Test a batch with picked IDs set */
        invokeSyncPickedSegmentationIds(batch, "", "");
        Assert.assertEquals("100,200", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Test add 1 but not remove anything */
        invokeSyncPickedSegmentationIds(batch, "1", "");
        Assert.assertEquals("1,100,200", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Test removing 1,100,200 */
        invokeSyncPickedSegmentationIds(batch, "", "100,200,1");
        Assert.assertEquals("", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSyncPickedSegmentationIds3() throws Exception {
        Set<Long> previouslyPickedSegmentationIds = new HashSet<Long>();
        previouslyPickedSegmentationIds.add(100L);
        batch.clearAddAllPostBatchEntriesForSegmentations(previouslyPickedSegmentationIds);

        /* Test remove 100 and add 200 */
        invokeSyncPickedSegmentationIds(batch, "200", "100");
        Assert.assertEquals("200", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Test don't add or delete anything */
        invokeSyncPickedSegmentationIds(batch, "", "");
        Assert.assertEquals("200", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));

        /* Test adding 300 */
        invokeSyncPickedSegmentationIds(batch, "300", "");
        Assert.assertEquals("200,300", StringUtils.collectionToCommaDelimitedString(batch.getEntrySegmentationIds()));
    }

    private Object invokeSyncPickedSegmentationIds(PostBatch batch, String pickedIdsStr, String notPickedIdsStr) throws Exception {
        Method method = EditBatchAction.class.getDeclaredMethod("syncPickedSegmentationIds", RequestContext.class, PostBatch.class, String.class, String.class);
        method.setAccessible(true);
        return method.invoke(action, mockRequestContext, batch, pickedIdsStr, notPickedIdsStr);
    }
}
