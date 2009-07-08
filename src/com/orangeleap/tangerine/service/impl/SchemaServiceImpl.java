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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.SchemaDao;
import com.orangeleap.tangerine.service.SchemaService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("schemaService")
@Transactional
public class SchemaServiceImpl extends AbstractTangerineService implements SchemaService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "schemaDAO")
    private SchemaDao schemaDao;
    
    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    
    @Override
	public List<String> readSchemas() {
    	List<String> list = schemaDao.readSchemas();
    	Iterator<String> it = list.iterator();
    	while (it.hasNext()) {
    		String s = (String)it.next();
    		if (EXCLUDE_LIST.contains(s) || s.endsWith("theguru")) it.remove();
    	}
		return list;
	}
    
    // Schemas to exclude
    private static final List<String> EXCLUDE_LIST = new ArrayList<String>();
    static {
    	EXCLUDE_LIST.add("mysql");
    	EXCLUDE_LIST.add("information_schema");
    	EXCLUDE_LIST.add("test");
    	EXCLUDE_LIST.add("jasperserver");
    	EXCLUDE_LIST.add("theguru");
    }

    @Override
	public void setSchema(String schema) {
        tangerineUserHelper.setSystemUserAndSiteName(schema);
	}

    
}
