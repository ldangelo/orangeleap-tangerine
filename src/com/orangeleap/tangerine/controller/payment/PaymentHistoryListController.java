package com.orangeleap.tangerine.controller.payment;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PaymentHistoryService;

public class PaymentHistoryListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="paymentHistoryService")
    private PaymentHistoryService paymentHistoryService;
    
    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("handleRequestInternal:");
        }
        String personId = request.getParameter("personId");

        List<PaymentHistory> historyList = paymentHistoryService.readPaymentHistory(Long.valueOf(personId));
        Person constituent = constituentService.readConstituentById(Long.valueOf(personId));

        String sort = request.getParameter("sort");
        String ascending = request.getParameter("ascending");
        Boolean sortAscending;
        if (StringUtils.trimToNull(ascending) != null) {
            sortAscending = new Boolean(ascending);
        } else {
            sortAscending = new Boolean(true);
        }
        MutableSortDefinition sortDef = new MutableSortDefinition(sort,true,sortAscending);
        PagedListHolder pagedListHolder = new PagedListHolder(historyList,sortDef);
        pagedListHolder.resort();
        pagedListHolder.setMaxLinkedPages(3);
        pagedListHolder.setPageSize(10);
        String page = request.getParameter("page");

        Integer pg = 0;
        if (!StringUtils.isBlank(page)) {
            pg = Integer.valueOf(page);
        }

        pagedListHolder.setPage(pg);

        
        
        ModelAndView mav = new ModelAndView(super.getViewName());
        if (constituent != null) {
            mav.addObject("person", constituent);
        }
        mav.addObject("paymentHistoryList", historyList);
        mav.addObject("pagedListHolder", pagedListHolder);
        mav.addObject("currentSort", sort);
        mav.addObject("currentAscending", sortAscending);
        mav.addObject("paymentHistoryListSize", historyList.size());
        return mav;
    }
}
