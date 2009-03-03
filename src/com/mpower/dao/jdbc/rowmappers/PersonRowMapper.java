package com.mpower.dao.jdbc.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mpower.domain.model.Person;

public class PersonRowMapper implements RowMapper  {
	
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
