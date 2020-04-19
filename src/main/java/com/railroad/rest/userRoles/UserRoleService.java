package com.railroad.rest.userRoles;

import com.railroad.entity.UserRole;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class UserRoleService {

    @Inject private UserRoleQuery userRoleQuery;

    public Collection<UserRole> findUserRolesByUserId(Long userId, int maxRestults, int firstResults){
        return userRoleQuery.findUserRolesByUserId(userId,maxRestults, firstResults);
    }
}
