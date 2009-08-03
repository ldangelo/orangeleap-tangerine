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
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 1:04:03 PM
 */
public class AbstractMutableGridDataProvider {

	@DataProvider(name = "setupAbstractDistroLineForm")
	public static Object[][] createDistroLineParameters() {
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
		addToMap(request, paramMap, "distributionLines[0].amount", "");
		addToMap(request, paramMap, "distributionLines[0].percentage", "");
		addToMap(request, paramMap, "distributionLines[0].projectCode", "");
		addToMap(request, paramMap, "distributionLines[0].customFieldMap[tribute].value", "");
		addToMap(request, paramMap, "distributionLines[1].amount", "45");
		addToMap(request, paramMap, "distributionLines[1].percentage", "98");
		addToMap(request, paramMap, "distributionLines[1].projectCode", "Trial");
		addToMap(request, paramMap, "distributionLines[1].customFieldMap[tribute].value", "inMemoryOf");
		addToMap(request, paramMap, "distributionLines[4].amount", ".99");
		addToMap(request, paramMap, "distributionLines[4].percentage", "2");
		addToMap(request, paramMap, "distributionLines[4].customFieldMap[onBehalfOf].value", "Fooey");
		addToMap(request, paramMap, "_distributionLines[4].customFieldMap[taxDeductible].value", "");
		addToMap(request, paramMap, "distributionLines[4].customFieldMap[taxDeductible].value", "true");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	@DataProvider(name = "setupAbstractGiftInKindDetailForm")
	public static Object[][] createDetailParameters() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Constituent constituent = new Constituent();
		constituent.setFirstName("firstname");
		constituent.setLastName("lastname");

		GiftInKind giftInKind = new GiftInKind();
		giftInKind.setFairMarketValue(new BigDecimal("25"));
		giftInKind.setConstituent(constituent);
		giftInKind.setId(100L);
		giftInKind.addCustomFieldValue("reference", "3");
		giftInKind.addCustomFieldValue("momma", "Yo Mama");

		TangerineForm form = new TangerineForm();
		form.setDomainObject(giftInKind);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		addToMap(request, paramMap, "fairMarketValue", "45.99");
		addToMap(request, paramMap, "dummyProperty", "foo");
		addToMap(request, paramMap, "motivationCode", "111");
		addToMap(request, paramMap, "customFieldMap[reference].value", "Joe Blow");
		addToMap(request, paramMap, "customFieldMap[daddyo].value", "787");
		addToMap(request, paramMap, "details[0].detailFairMarketValue", "");
		addToMap(request, paramMap, "details[0].description", "");
		addToMap(request, paramMap, "details[0].projectCode", "");
		addToMap(request, paramMap, "details[0].customFieldMap[tribute].value", "");
		addToMap(request, paramMap, "details[1].detailFairMarketValue", "45");
		addToMap(request, paramMap, "details[1].description", "dude");
		addToMap(request, paramMap, "details[1].projectCode", "Trial");
		addToMap(request, paramMap, "details[1].customFieldMap[tribute].value", "inMemoryOf");
		addToMap(request, paramMap, "details[4].detailFairMarketValue", ".99");
		addToMap(request, paramMap, "details[4].description", "2");
		addToMap(request, paramMap, "details[4].customFieldMap[onBehalfOf].value", "Fooey");
		addToMap(request, paramMap, "_details[4].customFieldMap[taxDeductible].value", "");
		addToMap(request, paramMap, "details[4].customFieldMap[taxDeductible].value", "true");

		return new Object[][] { new Object[] { request, form, paramMap } };
	}

	private static void addToMap(MockHttpServletRequest request, Map<String, Object> paramMap, String key, Object value) {
		request.setParameter(TangerineForm.escapeFieldName(key), value != null ? value.toString() : null);
		paramMap.put(TangerineForm.escapeFieldName(key), value);
	}
}