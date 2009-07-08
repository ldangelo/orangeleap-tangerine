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

import com.orangeleap.tangerine.dao.CustomFieldRelationshipDao;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.customization.CustomFieldRelationship;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRelationship;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.CustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("customFieldRelationshipService")
public class CustomFieldRelationshipServiceImpl extends AbstractTangerineService implements CustomFieldRelationshipService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name = "customFieldRelationshipDAO")
    private CustomFieldRelationshipDao customFieldRelationshipDao;

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;


    // Used for custom field maintenance
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomFieldRelationship maintainCustomFieldRelationshipCustomFields(CustomFieldRelationship CustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainCustomFieldRelationship: id = " + CustomFieldRelationship.getId());
        }
        CustomFieldRelationship = customFieldRelationshipDao.maintainCustomFieldRelationship(CustomFieldRelationship);

        //auditService.auditObject(CustomFieldRelationship, CustomFieldRelationship);  // TODO
        return CustomFieldRelationship;
    }

    @Override
    public CustomFieldRelationship readById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("CustomFieldRelationshipService.readById: id = " + id);
        }
        CustomFieldRelationship CustomFieldRelationship = customFieldRelationshipDao.readById(id);
        return CustomFieldRelationship;
    }

    @Override
    public String getMasterFieldDefinitionId(String id) {
        // Get corresponding master field definition id for relationship
        FieldDefinition fd = fieldDao.readFieldDefinition(id);
        if (fd == null) return null;
        List<FieldRelationship> frs = fieldDao.readMasterFieldRelationships(fd.getId());
        if (frs.size() == 0) frs = fieldDao.readDetailFieldRelationships(fd.getId());
        if (frs.size() == 0) return null;
        FieldRelationship fr = frs.get(0);
        return fr.getMasterRecordField().getId();
    }

    @Override
    public CustomFieldRelationship readByFieldDefinitionId(String id) {
        if (logger.isTraceEnabled()) {
            logger.trace("CustomFieldRelationshipService.readByFieldDefinitionId: id = " + id);
        }

        CustomFieldRelationship CustomFieldRelationship = customFieldRelationshipDao.readByFieldDefinitionId(id);
        return CustomFieldRelationship;
    }

    @Override
    public List<CustomFieldRelationship> readAllBySite() {
        return customFieldRelationshipDao.readAllBySite();
    }

}
