package com.orangeleap.tangerine.controller.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class MultiPicklistController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private final CodeValueComparator comparator = new CodeValueComparator();

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(getViewName());
        mv.addObject("picklistOptions", getSelectedOptions(request));
        mv.addObject("additionalOptions", getAdditionalOptions(request));
        mv.addObject("modalTitle", "Select " + request.getParameter("labelText"));
        mv.addObject("showAdditionalField", Boolean.valueOf(request.getParameter("showAdditionalField")));
        return mv;
    }

    /**
     * Expects a query string in the format selectedOptions=code1|displayValue1|reference1|selected1^code2|displayValue2|reference2|selected2^ ...
     * where the key is irrelevant and the value includes the code (index 0), display value (index 1), reference (index 2), and if selected (index 3), in that order
     * @param request
     * @return
     */
    private List<CodeValue> getSelectedOptions(HttpServletRequest request) {
        List<CodeValue> options = new ArrayList<CodeValue>();
        String selectedOptions = request.getParameter("selectedOptions");
        if (StringUtils.hasText(selectedOptions)) {
            String[] s = StringUtils.delimitedListToStringArray(selectedOptions, "^");
            if (s != null) {
                for (String opts : s) {
                    String[] a = StringUtils.delimitedListToStringArray(opts, "|");
                    if (a != null && a.length == 4) {
                        options.add(new CodeValue(a[0], a[1], a[2], "true".equals(a[3])));
                    }
                }
            }
        }
        Collections.sort(options, comparator);
        return options;
    }
    
    private List<String> getAdditionalOptions(HttpServletRequest request) {
        List<String> additionalOptions = new ArrayList<String>();
        String additionalOptionsStr = request.getParameter("additionalFieldOptions");
        if (StringUtils.hasText(additionalOptionsStr)) {
            String[] s = StringUtils.commaDelimitedListToStringArray(additionalOptionsStr);
            for (String str : s) {
                // TODO: unescape commas?
                additionalOptions.add(str + "\n");
            }
        }
        return additionalOptions;
    }
}
