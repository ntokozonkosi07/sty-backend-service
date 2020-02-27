package com.railroad.rest.requirement;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.common.filters.LoggingFilter;
import com.railroad.common.producers.EntityManagerProducer;
import com.railroad.configuration.config;
import com.railroad.entity.Reservation;
import com.railroad.entity.User;
import com.railroad.entity.UserRating;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import com.railroad.rest.user.UserRest;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class RequirementRestTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "style-beat.war")
                .addPackage(NoResultExceptionMapper.class.getPackage())
                .addPackage(RequirementRest.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(EntityManagerProducer.class.getPackage())
                .addPackage(LoggingFilter.class.getPackage())
                .addClasses(ServiceProvided.class, Requirement.class,com.railroad.entity.adapters.EntityAdapter.class,EntityAdapter.class,config.class)
                .addPackage(CustomExceptionMapperQualifier.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    String url = "http://localhost:9999/style-beat/api/v1/requirement";

    JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<Requirement>() {

        @Override
        public JsonObject adaptToJson(Requirement obj){

            JsonArray jsonArray = Json.createArrayBuilder().build();


            JsonObject jsonObj = Json.createObjectBuilder().
                    add("name", obj.getName()).
                    add("description", obj.getDescription()).
                    add("price", obj.getPrice()).
                    add("servicesProvided", jsonArray).
                    build();

            return jsonObj;
        }

        @Override
        public Requirement adaptFromJson(JsonObject obj) {
            Requirement req = new Requirement();
            req.setId(((JsonNumber)obj.get("id")).longValue());
            req.setName(((JsonString)obj.get("name")).getString());
            req.setDescription(((JsonString)obj.get("description")).getString());
            req.setPrice(((JsonNumber)obj.get("price")).doubleValue());

            Collection services = new ArrayList();

            obj.get("servicesProvided").asJsonArray().forEach(x -> services.add(x));

            req.setServicesProvided(services);
            return req;
        }
    });

    @Test
    @RunAsClient
    @InSequence(1)
    public void get_list_of_requirements() throws IOException {
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
    public void save_requirement() throws IOException {
        HttpPost post = new HttpPost(url);

        Requirement req = new Requirement();
        req.setName("Hair Weave");
        req.setDescription("its just fake hair");
        req.setPrice(42.5);
        req.setServicesProvided(null);

        Jsonb jsonb = JsonbBuilder.create(config);
        String json = jsonb.toJson(req);

        String result;

        // send a JSON data
        post.setEntity(new StringEntity(json));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());

        }


        Requirement _req = jsonb.fromJson(result, Requirement.class);
        assertEquals(_req.getName(), req.getName());
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void should_get_requirement_by_id() throws IOException {
        HttpGet request = new HttpGet(url+"/1");

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
            Requirement req = jsonb.fromJson(json, Requirement.class);

            assertEquals(200, response.getStatusLine().getStatusCode());
            assertEquals("Hair Weave", req.getName());
            assertEquals("its just fake hair", req.getDescription());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
    }
}
