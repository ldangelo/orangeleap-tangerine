package com.mpower.service;

import com.mpower.domain.User;

public interface UserService {

	public User authenticateUser(String userName, String password);
	
	public User maintainUser(User user);
	
}
