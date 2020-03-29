package com.railroad.rest.role;

import com.railroad.entity.Role;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class RoleService extends AbstractService {
    @Inject RoleQuery rq;

    public Collection<Role> findAll(int maxResults, int firstResults){
        int firsRes = parameterValidation(maxResults, java.util.Optional.of(firstResults));
        return rq.findAll(maxResults,firsRes);
    }

    public Role findById(Long id){
        return rq.findById(id);
    }

    public Role save(Role role){
        return rq.save(role);
    }

    public Role update(Role role){
        return rq.update(role);
    }

    public void delete(Long id){
        rq.delete(id);
    }

    public Collection<Role> findRolesByUserId(Long userId, int maxResults, int firstResults){
        return rq.findRolesByUserId(userId, maxResults, firstResults);
    }
}
