/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.web.customization.tag.fields;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.LayoutType;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.AbstractTag;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandlerHelper;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.List;

public class SectionFieldTag extends AbstractTag {

	protected final Log logger = OLLogger.getLog(getClass());

	private PageCustomizationService pageCustomizationService;
	private TangerineUserHelper tangerineUserHelper;
	private MessageService messageService;
	private String pageName;
	private FieldHandlerHelper fieldHandlerHelper;

	public void setPageName(String pageName) {
	    this.pageName = pageName;
	}
	
	@Override
	protected int doStartTagInternal() throws Exception {
		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		pageCustomizationService = (PageCustomizationService) appContext.getBean("pageCustomizationService");
		tangerineUserHelper = (TangerineUserHelper) appContext.getBean("tangerineUserHelper");
		messageService = (MessageService) appContext.getBean("messageService");
		fieldHandlerHelper = (FieldHandlerHelper) appContext.getBean("fieldHandlerHelper");

		List<SectionDefinition> sectionDefs = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(pageName), tangerineUserHelper.lookupUserRoles());

		StringBuilder sb = new StringBuilder();
		writeSections(sectionDefs, sb);
		pageContext.getOut().println(sb.toString());

		pageContext.getRequest().setAttribute(StringConstants.SECTION_DEFINITIONS, sectionDefs);
		
		return EVAL_BODY_INCLUDE;
	}

	protected void writeSections(List<SectionDefinition> sectionDefinitions, StringBuilder sb) {
		if (sectionDefinitions != null) {
			int oneColumnCount = 0;
			for (int x = 0; x < sectionDefinitions.size(); x++) {
				SectionDefinition sectionDef = sectionDefinitions.get(x);

				List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDef);

				if (LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
					writeColumnsStart(sectionDef, sb);

					/* First column */
					writeSingleColumnStart(sectionDef, sb);
					writeSectionField(sectionDef, sectionFields, sb, true);
					writeSingleColumnEnd(sb);

					/* Second column */
					writeSingleColumnStart(sectionDef, sb);
					writeSectionField(sectionDef, sectionFields, sb, false);
					writeSingleColumnEnd(sb);

					writeColumnsEnd(sb);

				}
				else if (LayoutType.ONE_COLUMN.equals(sectionDef.getLayoutType()) || LayoutType.ONE_COLUMN_HIDDEN.equals(sectionDef.getLayoutType())) {
					if (oneColumnCount == 0) {
						writeColumnsStart(sectionDef, sb);
					}

					writeSingleColumnStart(sectionDef, sb);
					writeSectionField(sectionDef, sectionFields, sb);
					writeSingleColumnEnd(sb);
					oneColumnCount++;

					int nextIndex = x + 1;

					if (nextIndex < sectionDefinitions.size()) {
						if (!LayoutType.ONE_COLUMN.equals(sectionDefinitions.get(nextIndex).getLayoutType()) &&
								!LayoutType.ONE_COLUMN_HIDDEN.equals(sectionDefinitions.get(nextIndex).getLayoutType())) {
							writeColumnsEnd(sb);
							oneColumnCount = 0;
						}
						else if ((LayoutType.ONE_COLUMN.equals(sectionDefinitions.get(nextIndex).getLayoutType()) ||
									LayoutType.ONE_COLUMN_HIDDEN.equals(sectionDefinitions.get(nextIndex).getLayoutType())) &&
									oneColumnCount == 2) {
							writeColumnsEnd(sb);
							oneColumnCount = 0;
						}
					}
					else {
						writeColumnsEnd(sb);
						oneColumnCount = 0;
					}
				}
				else if (LayoutType.GRID.equals(sectionDef.getLayoutType())) {
					
				}
			}
		}
	}

	protected void writeSectionHeader(SectionDefinition sectionDef, StringBuilder sb) {
		sb.append("<h4 class=\"formSectionHeader\">").append(getSectionHeader(sectionDef)).append("</h4>");
	}

	protected String getSectionHeader(SectionDefinition sectionDef) {
		String messageValue = messageService.lookupMessage(MessageResourceType.SECTION_HEADER, sectionDef.getSectionName(), pageContext.getRequest().getLocale());
		if (!StringUtils.hasText(messageValue)) {
		    messageValue = sectionDef.getDefaultLabel();
		}
		return messageValue;
	}

	protected void writeColumnsStart(SectionDefinition sectionDef, StringBuilder sb) {
		sb.append("<div class=\"columns ");
		if (LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			sb.append(sectionDef.getSectionHtmlName());
		}
		sb.append("\" ");
		if (LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			sb.append(" id=\"").append(sectionDef.getSectionHtmlName()).append("\"");
		}
		sb.append(">");
		if (LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			writeSectionHeader(sectionDef, sb);
		}
	}

	protected void writeColumnsEnd(StringBuilder sb) {
		sb.append("<div class=\"clearColumns\"></div></div>");
	}

	protected void writeSingleColumnStart(SectionDefinition sectionDef, StringBuilder sb) {
		sb.append("<div class=\"column singleColumn ");
		if ( ! LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			sb.append(sectionDef.getSectionHtmlName());
		}
		sb.append("\" ");
		if (LayoutType.ONE_COLUMN_HIDDEN.equals(sectionDef.getLayoutType())) {
			sb.append("style=\"display:none\" ");
		}
		if ( ! LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			sb.append("id=\"").append(sectionDef.getSectionHtmlName()).append("\"");
		}
		sb.append(">");
		if (LayoutType.ONE_COLUMN.equals(sectionDef.getLayoutType()) || LayoutType.ONE_COLUMN_HIDDEN.equals(sectionDef.getLayoutType())) {
			writeSectionHeader(sectionDef, sb);
		}
		sb.append("<ul class=\"formFields width385\">");
	}

	protected void writeSingleColumnEnd(StringBuilder sb) {
		sb.append("<li class=\"clear\"/></ul></div>");
	}

	protected void writeSectionField(SectionDefinition sectionDef, List<SectionField> sectionFields, StringBuilder sb) {
		writeSectionField(sectionDef, sectionFields, sb, false);	
	}

	protected void writeSectionField(SectionDefinition sectionDef, List<SectionField> sectionFields, StringBuilder sb, boolean firstColumn) {
		int begin = 0;
		int end = sectionFields.size();
		if (LayoutType.TWO_COLUMN.equals(sectionDef.getLayoutType())) {
			if (firstColumn) {
				end = (int)Math.ceil((double)(sectionFields.size() / 2));
			}
			else {
				begin = (int)Math.floor((double)(sectionFields.size() / 2));
			}
		}
		for (int x = begin; x < end; x++) {
			SectionField sectionField = sectionFields.get(x);
			FieldHandler fieldHandler = fieldHandlerHelper.lookupFieldHandler(sectionField.getFieldType());
			if (fieldHandler != null) {
				fieldHandler.handleField(getRequest(), getResponse(),
						pageContext, sectionDef, sectionFields, sectionField,
						(TangerineForm) getRequestAttribute(StringConstants.FORM), sb);
			}
		}
	}
}
