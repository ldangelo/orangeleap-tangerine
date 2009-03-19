package com.orangeleap.tangerine.test.controller.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.controller.validator.ConstituentValidator;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.util.StringConstants;

public class ConstituentValidatorTest extends BaseTest {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private ConstituentValidator validator = new ConstituentValidator();
    private BindException errors;

    @Test(groups = { "validateConstituent" })
    public void testValidIndividual() throws Exception {
        Person p = new Person();
        p.setConstituentType(Person.INDIVIDUAL);
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateConstituent" })
    public void testValidOrganization() throws Exception {
        Person p = new Person();
        p.setConstituentType(Person.ORGANIZATION);
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, null);
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, null);
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, "");
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, null);
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END) == false;

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, " foo");
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, "7.x56");
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, "3");
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Person.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, "5");
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Person.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Person.ORGANIZATION_MAXIMUM_GIFT_MATCH, "8");
        errors = new BindException(p, "person");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;
    }
}
