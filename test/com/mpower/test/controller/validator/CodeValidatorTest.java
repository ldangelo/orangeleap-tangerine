package com.mpower.test.controller.validator;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.controller.validator.CodeValidator;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.Site;
import com.mpower.domain.customization.Code;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.CodeService;
import com.mpower.test.BaseTest;
import com.mpower.type.FieldType;

public class CodeValidatorTest extends BaseTest {

    private CodeValidator validator;
    private DistributionLine distributionLine;
    private Gift gift;
    private BindException errors;
    private Mockery mockery;
    private Code projCode;
    private Code motivationCode;
    private Site site;
    private Person person;

    @BeforeMethod
    public void setupMocks() {
        projCode = new Code();
        projCode.setValue("001000");
        motivationCode = new Code();
        motivationCode.setValue("XYZ");
        validator = new CodeValidator();
        mockery = new Mockery();
        final CodeService service = mockery.mock(CodeService.class);
        validator.setCodeService(service);

        mockery.checking(new Expectations() {{
            allowing (service).readCodeBySiteTypeValue("company1", "projectCode", "001000"); will(returnValue(projCode));
            allowing (service).readCodeBySiteTypeValue("company1", "projectCode", "foo"); will(returnValue(null));
            allowing (service).readCodeBySiteTypeValue("company1", "projectCode", " "); will(returnValue(null));
            allowing (service).readCodeBySiteTypeValue("company1", "motivationCode", "XYZ"); will(returnValue(motivationCode));
            allowing (service).readCodeBySiteTypeValue("company1", "motivationCode", "foo"); will(returnValue(null));
            allowing (service).readCodeBySiteTypeValue("company1", "motivationCode", " "); will(returnValue(null));
        }});

        distributionLine = new DistributionLine();
        Map<String, FieldDefinition> map = new HashMap<String, FieldDefinition>();
        FieldDefinition fieldDef = new FieldDefinition();
        fieldDef.setFieldName("projectCode");
        fieldDef.setFieldType(FieldType.CODE);
        fieldDef.setDefaultLabel("Project Code");
        map.put("projectCode", fieldDef);
        
        fieldDef = new FieldDefinition();
        fieldDef.setFieldName("motivationCode");
        fieldDef.setFieldType(FieldType.CODE_OTHER);
        fieldDef.setDefaultLabel("Motivation Code");
        map.put("motivationCode", fieldDef);
        distributionLine.setFieldTypeMap(map);
        
        site = new Site();
        site.setName("company1");
        person = new Person();
        person.setSite(site);
        gift = new Gift();
        
        gift.setPerson(person);
        distributionLine.setGift(gift);
    }

    @Test(groups = { "validateCode" })
    public void testValidCode() throws Exception {
        assert validator.supports(Gift.class);
        errors = new BindException(distributionLine, "distributionLine");       
        distributionLine.setProjectCode("001000");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(distributionLine, "distributionLine");       
        distributionLine.setProjectCode(null);
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
}

    @Test(groups = { "validateCode" })
    public void testInvalidCode() throws Exception {
        errors = new BindException(distributionLine, "distributionLine");        
        distributionLine.setProjectCode("foo");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        assert "invalidCode".equals(errors.getFieldError("projectCode").getCode());
        assert "'foo' is an invalid Project Code".equals(errors.getFieldError("projectCode").getDefaultMessage());

        errors = new BindException(distributionLine, "distributionLine"); 
        distributionLine.setProjectCode(" ");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        assert "invalidCode".equals(errors.getFieldError("projectCode").getCode());
        assert "' ' is an invalid Project Code".equals(errors.getFieldError("projectCode").getDefaultMessage());
    }

    @Test(groups = { "validateCodeOther" })
    public void testValidCodeOther() throws Exception {
        errors = new BindException(distributionLine, "distributionLine");
        distributionLine.setMotivationCode("XYZ");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(distributionLine, "distributionLine");
        distributionLine.setMotivationCode(null);
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(distributionLine, "distributionLine");
        distributionLine.setMotivationCode("foo");
        distributionLine.setOther_motivationCode("blarg");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(distributionLine, "distributionLine");
        distributionLine.setMotivationCode("foo");
        distributionLine.setOther_motivationCode(" ");
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateCodeOther" })
    public void testInvalidCodeOther() throws Exception {
        errors = new BindException(distributionLine, "distributionLine");        
        distributionLine.setMotivationCode("foo");
        distributionLine.setOther_motivationCode(null);
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        assert "invalidCode".equals(errors.getFieldError("motivationCode").getCode());
        assert "'foo' is an invalid Motivation Code".equals(errors.getFieldError("motivationCode").getDefaultMessage());

        errors = new BindException(distributionLine, "distributionLine");        
        distributionLine.setMotivationCode(" ");
        distributionLine.setOther_motivationCode(null);
        validator.validate(distributionLine, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        assert "invalidCode".equals(errors.getFieldError("motivationCode").getCode());
        assert "' ' is an invalid Motivation Code".equals(errors.getFieldError("motivationCode").getDefaultMessage());
   }

}
