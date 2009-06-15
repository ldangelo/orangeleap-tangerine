package com.orangeleap.tangerine.test.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.impl.AbstractCommitmentService;
import com.orangeleap.tangerine.service.impl.RecurringGiftServiceImpl;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.test.BaseTest;

public class AbstractCommitmentServiceTest extends BaseTest {
    MockCommitmentServiceImpl service = new MockCommitmentServiceImpl();

    @Test
    public void testSetCommitmentStatus() throws Exception {
    //	MockCommitmentServiceImpl service = new MockCommitmentServiceImpl();
    	
    	// Null amountTotal
    	Pledge pledge = new Pledge(null, null, Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_PENDING.equals(pledge.getPledgeStatus());
    	
    	pledge = new Pledge(null, BigDecimal.ZERO, Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_PENDING.equals(pledge.getPledgeStatus());
    	
    	pledge = new Pledge(null, BigDecimal.ZERO, Commitment.STATUS_IN_PROGRESS);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());
    	
    	pledge = new Pledge(null, new BigDecimal("2"), Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(null, new BigDecimal("2"), Commitment.STATUS_FULFILLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(null, new BigDecimal("2"), Commitment.STATUS_CANCELLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus());
    	
    	
    	// Populated amountTotal
    	pledge = new Pledge(new BigDecimal("100"), BigDecimal.ZERO, Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_PENDING.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), BigDecimal.ZERO, Commitment.STATUS_IN_PROGRESS);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), BigDecimal.ZERO, Commitment.STATUS_EXPIRED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_EXPIRED.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), BigDecimal.ZERO, Commitment.STATUS_FULFILLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), new BigDecimal("2"), Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), new BigDecimal("100"), Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), new BigDecimal("102"), Commitment.STATUS_PENDING);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_FULFILLED.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), new BigDecimal("98"), Commitment.STATUS_FULFILLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_IN_PROGRESS.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), new BigDecimal("98"), Commitment.STATUS_CANCELLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus());

    	pledge = new Pledge(new BigDecimal("100"), null, Commitment.STATUS_CANCELLED);
    	service.setCommitmentStatus(pledge);
    	assert Commitment.STATUS_CANCELLED.equals(pledge.getPledgeStatus());
    }

 
    
    class MockCommitmentServiceImpl extends AbstractCommitmentService<Commitment> {

		public void setCommitmentStatus(Commitment commitment) {
			super.setCommitmentStatus(commitment, "pledgeStatus");
		}
    }
}
