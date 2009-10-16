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

package com.orangeleap.tangerine.json.controller.admin.picklists;

import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public abstract class AbstractPicklistController {
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    protected void checkPicklistEditAllowed(HttpServletRequest request) {
        Map<String, AccessType> pageAccess = (Map<String, AccessType>) WebUtils.getSessionAttribute(request, "pageAccess");
        if (pageAccess.get("/picklistItems.htm") != AccessType.ALLOWED) {
            throw new RuntimeException("You are not authorized to access this page");
        }
    }
}
