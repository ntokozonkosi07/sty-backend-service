package com.railroad.rest.requirement;

import com.railroad.entity.requirement.Requirement;

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
       return rq.createRequirement(requirement);
    }

    public Requirement getRequirementById(Long id)
            throws NoResultException, IllegalArgumentException{
        return rq.getRequirementById(id);
    }

    public Collection<Requirement> getRequirements(Integer maxResults, Optional<Integer> firstResults ) throws IllegalArgumentException {
        Integer firstRes = firstResults.isPresent() ? firstResults.get() : 0;

        if(firstRes > maxResults)
            throw new IllegalArgumentException("First results cannot be greater to max results");

        return rq.getRequirements(maxResults, firstRes);
    }

    public Requirement updateRequirement(Requirement requirement) throws IllegalArgumentException{
       return rq.updateRequirement(requirement);
    }
}
