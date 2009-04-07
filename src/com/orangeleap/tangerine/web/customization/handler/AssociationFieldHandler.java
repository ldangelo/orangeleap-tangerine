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
        boolean isCustom = currentField.getFieldDefinition().isCustom();
        Object propertyValue = super.getPropertyValue(model, fieldVO);

        if (propertyValue != null && NumberUtils.isNumber((String) propertyValue) && isCustom) {
            ReferenceType referenceType = currentField.getFieldDefinition().getReferenceType();
            Long id = Long.parseLong((String) propertyValue);
            fieldVO.setId(id);
            fieldVO.setDisplayValue(resolve(id, referenceType));
            fieldVO.setReferenceType(referenceType);
        }
        return fieldVO;
    }
}
