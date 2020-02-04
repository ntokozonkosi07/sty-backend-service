package com.railroad.rest.user;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.railroad.common.annotation.Log;

import java.awt.*;
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
        Collection<?> users = userService.getUsers();
        return Response.ok(users).build();
    }
}
