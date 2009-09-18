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

package com.orangeleap.tangerine.json.controller.list;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineBeanUtils;
import org.springframework.beans.BeanWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public abstract class TangerineJsonSearchResultsController extends TangerineJsonListController {

    @SuppressWarnings("unchecked")
    protected Map<String, Object> findSearchParameters(HttpServletRequest request, BeanWrapper bw) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String escapedParameterName = enu.nextElement();
            String value = request.getParameter(escapedParameterName);
            value = value != null ? value.replaceFirst("^none$", StringConstants.EMPTY) : null; // the value 'none' should be replaced with empty string
            String unescapedParameterName = TangerineForm.unescapeFieldName(escapedParameterName);

            TangerineBeanUtils.checkInnerBeanCreated(bw, unescapedParameterName);
            if (bw.isReadableProperty(unescapedParameterName)) {
                params.put(unescapedParameterName, value);
            }
        }
        return params;
    }
}
