package com.railroad.rest.role;

import com.railroad.common.annotation.Log;
import com.railroad.entity.Role;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleRest {

    @Inject
    private RoleService rs;

    @GET @Path("/")
    public Response findAll(
            @QueryParam("maxResults") @DefaultValue(value = "10") int maxResults,
            @QueryParam("firstResults") @DefaultValue(value = "0") int firstResults
    ){
        Collection<Role> roles = rs.findAll(maxResults, firstResults);
        return Response.ok(roles).build();
    }

    @GET @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Role role = rs.findById(id);
        return Response.ok(role).build();
    }

    @POST @Path("/")
    public Response save(Role role){
        return Response.ok(rs.save(role)).build();
    }

    @PUT @Path("/")
    public Response update(Role role){
        return Response.ok(rs.update(role)).build();
    }
}
