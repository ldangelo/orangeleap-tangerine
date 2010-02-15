/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.payments;



import org.apache.commons.logging.Log;
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
import com.orangeleap.tangerine.service.OrangeleapJmxNotificationBean;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.OLLogger;



public class EchexPaymentGateway implements ACHPaymentGateway {
    private CheckService checkService = null;
	private static final Log logger = OLLogger.getLog(EchexPaymentGateway.class);
	private ApplicationContext applicationContext;
	private OrangeleapJmxNotificationBean orangeleapJmxNotificationBean;


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
			Batch batch = null;

			//
			// make sure the site information is loaded
			SiteService ss = (SiteService) applicationContext.getBean("siteService");
			Site site = ss.readSite(g.getConstituent().getSite().getName());
			if (site.getAchMerchantId() == null || site.getAchCompanyName() == null || site.getAchRuleNumber() == null
					|| site.getAchSiteNumber() == null || site.getAchTestMode() == null){
	            if (logger.isErrorEnabled()) {
	                logger.error("General: " + "Some or all of the ACH Site Settings are null.");
	            }
	        	g.setPaymentStatus(Gift.PAY_STATUS_ERROR);
	            g.setPaymentMessage("Payment not processed: ACH Site Settings are null. ");
	            GiftService gs = (GiftService) applicationContext.getBean("giftService");

	            g.setSuppressValidation(true);
	            try {
	                gs.maintainGift(g);
	            } catch (BindException be) {
	                // Should not happen with suppressValidation = true.
	                logger.error(be);
	            }

	            return;
			}
			g.getConstituent().setSite(site);


			try{
				batch = getTestBatch(ss.readSite(g.getSite().getName()));
		        Detail detail = new Detail();
		        detail.setDateTime(g.getDonationDate());
	            if (g.getPaymentSource() != null) {
		            detail.setAccountNumber(g.getPaymentSource().getAchRoutingNumber()+g.getPaymentSource().getAchAccountNumber());
	            }
		        detail.setCheckAmount(g.getAmount());
		        detail.setTransactionNumber(g.getId().intValue());
		        batch.setDetail(detail);

	        } catch (Exception e) {
	            if (logger.isErrorEnabled()) {
	                logger.error("General: " + e.getMessage());
	            }
	        	g.setPaymentStatus(Gift.PAY_STATUS_ERROR);
	            g.setPaymentMessage(e.getMessage());
	            GiftService gs = (GiftService) applicationContext.getBean("giftService");

	            g.setSuppressValidation(true);
	            try {
	                gs.maintainGift(g);
	            } catch (BindException be) {
	                // Should not happen with suppressValidation = true.
	                logger.error(e);
	            }

	            return;
	        }


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

	            orangeleapJmxNotificationBean.incrementStatCount(g.getSite().getName(), OrangeleapJmxNotificationBean.ACH);
	        	orangeleapJmxNotificationBean.setStat(g.getSite().getName(), OrangeleapJmxNotificationBean.ECHEX_PAYMENT_STATUS, OrangeleapJmxNotificationBean.OK);

	        } catch(PaymentProcessorException exception) {
	        	logger.error(exception.getMessage());
	        	g.setPaymentStatus("Error");
	        	g.setPaymentMessage(exception.getMessage());

            	orangeleapJmxNotificationBean.publishNotification(OrangeleapJmxNotificationBean.ECHEX_PAYMENT_ERROR, ""+exception.getMessage());
            	orangeleapJmxNotificationBean.setStat(g.getSite().getName(), OrangeleapJmxNotificationBean.ECHEX_PAYMENT_STATUS, OrangeleapJmxNotificationBean.ERROR);
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
        orangeleapJmxNotificationBean = (OrangeleapJmxNotificationBean) applicationContext.getBean("OrangeleapJmxNotificationBean");
	}

}
