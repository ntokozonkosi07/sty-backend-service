package com.railroad.rest.role;

import com.railroad.entity.Role;
import com.railroad.rest.common.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class RoleQuery implements Repository<Role> {

    @Inject
    private EntityManager em;

    @Override
    public Collection<Role> findAll(int maxResults, int firstResults) {
        return em.createNamedQuery(Role.FIND_ALL_ROLES,Role.class)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    @Override
    public Role findById(Long id) {
        return em.createNamedQuery(Role.FIND_ALL_ROLE_BY_ID,Role.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Role save(Role obj) {
        return null;
    }

    @Override
    public Role update(Role obj) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
