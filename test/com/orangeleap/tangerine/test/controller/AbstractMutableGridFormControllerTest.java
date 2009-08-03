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

package com.orangeleap.tangerine.test.controller;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.gift.AbstractMutableGridFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.AbstractMutableGridDataProvider;
import com.orangeleap.tangerine.util.StringConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 10:14:35 AM
 */
public class AbstractMutableGridFormControllerTest extends BaseTest {

	@Test(dataProvider = "setupAbstractDistroLineForm", dataProviderClass = AbstractMutableGridDataProvider.class)
	public void testConvertDistroLineFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockDistroLineFormController controller = new MockDistroLineFormController();
		controller.convertFormToDomain(request, form, paramMap);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Gift gift = (Gift) form.getDomainObject();
		Assert.assertEquals(gift.getAmount(), new BigDecimal("45.99"), "Amount = " + gift.getAmount());
		Assert.assertEquals(new Long(gift.getId()), new Long(100L), "Id = " + gift.getId());
		Assert.assertEquals(gift.getPaymentType(), "Check", "PaymentType = " + gift.getPaymentType());
		Assert.assertEquals(gift.getCheckNumber(), "111", "CheckNumber = " + gift.getCheckNumber());
		Assert.assertEquals(gift.getComments(), "My gift to you", "Comments = " + gift.getComments());
		Assert.assertEquals(gift.getConstituent().getFirstName(), "firstname", "FirstName = " + gift.getConstituent().getFirstName());
		Assert.assertEquals(gift.getConstituent().getLastName(), "lastname", "LastName = " + gift.getConstituent().getLastName());
		Assert.assertEquals(gift.getPaymentType(), "Check", "PaymentType = " + gift.getPaymentType());
		Assert.assertNull(gift.getPaymentSource(), "PaymentSource != null");
		Assert.assertEquals(format.format(gift.getPostedDate()), "01/01/1999", "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals(gift.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals(gift.getCustomFieldValue("daddyo"), "787", "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals(gift.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));

		Assert.assertNotNull(gift.getDistributionLines(), "distributionLines were null");
		Assert.assertEquals(gift.getDistributionLines().size(), 2, "distributionLines size = " + gift.getDistributionLines().size());
		Assert.assertEquals(gift.getDistributionLines().get(0).getAmount(), new BigDecimal("45"), "DistroLine[0].amount = " + gift.getDistributionLines().get(0).getAmount());
		Assert.assertEquals(gift.getDistributionLines().get(0).getPercentage(), new BigDecimal("98"), "DistroLine[0].percentage = " + gift.getDistributionLines().get(0).getPercentage());
		Assert.assertEquals(gift.getDistributionLines().get(0).getProjectCode(), "Trial", "DistroLine[0].projectCode = " + gift.getDistributionLines().get(0).getProjectCode());
		Assert.assertEquals(gift.getDistributionLines().get(0).getCustomFieldValue("tribute"), "inMemoryOf", "DistroLine[0].customFieldMap[tribute].value = " + gift.getDistributionLines().get(0).getCustomFieldValue("tribute"));

		Assert.assertEquals(gift.getDistributionLines().get(1).getAmount(), new BigDecimal(".99"), "DistroLine[1].amount = " + gift.getDistributionLines().get(1).getAmount());
		Assert.assertEquals(gift.getDistributionLines().get(1).getPercentage(), new BigDecimal(2), "DistroLine[1].percentage = " + gift.getDistributionLines().get(1).getPercentage());
		Assert.assertEquals(gift.getDistributionLines().get(1).getCustomFieldValue("onBehalfOf"), "Fooey", "DistroLine[1].customFieldMap[onBehalfOf].value = " + gift.getDistributionLines().get(1).getCustomFieldValue("onBehalfOf"));
		Assert.assertEquals(gift.getDistributionLines().get(1).getCustomFieldValue("taxDeductible"), "true", "DistroLine[1].customFieldMap[taxDeductible].value = " + gift.getDistributionLines().get(1).getCustomFieldValue("taxDeductible"));

