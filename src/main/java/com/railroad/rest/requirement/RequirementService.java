package com.railroad.rest.requirement;

import com.railroad.entity.Requirement;
import com.railroad.entity.ServiceProvided;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class RequirementService {
    @Inject
    RequirementQuery rq;

    public Requirement createRequirement(Requirement requirement) throws EntityExistsException {
       return rq.save(requirement);
    }

    public Requirement getRequirementById(Long id)
            throws NoResultException, IllegalArgumentException{
        return rq.findById(id);
    }

    public Requirement updateRequirement(Requirement requirement) throws IllegalArgumentException{
        return rq.update(requirement);
    }

    public Collection<Requirement> getRequirements(Integer maxResults, Integer firstResults ) throws IllegalArgumentException {
        return rq.findAll(maxResults, firstResults);
    }

    public Collection<Requirement> findRequirementsByServiceProvidedId(Long id, Integer maxResults, Integer firstResults ) throws IllegalArgumentException {
        return rq.findRequirementsByServiceProvidedId(id,maxResults, firstResults);
    }
}
