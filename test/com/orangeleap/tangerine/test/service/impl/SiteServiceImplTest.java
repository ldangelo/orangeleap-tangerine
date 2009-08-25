package com.orangeleap.tangerine.test.service.impl;

import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.impl.SiteServiceImpl;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.SiteServiceDataProvider;
import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteServiceImplTest extends BaseTest {

    @Autowired
    private SiteService siteService;

    @Test
    public void testGetArrayFieldValues() throws Exception {
	    Gift gift = new Gift();
		DistributionLine dl1 = new DistributionLine(new BigDecimal("25"));
		dl1.addCustomFieldValue("reference", "Joe Blow");
		DistributionLine dl2 = new DistributionLine(new BigDecimal("1.34"));
		dl2.addCustomFieldValue("reference", "Momma");
		DistributionLine dl3 = new DistributionLine(new BigDecimal("1000.50"));
		dl3.addCustomFieldValue("reference", "Whoa Shoot");

		List<DistributionLine> lines = new ArrayList<DistributionLine>();
		lines.add(dl1);
		lines.add(dl2);
		lines.add(dl3);

		gift.setDistributionLines(lines);

		gift.addAssociatedPledgeId(1L);
		gift.addAssociatedPledgeId(5L);
		gift.addAssociatedPledgeId(3L);
	    gift.addAssociatedPledgeId(1000L);

	    /** Invocation 1 for distro line amounts */
	    SectionField sectionFieldLineAmt = new SectionField();

	    FieldDefinition fieldDef = new FieldDefinition();
	    fieldDef.setFieldName("distributionLines");

	    FieldDefinition secondaryFieldDef = new FieldDefinition();
	    secondaryFieldDef.setFieldName("amount");

	    sectionFieldLineAmt.setFieldDefinition(fieldDef);
	    sectionFieldLineAmt.setSecondaryFieldDefinition(secondaryFieldDef);

	    Map<String, Object> fieldValMap = new HashMap<String, Object>();

	    invokeGetArrayFieldValues(sectionFieldLineAmt, fieldValMap, gift);

	    Assert.assertTrue(fieldValMap.size() == 3, "FieldValMap size is " + fieldValMap.size());

	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[0].amount"), "Could not find key distributionLines[0].amount, keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[1].amount"), "Could not find key distributionLines[1].amount, keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[2].amount"), "Could not find key distributionLines[2].amount, keys = " + fieldValMap.keySet());

	    Assert.assertEquals(new BigDecimal("25"), fieldValMap.get("distributionLines[0].amount"), "Value for distributionLines[0].amount is = " + fieldValMap.get("distributionLines[0].amount"));
	    Assert.assertEquals(new BigDecimal("1.34"), fieldValMap.get("distributionLines[1].amount"), "Value for distributionLines[1].amount is = " + fieldValMap.get("distributionLines[1].amount"));
	    Assert.assertEquals(new BigDecimal("1000.50"), fieldValMap.get("distributionLines[2].amount"), "Value for distributionLines[2].amount is = " + fieldValMap.get("distributionLines[2].amount"));

	    
		/** Invocation 2 for distro line custom fields */
	    SectionField sectionFieldLineRef = new SectionField();
	    fieldValMap = new HashMap<String, Object>();

	    fieldDef = new FieldDefinition();
	    fieldDef.setFieldName("distributionLines");

	    secondaryFieldDef = new FieldDefinition();
	    secondaryFieldDef.setFieldName("customFieldMap[reference]");

	    sectionFieldLineRef.setFieldDefinition(fieldDef);
	    sectionFieldLineRef.setSecondaryFieldDefinition(secondaryFieldDef);
	    invokeGetArrayFieldValues(sectionFieldLineRef, fieldValMap, gift);

	    Assert.assertTrue(fieldValMap.size() == 3, "FieldValMap size is " + fieldValMap.size());

	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[0].customFieldMap[reference]"), "Could not find key distributionLines[0].customFieldMap[reference], keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[1].customFieldMap[reference]"), "Could not find key distributionLines[1].customFieldMap[reference], keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("distributionLines[2].customFieldMap[reference]"), "Could not find key distributionLines[2].customFieldMap[reference], keys = " + fieldValMap.keySet());

	    Assert.assertEquals("Joe Blow", fieldValMap.get("distributionLines[0].customFieldMap[reference]"), "Value for distributionLines[0].customFieldMap[reference] is = " + fieldValMap.get("distributionLines[0].customFieldMap[reference]"));
	    Assert.assertEquals("Momma", fieldValMap.get("distributionLines[1].customFieldMap[reference]"), "Value for distributionLines[1].customFieldMap[reference] is = " + fieldValMap.get("distributionLines[1].customFieldMap[reference]"));
	    Assert.assertEquals("Whoa Shoot", fieldValMap.get("distributionLines[2].customFieldMap[reference]"), "Value for distributionLines[2].customFieldMap[reference] is = " + fieldValMap.get("distributionLines[2].customFieldMap[reference]"));

	    
	    /** Invocation 3 for associatedPledgeIds */
        SectionField sectionFieldPledgeIds = new SectionField();
        fieldValMap = new HashMap<String, Object>();

        fieldDef = new FieldDefinition();
        fieldDef.setFieldName("associatedPledgeIds");

        sectionFieldPledgeIds.setFieldDefinition(fieldDef);
        invokeGetArrayFieldValues(sectionFieldPledgeIds, fieldValMap, gift);

        Assert.assertTrue(fieldValMap.size() == 4, "FieldValMap size is " + fieldValMap.size());
	    Assert.assertTrue(fieldValMap.containsKey("associatedPledgeIds[0]"), "Could not find key associatedPledgeIds[0], keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("associatedPledgeIds[1]"), "Could not find key associatedPledgeIds[1], keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("associatedPledgeIds[2]"), "Could not find key associatedPledgeIds[2], keys = " + fieldValMap.keySet());
	    Assert.assertTrue(fieldValMap.containsKey("associatedPledgeIds[3]"), "Could not find key associatedPledgeIds[3], keys = " + fieldValMap.keySet());

	    Assert.assertEquals(1L, fieldValMap.get("associatedPledgeIds[0]"), "Value for associatedPledgeIds[0] is = " + fieldValMap.get("associatedPledgeIds[0]"));
	    Assert.assertEquals(5L, fieldValMap.get("associatedPledgeIds[1]"), "Value for associatedPledgeIds[1] is = " + fieldValMap.get("associatedPledgeIds[1]"));
	    Assert.assertEquals(3L, fieldValMap.get("associatedPledgeIds[2]"), "Value for associatedPledgeIds[2] is = " + fieldValMap.get("associatedPledgeIds[2]"));
	    Assert.assertEquals(1000L, fieldValMap.get("associatedPledgeIds[3]"), "Value for associatedPledgeIds[3] is = " + fieldValMap.get("associatedPledgeIds[3]"));
    }

	@Test(dataProvider = "setupEntityDefault", dataProviderClass = SiteServiceDataProvider.class)
	public void testGetDefaultValue(Gift gift) throws Exception {
		// no conditions
		EntityDefault def = new EntityDefault("Paid");
		Object val = invokeGetDefaultValue(def, gift, "giftStatus");
		Assert.assertEquals(val.toString(), "Paid", "val = " + val);
		Assert.assertEquals(gift.getGiftStatus(), "Paid", "giftStatus = " + gift.getGiftStatus());

		def = new EntityDefault("dude");
		val = invokeGetDefaultValue(def, gift, "customFieldMap[other_reference]");
		Assert.assertEquals(val.toString(), "dude", "val = " + val);
		Assert.assertEquals(gift.getCustomFieldValue("other_reference"), "dude", "customFieldMap[other_reference] = " + gift.getCustomFieldValue("other_reference"));

		def = new EntityDefault(StringConstants.NOW_COLON);
		val = invokeGetDefaultValue(def, gift, "customFieldMap[giftTime]");
		Assert.assertTrue(Date.class.equals(val.getClass()), "val class = " + val.getClass());
		Assert.assertTrue(String.class.equals(gift.getCustomFieldValue("giftTime").getClass()), "giftTime class = " + gift.getCustomFieldValue("giftTime").getClass());

		def = new EntityDefault(StringConstants.BEAN_COLON + "customFieldMap[momma]");
		val = invokeGetDefaultValue(def, gift, "customFieldMap[papa]");
		Assert.assertEquals(val.toString(), "Yo Mama", "val = " + val);
		Assert.assertEquals(gift.getCustomFieldValue("papa"), "Yo Mama", "customFieldMap[papa] = " + gift.getCustomFieldValue("papa"));

		def = new EntityDefault(StringConstants.BEAN_COLON + "constituent.firstLast");
		val = invokeGetDefaultValue(def, gift, "customFieldMap[personResponsible]");
		Assert.assertEquals(val.toString(), "Joe Blow", "val = " + val);
		Assert.assertEquals(gift.getCustomFieldValue("personResponsible"), "Joe Blow", "customFieldMap[personResponsible] = " + gift.getCustomFieldValue("personResponsible"));

		def = new EntityDefault("true");
		val = invokeGetDefaultValue(def, gift, "distributionLines[0].customFieldMap[taxDeductible]");
		Assert.assertEquals(val.toString(), "true", "val = " + val);
		Assert.assertEquals(gift.getDistributionLines().get(0).getCustomFieldValue("taxDeductible"), "true", "gift.distributionLines[0].customFieldMap[taxDeductible] = " + gift.getDistributionLines().get(0).getCustomFieldValue("taxDeductible"));

		// Conditions
		def = new EntityDefault("Finished", "giftStatus == 'Not Paid'"); // set paymentStatus to 'Finished' if giftStatus is 'Not Paid'
		val = invokeGetDefaultValue(def, gift, "paymentStatus");
		Assert.assertNull(val, "val != null");
		Assert.assertFalse(StringUtils.hasText(gift.getPaymentStatus()), "paymentStatus = " + gift.getPaymentStatus());

		def = new EntityDefault("Finished", "giftStatus == 'Paid'"); // set paymentStatus to 'Finished' if giftStatus is 'Not Paid'
		val = invokeGetDefaultValue(def, gift, "paymentStatus");
		Assert.assertEquals(val.toString(), "Finished", "val = " + val);
		Assert.assertEquals(gift.getPaymentStatus(), "Finished", "paymentStatus = " + gift.getPaymentStatus());

		def = new EntityDefault(":now", "posted == 'true'"); // set postmarkDate to 'now' if posted = true
		val = invokeGetDefaultValue(def, gift, "postmarkDate");
		Assert.assertNull(val, "val != null");
		Assert.assertNull(gift.getPostmarkDate(), "postmarkDate != null");

		def = new EntityDefault(StringConstants.BEAN_COLON + "customFieldMap[momma]", "posted == 'true'"); // set customField[blarg] to value of customFieldMap[momma] if posted = true
		val = invokeGetDefaultValue(def, gift, "customFieldMap[blarg]");
		Assert.assertNull(val, "val != null");
		Assert.assertFalse(StringUtils.hasText(gift.getCustomFieldValue("blarg")), "customFieldMap[blarg] = " + gift.getCustomFieldValue("blarg"));

		gift.setPosted(true);

		def = new EntityDefault(StringConstants.NOW_COLON, "posted == 'true'"); // set postmarkDate to 'now' if posted = true
		val = invokeGetDefaultValue(def, gift, "postmarkDate");
		Assert.assertTrue(Date.class.equals(val.getClass()), "val.class = " + val.getClass());
		Assert.assertTrue(Date.class.equals(gift.getPostmarkDate().getClass()), "postmarkDate.class = " + gift.getPostmarkDate().getClass());
		
		def = new EntityDefault(StringConstants.BEAN_COLON + "customFieldMap[momma]", "posted == 'true'"); // set customField[blarg] to value of customFieldMap[momma] if posted = true
		val = invokeGetDefaultValue(def, gift, "customFieldMap[blarg]");
		Assert.assertEquals(val.toString(), "Yo Mama", "val = " + val);
		Assert.assertEquals(gift.getCustomFieldValue("blarg"), "Yo Mama", "customFieldMap[blarg] = " + gift.getCustomFieldValue("blarg"));


		// No defaults
		val = invokeGetDefaultValue(null, gift, "txRefNum");
		Assert.assertNull(val, "val != null");
		Assert.assertNull(gift.getTxRefNum(), "txRefNum = " + gift.getTxRefNum());

		def = new EntityDefault();
		val = invokeGetDefaultValue(def, gift, "transactionDate");
		Assert.assertNull(val, "val != null");
		Assert.assertNull(gift.getTransactionDate(), "transactionDate != null");

		def = new EntityDefault("   ");
		val = invokeGetDefaultValue(def, gift, "transactionDate");
		Assert.assertNull(val, "val != null");
		Assert.assertNull(gift.getTransactionDate(), "transactionDate != null");
	}
	
    private Object invokeGetArrayFieldValues(SectionField sectionField, Map returnMap, Object bean) throws Exception {
        Method method = ((SiteServiceImpl) siteService).getClass().getDeclaredMethod("getArrayFieldValues", SectionField.class, Map.class, BeanWrapper.class);
        method.setAccessible(true);
        return method.invoke(siteService, sectionField, returnMap, PropertyAccessorFactory.forBeanPropertyAccess(bean));
    }

	private Object invokeGetDefaultValue(EntityDefault entityDefault, Object bean, String key) throws Exception {
	    return siteService.getDefaultValue(entityDefault, PropertyAccessorFactory.forBeanPropertyAccess(bean), key);
	}
}