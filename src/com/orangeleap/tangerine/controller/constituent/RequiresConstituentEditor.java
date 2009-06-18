package com.orangeleap.tangerine.controller.constituent;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;

public abstract class RequiresConstituentEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

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

    protected Constituent getConstituent() {
        return constituentService.readConstituentById(new Long(constituentId));
    }
}
