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

package com.orangeleap.tangerine.test.service.impl;

import com.orangeleap.tangerine.domain.customization.MessageResource;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.service.customization.MessageServiceImpl;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.type.MessageResourceType;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageServiceImplTest extends BaseTest {

	@Autowired
	private MessageService messageService;

	@Test
	public void testLoadAllMessageResources() {
		Map<String, MessageResource> resourceMap = ((MessageServiceImpl) messageService).loadAllMessageResources();
		Assert.assertNotNull(resourceMap);
		Assert.assertEquals(2, resourceMap.size());
		Assert.assertEquals(resourceMap.get("FIELD_VALIDATION.fieldRequiredFailure.gift.amount.en_US").getMessageValue(), "Amount is required for COMPANY1");
		Assert.assertEquals(resourceMap.get("FIELD_VALIDATION.fieldValidationFailure.gift.amount.en_US").getMessageValue(), "Amount is incorrect");
	}

	@Test
	public void testLookupMessage() {
		Assert.assertEquals(messageService.lookupMessage(MessageResourceType.FIELD_VALIDATION, "fieldRequiredFailure.gift.amount", Locale.US), "Amount is required for COMPANY1");
		Assert.assertEquals(messageService.lookupMessage(MessageResourceType.FIELD_VALIDATION, "fieldValidationFailure.gift.amount", Locale.US), "Amount is incorrect");		
	}
}
