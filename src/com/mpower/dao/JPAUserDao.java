package com.mpower.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.User;

@Repository("userDao")
public class JPAUserDao implements UserDao {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public User readUserByUserNameAndPassword(String userName, String password) {
        Query query = em.createNamedQuery("READ_USER_BY_USERNAME_AND_PASSWORD");
        query.setParameter("userName", userName);
        query.setParameter("password", password);

        List<User> result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        return em.merge(user);
    }

    @Override
    public User getUserByLogin(String login) {
        if (null == login) {
            throw new IllegalArgumentException("Login is mandatory. Null value is forbidden.");
        }
        try {
            Query query = em.createNamedQuery("READ_USER_BY_USERNAME");
            query.setParameter("userName", login);
            logger.info("get User with login: " + login);
            System.out.println("query: "+query.toString());
            User user = (User) query.getSingleResult();
            return user;
        } catch (PersistenceException e) {
            // Critical errors : database unreachable, etc.
            logger.severe("Exception - PersistenceException occurs : " + e.getMessage() + " on complete getUserByLogin().");
            return null;
        }
    }
}
