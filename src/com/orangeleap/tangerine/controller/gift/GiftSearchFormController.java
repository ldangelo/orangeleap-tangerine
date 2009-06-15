package com.orangeleap.tangerine.controller.gift;

import java.util.*;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.TangerinePagedListHolder;

public class GiftSearchFormController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "giftService")
    private GiftService giftService;

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        if (logger.isTraceEnabled()) {
            logger.trace("formBackingObject:");
        }
        Constituent p = new Constituent();
        p.setSite(sessionService.lookupSite());
        Gift g = new Gift();
        g.setConstituent(p);
        return g;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("onSubmit:");
        }

        Gift gift = (Gift) command;
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(gift);
        Map<String, Object> params = new HashMap<String, Object>();

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String param = enu.nextElement();
            if (!param.equalsIgnoreCase("type") &&
                !param.equalsIgnoreCase("page") &&
                !param.equalsIgnoreCase("view") &&
                !param.equalsIgnoreCase("sort") &&
                StringUtils.trimToNull(request.getParameter(param)) != null) {

                if (bw.isReadableProperty(param)){
                    params.put(param, bw.getPropertyValue(param));
                }
            }
        }

        // Search box is used for both constituent and gift search, the value we need
        // is actually in an input field called lastName
        String textValue = request.getParameter("lastName");

        // if this is not null, it means they did a general search for Gift from the
        // search box in the navigation bar
        if (textValue != null) {
            // Make sure we have a BigDecimal
            BigDecimal val = null;
            try {
                val = new BigDecimal(textValue);
                gift.setAmount(val);
                params.put("amount", val);
            } catch (NumberFormatException ex) {
                // not a valid big decimal so ignore parameter
                logger.info("Invalid BigDecimal for gift search [" + textValue + "]", ex);
            }
        }

        List<Gift> giftList = giftService.searchGifts(params);

        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } else {
            sortAscending = new Boolean(true);
        }
        MutableSortDefinition sortDef = new MutableSortDefinition(sort, true, sortAscending);
        TangerinePagedListHolder pagedListHolder = new TangerinePagedListHolder(giftList, sortDef);
        pagedListHolder.resort();
        pagedListHolder.setMaxLinkedPages(3);
        pagedListHolder.setPageSize(10);
        String page = request.getParameter("page");

        Integer pg = 0;
        if (!StringUtils.isBlank(page)) {
            pg = Integer.valueOf(page);
        }

        pagedListHolder.setPage(pg);

        ModelAndView mav = new ModelAndView(getSuccessView());
        mav.addObject("pagedListHolder", pagedListHolder);
        mav.addObject("currentSort", sort);
        mav.addObject("currentAscending", sortAscending);
        mav.addObject("gift", gift);
        mav.addObject("giftList", giftList);
        mav.addObject("giftListSize", giftList.size());
        return mav;
    }
}
