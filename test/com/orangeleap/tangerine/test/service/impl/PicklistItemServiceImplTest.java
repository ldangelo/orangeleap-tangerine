package com.orangeleap.tangerine.test.service.impl;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.impl.PicklistItemServiceImpl;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.PicklistItemServiceDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class PicklistItemServiceImplTest extends BaseTest {

    @Autowired
    private PicklistItemService picklistItemService;

	@Test(dataProvider = "sameOrderItems1", dataProviderClass = PicklistItemServiceDataProvider.class)
	public void testResolveItemOrder1(final Picklist picklist, final List<PicklistItem> items) throws Exception {
		invokeResolveItemOrder(picklist, items);
		Assert.assertEquals(items.get(0).getId(), new Long(200L));
        Assert.assertEquals(items.get(1).getId(), new Long(100L));
        Assert.assertEquals(items.get(2).getId(), new Long(300L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
	}

    @Test(dataProvider = "differentOrderItems", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder2(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(300L));
        Assert.assertEquals(items.get(1).getId(), new Long(100L));
        Assert.assertEquals(items.get(2).getId(), new Long(200L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
    }

    @Test(dataProvider = "sameOrderItems2", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder3(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(200L));
        Assert.assertEquals(items.get(1).getId(), new Long(100L));
        Assert.assertEquals(items.get(2).getId(), new Long(300L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
    }

    @Test(dataProvider = "sameOrderItems4", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder5(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(100L));
        Assert.assertEquals(items.get(1).getId(), new Long(200L));
        Assert.assertEquals(items.get(2).getId(), new Long(300L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
    }

    @Test(dataProvider = "sameOrderItems5", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder6(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(200L));
        Assert.assertEquals(items.get(1).getId(), new Long(300L));
        Assert.assertEquals(items.get(2).getId(), new Long(100L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
    }

    @Test(dataProvider = "sameOrderNewItems1", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder7(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(200L));
        Assert.assertEquals(items.get(1).getId(), new Long(0L));
        Assert.assertEquals(items.get(2).getId(), new Long(300L));
        Assert.assertEquals(items.get(3).getId(), new Long(100L));

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
        Assert.assertEquals(items.get(3).getItemOrder(), new Integer(4));
    }

    @Test(dataProvider = "sameOrderNewItems2", dataProviderClass = PicklistItemServiceDataProvider.class)
    public void testResolveItemOrder8(final Picklist picklist, final List<PicklistItem> items) throws Exception {
        invokeResolveItemOrder(picklist, items);
        Assert.assertEquals(items.get(0).getId(), new Long(0L));
        Assert.assertEquals(items.get(1).getId(), new Long(0L));
        Assert.assertEquals(items.get(2).getId(), new Long(100L));
        Assert.assertEquals(items.get(3).getId(), new Long(300L));
        Assert.assertEquals(items.get(4).getId(), new Long(200L));

        Assert.assertEquals(items.get(0).getDefaultDisplayValue(), "blarg");
        Assert.assertEquals(items.get(1).getDefaultDisplayValue(), "sss");

        Assert.assertEquals(items.get(0).getItemOrder(), new Integer(1));
        Assert.assertEquals(items.get(1).getItemOrder(), new Integer(2));
        Assert.assertEquals(items.get(2).getItemOrder(), new Integer(3));
        Assert.assertEquals(items.get(3).getItemOrder(), new Integer(4));
        Assert.assertEquals(items.get(4).getItemOrder(), new Integer(5));
    }

    private Object invokeResolveItemOrder(Picklist picklist, List<PicklistItem> items) throws Exception {
        Method method = ((PicklistItemServiceImpl) picklistItemService).getClass().getDeclaredMethod("resolveItemOrder", Picklist.class, List.class);
        method.setAccessible(true);
        return method.invoke(picklistItemService, picklist, items);
    }
}