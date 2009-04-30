package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class AssociationFieldHandler extends LookupFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public AssociationFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);

        if (propertyValue != null && NumberUtils.isNumber(propertyValue.toString())) {
            ReferenceType referenceType = currentField.getFieldDefinition().getReferenceType();
            Long id = Long.parseLong(propertyValue.toString());
            fieldVO.setId(id);
            fieldVO.addId(id);
            String displayValue = resolve(id, referenceType);
            fieldVO.setDisplayValue(displayValue);
            fieldVO.addDisplayValue(displayValue);
            fieldVO.setReferenceType(referenceType);
        }
        return fieldVO;
    }
}
