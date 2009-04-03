package com.orangeleap.tangerine.test.controller.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.controller.validator.DistributionLinesValidator;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.test.BaseTest;

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
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        gift.setMutableDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        
        validator.validate(gift, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(gift, "gift");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        gift = new Gift();
        gift.setMutableDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        validator.validate(gift, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateRecurringGiftDistributionLine() throws Exception {
        RecurringGift recurringGift = new RecurringGift();
        errors = new BindException(recurringGift, "recurringGift");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        recurringGift.setMutableDistributionLines(lines);
        recurringGift.setAmountPerGift(new BigDecimal(16));
        
        validator.validate(recurringGift, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(recurringGift, "recurringGift");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        recurringGift = new RecurringGift();
        recurringGift.setMutableDistributionLines(lines);
        recurringGift.setAmountPerGift(new BigDecimal(16));
        validator.validate(recurringGift, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateRecurringPledgeDistributionLine() throws Exception {
        Pledge pledge = new Pledge();
        errors = new BindException(pledge, "pledge");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        pledge.setMutableDistributionLines(lines);
        pledge.setRecurring(true);
        pledge.setAmountPerGift(new BigDecimal(16));
        
        validator.validate(pledge, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(pledge, "pledge");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        pledge = new Pledge();
        pledge.setMutableDistributionLines(lines);
        pledge.setRecurring(true);
        pledge.setAmountPerGift(new BigDecimal(16));
        validator.validate(pledge, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateNotRecurringPledgeDistributionLine() throws Exception {
        Pledge pledge = new Pledge();
        errors = new BindException(pledge, "commitment");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        pledge.setMutableDistributionLines(lines);
        pledge.setRecurring(false);
        pledge.setAmountTotal(new BigDecimal(13.5));
        
        validator.validate(pledge, errors);
        assert errors.hasErrors() == false;
        
        errors = new BindException(pledge, "commitment");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        pledge = new Pledge();
        pledge.setMutableDistributionLines(lines);
        pledge.setMutableDistributionLines(lines);
        pledge.setRecurring(false);
        pledge.setAmountTotal(new BigDecimal(13.5));
        validator.validate(pledge, errors);
        assert errors.hasErrors() == true;
    }
}
