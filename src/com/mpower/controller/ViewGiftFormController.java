package com.mpower.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Gift;
import com.mpower.service.GiftService;

public class ViewGiftFormController extends SimpleFormController {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private GiftService giftService;

	public void setGiftService(GiftService giftService) {
		this.giftService = giftService;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		String giftId = request.getParameter("giftId");
		Gift gift = giftService.readGiftById(new Long(giftId));
		return gift;
	}
}
