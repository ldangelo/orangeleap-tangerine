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

import com.orangeleap.tangerine.web.customization.FieldVO;

public class MultiPicklistController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private final CodeValueComparator comparator = new CodeValueComparator();

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView(getViewName());
        mv.addObject("picklistOptions", getOptions(request));
        mv.addObject("modalTitle", "Select " + request.getParameter("labelText"));
        return mv;
    }

    /**
     * Expects a query string in the format A=code1|displayValue1|reference1|selected1&B=code2|displayValue2|reference2|selected2& ...
     * where the key is irrelevant and the value includes the code (index 0), display value (index 1), reference (index 2), and if selected (index 3), in that order
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<CodeValue> getOptions( HttpServletRequest request) {
        List<CodeValue> options = new ArrayList<CodeValue>();

        List<String[]> values = new ArrayList<String[]>(request.getParameterMap().values());
        for (int x = 0; x < values.size(); x++) {
            String[] optionString = values.get(x);
            if (optionString != null && optionString.length > 0) {
                String[] s = StringUtils.delimitedListToStringArray(optionString[0], FieldVO.DISPLAY_VALUE_DELIMITER);
                if (s != null && s.length == 4) {
                    options.add(new CodeValue(s[0], s[1], s[2], "true".equals(s[3])));
                }
            }
        }

        Collections.sort(options, comparator);
        return options;
    }
}
