package com.railroad.rest.user;

import com.railroad.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.Collection;

@Transactional
public class UserQuery {
    @Inject
    EntityManager entityManager;

    public Collection<User> getUsers(){
         return entityManager.createNamedQuery(User.FIND_ALL_USERS, User.class)
                .getResultList();
    }

    public User createUser(User user) throws ConstraintViolationException {
        if(user.getId() == null){
            entityManager.persist(user);
            return user;
        }
        
        return user;
    }
    
    public User updateUser(User user){
        entityManager.merge(user);
        return user;
    }
    
    public User findUserById(Long id){
        return entityManager.createNamedQuery(User.FIND_USER_BY_ID, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
