package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.CommunicationHistory;
import com.mpower.domain.CommunicationHistoryCustomField;

public class CommunicationHistoryCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    private CommunicationHistory communicationHistory;

    public static CommunicationHistoryCustomFieldMap buildCustomFieldMap(List<CommunicationHistoryCustomField> communicationHistoryCustomFieldList, CommunicationHistory communicationHistory) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (CommunicationHistoryCustomField pcf: communicationHistoryCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        CommunicationHistoryCustomFieldMap cfm = new CommunicationHistoryCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.communicationHistory = communicationHistory;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        communicationHistory.getCommunicationHistoryCustomFields().add(new CommunicationHistoryCustomField(communicationHistory, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
