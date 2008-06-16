package com.mpower.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpower.dao.UserDao;
import com.mpower.domain.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;
		
	@Override
	public User authenticateUser(String userName, String password) {
		return userDao.readUserByUserNameAndPassword(userName, password);
	}
	
	@Override
	public User maintainUser(User user) {
		return userDao.saveUser(user);
	}
}
