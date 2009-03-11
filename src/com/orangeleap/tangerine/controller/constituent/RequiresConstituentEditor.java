package com.orangeleap.tangerine.controller.constituent;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.ConstituentService;

public abstract class RequiresConstituentEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="constituentService")
    protected ConstituentService constituentService;
    protected String constituentId;

    public RequiresConstituentEditor() {
        super();
    }

    public RequiresConstituentEditor(String constituentId) {
        super();
        this.constituentId = constituentId;
    }

    protected Person getPerson() {
        return constituentService.readConstituentById(new Long(constituentId));
    }
}
