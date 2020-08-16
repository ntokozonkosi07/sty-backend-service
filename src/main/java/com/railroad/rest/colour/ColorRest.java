package com.railroad.rest.colour;

import com.railroad.common.annotation.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/ref-colours")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ColorRest {
    @Inject
    private ColorService cservice;

    @Path("/") @GET
    public Response findAll(
            @QueryParam("maxResults") @DefaultValue("10") int maxResults,
            @QueryParam("maxResults") @DefaultValue("0") int firstResults
    ){
        Collection<?> colours = cservice.findAll(maxResults, firstResults);

        return Response.ok(colours).build();
    }
}
