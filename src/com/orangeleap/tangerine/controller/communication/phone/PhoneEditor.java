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

package com.orangeleap.tangerine.controller.communication.phone;

import javax.annotation.Resource;

import com.orangeleap.tangerine.controller.communication.CommunicationEditor;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.CommunicationService;
import com.orangeleap.tangerine.service.PhoneService;

public class PhoneEditor extends CommunicationEditor<Phone> {

    @Resource(name="phoneService")
    private PhoneService phoneService;

    public PhoneEditor() {
        super();
    }

    public PhoneEditor(PhoneService phoneService, String constituentId) {
        super(constituentId);
        this.phoneService = phoneService;
    }

    @Override
    protected Phone createEntity(Long constituentId) {
        return new Phone(constituentId);
    }

    @Override
    protected CommunicationService<Phone> getCommunicationService() {
        return phoneService;
    }
}
