package com.mpower.test.controller.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.controller.validator.DistributionLinesValidator;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.DistributionLine;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.test.BaseTest;

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
        gift.setDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        
        validator.validate(gift, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(gift, "gift");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        gift = new Gift();
        gift.setDistributionLines(lines);
        gift.setAmount(new BigDecimal(16));
        validator.validate(gift, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateRecurringCommitmentDistributionLine() throws Exception {
        Commitment commitment = new Commitment();
        errors = new BindException(commitment, "commitment");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        commitment.setDistributionLines(lines);
        commitment.setRecurring(true);
        commitment.setAmountPerGift(new BigDecimal(16));
        
        validator.validate(commitment, errors);
        assert errors.hasErrors() == true;
        
        errors = new BindException(commitment, "commitment");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        commitment = new Commitment();
        commitment.setDistributionLines(lines);
        commitment.setRecurring(true);
        commitment.setAmountPerGift(new BigDecimal(16));
        validator.validate(commitment, errors);
        assert errors.hasErrors() == false;
    }

    @Test(groups = { "validateDistributionLine" })
    public void testValidateNotRecurringCommitmentDistributionLine() throws Exception {
        Commitment commitment = new Commitment();
        errors = new BindException(commitment, "commitment");
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(13.5));
        lines.add(aLine);
        commitment.setDistributionLines(lines);
        commitment.setRecurring(false);
        commitment.setAmountTotal(new BigDecimal(13.5));
        
        validator.validate(commitment, errors);
        assert errors.hasErrors() == false;
        
        errors = new BindException(commitment, "commitment");
        aLine = new DistributionLine();
        aLine.setAmount(new BigDecimal(2.5));
        lines.add(aLine);
        commitment = new Commitment();
        commitment.setDistributionLines(lines);
        commitment.setDistributionLines(lines);
        commitment.setRecurring(false);
        commitment.setAmountTotal(new BigDecimal(13.5));
        validator.validate(commitment, errors);
        assert errors.hasErrors() == true;
    }
}
