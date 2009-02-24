package com.mpower.test.dao.jdbc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.QueryLookupExecutorDao;
import com.mpower.domain.model.Person;

@ContextConfiguration(locations = { "classpath:/test-jdbc-application-context.xml" })
public class JdbcQueryLookupExecutorDaoTest extends AbstractTestNGSpringContextTests {

    private QueryLookupExecutorDao queryLookupExecutorDAO;

    @BeforeMethod
    public void setup() {
        queryLookupExecutorDAO = (QueryLookupExecutorDao)super.applicationContext.getBean("queryLookupExecutorDAO");
    }

    @Test(groups = { "testExecuteQueryLookup" })
    public void testExecuteQueryLookupNoResults() throws Exception {
        Map<String, String> parameters = new LinkedHashMap<String, String>(1); 
        parameters.put("siteName", "company1");
        List<Object> results = queryLookupExecutorDAO.executeQueryLookup("SELECT person_id, organization_name, last_name, first_name, middle_name, suffix FROM PERSON WHERE site_name = :siteName AND constituent_type = 'individual' AND title = 'Sucka.'", parameters);
        assert results != null && results.isEmpty();
    }

    @Test(groups = { "testExecuteQueryLookup" })
    public void testExecuteQueryLookup() throws Exception {
        Map<String, String> parameters = new LinkedHashMap<String, String>(1); 
        parameters.put("siteName", "company1");
        List<Object> results = queryLookupExecutorDAO.executeQueryLookup("SELECT person_id, organization_name, last_name, first_name, middle_name, suffix FROM PERSON WHERE site_name = :siteName AND constituent_type = 'individual' AND title = 'Rev.'", parameters);
        assert results != null && results.size() == 1;
        assert results.get(0) instanceof Person;
        Person person = (Person)results.get(0);
        assert person.getId() > 0;
        assert "Billy Graham Ministries".equals(person.getOrganizationName());
        assert "Graham".equals(person.getLastName());
        assert "Billy".equals(person.getFirstName());
        assert person.getMiddleName() == null;
        assert person.getSuffix() == null;
    }
}