package com.orangeleap.tangerine.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.StringConstants;

public abstract class TangerineListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    protected ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("handleRequestInternal:");
        }
        Long constituentId = Long.valueOf(request.getParameter(StringConstants.CONSTITUENT_ID));

        List<? extends GeneratedId> list = getList(constituentId);
        Constituent constituent = constituentService.readConstituentById(constituentId);

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (constituent != null) {
            mav.addObject(StringConstants.CONSTITUENT, constituent);
        }
        mav.addObject("list", list);
        mav.addObject("listSize", list.size());
        return mav;
    }
    
    protected abstract List<? extends GeneratedId> getList(Long constituentId);
}
