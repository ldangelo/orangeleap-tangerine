package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.PaymentHistoryDao;
import com.orangeleap.tangerine.domain.PaymentHistory;
import com.orangeleap.tangerine.service.PaymentHistoryService;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Service("paymentHistoryService")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentHistoryServiceImpl extends AbstractTangerineService implements PaymentHistoryService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "paymentHistoryDAO")
    private PaymentHistoryDao paymentHistoryDao;

	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
        if (logger.isTraceEnabled()) {
            logger.trace("addPaymentHistory: paymentHistory = " + paymentHistory);
        }
		if (paymentHistory.getConstituent() == null) {
            return null;
        }
		return paymentHistoryDao.addPaymentHistory(paymentHistory);
	}

	@Override
	public PaginatedResult readPaymentHistory(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentHistory: constituentId = " + constituentId);
        }
		return paymentHistoryDao.readPaymentHistoryByConstituentId(constituentId, sortinfo);
	}
	
	@Override
	public PaginatedResult readPaymentHistoryBySite(SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentHistoryBySite:");
        }
		return paymentHistoryDao.readPaymentHistoryBySite(sortinfo);
	}
}
