package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Commitment;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.service.CommitmentService;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.SiteService;
import com.mpower.type.PageType;

public class GiftFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CommitmentService commitmentService;

    private GiftService giftService;

    private PersonService personService;

    private SiteService siteService;

    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String giftId = request.getParameter("giftId");
        Gift gift = null;
        if (giftId == null) {
            String commitmentId = request.getParameter("commitmentId");
            Commitment commitment = null;
            if (commitmentId != null) {
                commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
                if (commitment == null) {
                    logger.error("**** commitment not found for id: " + commitmentId);
                    return gift;
                }
                gift = giftService.createGift(commitment);
                // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
                gift.setPerson(commitment.getPerson());
            }
            if (gift == null) {
                String personId = request.getParameter("personId");
                Person person = null;
                if (personId != null) {
                    person = personService.readPersonById(Long.valueOf(personId));
                    if (person == null) {
                        logger.error("**** person not found for id: " + personId);
                        return gift;
                    }
                    gift = giftService.createDefaultGift(person);
                    // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
                    gift.setPerson(person);
                }
            }
        } else {
            gift = giftService.readGiftById(new Long(giftId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            gift.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), gift);
            gift.setFieldValueMap(valueMap);
        }
        return gift;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        Gift gift = (Gift) command;

        // validate required fields

        // TODO: This code is temporary validation to strip out invalid distribution lines.
        Iterator<DistributionLine> distLineIter = gift.getDistributionLines().iterator();
        while (distLineIter.hasNext()) {
            DistributionLine line = distLineIter.next();
            if (line == null || line.getAmount() == null) {
                distLineIter.remove();
            }
        }

        Gift current = giftService.maintainGift(gift);

        ModelAndView mav = new ModelAndView("redirect:/giftView.htm");
        mav.addObject("giftId", current.getId());
        return mav;
    }
}
