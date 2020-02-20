package com.railroad.rest.requirement;

import com.railroad.entity.requirement.Requirement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

@Stateless
public class RequirementService {
    @Inject
    RequirementQuery rq;

    public Requirement createRequiremnt(Requirement requirement) throws EntityExistsException {
       return rq.createRequiremnt(requirement);
    }
}
