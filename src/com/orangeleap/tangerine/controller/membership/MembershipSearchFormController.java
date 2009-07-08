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

package com.orangeleap.tangerine.controller.membership;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.SessionService;

public class MembershipSearchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

//    @Resource(name="commitmentService")
//    private CommitmentService commitmentService;

    @Resource(name="sessionService")
    private SessionService sessionService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        if (logger.isTraceEnabled()) {
            logger.trace("handleRequestInternal:");
        }
        Constituent p = new Constituent();
        p.setSite(sessionService.lookupSite());
//        Commitment g = new Commitment();
//        g.setConstituent(p);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("onSubmit:");
        }
//        Commitment commitment = (Commitment) command;
//        BeanWrapper bw = new BeanWrapperImpl(commitment);
//        Map<String, Object> params = new HashMap<String, Object>();
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String param = enu.nextElement();
//            if (StringUtils.trimToNull(request.getParameter(param)) != null) {
//                if (bw.isReadableProperty(param)) {
//                    params.put(param, bw.getPropertyValue(param));
//                }
//            }
//        }

//        List<Commitment> commitmentList = commitmentService.searchCommitments(CommitmentType.MEMBERSHIP, params);
        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
//        mav.addObject("commitmentList", commitmentList);
//        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
