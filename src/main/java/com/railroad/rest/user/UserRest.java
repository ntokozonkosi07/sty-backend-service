package com.railroad.rest.user;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.railroad.common.annotation.Log;
import com.railroad.entity.AbstractEntity;
import com.railroad.entity.User;
import com.railroad.entity.adapters.EntityAdapter;
import com.railroad.entity.ServiceProvided;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;

import java.util.Collection;
import java.util.Optional;

@Log
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    UserService userService;
    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<User>() {
            @Override
            public JsonObject adaptToJson(AbstractEntity obj) throws Exception {

                JsonArrayBuilder userRatingBuilder = Json.createArrayBuilder();
                ((User)obj).setUserRatings(userService.findUserRatingsById(obj.getId(), 10, Optional.of(0)));

                ((User)obj).getUserRatings().forEach(r -> {
                    JsonObject userJsonObj = Json.createObjectBuilder()
                            .add("id", r.getUser().getId())
                            .add("name", r.getUser().getName())
                            .add("lastName", r.getUser().getLastName())
                            .add("picture", r.getUser().getPicture().toString())
                            .build();

                    JsonObject userRating = Json.createObjectBuilder()
                            .add("id",r.getUserRating().getId())
                            .add("rateValue",r.getUserRating().getRateValue())
                            .add("comment",r.getUserRating().getComment())
                            .build();

                    JsonObject ratingJsonObj = Json.createObjectBuilder()
                            .add("user",userJsonObj)
                            .add("userRating",userRating)
                            .build();

                    userRatingBuilder.add(ratingJsonObj);
                });
                JsonArray ratingsArr = userRatingBuilder.build();

                JsonArrayBuilder reservationBuilder = Json.createArrayBuilder();
                ((User)obj).setReservation(userService.findReservationsById(obj.getId(), 10, Optional.of(0)));

                ((User)obj).getReservation().forEach(r -> {
                    JsonObject artistJsonObj = Json.createObjectBuilder()
                            .add("id", r.getArtist().getId())
                            .add("name", r.getArtist().getName())
                            .add("lastName", r.getArtist().getLastName())
                            .add("email", r.getArtist().getEmail())
                            .add("picture", r.getArtist().getPicture().toString())
                            .build();

                    JsonObject serviceProvidedJsonObj = Json.createObjectBuilder().
                            add("name", ((ServiceProvided)obj).getName()).
                            add("price", ((ServiceProvided)obj).getPrice()).
                            build();

                    JsonObject ratingObj = Json.createObjectBuilder()
                            .add("id",r.getId())
                            .add("status",r.getStatus().toString())
                            .add("datTimeFrom",r.getDatTimeFrom().toString())
                            .add("datTimeTo",r.getDatTimeTo().toString())
                            .add("artist",artistJsonObj)
                            .add("servicesProvided",serviceProvidedJsonObj)
                            .build();
                });
                JsonArray reservationArray = reservationBuilder.build();

                JsonArrayBuilder servicesProvidedBuilder = Json.createArrayBuilder();
                ((User)obj).setServiceProvided(userService.findServicesProvidedById(obj.getId(),10, Optional.of(0)));

                ((User)obj).getServiceProvided().forEach(s ->{
                    JsonObject serviceProvided = Json.createObjectBuilder().
                            add("name", s.getName()).
                            add("price", s.getPrice()).
                            build();

                    servicesProvidedBuilder.add(serviceProvided);
                });
                JsonArray servicesProvided = servicesProvidedBuilder.build();

                JsonObject userObj = Json.createObjectBuilder()
                        .add("id", obj.getId())
                        .add("name", ((User)obj).getName())
                        .add("lastName", ((User)obj).getLastName())
                        .add("email", ((User)obj).getEmail())
                        .add("picture", ((User)obj).getPicture() == null ? "" : ((User)obj).getPicture().toString())
                        .add("userRatings", ratingsArr)
                        .add("reservation", reservationArray)
                        .add("servicesProvided", servicesProvided)
                        .build();

                return userObj;
            }

            @Override
            public AbstractEntity adaptFromJson(JsonObject obj) throws Exception {
                return null;
            }
        });

        this.jsonb = JsonbBuilder.create(config);
    }

    @Path("/") @GET
    public Response getUsers(){
        Collection<?> users = userService.getUsers(10, java.util.Optional.of(0));

        return Response.ok(jsonb.toJson(users)).build();
    }
    
    @Path("/") @POST
    public Response saveUser(@NotNull @Valid User user) {
        User newUser = userService.createUser(user);
        return Response.ok(newUser).build();
    }
    
    @Path("/{query}")  @POST
    public Response searchUser(@NotNull @PathParam("query") String query) {
//    	hook up elasticsearch here
        return null;
    }

    @Path("/{id}") @GET
    public Response findUserById(@NotNull @PathParam("id") Long id) {
    	User user = userService.findUserById(id);
    	return Response.ok(jsonb.toJson(user)).build();
    }

    @Path("/") @PUT
    public Response updateUser(@NotNull @Valid User user) throws NoResultExceptionMapper {
        User _user = userService.updateUser(user);
        return Response.ok(_user).build();
    }
}
