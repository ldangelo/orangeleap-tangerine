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

import com.orangeleap.tangerine.controller.NewTangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.TangerineFormConstituentAttributesDataProvider;
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
public class TangerineConstituentAttributesFormControllerTest extends BaseTest {

	@Test(dataProvider = "setupExistingPaymentSourceTangerineForm", dataProviderClass = TangerineFormConstituentAttributesDataProvider.class)
	public void testConvertFormToDomainExistingPaymentSource(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineConstituentAttributesFormController controller = new MockTangerineConstituentAttributesFormController();
		controller.convertFormToDomain(request, form, paramMap);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Gift gift = (Gift) form.getDomainObject();
		Assert.assertEquals(gift.getAmount(), new BigDecimal("45.99"), "Amount = " + gift.getAmount());
		Assert.assertEquals(new Long(gift.getId()), new Long(100L), "Id = " + gift.getId());
		Assert.assertEquals(gift.getComments(), "My gift to you", "Comments = " + gift.getComments());
		Assert.assertEquals(gift.getConstituent().getFirstName(), "firstname", "FirstName = " + gift.getConstituent().getFirstName());
		Assert.assertEquals(gift.getConstituent().getLastName(), "lastname", "LastName = " + gift.getConstituent().getLastName());
		Assert.assertEquals(format.format(gift.getPostedDate()), "01/01/1999", "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals(gift.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals(gift.getCustomFieldValue("daddyo"), "787", "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals(gift.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));

		Assert.assertEquals(gift.getPaymentSource().getId(), new Long(1L), "PaymentSource.id = " + gift.getPaymentSource().getId());
		Assert.assertEquals(gift.getPaymentSource().getPaymentType(), "CREDIT_CARD", "PaymentType = " + gift.getPaymentSource().getPaymentType());
		Assert.assertEquals(gift.getPaymentSource().getCreditCardHolderName(), "Big Brown One", "CreditCardHolderName = " + gift.getPaymentSource().getCreditCardHolderName());
		Assert.assertEquals(gift.getPaymentSource().getCreditCardSecurityCode(), "202", "CreditCardSecurityCode = " + gift.getPaymentSource().getCreditCardSecurityCode());
	}

	@Test(dataProvider = "setupNonePaymentSourceTangerineForm", dataProviderClass = TangerineFormConstituentAttributesDataProvider.class)
	public void testConvertFormToDomainNonePaymentSource(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineConstituentAttributesFormController controller = new MockTangerineConstituentAttributesFormController();
		controller.convertFormToDomain(request, form, paramMap);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Gift gift = (Gift) form.getDomainObject();
		Assert.assertEquals(gift.getAmount(), new BigDecimal("45.99"), "Amount = " + gift.getAmount());
		Assert.assertEquals(new Long(gift.getId()), new Long(100L), "Id = " + gift.getId());
		Assert.assertEquals(gift.getComments(), "My gift to you", "Comments = " + gift.getComments());
		Assert.assertEquals(gift.getConstituent().getFirstName(), "firstname", "FirstName = " + gift.getConstituent().getFirstName());
		Assert.assertEquals(gift.getConstituent().getLastName(), "lastname", "LastName = " + gift.getConstituent().getLastName());
		Assert.assertEquals(format.format(gift.getPostedDate()), "01/01/1999", "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals(gift.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals(gift.getCustomFieldValue("daddyo"), "787", "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals(gift.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));

		Assert.assertNull(gift.getPaymentSource(), "paymentSource != null");
	}

	@Test(dataProvider = "setupNewPaymentSourceTangerineForm", dataProviderClass = TangerineFormConstituentAttributesDataProvider.class)
	public void testConvertFormToDomainNewPaymentSource(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineConstituentAttributesFormController controller = new MockTangerineConstituentAttributesFormController();
		controller.convertFormToDomain(request, form, paramMap);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Gift gift = (Gift) form.getDomainObject();
		Assert.assertEquals(gift.getAmount(), new BigDecimal("45.99"), "Amount = " + gift.getAmount());
		Assert.assertEquals(new Long(gift.getId()), new Long(100L), "Id = " + gift.getId());
		Assert.assertEquals(gift.getComments(), "My gift to you", "Comments = " + gift.getComments());
		Assert.assertEquals(gift.getConstituent().getFirstName(), "firstname", "FirstName = " + gift.getConstituent().getFirstName());
		Assert.assertEquals(gift.getConstituent().getLastName(), "lastname", "LastName = " + gift.getConstituent().getLastName());
		Assert.assertEquals(format.format(gift.getPostedDate()), "01/01/1999", "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals(gift.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals(gift.getCustomFieldValue("daddyo"), "787", "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals(gift.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));

		Assert.assertNull(gift.getPaymentSource().getId(), "PaymentSource.id = " + gift.getPaymentSource().getId());
		Assert.assertEquals(gift.getPaymentSource().getPaymentType(), "ACH", "PaymentType = " + gift.getPaymentSource().getPaymentType());
		Assert.assertEquals(gift.getPaymentSource().getAchHolderName(), "Big Brown One", "AchHolderName = " + gift.getPaymentSource().getAchHolderName());
		Assert.assertEquals(gift.getPaymentSource().getAchRoutingNumber(), "12100005", "AchRoutingNumber = " + gift.getPaymentSource().getAchRoutingNumber());
		Assert.assertEquals(gift.getPaymentSource().getAchAccountNumber(), "8", "AchAccountNumber = " + gift.getPaymentSource().getAchAccountNumber());
		Assert.assertNull(gift.getPaymentSource().getCreditCardNumber(), "CreditCardNumber != null");
	}

	class MockTangerineConstituentAttributesFormController extends NewTangerineConstituentAttributesFormController {

		MockTangerineConstituentAttributesFormController() {
			setBindPaymentSource(true);
		}

		@Override
		protected Constituent getConstituent(HttpServletRequest request) {
			return new Constituent();
		}

		@Override
		protected AbstractEntity findEntity(HttpServletRequest request) {
			return null;
		}

		@Override
		public void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {
			super.convertFormToDomain(request, form, paramMap);
		}
	}
}