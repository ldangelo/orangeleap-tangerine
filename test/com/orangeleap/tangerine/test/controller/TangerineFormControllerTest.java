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
import com.orangeleap.tangerine.controller.TangerineFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.TangerineFormDataProvider;
import com.orangeleap.tangerine.util.AES;
import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
public class TangerineFormControllerTest extends BaseTest {

	@Test(dataProvider = "setupTangerineForm", dataProviderClass = TangerineFormDataProvider.class)
	public void testConvertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineFormController controller = new MockTangerineFormController();
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
		Assert.assertEquals(format.format(gift.getPostedDate()), "01/01/1999", "PostedDate = " + gift.getPostedDate());
		Assert.assertTrue(gift.isPosted(), "Posted = false");
		Assert.assertFalse(gift.isDeductible(), "Deductible = true");
		Assert.assertEquals(gift.getCustomFieldValue("reference"), "Joe Blow", "customFieldMap[reference].value = " + gift.getCustomFieldValue("reference"));
		Assert.assertEquals(gift.getCustomFieldValue("daddyo"), "787", "customFieldMap[daddyo].value = " + gift.getCustomFieldValue("daddyo"));
		Assert.assertEquals(gift.getCustomFieldValue("momma"), "Yo Mama", "customFieldMap[momma].value = " + gift.getCustomFieldValue("momma"));
        Assert.assertEquals(gift.getCustomFieldValue("checkAccountNumber"), AES.encrypt("911911"));
        Assert.assertEquals(gift.getCustomFieldValue("checkRoutingNumber"), AES.encrypt("abcdefghijk"));
	}

	@Test(dataProvider = "setupTestArrayForm", dataProviderClass = TangerineFormDataProvider.class)
	public void testConvertArrayFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {

		MockTangerineFormController controller = new MockTangerineFormController();
		controller.convertFormToDomain(request, form, paramMap);
		FakeTestArray array = (FakeTestArray) form.getDomainObject();

		Assert.assertEquals(array.getStringArray()[0], "a", "StrArray[0] = " + array.getStringArray()[0]);
		Assert.assertEquals(array.getStringArray()[1], "b", "StrArray[1] = " + array.getStringArray()[1]);

		Assert.assertEquals(array.getIntArray()[0], 1, "IntArray[0] = " + array.getIntArray()[0]);
		Assert.assertEquals(array.getIntArray()[1], 2, "IntArray[1] = " + array.getIntArray()[1]);
	}

	@Test(dataProvider = "setupBindErrors", dataProviderClass = TangerineFormDataProvider.class)
	public void testBindDomainErrorsToForm(BindException formErrors, BindException domainErrors) throws Exception {
		MockTangerineFormController controller = new MockTangerineFormController();
		controller.bindDomainErrorsToForm(formErrors, domainErrors);

		Assert.assertTrue(formErrors.hasErrors(), "No form errors");
		Assert.assertEquals(formErrors.getGlobalErrors().size(), 1, "Global errors size = " + formErrors.getGlobalErrors().size());
		Assert.assertEquals(formErrors.getFieldErrors().size(), 2, "Field errors size = " + formErrors.getGlobalErrors().size());
		Assert.assertEquals(((ObjectError) formErrors.getGlobalErrors().get(0)).getCode(), "errorMaxReminders", "Global error[0] code = " + ((ObjectError) formErrors.getGlobalErrors().get(0)).getCode());
		Assert.assertEquals(((FieldError) formErrors.getFieldErrors().get(0)).getField(), StringConstants.FIELD_MAP_START + "amount" + StringConstants.FIELD_MAP_END,
				"Field error[0] fieldName = " + ((FieldError) formErrors.getFieldErrors().get(0)).getField());
		Assert.assertEquals(((FieldError) formErrors.getFieldErrors().get(1)).getField(), StringConstants.FIELD_MAP_START +
				TangerineForm.escapeFieldName("customFieldMap[reference]") + StringConstants.FIELD_MAP_END, "Field error[1] fieldName = " + ((FieldError) formErrors.getFieldErrors().get(1)).getField());
		Assert.assertEquals(((FieldError) formErrors.getFieldErrors().get(0)).getCode(), "exceptionHeading", "Field error[0] code = " + ((FieldError) formErrors.getFieldErrors().get(0)).getCode());
		Assert.assertEquals(((FieldError) formErrors.getFieldErrors().get(1)).getCode(), "errorPhoneExists", "Field error[1] code = " + ((FieldError) formErrors.getFieldErrors().get(1)).getCode());
	}

	class MockTangerineFormController extends TangerineFormController {
		
		@Override
		protected AbstractEntity findEntity(HttpServletRequest request) {
			return null;
		}

		@Override
		public void convertFormToDomain(HttpServletRequest request, TangerineForm form, Map<String, Object> paramMap) throws Exception {
			super.convertFormToDomain(request, form, paramMap);
		}

		@Override
		public void bindDomainErrorsToForm(BindException formErrors, BindException domainErrors) {
			super.bindDomainErrorsToForm(formErrors, domainErrors);
		}
	}
}