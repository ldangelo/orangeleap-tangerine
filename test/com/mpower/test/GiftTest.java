package com.mpower.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.AuditService;
import com.mpower.service.GiftService;
import com.mpower.test.dataprovider.GiftDataProvider;

public class GiftTest extends BaseTest {

    private EntityManagerFactory emf;

    private GiftService giftService;

    private AuditService auditService;

    @Test(dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
    public void createGift(Site site, Person person, Gift gift) {
        giftService.setAuditService(auditService);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        person.setSite(site);
        em.persist(person);
        gift.setPerson(person);
        gift = giftService.maintainGift(gift);
        Long giftId = gift.getId();
        em.getTransaction().commit();
        em.remove(em.find(Gift.class, giftId));
        em.remove(person);
        em.remove(site);
    }

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    @BeforeClass
    public void setup() {
        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        giftService = (GiftService) applicationContext.getBean("giftService");
        auditService = (AuditService) applicationContext.getBean("auditService");
    }
}
