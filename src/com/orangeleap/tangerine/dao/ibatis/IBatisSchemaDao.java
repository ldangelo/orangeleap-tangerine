package com.orangeleap.tangerine.dao.ibatis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisSchemaDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
	public List<String> readSchemas() {
    	
        if (logger.isDebugEnabled()) {
            logger.debug("readSchemas");
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