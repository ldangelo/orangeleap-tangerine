package com.mpower.event;


import com.mpower.domain.Gift;

public class PaymentEvent extends GiftEvent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3112706059155412766L;

	public PaymentEvent(Object source,Gift gift) {
		super(source,gift);
	}
}
