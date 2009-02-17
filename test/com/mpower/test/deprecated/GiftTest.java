package com.mpower.test.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.AuditService;
import com.mpower.service.GiftService;
import com.mpower.test.BaseTest;
import com.mpower.test.dataprovider.GiftDataProvider;

public class GiftTest extends BaseTest {

    @Autowired
    private GiftService giftService;

    @Autowired
    private AuditService auditService;

    @Test(dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
    public void createGift(Site site, Person person, Gift gift) {
        giftService.setAuditService(auditService);
        em.getTransaction().begin();
        em.persist(site);
        person.setSite(site);
        em.persist(person);
        gift.setPerson(person);
        gift = giftService.maintainGift(gift);
        Long giftId = gift.getId();
        em.remove(em.find(Gift.class, giftId));
        em.remove(person);
        em.remove(site);
        em.getTransaction().rollback();
    }
}
