package com.railroad.rest.role;

import com.railroad.common.annotation.Log;
import com.railroad.entity.Role;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
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
    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new RoleMashaler());
        this.jsonb = JsonbBuilder.create(config);
    }

    @GET
    @Path("/")
    public Response findAll(
            @QueryParam("maxResults") @DefaultValue(value = "10") int maxResults,
            @QueryParam("firstResults") @DefaultValue(value = "0") int firstResults
    ){
        Collection<Role> roles = rs.findAll(maxResults, firstResults);
        return Response.ok(jsonb.toJson(roles)).build();
    }

    @GET @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Role role = rs.findById(id);
        return Response.ok(jsonb.toJson(role)).build();
    }

    @POST @Path("/")
    public Response save(Role role){
        String json = jsonb.toJson(rs.save(role));
        return Response.ok(json).build();
    }

    @PUT @Path("/")
    public Response update(Role role){
        String json = jsonb.toJson(rs.update(role));
        return Response.ok(json).build();
    }
}
