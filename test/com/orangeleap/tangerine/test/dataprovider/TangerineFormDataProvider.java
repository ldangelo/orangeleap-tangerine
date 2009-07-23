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

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.test.controller.FakeTestArray;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 1:04:03 PM
 */
public class TangerineFormDataProvider {

	@DataProvider(name = "setupTangerineForm")
	public static Object[][] createTangerineFormParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Constituent constituent = new Constituent();
		constituent.setFirstName("firstname");
		constituent.setLastName("lastname");

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setCreditCardExpirationMonth(11);
		paymentSource.setCreditCardExpirationYear(2010);
		paymentSource.setCreditCardHolderName("John Q. Test");
		paymentSource.setCreditCardNumber("4111111111111111");
		paymentSource.setPaymentType("Credit Card");

		Gift gift = new Gift();
		gift.setComments("My gift to you");
		gift.setPaymentType("Cash");
		gift.setAmount(new BigDecimal("25"));
		gift.setConstituent(constituent);
		gift.setId(100L);
		gift.setPaymentSource(paymentSource);
		gift.addCustomFieldValue("reference", "3");
		gift.addCustomFieldValue("momma", "Yo Mama");

		TangerineForm form = new TangerineForm();
		form.setDomainObject(gift);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		addToMap(request, paramMap, "amount", "45.99");
		addToMap(request, paramMap, "dummyProperty", "foo");
		addToMap(request, paramMap, "postedDate", "01/01/1999");
		addToMap(request, paramMap, "posted", "true");
		addToMap(request, paramMap, "paymentType", "Check");
		addToMap(request, paramMap, "checkNumber", "111");
		addToMap(request, paramMap, "customFieldMap[reference].value", "Joe Blow");
		addToMap(request, paramMap, "customFieldMap[daddyo].value", "787");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	@DataProvider(name = "setupTestArrayForm")
	public static Object[][] createTestArrayParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		FakeTestArray array = new FakeTestArray();

		TangerineForm form = new TangerineForm();
		form.setDomainObject(array);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		addToMap(request, paramMap, "stringArray", "a");
		addToMap(request, paramMap, "stringArray", "b");
		addToMap(request, paramMap, "intArray", "1");
		addToMap(request, paramMap, "intArray", "2");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	@DataProvider(name = "setupBindErrors")
	public static Object[][] createBindErrorParameters() {
		Gift gift = new Gift();
		BindException domainErrors = new BindException(gift, "gift");
		domainErrors.rejectValue("amount", "exceptionHeading");
		domainErrors.rejectValue("customFieldMap[reference]", "errorPhoneExists");
		domainErrors.reject("errorMaxReminders");

		TangerineForm form = new TangerineForm();
		form.setDomainObject(gift);
		BindException formErrors = new BindException(form, "form");

		return new Object[][] { new Object[] { formErrors, domainErrors } };
	}
	
	private static void addToMap(MockHttpServletRequest request, Map<String, Object> paramMap, String key, Object value) {
		request.addParameter(TangerineForm.escapeFieldName(key), value.toString());
		paramMap.put(TangerineForm.escapeFieldName(key), value);
	}
}
