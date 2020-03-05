package com.railroad.rest.serviceProvided;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.common.filters.LoggingFilter;
import com.railroad.common.producers.EntityManagerProducer;
import com.railroad.configuration.config;
import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import com.railroad.rest.user.UserRest;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import java.io.IOException;

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
}
