package com.orangeleap.tangerine.web.customization.tag.inputs.impl.lookups;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

public class QueryLookupInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        createLookupWrapperBegin(request, response, pageContext, fieldVO, sb);
        return null;
    }

    protected void createLookupWrapperBegin(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupWrapper\">");
    }
    
    protected void createLookupFieldBegin(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div class=\"lookupField " + checkForNull(fieldVO.getEntityAttributes()) + "\">");
    }
    
    protected void createLookupOptionBegin(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div id=\"lookup-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" class=\"queryLookupOption\" selectedId=\"" + checkForNull(fieldVO.getId()) + "\">");
    }
        
    protected String createOptionText(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        String displayValue = fieldVO.getDisplayValue() != null ? StringEscapeUtils.escapeHtml(fieldVO.getDisplayValue().toString().trim()) : StringConstants.EMPTY;
        sb.append("<span>");
        if (fieldVO.getId() != null && fieldVO.getId() > 0 && fieldVO.getReferenceType() != null) {
            StringBuilder linkSb = new StringBuilder();
            if (ReferenceType.gift.equals(fieldVO.getReferenceType())) {
                linkSb.append("giftView.htm?"); // TODO: remove hardCoded link when automatic routing to the view page is implemented
            }
            else {
                linkSb.append(fieldVO.getReferenceType().toString()).append(".htm?");
            }
            linkSb.append(fieldVO.getReferenceType().toString()).append("Id=" + checkForNull(fieldVO.getId()));
            if (ReferenceType.person.equals(fieldVO.getReferenceType()) == false) {
                linkSb.append("&" + StringConstants.PERSON_ID + "=" + request.getParameter(StringConstants.PERSON_ID));
            }
            String linkMsg = getMessage("gotoLink");
            sb.append("<a href=\"" + linkSb.toString() + "\" target=\"_blank\" alt=\"" + linkMsg + "\" title=\"" + linkMsg + "\">");
            sb.append(displayValue);
            sb.append("</a>");
        }
        else {
            sb.append(displayValue);
        }
        sb.append("</span>");
        return displayValue;
    }
    
    protected void createDeleteOption(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb, String displayValue) {
        if (StringUtils.hasText(displayValue)) {
            String removeMsg = getMessage("removeThisOption");
            sb.append("<a href=\"javascript:void(0)\" onclick=\"" + getDeleteClickHandler() + "\" class=\"deleteOption\"><img src=\"images/icons/deleteRow.png\" alt=\"" + removeMsg + "\" title=\"" + removeMsg + "\"/></a>");
        }
    }
    
    protected String getDeleteClickHandler() {
        return "Lookup.deleteOption(this)";
    }
    
    protected void createLookupOptionEnd(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</div>");
    }
    
    protected void createLookupLink(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<a href=\"javascript:void(0)\" onclick=\"" + getQueryClickHandler() + "\" fieldDef=\"");
        
        SectionField sectionField = (SectionField)request.getAttribute("sectionField");
        if (sectionField != null && sectionField.getFieldDefinition() != null && sectionField.getFieldDefinition().getId() != null) {
            sb.append(StringEscapeUtils.escapeHtml(sectionField.getFieldDefinition().getId()));
        }
        String lookupMsg = getMessage("lookup");
        sb.append("\" class=\"hideText\" alt=\"" + lookupMsg + "\" title=\"" + lookupMsg + "\">" + lookupMsg + "</a>");
    }
    
    protected String getQueryClickHandler() {
        return "Lookup.loadQueryLookup(this)";
    }

    protected void createLookupFieldEnd(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</div>");
    }
    
    protected void createHiddenField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" value=\"" + checkForNull(fieldVO.getId()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\"/>");
    }
    
    protected void createCloneable(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        String removeMsg = getMessage("removeThisOption");
        sb.append("<div class=\"queryLookupOption noDisplay clone\">");
        sb.append("<span><a href=\"\" target=\"_blank\"></a></span>");
        sb.append("<a href=\"javascript:void(0)\" onclick=\"" + getDeleteClickHandler() + "\" class=\"deleteOption\">");
        sb.append("<img src=\"images/icons/deleteRow.png\" alt=\"" + removeMsg + "\" title=\"" + removeMsg + "\"/>");
        sb.append("</a>");
        sb.append("</div>");
    }
    
    protected void createHiearchy(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        if (fieldVO.isHierarchy()) {
            sb.append("<a href=\"javascript:void(0)\" onclick=\"Lookup.loadTreeView(this)\" divid=\"treeview-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" "); 
            sb.append("personid=\"" + request.getParameter(StringConstants.PERSON_ID) + "\" fieldDef=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\">");
            sb.append(getMessage("viewHierarchy"));
            sb.append("</a>");
            sb.append("<div id=\"treeview-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\"></div>");
        }
    }
}
