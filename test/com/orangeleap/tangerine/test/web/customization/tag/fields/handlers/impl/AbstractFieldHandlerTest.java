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

package com.orangeleap.tangerine.test.web.customization.tag.fields.handlers.impl;

import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AbstractFieldHandlerTest extends BaseTest {

	@Test
	public void testResolveUnescapedPrefixedFieldName() throws Exception {
	    Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "distributionLines.motivationCode"), "distributionLines.other_motivationCode");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "motivationCode"), "other_motivationCode");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "customFieldMap[individual.motivationCode]"), "customFieldMap[individual.other_motivationCode]");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "distributionLines.customFieldMap[motivationCode]"), "distributionLines.customFieldMap[other_motivationCode]");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "distributionLines.customFieldMap[individual.motivationCode]"), "distributionLines.customFieldMap[individual.other_motivationCode]");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "customFieldMap[individual.motivationCode].value"), "customFieldMap[individual.other_motivationCode].value");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "distributionLines.customFieldMap[motivationCode].value"), "distributionLines.customFieldMap[other_motivationCode].value");
		Assert.assertEquals(AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, "distributionLines.customFieldMap[individual.motivationCode].value"), "distributionLines.customFieldMap[individual.other_motivationCode].value");
	}
}
