package com.orangeleap.tangerine.controller.communicationHistory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;

public class CommunicationHistoryListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("handleRequestInternal:");
        }
        String constituentId = request.getParameter("constituentId");
        Constituent constituent = constituentService.readConstituentById(Long.valueOf(constituentId));
        
        ModelAndView mav = new ModelAndView(super.getViewName());
        if (constituent != null) {
            mav.addObject("constituent", constituent);
        }
        return mav;
    }
}
