package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.Email;
import com.mpower.domain.EmailCustomField;

@Deprecated
public class EmailCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Email email;

    public static EmailCustomFieldMap buildCustomFieldMap(List<EmailCustomField> emailCustomFieldList, Email email) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (EmailCustomField pcf : emailCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        EmailCustomFieldMap cfm = new EmailCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.email = email;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        email.getEmailCustomFields().add(new EmailCustomField(email, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
