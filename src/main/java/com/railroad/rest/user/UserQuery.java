package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.exception.UpdateException;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;

import javax.ejb.Stateless;
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
    
    public User updateUser(User user) throws NoResultExceptionMapper {
        try {
            // if finduserById throws a NoResultException means the user is not found
            this.findUserById(user.getId());

            User _user = entityManager.merge(user);
            return _user;
        } catch(NoResultException e){
            throw new NoResultExceptionMapper();
        }
    }
    
    public User findUserById(Long id){
        return entityManager.createNamedQuery(User.FIND_USER_BY_ID, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
