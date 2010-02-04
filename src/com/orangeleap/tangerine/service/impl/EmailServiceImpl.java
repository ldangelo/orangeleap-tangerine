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

import com.orangeleap.tangerine.service.validator.EmailValidator;
import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.dao.EmailDao;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.service.EmailService;
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

@Service("emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl extends AbstractCommunicationService<Email> implements EmailService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "emailDAO")
    private EmailDao emailDao;

    @Resource(name = "emailValidator")
    private EmailValidator emailValidator;

    @Resource(name = "emailManagerEntityValidator")
    private com.orangeleap.tangerine.service.validator.EntityValidator entityValidator;
    
    @Override
    protected CommunicationDao<Email> getDao() {
        return emailDao;
    }

    @Override
    protected Email createEntity(Long constituentId) {
        return new Email(constituentId);
    }

    @Override
    protected void validate(Email entity) throws BindException {
        if (entity.getFieldLabelMap() != null && !entity.isSuppressValidation()) {
            BindingResult br = new BeanPropertyBindingResult(entity, "email");
            BindException errors = new BindException(br);

            entityValidator.validate(entity, errors);
            emailValidator.validate(entity, errors);

            if (errors.hasErrors()) {
                throw errors;
            }
        }
    }

    @Override
    public List<Email> readAllEmailsByConstituentId(Long constituentId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllEmailsByConstituentId: constituentId = " + constituentId + " sort = " + sort);
        }
        return emailDao.readAllEmailsByConstituentId(constituentId, sort.getSort(), sort.getDir(), sort.getStart(), sort.getLimit(), locale);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        return emailDao.readCountByConstituentId(constituentId);
    }
}
