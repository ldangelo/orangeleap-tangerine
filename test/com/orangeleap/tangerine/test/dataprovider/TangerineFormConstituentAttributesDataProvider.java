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
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 1:04:03 PM
 */
public class TangerineFormConstituentAttributesDataProvider {

	@DataProvider(name = "setupExistingPaymentSourceTangerineForm")
	public static Object[][] createExistingParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Constituent constituent = new Constituent();
		constituent.setId(1L);
		constituent.setFirstName("firstname");
		constituent.setLastName("lastname");

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setId(1L);
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
		addToMap(request, paramMap, "paymentType", "Credit Card");
		addToMap(request, paramMap, "paymentSource.id", "1");
		addToMap(request, paramMap, "paymentSource.creditCardHolderName", "Big Brown One");
		addToMap(request, paramMap, "paymentSource.creditCardSecurityCode", "202");
		addToMap(request, paramMap, "customFieldMap[reference].value", "Joe Blow");
		addToMap(request, paramMap, "customFieldMap[daddyo].value", "787");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	@DataProvider(name = "setupNonePaymentSourceTangerineForm")
	public static Object[][] createNoneParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Constituent constituent = new Constituent();
		constituent.setId(1L);
		constituent.setFirstName("firstname");
		constituent.setLastName("lastname");

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setId(1L);
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
		addToMap(request, paramMap, "paymentSource.id", "-1");
		addToMap(request, paramMap, "customFieldMap[reference].value", "Joe Blow");
		addToMap(request, paramMap, "customFieldMap[daddyo].value", "787");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	@DataProvider(name = "setupNewPaymentSourceTangerineForm")
	public static Object[][] createNewParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Constituent constituent = new Constituent();
		constituent.setId(1L);
		constituent.setFirstName("firstname");
		constituent.setLastName("lastname");

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setId(1L);
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
		addToMap(request, paramMap, "paymentSource.id", "0");
		addToMap(request, paramMap, "paymentType", "ACH");
		addToMap(request, paramMap, "paymentSource.achHolderName", "Big Brown One");
		addToMap(request, paramMap, "paymentSource.achRoutingNumber", "12100005");
		addToMap(request, paramMap, "paymentSource.achAccountNumber", "8");
		addToMap(request, paramMap, "customFieldMap[reference].value", "Joe Blow");
		addToMap(request, paramMap, "customFieldMap[daddyo].value", "787");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	private static void addToMap(MockHttpServletRequest request, Map<String, Object> paramMap, String key, Object value) {
		request.setParameter(TangerineForm.escapeFieldName(key), value.toString());
		paramMap.put(TangerineForm.escapeFieldName(key), value);
	}
}