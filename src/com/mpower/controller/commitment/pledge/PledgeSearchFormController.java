package com.mpower.controller.commitment.pledge;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.service.CommitmentService;
import com.mpower.service.SessionService;
import com.mpower.type.CommitmentType;

//TODO: refactor this and RecurringGiftSearchFormController into one class
public class PledgeSearchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="commitmentService")
    private CommitmentService commitmentService;

    @Resource(name="sessionService")
    private SessionService sessionService;

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("formBackingObject:");
        }
        Person p = new Person();
        p.setSite(sessionService.lookupSite());
        Commitment g = new Commitment();
        g.setPerson(p);
        return g;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("onSubmit:");
        }
        Commitment commitment = (Commitment) command;
        BeanWrapper bw = new BeanWrapperImpl(commitment);
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String param = enu.nextElement();
            if (StringUtils.trimToNull(request.getParameter(param)) != null) {
                if (bw.isReadableProperty(param)) {
                    params.put(param, bw.getPropertyValue(param));
                }
            }
        }

        List<Commitment> commitmentList = commitmentService.searchCommitments(CommitmentType.PLEDGE, params);
        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
        mav.addObject("commitmentList", commitmentList);
        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
