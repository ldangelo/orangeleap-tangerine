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

package com.orangeleap.tangerine.controller.relationship;

import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class RelationshipQueryLookupController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    protected ConstituentService constituentService;

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
	        final Map<String, Object> parameters = new HashMap<String, Object>();
	        if (StringConstants.INDIVIDUAL.equals(searchOption)) {
		        parameters.put(StringConstants.ACCOUNT_NUMBER, request.getParameter(StringConstants.ACCOUNT_NUMBER));
		        parameters.put(StringConstants.LAST_NAME, request.getParameter(StringConstants.LAST_NAME));
		        parameters.put(StringConstants.FIRST_NAME, request.getParameter(StringConstants.FIRST_NAME));
		        parameters.put(QueryUtil.ADDITIONAL_WHERE, "constituent_type = 'individual' ");
	        }
	        else if (StringConstants.ORGANIZATION.equals(searchOption)) {
		        parameters.put(StringConstants.ACCOUNT_NUMBER, request.getParameter(StringConstants.ACCOUNT_NUMBER));
		        parameters.put(StringConstants.ORGANIZATION_NAME, request.getParameter(StringConstants.ORGANIZATION_NAME));
		        parameters.put(QueryUtil.ADDITIONAL_WHERE, "constituent_type = 'organization' ");
	        }
	        else if (StringConstants.FULLTEXT.equals(searchOption)) {
		        if (StringUtils.hasText(request.getParameter(StringConstants.FULLTEXT))) {
		            parameters.put(StringConstants.FULLTEXT, request.getParameter(StringConstants.FULLTEXT));
		        }
	        }

            List<Constituent> constituents = constituentService.searchConstituents(parameters);
            if (constituents != null) {
	            if (StringConstants.FULLTEXT.equals(searchOption)) {
		            constituents = filterConstituents(constituents, request.getParameter(StringConstants.FIELD_DEF));
	            }
                sortPaginate(request, constituents);
            }
        }
        return new ModelAndView(super.getSuccessView());
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return "";
    }

    @SuppressWarnings("unchecked")
    protected void sortPaginate(HttpServletRequest request, List objects) {
	    TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder();
	    pagedListHolder.doSort(objects, StringConstants.ACCOUNT_NUMBER, "displayValue");
        request.setAttribute("results", objects);
    }

	protected List<Constituent> filterConstituents(List<Constituent> constituents, String constituentType) {
		final List<Constituent> filteredConstituents = new ArrayList<Constituent>();
		if (StringConstants.INDIVIDUAL.equals(constituentType) || StringConstants.ORGANIZATION.equals(constituentType)) {
			for (Constituent thisConstituent : constituents) {
				if (thisConstituent.getConstituentType().equals(constituentType)) {
					filteredConstituents.add(thisConstituent);
				}
			}
		}
		else {
			filteredConstituents.addAll(constituents);
		}
		return filteredConstituents;
	}
}
