package com.railroad.rest.reservation;

import com.railroad.common.annotation.Log;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.entity.reservation.Reservation;
import com.railroad.entity.reservation.ReservationDto;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/reservation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationRest {

    @Inject
    private  ReservationService reservationService;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<Reservation>() {

            @Override
            public JsonObject adaptToJson(Reservation obj) throws Exception {
                JsonObject client = Json.createObjectBuilder()
                        .add("id", obj.getClient().getId())
                        .add("name", obj.getClient().getName())
                        .add("lastName", obj.getClient().getLastName())
                        .add("email", obj.getClient().getEmail())
                        .build();

                JsonObject artist = Json.createObjectBuilder()
                        .add("id", obj.getArtist().getId())
                        .add("name", obj.getArtist().getName())
                        .add("lastName", obj.getArtist().getLastName())
                        .add("email", obj.getArtist().getEmail())
                        .build();

                JsonObject serviceProvided = Json.createObjectBuilder()
                        .add("id", obj.getServiceProvided().getId())
                        .add("name", obj.getServiceProvided().getName())
                        .add("price", obj.getServiceProvided().getPrice())
                        .build();

                JsonObject reservation = Json.createObjectBuilder()
                        .add("id", obj.getId())
                        .add("client", client)
                        .add("artist", artist)
                        .add("note", obj.getNote())
                        .add("serviceProvided", serviceProvided)
                        .add("startDateTime", obj.getStartDateTime().toString())
                        .add("endDateTime", obj.getEndDateTime().toString())
                        .add("status", obj.getStatus().toString())
                        .build();

                return reservation;
            }

            @Override
            public Reservation adaptFromJson(JsonObject obj) throws Exception {
                return null;
            }
        });

        this.jsonb = JsonbBuilder.create(config);
    }

    @Path("/") @GET
    public Response findAppointments(
            @QueryParam(value = "maxResults") @DefaultValue(value = "10") int maxResults,
            @QueryParam(value = "firstResults") @DefaultValue(value = "0") int firstResults
    ){
        Collection<Reservation> res = this.reservationService.findAll(maxResults, firstResults);
        return Response.ok(jsonb.toJson(res)).build();
    }

    @Path("/") @POST
    public Response bookAppointment(@NotNull ReservationDto reservation){
        Reservation res = this.reservationService.bookAppointment(reservation);
        return Response.ok(jsonb.toJson(res)).build();
    }

    @Path("/{id: \\d+}") @GET
    public Response findAppointment(@PathParam(value = "id") Long id){
        Reservation res = this.reservationService.findById(id);
        return Response.ok(jsonb.toJson(res)).build();
    }
}
