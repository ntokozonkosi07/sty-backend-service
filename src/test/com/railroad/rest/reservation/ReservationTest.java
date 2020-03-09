package com.railroad.rest.reservation;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.common.filters.LoggingFilter;
import com.railroad.common.producers.EntityManagerProducer;
import com.railroad.configuration.config;
import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.rest.common.HttpUtils;
import com.railroad.rest.exception.mappers.NoResultExceptionMapper;
import com.railroad.rest.requirement.RequirementRest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ReservationTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "style-beat.war")
                .addPackage(NoResultExceptionMapper.class.getPackage())
                .addPackage(ReservationRest.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(EntityManagerProducer.class.getPackage())
                .addPackage(LoggingFilter.class.getPackage())
                .addClasses(ServiceProvided.class, Requirement.class,com.railroad.entity.adapters.EntityAdapter.class, EntityAdapter.class, config.class)
                .addPackage(CustomExceptionMapperQualifier.class.getPackage())
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private String url;
    private HttpUtils http;

    @Before
    public void beforeEach(){
        this.http = new HttpUtils();
        this.url = "http://localhost:8080/style-beat/api/v1/reservation";
    }



    @Test
    @RunAsClient
    @InSequence(1)
    @Ignore
    public void should_book_a_reservation(){

    }
}
