package com.orangeleap.tangerine.web.customization.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@SuppressWarnings("serial")
public class InputTag extends RequestContextAwareTag {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	protected FieldVO field;

	public FieldVO getField() {
		return field;
	}

	public void setField(FieldVO field) {
		this.field = field;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		println(createInputHtml());
		return SKIP_BODY;
	}

	protected String createInputHtml() {
		String html = StringConstants.EMPTY;
		String inputName = null;

		FieldType ft = field.getFieldType();
		if (FieldType.PICKLIST.equals(ft)) {
			inputName = "picklistInput";
		}
		else if (FieldType.PAYMENT_SOURCE_PICKLIST.equals(ft)) {
			inputName = "paymentSourcePicklistInput";
		}
		else if (FieldType.ADJUSTED_GIFT_PAYMENT_TYPE_PICKLIST.equals(ft)) {
			inputName = "adjustedGiftPaymentTypePicklistInput";
		}
		else if (FieldType.ADJUSTED_GIFT_PAYMENT_SOURCE_PICKLIST.equals(ft)) {
			inputName = "adjustedGiftPaymentSourcePicklistInput";
		}
		else if (FieldType.DATE_DISPLAY.equals(ft)) {
			inputName = "dateDisplayInput";
		}
		else if (FieldType.CC_EXPIRATION.equals(ft)) {
			inputName = "creditCardExpirationInput";
		}
		else if (FieldType.CC_EXPIRATION_DISPLAY.equals(ft)) {
			inputName = "creditCardExpirationDisplayInput";
		}
		else if (FieldType.ADDRESS_PICKLIST.equals(ft)) {
			inputName = "addressPicklistInput";
		}
		else if (FieldType.EXISTING_ADDRESS_PICKLIST.equals(ft)) {
			inputName = "existingAddressPicklistInput";
		}
		else if (FieldType.PHONE_PICKLIST.equals(ft)) {
			inputName = "phonePicklistInput";
		}
		else if (FieldType.EXISTING_PHONE_PICKLIST.equals(ft)) {
			inputName = "existingPhonePicklistInput";
		}
		else if (FieldType.EMAIL_PICKLIST.equals(ft)) {
			inputName = "emailPicklistInput";
		}
		else if (FieldType.EXISTING_EMAIL_PICKLIST.equals(ft)) {
			inputName = "existingEmailPicklistInput";
		}
		else if (FieldType.CHECKBOX.equals(ft)) {
			inputName = "checkboxInput";
		}
		else if (FieldType.LONG_TEXT.equals(ft)) {
			inputName = "textAreaInput";
		}
		else if (FieldType.READ_ONLY_TEXT.equals(ft) || FieldType.PAYMENT_TYPE_READ_ONLY_TEXT.equals(ft) || FieldType.CODE_OTHER_DISPLAY.equals(ft)) {
			inputName = "readOnlyTextInput";
		}
		else if (FieldType.SPACER.equals(ft)) {
			inputName = "spacerInput";
		}
		else if (FieldType.TEXT.equals(ft) || FieldType.ADDRESS.equals(ft) || FieldType.PHONE.equals(ft)) {
			inputName = "textInput";
		}
		else if (FieldType.LOOKUP.equals(ft)) {
			inputName = "lookupInput";
		}
		else if (FieldType.CODE.equals(ft)) {
			inputName = "codeInput";
		}
		else if (FieldType.CODE_OTHER.equals(ft)) {
			inputName = "codeOtherInput";
		}
		else if (FieldType.DATE_TIME.equals(ft)) {
			inputName = "dateTimeInput";
		}
		else if (FieldType.NUMBER.equals(ft)) {
			inputName = "numberInput";
		}
		else if (FieldType.PERCENTAGE.equals(ft)) {
			inputName = "percentageInput";
		}
		else if (FieldType.PREFERRED_PHONE_TYPES.equals(ft)) {
			inputName = "preferredPhoneTypesInput";
		}

		if (inputName != null) {
			AbstractInput input = getInputBean(inputName);
			if (input != null) {
				html = input.handleField(getRequest(), getResponse(), pageContext, field);
			}
			else {
				if (logger.isWarnEnabled()) {
					logger.warn("createInputHtml: could not find input handler for - " + inputName);
				}
			}
		}
		return html;
	}

	protected AbstractInput getInputBean(String beanName) {
		return (AbstractInput) getRequestContext().getWebApplicationContext().getBean(beanName);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) pageContext.getResponse();
	}

	protected JspWriter getOut() {
		return pageContext.getOut();
	}

	/**
	 * Print to the page
	 * @param text
	 * @throws IOException
	 */
	protected void print(String text) throws IOException {
		getOut().print(text);
	}

	/**
	 * Print to the page with a new line
	 * @param text
	 * @throws IOException
	 */
	protected void println(String text) throws IOException {
		getOut().println(text);
	}
}
