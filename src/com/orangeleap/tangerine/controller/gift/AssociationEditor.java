package com.orangeleap.tangerine.controller.gift;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;

public class AssociationEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    public AssociationEditor() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Set<String> idsStrings = StringUtils.commaDelimitedListToSet(text);
        if (idsStrings != null && idsStrings.isEmpty() == false) {
            List<Long> idsLongs = new ArrayList<Long>();
            for (String s : idsStrings) {
                if (NumberUtils.isDigits(s)) {
                    idsLongs.add(Long.parseLong(s));
                }
            }
            setValue(idsLongs);
        }
    }
}
