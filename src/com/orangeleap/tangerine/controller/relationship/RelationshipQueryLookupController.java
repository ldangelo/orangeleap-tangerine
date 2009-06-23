package com.orangeleap.tangerine.controller.relationship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;

public class RelationshipQueryLookupController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="relationshipService")
    protected RelationshipService relationshipService;
    
    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map<String, Object> refData = new HashMap<String, Object>();
        refData.put("relationshipType", request.getParameter(StringConstants.FIELD_DEF));
        refData.put(StringConstants.FIELD_DEF, request.getParameter(StringConstants.FIELD_DEF));
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String searchOption = request.getParameter(StringConstants.SEARCH_OPTION);
        if (StringUtils.hasText(searchOption)) {
            String searchValue = request.getParameter(searchOption);
            String fieldDef = request.getParameter(StringConstants.FIELD_DEF);
            
            List<Constituent> constituents = relationshipService.executeRelationshipQueryLookup(fieldDef, searchOption, searchValue);
            if (constituents != null) {
                sortPaginate(request, constituents, searchOption);
            }
        }
        return new ModelAndView(super.getSuccessView());
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }

    @SuppressWarnings("unchecked")
    protected void sortPaginate(HttpServletRequest request, List objects, String searchOption) {
        MutableSortDefinition sortDef = new MutableSortDefinition(searchOption, true, Boolean.TRUE);
        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(objects, sortDef);
        pagedListHolder.resort();
        request.setAttribute("results", pagedListHolder.getSource());
    }
}
