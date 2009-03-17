package com.orangeleap.tangerine.test.controller.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.controller.validator.CodeValidator;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class CodeValidatorTest extends BaseTest {

    private CodeValidator validator;
    private DistributionLine distributionLine;
    private Gift gift;
    private BindException errors;
    private Mockery mockery;
    private PicklistItem projCode;
    private PicklistItem motivationCode;
    private PicklistItem currencyCode;
    private Site site;
    private Person person;

    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setupMocks() {
        projCode = new PicklistItem();
        projCode.setItemName("001000");
        motivationCode = new PicklistItem();
        motivationCode.setItemName("0201");
        currencyCode = new PicklistItem();
        currencyCode.setItemName("USD");
        validator = new CodeValidator();
        mockery = new Mockery();
        final PicklistItemService picklistItemService = mockery.mock(PicklistItemService.class);
        final TangerineUserHelper tangerineUserHelper = mockery.mock(TangerineUserHelper.class);
        validator.setPicklistItemService(picklistItemService);
        validator.setTangerineUserHelper(tangerineUserHelper);

        mockery.checking(new Expectations() {{
            allowing (picklistItemService).getPicklistItem("currencyCode", "USD"); will(returnValue(currencyCode));
            allowing (picklistItemService).getPicklistItem("currencyCode", "foo"); will(returnValue(null));
            allowing (picklistItemService).getPicklistItem("currencyCode", " "); will(returnValue(null));
            allowing (picklistItemService).getPicklistItem("projectCode", "001000"); will(returnValue(projCode));
            allowing (picklistItemService).getPicklistItem("projectCode", "foo"); will(returnValue(null));
            allowing (picklistItemService).getPicklistItem("projectCode", " "); will(returnValue(null));
            allowing (picklistItemService).getPicklistItem("motivationCode", "0201"); will(returnValue(motivationCode));
            allowing (picklistItemService).getPicklistItem("motivationCode", "foo"); will(returnValue(null));
            allowing (picklistItemService).getPicklistItem("motivationCode", " "); will(returnValue(null));
            allowing (tangerineUserHelper).lookupUserSiteName(); will(returnValue("company1"));
        }});

        gift = new Gift();
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

        fieldDef = new FieldDefinition();
        fieldDef.setFieldName("currencyCode");
        fieldDef.setFieldType(FieldType.CODE);
        fieldDef.setDefaultLabel("Currency Code");
        map.put("currencyCode", fieldDef);
        gift.setFieldTypeMap(map);
        
        site = new Site();
        site.setName("company1");
        person = new Person();
        person.setSite(site);
        
        gift.setPerson(person);
        distributionLine.setGiftId(gift.getId());
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        lines.add(distributionLine);
        gift.setDistributionLines(lines);
    }

    @Test(groups = { "validateCode" })
    public void testValidCode() throws Exception {
        assert validator.supports(Gift.class);
        errors = new BindException(gift, "gift");       
        gift.setCurrencyCode("USD");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        assert validator.supports(Gift.class);
        errors = new BindException(gift, "gift");       
        gift.setCurrencyCode(null);
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        assert validator.supports(Gift.class);
        errors = new BindException(gift, "gift");       
        gift.getDistributionLines().get(0).setProjectCode("001000");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(gift, "gift");       
        gift.getDistributionLines().get(0).setProjectCode(null);
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateCode" })
    public void testInvalidCode() throws Exception {
        assert validator.supports(Gift.class);
        errors = new BindException(gift, "gift");       
        gift.setCurrencyCode("foo");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();

        assert validator.supports(Gift.class);
        errors = new BindException(gift, "gift");       
        gift.setCurrencyCode(" ");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();

        errors = new BindException(gift, "gift");        
        gift.getDistributionLines().get(0).setProjectCode("foo");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();

        errors = new BindException(gift, "gift"); 
        gift.getDistributionLines().get(0).setProjectCode(" ");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
    }

    @Test(groups = { "validateCodeOther" })
    public void testValidCodeOther() throws Exception {
        errors = new BindException(gift, "gift");
        gift.getDistributionLines().get(0).setMotivationCode("0201");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(gift, "gift");
        gift.getDistributionLines().get(0).setMotivationCode(null);
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(gift, "gift");
        gift.getDistributionLines().get(0).setMotivationCode("foo");
        gift.getDistributionLines().get(0).setOther_motivationCode("blarg");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        errors = new BindException(gift, "gift");
        gift.getDistributionLines().get(0).setMotivationCode("foo");
        gift.getDistributionLines().get(0).setOther_motivationCode(" ");
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateCodeOther" })
    public void testInvalidCodeOther() throws Exception {
        errors = new BindException(gift, "gift");        
        gift.getDistributionLines().get(0).setMotivationCode("foo");
        gift.getDistributionLines().get(0).setOther_motivationCode(null);
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();

        errors = new BindException(gift, "gift");        
        gift.getDistributionLines().get(0).setMotivationCode(" ");
        gift.getDistributionLines().get(0).setOther_motivationCode(null);
        validator.validate(gift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
   }

}
