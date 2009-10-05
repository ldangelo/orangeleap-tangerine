package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractPicklistHandler extends AbstractFieldHandler {

	public static final String NONE_ID = StringConstants.NONE;
	public static final String NEW_ID = "0";

	protected AbstractPicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	protected Picklist resolvePicklist(SectionField currentField) {
		EntityType entityType = currentField.getSecondaryFieldDefinition() != null ?
				currentField.getSecondaryFieldDefinition().getEntityType() : currentField.getFieldDefinition().getEntityType();
		return fieldService.readPicklistByFieldNameEntityType(currentField.getPicklistName(), entityType);
	}

	@SuppressWarnings("unchecked")
	protected String resolveReferenceValues(SectionField currentField, Picklist picklist) {
		Set<String> refValues = new TreeSet<String>();
		if (picklist != null) {
			for (PicklistItem item : picklist.getActivePicklistItems()) {
				refValues.addAll(StringUtils.commaDelimitedListToSet(item.getReferenceValue()));
			}
		}
		return checkForNull(StringUtils.collectionToCommaDelimitedString(refValues));
	}

	protected String resolveFirstReferenceValue(Picklist picklist) {
		String refValue = StringConstants.EMPTY;
		if (picklist != null && picklist.getActivePicklistItems().size() >= 1) {
			refValue = picklist.getActivePicklistItems().get(0).getReferenceValue();
		}
		return refValue;
	}

	protected boolean resolvePicklistIsCascading(Picklist picklist) {
		boolean isCascading = false;
		if (picklist != null) {
			for (PicklistItem item : picklist.getActivePicklistItems()) {
				if (StringUtils.hasText(item.getReferenceValue())) {
					isCascading = true;
					break;
				}
			}
		}
		return isCascading;
	}

	protected List<String> resolvePicklistItemNames(Picklist picklist) {
		List<String> itemNames = new ArrayList<String>();
		if (picklist != null) {
			for (PicklistItem item : picklist.getActivePicklistItems()) {
				itemNames.add(item.getItemName());
			}
		}
		return itemNames;
	}

	protected String resolvePicklistItemDisplayValue(PicklistItem item, ServletRequest request) {
		String displayValue = messageService.lookupMessage(MessageResourceType.PICKLIST_VALUE, item.getItemName(), request.getLocale());
		if ( ! StringUtils.hasText(displayValue)) {
			displayValue = item.getLongDescription();
			if ( ! StringUtils.hasText(displayValue)) {
				displayValue = item.getDefaultDisplayValue();
			}
		}
		return checkForNull(displayValue);
	}

	protected String resolveLabelText(SectionField currentField, PageContext pageContext) {
		String labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, currentField.getFieldLabelName(), pageContext.getRequest().getLocale());
		if (!StringUtils.hasText(labelText)) {
			if (!currentField.isCompoundField()) {
				labelText = currentField.getFieldDefinition().getDefaultLabel();
			}
			else {
				labelText = currentField.getSecondaryFieldDefinition().getDefaultLabel();
			}
		}
		return labelText;
	}

    protected void createNoneOption(SectionField currentField, Object fieldValue, StringBuilder sb) {
        if ( ! isFieldRequired(currentField)) {
            sb.append("<option value=\"").append(NONE_ID).append("\"");
	        if (fieldValue != null && NONE_ID.equals(fieldValue.toString())) {
		        sb.append(" selected=\"selected\"");
	        }
	        sb.append(">").append(getMessage("none")).append("</option>");
        }
    }

    protected void createNewOption(Object fieldValue, String reference, StringBuilder sb) {
        sb.append("<option value=\"").append(NEW_ID).append("\"");
        if (StringUtils.hasText(reference)) {
            sb.append(" reference=\"").append(StringEscapeUtils.escapeHtml(reference)).append("\"");
        }

	    // TODO: fix for user created?
	    if (fieldValue != null && NEW_ID.equals(fieldValue.toString())) {
		   sb.append(" selected=\"selected\"");
	    }
        sb.append(">").append(getMessage("createNew")).append("</option>");
    }

	protected void checkIfExistingOptionSelected(Object fieldValue, Object valueToCompare, StringBuilder sb) {
		if (valueToCompare != null && fieldValue != null) {
			if (valueToCompare.getClass().equals(fieldValue.getClass())) {
				if (valueToCompare.equals(fieldValue)) {
					sb.append(" selected=\"selected\"");
				}
			}
			else if (valueToCompare.toString().equals(fieldValue.toString())) {
				sb.append(" selected=\"selected\"");
			}
		}
	}

    @SuppressWarnings("unchecked")
    protected void createBeginOptGroup(List list, StringBuilder sb) {
        if (list != null && ! list.isEmpty()) {
            sb.append("<optgroup label=\"").append(getMessage("orChoose")).append("\">");
        }
    }

    @SuppressWarnings("unchecked")
    protected void createEndOptGroup(List list, StringBuilder sb) {
        if (list != null && ! list.isEmpty()) {
            sb.append("</optgroup>");
        }
    }

	protected void createBeginSelect(PageContext pageContext, SectionField currentField, String formFieldName, Picklist picklist, StringBuilder sb) {
        sb.append("<select name=\"").append(formFieldName).append("\" id=\"").append(formFieldName).append("\" class=\" ");

		getBeginSelectCssClass(picklist, sb);
        writeErrorClass(pageContext, formFieldName, sb);
		
	    sb.append(resolveEntityAttributes(currentField)).append("\" references=\"");
		sb.append(resolveReferenceValues(currentField, picklist)).append("\"");
        writeTabIndex(currentField, sb);
        sb.append(">");
    }

    protected void createEndSelect(StringBuilder sb) {
        sb.append("</select>");
    }

	protected void getBeginSelectCssClass(Picklist picklist, StringBuilder sb) {
		sb.append(" picklist ");
	}

	protected void createSelectedRef(String formFieldName, Object fieldValue, String reference, StringBuilder sb) {
	    String selectedRef = StringConstants.EMPTY;
	    if (fieldValue != null && NEW_ID.equals(fieldValue.toString())) {
	        selectedRef = reference;
	    }
	    sb.append("<div style=\"display:none\" id=\"selectedRef-").append(formFieldName).append("\">").append(checkForNull(selectedRef)).append("</div>");
	}

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        Object displayValue = StringConstants.EMPTY;
        Picklist picklist = resolvePicklist(currentField);
        if (picklist != null && beanWrapper != null) {
	        for (PicklistItem item : picklist.getActivePicklistItems()) {
		        if (StringUtils.hasText(item.getItemName())) {
					String picklistItemDisplayValue = resolvePicklistItemDisplayValue(item, request);

			        if (StringUtils.hasText(picklistItemDisplayValue) && fieldValue != null && fieldValue.toString().equals(item.getItemName())) {
				        displayValue = picklistItemDisplayValue;
			        }
		        }
	        }
        }
        return displayValue;
    }
}