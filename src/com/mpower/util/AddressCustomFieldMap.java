package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Address;
import com.mpower.domain.AddressCustomField;
import com.mpower.domain.CustomField;

public class AddressCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Address address;

    public static AddressCustomFieldMap buildCustomFieldMap(List<AddressCustomField> addressCustomFieldList, Address address) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (AddressCustomField pcf : addressCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        AddressCustomFieldMap cfm = new AddressCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.address = address;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        address.getAddressCustomFields().add(new AddressCustomField(address, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
