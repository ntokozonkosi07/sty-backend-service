package com.railroad.rest.serviceProvided;

import com.railroad.common.annotation.Log;
import com.railroad.entity.AbstractEntity;
import com.railroad.entity.adapters.EntityAdapter;
import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Log
@Path("/services-provided")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicesProvidedRest {

    @Inject
    ServiceProvidedService serviceProv;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new EntityAdapter<ServiceProvided>() {
            @Override
            public JsonObject adaptToJson(AbstractEntity obj) throws Exception {
                ((ServiceProvided)obj).setRequirements(serviceProv.getServiceProvidedRequirements(obj.getId(),10, Optional.of(0)));
                ((ServiceProvided)obj).setArtists(serviceProv.getServiceProvidedArtists(obj.getId(),10, Optional.of(0)));


                JsonArrayBuilder jsonReqArrBuilder = Json.createArrayBuilder();
                ((ServiceProvided)obj).getRequirements().forEach(r -> {
                    JsonArrayBuilder jsonServArr = Json.createArrayBuilder();

                    JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder()
                            .add("name", r.getName())
                            .add("price", r.getPrice())
                            .add("description", r.getDescription())
                            .add("servicesProvided", jsonServArr);

                    if(obj.getId() != null) jsonObjBuilder.add("id", obj.getId());

                    JsonObject jsonObj = jsonObjBuilder.build();
                    jsonReqArrBuilder.add(jsonObj);
                });
                JsonArray jsonReqArr = jsonReqArrBuilder.build();

                JsonArrayBuilder jsonArtArrBuilder = Json.createArrayBuilder();
                ((ServiceProvided)obj).getArtists().forEach(a -> {
                    JsonArray jsonArr = Json.createArrayBuilder().build();
                    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                            .add("email", a.getEmail())
                            .add("name", a.getName())
                            .add("lastName", a.getLastName())
                            .add("picture", "")
                            .add("reservations",jsonArr)
                            .add("userRatings",jsonArr)
                            .add("servicesProvided",jsonArr);

                    if(obj.getId() != null) jsonObjectBuilder.add("id", obj.getId());
                    JsonObject jsonObj = jsonObjectBuilder.build();

                    jsonArtArrBuilder.add(jsonObj);
                });
                JsonArray jsonArtArr = jsonArtArrBuilder.build();

                JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder().
                        add("name", ((ServiceProvided)obj).getName()).
                        add("price", ((ServiceProvided)obj).getPrice()).
                        add("requirements", jsonReqArr).
                        add("artists", jsonArtArr);

                if(obj.getId() != null){
                    jsonObjBuilder.add("id", obj.getId());
                }

                return jsonObjBuilder.build();
            }

            @Override
            public AbstractEntity adaptFromJson(JsonObject obj) throws Exception {
                return null;
            }
        }));
    }

    @GET @Path("/")
    public Response getSerivcesProvided(
            @QueryParam("maxResults") @DefaultValue(value = "10") Integer maxResults,
            @QueryParam("startIndex") @DefaultValue(value = "0") Optional<Integer> firstResults
    ){
        Collection<ServiceProvided> servs = serviceProv.getSerivcesProvided(maxResults,firstResults);

        return Response.ok(jsonb.toJson(servs)).build();
    }

    @POST @Path("/")
    public Response saveServiceProvided(ServiceProvided serviceProvided){
        ServiceProvided serv = serviceProv.saveServiceProvided(serviceProvided);
        return Response.ok(jsonb.toJson(serv)).build();
    }

    @POST @Path("/{id}")
    public Response getServicesProvided(@PathParam("id") @DefaultValue(value = "0") Long id){
        ServiceProvided servProd = serviceProv.getServicesProvided(id);

        return Response.ok(jsonb.toJson(servProd)).build();
    }
}
