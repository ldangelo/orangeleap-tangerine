package com.orangeleap.tangerine.web.flow;

import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class AbstractAction {
    
    public AbstractAction() { }

    protected HttpServletRequest getRequest(final RequestContext flowRequestContext) {
        return (HttpServletRequest) ((ServletExternalContext) flowRequestContext.getExternalContext()).getNativeRequest();
    }

    protected String getRequestParameter(final RequestContext flowRequestContext, String parameterName) {
        HttpServletRequest request = getRequest(flowRequestContext);
        return request.getParameter(parameterName);
    }

    protected void setFlowScopeAttribute(final RequestContext flowRequestContext, final Object object, final String key) {
        MutableAttributeMap flowScopeMap = flowRequestContext.getFlowScope();
        flowScopeMap.put(key, object);
    }

    protected Object getFlowScopeAttribute(final RequestContext flowRequestContext, final String key) {
        MutableAttributeMap flowScopeMap = flowRequestContext.getFlowScope();
        return flowScopeMap.get(key);
    }

    protected SortInfo getSortInfo(final RequestContext flowRequestContext) {
        return new SortInfo(getRequestParameter(flowRequestContext, StringConstants.SORT),
                getRequestParameter(flowRequestContext, StringConstants.DIR),
                getRequestParameter(flowRequestContext, StringConstants.LIMIT),
                getRequestParameter(flowRequestContext, StringConstants.START));
    }
}