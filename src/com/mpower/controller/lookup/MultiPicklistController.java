package com.mpower.controller.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.web.customization.FieldVO;
import com.mpower.web.customization.inputs.CodeValue;
import com.mpower.web.customization.inputs.CodeValueComparator;

public class MultiPicklistController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private final CodeValueComparator comparator = new CodeValueComparator();

    public static final String SELECTED_CODES = "selectedCodes";
    public static final String AVAILABLE_CODES = "availableCodes";
    public static final String REFERENCE_VALUES = "referenceValues";
    public static final String DISPLAY_VALUES = "displayValues";

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(getViewName());
        mv.addObject("picklistOptions", getParameters(request));
        mv.addObject("modalTitle", "Select " + request.getParameter("labelText"));
        return mv;
    }

    private List<CodeValue> getParameters( HttpServletRequest request) {
        List<CodeValue> options = new ArrayList<CodeValue>();

        request.getParameterNames();
        List<String> selectedCodes = getDelimitedStringAsList(request.getParameter(SELECTED_CODES), FieldVO.NORMAL_DELIMITER);
        List<String> availableCodes = getDelimitedStringAsList(request.getParameter(AVAILABLE_CODES), FieldVO.NORMAL_DELIMITER);
        List<String> referenceValues = getDelimitedStringAsList(request.getParameter(REFERENCE_VALUES), FieldVO.NORMAL_DELIMITER);
        List<String> displayValues = getDelimitedStringAsList(request.getParameter(DISPLAY_VALUES), FieldVO.DISPLAY_VALUE_DELIMITER);

        for (int i = 0; i < availableCodes.size(); i++) {
            options.add(new CodeValue(availableCodes.get(i), displayValues.get(i), referenceValues.get(i), selectedCodes == null ? false : selectedCodes.contains(availableCodes.get(i))));
        }

        Collections.sort(options, comparator);
        return options;
    }

    @SuppressWarnings("unchecked")
    private List<String> getDelimitedStringAsList(String paramValue, String delimiter) {
        String[] vals = StringUtils.delimitedListToStringArray(paramValue, delimiter);
        return CollectionUtils.arrayToList(vals);
    }
}
