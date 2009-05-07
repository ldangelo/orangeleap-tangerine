package com.orangeleap.tangerine.web.customization.tag.inputs;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;

@Component
public abstract class AbstractInput implements ApplicationContextAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSource messageSource;
    
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        messageSource = (MessageSource)context.getBean("staticMessageSource");
    }

    protected String getMessage(String code) {
        return getMessage(code, null);
    }
    
    protected String getMessage(String code, String[] args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
    
    public abstract String handleField(HttpServletRequest request, FieldVO fieldVO);
}
