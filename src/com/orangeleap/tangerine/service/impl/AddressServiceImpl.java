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

package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;

import com.orangeleap.tangerine.dao.AddressDao;
import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.controller.validator.AddressValidator;
import com.orangeleap.tangerine.controller.validator.EntityValidator;

@Service("addressService")
@Transactional(propagation = Propagation.REQUIRED)
public class AddressServiceImpl extends AbstractCommunicationService<Address> implements AddressService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "addressDAO")
    private AddressDao addressDao;

    @Resource(name = "addressValidator")
    private AddressValidator addressValidator;

    @Resource(name = "addressManagerEntityValidator")
    private EntityValidator entityValidator;

    @Override
    protected CommunicationDao<Address> getDao() {
        return addressDao;
    }

    @Override
    protected Address createEntity(Long constituentId) {
        return new Address(constituentId);
    }

    @Override
    protected void validate(Address entity) throws BindException {
        if (entity.getFieldLabelMap() != null && !entity.isSuppressValidation()) {
            BindingResult br = new BeanPropertyBindingResult(entity, "address");
            BindException errors = new BindException(br);

            entityValidator.validate(entity, errors);
            addressValidator.validate(entity, errors);

            if (errors.hasErrors()) {
                throw errors;
            }
        }
    }
}
