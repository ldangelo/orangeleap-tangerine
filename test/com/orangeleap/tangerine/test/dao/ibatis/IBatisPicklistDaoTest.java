package com.orangeleap.tangerine.test.dao.ibatis;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.PicklistDao;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.EntityType;

public class IBatisPicklistDaoTest extends AbstractIBatisTest {

    private PicklistDao picklistDao;

    @BeforeMethod
    public void setup() {
        picklistDao = (PicklistDao)super.applicationContext.getBean("picklistDAO");
    }

    @Test(groups = { "testMaintainPicklist" }, dependsOnGroups = { "testReadPicklist" })
    public void testMaintainPicklist() throws Exception {
        Picklist picklist = picklistDao.readPicklistByFieldName("creditCardType", EntityType.paymentSource);
        assert picklist != null;
        assert "creditCardType".equals(picklist.getPicklistNameId());
        assert "creditCardType".equals(picklist.getPicklistName());
        assert picklist.getSite() != null && "company1".equals(picklist.getSite().getName());
        assert picklist.isMultiselect() == true;
        assert EntityType.paymentSource.equals(picklist.getEntityType());
        assert picklist.getPicklistItems() != null && picklist.getPicklistItems().size() == 4;

        for (PicklistItem item : picklist.getPicklistItems()) {
            if (item.getItemName().equals("Visa")) {
                assert "Visa".equals(item.getDefaultDisplayValue());
                assert item.getReferenceValue() == null;
            }
        }

        picklist.setPicklistName("myCreditCardType");
        picklist.setMultiselect(false);
        for (PicklistItem item : picklist.getPicklistItems()) {
            if (item.getItemName().equals("Visa")) {
                item.setDefaultDisplayValue("Visa Credit Card");
                item.setReferenceValue("li:has(.visa)");
            }
        }
        
        picklistDao.maintainPicklist(picklist);
        Picklist readPicklist = picklistDao.readPicklistByFieldName("creditCardType", EntityType.paymentSource);
        assert readPicklist == null;
        readPicklist = picklistDao.readPicklistByFieldName("myCreditCardType", EntityType.paymentSource);
        assert readPicklist != null;
        assert "creditCardType".equals(readPicklist.getPicklistNameId());
        assert "myCreditCardType".equals(readPicklist.getPicklistName());
        assert readPicklist.getSite() != null && "company1".equals(readPicklist.getSite().getName());
        assert readPicklist.isMultiselect() == false;
        assert EntityType.paymentSource.equals(readPicklist.getEntityType());
        assert readPicklist.getPicklistItems() != null && readPicklist.getPicklistItems().size() == 4;

        for (PicklistItem readItem : readPicklist.getPicklistItems()) {
            assert readItem.isInactive() == false;
            assert readItem.getSuppressReferenceValue() == null;
            if (readItem.getItemName().equals("Visa")) {
                assert "Visa Credit Card".equals(readItem.getDefaultDisplayValue());
                "li:has(.visa)".equals(readItem.getReferenceValue());
                assert 1 == readItem.getItemOrder();
            }
            else if (readItem.getItemName().equals("Master Card")) {
                assert "Master Card".equals(readItem.getDefaultDisplayValue());
                assert 2 == readItem.getItemOrder();
                assert readItem.getReferenceValue() == null;
            }
            else if (readItem.getItemName().equals("American Express")) {
                assert "American Express".equals(readItem.getDefaultDisplayValue());
                assert 3 == readItem.getItemOrder();
                assert readItem.getReferenceValue() == null;
            }
            else if (readItem.getItemName().equals("Discover")) {
                assert "Discover".equals(readItem.getDefaultDisplayValue());
                assert 4 == readItem.getItemOrder();
                assert readItem.getReferenceValue() == null;
            }
        }
    }
    
