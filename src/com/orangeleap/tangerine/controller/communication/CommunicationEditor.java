/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.communication;

import com.orangeleap.tangerine.controller.constituent.RequiresConstituentEditor;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import com.orangeleap.tangerine.service.CommunicationService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;

public abstract class CommunicationEditor<T extends AbstractCommunicationEntity> extends RequiresConstituentEditor {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

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
