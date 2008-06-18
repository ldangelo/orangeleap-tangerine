package com.mpower.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.entity.Gift;
import com.mpower.domain.entity.PaymentSource;
import com.mpower.domain.entity.Person;
import com.mpower.domain.entity.Site;
import com.mpower.service.GiftService;
import com.mpower.test.dataprovider.GiftDataProvider;

public class GiftTest extends BaseTest {

    private EntityManagerFactory emf;

    private GiftService giftService;

    @Test(dataProvider = "setupGift", dataProviderClass = GiftDataProvider.class)
    public void createGift(Site site, Person person, Gift gift, PaymentSource paymentSource) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        person.setSite(site);
        em.persist(person);
        paymentSource.setPerson(person);
        em.persist(paymentSource);
        gift.setPerson(person);
        gift.setPaymentSource(paymentSource);
        gift = giftService.maintainGift(gift);
        Long giftId = gift.getId();
        em.getTransaction().commit();
        em.remove(em.find(Gift.class, giftId));
        em.remove(paymentSource);
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
    }
}
