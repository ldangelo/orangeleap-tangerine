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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 1:04:03 PM
 */
public class SiteServiceDataProvider {

	@DataProvider(name = "setupEntityDefault")
	public static Object[][] createParameters() {
		Gift gift = new Gift();
		gift.setComments("My gift to you");
		gift.setPaymentType("Cash");
		gift.setAmount(new BigDecimal("25"));
		gift.setId(100L);
		gift.addCustomFieldValue("reference", "3");
		gift.addCustomFieldValue("momma", "Yo Mama");
		gift.addDistributionLine(new DistributionLine());
		Constituent constituent = new Constituent();
		constituent.setFirstName("Joe");
		constituent.setLastName("Blow");
		gift.setConstituent(constituent);

		return new Object[][] { new Object[] { gift } };
	}
}