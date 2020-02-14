package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.exception.UpdateException;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import java.util.Collection;

@Stateless
public class UserService {
    @Inject
    UserQuery query;

    public Collection<User> getUsers(){
        return query.getUsers();
    }
    
    public User createUser(User user) throws ConstraintViolationException {
        try {
            if (user.getId() == null) {
                return query.createUser(user);
            }

            return user;
        }catch(ConstraintViolationException e){
            throw e;
        }
    }

	public User findUserById(@NotNull Long id) {
		
		return query.findUserById(id);
	}

	public User updateUser(@NotNull User user) throws NoResultExceptionMapper {
        return query.updateUser(user);
    }
}
