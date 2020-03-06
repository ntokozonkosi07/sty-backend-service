package com.railroad.rest.serviceProvided;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.common.filters.LoggingFilter;
import com.railroad.common.producers.EntityManagerProducer;
import com.railroad.configuration.config;
import com.railroad.entity.Artist;
import com.railroad.entity.Reservation;
import com.railroad.entity.User;
import com.railroad.entity.UserRating;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ServiceProvidedTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "style-beat.war")
                .addPackage(ServicesProvidedRest.class.getPackage())
                .addPackage(NoResultExceptionMapper.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(EntityManagerProducer.class.getPackage())
                .addPackage(LoggingFilter.class.getPackage())
                .addClasses(ServiceProvided.class, Requirement.class,com.railroad.entity.adapters.EntityAdapter.class, EntityAdapter.class, config.class)
                .addPackage(CustomExceptionMapperQualifier.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<ServiceProvided>(){

        @Override
        public JsonObject adaptToJson(ServiceProvided obj) {
            JsonArrayBuilder jsonReqArr = Json.createArrayBuilder();
            obj.getRequirements().forEach(r -> {
                JsonArrayBuilder jsonServArr = Json.createArrayBuilder();

                JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder()
                        .add("name", r.getName())
                        .add("price", r.getPrice())
                        .add("description", r.getDescription())
                        .add("servicesProvided", jsonServArr);

                if(obj.getId() != null) jsonObjBuilder.add("id", obj.getId());

                JsonObject jsonObj = jsonObjBuilder.build();
                jsonReqArr.add(jsonObj);
            });

            JsonArray jsonArtArr = Json.createArrayBuilder().build();
            obj.getArtists().forEach(a -> {
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

                jsonArtArr.add(jsonObj);
            });

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder().
                    add("name", obj.getName()).
                    add("price", obj.getPrice()).
                    add("requirements", jsonReqArr).
                    add("artists", jsonArtArr);

            if(obj.getId() != null){
                jsonObjBuilder.add("id", obj.getId());
            }

            return jsonObjBuilder.build();
        }

        @Override
        public ServiceProvided adaptFromJson(JsonObject obj) {
            ServiceProvided serv = new ServiceProvided();
            serv.setId(((JsonNumber)obj.get("id")).longValue());
            serv.setName(((JsonString)obj.get("name")).getString());
            serv.setPrice(((JsonNumber)obj.get("price")).doubleValue());

            Collection<Requirement> reqs = new ArrayList<>();
            JsonArray jsonReqs = obj.get("requirements").asJsonArray();
            if (jsonReqs != null) {
                jsonReqs.forEach(r ->{
                    Requirement req = new Requirement();
                    req.setId(((JsonNumber)((JsonObject)r).get("id")).longValue());
                    req.setName(((JsonString)((JsonObject)r).get("name")).getString());
                    req.setDescription(((JsonString)((JsonObject)r).get("description")).getString());
                    req.setPrice(((JsonNumber)((JsonObject)r).get("price")).doubleValue());
                    reqs.add(req);
                });
            }

            serv.setRequirements(reqs);

            Collection<Artist> artists = new ArrayList<>();
            JsonArray jsonArts = obj.get("artists").asJsonArray();
            if(jsonArts !=null){
                jsonArts.forEach(a -> {
                    Artist artist = new Artist();
                    artist.setId(((JsonNumber)((JsonObject)a).get("id")).longValue());
                    artist.setLastName(((JsonString)((JsonObject)a).get("lastName")).getString());
                    artist.setName(((JsonString)((JsonObject)a).get("name")).getString());
                    artist.setEmail(((JsonString)((JsonObject)a).get("email")).getString());
                    artist.setPicture(null);
                    artist.setUserRatings(new ArrayList<>());
                    artist.setReservation(new ArrayList<>());

                    artists.add(artist);
                });
            }

            serv.setArtists(artists);

            return serv;
        }
    });

    String url = "http://localhost:8080/style-beat/api/v1/services-provided";

    @Test
    @RunAsClient
    @InSequence(1)
    public void should_get_list_of_services_provided() throws IOException {
        HttpGet request = new HttpGet(url);

        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)
        ) {
            HttpEntity entity = response.getEntity();
            String json = null;
            if (entity != null) {
                json = EntityUtils.toString(entity);
                System.out.println(json);
            }

            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals(json, "[]");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void should_save_service_provided() throws IOException {
        HttpPost post = new HttpPost(url);

        ServiceProvided serv = new ServiceProvided();
        serv.setName("Hair Weave");
        serv.setRequirements(new ArrayList<>());
        serv.setArtists(new ArrayList<>());
        serv.setPrice(5.21);

        Jsonb jsonb = JsonbBuilder.create(config);
        String json = jsonb.toJson(serv);

        String result;

        // send a JSON data
        post.setEntity(new StringEntity(json));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());

        }


        ServiceProvided _serv = jsonb.fromJson(result, ServiceProvided.class);
        assertEquals(_serv.getName(), serv.getName());
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void should_get_list_of_services_provided_after_some_date_has_been_saved() throws IOException {
        HttpGet request = new HttpGet(url);

        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)
        ) {
            HttpEntity entity = response.getEntity();
            String json = null;
            if (entity != null) {
                json = EntityUtils.toString(entity);
                System.out.println(json);
            }

            Jsonb jsonb = JsonbBuilder.create(config);
            Collection res = jsonb.fromJson(json, new ArrayList<ServiceProvided>(){}.getClass().getGenericSuperclass());

            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals(1, res.size());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
    }

    @Test
    @RunAsClient
    @InSequence(4)
    public void should_get_saved_service_provided() throws IOException {
        HttpPost request = new HttpPost(url+"/1");

        try(
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)
        ) {

            HttpEntity entity = response.getEntity();
            String json = null;
            if (entity != null) {
                // return it as a String
                json = EntityUtils.toString(entity);
                System.out.println(json);
            }

            ServiceProvided servProd = JsonbBuilder.create(config).fromJson(json, ServiceProvided.class);
            assertEquals(servProd.getName(), "Hair Weave");
            assertEquals(200, response.getStatusLine().getStatusCode());
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }

    @Test
    @RunAsClient
    @InSequence(5)
    public void should_update_saved_service_provided() throws IOException {
        HttpPut request = new HttpPut(url);

        ServiceProvided serv = new ServiceProvided();
        serv.setId(1L);
        serv.setName("Hair Weave bomb");
        serv.setRequirements(new ArrayList<>());
        serv.setArtists(new ArrayList<>());
        serv.setPrice(54.5);

        Jsonb jsonb = JsonbBuilder.create(config);
        String json = jsonb.toJson(serv);

        String result;

        // send a JSON data
        request.setEntity(new StringEntity(json));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        try(
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)
        ) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                json = EntityUtils.toString(entity);
                System.out.println(json);
            }

            ServiceProvided servProd = JsonbBuilder.create(config).fromJson(json, ServiceProvided.class);
            assertEquals(servProd.getName(), serv.getName());
            assertEquals(200, response.getStatusLine().getStatusCode());
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }
}
