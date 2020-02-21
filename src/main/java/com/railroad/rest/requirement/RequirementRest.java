package com.railroad.rest.requirement;

import com.railroad.common.annotation.Log;
import com.railroad.entity.requirement.Requirement;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Log
@Path("/requirement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequirementRest {
    @Inject
    RequirementService rs;

    @Path("/") @POST
    public Response createRequirement(Requirement requirement) throws EntityExistsException {
        Requirement req = rs.createRequirement(requirement);
        return Response.ok(req).build();
    }

    public Response getRequirementById(Long id)
            throws NoResultException, IllegalArgumentException{
        return null;
    }

    public Response getRequirements(int maxResults, Optional<Integer> firstResults ) throws IllegalArgumentException {

        return null;
    }

    public Response updateRequirement(Requirement requirement) throws IllegalArgumentException{
        return null;
    }
}