    @Test(groups = { "testReadPicklist" })
    public void testReadPicklistById() throws Exception {
        Picklist picklist = picklistDao.readPicklistByNameId("constituentIndividualRoles");
        assert picklist != null;
        assert "constituentIndividualRoles".equals(picklist.getPicklistNameId());
        assert "constituentIndividualRoles".equals(picklist.getPicklistName());
        assert picklist.getSite() != null && "company1".equals(picklist.getSite().getName());
        assert picklist.isMultiselect() == false;
        assert picklist.getEntityType() == null;
        
        assert picklist.getPicklistItems() != null && picklist.getPicklistItems().size() == 10;
        for (PicklistItem item : picklist.getPicklistItems()) {
            
            if ("donor".equals(item.getItemName())) {
                assert "Donor".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-donor)".equals(item.getReferenceValue());
                assert 100 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("sponsor".equals(item.getItemName())) {
                assert "Sponsor".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-sponsor)".equals(item.getReferenceValue());
                assert 200 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("affiliate".equals(item.getItemName())) {
                assert "Affiliate".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-affiliate)".equals(item.getReferenceValue());
                assert 300 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("volunteer".equals(item.getItemName())) {
                assert "Volunteer".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-volunteer)".equals(item.getReferenceValue());
                assert 400 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("member".equals(item.getItemName())) {
                assert "Member".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-member)".equals(item.getReferenceValue());
                assert 500 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("prospect".equals(item.getItemName())) {
                assert "Prospect".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-prospect)".equals(item.getReferenceValue());
                assert 600 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("alumni".equals(item.getItemName())) {
                assert "Alumni".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-alumni)".equals(item.getReferenceValue());
                assert 700 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("headofhousehold".equals(item.getItemName())) {
                assert "Head of Household".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-headofhousehold)".equals(item.getReferenceValue());
                assert 800 == item.getItemOrder();
                assert item.isInactive() == false;
                assert "li:has(.ea-not.headofhousehold)".equals(item.getSuppressReferenceValue());
            }
            else if ("contact".equals(item.getItemName())) {
                assert "Contact".equals(item.getDefaultDisplayValue());
                assert item.getReferenceValue() == null;
                assert 900 == item.getItemOrder();
                assert item.isInactive() == false;
                assert item.getSuppressReferenceValue() == null;
            }
            else if ("user".equals(item.getItemName())) {
                assert "User".equals(item.getDefaultDisplayValue());
                assert "li:has(.ea-user)".equals(item.getReferenceValue());
                assert 1000 == item.getItemOrder();
                assert item.isInactive() == true;
                assert item.getSuppressReferenceValue() == null;
            }
        }
    }
    
    @Test(groups = { "testReadPicklist" })
    public void testReadPicklistByFieldName() throws Exception {
        Picklist picklist = picklistDao.readPicklistByFieldName("creditCardType", EntityType.gift);
        assert picklist == null;
        
        picklist = picklistDao.readPicklistByFieldName("creditCardType", EntityType.paymentSource);
        assert picklist != null;
        assert "creditCardType".equals(picklist.getPicklistNameId());
        assert "creditCardType".equals(picklist.getPicklistName());
        assert picklist.getSite() != null && "company1".equals(picklist.getSite().getName());
        assert picklist.isMultiselect() == true;
        assert EntityType.paymentSource.equals(picklist.getEntityType());
        assert picklist.getPicklistItems() != null && picklist.getPicklistItems().size() == 4;
    }
    
    @Test(groups = { "testReadPicklist" })
    public void testListPicklists() throws Exception {
        List<Picklist> lists = picklistDao.listPicklists();
        assert lists != null && lists.size() > 0;
        for (Picklist picklist : lists) {
            if ("creditCardType".equals(picklist.getId())) {
                assert picklist.getPicklistItems() != null && picklist.getPicklistItems().size() == 4;
            }
            else if ("constituentIndividualRoles".equals(picklist.getId())) {
                assert picklist.getPicklistItems() != null && picklist.getPicklistItems().size() == 10;
            }
        } 
    }
    
    @Test(groups = { "testReadPicklistItem" })
    public void testReadPicklistItemById() throws Exception {
        PicklistItem item = picklistDao.readPicklistItemById(7000L);
        assert item != null;
        assert "Alumni".equals(item.getDefaultDisplayValue());
        assert "li:has(.ea-alumni)".equals(item.getReferenceValue());
        assert 700 == item.getItemOrder();
        assert item.isInactive() == false;
        assert item.getSuppressReferenceValue() == null;
    }
}
