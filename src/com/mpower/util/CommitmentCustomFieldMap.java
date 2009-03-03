package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Commitment;
import com.mpower.domain.CommitmentCustomField;
import com.mpower.domain.CustomField;

@Deprecated
public class CommitmentCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private Commitment commitment;

    public static CommitmentCustomFieldMap buildCustomFieldMap(List<CommitmentCustomField> commitmentCustomFieldList, Commitment commitment) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (CommitmentCustomField pcf : commitmentCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        CommitmentCustomFieldMap cfm = new CommitmentCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.commitment = commitment;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        commitment.getCustomFields().add(new CommitmentCustomField(commitment, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
