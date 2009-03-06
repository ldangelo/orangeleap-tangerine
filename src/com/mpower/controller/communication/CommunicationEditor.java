package com.mpower.controller.communication;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.model.communication.AbstractCommunicationEntity;
import com.mpower.service.CommunicationService;
import com.mpower.util.StringConstants;

public abstract class CommunicationEditor<T extends AbstractCommunicationEntity> extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public CommunicationEditor() {
        super();
    }

    public CommunicationEditor(String constituentId) {
        super(constituentId);
    }

    protected abstract CommunicationService<T> getCommunicationService();
    protected abstract T createEntity(Long constituentId);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long id = NumberUtils.createLong(text);
            T entity = getCommunicationService().readById(id);
            setValue(entity);
        }
        else if (StringConstants.NEW.equals(text)){
            T entity = createEntity(getPerson().getId());
            entity.setUserCreated(true);
            setValue(entity);
        }
    }
}
