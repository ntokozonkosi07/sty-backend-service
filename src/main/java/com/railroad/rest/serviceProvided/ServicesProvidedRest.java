package com.railroad.rest.serviceProvided;

import com.railroad.common.annotation.Log;
import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Log
@Path("/services-provided")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicesProvidedRest {

    @Inject
    ServiceProvidedService serviceProv;

    @GET @Path("/")
    public Response getSerivceProvided(
            @QueryParam("maxResults") @DefaultValue(value = "10") Integer maxResults,
            @QueryParam("startIndex") @DefaultValue(value = "0") Optional<Integer> firstResults
    ){
        Collection<ServiceProvided> servs = serviceProv.getRequirements(maxResults,firstResults);

        return Response.ok(servs).build();
    }
}
