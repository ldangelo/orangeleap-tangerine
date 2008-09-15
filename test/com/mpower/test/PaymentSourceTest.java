package com.mpower.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.AuditService;
import com.mpower.service.PaymentSourceService;
import com.mpower.test.dataprovider.PaymentSourceDataProvider;

public class PaymentSourceTest extends BaseTest {

    private EntityManagerFactory emf;

    private PaymentSourceService paymentSourceService;

    private AuditService auditService;

    @Test(groups = { "createPaymentSource" }, dataProvider = "setupPaymentSource", dataProviderClass = PaymentSourceDataProvider.class)
    public void createPaymentSource(Site site, Person person, PaymentSource ps) {
        paymentSourceService.setAuditService(auditService);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(site);
        person.setSite(site);
        em.persist(person);
        ps.setPerson(person);
        int begin = paymentSourceService.readPaymentSources(person.getId()).size();
        ps = paymentSourceService.savePaymentSource(ps);
        int end = paymentSourceService.readPaymentSources(person.getId()).size();
        assert (end - begin) == 1;
        em.getTransaction().commit();
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @BeforeClass
    public void setup() {
        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        paymentSourceService = (PaymentSourceService) applicationContext.getBean("paymentSourceService");
        auditService = (AuditService) applicationContext.getBean("auditService");
    }
}
