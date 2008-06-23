package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.entity.User;
import com.mpower.domain.entity.customization.SectionField;
import com.mpower.domain.type.FieldType;

public class SpacerFieldHandler implements FieldHandler {

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model) {
        FieldVO fieldVO = new FieldVO();
        fieldVO.setFieldType(FieldType.SPACER);
        return fieldVO;
    }
}
