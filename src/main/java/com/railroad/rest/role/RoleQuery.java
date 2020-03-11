package com.railroad.rest.role;

import com.railroad.entity.Role;
import com.railroad.rest.common.Repository;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class RoleQuery extends Repository<Role> {

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
}
