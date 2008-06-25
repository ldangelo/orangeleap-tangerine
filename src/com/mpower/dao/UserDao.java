package com.mpower.dao;

import com.mpower.domain.User;

public interface UserDao {

	public User readUserByUserNameAndPassword(String userName, String password);
	
	public User saveUser(User user);
	
}
