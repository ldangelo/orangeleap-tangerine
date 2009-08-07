package com.orangeleap.tangerine.test.dao.ibatis;

import com.orangeleap.tangerine.dao.ibatis.AbstractIBatisDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Map;

public class AbstractIBatisDaoTest extends AbstractIBatisTest {
    
    @Test
    public void testFindBeanPropertyColumnMap() throws Exception {
        MockIBatisDao dao = new MockIBatisDao((SqlMapClient) applicationContext.getBean("sqlMapClient"));
        Map<String, String> map = dao.findBeanPropertyColumnMap("GIFT.GIFT_RESULT");

        Assert.assertNotNull(map);
        Assert.assertTrue(map.containsKey("id"));
        Assert.assertTrue("GIFT_ID".equals(map.get("id")));
        Assert.assertTrue(map.containsKey("donationDate"));
        Assert.assertTrue("GIFT_DONATION_DATE".equals(map.get("donationDate")));
        Assert.assertTrue(map.containsKey("paymentSource.creditCardHolderName"));
        Assert.assertTrue("PAYMENT_SRC_CREDIT_CARD_HOLDER_NAME".equals(map.get("paymentSource.creditCardHolderName")));
        Assert.assertTrue(map.containsKey("constituent.lastName"));
        Assert.assertTrue("LAST_NAME".equals(map.get("constituent.lastName")));
        Assert.assertTrue(map.containsKey("address.addressLine1"));
        Assert.assertTrue("ADDRESS_LINE_1".equals(map.get("address.addressLine1")));
        Assert.assertTrue(map.containsKey("phone.number"));
        Assert.assertTrue("PHONE_NUMBER".equals(map.get("phone.number")));
    }

    class MockIBatisDao extends AbstractIBatisDao {
        MockIBatisDao(SqlMapClient sqlMapClient) {
            super(sqlMapClient);
        }
    }
}
