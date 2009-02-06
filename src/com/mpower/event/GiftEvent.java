package com.mpower.event;

import org.springframework.context.ApplicationEvent;

import com.mpower.domain.Gift;

public class GiftEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -3094525811637468885L;
	private Gift gift;
	
	public GiftEvent(Object source,Gift gift) {
		super(source);
		this.gift = gift;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}
}
