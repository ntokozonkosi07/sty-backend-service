package com.railroad.rest.requirement;

import com.railroad.common.annotation.Log;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.entity.Requirement;
import com.railroad.entity.ServiceProvided;
import com.railroad.rest.serviceProvided.ServiceProvidedService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/requirement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequirementRest {
    @Inject private RequirementService rs;
    @Inject private ServiceProvidedService serviceProvidedServ;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<Requirement>(){

            @Override
            public JsonObject adaptToJson(Requirement obj) throws Exception {

                JsonObjectBuilder requirementJsonObj = Json.createObjectBuilder();

                JsonArrayBuilder servicesProvidedBuilder = Json.createArrayBuilder();
                Collection<ServiceProvided> servicesProvided = serviceProvidedServ.findServicesProvidedByRequirementId(obj.getId(), 10, 0);
                servicesProvided.forEach(s -> {
                    servicesProvidedBuilder.add(Json.createObjectBuilder()
                    .add("id", s.getId())
                    .add("name", s.getName())
//                    .add("price", s.getPrice())
                    .build());
                });
                JsonArray servicesProvidedArr = servicesProvidedBuilder.build();


                return requirementJsonObj
                        .add("id", obj.getId())
                        .add("name",((Requirement)obj).getName())
                        .add("description",((Requirement)obj).getDescription())
                        .add("price",((Requirement)obj).getPrice())
                        .add("servicesProvided",servicesProvidedArr)
                        .build();
            }

            @Override
            public Requirement adaptFromJson(JsonObject obj) throws Exception {
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
            @QueryParam("startIndex") @DefaultValue(value = "0") Integer firstResults
    ) throws IllegalArgumentException {
        Collection<Requirement> reqs = rs.getRequirements(maxResults, firstResults);

        return Response.ok(jsonb.toJson(reqs)).status(Response.Status.OK).build();
    }

    @Path("/") @PUT
    public Response updateRequirement(Requirement requirement) throws IllegalArgumentException{
        Requirement req = rs.updateRequirement(requirement);
        return Response.ok(jsonb.toJson(req)).build();
    }
}
