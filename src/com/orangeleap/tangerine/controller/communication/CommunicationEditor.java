package com.orangeleap.tangerine.controller.communication;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.controller.constituent.RequiresConstituentEditor;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import com.orangeleap.tangerine.service.CommunicationService;

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
//        else if (StringConstants.NEW.equals(text)){
//            T entity = createEntity(getConstituent().getId());
//            entity.setUserCreated(true);
//            setValue(entity);
//        }
    }
}
