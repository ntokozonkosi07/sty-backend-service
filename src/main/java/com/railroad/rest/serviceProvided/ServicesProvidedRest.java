package com.railroad.rest.serviceProvided;

import com.railroad.common.annotation.Log;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.entity.ServiceProvided;
import com.railroad.rest.requirement.RequirementService;
import com.railroad.rest.user.UserService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Log
@Path("/services-provided")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicesProvidedRest {

    @Inject private ServiceProvidedService serviceProv;
    @Inject private RequirementService reqService;
    @Inject private UserService userService;

    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new EntityAdapter<ServiceProvided>() {
            @Override
            public JsonObject adaptToJson(ServiceProvided obj) throws Exception {
//                ((ServiceProvided)obj).setRequirements(reqService.findRequirementsByServiceProvidedId(obj.getId(), 10, 0));
                ((ServiceProvided)obj).setArtists(userService.findArtistByServiceProvidedId(obj.getId(),10, 0));


//                JsonArrayBuilder jsonReqArrBuilder = Json.createArrayBuilder();
//                ((ServiceProvided)obj).getRequirements().forEach(r -> {
//                    JsonArrayBuilder jsonServArr = Json.createArrayBuilder();
//
//                    JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder()
//                            .add("name", r.getName())
//                            .add("price", r.getPrice())
//                            .add("description", r.getDescription());
//                            .add("servicesProvided", jsonServArr);
//
//                    if(obj.getId() != null) jsonObjBuilder.add("id", obj.getId());
//
//                    JsonObject jsonObj = jsonObjBuilder.build();
//                    jsonReqArrBuilder.add(jsonObj);
//                });
//                JsonArray jsonReqArr = jsonReqArrBuilder.build();

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
//                        add("price", ((ServiceProvided)obj).getPrice()).
//                        add("requirements", jsonReqArr).
                        add("artists", jsonArtArr);

                if(obj.getId() != null){
                    jsonObjBuilder.add("id", obj.getId());
                }

                return jsonObjBuilder.build();
            }

            @Override
            public ServiceProvided adaptFromJson(JsonObject obj) throws Exception {
                return null;
            }
        }));
    }

    @GET @Path("/")
    public Response getSerivceProvided(
            @QueryParam("maxResults") @DefaultValue(value = "10") Integer maxResults,
            @QueryParam("startIndex") @DefaultValue(value = "0") Integer firstResults
    ){
        Collection<ServiceProvided> servs = serviceProv.getSerivcesProvided(maxResults,firstResults);

        return Response.ok(jsonb.toJson(servs)).build();
    }

    @POST @Path("/")
    public Response saveServiceProvided(ServiceProvided serviceProvided){
        ServiceProvided serv = serviceProv.saveServiceProvided(serviceProvided);
        return Response.ok(jsonb.toJson(serv)).build();
    }

    @GET @Path("/{id}")
    public Response getServiceProvided(@PathParam("id") @DefaultValue(value = "0") Long id){
        ServiceProvided servProd = serviceProv.findById(id);

        return Response.ok(jsonb.toJson(servProd)).build();
    }

    @PUT @Path("/")
    public Response updateServiceProvided(@Valid ServiceProvided service){
        ServiceProvided serv = serviceProv.update(service);
        return Response.ok(jsonb.toJson(serv)).build();
    }
}
