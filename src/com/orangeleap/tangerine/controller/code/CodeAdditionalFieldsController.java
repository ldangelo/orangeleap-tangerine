package com.orangeleap.tangerine.controller.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.customization.PicklistItem;

public class CodeAdditionalFieldsController extends CodeHelperController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private String additionalFieldsView;

    public void setAdditionalFieldsView(String additionalFieldsView) {
        this.additionalFieldsView = additionalFieldsView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = super.handleRequestInternal(request, response);
        mv.setViewName(additionalFieldsView);
        if (isResultsOnlyView(request) != null) {
            mv.setViewName(isResultsOnlyView(request));
        }
        getSelectedCodes((List<PicklistItem>)mv.getModel().get(CODES), request, mv);
        mv.addObject("additionalCodes", getAdditionalCodes(request));
        return mv;
    }

    @SuppressWarnings("unchecked")
    public void getSelectedCodes(List<PicklistItem> allCodes, HttpServletRequest request, ModelAndView mv) {
        List<PicklistItem> selectedCodes = new ArrayList<PicklistItem>();
        List<PicklistItem> availableCodes = new ArrayList<PicklistItem>();
        if (allCodes != null) {
            String selectedCodesStr = request.getParameter("selectedCodes");
            if (StringUtils.hasText(selectedCodesStr)) {
                Set<String> chosenCodesStrings = StringUtils.commaDelimitedListToSet(selectedCodesStr);
                if (chosenCodesStrings != null) {
                    for (PicklistItem aCode : allCodes) {
                        if (chosenCodesStrings.contains(aCode.getItemName())) {
                            selectedCodes.add(aCode);
                        }
                        else {
                            availableCodes.add(aCode);
                        }
                    }
                }
            }
            else {
                availableCodes = allCodes;
            }
        }
        mv.addObject("selectedCodes", selectedCodes);
        mv.addObject("availableCodes", availableCodes);
    }
    
    private List<String> getAdditionalCodes(HttpServletRequest request) {
        List<String> additionalCodes = new ArrayList<String>();
        String additionalCodesStr = request.getParameter("additionalCodes");
        if (StringUtils.hasText(additionalCodesStr)) {
            String[] s = StringUtils.commaDelimitedListToStringArray(additionalCodesStr);
            for (String str : s) {
                // TODO: unescape commas?
                additionalCodes.add(str + "\n");
            }
        }
        return additionalCodes;
    }
}
