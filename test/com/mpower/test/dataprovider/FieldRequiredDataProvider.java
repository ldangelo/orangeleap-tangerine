package com.mpower.test.dataprovider;

import org.testng.annotations.DataProvider;

import com.mpower.domain.Site;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.customization.FieldRequired;
import com.mpower.domain.customization.SectionField;

public class FieldRequiredDataProvider {

    @DataProvider(name = "testFieldRequired")
    public static Object[][] provideFieldRequired() {
        Site site1 = new Site();
        site1.setName("site 1");

        SectionField sectionField = new SectionField();
        sectionField.setSectionName("section 1");

        FieldDefinition fieldDefinition = new FieldDefinition();
        fieldDefinition.setFieldName("field 1");
        fieldDefinition.setId("field 1");
        fieldDefinition.setDefaultLabel("field 1");

        FieldRequired defaultField = new FieldRequired();
        defaultField.setSectionName(sectionField.getSectionName());
        defaultField.setFieldDefinitionId(fieldDefinition.getFieldName());
        defaultField.setRequired(false);

        FieldRequired modifiedField = new FieldRequired();
        modifiedField.setSectionName(sectionField.getSectionName());
        modifiedField.setFieldDefinitionId(fieldDefinition.getFieldName());
        modifiedField.setRequired(true);

        return new Object[][] { new Object[] { site1, fieldDefinition, sectionField, defaultField, modifiedField, true } };
    }
}
