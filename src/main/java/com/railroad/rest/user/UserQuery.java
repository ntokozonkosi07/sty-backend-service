package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.Collection;

@Transactional
public class UserQuery {
    @Inject
    EntityManager entityManager;

    public Collection<User> getUsers(Integer maxResults, Integer firstResults){
         return entityManager.createNamedQuery(User.FIND_ALL_USERS, User.class)
                 .setMaxResults(maxResults)
                 .setFirstResult(firstResults)
                 .getResultList();
    }

    public User createUser(User user) throws ConstraintViolationException {
        if(user.getId() == null){
            entityManager.persist(user);
            return user;
        }
        
        return user;
    }
    
    public User updateUser(User user) throws NoResultException {
        try {
            // if finduserById throws a NoResultException means the user is not found
//            User user = this.findUserById(user.getId());

            User _user = entityManager.merge(user);
            return _user;
        } catch(NoResultException e){
            throw new NoResultException();
        }
    }
    
    public User findUserById(Long id){
        return entityManager.createNamedQuery(User.FIND_USER_BY_ID, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
