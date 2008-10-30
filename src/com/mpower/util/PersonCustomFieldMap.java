package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.Person;
import com.mpower.domain.PersonCustomField;

public class PersonCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    private Person person;

    public static PersonCustomFieldMap buildCustomFieldMap(List<PersonCustomField> personCustomFieldList, Person person) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (PersonCustomField pcf: personCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        PersonCustomFieldMap cfm = new PersonCustomFieldMap();
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
