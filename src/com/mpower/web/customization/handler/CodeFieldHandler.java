package com.mpower.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.model.customization.Code;
import com.mpower.domain.model.customization.SectionField;
import com.mpower.service.CodeService;
import com.mpower.web.customization.FieldVO;

public class CodeFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private final CodeService codeService;

    public CodeFieldHandler(ApplicationContext appContext) {
        super(appContext);
        codeService = (CodeService)appContext.getBean("codeService");
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        fieldVO.setDisplayValue(resolve(fieldVO.getFieldName(), (String)propertyValue));
        return fieldVO;
    }
    
    private String resolve(String codeType, String codeValue) {
        if (logger.isDebugEnabled()) {
            logger.debug("resolve: codeValue = " + codeValue);
        }
        String val = codeValue;
        Code code = codeService.readCodeBySiteTypeValue(codeType, codeValue);
        if (code != null) {
            val = code.getDisplayValue();
        }
        return val;
    }
}
