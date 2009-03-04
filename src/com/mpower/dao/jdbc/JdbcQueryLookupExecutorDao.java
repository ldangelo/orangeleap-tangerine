package com.mpower.dao.jdbc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mpower.dao.interfaces.QueryLookupExecutorDao;
import com.mpower.util.StringConstants;
import com.mpower.util.TangerineUserHelper;

@Repository("queryLookupExecutorDAO")
public class JdbcQueryLookupExecutorDao implements QueryLookupExecutorDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource(name="tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Query lookups map to RowMapper classes based on the first table in the FROM clause
     * e.g. FROM CONSTITUENT -> com.mpower.dao.jdbc.rowmappers.ConstituentRowMapper
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> executeQueryLookup(String queryString, Map<String, String> parameters) {
        if (logger.isDebugEnabled()) {
            logger.debug("executeQueryLookup: queryString = " + queryString + " parameters = " + parameters);
        }
        parameters.put(StringConstants.SITE_NAME, tangerineUserHelper.lookupUserSiteName());
        RowMapper rowMapper = getRowMapper(queryString);
        return namedParameterJdbcTemplate.query(queryString, parameters, rowMapper);
    }
    
    private RowMapper getRowMapper(String queryString) {
		try {
	        String table = getMainTable(queryString);
			return (RowMapper) Class.forName("com.mpower.dao.jdbc.rowmappers." + table + "RowMapper").newInstance();
		} catch (Exception e) {
		    if (logger.isErrorEnabled()) {
		        logger.error("Invalid query lookup SQL", e);
		    }
			throw new RuntimeException("Invalid query lookup string " + queryString);
		}
    }
    
    private String getMainTable(String query) {
    	String [] tokens = query.split("\\s+");
    	boolean fromClause = false;
    	for (String token : tokens) {
    		if (fromClause) {
    			String table = token.toLowerCase();
    			return table.substring(0,1).toUpperCase() + table.substring(1);
    		}
    		if (token.trim().toLowerCase().equals("from")) {
                fromClause = true;
            }
    	}
    	return "";
    }
    
}
