package com.mpower.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.mpower.domain.model.Person;

@Repository("queryLookupExecutorDAO")
public class JdbcQueryLookupExecutorDao implements QueryLookupExecutorDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PersonRowMapper rowMapper = new PersonRowMapper();
    
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Query lookups are assumed to be for Person domain objects only; TODO: refactor 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> executeQueryLookup(String queryString, Map<String, String> parameters) {
        if (logger.isDebugEnabled()) {
            logger.debug("executeQueryLookup: queryString = " + queryString + " parameters = " + parameters);
        }
        return namedParameterJdbcTemplate.query(queryString, parameters, rowMapper);
    }
    
    private class PersonRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getLong("person_id"));
            person.setOrganizationName(rs.getString("organization_name"));
            person.setLastName(rs.getString("last_name"));
            person.setFirstName(rs.getString("first_name"));
            person.setMiddleName(rs.getString("middle_name"));
            person.setSuffix(rs.getString("suffix"));
            return person;
        }
    }
}
