package com.railroad.rest.serviceProvided;

import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class ServiceProvidedService {
    @Inject
    private ServiceProvidedQuery sq;


    public Collection<ServiceProvided> getRequirements(int i) {
        return new ArrayList<>();
    }
}
