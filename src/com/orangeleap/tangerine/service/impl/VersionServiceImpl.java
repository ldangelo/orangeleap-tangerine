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

import com.orangeleap.tangerine.dao.VersionDao;
import com.orangeleap.tangerine.service.VersionService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("versionService")
public class VersionServiceImpl extends AbstractTangerineService implements VersionService {


    public final static String ORANGE_ID = "ORANGE";

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "versionDAO")
    private VersionDao versionDao;

    @Override
    public void checkOrangeLeapSchemaCompatible() {

        String message = "";
        try {
            int majorVersion = versionDao.selectVersion(ORANGE_ID).getMajorVersion();
            if (majorVersion == OrangeLeapSchemaVersion.ORANGE_SCHEMA_MAJOR_VERSION) {
                logger.info("Schema version successfully checked.");
                return;
            }
            message = "Invalid database version " + majorVersion + " does not match program schema version " + OrangeLeapSchemaVersion.ORANGE_SCHEMA_MAJOR_VERSION;
        } catch (Exception e) {
            e.printStackTrace();
            message = "Unable to determine database schema version.";
        }

        logger.fatal(message);
        throw new RuntimeException(message);

    }

}
