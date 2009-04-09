package com.orangeleap.tangerine.web.customization.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.FieldType;

public class FieldHandlerHelper {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    private static Map<FieldType, FieldHandler> fieldTypeToHandlerMap = new HashMap<FieldType, FieldHandler>();
    private static boolean initialized;

    public static FieldHandler lookupFieldHandler(ApplicationContext appContext, SectionField sectionField) {
        if (! initialized) {
            synchronized (fieldTypeToHandlerMap) {
                if (! initialized) {
                    initializeFieldHandlerMap(appContext);
                }
            }
        }
        return fieldTypeToHandlerMap.get(sectionField.getFieldType());
    }

    private static void initializeFieldHandlerMap(ApplicationContext appContext) {
        GenericFieldHandler genericFieldHandler = new GenericFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.HIDDEN, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.DATE, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.DATE_DISPLAY, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.TEXT, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.READ_ONLY_TEXT, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.PAYMENT_TYPE_READ_ONLY_TEXT, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.LONG_TEXT, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.LOOKUP, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.DATE_TIME, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION_DISPLAY, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.CHECKBOX, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.NUMBER, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.PERCENTAGE, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.PAYMENT_SOURCE_PICKLIST, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.ADDRESS_PICKLIST, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.PHONE_PICKLIST, genericFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.EMAIL_PICKLIST, genericFieldHandler);
        
        AssociationFieldHandler associationFieldHandler = new AssociationFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.ASSOCIATION, associationFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.ASSOCIATION_DISPLAY, associationFieldHandler);
        
        fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION, new ExpirationFieldHandler(appContext));
        fieldTypeToHandlerMap.put(FieldType.PHONE, new PhoneFieldHandler(appContext));

        PicklistFieldHandler picklistFieldHandler = new PicklistFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.PICKLIST, picklistFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.PICKLIST_DISPLAY, picklistFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST, picklistFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_DISPLAY, picklistFieldHandler);
        PicklistAdditionalFieldsHandler picklistAdditionalFieldsHandler = new PicklistAdditionalFieldsHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_ADDITIONAL, picklistAdditionalFieldsHandler);
        fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_ADDITIONAL_DISPLAY, picklistAdditionalFieldsHandler);

        fieldTypeToHandlerMap.put(FieldType.PREFERRED_PHONE_TYPES, new PreferredPhoneFieldHandler(appContext));

        LookupFieldHandler lookupFieldHandler = new LookupFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP, lookupFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.MULTI_QUERY_LOOKUP, lookupFieldHandler);
        LookupOtherFieldHandler lookupOtherFieldHandler = new LookupOtherFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP_OTHER, lookupOtherFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP_DISPLAY, lookupOtherFieldHandler);

        fieldTypeToHandlerMap.put(FieldType.CODE, new CodeFieldHandler(appContext)); 
        fieldTypeToHandlerMap.put(FieldType.CODE_OTHER, new CodeOtherFieldHandler(appContext));
        fieldTypeToHandlerMap.put(FieldType.MULTI_CODE_ADDITIONAL, new CodeAdditionalFieldsHandler(appContext));

        SelectionFieldHandler selectionFieldHandler = new SelectionFieldHandler(appContext);
        fieldTypeToHandlerMap.put(FieldType.SELECTION, selectionFieldHandler);
        fieldTypeToHandlerMap.put(FieldType.SELECTION_DISPLAY, selectionFieldHandler);

        fieldTypeToHandlerMap.put(FieldType.SPACER, new SpacerFieldHandler());
        initialized = true;

    }
}
