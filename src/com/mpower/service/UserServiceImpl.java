package com.mpower.service;

import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.UserDao;
import com.mpower.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override
    public User authenticateUser(String userName, String password) {
        return userDao.readUserByUserNameAndPassword(userName, password);
    }

    @Override
    public User maintainUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        User user = userDao.getUserByLogin(login);

        if (null == user) {
            logger.severe("User with login: " + login + " not found in database. Authentication failed for user " + login);
            throw new UsernameNotFoundException("user not found in database");
        }

        GrantedAuthority[] authorities = new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_SUPERVISOR"), new GrantedAuthorityImpl("ROLE_USER"), new GrantedAuthorityImpl("ROLE_TELLER") };
        return new org.springframework.security.userdetails.User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
    }
}
