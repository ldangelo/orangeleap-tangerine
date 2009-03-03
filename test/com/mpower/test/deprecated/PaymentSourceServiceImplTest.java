package com.mpower.test.deprecated;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.Site;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;
import com.mpower.test.BaseTest;
import com.mpower.test.dataprovider.PaymentSourceDataProvider;

public class PaymentSourceServiceImplTest extends BaseTest {

    @Autowired
    private PaymentSourceService paymentSourceService;

    private final List<String> siteIds = new ArrayList<String>();

    private final List<Long> personIds = new ArrayList<Long>();

    private final List<Long> paymentSourceIds = new ArrayList<Long>();

    @Test(groups = { "createPaymentSource" }, dataProvider = "setupPaymentSource", dataProviderClass = PaymentSourceDataProvider.class)
    public void createPaymentSource(Site site, Person person, PaymentSource ps) {
        em.getTransaction().begin();
        em.persist(site);
        siteIds.add(site.getName());
        person.setSite(site);
        em.persist(person);
        personIds.add(person.getId());
        ps.setPerson(person);
        int begin = paymentSourceService.readPaymentSources(person.getId()).size();
        ps = paymentSourceService.maintainPaymentSource(ps);
        paymentSourceIds.add(ps.getId());
        int end = paymentSourceService.readPaymentSources(person.getId()).size();
        logger.debug("change = " + (end - begin));
        assert end - begin == 1;
        em.getTransaction().commit();
    }

    @Test(groups = { "checkPaymentSource" }, dependsOnGroups = { "createPaymentSource" })
    public void checkForExistingPaymentSources() {
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllConstituentsBySite();
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

    @Test(groups = { "createEtcPaymentSource" }, dataProvider = "setupEtcPaymentSource", dataProviderClass = PaymentSourceDataProvider.class)
    public void createEtcPaymentSource(Site site, Person person, PaymentSource ps) {
        em.getTransaction().begin();
        em.persist(site);
        siteIds.add(site.getName());
        person.setSite(site);
        em.persist(person);
        personIds.add(person.getId());
        ps.setPerson(person);
        List <PaymentSource> old = paymentSourceService.readPaymentSources(person.getId());
        assert old != null;
        ps = paymentSourceService.maintainPaymentSource(ps);
        paymentSourceIds.add(ps.getId());
        List<PaymentSource> s = paymentSourceService.readPaymentSources(person.getId());
        assert s != null;
        em.getTransaction().commit();
    }

    @Test(groups = { "checkEtcPaymentSource" }, dependsOnGroups = { "createEtcPaymentSource" })
    public void testReadActivePaymentSourcesACHCreditCard() {
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllConstituentsBySite();
        Long personId = null;
        for (Person person : persons) {
            if ("createPaymentSourceLastName-5".equals(person.getLastName())) {
                personId = person.getId();
                break;
            }
        }
        assert personId != null;
        List<PaymentSource> sources = paymentSourceService.readActivePaymentSourcesACHCreditCard(personId);
        assert sources != null;
        assert sources.size() == 1;
        assert PaymentSource.CREDIT_CARD.equals(sources.get(0).getType());
    }

    @Test(groups = { "deletePaymentSource" }, dependsOnGroups = { "createPaymentSource", "checkPaymentSource" })
    public void inactivatePaymentSources() {
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        List<Person> persons = personService.readAllConstituentsBySite();
        for (Person person : persons) {
            List<PaymentSource> sources = paymentSourceService.readPaymentSources(person.getId());
            for (PaymentSource ps : sources) {
                ps = em.getReference(PaymentSource.class, ps.getId());
                ps.setInactive(true);
                paymentSourceService.maintainPaymentSource(ps);
            }
            logger.debug("size = " + paymentSourceService.readPaymentSources(person.getId()).size());
            assert paymentSourceService.readPaymentSources(person.getId()).size() == 0;
        }
        for (Long personId : personIds) {
            Person person = em.find(Person.class, personId);
            if (person != null) {
                em.remove(person);
            }
        }
        for (String siteId : siteIds) {
            Site site = em.find(Site.class, siteId);
            if (site != null) {
                em.remove(site);
            }
        }
    }
}
