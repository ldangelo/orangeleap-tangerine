package com.orangeleap.tangerine.web.customization.tag.inputs.impl.display;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;

@Component("creditCardExpirationDisplayInput")
public class CreditCardExpirationDisplayInput extends DateDisplayInput {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    @Override
    public String getDateFormat() {
        return "MM / yyyy";
    }
}
