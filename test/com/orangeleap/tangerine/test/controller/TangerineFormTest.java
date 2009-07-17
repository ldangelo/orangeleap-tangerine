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
import com.orangeleap.tangerine.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: alexlo
 * Date: Jul 16, 2009
 * Time: 10:14:35 AM
 */
public class TangerineFormTest extends BaseTest {

	@Test
	public void testIsGridField() throws Exception {
		Assert.assertEquals("gift.distributionLines", TangerineForm.getGridCollectionName("gift.distributionLines[1].amount"),
				"Expected gift.distributionLines, not " + TangerineForm.getGridCollectionName("gift.distributionLines[1].amount"));
		Assert.assertEquals("distributionLines", TangerineForm.getGridCollectionName("distributionLines[3].amount"),
				"Expected distributionLines, not " + TangerineForm.getGridCollectionName("distributionLines[3].amount"));
		Assert.assertEquals("gift.distributionLines", TangerineForm.getGridCollectionName("gift.distributionLines[99]"),
				"Expected gift.distributionLines, not " + TangerineForm.getGridCollectionName("gift.distributionLines[99]"));
		Assert.assertEquals("distributionLines", TangerineForm.getGridCollectionName("distributionLines[0]"),
				"Expected distributionLines, not "+ TangerineForm.getGridCollectionName("distributionLines[0]"));
		Assert.assertNull(TangerineForm.getGridCollectionName("distributionLines"));
		Assert.assertNull(TangerineForm.getGridCollectionName("distributionLines[]"));
		Assert.assertNull(TangerineForm.getGridCollectionName("distributionLines[x]"));
		Assert.assertNull(TangerineForm.getGridCollectionName("distributionLines[x2]"));
		Assert.assertNull(TangerineForm.getGridCollectionName("distributionLines[2x]"));
		Assert.assertNull(TangerineForm.getGridCollectionName("[]distributionLines"));
		Assert.assertNull(TangerineForm.getGridCollectionName("[0]distributionLines"));
		Assert.assertNull(TangerineForm.getGridCollectionName("gift.amount"));
	}
}
