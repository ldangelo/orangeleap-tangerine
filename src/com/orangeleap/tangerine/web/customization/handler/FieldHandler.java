package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.FieldVO;

public interface FieldHandler {
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model);
}
