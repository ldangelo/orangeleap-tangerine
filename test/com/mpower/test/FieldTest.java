package com.mpower.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mpower.domain.Site;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.customization.FieldService;
import com.mpower.test.dataprovider.FieldRequiredDataProvider;

public class FieldTest extends BaseTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    private FieldService fieldService;

    @Test(dataProvider = "testFieldRequired", dataProviderClass = FieldRequiredDataProvider.class)
    public void insertRequiredField(Site site, FieldDefinition fieldDefinition, SectionField sectionField, FieldRequired defaultField, FieldRequired modifiedField, boolean expectedValue) {
        em.getTransaction().begin();
        em.persist(site);
        String siteName = site.getName();

        fieldDefinition.setSite(site);
        em.persist(fieldDefinition);

        sectionField.setFieldDefinition(fieldDefinition);
        sectionField.setSite(site);
        em.persist(sectionField);
        Long sectionFieldId = sectionField.getId();

        em.persist(defaultField);

        modifiedField.setSiteName(siteName);
        em.persist(modifiedField);

        assert fieldService.lookupFieldRequired(siteName, em.find(SectionField.class, sectionFieldId)) == expectedValue;
        em.getTransaction().rollback();
    }

    @BeforeClass
    public void setup() {
        emf = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        em = emf.createEntityManager();
        fieldService = (FieldService) applicationContext.getBean("fieldService");
    }
}
