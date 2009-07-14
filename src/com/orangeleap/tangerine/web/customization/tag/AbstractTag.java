package com.orangeleap.tangerine.web.customization.tag;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: alexlo
 * Date: Jul 6, 2009
 * Time: 11:02:14 AM
 */
public abstract class AbstractTag extends RequestContextAwareTag {

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) pageContext.getResponse();
	}

	protected Object getRequestAttribute(String attributeName) {
		return getRequest().getAttribute(attributeName);
	}

	protected void println(StringBuilder sb) throws Exception {
		pageContext.getOut().println(sb.toString());
	}
}
