package com.mpower.web.customization.handler;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.model.customization.SectionField;
import com.mpower.web.customization.FieldVO;

public interface FieldHandler {
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model);
}
