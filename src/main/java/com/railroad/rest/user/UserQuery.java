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
class UserQuery {
    @Inject
    EntityManager entityManager;

    Collection<User> getUsers(Integer maxResults, Integer firstResults){
         return entityManager.createNamedQuery(User.FIND_ALL_USERS, User.class)
                 .setMaxResults(maxResults)
                 .setFirstResult(firstResults)
                 .getResultList();
    }

    User createUser(User user) throws ConstraintViolationException {
        if(user.getId() == null){
            entityManager.persist(user);
            return user;
        }
        
        return user;
    }
    
    User updateUser(User user) throws NoResultException {
        try {
            // if finduserById throws a NoResultException means the user is not found
//            User user = this.findUserById(user.getId());

            User _user = entityManager.merge(user);
            return _user;
        } catch(NoResultException e){
            throw new NoResultException();
        }
    }
    
    User findUserById(Long id){
        return entityManager.createNamedQuery(User.FIND_USER_BY_ID, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
