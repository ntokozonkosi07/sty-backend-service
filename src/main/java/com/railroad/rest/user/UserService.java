package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Optional;

@Stateless
public class UserService {
    @Inject
    UserQuery query;

    public Collection<User> getUsers(Integer maxResults, Optional<Integer> firstResults){
        Integer firstRes = firstResults.isPresent() ? firstResults.get() : 0;

        if(firstRes > maxResults)
            throw new IllegalArgumentException("First results cannot be greater to max results");
        return query.getUsers(maxResults,firstRes);
    }
    
    public User createUser(User user) throws ConstraintViolationException {
        try {
            if (user.getId() == null) {
                return query.createUser(user);
            }

            return user;
        }catch(ConstraintViolationException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

	public User findUserById(@NotNull Long id) {
		
		return query.findUserById(id);
	}

	public User updateUser(@NotNull User user) {
        return query.updateUser(user);
    }
}
