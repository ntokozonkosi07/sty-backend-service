package com.railroad.rest.user;

import com.railroad.entity.*;
import com.railroad.entity.ServiceProvided;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
class UserQuery extends Repository<User> {

    public UserQuery(){
        super(User.class);
    }

    @Override
    public Collection<User> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults,firstResults,User.FIND_ALL_USERS);
    }

    @Override
    public User findById(Long id) {
        return getSingleResults(new HashMap<String, Object>(){{ put("id", Long.valueOf(id)); }}, User.FIND_USER_BY_ID);
    }

    public Collection<User> findUserRatingsById(Long id,Integer maxResults, Integer firstResults) {
        return getCollectionResults(new HashMap<String, Object>(){{ put("id", Long.valueOf(id)); }}, maxResults, firstResults, User.FIND_USER_RATING_BY_ID);
    }

    public Collection<User> findReservationsById(Long id, int maxResults, Integer firstResults) {
        return getCollectionResults(new HashMap<String, Object>(){{ put("id", Long.valueOf(id)); }}, maxResults, firstResults, User.FIND_USER_RESERVATIONS_BY_ID);
    }

    public Collection<ServiceProvided> findServicesProvidedById(Long id, Integer maxResults, Integer firstResults) {
        return em.createNamedQuery(User.FIND_USER_SERVICES_PROVIDED_BY_ID)
                .setParameter("id", id)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    public Collection<UserRole> finRolesByUserId(Long id, int maxResults, Integer firstResults) {
//        return getCollectionResults(new HashMap<String, Object>(){{ put("id", Long.valueOf(id)); }}, maxResults,firstResults, User.FIND_USER_SERVICES_PROVIDED_BY_ID);
        return em.createNamedQuery(User.FIND_ROLE_BY_USER_ID)
                .setParameter("id", id)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

}
