package com.railroad.rest.user;

import com.railroad.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class UserService {
    @Inject
    UserQuery query;

    public Collection<User> getUsers(){
        return query.getUsers();
    }
}
