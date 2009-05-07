package com.orangeleap.tangerine.web.customization.tag.inputs.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("creditCardExpirationDisplayInput")
public class CreditCardExpirationDisplayInput extends DateDisplayInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public String getDateFormat() {
        return "MM / yyyy";
    }
}
