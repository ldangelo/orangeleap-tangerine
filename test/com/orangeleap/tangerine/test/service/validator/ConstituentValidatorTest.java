package com.orangeleap.tangerine.test.service.validator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.service.validator.ConstituentValidator;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.util.StringConstants;

public class ConstituentValidatorTest extends BaseTest {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private ConstituentValidator validator = new ConstituentValidator();
    private BindException errors;

    @Test(groups = { "validateConstituent" })
    public void testValidIndividual() throws Exception {
        Constituent p = new Constituent();
        p.setConstituentType(Constituent.INDIVIDUAL);
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateConstituent" })
    public void testValidOrganization() throws Exception {
        Constituent p = new Constituent();
        p.setConstituentType(Constituent.ORGANIZATION);
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, null);
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, null);
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, "");
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, null);
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END) == false;

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, " foo");
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, "7.x56");
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, "3");
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasFieldErrors(StringConstants.CUSTOM_FIELD_MAP_START + Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH + StringConstants.CUSTOM_FIELD_MAP_END);

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, "5");
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;

        p.setCustomFieldValue(Constituent.ORGANIZATION_MINIMUM_GIFT_MATCH, "5");
        p.setCustomFieldValue(Constituent.ORGANIZATION_MAXIMUM_GIFT_MATCH, "8");
        errors = new BindException(p, "constituent");       
        validator.validate(p, errors);
        assert errors.hasErrors() == false;
    }
}
