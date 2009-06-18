package com.orangeleap.tangerine.service.payments;



import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;


import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.checkservice.Batch;
import com.orangeleap.tangerine.domain.checkservice.Detail;
import com.orangeleap.tangerine.domain.checkservice.PaymentProcessorException;
import com.orangeleap.tangerine.domain.checkservice.Response;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.CheckService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.SiteService;


public class EchexPaymentGateway implements ACHPaymentGateway {
    private CheckService checkService = null;
	private static final Log logger = OLLogger.getLog(EchexPaymentGateway.class);
	private ApplicationContext applicationContext;
	
    public void setCheckService(CheckService s)
    {
    	checkService = s;
    }
    
    public CheckService getCheckService()
    {
    	return checkService;
    }

    // Return a Batch configured for TEST (to be safe)
    private Batch getTestBatch(Site s) {
//        Batch batch = checkService.createBatch(853,11229,5000,"Orange Leap");
        Batch batch = checkService.createBatch(s.getAchSiteNumber(),s.getAchMerchantId(),s.getAchRuleNumber(),s.getAchCompanyName());
        
        if (s.isAchTestMode())
        	batch.isTest(true);
        
        batch.setFileNumber(1);

        return batch;

    }

	@Override
	public void Process(Gift g) {
			if (g.getPaymentType().compareTo("ACH") != 0) return;
			

			//
			// make sure the site information is loaded
			SiteService ss = (SiteService) applicationContext.getBean("siteService");
			g.getConstituent().setSite(ss.readSite(g.getConstituent().getSite().getName()));
			
			Batch batch = getTestBatch(ss.readSite(g.getSite().getName()));
	        Detail detail = new Detail();
	        detail.setDateTime(g.getDonationDate());
	        detail.setAccountNumber(g.getSelectedPaymentSource().getAchRoutingNumber()+g.getSelectedPaymentSource().getAchAccountNumber());
	        detail.setCheckAmount(g.getAmount());
	        detail.setTransactionNumber(g.getId().intValue());
	        batch.setDetail(detail);

	        Response response = null;

	        try {
	            response = checkService.sendBatch(batch);

	            if (response.isAccepted()) {
	            	g.setAuthCode(String.valueOf(response.getTransactionNumber()));
	    			g.setTxRefNum(String.valueOf(response.getTransactionNumber()));
	    			g.setGiftStatus("Paid");
	    			g.setPaymentStatus("Approved");
	    			g.setPaymentMessage(response.getMessage());
//	    			g.setComments(response.getMessage());
	            } else {
	    			g.setGiftStatus("Not Paid");
	    			g.setPaymentStatus("Declined");
	    			g.setPaymentMessage(response.getMessage());
//	    			g.setComments(response.getMessage());	    			
	            }
	            
	        } catch(PaymentProcessorException exception) {
	        	logger.error(exception.getMessage());
	        	g.setPaymentStatus("Error");
	        	g.setPaymentMessage(exception.getMessage());
	        }
	        GiftService gs = (GiftService) applicationContext.getBean("giftService");

	        g.setSuppressValidation(true);
	        try {
	        	gs.maintainGift(g);
	        } catch (BindException e) {
	        	// Should not happen with suppressValidation = true.
	        	logger.error(e);
	        }

	}

	@Override
	public void Refund(Gift g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
