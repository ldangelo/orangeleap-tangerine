package com.mpower.dao.jdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mpower.domain.model.Person;

public class ConstituentRowMapper implements RowMapper  {
	
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person constituent = new Person();
        constituent.setId(rs.getLong("constituent_id"));
        constituent.setOrganizationName(rs.getString("organization_name"));
        constituent.setLastName(rs.getString("last_name"));
        constituent.setFirstName(rs.getString("first_name"));
        constituent.setMiddleName(rs.getString("middle_name"));
        constituent.setSuffix(rs.getString("suffix"));
        return constituent;
    }
    
}
