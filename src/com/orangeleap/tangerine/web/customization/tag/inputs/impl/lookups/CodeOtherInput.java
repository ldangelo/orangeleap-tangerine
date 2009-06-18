package com.orangeleap.tangerine.web.customization.tag.inputs.impl.lookups;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("codeOtherInput")
public class CodeOtherInput extends CodeInput {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected String getDisplayAttributes(FieldVO fieldVO) {
        return super.getDisplayAttributes(fieldVO) + " otherFieldId=\"" + StringEscapeUtils.escapeHtml(fieldVO.getOtherFieldId()) + "\" ";
    }

    @Override
    protected String getLookupClickHandler() {
        return "Lookup.loadCodePopup(this, true)";
    }

}
