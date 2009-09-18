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

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.TangerineFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class GiftSearchFormController extends TangerineFormController {
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Constituent constituent = new Constituent(null, sessionService.lookupSite());
        Gift gift = new Gift();
        gift.setConstituent(constituent);
        if (mapSearchFieldToAmount(request)) {
            // searchField value is mapped to 'amount'
            gift.setAmount(new BigDecimal(request.getParameter("searchField")));
        }
        return gift;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException e) throws Exception {
        TangerineForm form = (TangerineForm) command;
        if (mapSearchFieldToAmount(request)) {
            String amountVal = request.getParameter("searchField");

            ServletRequestDataBinder binder = new ServletRequestDataBinder(form.getDomainObject());
            initBinder(request, binder);

            MutablePropertyValues propertyValues = new MutablePropertyValues();
            form.addField(StringConstants.AMOUNT, amountVal);
            propertyValues.addPropertyValue(StringConstants.AMOUNT, amountVal);
            binder.bind(propertyValues);
        }
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
//        if (logger.isTraceEnabled()) {
//            logger.trace("onSubmit:");
//        }
//
//        Gift gift = (Gift) command;
//        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(gift);
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String param = enu.nextElement();
//            if (!param.equalsIgnoreCase("type") &&
//                !param.equalsIgnoreCase("page") &&
//                !param.equalsIgnoreCase("view") &&
//                !param.equalsIgnoreCase("sort") &&
//                StringUtils.trimToNull(request.getParameter(param)) != null) {
//
//                if (bw.isReadableProperty(param)){
//                    params.put(param, bw.getPropertyValue(param));
//                }
//            }
//        }
//
//        // Search box is used for both constituent and gift search, the value we need
//        // is actually in an input field called searchField
//        String textValue = request.getParameter("searchField");
//
//        // if this is not null, it means they did a general search for Gift from the
//        // search box in the navigation bar
//        if (textValue != null && NumberUtils.isNumber(textValue)) {
//            // Make sure we have a BigDecimal
//            BigDecimal val = null;
//            try {
//                val = new BigDecimal(textValue);
//                gift.setAmount(val);
//                params.put("amount", val);
//            } catch (NumberFormatException ex) {
//                // not a valid big decimal so ignore parameter
//                logger.info("Invalid BigDecimal for gift search [" + textValue + "]", ex);
//            }
//        }
//
//        List<Gift> giftList = giftService.searchGifts(params);
//
//        String sort = request.getParameter("sort");
//        String ascending = request.getParameter("ascending");
//        Boolean sortAscending;
//        if (StringUtils.trimToNull(ascending) != null) {
//            sortAscending = new Boolean(ascending);
//        } else {
//            sortAscending = new Boolean(true);
//        }
//        MutableSortDefinition sortDef = new MutableSortDefinition(sort, true, sortAscending);
//        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(giftList, sortDef);
//        pagedListHolder.resort();
//        pagedListHolder.setMaxLinkedPages(3);
//        pagedListHolder.setPageSize(10);
//        String page = request.getParameter("page");
//
//        Integer pg = 0;
//        if (!StringUtils.isBlank(page)) {
//            pg = Integer.valueOf(page);
//        }
//
//        pagedListHolder.setPage(pg);
//
//        ModelAndView mav = new ModelAndView(getSuccessView());
//        mav.addObject("pagedListHolder", pagedListHolder);
//        mav.addObject("currentSort", sort);
//        mav.addObject("currentAscending", sortAscending);
//        mav.addObject("gift", gift);
//        mav.addObject("giftList", giftList);
//        mav.addObject("giftListSize", giftList.size());
//        return mav;
//    }


    private boolean mapSearchFieldToAmount(HttpServletRequest request) {
        return Boolean.TRUE.toString().equalsIgnoreCase(request.getParameter("autoLoad")) &&
                NumberUtils.isNumber(request.getParameter("searchField"));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(getSuccessView(), StringConstants.FORM, command);
    }
}
