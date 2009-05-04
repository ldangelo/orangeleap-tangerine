package com.orangeleap.tangerine.test.controller.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.springframework.validation.BindException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.controller.validator.AdjustedDistributionLinesValidator;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.test.BaseTest;

public class AdjustedDistributionLinesValidatorTest extends BaseTest {

    private AdjustedDistributionLinesValidator validator;
    private BindException errors;
    private Mockery mockery;
    private List<DistributionLine> lines;

    @BeforeMethod
    public void setupMocks() {
        validator = new AdjustedDistributionLinesValidator();
        mockery = new Mockery();
        final AdjustedGiftService adjustedGiftService = mockery.mock(AdjustedGiftService.class);
        validator.setAdjustedGiftService(adjustedGiftService);

        mockery.checking(new Expectations() {{
            allowing (adjustedGiftService).findCurrentTotalAdjustedAmount(1L); will(returnValue(new BigDecimal("-20")));
            allowing (adjustedGiftService).findCurrentTotalAdjustedAmount(2L); will(returnValue(BigDecimal.ZERO));
        }});
        
        lines = new ArrayList<DistributionLine>();
        lines.add(new DistributionLine(new BigDecimal("-10")));
        lines.add(new DistributionLine(new BigDecimal("-8.99")));
    }

    @Test(groups = { "validateAdjustedDistributionLine" })
    public void testCheckTotaledDistributionLineAmountMatch() throws Exception {
        
        AdjustedGift adjustedGift = new AdjustedGift(new BigDecimal("-5"), new BigDecimal("25"), 1L, lines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotaledDistributionLineAmountMatch(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorDistributionLineAmounts".equals(errors.getGlobalError().getCode()), "Expected code of 'errorDistributionLineAmounts', not '" + errors.getGlobalError().getCode() + "'");
        
        adjustedGift = new AdjustedGift(new BigDecimal("-18.99"), new BigDecimal("25"), 1L, lines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotaledDistributionLineAmountMatch(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateAdjustedDistributionLine" })
    public void testCheckAmountsNotPositive() throws Exception {
        AdjustedGift adjustedGift = new AdjustedGift(new BigDecimal("-1.99"));
        adjustedGift.setDistributionLines(lines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkAmountsNotPositive(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
        
        List<DistributionLine> newLines = new ArrayList<DistributionLine>();
        newLines.addAll(lines);
        newLines.add(new DistributionLine(new BigDecimal("0")));
        adjustedGift.setDistributionLines(newLines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkAmountsNotPositive(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
        
        newLines.add(new DistributionLine(new BigDecimal("1")));
        adjustedGift.setDistributionLines(newLines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkAmountsNotPositive(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorIndividualAdjustedDistributionLineAmountPositive".equals(errors.getGlobalError().getCode()), "Expected code of 'errorIndividualAdjustedDistributionLineAmountPositive', not '" + errors.getGlobalError().getCode() + "'");

        adjustedGift = new AdjustedGift(new BigDecimal("1.99"));
        adjustedGift.setDistributionLines(lines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkAmountsNotPositive(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorAdjustedAmountPositive".equals(errors.getFieldError("adjustedAmount").getCode()), "Expected code of 'errorAdjustedAmountPositive', not '" + errors.getFieldError("adjustedAmount").getCode() + "'");

        adjustedGift = new AdjustedGift(new BigDecimal("0"));
        adjustedGift.setDistributionLines(lines);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkAmountsNotPositive(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorAdjustedAmountPositive".equals(errors.getFieldError("adjustedAmount").getCode()), "Expected code of 'errorAdjustedAmountPositive', not '" + errors.getFieldError("adjustedAmount").getCode() + "'");
    }
    
    @Test(groups = { "validateAdjustedDistributionLine" })
    public void testCheckTotalAdjustedAmount() throws Exception {
        AdjustedGift adjustedGift = new AdjustedGift(new BigDecimal("-5"), new BigDecimal("25"), 1L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;
        
        adjustedGift = new AdjustedGift(new BigDecimal("-4"), new BigDecimal("25"), 1L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        adjustedGift = new AdjustedGift(new BigDecimal("-6"), new BigDecimal("25"), 1L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorTotalAdjustedAmount".equals(errors.getFieldError("adjustedAmount").getCode()), "Expected code of 'errorTotalAdjustedAmount', not '" + errors.getFieldError("adjustedAmount").getCode() + "'");

        /* Original Gift ID of 2 */
        adjustedGift = new AdjustedGift(new BigDecimal("-5"), new BigDecimal("25"), 2L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        adjustedGift = new AdjustedGift(new BigDecimal("0"), new BigDecimal("25"), 2L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors() == false;

        adjustedGift = new AdjustedGift(new BigDecimal("-26"), new BigDecimal("25"), 2L, null);
        errors = new BindException(adjustedGift, "adjustedGift");       
        validator.checkTotalAdjustedAmount(adjustedGift, errors);
        mockery.assertIsSatisfied();
        assert errors.hasErrors();
        Assert.assertTrue("errorTotalAdjustedAmount".equals(errors.getFieldError("adjustedAmount").getCode()), "Expected code of 'errorTotalAdjustedAmount', not '" + errors.getFieldError("adjustedAmount").getCode() + "'");
    }
}
