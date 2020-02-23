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

    Requirement createRequirement(Requirement requirement) throws EntityExistsException{
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

    Collection<Requirement> getRequirements(Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return em.createNamedQuery(Requirement.FIND_ALL_REQUIREMENTS, Requirement.class)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    Requirement updateRequirement(Requirement requirement) throws IllegalArgumentException{
        if(requirement.getId() == null)
            throw new IllegalArgumentException("Requirement does not have an id");

        em.merge(requirement);
        return requirement;
    }
}
