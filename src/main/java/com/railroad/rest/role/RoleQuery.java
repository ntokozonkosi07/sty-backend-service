package com.railroad.rest.role;

import com.railroad.entity.Role;
import com.railroad.rest.common.Repository;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
public class RoleQuery extends Repository<Role> {

    public RoleQuery() {
        super(Role.class);
    }

    @Override
    public Collection<Role> findAll(int maxResults, int firstResults) throws IllegalArgumentException {
        return em.createNamedQuery(Role.FIND_ALL_ROLES,Role.class)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    @Override
    public Role findById(Long id) throws IllegalArgumentException {
        return em.createNamedQuery(Role.FIND_ROLE_BY_ID,Role.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Collection<Role> findRolesByUserId(Long userId, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Long>(){{ put("id", Long.valueOf(userId)); }}, maxResults, firstResults, Role.FIND_ROLES_BY_USER_ID);
    }
}
