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

package com.orangeleap.tangerine.web.filters;

import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TangerineUserCheckFilter extends OncePerRequestFilter {

    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        final TangerineUserHelper tangerineUserHelper = (TangerineUserHelper) context.getBean("tangerineUserHelper");
        final TangerineAuthenticationDetails details = tangerineUserHelper.getDetails();
        if (details != null) {
            if (StringUtils.isBlank(details.getSite()) || StringUtils.isBlank(details.getUserName())) {
                if (logger.isInfoEnabled()) {
                    logger.info("doFilterInternal: Details site or userName is not valid");
                }
                response.sendRedirect("sessionTimedOut.jsp");
            }
        }
        chain.doFilter(request, response);
    }
}