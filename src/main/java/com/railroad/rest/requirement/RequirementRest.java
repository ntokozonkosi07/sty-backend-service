package com.railroad.rest.requirement;

import com.railroad.common.annotation.Log;
import com.railroad.entity.User;
import com.railroad.entity.adapters.CollectionAdapter;
import com.railroad.entity.adapters.EntityAdapter;
import com.railroad.entity.requirement.Requirement;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Log
@Path("/requirement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequirementRest {
    @Inject
    private RequirementService rs;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter() {
            @Override
            public Object adaptToJson(Object obj) throws Exception {
                if( ((Requirement)obj).getServicesProvided() == null )
                    ((Requirement)obj).setServicesProvided(new ArrayList<>());

                return obj;
            }
            @Override
            public Object adaptFromJson(Object obj) throws Exception {
                return null;
            }
        });

        this.jsonb = JsonbBuilder.create(config);
    }

    @Path("/") @POST
    public Response createRequirement(@Valid Requirement requirement) throws EntityExistsException {

        Requirement req = rs.createRequirement(requirement);
        return Response.ok(jsonb.toJson(req)).build();
    }

    @Path("/{id}") @GET
    public Response getRequirementById(@PathParam("id") Long id)
            throws NoResultException, IllegalArgumentException{

        return Response.ok(jsonb.toJson(rs.getRequirementById(id))).status(Response.Status.OK).build();
    }

    @Path("/") @GET
    public Response getRequirements(
            @QueryParam("maxResults") @DefaultValue(value = "10") Integer maxResults,
            @QueryParam("startIndex") @DefaultValue(value = "0") Optional<Integer> firstResults
    ) throws IllegalArgumentException {

        Collection<Requirement> reqs = rs.getRequirements(maxResults, Optional.of(firstResults.get()));
        return Response.ok(jsonb.toJson(reqs)).status(Response.Status.OK).build();
    }

    @Path("/") @PUT
    public Response updateRequirement(Requirement requirement) throws IllegalArgumentException{
        Requirement req = rs.updateRequirement(requirement);
        return Response.ok(req).build();
    }


}
