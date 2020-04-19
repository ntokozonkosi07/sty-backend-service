package com.railroad.rest.user;

import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.entity.User;
import com.railroad.entity.reservation.Reservation;
import com.railroad.rest.reservation.ReservationService;
import com.railroad.rest.serviceProvided.ServiceProvidedService;
import com.railroad.rest.userRating.UserRatingService;
import com.railroad.rest.userRoles.UserRoleService;
import com.railroad.security.SecurityRealm;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.logging.Logger;

public class UserAdapter extends EntityAdapter<User> {
    private Logger logger;
    private UserRatingService urs;
    private ReservationService rs;
    private ServiceProvidedService sps;
    private UserRoleService userRoleService;

    public UserAdapter(){
        this.logger = Logger.getLogger(SecurityRealm.class.getName());

        try {
            InitialContext ctx = new InitialContext();
            String moduleName = (String) ctx.lookup("java:module/ModuleName");

            this.urs = (UserRatingService) ctx.lookup(String.format("java:global/%s/UserRatingService", moduleName));
            this.rs = (ReservationService) ctx.lookup(String.format("java:global/%s/ReservationService", moduleName));
            this.sps = (ServiceProvidedService) ctx.lookup(String.format("java:global/%s/ServiceProvidedService", moduleName));
            this.userRoleService = (UserRoleService) ctx.lookup(String.format("java:global/%s/UserRoleService", moduleName));
        } catch (NamingException ex) {
            logger.warning("Cannot do the JNDI Lookup to instantiate the User service : " + ex);
        }
    }

    @Override
    public JsonObject adaptToJson(User obj) throws Exception {

        JsonArrayBuilder userRatingBuilder = Json.createArrayBuilder();
        ((User)obj).setUserRatings(urs.getUserRatingsByUserId(obj.getId(), 10, 0));

        ((User)obj).getUserRatings().forEach(r -> {
            JsonObject userJsonObj = Json.createObjectBuilder()
                    .add("id", r.getRatee().getId())
                    .add("name", r.getRatee().getName())
                    .add("lastName", r.getRatee().getLastName())
                    .add("picture", r.getRatee().getPicture().toString())
                    .build();

            JsonObject userRating = Json.createObjectBuilder()
                    .add("id",r.getRating().getId())
                    .add("rateValue",r.getRating().getRateValue())
                    .add("comment",r.getRating().getComment())
                    .build();

            JsonObject ratingJsonObj = Json.createObjectBuilder()
                    .add("user",userJsonObj)
                    .add("userRating",userRating)
                    .build();

            userRatingBuilder.add(ratingJsonObj);
        });
        JsonArray ratingsArr = userRatingBuilder.build();

        JsonArrayBuilder reservationBuilder = Json.createArrayBuilder();
        Collection<Reservation> res = rs.findReservationsByClientId(obj.getId(),10,0 );
        ((User)obj).setReservations(res);

        ((User)obj).getReservations().forEach(r -> {
            JsonObject artistJsonObj = Json.createObjectBuilder()
                    .add("id", r.getArtist().getId())
                    .add("name", r.getArtist().getName())
                    .add("lastName", r.getArtist().getLastName())
                    .add("email", r.getArtist().getEmail())
                    .add("picture", r.getArtist().getPicture().toString())
                    .build();

            JsonObject serviceProvidedJsonObj = Json.createObjectBuilder().
//                            add("name", ((ServiceProvided)obj).getName()).
//                            add("price", ((ServiceProvided)obj).getPrice()).
        build();

            JsonObject ratingObj = Json.createObjectBuilder()
                    .add("status",r.getStatus().toString())
                    .add("startDateTime",r.getStartDateTime().toString())
                    .add("endDateTime",r.getEndDateTime().toString())
                    .add("artist",artistJsonObj)
                    .add("servicesProvided",serviceProvidedJsonObj)
                    .build();

//                    reservationBuilder.add()
        });
        JsonArray reservationArray = reservationBuilder.build();

        JsonArrayBuilder servicesProvidedBuilder = Json.createArrayBuilder();
        ((User)obj).setServicesProvided(sps.getServiceProvidedByArtistId(obj.getId(),10, 0));

        ((User)obj).getServicesProvided().forEach(s ->{
            JsonObject serviceProvided = Json.createObjectBuilder().
                    add("id", s.getId()).
                    add("name", s.getName()).
                    add("price", s.getPrice()).
                    build();

            servicesProvidedBuilder.add(serviceProvided);
        });
        JsonArray servicesProvided = servicesProvidedBuilder.build();

        JsonArrayBuilder rolesBuilder = Json.createArrayBuilder();
        ((User)obj).setUserRoles(userRoleService.findUserRolesByUserId(obj.getId(), 10,  0));

        ((User)obj).getUserRoles().forEach(r ->{
            JsonObject role = Json.createObjectBuilder().
                    add("id",r.getRole().getId()).
                    add("name", r.getRole().getName()).
                    build();

            rolesBuilder.add(role);
        });
        JsonArray roles = rolesBuilder.build();

        JsonObject userObj = Json.createObjectBuilder()
                .add("id", obj.getId())
                .add("name", ((User)obj).getName())
                .add("lastName", ((User)obj).getLastName())
                .add("email", ((User)obj).getEmail())
                .add("picture", ((User)obj).getPicture() == null ? "" : ((User)obj).getPicture().toString())
                .add("userRatings", ratingsArr)
                .add("reservation", reservationArray)
                .add("servicesProvided", servicesProvided)
                .add("roles", roles)
                .build();

        return userObj;
    }

    @Override
    public User adaptFromJson(JsonObject obj) throws Exception {
        return null;
    }
}
