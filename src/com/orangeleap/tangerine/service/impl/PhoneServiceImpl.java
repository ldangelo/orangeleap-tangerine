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

import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.controller.validator.PhoneValidator;
import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.dao.PhoneDao;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

@Service("phoneService")
@Transactional(propagation = Propagation.REQUIRED)
public class PhoneServiceImpl extends AbstractCommunicationService<Phone> implements PhoneService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "phoneDAO")
    private PhoneDao phoneDao;

    @Resource(name = "phoneValidator")
    private PhoneValidator phoneValidator;

    @Resource(name = "phoneManagerEntityValidator")
    private EntityValidator entityValidator;

    @Override
    protected CommunicationDao<Phone> getDao() {
        return phoneDao;
    }

    @Override
    protected Phone createEntity(Long constituentId) {
        return new Phone(constituentId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void maintainResetReceiveCorrespondenceText(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainResetReceiveCorrespondenceText: constituentId = " + constituentId);
        }
        List<Phone> entities = readByConstituentId(constituentId);
        if (entities != null) {
            for (Phone phone : entities) {
                resetReceiveCorrespondenceText(phone);
                getDao().maintainEntity(phone);
            }
        }
    }

    @Override
    public void resetReceiveCorrespondenceText(Phone entity) {
        if (logger.isTraceEnabled()) {
            logger.trace("resetReceiveCorrespondenceText: entity.id = " + entity.getId());
        }
        if (entity != null) {
            entity.setReceiveCorrespondenceText(false);
        }
    }

    @Override
    protected void validate(Phone entity) throws BindException {
        if (entity.getFieldLabelMap() != null && !entity.isSuppressValidation()) {
            BindingResult br = new BeanPropertyBindingResult(entity, "phone");
            BindException errors = new BindException(br);

            entityValidator.validate(entity, errors);
            phoneValidator.validate(entity, errors);

            if (errors.hasErrors()) {
                throw errors;
            }
        }
    }

    @Override
    public List<Phone> readAllPhonesByConstituentId(Long constituentId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPhonesByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return phoneDao.readAllPhonesByConstituentId(constituentId, sort.getSort(), sort.getDir(), sort.getStart(), sort.getLimit(), locale);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        return phoneDao.readCountByConstituentId(constituentId);
    }
}
