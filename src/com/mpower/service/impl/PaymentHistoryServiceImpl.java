package com.mpower.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.PaymentHistoryDao;
import com.mpower.domain.PaymentHistory;
import com.mpower.service.PaymentHistoryService;

@Service("paymentHistoryService")
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "paymentHistoryDao")
    private PaymentHistoryDao paymentHistoryDao;


	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
		if (paymentHistory.getPerson() == null) return null;
		return paymentHistoryDao.addPaymentHistory(paymentHistory);
	}

	@Override
	public List<PaymentHistory> readPaymentHistory(Long personId) {
		return paymentHistoryDao.readPaymentHistory(personId);
	}
	
	@Override
	public List<PaymentHistory> readPaymentHistoryBySite(String siteId) {
		return paymentHistoryDao.readPaymentHistoryBySite(siteId);
	}
	
}
