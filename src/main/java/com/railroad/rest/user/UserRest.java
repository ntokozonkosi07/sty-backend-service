package com.railroad.rest.user;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.railroad.common.annotation.Log;
import com.railroad.entity.User;
import com.railroad.entity.adapters.EntityAdapter;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

@Log
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    UserService userService;

    @Path("/") @GET
    public Response getUsers(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter() {
            @Override
            public Object adaptToJson(Object obj) throws Exception {
                if( ((User)obj).getReservation() == null)
                    ((User)obj).setReservation(new ArrayList<>());
                if(((User)obj).getUserRatings() == null)
                    ((User)obj).setUserRatings(new ArrayList<>());
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
    
    @Path("/") @POST
    public Response saveUser(@NotNull @Valid User user) {
        User newUser = userService.createUser(user);
        return Response.ok(newUser).build();
    }
    
    @Path("/{query}")  @POST
    public Response searchUser(@NotNull @PathParam("query") String query) throws NotImplementedException {
//    	hook up elasticsearch here
        throw new NotImplementedException();
    }

    @Path("/{id}") @GET
    public Response findUserById(@NotNull @PathParam("id") Long id) {
    	User user = userService.findUserById(id);
    	return Response.ok(user).build();
    }

    @Path("/") @PUT
    public Response updateUser(@NotNull @Valid User user) throws NoResultExceptionMapper {
        User _user = userService.updateUser(user);
        return Response.ok(_user).build();
    }
}
