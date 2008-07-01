package com.mpower.web.customization;

import java.util.List;
import java.util.Locale;

import com.mpower.domain.User;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.FieldType;

public class ExpirationFieldHandler implements FieldHandler {

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, User user, Object model) {
        FieldVO fieldVO = new FieldVO();
        fieldVO.setFieldType(FieldType.CC_EXPIRATION);
        return fieldVO;
    }
}
