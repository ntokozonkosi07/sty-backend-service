package com.railroad.rest.measurement;

import com.railroad.common.annotation.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/ref-measurements")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MeasurementRest {

    @Inject
    private MeasurementService mservice;

    @Path("/") @GET
    public Response getMeasurements(
            @QueryParam("maxResults") @DefaultValue("10") int maxResults,
            @QueryParam("maxResults") @DefaultValue("0") int firstResults
    ){
        Collection<?> measurements = mservice.findAll(maxResults, firstResults);

        return Response.ok(measurements).build();
    }
}
