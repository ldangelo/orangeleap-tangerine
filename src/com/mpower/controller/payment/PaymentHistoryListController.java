package com.mpower.controller.payment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.PaymentHistory;
import com.mpower.domain.Person;
import com.mpower.service.PaymentHistoryService;
import com.mpower.service.PersonService;

public class PaymentHistoryListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentHistoryService paymentHistoryService;
    
    private PersonService personService;

    public void setPaymentHistoryService(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }
    
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }



    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("**** in handleRequestInternal()");
        String personId = request.getParameter("personId");

        List<PaymentHistory> historyList = paymentHistoryService.readPaymentHistory(Long.valueOf(personId));
        Person person = personService.readPersonById(Long.valueOf(personId));

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
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("paymentHistoryList", historyList);
        mav.addObject("pagedListHolder", pagedListHolder);
        mav.addObject("currentSort", sort);
        mav.addObject("currentAscending", sortAscending);
        mav.addObject("paymentHistoryListSize", historyList.size());
        return mav;
    }
}
