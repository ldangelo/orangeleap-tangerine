package com.mpower.service.payments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.domain.Gift;

import com.paymentech.orbital.sdk.configurator.Configurator;
import com.paymentech.orbital.sdk.configurator.ConfiguratorIF;
import com.paymentech.orbital.sdk.interfaces.RequestIF;
import com.paymentech.orbital.sdk.interfaces.ResponseIF;
import com.paymentech.orbital.sdk.interfaces.TransactionProcessorIF;
import com.paymentech.orbital.sdk.request.FieldNotFoundException;
import com.paymentech.orbital.sdk.request.Request;
import com.paymentech.orbital.sdk.transactionProcessor.TransactionException;
import com.paymentech.orbital.sdk.transactionProcessor.TransactionProcessor;
import com.paymentech.orbital.sdk.util.exceptions.InitializationException;

//@Service("paymentGateway")
public class OrbitalPaymentGateway implements PaymentGateway {
	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private String configFile;
	protected static ConfiguratorIF configurator = null;
	private TransactionProcessorIF tp = null;

	public OrbitalPaymentGateway() {
	}

	void Initialize() {
		try {
			configurator = Configurator.getInstance(configFile);
		} catch (InitializationException ie) {
			if (logger.isErrorEnabled()) {
				logger.error("Configurator initialization failed.");
			}
		}

		try {
			tp = new TransactionProcessor();
		} catch (InitializationException iex) {
			if (logger.isErrorEnabled()) {
				logger.error(iex.getMessage());
			}
		}
	}

	@Override
	public void AuthorizeAndCapture(Gift gift) {
		RequestIF request = null;

		if (configurator == null)
			Initialize();

		try {
			//
			// Create the request
			// Tell the request object which template to use (see
			// RequestIF.java)
			request = new Request(RequestIF.NEW_ORDER_TRANSACTION);

			// Basic Information
			request.setFieldValue("IndustryType", "EC");
			request.setFieldValue("MessageType", "AC");
			request.setFieldValue("MerchantID", gift.getSite()
					.getMerchantNumber());
			request.setFieldValue("BIN", "000002");
			request.setFieldValue("OrderID", gift.getId().toString());
			request.setFieldValue("AccountNum", gift.getPaymentSource()
					.getCreditCardNumber());
			request.setFieldValue("Amount", gift.getAmount().toString());
			request.setFieldValue("Exp", gift.getPaymentSource()
					.getCreditCardExpirationMonth().toString()
					+ gift.getPaymentSource().getCreditCardExpirationYear()
							.toString());
			// AVS Information
			if (gift.getAddress().isValid()) {
				request.setFieldValue("AVSname", gift.getPaymentSource()
						.getCreditCardHolderName());
				request.setFieldValue("AVSaddress1", gift.getAddress()
						.getAddressLine1());
				request.setFieldValue("AVScity", gift.getAddress().getCity());
				request.setFieldValue("AVSstate", gift.getAddress()
						.getStateProvince());
				request.setFieldValue("AVSzip", gift.getAddress()
						.getPostalCode());
			}

			if (logger.isInfoEnabled()) {
				logger.info(request.getXML());
			}
		} catch (InitializationException ie) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to initialize request object.");
			}
			return;
		} catch (FieldNotFoundException fnfe) {
			if (logger.isErrorEnabled()) {
				logger.error("Unable to find XML field in template.");
				logger.error(fnfe.getMessage());
			}
			return;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			return;
		}

		// Process the transaction
		ResponseIF response = null;
		try {
			response = tp.process(request);
		} catch (Exception tex) {
			if (logger.isErrorEnabled()) {
				logger.error(tex.getMessage());
			}
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info(response.toXmlString());
		}

		// if you get here the card has been charged and everything is groovy
		// set the auth code on the gift and return
		if (response.isApproved()) {
			gift.setAuthCode(response.getAuthCode());
			gift.setTxRefNum(response.getTxRefNum());
		} else {
			gift.setPaymentStatus(response.getStatus());
			gift.setPaymentMessage(response.getMessage());
		}

	}

	public void setConfigFile(String f) {
		configFile = f;
	}

	public String getConfigFile() {
		return configFile;
	}

	@Override
	public void Authorize(Gift gift) {
		int i = 0; i++;
	}

	@Override
	public void Capture(Gift gift) {

	}

	@Override
	public void Refund(Gift gift) {

	}

	private ResponseIF process(RequestIF request) {
		return null;
	}
}
