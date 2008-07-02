package com.mpower.web.customization;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.SectionField;
import com.mpower.type.FieldType;

public class FieldHandlerHelper {
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
		fieldTypeToHandlerMap.put(FieldType.DATE, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.TEXT, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.READ_ONLY_TEXT, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.LOOKUP, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.DATE_TIME, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION_DISPLAY, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.LONG_TEXT, genericFieldHandler);
		fieldTypeToHandlerMap.put(FieldType.NUMBER, genericFieldHandler);
		ExpirationFieldHandler expirationFieldHandler = new ExpirationFieldHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION, expirationFieldHandler);

		PhoneFieldHandler phoneFieldHandler = new PhoneFieldHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PHONE, phoneFieldHandler);

		PicklistFieldHandler picklistFieldHandler = new PicklistFieldHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PICKLIST, picklistFieldHandler);

		PreferredPhoneFieldHandler preferredPhoneFieldHandler = new PreferredPhoneFieldHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PREFERRED_PHONE_TYPES, preferredPhoneFieldHandler);

		fieldTypeToHandlerMap.put(FieldType.SPACER, new SpacerFieldHandler());
		initialized = true;

    }
}
