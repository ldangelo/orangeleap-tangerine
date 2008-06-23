package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.entity.User;
import com.mpower.domain.entity.customization.SectionField;

public interface FieldHandler {
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model);
}
