package com.orangeleap.tangerine.test.service.validator;

import com.orangeleap.tangerine.service.validator.DistributionLinesValidator;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.test.BaseTest;
import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DistributionLinesValidatorTest extends BaseTest {

    private DistributionLinesValidator validator;
    private BindException errors;

    @BeforeMethod
    public void setupMocks() {
        validator = new DistributionLinesValidator();
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateGiftDistributionLine() throws Exception {
        Gift gift = new Gift();
        errors = new BindException(gift, "gift");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setProjectCode("foo");
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        gift.setDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        
        validator.validate(gift, errors);
        assert errors.hasErrors();
        
        errors = new BindException(gift, "gift");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        gift = new Gift();
        gift.setDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        validator.validate(gift, errors);
        assert !errors.hasErrors();
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateRecurringGiftDistributionLine() throws Exception {
        RecurringGift recurringGift = new RecurringGift();
        errors = new BindException(recurringGift, "recurringGift");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        recurringGift.setDistributionLines(lines);
        recurringGift.setAmountPerGift(new BigDecimal(16));
        
        validator.validate(recurringGift, errors);
        assert errors.hasErrors();
        
        errors = new BindException(recurringGift, "recurringGift");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        recurringGift = new RecurringGift();
        recurringGift.setDistributionLines(lines);
        recurringGift.setAmountPerGift(new BigDecimal(16));
        validator.validate(recurringGift, errors);
        assert !errors.hasErrors();
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateRecurringPledgeDistributionLine() throws Exception {
        Pledge pledge = new Pledge();
        errors = new BindException(pledge, "pledge");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        pledge.setDistributionLines(lines);
        pledge.setRecurring(true);
        pledge.setAmountPerGift(new BigDecimal(16));
        
        validator.validate(pledge, errors);
        assert errors.hasErrors();
        
        errors = new BindException(pledge, "pledge");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        pledge = new Pledge();
        pledge.setDistributionLines(lines);
        pledge.setRecurring(true);
        pledge.setAmountPerGift(new BigDecimal(16));
        validator.validate(pledge, errors);
        assert !errors.hasErrors();
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateNotRecurringPledgeDistributionLine() throws Exception {
        Pledge pledge = new Pledge();
        errors = new BindException(pledge, "commitment");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        pledge.setDistributionLines(lines);
        pledge.setRecurring(false);
        pledge.setAmountTotal(new BigDecimal(13.5));
        
        validator.validate(pledge, errors);
        assert !errors.hasErrors();
        
        errors = new BindException(pledge, "commitment");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        aLine.setProjectCode("foo");
        lines.add(aLine);
        pledge = new Pledge();
        pledge.setDistributionLines(lines);
        pledge.setDistributionLines(lines);
        pledge.setRecurring(false);
        pledge.setAmountTotal(new BigDecimal(13.5));
        validator.validate(pledge, errors);
        assert errors.hasErrors();
    }
}
