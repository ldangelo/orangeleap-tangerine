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

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.orangeleap.tangerine.dao.BackgroundDao;
import com.orangeleap.tangerine.domain.Background;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.BackgroundService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("backgroundService")
@Transactional(propagation = Propagation.REQUIRED)
public class BackgroundServiceImpl extends AbstractTangerineService implements BackgroundService, ApplicationContextAware {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "backgroundDAO")
    private BackgroundDao backgroundDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Resource(name = "auditService")
    protected AuditService auditService;
    
    @Resource(name = "backgroundEntityValidator")
    protected com.orangeleap.tangerine.service.validator.EntityValidator entityValidator;

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public Background maintainBackground(Background background) throws BindException {
       
    	if (logger.isTraceEnabled()) {
            logger.trace("maintainBackground: background = " + background);
        }
        if (background.getConstituentId() == null || background.getConstituentId() == 0) {
            return null;
        }

        if (background.getFieldLabelMap() != null && !background.isSuppressValidation()) {

            BindingResult br = new BeanPropertyBindingResult(background, "background");
            BindException errors = new BindException(br);

            entityValidator.validate(background, errors);
            if (errors.hasErrors()) {
	            throw errors;
            }
        }

        Long lookupUserId = tangerineUserHelper.lookupUserId();
        if (lookupUserId != null) {
        	background.setCustomFieldValue("recordedBy", "" + tangerineUserHelper.lookupUserId());
        }
        
        Background savedBackground = backgroundDao.maintainBackground(background);
        auditService.auditObject(savedBackground);
        
        savedBackground = backgroundDao.readBackgroundById(savedBackground.getId()); // populate update/create date in object
        
        return savedBackground;
    }

    
    
    @Override
    public Background readBackgroundById(Long backgroundId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBackgroundById: backgroundId = " + backgroundId);
        }
        return backgroundDao.readBackgroundById(backgroundId);
    }

    @Override
    public Background readBackgroundByIdCreateIfNull(String backgroundId, Constituent constituent) {
        if (logger.isTraceEnabled()) {
            logger.trace("readBackgroundByIdCreateIfNull: backgroundId = " + backgroundId + " constituentId = " + (constituent == null ? null : constituent.getId()));
        }
        Background background = null;
        if (backgroundId == null) {
        	background = this.createBackground(constituent);
        } else {
        	background = this.readBackgroundById(Long.valueOf(backgroundId));
        }
        return background;
    }

    private Background createBackground(Constituent constituent) {
    	Background background = new Background();
    	background.setConstituentId(constituent.getId());
        return background;
    }

    // Always returns all
    @Override
    public List<Background> readAllBackgroundByConstituentId(Long constituentId, SortInfo sort, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllBackgroundByConstituentId: constituentId = " + constituentId );
        }
        return backgroundDao.readBackgroundByConstituentId(constituentId);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        return backgroundDao.readCountByConstituentId(constituentId);
    }

}
