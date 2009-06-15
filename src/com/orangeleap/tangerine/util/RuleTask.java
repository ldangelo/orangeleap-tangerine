package com.orangeleap.tangerine.util;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public class RuleTask {
	private String agendaGroup;
	private Constituent constituent;
	private Gift   gift;
	private ApplicationContext context;
	
	
	public RuleTask(ApplicationContext c,String string, Constituent constituent2, Gift gift2) {
		setContext(c);
		setAgendaGroup(string);
		setConstituent(constituent2);
		setGift(gift2);
	}
	public String getAgendaGroup() {
		return agendaGroup;
	}
	public void setAgendaGroup(String agendaGroup) {
		this.agendaGroup = agendaGroup;
	}
	public Constituent getConstituent() {
		return constituent;
	}
	public void setConstituent(Constituent constituent) {
		this.constituent = constituent;
	}
	public Gift getGift() {
		return gift;
	}
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	public ApplicationContext getContext() {
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	

}
