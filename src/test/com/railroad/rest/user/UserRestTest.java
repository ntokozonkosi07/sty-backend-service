package com.railroad.rest.user;

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

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class UserRestTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "style-beat.war")
                .addPackage(UserRest.class.getPackage())
                .addPackage(NoResultExceptionMapper.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(EntityManagerProducer.class.getPackage())
                .addPackage(LoggingFilter.class.getPackage())
                .addClasses(ServiceProvided.class, Requirement.class,com.railroad.entity.adapters.EntityAdapter.class,EntityAdapter.class,config.class)
                .addPackage(CustomExceptionMapperQualifier.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    String url = "http://localhost:8080/style-beat/api/v1/users";

    @Test @RunAsClient  @InSequence(1)
    public void get_list_of_users() throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(request);

            try {       // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String json = null;
                if (entity != null) {
                    // return it as a String
                    json = EntityUtils.toString(entity);
                    System.out.println(json);
                }

                assertEquals(200, response.getStatusLine().getStatusCode());
                assertEquals(json, "[]");

            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }
    }

    @Test @RunAsClient @InSequence(2)
    public void save_user() throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);

        User user = new User();
        user.setName("Baleka");
        user.setLastName("Mbethe");
        user.setEmail("baleka.mbethe@balekas.co.za");

        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(user);


        // send a JSON data
        post.setEntity(new StringEntity(json));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());

        }
        user.setId(1L);
        User _usr = jsonb.fromJson(result, User.class);
        assertEquals(_usr.getEmail(), user.getEmail());
    }

    @Test @RunAsClient  @InSequence(3)
    public void get_user_by_id() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet(url+"/1");

            CloseableHttpResponse response = httpClient.execute(request);

            try {      // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String json = null;
                if (entity != null) {
                    // return it as a String
                    json = EntityUtils.toString(entity);
                    System.out.println(json);
                }

                JsonbConfig config = new JsonbConfig().withAdapters(new EntityAdapter<User>() {

                    @Override
                    public JsonObject adaptToJson(User obj) throws Exception {
                        return null;//super.adaptToJson(obj);
                    }

                    @Override
                    public User adaptFromJson(JsonObject obj) throws Exception {
                        User user = new User();
                        user.setId(Long.parseLong(String.valueOf(obj.get("id")+"")));
                        user.setLastName(((JsonString)obj.get("lastName")).getString());
                        user.setName(((JsonString)obj.get("name")).getString());
                        user.setEmail(((JsonString)obj.get("email")).getString());
                        user.setPicture(null);
                        user.setUserRatings((Collection<UserRating>)obj.get("userRatings"));
                        user.setReservation((Collection<Reservation>)obj.get("reservation"));

                        return user;
                    }
                });

                User _usr = JsonbBuilder.create(config).fromJson(json, User.class);
                assertEquals(_usr.getName(), "Baleka");
                assertEquals(_usr.getLastName(), "Mbethe");
                assertEquals(200,response.getStatusLine().getStatusCode());

            } catch(Exception e){
                System.out.println(e.getLocalizedMessage());
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }

    @Test @RunAsClient @InSequence(4)
    public void update_user() throws IOException {
        String result = "";
        HttpPut putReq = new HttpPut(url);

        User user = new User();
        user.setName("Baleka");
        user.setLastName("Ndlela");
        user.setEmail("baleka.dlela@balekas.co.za");

        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(user);


        // send a JSON data
        putReq.setEntity(new StringEntity(json));
        putReq.setHeader("Accept", "application/json");
        putReq.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(putReq)) {

            result = EntityUtils.toString(response.getEntity());

        }

        User _user = jsonb.fromJson(result, User.class);
        assertEquals("baleka.dlela@balekas.co.za", _user.getEmail());
        assertEquals("Ndlela", _user.getLastName());
    }
}
