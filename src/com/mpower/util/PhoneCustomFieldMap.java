package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.Phone;
import com.mpower.domain.PhoneCustomField;

public class PhoneCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Phone phone;

    public static PhoneCustomFieldMap buildCustomFieldMap(List<PhoneCustomField> phoneCustomFieldList, Phone phone) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (PhoneCustomField pcf : phoneCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        PhoneCustomFieldMap cfm = new PhoneCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.phone = phone;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        phone.getPhoneCustomFields().add(new PhoneCustomField(phone, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
