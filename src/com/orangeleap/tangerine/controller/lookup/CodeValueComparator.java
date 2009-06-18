package com.orangeleap.tangerine.controller.lookup;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

public class CodeValueComparator implements Comparator<CodeValue> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public int compare(CodeValue cv1, CodeValue cv2) {
        if (cv1.getDisplayValue() == null) {
            return 1;
        }
        return cv1.getDisplayValue().compareToIgnoreCase(cv2.getDisplayValue());
    }
}
