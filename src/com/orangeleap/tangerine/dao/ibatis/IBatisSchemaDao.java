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

package com.orangeleap.tangerine.dao.ibatis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.SchemaDao;

/** 
 * reads schemas
 */
@Repository("schemaDAO")
public class IBatisSchemaDao extends AbstractIBatisDao implements SchemaDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisSchemaDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
	public List<String> readSchemas() {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("readSchemas");
        }
        
        List<String> list = new ArrayList<String>();
		try {
			Statement stat = getDataSource().getConnection().createStatement();
			try {
				ResultSet rs = stat.executeQuery("SHOW DATABASES");
				while (rs.next()) {
					list.add(rs.getString(1));
				}
				rs.close();
			} finally {
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(e);
		}
		
		return list;
		
	}

}