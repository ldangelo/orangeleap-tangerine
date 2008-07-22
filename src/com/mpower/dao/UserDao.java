package com.mpower.dao;

import com.mpower.domain.User;

public interface UserDao {

    public User readUserByUserNameAndSite(String userName, String siteName);

    public User saveUser(User user);
}
