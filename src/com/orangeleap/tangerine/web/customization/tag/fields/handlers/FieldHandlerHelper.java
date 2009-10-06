package com.orangeleap.tangerine.web.customization.tag.fields.handlers;

import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.AssociationDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.CodeOtherDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.CreditCardExpirationDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.DateDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.MultiPicklistAdditionalDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.PicklistDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.QueryLookupDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.ReadOnlyTextHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.SelectionDisplayHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.display.SpacerHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.CheckboxHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.CreditCardExpirationHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.DateHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.DateTimeHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.HiddenHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.NumberHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.PercentageHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.TextAreaHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form.TextHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.AssociationHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.codes.CodeHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.LookupHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.codes.MultiCodeAdditionalHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.MultiQueryLookupHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.QueryLookupHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.QueryLookupOtherHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups.SelectionHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.AdjustedGiftPaymentSourcePicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.AdjustedGiftPaymentTypePicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.PaymentSourcePicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.PicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication.AddressPicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication.EmailPicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication.PhonePicklistHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.multi.MultiPicklistAdditionalHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.multi.MultiPicklistHandler;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("fieldHandlerHelper")
public class FieldHandlerHelper implements ApplicationContextAware {

    protected final Log logger = OLLogger.getLog(getClass());

    private final Map<FieldType, FieldHandler> fieldTypeToHandlerMap = new HashMap<FieldType, FieldHandler>();

