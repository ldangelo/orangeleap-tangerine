package com.orangeleap.tangerine.test.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.ReferenceType;

public class IBatisQueryLookupDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private QueryLookupDao queryLookupDao;

    @BeforeMethod
    public void setupMocks() {
        queryLookupDao = (QueryLookupDao)super.applicationContext.getBean("queryLookupDAO");
    }

    @Test(groups = { "testReadQueryLookup" })
    public void testReadQueryLookupInvalid() throws Exception {
        QueryLookup lookup = queryLookupDao.readQueryLookup("constituent.customFieldMap[headofhousehold.householdMembers]");
        assert lookup == null;
    }
    
    @Test(groups = { "testReadQueryLookup" })
    public void testReadQueryLookupValid() throws Exception {
        QueryLookup lookup = queryLookupDao.readQueryLookup("constituent.customFieldMap[organization.employees]");
        assert lookup != null;
        assert lookup.getSite() != null && "company1".equals(lookup.getSite().getName());
        assert "constituent.contactInfo".equals(lookup.getSectionName());
        // TODO test entity type and sqlWhere fields
        assert lookup.getFieldDefinition() != null;
        assert "constituent.customFieldMap[organization.employees]".equals(lookup.getFieldDefinition().getId());
        assert EntityType.constituent.equals(lookup.getFieldDefinition().getEntityType());
        assert ReferenceType.constituent.equals(lookup.getFieldDefinition().getReferenceType());
        assert "customFieldMap[organization.employees]".equals(lookup.getFieldDefinition().getFieldName());
        assert "Employee List".equals(lookup.getFieldDefinition().getDefaultLabel());
        assert FieldType.MULTI_QUERY_LOOKUP.equals(lookup.getFieldDefinition().getFieldType());
        assert "organization".equals(lookup.getFieldDefinition().getEntityAttributes());
        assert lookup.getQueryLookupParams() != null && lookup.getQueryLookupParams().size() == 3;
        for (QueryLookupParam param : lookup.getQueryLookupParams()) {
            assert "lastName".equals(param.getName()) || "firstName".equals(param.getName()) || "middleName".equals(param.getName()); 
        }
    } 
}
