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

import com.orangeleap.tangerine.controller.NewTangerineFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.TangerineFormDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 10:14:35 AM
 */
public class TangerineFormControllerTest extends BaseTest {

	@Test(dataProvider = "setupTangerineForm", dataProviderClass = TangerineFormDataProvider.class)
	public void testConvertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineFormController controller = new MockTangerineFormController();
		controller.convertFormToDomain(request, form, paramMap);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Gift gift = (Gift) form.getDomainObject();
		Assert.assertEquals(new BigDecimal("45.99"), gift.getAmount(), "Amount = " + gift.getAmount());
		Assert.assertEquals(new Long(100L), new Long(gift.getId()), "Id = " + gift.getId());
		Assert.assertEquals("Check", gift.getPaymentType(), "PaymentType = " + gift.getPaymentType());
		Assert.assertEquals("111", gift.getCheckNumber(), "CheckNumber = " + gift.getCheckNumber());
		Assert.assertEquals("My gift to you", gift.getComments(), "Comments = " + gift.getComments());
		Assert.assertEquals("firstname", gift.getConstituent().getFirstName(), "FirstName = " + gift.getConstituent().getFirstName());
		Assert.assertEquals("lastname", gift.getConstituent().getLastName(), "LastName = " + gift.getConstituent().getLastName());
		Assert.assertEquals("CREDIT_CARD", gift.getPaymentSource().getPaymentType(), "PaymentType = " + gift.getPaymentSource().getPaymentType());
		Assert.assertEquals("4111111111111111", gift.getPaymentSource().getCreditCardNumber(), "CreditCardNumber = " + gift.getPaymentSource().getCreditCardNumber());
		Assert.assertEquals("01/01/1999", format.format(gift.getPostedDate()), "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals("Joe Blow", gift.getCustomFieldValue("reference"), "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals("787", gift.getCustomFieldValue("daddyo"), "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals("Yo Mama", gift.getCustomFieldValue("momma"), "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));
	}

	class MockTangerineFormController extends NewTangerineFormController {
		
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