		/* Check the form bean to see if the re-indexing was correct */
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[0].amount"), "45", "form.distributionLines[0].amount = " + form.getFieldValueFromUnescapedFieldName("distributionLines[0].amount"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[0].percentage"), "98", "form.distributionLines[0].percentage = " + form.getFieldValueFromUnescapedFieldName("distributionLines[0].percentage"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[0].projectCode"), "Trial", "form.distributionLines[0].projectCode = " + form.getFieldValueFromUnescapedFieldName("distributionLines[0].projectCode"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[0].customFieldMap[tribute]"), "inMemoryOf", "form.distributionLines[0].customFieldMap[tribute] = " + form.getFieldValueFromUnescapedFieldName("distributionLines[0].customFieldMap[tribute]"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[1].amount"), ".99", "form.distributionLines[1].amount = " + form.getFieldValueFromUnescapedFieldName("distributionLines[1].amount"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[1].percentage"), "2", "form.distributionLines[1].percentage = " + form.getFieldValueFromUnescapedFieldName("distributionLines[1].percentage"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[1].customFieldMap[onBehalfOf]"), "Fooey", "form.distributionLines[1].customFieldMap[onBehalfOf] = " + form.getFieldValueFromUnescapedFieldName("distributionLines[1].customFieldMap[onBehalfOf]"));
		Assert.assertTrue(form.getFieldMap().containsKey(TangerineForm.escapeFieldName("_distributionLines[1].customFieldMap[taxDeductible]")), "_distributionLines[1].customFieldMap[taxDeductible] not in form fieldMap");
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("distributionLines[1].customFieldMap[taxDeductible]"), "true", "form.distributionLines[1].customFieldMap[taxDeductible] = " + form.getFieldValueFromUnescapedFieldName("distributionLines[1].customFieldMap[taxDeductible]"));
	}

	@Test(dataProvider = "setupAbstractGiftInKindDetailForm", dataProviderClass = AbstractMutableGridDataProvider.class)
	public void testConvertGiftInKindDetailsFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockGiftInKindDetailsFormController controller = new MockGiftInKindDetailsFormController();
		controller.convertFormToDomain(request, form, paramMap);

		GiftInKind giftInKind = (GiftInKind) form.getDomainObject();
		Assert.assertEquals(giftInKind.getFairMarketValue(), new BigDecimal("45.99"), "fairMarketValue = " + giftInKind.getFairMarketValue());
		Assert.assertEquals(new Long(giftInKind.getId()), new Long(100L), "Id = " + giftInKind.getId());
		Assert.assertEquals(giftInKind.getMotivationCode(), "111", "MotivationCode = " + giftInKind.getMotivationCode());
		Assert.assertEquals(giftInKind.getConstituent().getFirstName(), "firstname", "FirstName = " + giftInKind.getConstituent().getFirstName());
		Assert.assertEquals(giftInKind.getConstituent().getLastName(), "lastname", "LastName = " + giftInKind.getConstituent().getLastName());
		Assert.assertEquals(giftInKind.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + giftInKind.getCustomFieldValue("reference"));
		Assert.assertEquals(giftInKind.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + giftInKind.getCustomFieldValue("momma"));

		Assert.assertNotNull(giftInKind.getDetails(), "details were null");
		Assert.assertEquals(giftInKind.getDetails().size(), 2, "details size = " + giftInKind.getDetails().size());
		Assert.assertEquals(giftInKind.getDetails().get(0).getDetailFairMarketValue(), new BigDecimal("45"), "Detail[0].detailFairMarketValue = " + giftInKind.getDetails().get(0).getDetailFairMarketValue());
		Assert.assertEquals(giftInKind.getDetails().get(0).getDescription(), "dude", "Detail[0].description = " + giftInKind.getDetails().get(0).getDescription());
		Assert.assertEquals(giftInKind.getDetails().get(0).getProjectCode(), "Trial", "Detail[0].projectCode = " + giftInKind.getDetails().get(0).getProjectCode());
		Assert.assertEquals(giftInKind.getDetails().get(0).getCustomFieldValue("tribute"), "inMemoryOf", "Detail[0].customFieldMap[tribute].value = " + giftInKind.getDetails().get(0).getCustomFieldValue("tribute"));

		Assert.assertEquals(giftInKind.getDetails().get(1).getDetailFairMarketValue(), new BigDecimal(".99"), "Detail[1].detailFairMarketValue = " + giftInKind.getDetails().get(1).getDetailFairMarketValue());
		Assert.assertEquals(giftInKind.getDetails().get(1).getDescription(), "2", "Detail[1].description = " + giftInKind.getDetails().get(1).getDescription());
		Assert.assertEquals(giftInKind.getDetails().get(1).getCustomFieldValue("onBehalfOf"), "Fooey", "Detail[1].customFieldMap[onBehalfOf].value = " + giftInKind.getDetails().get(1).getCustomFieldValue("onBehalfOf"));
		Assert.assertEquals(giftInKind.getDetails().get(1).getCustomFieldValue("taxDeductible"), "true", "Detail[1].customFieldMap[taxDeductible].value = " + giftInKind.getDetails().get(1).getCustomFieldValue("taxDeductible"));

		/* Check the form bean to see if the re-indexing was correct */
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[0].detailFairMarketValue"), "45", "form.details[0].detailFairMarketValue = " + form.getFieldValueFromUnescapedFieldName("details[0].detailFairMarketValue"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[0].description"), "dude", "form.details[0].description = " + form.getFieldValueFromUnescapedFieldName("details[0].description"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[0].projectCode"), "Trial", "form.details[0].projectCode = " + form.getFieldValueFromUnescapedFieldName("details[0].projectCode"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[0].customFieldMap[tribute]"), "inMemoryOf", "form.details[0].customFieldMap[tribute] = " + form.getFieldValueFromUnescapedFieldName("details[0].customFieldMap[tribute]"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[1].detailFairMarketValue"), ".99", "form.details[1].detailFairMarketValue = " + form.getFieldValueFromUnescapedFieldName("details[1].detailFairMarketValue"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[1].description"), "2", "form.details[1].description = " + form.getFieldValueFromUnescapedFieldName("details[1].description"));
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[1].customFieldMap[onBehalfOf]"), "Fooey", "form.details[1].customFieldMap[onBehalfOf] = " + form.getFieldValueFromUnescapedFieldName("details[1].customFieldMap[onBehalfOf]"));
		Assert.assertTrue(form.getFieldMap().containsKey(TangerineForm.escapeFieldName("_details[1].customFieldMap[taxDeductible]")), "_details[1].customFieldMap[taxDeductible] not in form fieldMap");
		Assert.assertEquals(form.getFieldValueFromUnescapedFieldName("details[1].customFieldMap[taxDeductible]"), "true", "form.details[1].customFieldMap[taxDeductible] = " + form.getFieldValueFromUnescapedFieldName("details[1].customFieldMap[taxDeductible]"));
	}

	class MockDistroLineFormController extends AbstractMutableGridFormController {
		MockDistroLineFormController() {
			setBindPaymentSource(true);
			setBindAddress(true);
			setBindPhone(true);
		}

		@Override
		public String getGridCollectionName() {
			return StringConstants.DISTRIBUTION_LINES;
		}

		@Override
		public String getAmountKey() {
			return "amount";
		}

		@Override
		protected AbstractEntity findEntity(HttpServletRequest request) {
			return null;
		}

		@Override
		public void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {
			super.convertFormToDomain(request, form, paramMap);
		}

		@Override
		protected Constituent getConstituent(HttpServletRequest request) {
			return new Constituent();
		}
	}

	class MockGiftInKindDetailsFormController extends AbstractMutableGridFormController {

		@Override
		public String getGridCollectionName() {
			return "details";
		}

		@Override
		public String getAmountKey() {
			return "detailFairMarketValue";
		}

		@Override
		protected AbstractEntity findEntity(HttpServletRequest request) {
			return null;
		}

		@Override
		public void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {
			super.convertFormToDomain(request, form, paramMap);
		}

		@Override
		protected Constituent getConstituent(HttpServletRequest request) {
			return new Constituent();
		}
	}
}