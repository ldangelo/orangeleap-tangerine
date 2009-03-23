package com.orangeleap.tangerine.web.customization.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class CodeAdditionalFieldsHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private final PicklistItemService picklistItemService;

    public CodeAdditionalFieldsHandler(ApplicationContext appContext) {
        super(appContext);
        picklistItemService = (PicklistItemService)appContext.getBean("picklistItemService");
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        resolveCodes(fieldVO, fieldVO.getFieldName(), (String)propertyValue);
        AdditionalFieldsHandler.handleAdditionalFields(fieldVO, model);
        return fieldVO;
    }
    
    /**
     * 
     * @param fieldVO TODO
     * @param picklistNameId
     * @param itemNamesStr a comma delimited list of itemNames
     * @return
     */
    @SuppressWarnings("unchecked")
    private void resolveCodes(FieldVO fieldVO, String picklistNameId, String itemNamesStr) {
        if (logger.isDebugEnabled()) {
            logger.debug("resolve: picklistNameId = " + picklistNameId + " itemNames = " + itemNamesStr);
        }
        Picklist picklist = picklistItemService.getPicklist(picklistNameId);
        List<String> displayValues = new ArrayList<String>();
        if (picklist != null) {
            Set<String> itemNames = StringUtils.commaDelimitedListToSet(itemNamesStr);
            if (itemNames != null) {
                for (PicklistItem code : picklist.getPicklistItems()) { // TODO: does this need to check for active picklist items?
                    if (itemNames.contains(code.getItemName())) {
                        displayValues.add(new StringBuilder(code.getValue()).append(" - ").append(code.getDefaultDisplayValue()).toString());
                    }
                }
            }
        }
        fieldVO.setDisplayValues(displayValues);
    }
}
