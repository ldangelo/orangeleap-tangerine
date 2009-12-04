package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.PostBatchDao;
import com.orangeleap.tangerine.domain.PostBatch;
import com.orangeleap.tangerine.domain.PostBatchSegmentation;
import com.orangeleap.tangerine.util.OLLogger;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

public class IBatisPostBatchDaoTest extends AbstractIBatisTest {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private PostBatchDao postBatchDao;

    @BeforeMethod
    public void setup() {
        postBatchDao = (PostBatchDao)super.applicationContext.getBean("postBatchDAO");
    }

    @Test(groups = { "testMaintainPostBatch" }, dependsOnGroups = { "testReadPostBatch" })
    public void testMaintainGift() throws Exception {
        // Insert
        PostBatch batch = new PostBatch();
        batch.setBatchCreatedById(100L);
        batch.setBatchCreatedDate(new Date());
        batch.setBatchType("gift");
        batch.setBatchDesc("This is my gift batch");
        batch.setSiteName("company1");

        PostBatchSegmentation segmentation = new PostBatchSegmentation();
        segmentation.setSegmentationId(550L);
        batch.addPostBatchSegmentation(segmentation);

        segmentation = new PostBatchSegmentation();
        segmentation.setSegmentationId(650L);
        batch.addPostBatchSegmentation(segmentation);

        batch.addUpdateField("postedDate", "12/10/2010 00:00:00");
        batch.addUpdateField("giftStatus", "Not Paid");
        batch.addUpdateField("customFieldMap[source]", "Mail");

        batch = postBatchDao.maintainPostBatch(batch);
        Assert.assertTrue(batch.getId() > 0);

        PostBatch readBatch = postBatchDao.readPostBatchById(batch.getId());
        Assert.assertNotNull(readBatch);
        Assert.assertEquals(batch.getId(), readBatch.getId());
        Assert.assertEquals("This is my gift batch", readBatch.getBatchDesc());
        Assert.assertEquals("gift", readBatch.getBatchType());
        Assert.assertEquals(new Long(100L), readBatch.getBatchCreatedById());
        Assert.assertNotNull(readBatch.getBatchCreatedDate());
        Assert.assertEquals("company1", readBatch.getSiteName());
        Assert.assertFalse(readBatch.isExecuted());
        Assert.assertNull(readBatch.getExecutedById());
        Assert.assertNull(readBatch.getExecutedDate());
        Assert.assertFalse(readBatch.isPosted());
        Assert.assertNull(readBatch.getPostedById());
        Assert.assertNull(readBatch.getPostedDate());
        Assert.assertNotNull(readBatch.getCreateDate());
        Assert.assertNotNull(readBatch.getUpdateDate());
        Assert.assertNotNull(readBatch.getPostBatchSegmentations());
        Assert.assertEquals(2, readBatch.getPostBatchSegmentations().size());
        for (PostBatchSegmentation thisSeg : readBatch.getPostBatchSegmentations()) {
            Assert.assertEquals(batch.getId(), thisSeg.getPostBatchId());
            Assert.assertTrue(thisSeg.getSegmentationId() == 550L || thisSeg.getSegmentationId() == 650L);
        }

        Assert.assertNotNull(readBatch.getUpdateFields());
        Assert.assertEquals(3, readBatch.getUpdateFields().size());
        Assert.assertEquals("12/10/2010 00:00:00", readBatch.getUpdateFieldValue("postedDate"));
        Assert.assertEquals("Not Paid", readBatch.getUpdateFieldValue("giftStatus"));
        Assert.assertEquals("Mail", readBatch.getUpdateFieldValue("customFieldMap[source]"));
    }

    @Test(groups = { "testReadPostBatch" })
    public void testReadPostBatchById() throws Exception {
        PostBatch batch = postBatchDao.readPostBatchById(0L);
        Assert.assertNull(batch);

        batch = postBatchDao.readPostBatchById(1L);
        Assert.assertNotNull(batch);
        Assert.assertEquals(new Long(1L), batch.getId());
        Assert.assertEquals("Batch 1", batch.getBatchDesc());
        Assert.assertEquals("gift", batch.getBatchType());
        Assert.assertTrue(batch.isExecuted());
        Assert.assertEquals(new Long(100L), batch.getExecutedById());
        Assert.assertNotNull(batch.getExecutedDate());
        Assert.assertTrue(batch.isPosted());
        Assert.assertEquals(new Long(100L), batch.getPostedById());
        Assert.assertNotNull(batch.getPostedDate());
        Assert.assertEquals(new Long(100L), batch.getBatchCreatedById());
        Assert.assertNotNull(batch.getBatchCreatedDate());
        Assert.assertEquals("company1", batch.getSiteName());
        Assert.assertNotNull(batch.getCreateDate());
        Assert.assertNotNull(batch.getUpdateDate());
        Assert.assertNotNull(batch.getPostBatchSegmentations());
        Assert.assertEquals(3, batch.getPostBatchSegmentations().size());
        for (PostBatchSegmentation segmentation : batch.getPostBatchSegmentations()) {
            Assert.assertEquals(new Long(1L), segmentation.getPostBatchId());
            Assert.assertTrue(segmentation.getSegmentationId() == 100L || segmentation.getSegmentationId() == 200L || segmentation.getSegmentationId() == 300L);
        }

        batch = postBatchDao.readPostBatchById(2L);
        Assert.assertNotNull(batch);
        Assert.assertEquals(new Long(2L), batch.getId());
        Assert.assertEquals("Batch 2", batch.getBatchDesc());
        Assert.assertEquals("gift", batch.getBatchType());
        Assert.assertFalse(batch.isExecuted());
        Assert.assertNull(batch.getExecutedById());
        Assert.assertNull(batch.getExecutedDate());
        Assert.assertEquals(new Long(100L), batch.getBatchCreatedById());
        Assert.assertNotNull(batch.getBatchCreatedDate());
        Assert.assertFalse(batch.isPosted());
        Assert.assertNull(batch.getPostedById());
        Assert.assertNull(batch.getPostedDate());
        Assert.assertEquals("company1", batch.getSiteName());
        Assert.assertNotNull(batch.getCreateDate());
        Assert.assertNotNull(batch.getUpdateDate());
        Assert.assertNotNull(batch.getPostBatchSegmentations());
        Assert.assertEquals(1, batch.getPostBatchSegmentations().size());
        for (PostBatchSegmentation segmentation : batch.getPostBatchSegmentations()) {
            Assert.assertEquals(new Long(2L), segmentation.getPostBatchId());
            Assert.assertTrue(segmentation.getSegmentationId() == 9000L);
        }

        batch = postBatchDao.readPostBatchById(3L);
        Assert.assertNotNull(batch);
        Assert.assertEquals(new Long(3L), batch.getId());
        Assert.assertEquals("Batch 3", batch.getBatchDesc());
        Assert.assertEquals("constituent", batch.getBatchType());
        Assert.assertTrue(batch.isExecuted());
        Assert.assertEquals(new Long(200L), batch.getExecutedById());
        Assert.assertEquals("pablo@company1.com", batch.getExecutedByUser());
        Assert.assertEquals(new Long(100L), batch.getBatchCreatedById());
        Assert.assertNotNull(batch.getBatchCreatedDate());
        Assert.assertNotNull(batch.getPostBatchSegmentations());
        Assert.assertTrue(batch.getPostBatchSegmentations().isEmpty());
    }
}