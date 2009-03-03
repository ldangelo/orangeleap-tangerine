package com.mpower.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.PaymentHistoryDao;
import com.mpower.domain.model.PaymentHistory;
import com.mpower.service.PaymentHistoryService;

@Service("paymentHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "paymentHistoryDAO")
    private PaymentHistoryDao paymentHistoryDao;

	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
        if (logger.isDebugEnabled()) {
            logger.debug("addPaymentHistory: paymentHistory = " + paymentHistory);
        }
		if (paymentHistory.getPerson() == null) {
            return null;
        }
		return paymentHistoryDao.addPaymentHistory(paymentHistory);
	}

	@Override
	public List<PaymentHistory> readPaymentHistory(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistory: constituentId = " + constituentId);
        }
		return paymentHistoryDao.readPaymentHistoryByConstituentId(constituentId);
	}
	
	@Override
	public List<PaymentHistory> readPaymentHistoryBySite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistoryBySite:");
        }
		return paymentHistoryDao.readPaymentHistoryBySite();
	}
}
