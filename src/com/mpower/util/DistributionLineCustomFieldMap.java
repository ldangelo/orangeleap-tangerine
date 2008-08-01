package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.CustomField;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.DistributionLineCustomField;

public class DistributionLineCustomFieldMap extends AbstractInstanceValuesMap {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    private DistributionLine distributionLine;

    public static DistributionLineCustomFieldMap buildCustomFieldMap(List<DistributionLineCustomField> distributionLineCustomFieldList, DistributionLine distributionLine) {
        List<CustomField> customFieldList = new ArrayList<CustomField>();
        for (DistributionLineCustomField pcf : distributionLineCustomFieldList) {
            customFieldList.add(pcf.getCustomField());
        }
        DistributionLineCustomFieldMap cfm = new DistributionLineCustomFieldMap();
        cfm.initialize(customFieldList);
        cfm.distributionLine = distributionLine;
        return cfm;
    }

    @Override
    public Object createObject(Object key) {
        CustomField newCustomField = new CustomField();
        newCustomField.setName((String) key);
        distributionLine.getCustomFields().add(new DistributionLineCustomField(distributionLine, newCustomField));
        return newCustomField;
    }

    @Override
    public Object getKeyForItem(Object o) {
        CustomField customField = (CustomField) o;
        return customField.getName();
    }
}
