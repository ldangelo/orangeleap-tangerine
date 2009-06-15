package com.orangeleap.tangerine.controller.constituent;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;

public class ConstituentEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	if(StringUtils.trimToNull(text)==null) {
            return;
        }
        long constituentId = Long.valueOf(StringUtils.trimToNull(text));
        Constituent constituent = constituentService.readConstituentById(constituentId);
        setValue(constituent);
    }
    
   // public String getAsText() {
    //	if (this.getValue() instanceof String) {
    //	    return (String)this.getValue();
    //	}
   // 	return ("" + this.getValue());
   //     }
}
