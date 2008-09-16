package com.mpower.test;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.AuditService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;
import com.mpower.test.dataprovider.PaymentSourceDataProvider;

public class PaymentSourceTest extends BaseTest {

    private PaymentSourceService paymentSourceService;

    private AuditService auditService;

    @Test(groups = { "createPaymentSource" }, dataProvider = "setupPaymentSource", dataProviderClass = PaymentSourceDataProvider.class)
    public void createPaymentSource(Site site, Person person, PaymentSource ps) {
        paymentSourceService.setAuditService(auditService);
        em.getTransaction().begin();
        em.persist(site);
        person.setSite(site);
        em.persist(person);
        ps.setPerson(person);
        int begin = paymentSourceService.readPaymentSources(person.getId()).size();
        ps = paymentSourceService.savePaymentSource(ps);
        int end = paymentSourceService.readPaymentSources(person.getId()).size();
        logger.debug("change = " + (end - begin));
        assert (end - begin) == 1;
        em.getTransaction().commit();
    }

    @Test(groups = { "deletePaymentSource" }, dependsOnGroups = { "createPaymentSource" })
    public void deletePaymentSource() {
        em.getTransaction().begin();
        paymentSourceService.setAuditService(auditService);
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllPeople();
        for (Person person : persons) {
            List<PaymentSource> sources = paymentSourceService.readPaymentSources(person.getId());
            for (PaymentSource ps : sources) {
                ps = em.getReference(PaymentSource.class, ps.getId());
                paymentSourceService.deletePaymentSource(ps);
            }
            // assert paymentSourceService.readPaymentSources(person.getId()).size() == 0;
        }
        em.getTransaction().commit();
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @BeforeClass
    public void setup() {
        getEntityManager();
        paymentSourceService = (PaymentSourceService) applicationContext.getBean("paymentSourceService");
        auditService = (AuditService) applicationContext.getBean("auditService");
    }
}
