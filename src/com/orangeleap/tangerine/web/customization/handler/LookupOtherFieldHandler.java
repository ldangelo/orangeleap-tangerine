package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class LookupOtherFieldHandler extends LookupFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    public LookupOtherFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        OtherFieldHandler.handleOtherField(fieldVO, model);
        return fieldVO;
    }
}
