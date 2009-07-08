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

package com.orangeleap.tangerine.web.customization.tag;

import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

public class PageTitleTag extends TagSupport {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());


    private static final long serialVersionUID = 1L;
    private String pageName;
    private MessageService messageService;

    @Override
    public int doStartTag() throws JspException {
        messageService = (MessageService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("messageService");

        Locale locale = pageContext.getRequest().getLocale();
        String messageValue = messageService.lookupMessage(MessageResourceType.TITLE, pageName, locale);
        try {
            pageContext.getOut().write(messageValue);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.SKIP_BODY;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String messageKey) {
        this.pageName = messageKey;
    }
}
