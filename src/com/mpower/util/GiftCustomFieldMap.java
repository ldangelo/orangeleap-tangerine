package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.Gift;
import com.mpower.domain.GiftCustomField;

@Deprecated
public class GiftCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    private Gift gift;

    public static GiftCustomFieldMap buildCustomFieldMap(List<GiftCustomField> giftCustomFieldList, Gift gift) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (GiftCustomField pcf : giftCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        GiftCustomFieldMap cfm = new GiftCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.gift = gift;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        gift.getCustomFields().add(new GiftCustomField(gift, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
