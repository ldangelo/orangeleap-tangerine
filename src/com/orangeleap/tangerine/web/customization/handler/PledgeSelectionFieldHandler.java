package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class PledgeSelectionFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PledgeService pledgeService;
    
    public PledgeSelectionFieldHandler(ApplicationContext appContext) {
        super(appContext);
        pledgeService = (PledgeService)appContext.getBean("pledgeService");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        if (propertyValue != null) {
            List<Long> pledgeIds = (List<Long>)propertyValue;
            for (Long pledgeId : pledgeIds) {
                Pledge pledge = pledgeService.readPledgeById(pledgeId);
                fieldVO.addDisplayValue(pledge.getPledgeShortDescription());
                fieldVO.addId(pledgeId);
            }
        }
        return fieldVO;
    }
}
