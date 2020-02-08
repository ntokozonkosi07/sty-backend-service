package com.railroad.rest.user;

import com.railroad.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import java.util.Collection;

@Stateless
public class UserService {
    @Inject
    UserQuery query;

    public Collection<User> getUsers(){
        return query.getUsers();
    }
    
    public User createUser(User user) {
    	if(user.getId() == null) {
    		return query.createUser(user);
    	}
    	
    	return user;
    }

	public User findUserById(@NotNull Long id) {
		
		return query.findUserById(id);
	}
}
