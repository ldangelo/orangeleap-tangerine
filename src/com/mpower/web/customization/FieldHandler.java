package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.customization.SectionField;

public interface FieldHandler {
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model);
}
