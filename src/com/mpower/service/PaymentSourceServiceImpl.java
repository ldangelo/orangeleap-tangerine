package com.mpower.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PaymentSourceDao;
import com.mpower.domain.PaymentSource;

@Service("paymentSourceService")
public class PaymentSourceServiceImpl implements PaymentSourceService {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "auditService")
	private AuditService auditService;

	@Resource(name = "paymentSourceDao")
	private PaymentSourceDao paymentSourceDao;

	@Transactional(propagation = Propagation.SUPPORTS)
	public PaymentSource savePaymentSource(PaymentSource paymentSource) {
		if (!paymentSource.getType().equals("ACH")) {
			paymentSource.setAchAccountNumber(null);
			paymentSource.setAchRoutingNumber(null);
			paymentSource.setAchType(null);
		} else if (!paymentSource.getType().equals("Credit Card")) {
			paymentSource.setCreditCardExpiration(null);
			paymentSource.setCreditCardNumber(null);
			paymentSource.setCreditCardSecurityCode(null);
			paymentSource.setCreditCardType(null);
		}
		paymentSource.setActive(true);
		auditService.auditObject(paymentSource);
		return paymentSourceDao.maintainPaymentSource(paymentSource);
	}

	public List<PaymentSource> readPaymentSources(Long personId) {
		return paymentSourceDao.readPaymentSources(personId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void deletePaymentSource(PaymentSource paymentSource) {
		auditService.auditObjectDelete(paymentSource);
		paymentSourceDao.deletePaymentSource(paymentSource);
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	@Override
	public List<PaymentSource> readActivePaymentSources(Long personId) {
		return paymentSourceDao.readActivePaymentSources(personId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void inactivatePaymentSource(Long paymentSourceId) {
		paymentSourceDao.inactivatePaymentSource(paymentSourceId);
	}

	@Override
	public void removePaymentSource(Long paymentSourceId) {
		paymentSourceDao.removePaymentSource(paymentSourceId);
	}

	@Override
	public PaymentSource readPaymentSource(Long paymentSourceId) {
		return paymentSourceDao.readPaymentSource(paymentSourceId);
	}

}
