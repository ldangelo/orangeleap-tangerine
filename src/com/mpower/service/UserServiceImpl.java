package com.mpower.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.UserDao;
import com.mpower.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User authenticateUser(String userName, String siteName) {
        return userDao.readUserByUserNameAndSite(userName, siteName);
    }

    public User getUser(Long userId) {
        return userDao.readUserById(userId);
    }

    @Override
    public User maintainUser(User user) {
        return userDao.saveUser(user);
    }
}
