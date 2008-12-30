package com.mpower.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.service.AuditService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;
import com.mpower.test.dataprovider.PaymentSourceDataProvider;

public class PaymentSourceTest extends BaseTest {

    @Autowired
    private PaymentSourceService paymentSourceService;

    @Autowired
    private AuditService auditService;

    private final List<String> siteIds = new ArrayList<String>();

    private final List<Long> personIds = new ArrayList<Long>();

    private final List<Long> paymentSourceIds = new ArrayList<Long>();

    @Test(groups = { "createPaymentSource" }, dataProvider = "setupPaymentSource", dataProviderClass = PaymentSourceDataProvider.class)
    public void createPaymentSource(Site site, Person person, PaymentSource ps) {
        paymentSourceService.setAuditService(auditService);
        em.getTransaction().begin();
        em.persist(site);
        siteIds.add(site.getName());
        person.setSite(site);
        em.persist(person);
        personIds.add(person.getId());
        ps.setPerson(person);
        int begin = paymentSourceService.readPaymentSources(person.getId()).size();
        ps = paymentSourceService.savePaymentSource(ps);
        paymentSourceIds.add(ps.getId());
        int end = paymentSourceService.readPaymentSources(person.getId()).size();
        logger.debug("change = " + (end - begin));
        assert end - begin == 1;
        em.getTransaction().commit();
    }

    @Test(groups = { "checkPaymentSource" }, dependsOnGroups = { "createPaymentSource" })
    public void checkForExistingPaymentSources() {
        paymentSourceService.setAuditService(auditService);
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllPeople();
        Long personId = null;
        for (Person person : persons) {
            if ("createPaymentSourceLastName-4".equals(person.getLastName())) {
                personId = person.getId();
                break;
            }
        }
        assert personId != null;
        assert paymentSourceService.findPaymentSourceProfile(personId, "MyProfile") != null;
        assert paymentSourceService.findPaymentSourceProfile(personId, "MyProfile2") == null;
        assert paymentSourceService.findPaymentSourceProfile(personId.longValue() + 1, "MyProfile") == null;
    }

    @Test(groups = { "deletePaymentSource" }, dependsOnGroups = { "createPaymentSource", "checkPaymentSource" })
    public void inactivatePaymentSources() {
        paymentSourceService.setAuditService(auditService);
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllPeople();
        for (Person person : persons) {
            List<PaymentSource> sources = paymentSourceService.readPaymentSources(person.getId());
            for (PaymentSource ps : sources) {
                ps = em.getReference(PaymentSource.class, ps.getId());
                ps.setInactive(true);
                paymentSourceService.savePaymentSource(ps);
            }
            logger.debug("size = " + paymentSourceService.readPaymentSources(person.getId()).size());
            assert paymentSourceService.readPaymentSources(person.getId()).size() == 0;
        }
        for (Long personId : personIds) {
            em.remove(em.find(Person.class, personId));
        }
        for (String siteId : siteIds) {
            em.remove(em.find(Site.class, siteId));
        }
    }
}
