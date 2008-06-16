package com.mpower.domain.util;

import java.util.ArrayList;
import java.util.List;

import com.mpower.domain.entity.CustomField;
import com.mpower.domain.entity.Person;
import com.mpower.domain.entity.PersonCustomField;

@SuppressWarnings("unchecked")
public class CustomFieldMap extends AbstractInstanceValuesMap {

    private static final long serialVersionUID = 1L;

    private Person person;

    public static CustomFieldMap buildCustomFieldMap(List<PersonCustomField> personCustomFieldList, Person person) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (PersonCustomField pcf: personCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        CustomFieldMap cfm = new CustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.person = person;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        person.getPersonCustomFields().add(new PersonCustomField(person, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
