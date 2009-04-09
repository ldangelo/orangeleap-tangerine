package com.orangeleap.tangerine.web.customization.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class LookupFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    protected PledgeService pledgeService;

    public LookupFieldHandler(ApplicationContext appContext) {
        super(appContext);
        pledgeService = (PledgeService) appContext.getBean("pledgeService");
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        boolean isCustom = currentField.getFieldDefinition().isCustom();
        Object propertyValue = super.getPropertyValue(model, fieldVO);

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
            fieldVO.setReferenceType(referenceType);
        }
        return fieldVO;
    }

    protected String resolve(Long id, ReferenceType referenceType) {
        if (referenceType == ReferenceType.person) {
            Person constituent = constituentService.readConstituentById(id);
            return constituent.getDisplayValue();
        }
        else if (referenceType == ReferenceType.pledge) {
            Pledge pledge = pledgeService.readPledgeById(id);
            return pledge.getShortDescription();
        }
        return "" + id;
    }
}
