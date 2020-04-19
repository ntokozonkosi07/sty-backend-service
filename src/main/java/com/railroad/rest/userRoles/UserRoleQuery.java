package com.railroad.rest.userRoles;

import com.railroad.entity.UserRole;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
public class UserRoleQuery extends Repository<UserRole> {

    public UserRoleQuery(){
        super(UserRole.class);
    }

    @Override
    public Collection<UserRole> findAll(int maxResults, int firstResults) {
        return null;
    }

    @Override
    public UserRole findById(Long id) {
        return null;
    }

    public Collection<UserRole> findUserRolesByUserId(Long userId, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Long>(){{ put("id", userId); }}, maxResults, firstResults, UserRole.FIND_USER_ROLES_BY_USER_ID);
    }
}
