package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	public PaginatedResult readPaymentHistory(Long constituentId, SortInfo sortinfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistory: constituentId = " + constituentId);
        }
		return paymentHistoryDao.readPaymentHistoryByConstituentId(constituentId, sortinfo);
	}
	
	@Override
	public PaginatedResult readPaymentHistoryBySite(SortInfo sortinfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentHistoryBySite:");
        }
		return paymentHistoryDao.readPaymentHistoryBySite(sortinfo);
	}
}
