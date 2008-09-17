package com.mpower.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mpower.domain.Site;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.customization.FieldService;
import com.mpower.test.dataprovider.FieldRequiredDataProvider;

public class FieldTest extends BaseTest {

    @Autowired
    private FieldService fieldService;

    @Test(dataProvider = "testFieldRequired", dataProviderClass = FieldRequiredDataProvider.class)
    public void insertRequiredField(Site site, FieldDefinition fieldDefinition, SectionDefinition sectionDefinition, SectionField sectionField, FieldRequired defaultField, FieldRequired modifiedField, boolean expectedValue) {
        em.getTransaction().begin();
        em.persist(site);
        String siteName = site.getName();

        fieldDefinition.setSite(site);
        em.persist(fieldDefinition);

        em.persist(sectionDefinition);

        sectionField.setFieldDefinition(fieldDefinition);
        em.persist(sectionField);
        Long sectionFieldId = sectionField.getId();

        em.persist(defaultField);

        modifiedField.setSiteName(siteName);
        em.persist(modifiedField);

        assert fieldService.lookupFieldRequired(siteName, em.find(SectionField.class, sectionFieldId)) == expectedValue;
        em.getTransaction().rollback();
    }
}
