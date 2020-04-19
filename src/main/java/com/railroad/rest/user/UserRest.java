package com.railroad.rest.user;

import com.railroad.common.annotation.Log;
import com.railroad.entity.User;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import com.railroad.security.Auth;
import org.apache.shiro.authc.credential.PasswordService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    private UserService userService;

    @Inject
    private PasswordService passwordService;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new UserAdapter());
        this.jsonb = JsonbBuilder.create(config);
    }

    @Auth
    @Path("/") @GET
    public Response getUsers(
            @QueryParam("maxResults") @DefaultValue("10") int maxResults,
            @QueryParam("maxResults") @DefaultValue("0") int firstResults
        ){
        Collection<?> users = userService.getUsers(maxResults, java.util.Optional.of(firstResults));

        return Response.ok(jsonb.toJson(users)).build();
    }

    @Path("/") @POST
    public Response saveUser(@NotNull @Valid User user) {
        user.setPassword(passwordService.encryptPassword(user.getPassword()));
        User newUser = userService.createUser(user);
        return Response.ok(newUser).build();
    }

    @Auth
    @Path("/{query}")  @POST
    public Response searchUser(@NotNull @PathParam("query") String query) {
//    	hook up elasticsearch here
        return null;
    }

    @Auth
    @Path("/{id}") @GET
    public Response findUserById(@NotNull @PathParam("id") Long id) {
    	User user = userService.findUserById(id);
    	return Response.ok(jsonb.toJson(user)).build();
    }

    @Path("/findByEmail") @GET
    public Response findUserByEmail(@NotNull @QueryParam("email") String email) throws NoSuchFieldException {
        User user = userService.findUserByEmail(email);
        return Response.ok(jsonb.toJson(user)).build();
    }

    @Auth
    @Path("/") @PUT
    public Response updateUser(@NotNull @Valid User user) throws NoResultExceptionMapper {
        User _user = userService.updateUser(user);
        return Response.ok(_user).build();
    }
}
