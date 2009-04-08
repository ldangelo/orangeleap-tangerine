package com.orangeleap.tangerine.controller.gift;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class AssociatedPledgeEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public AssociatedPledgeEditor() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Set<String> ids = StringUtils.commaDelimitedListToSet(text);
        if (ids != null && ids.isEmpty() == false) {
            List<Long> pledgeIds = new ArrayList<Long>();
            for (String s : ids) {
                if (NumberUtils.isDigits(s)) {
                    pledgeIds.add(Long.parseLong(s));
                }
            }
            setValue(pledgeIds);
        }
    }
}
