package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.customization.SectionField;
import com.mpower.type.FieldType;

public class SpacerFieldHandler implements FieldHandler {

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Long userId, Object model) {
        FieldVO fieldVO = new FieldVO();
        fieldVO.setFieldType(FieldType.SPACER);
        return fieldVO;
    }
}
