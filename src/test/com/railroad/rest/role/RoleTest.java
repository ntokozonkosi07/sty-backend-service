package com.railroad.rest.role;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.common.filters.LoggingFilter;
import com.railroad.common.producers.EntityManagerProducer;
import com.railroad.configuration.config;
import com.railroad.entity.Role;
import com.railroad.entity.reservation.Reservation;
import com.railroad.rest.common.AbstractService;
import com.railroad.rest.common.HttpUtils;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import lombok.Cleanup;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class RoleTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "style-beat.war")
                .addPackage(RoleRest.class.getPackage())
                .addPackage(NoResultExceptionMapper.class.getPackage())
                .addPackage(Role.class.getPackage())
                .addPackage(EntityManagerProducer.class.getPackage())
                .addPackage(LoggingFilter.class.getPackage())
                .addPackage(Reservation.class.getPackage())
                .addPackage(AbstractService.class.getPackage())
                .addClasses(EntityAdapter.class, config.class)
                .addPackage(CustomExceptionMapperQualifier.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private HttpUtils http;
    private String url;
    private Jsonb jsonb;

    @Before
    public void beforeEach(){
        this.http = new HttpUtils();
        this.url = "http://localhost:8080/style-beat/api/v1/role";

        JsonbConfig config = new JsonbConfig().withAdapters(new RoleMashaler());

        this.jsonb = JsonbBuilder.create(config);
    }

    @Test @InSequence(1) @RunAsClient
    public void should_find_empty_list_of_roles() throws IOException {
        @Cleanup() CloseableHttpResponse response = http.get(url);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(json, "[]");
    }

    @Test @InSequence(2) @RunAsClient
    public void should_save_role() throws IOException {
        Role roleIn = new Role("Hair Stylist");

        @Cleanup() CloseableHttpResponse response = http.post(url,jsonb.toJson(roleIn));
        HttpEntity entity = response.getEntity();

        Role roleOut = jsonb.fromJson(EntityUtils.toString(entity),Role.class);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(roleIn.getName(),roleOut.getName());
    }

    @Test @InSequence(3) @RunAsClient
    public void should_throw_an_exception_for_saving_a_duplicate() throws IOException {
        Role roleIn = new Role("Hair Stylist");

        @Cleanup() CloseableHttpResponse response = http.post(url,jsonb.toJson(roleIn));

        assertEquals(409, response.getStatusLine().getStatusCode());
    }

    @Test @InSequence(4) @RunAsClient
    public void should_find_list_of_roles() throws IOException {
        @Cleanup() CloseableHttpResponse response = http.get(url);
        HttpEntity entity = response.getEntity();
//        Collection<Role> roles = jsonb.fromJson(EntityUtils.toString(entity), new ArrayList<Role>(){}.getClass().getGenericSuperclass());
        String json = EntityUtils.toString(entity);
        Collection<Role> roles = jsonb.fromJson(json, ArrayList.class);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(1, roles.size());
    }

    @Test @InSequence(5) @RunAsClient
    public void should_update_the_saved_role() throws IOException, URISyntaxException {
        Role roleIn = new Role("Bravo Hair Stylist");
        roleIn.setId(1L);

        @Cleanup() CloseableHttpResponse response = http.put(url,jsonb.toJson(roleIn));
        HttpEntity entity = response.getEntity();

        String json = EntityUtils.toString(entity);

        Role roleOut = jsonb.fromJson(json,Role.class);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals(roleOut.getName(), roleOut.getName());
    }

    @Test @InSequence(6) @RunAsClient
    public void should_get_role_by_id() throws IOException {
        @Cleanup() CloseableHttpResponse response = http.get(url+"/1");
        HttpEntity entity = response.getEntity();

        String json = EntityUtils.toString(entity);

        Role roleOut = this.jsonb.fromJson(json,Role.class);

        assertNotNull(roleOut);
    }

}
