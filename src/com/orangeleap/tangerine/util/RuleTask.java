package com.orangeleap.tangerine.util;

import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;

public class RuleTask {
	private String agendaGroup;
	private Person person;
	private Gift   gift;
	private ApplicationContext context;
	
	
	public RuleTask(ApplicationContext c,String string, Person person2, Gift gift2) {
		setContext(c);
		setAgendaGroup(string);
		setPerson(person2);
		setGift(gift2);
	}
	public String getAgendaGroup() {
		return agendaGroup;
	}
	public void setAgendaGroup(String agendaGroup) {
		this.agendaGroup = agendaGroup;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
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
