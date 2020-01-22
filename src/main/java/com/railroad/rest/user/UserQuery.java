package com.railroad.rest.user;

import com.railroad.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Stateless
public class UserQuery {
    @Inject
    EntityManager entityManager;

    public Collection<User> getUsers(){
         return entityManager.createNamedQuery(User.FIND_ALL_USERS, User.class)
                .getResultList();
    }

    public User createUser(User user){
        
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
    
    public User getUserById(Long id){
        return entityManager.find(User.class, id);
    }

}
