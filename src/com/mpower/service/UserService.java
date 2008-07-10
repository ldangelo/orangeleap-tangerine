package com.mpower.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.mpower.domain.User;

public interface UserService {

	public User authenticateUser(String userName, String password);

	public User maintainUser(User user);

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException;
}
