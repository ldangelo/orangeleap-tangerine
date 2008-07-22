package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.User;

@Repository("userDao")
public class JPAUserDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public User readUserByUserNameAndSite(String userName, String siteName) {
        Query query = null;
        if (siteName != null) {
            query = em.createNamedQuery("READ_USER_BY_USERNAME_AND_SITE");
            query.setParameter("userName", userName);
            query.setParameter("siteName", siteName);
        } else {
            query = em.createNamedQuery("READ_USER_BY_USERNAME_AND_NULL_SITE");
            query.setParameter("userName", userName);
        }

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
}
