package com.orangeleap.tangerine.service;


public interface RulesService {
    
	public void executeDailyJobRules();

	public void executeMonthlyJobRules();

	public void executeWeeklyJobRules();
    
}
