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

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class PicklistItemServiceDataProvider {

	@DataProvider(name = "sameOrderItems1")
	public static Object[][] createParameters() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

		return new Object[][] { new Object[] { picklist, modifiedItems } };
	}

    @DataProvider(name = "differentOrderItems")
    public static Object[][] createParameters2() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(3);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderItems2")
    public static Object[][] createParameters3() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(4);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(4);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(4);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderItems3")
    public static Object[][] createParameters4() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderItems4")
    public static Object[][] createParameters5() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderItems5")
    public static Object[][] createParameters6() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderNewItems1")
    public static Object[][] createParameters7() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(0L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("blarg");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }

    @DataProvider(name = "sameOrderNewItems2")
    public static Object[][] createParameters8() {
        Picklist picklist = new Picklist();
        List<PicklistItem> existingItems = new ArrayList<PicklistItem>();
        PicklistItem item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(2);
        existingItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(3);
        existingItems.add(item);

        picklist.setPicklistItems(existingItems);

        List<PicklistItem> modifiedItems = new ArrayList<PicklistItem>();
        item = new PicklistItem();
        item.setId(200L);
        item.setItemOrder(3);
        item.setDefaultDisplayValue("abba");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(100L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("croak");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(300L);
        item.setItemOrder(2);
        item.setDefaultDisplayValue("dadda");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(0L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("sss");
        modifiedItems.add(item);

        item = new PicklistItem();
        item.setId(0L);
        item.setItemOrder(1);
        item.setDefaultDisplayValue("blarg");
        modifiedItems.add(item);

        return new Object[][] { new Object[] { picklist, modifiedItems } };
    }
}