	/**
	 * Show only be called by Spring during initialization of the bean
	 * @param appContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		initializeFieldHandlerMap(appContext);
	}

	public FieldHandler lookupFieldHandler(FieldType fieldType) {
        FieldHandler handler = fieldTypeToHandlerMap.get(fieldType);
		if (handler == null) {
			throw new IllegalArgumentException("Unhandled fieldType = " + fieldType);
		}
		return handler;
    }

    private void initializeFieldHandlerMap(ApplicationContext appContext) {
	    initTextHandler(appContext);
	    initNumberHandler(appContext);
	    initPercentageHandler(appContext);
	    initDateHandlers(appContext);
	    initTextAreaHandler(appContext);
	    initCheckboxHandler(appContext);
	    initCreditCardExpirationHandler(appContext);
	    initHiddenHandler(appContext);

	    initLookupHandler(appContext);
	    initQueryLookupHandlers(appContext);
	    initAssociationHandlers(appContext);
	    initSelectionHandler(appContext);

	    initCodeHandlers(appContext);

	    initPicklistHandlers(appContext);
	    initPaymentSourcePicklistHandler(appContext);
	    initCommunicationPicklistHandlers(appContext);

	    initAdjustedPicklistHandler(appContext);
	    
	    initDisplayHandlers(appContext);
    }

	private void initTextHandler(ApplicationContext appContext) {
		FieldHandler textHandler = new TextHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.TEXT, textHandler);
		fieldTypeToHandlerMap.put(FieldType.ADDRESS, textHandler);
		fieldTypeToHandlerMap.put(FieldType.PHONE, textHandler);
	}

	private void initNumberHandler(ApplicationContext appContext) {
		FieldHandler numberHandler = new NumberHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.NUMBER, numberHandler);
	}

	private void initHiddenHandler(ApplicationContext appContext) {
		FieldHandler hiddenHandler = new HiddenHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.HIDDEN, hiddenHandler);
	}

	private void initPercentageHandler(ApplicationContext appContext) {
		FieldHandler percentageHandler = new PercentageHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PERCENTAGE, percentageHandler);
	}

	private void initDateHandlers(ApplicationContext appContext) {
		FieldHandler dateHandler = new DateHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.DATE, dateHandler);

		FieldHandler dateTimeHandler = new DateTimeHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.DATE_TIME, dateTimeHandler);
	}

	private void initLookupHandler(ApplicationContext appContext) {
		FieldHandler lookupTimeHandler = new LookupHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.LOOKUP, lookupTimeHandler);
	}

	private void initTextAreaHandler(ApplicationContext appContext) {
		FieldHandler textAreaHandler = new TextAreaHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.LONG_TEXT, textAreaHandler);
	}

	private void initCheckboxHandler(ApplicationContext appContext) {
		FieldHandler checkboxHandler = new CheckboxHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CHECKBOX, checkboxHandler);
	}

	private void initCreditCardExpirationHandler(ApplicationContext appContext) {
		FieldHandler creditCardExpirationHandler = new CreditCardExpirationHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION, creditCardExpirationHandler);
	}

	private void initAssociationHandlers(ApplicationContext appContext) {
		FieldHandler associationHandler = new AssociationHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.ASSOCIATION, associationHandler);
	}

	private void initSelectionHandler(ApplicationContext appContext) {
		FieldHandler selectionHandler = new SelectionHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.SELECTION, selectionHandler);
	}

	private void initQueryLookupHandlers(ApplicationContext appContext) {
		FieldHandler queryLookupHandler = new QueryLookupHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP, queryLookupHandler);

		FieldHandler queryLookupOtherHandler = new QueryLookupOtherHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP_OTHER, queryLookupOtherHandler);

		FieldHandler multiQueryLookupHandler = new MultiQueryLookupHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.MULTI_QUERY_LOOKUP, multiQueryLookupHandler);
	}

	private void initCodeHandlers(ApplicationContext appContext) {
		FieldHandler codeHandler = new CodeHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CODE, codeHandler);

		FieldHandler codeOtherHandler = new com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.codes.CodeOtherHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CODE_OTHER, codeOtherHandler);

		FieldHandler multiCodeAdditionalHandler = new MultiCodeAdditionalHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.MULTI_CODE_ADDITIONAL, multiCodeAdditionalHandler);
	}

	private void initPicklistHandlers(ApplicationContext appContext) {
		FieldHandler picklistHandler = new PicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PICKLIST, picklistHandler);

		FieldHandler multiPicklistHandler = new MultiPicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST, multiPicklistHandler);

		FieldHandler multiPicklistAdditionalHandler = new MultiPicklistAdditionalHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_ADDITIONAL, multiPicklistAdditionalHandler);
	}

	private void initCommunicationPicklistHandlers(ApplicationContext appContext) {
		FieldHandler addressPicklistHandler = new AddressPicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.ADDRESS_PICKLIST, addressPicklistHandler);
		fieldTypeToHandlerMap.put(FieldType.EXISTING_ADDRESS_PICKLIST, addressPicklistHandler);

		FieldHandler phonePicklistHandler = new PhonePicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PHONE_PICKLIST, phonePicklistHandler);
		fieldTypeToHandlerMap.put(FieldType.EXISTING_PHONE_PICKLIST, phonePicklistHandler);

		FieldHandler emailPicklistHandler = new EmailPicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.EMAIL_PICKLIST, emailPicklistHandler);
		fieldTypeToHandlerMap.put(FieldType.EXISTING_EMAIL_PICKLIST, emailPicklistHandler);
	}

	private void initPaymentSourcePicklistHandler(ApplicationContext appContext) {
		FieldHandler paymentSourcePicklistHandler = new PaymentSourcePicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PAYMENT_SOURCE_PICKLIST, paymentSourcePicklistHandler);
	}

	private void initAdjustedPicklistHandler(ApplicationContext appContext) {
		FieldHandler adjustedGiftPaymentTypePicklistHandler = new AdjustedGiftPaymentTypePicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.ADJUSTED_GIFT_PAYMENT_TYPE_PICKLIST, adjustedGiftPaymentTypePicklistHandler);

		FieldHandler adjustedGiftPaymentSourcePicklistHandler = new AdjustedGiftPaymentSourcePicklistHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.ADJUSTED_GIFT_PAYMENT_SOURCE_PICKLIST, adjustedGiftPaymentSourcePicklistHandler);
	}

	private void initDisplayHandlers(ApplicationContext appContext) {
		FieldHandler spacerHandler = new SpacerHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.SPACER, spacerHandler);

		FieldHandler readOnlyTextHandler = new ReadOnlyTextHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.READ_ONLY_TEXT, readOnlyTextHandler);
        fieldTypeToHandlerMap.put(FieldType.NUMBER_DISPLAY, readOnlyTextHandler);
        fieldTypeToHandlerMap.put(FieldType.PERCENTAGE_DISPLAY, readOnlyTextHandler);

		FieldHandler codeOtherDisplayHandler = new CodeOtherDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CODE_OTHER_DISPLAY, codeOtherDisplayHandler);

		FieldHandler dateDisplayHandler = new DateDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.DATE_DISPLAY, dateDisplayHandler);

		FieldHandler creditCardExpirationDisplayHandler = new CreditCardExpirationDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.CC_EXPIRATION_DISPLAY, creditCardExpirationDisplayHandler);

		FieldHandler multiPicklistAdditionalDisplayHandler = new MultiPicklistAdditionalDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_ADDITIONAL_DISPLAY, multiPicklistAdditionalDisplayHandler);
		fieldTypeToHandlerMap.put(FieldType.MULTI_PICKLIST_DISPLAY, multiPicklistAdditionalDisplayHandler);

		FieldHandler associationDisplayHandler = new AssociationDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.ASSOCIATION_DISPLAY, associationDisplayHandler);

		FieldHandler queryLookupDisplayHandler = new QueryLookupDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.QUERY_LOOKUP_DISPLAY, queryLookupDisplayHandler);

		FieldHandler picklistDisplayHandler = new PicklistDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.PICKLIST_DISPLAY, picklistDisplayHandler);

		FieldHandler selectionDisplayHandler = new SelectionDisplayHandler(appContext);
		fieldTypeToHandlerMap.put(FieldType.SELECTION_DISPLAY, selectionDisplayHandler);
	}

}