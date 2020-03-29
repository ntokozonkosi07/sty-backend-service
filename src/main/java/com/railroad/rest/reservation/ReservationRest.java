package com.railroad.rest.reservation;

import com.railroad.common.annotation.Log;
import com.railroad.entity.AbstractEntity;
import com.railroad.entity.User;
import com.railroad.entity.adapters.EntityAdapter;
import com.railroad.entity.reservation.Reservation;
import com.railroad.entity.reservation.ReservationDto;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log
@Path("/reservation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationRest {

    @Inject
    private  ReservationService rs;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<Reservation>() {

            @Override
            public JsonObject adaptToJson(AbstractEntity obj) throws Exception {
                return null;
            }

            @Override
            public AbstractEntity adaptFromJson(JsonObject obj) throws Exception {
                return null;
            }
        });
    }

    @Path("/") @POST
    public Response bookAppointment(@NotNull ReservationDto reservation){

        return Response.ok(jsonb.toJson(null)).build();
    }
}
