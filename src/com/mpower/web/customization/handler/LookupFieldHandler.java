package com.mpower.web.customization.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.Person;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.ReferenceType;
import com.mpower.web.customization.FieldVO;

public class LookupFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public LookupFieldHandler(ApplicationContext appContext) {
        super(appContext);
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, String siteName, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, siteName, model);
        boolean isCustom = currentField.getFieldDefinition().isCustom();
        BeanWrapper modelBeanWrapper = new BeanWrapperImpl(model);
        String fieldProperty = fieldVO.getFieldName();
        Object propertyValue = modelBeanWrapper.getPropertyValue(fieldProperty);

        if (propertyValue != null && isCustom) {
            ReferenceType referenceType = currentField.getFieldDefinition().getReferenceType();

            List<Long> list = new ArrayList<Long>();
            fieldVO.setIds(list);
            String[] ids = ((String)propertyValue).split(",");
            StringBuffer sb = new StringBuffer();
            for (String id : ids) {
                if (id.length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(FieldVO.DISPLAY_VALUE_DELIMITER);
                    }
                    Long longid = Long.valueOf(id);
                    sb.append(resolve(longid, referenceType));
                    fieldVO.setId(longid);
                    list.add(longid);
                }
            }
            fieldVO.setDisplayValue(sb.toString());
        }
        return fieldVO;
    }

    private String resolve(Long id, ReferenceType referenceType) {
        if (referenceType == ReferenceType.person) {
            Person person = personService.readPersonById(id);
            return person.getDisplayValue();
        }
        return "" + id;
    }
}
