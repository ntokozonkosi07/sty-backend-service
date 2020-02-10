package com.railroad.rest.user;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.railroad.common.annotation.Log;
import com.railroad.entity.User;
import com.railroad.entity.adapters.EntityAdapter;

import java.util.Collection;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    UserService userService;

    @Path("/")
    @Log
    @GET
    public Response getUsers(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter() {
            @Override
            public Object adaptToJson(Object obj) throws Exception {
                ((User)obj).setReservation(null);
                ((User)obj).setUserRatings(null);
                return obj;
            }
            @Override
            public Object adaptFromJson(Object obj) throws Exception {
                return null;
            }
        });
        Jsonb jsonb = JsonbBuilder.create(config);

        Collection<?> users = userService.getUsers();

        return Response.ok(jsonb.toJson(users)).build();
    }
    
    @Path("/")
    @Log
    @POST
    public Response saveUser(@NotNull User user) throws ConstraintViolationException {
        try {
            User newUser = userService.createUser(user);
            return Response.ok(newUser).build();
        } catch (Exception e){
            throw e;
        }
    }
    
    @Path("/{query}")
    @POST
    @Log
    public Response searchUser(@NotNull @PathParam("query") String query) {
//    	hook up elasticsearch here
    	return Response.ok().build();
    }
    
    @Path("/{id}")
    public Response findUserById(@NotNull @PathParam("id") Long id) {
    	User user = userService.findUserById(id);
    	return Response.ok(user).build();
    }
}
