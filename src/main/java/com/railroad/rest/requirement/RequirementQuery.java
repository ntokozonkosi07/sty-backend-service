package com.railroad.rest.requirement;

import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
class RequirementQuery {

    @Inject
    EntityManager em;

    Requirement createRequiremnt(Requirement requirement) throws EntityExistsException{
        if(requirement.getId() != null)
            throw new EntityExistsException();

        em.persist(requirement);
        return requirement;
    }

    Requirement getRequirementById(Long id) throws IllegalArgumentException{
        return em.createNamedQuery(Requirement.FIND_REQUIREMENT_BY_ID, Requirement.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    Collection<Requirement> getRequirements(int maxResults, int firstResults) throws IllegalArgumentException{
        return em.createNamedQuery(Requirement.FIND_REQUIREMENT_BY_ID, Requirement.class)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }
}
